package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SellService {
    private final Logger log = LoggerFactory.getLogger(SellService.class);
    private final SellRepository sellRepository;

    @Autowired
    public SellService(SellRepository sellRepository) {
        this.sellRepository = sellRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByWaiterId(Integer waiterId) {
        List<Sell> sells = sellRepository.findByWaiter_WaiterId(waiterId);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found for waiter", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sell> sells = sellRepository.findBySellDateBetween(startDate, endDate);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found in date range", TypesResponse.ERROR), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
