package jbar.service_core.Sell_Detail.Controller;

import jbar.service_core.Category.Model.Category;
import jbar.service_core.Category.Model.CategoryDTO;
import jbar.service_core.Multimedia.Model.Multimedia;
import jbar.service_core.Multimedia.Model.MultimediaDTO;
import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductRepository;
import jbar.service_core.Product_Category.Model.ProductCategory;
import jbar.service_core.Product_Category.Model.ProductCategoryRepository;
import jbar.service_core.Product_Multimedia.ProductMultimedia;
import jbar.service_core.Product_Multimedia.ProductMultimediaRepository;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.Sell_Detail.Model.SellDetail;
import jbar.service_core.Sell_Detail.Model.SellDetailDTO;
import jbar.service_core.Sell_Detail.Model.SellDetailRepository;
import jbar.service_core.Sell_Detail.Model.SellDetailResponseDTO;
import jbar.service_core.Sell_Detail.Model.TopProductChartDTO;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellDetailService {
    private final Logger log = LoggerFactory.getLogger(SellDetailService.class);
    private final SellDetailRepository sellDetailRepository;
    private final SellRepository sellRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMultimediaRepository productMultimediaRepository;

    @Autowired
    public SellDetailService(SellDetailRepository sellDetailRepository, 
            SellRepository sellRepository, 
            ProductRepository productRepository,
            ProductCategoryRepository productCategoryRepository,
            ProductMultimediaRepository productMultimediaRepository) {
        this.sellDetailRepository = sellDetailRepository;
        this.sellRepository = sellRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productMultimediaRepository = productMultimediaRepository;
    }

    public ResponseEntity<Message> create(SellDetailDTO sellDetailDTO) {
        try {
            Optional<Sell> sell = sellRepository.findById(sellDetailDTO.getSellId());
            if (sell.isEmpty()) {
                log.warn("Sell with id {} not found", sellDetailDTO.getSellId());
                return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Optional<Product> product = productRepository.findById(sellDetailDTO.getProductId());
            if (product.isEmpty()) {
                log.warn("Product with id {} not found", sellDetailDTO.getProductId());
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            SellDetail sellDetail = new SellDetail();
            sellDetail.setSell(sell.get());
            sellDetail.setProduct(product.get());
            sellDetail.setQuantity(sellDetailDTO.getQuantity());
            sellDetail.setUnitPrice(product.get().getPrice()); // Obtener el precio del producto
            sellDetail.setTotalPrice(sellDetailDTO.getQuantity() * product.get().getPrice());

            sellDetailRepository.save(sellDetail);

            log.info("SellDetail created successfully: {}", sellDetail);
            return new ResponseEntity<>(new Message(sellDetail, "SellDetail created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating SellDetail: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating SellDetail", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Message> update(Integer id, SellDetailDTO sellDetailDTO) {
        try {
            Optional<SellDetail> existingSellDetail = sellDetailRepository.findById(id);
            if (existingSellDetail.isEmpty()) {
                log.warn("SellDetail with id {} not found", id);
                return new ResponseEntity<>(new Message(null, "SellDetail not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            // Si la cantidad es cero, eliminar el SellDetail
            if (sellDetailDTO.getQuantity() == 0) {
                sellDetailRepository.delete(existingSellDetail.get());
                log.info("SellDetail with id {} deleted because quantity is zero", id);
                return new ResponseEntity<>(new Message(null, "SellDetail deleted because quantity is zero", TypesResponse.SUCCESS), HttpStatus.OK);
            }

            Optional<Sell> sell = sellRepository.findById(sellDetailDTO.getSellId());
            if (sell.isEmpty()) {
                log.warn("Sell with id {} not found", sellDetailDTO.getSellId());
                return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            Optional<Product> product = productRepository.findById(sellDetailDTO.getProductId());
            if (product.isEmpty()) {
                log.warn("Product with id {} not found", sellDetailDTO.getProductId());
                return new ResponseEntity<>(new Message(null, "Product not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            SellDetail sellDetail = existingSellDetail.get();
            sellDetail.setSell(sell.get());
            sellDetail.setProduct(product.get());
            sellDetail.setQuantity(sellDetailDTO.getQuantity());
            sellDetail.setUnitPrice(product.get().getPrice());
            sellDetail.setTotalPrice(sellDetailDTO.getQuantity() * product.get().getPrice());

            sellDetailRepository.save(sellDetail);

            log.info("SellDetail updated successfully: {}", sellDetail);
            return new ResponseEntity<>(new Message(sellDetail, "SellDetail updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating SellDetail: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error updating SellDetail", TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getTopSellingProducts(Timestamp startDate, Timestamp endDate) {
        List<Object[]> rawData = sellDetailRepository.findTopSellingProducts(startDate, endDate);

        List<TopProductChartDTO> chartData = rawData.stream()
                .map(row -> new TopProductChartDTO(
                        (String) row[0],
                        ((Number) row[1]).intValue()
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new Message(chartData, "Top selling products retrieved", TypesResponse.SUCCESS),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getSellDetailsBySellId(Integer sellId) {
        try {
            List<SellDetail> sellDetails = sellDetailRepository.findBySell_SellIdAndDeletedAtIsNull(sellId);
            
            if (sellDetails.isEmpty()) {
                return new ResponseEntity<>(
                    new Message(null, "No sell details found for the given sell ID", TypesResponse.ERROR),
                    HttpStatus.NOT_FOUND
                );
            }

            List<SellDetailResponseDTO> responseDTOs = sellDetails.stream()
                .map(detail -> {
                    Product product = productRepository.findById(detail.getProduct().getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                    
                    // Obtener categorías del producto
                    List<ProductCategory> productCategories = productCategoryRepository.findByProduct_ProductId(product.getProductId());
                    List<CategoryDTO> categoryDTOs = productCategories.stream()
                        .map(pc -> {
                            Category category = pc.getCategory();
                            CategoryDTO catDto = new CategoryDTO();
                            catDto.setCategoryId(category.getCategoryId());
                            catDto.setName(category.getName());
                            catDto.setStatus(category.getStatus());
                            return catDto;
                        })
                        .collect(Collectors.toList());

                    // Obtener multimedia del producto
                    List<ProductMultimedia> productMultimedia = productMultimediaRepository.findByProduct_ProductId(product.getProductId());
                    List<MultimediaDTO> multimediaDTOs = productMultimedia.stream()
                        .map(pm -> {
                            Multimedia media = pm.getMultimedia();
                            MultimediaDTO mDto = new MultimediaDTO();
                            mDto.setId(media.getMultimediaId());
                            mDto.setUrl(media.getUrl());
                            return mDto;
                        })
                        .collect(Collectors.toList());
                    
                    return new SellDetailResponseDTO(
                        detail.getSellDetailId(),
                        product.getProductId(),
                        product.getName(),
                        product.getDescription(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getTotalPrice(),
                        categoryDTOs,
                        multimediaDTOs
                    );
                })
                .collect(Collectors.toList());

            return new ResponseEntity<>(
                new Message(responseDTOs, "Sell details retrieved successfully", TypesResponse.SUCCESS),
                HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("Error retrieving sell details: {}", e.getMessage());
            return new ResponseEntity<>(
                new Message(null, "Error retrieving sell details: " + e.getMessage(), TypesResponse.ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
