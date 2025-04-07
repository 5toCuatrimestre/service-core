package jbar.service_core.Sell_Detail.Controller;

import jbar.service_core.Sell_Detail.Model.SellDetailDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sell-details")
public class SellDetailController {

    private final SellDetailService sellDetailService;

    @Autowired
    public SellDetailController(SellDetailService sellDetailService) {
        this.sellDetailService = sellDetailService;
    }

    @PostMapping
    public ResponseEntity<Message> createSellDetail(@RequestBody SellDetailDTO sellDetailDTO) {
        return sellDetailService.create(sellDetailDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateSellDetail(@PathVariable Integer id, @RequestBody SellDetailDTO sellDetailDTO) {
        return sellDetailService.update(id, sellDetailDTO);
    }
}
