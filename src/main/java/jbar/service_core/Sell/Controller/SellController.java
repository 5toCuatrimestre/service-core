package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Sell_Detail.Controller.SellDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

@RestController
@RequestMapping("/sell")
public class SellController {

    private final SellService sellService;
    private final SellDetailService sellDetailService;

    @Autowired
    public SellController(SellService sellService, SellDetailService sellDetailService) {
        this.sellService = sellService;
        this.sellDetailService = sellDetailService;
    }

    // Obtener todas las ventas
    @GetMapping("/all")
    public ResponseEntity<Message> getAllSells() {
        return sellService.findAll();
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getSellById(@PathVariable Integer id) {
        return sellService.findById(id);
    }

    // Obtener ventas por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<Message> getSellsByUser(@PathVariable Integer userId) {
        return sellService.findByUserId(userId);
    }

    @GetMapping("/position-site/{positionSiteId}")
    public ResponseEntity<Message> getSellsByPositionSite(@PathVariable Integer positionSiteId) {
        return sellService.findByPositionSiteId(positionSiteId);
    }

    // Obtener ventas por rango de fechas
    @GetMapping("/by-date")
    public ResponseEntity<Message> getSellsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            // Convertir String a Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Esto hace que el parsing sea estricto

            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);

            Timestamp startTimestamp = new Timestamp(parsedStartDate.getTime());

            // Ajustar endTimestamp al final del día
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedEndDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            Timestamp endTimestamp = new Timestamp(cal.getTimeInMillis());

            return sellService.findByDateRange(startTimestamp, endTimestamp);
        } catch (ParseException e) {
            return new ResponseEntity<>(
                    new Message(null, "Invalid date format. Use yyyy-MM-dd", TypesResponse.ERROR),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Crear una venta
    @PostMapping
    public ResponseEntity<Message> createSell(@RequestBody SellDTO sellDTO) {
        return sellService.create(sellDTO);
    }

    // Actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateSell(@PathVariable Integer id, @RequestBody SellDTO sellDTO) {
        return sellService.update(id, sellDTO);
    }

    // Cambiar el estado de una venta (Soft Delete)
    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeSellStatus(@PathVariable Integer id) {
        return sellService.changeStatus(id);
    }

    @GetMapping("/chart-data")
    public ResponseEntity<Message> getSalesChartData(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            // Convertir String a Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);

            Timestamp startTimestamp = new Timestamp(parsedStartDate.getTime());

            // Ajustar endTimestamp al final del día
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedEndDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            Timestamp endTimestamp = new Timestamp(cal.getTimeInMillis());

            return sellService.getSalesChartData(startTimestamp, endTimestamp);
        } catch (ParseException e) {
            return new ResponseEntity<>(
                    new Message(null, "Invalid date format. Use yyyy-MM-dd", TypesResponse.ERROR),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/average-sales-by-hour")
    public ResponseEntity<Message> getAverageSalesByHour(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);

            Timestamp startTimestamp = new Timestamp(parsedStartDate.getTime());

            // Ajustamos el endTimestamp al final del día
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedEndDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            Timestamp endTimestamp = new Timestamp(cal.getTimeInMillis());

            return sellService.getAverageSalesByHour(startTimestamp, endTimestamp);

        } catch (ParseException e) {
            return new ResponseEntity<>(
                    new Message(null, "Invalid date format. Use yyyy-MM-dd", TypesResponse.ERROR),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/top-selling-products")
    public ResponseEntity<Message> getTopSellingProducts(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            Date parsedStartDate = dateFormat.parse(startDate);
            Date parsedEndDate = dateFormat.parse(endDate);

            Timestamp startTimestamp = new Timestamp(parsedStartDate.getTime());

            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedEndDate);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            Timestamp endTimestamp = new Timestamp(cal.getTimeInMillis());

            return sellDetailService.getTopSellingProducts(startTimestamp, endTimestamp);

        } catch (ParseException e) {
            return new ResponseEntity<>(
                    new Message(null, "Invalid date format. Use yyyy-MM-dd", TypesResponse.ERROR),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ticket/{sellId}")
    public ResponseEntity<Message> generateTicket(@PathVariable Integer sellId) {
        return sellService.generateTicket(sellId);
    }

}
