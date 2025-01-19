package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sell")
public class SellController {

    private final SellService sellService;

    @Autowired
    public SellController(SellService sellService) {
        this.sellService = sellService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllSells() {
        return sellService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getSellById(@PathVariable Integer id) {
        return sellService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> createSell(@RequestBody SellDTO sellDTO) {
        return sellService.create(sellDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateSell(@PathVariable Integer id, @RequestBody SellDTO sellDTO) {
        return sellService.update(id, sellDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteSell(@PathVariable Integer id) {
        return sellService.delete(id);
    }
}
