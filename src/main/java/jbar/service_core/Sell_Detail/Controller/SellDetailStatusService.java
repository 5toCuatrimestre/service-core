package jbar.service_core.Sell_Detail.Controller;

import jbar.service_core.Sell_Detail.Model.SellDetailStatus;
import jbar.service_core.Sell_Detail.Model.SellDetailStatusDTO;
import jbar.service_core.Sell_Detail.Model.SellDetailStatusUpdateDTO;
import jbar.service_core.Sell_Detail.Model.SellDetailStatusRepository;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SellDetailStatusService {
    private final Logger log = LoggerFactory.getLogger(SellDetailStatusService.class);
    private final SellDetailStatusRepository sellDetailStatusRepository;

    @Autowired
    public SellDetailStatusService(SellDetailStatusRepository sellDetailStatusRepository) {
        this.sellDetailStatusRepository = sellDetailStatusRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<SellDetailStatus> statuses = sellDetailStatusRepository.findAll();
        return new ResponseEntity<>(new Message(statuses, "All sell detail statuses retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findPending() {
        List<SellDetailStatus> pendingStatuses = sellDetailStatusRepository.findByStatusAndDeletedAtIsNull(SellDetailStatus.Status.PENDING);
        return new ResponseEntity<>(new Message(pendingStatuses, "Pending sell detail statuses retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(SellDetailStatusDTO statusDTO) {
        try {
            SellDetailStatus status = new SellDetailStatus();
            status.setSellDetailId(statusDTO.getSellDetailId());
            status.setPositionSiteId(statusDTO.getPositionSiteId());
            status.setName(statusDTO.getName());
            status.setNameWaiter(statusDTO.getNameWaiter());
            status.setStatus(statusDTO.getStatus());
            status.setQuantity(statusDTO.getQuantity());

            SellDetailStatus savedStatus = sellDetailStatusRepository.save(status);
            log.info("Sell detail status created successfully: {}", savedStatus);
            return new ResponseEntity<>(new Message(savedStatus, "Sell detail status created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating sell detail status: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating sell detail status: " + e.getMessage(), TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, SellDetailStatusUpdateDTO statusDTO) {
        try {
            Optional<SellDetailStatus> existingStatus = sellDetailStatusRepository.findById(id);
            if (existingStatus.isEmpty()) {
                log.warn("Sell detail status with id {} not found", id);
                return new ResponseEntity<>(new Message(null, "Sell detail status not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }

            if (statusDTO.getStatus() != SellDetailStatus.Status.ACCEPTED && 
                statusDTO.getStatus() != SellDetailStatus.Status.REJECTED) {
                log.warn("Invalid status update attempt. Only ACCEPTED or REJECTED are allowed");
                return new ResponseEntity<>(new Message(null, "Only ACCEPTED or REJECTED status are allowed", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
            }

            SellDetailStatus status = existingStatus.get();
            status.setStatus(statusDTO.getStatus());

            SellDetailStatus updatedStatus = sellDetailStatusRepository.save(status);
            log.info("Sell detail status updated successfully: {}", updatedStatus);
            return new ResponseEntity<>(new Message(updatedStatus, "Sell detail status updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating sell detail status: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error updating sell detail status: " + e.getMessage(), TypesResponse.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<SellDetailStatus> status = sellDetailStatusRepository.findById(id);
        if (status.isEmpty()) {
            log.warn("Sell detail status with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Sell detail status not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(status.get(), "Sell detail status found", TypesResponse.SUCCESS), HttpStatus.OK);
    }
} 