package jbar.service_core.Sell.Controller;

import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sell")
public class SellController {

    private final SellService sellService;

    @Autowired
    public SellController(SellService sellService) {
        this.sellService = sellService;
    }

    @GetMapping("/by-waiter/{waiterId}")
    public ResponseEntity<Message> getSellsByWaiter(@PathVariable Integer waiterId) {
        return sellService.findByWaiterId(waiterId);
    }

    @GetMapping("/by-date")
    public ResponseEntity<Message> getSellsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
    ) {
        return sellService.findByDateRange(startDate, endDate);
    }
}
