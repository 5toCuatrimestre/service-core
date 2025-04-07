package jbar.service_core.Sell_Detail.Controller;

import jbar.service_core.Product.Model.Product;
import jbar.service_core.Product.Model.ProductRepository;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.Sell_Detail.Model.SellDetail;
import jbar.service_core.Sell_Detail.Model.SellDetailDTO;
import jbar.service_core.Sell_Detail.Model.SellDetailRepository;
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

    @Autowired
    public SellDetailService(SellDetailRepository sellDetailRepository, SellRepository sellRepository, ProductRepository productRepository) {
        this.sellDetailRepository = sellDetailRepository;
        this.sellRepository = sellRepository;
        this.productRepository = productRepository;
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
            sellDetail.setUnitPrice(product.get().getPrice()); // Obtener el precio del producto
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
}
