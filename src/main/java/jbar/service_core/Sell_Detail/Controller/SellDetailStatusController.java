package jbar.service_core.Sell_Detail.Controller;

import jbar.service_core.Sell_Detail.Model.SellDetailStatusDTO;
import jbar.service_core.Sell_Detail.Model.SellDetailStatusUpdateDTO;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sell-detail-status")
public class SellDetailStatusController {
    private final SellDetailStatusService sellDetailStatusService;

    @Autowired
    public SellDetailStatusController(SellDetailStatusService sellDetailStatusService) {
        this.sellDetailStatusService = sellDetailStatusService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllStatuses() {
        return sellDetailStatusService.findAll();
    }

    @GetMapping("/pending")
    public ResponseEntity<Message> getPendingStatuses() {
        return sellDetailStatusService.findPending();
    }

    @PostMapping
    public ResponseEntity<Message> createStatus(@RequestBody SellDetailStatusDTO statusDTO) {
        return sellDetailStatusService.create(statusDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateStatus(@PathVariable Integer id, @RequestBody SellDetailStatusUpdateDTO statusDTO) {
        return sellDetailStatusService.update(id, statusDTO);
    }

    @GetMapping("/ById/{id}")
    public ResponseEntity<Message> getStatusById(@PathVariable Integer id) {
        return sellDetailStatusService.findById(id);
    }
} 