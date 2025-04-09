package jbar.service_core.Sell.Controller;

import jbar.service_core.Sell.Model.SalesChartDTO;
import jbar.service_core.Sell.Model.SalesTimeChartDTO;
import jbar.service_core.Sell.Model.Sell;
import jbar.service_core.Sell.Model.SellDTO;
import jbar.service_core.Sell.Model.SellRepository;
import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp; // Usamos Timestamp para manejar fechas con hora
import java.sql.Time; // Usamos Time para manejar solo la hora
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SellService {
    private final Logger log = LoggerFactory.getLogger(SellService.class);
    private final SellRepository sellRepository;
    private final UserRepository userRepository;

    @Autowired
    public SellService(SellRepository sellRepository, UserRepository userRepository) {
        this.sellRepository = sellRepository;
        this.userRepository = userRepository;
    }

    // Obtener todas las ventas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Sell> sells = sellRepository.findAll();
        return new ResponseEntity<>(new Message(sells, "All sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener venta por ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        return sell.map(
                value -> new ResponseEntity<>(new Message(value, "Sell found", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR),
                        HttpStatus.NOT_FOUND));
    }

    // Obtener ventas por usuario
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByUserId(Integer userId) {
        List<Sell> sells = sellRepository.findByUserUserIdAndDeletedAtIsNull(userId);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found for user", TypesResponse.ERROR),
                        HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByPositionSiteId(Integer positionSiteId) {
        List<Sell> sells = sellRepository.findByPositionSiteIdAndDeletedAtIsNull(positionSiteId);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found for position site", TypesResponse.ERROR),
                        HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener ventas en un rango de fechas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByDateRange(Timestamp startDate, Timestamp endDate) {
        List<Sell> sells = sellRepository.findBySellDateBetween(startDate, endDate);
        return sells.isEmpty()
                ? new ResponseEntity<>(new Message(null, "No sells found in date range", TypesResponse.ERROR),
                        HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new Message(sells, "Sells retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Crear una venta
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(SellDTO sellDTO) {
        try {
            Optional<User> user = userRepository.findById(sellDTO.getUserId());
            if (user.isEmpty()) {
                return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR),
                        HttpStatus.NOT_FOUND);
            }

            Sell sell = new Sell();
            sell.setUser(user.get());
            sell.setTotalPrice(0.0);
            sell.setPositionSiteId(sellDTO.getPosition_site_id());
            sell.setSellDate(sellDTO.getSellDate()); // Timestamp
            sell.setSellTime(sellDTO.getSellTime()); // Time
            sell.setStatus(sellDTO.getStatus());

            sellRepository.save(sell);
            log.info("Sell created successfully: {}", sell);
            return new ResponseEntity<>(new Message(sell, "Sell created", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating sell: {}", e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error creating sell", TypesResponse.ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una venta
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, SellDTO sellDTO) {
        try {
            Optional<Sell> existingSell = sellRepository.findById(id);
            if (existingSell.isEmpty()) {
                return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR),
                        HttpStatus.NOT_FOUND);
            }

            Sell sell = existingSell.get();
            sell.setTotalPrice(sellDTO.getTotalPrice());
            sell.setSellDate(sellDTO.getSellDate()); // Timestamp
            sell.setSellTime(sellDTO.getSellTime()); // Time
            sell.setStatus(sellDTO.getStatus());

            sellRepository.save(sell);
            log.info("Sell with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(sell, "Sell updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating sell with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(new Message(null, "Error updating sell", TypesResponse.ERROR),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cambiar el estado de una venta (Soft Delete)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<Sell> sell = sellRepository.findById(id);
        if (sell.isPresent()) {
            Sell existingSell = sell.get();
            existingSell.setStatus(false);
            existingSell.setDeletedAt(new Timestamp(System.currentTimeMillis()));                                 

            sellRepository.save(existingSell);
            log.info("Sell with id {} status changed", id);
            return new ResponseEntity<>(new Message(null, "Sell status changed to false", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(null, "Sell not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getSalesChartData(Timestamp startDate, Timestamp endDate) {
        List<Sell> sells = sellRepository.findBySellDateBetween(startDate, endDate);

        if (sells.isEmpty()) {
            return new ResponseEntity<>(new Message(null, "No sells found in date range", TypesResponse.ERROR),
                    HttpStatus.NOT_FOUND);
        }

        // Agrupar ventas por mes y sumar los totales
        Map<String, Double> salesByMonth = new LinkedHashMap<>();

        // Definir nombres de meses en español
        String[] monthNames = { "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic" };

        // Inicializar el mapa con todos los meses en el rango (para asegurar que todos
        // aparezcan incluso si no tienen ventas)
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(startDate.getTime());

        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endDate.getTime());

        Calendar currentCal = (Calendar) startCal.clone();
        while (!currentCal.after(endCal)) {
            int month = currentCal.get(Calendar.MONTH);
            int year = currentCal.get(Calendar.YEAR);
            String key = monthNames[month] + " " + year;
            salesByMonth.put(key, 0.0);

            currentCal.add(Calendar.MONTH, 1);
        }

        // Sumar ventas por mes
        for (Sell sell : sells) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sell.getSellDate().getTime());

            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            String key = monthNames[month] + " " + year;

            // Sumar al total existente
            double currentTotal = salesByMonth.getOrDefault(key, 0.0);
            salesByMonth.put(key, currentTotal + sell.getTotalPrice());
        }

        // Convertir el mapa a la lista de DTOs
        List<SalesChartDTO> chartData = salesByMonth.entrySet().stream()
                .map(entry -> new SalesChartDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new Message(chartData, "Sales chart data retrieved", TypesResponse.SUCCESS),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAverageSalesByHour(Timestamp startDate, Timestamp endDate) {
        List<Sell> sells = sellRepository.findBySellDateBetween(startDate, endDate);

        if (sells.isEmpty()) {
            return new ResponseEntity<>(new Message(null, "No sells found in date range", TypesResponse.ERROR),
                    HttpStatus.NOT_FOUND);
        }

        // Mapa: Hora -> Lista de precios
        Map<String, List<Double>> salesByHour = new LinkedHashMap<>();

        // Inicializamos todas las horas de 08:00 a 22:00 con listas vacías (para
        // asegurar consistencia)
        for (int hour = 8; hour <= 22; hour++) {
            String hourKey = String.format("%02d:00", hour);
            salesByHour.put(hourKey, new ArrayList<>());
        }

        // Llenar el mapa con las ventas correspondientes
        for (Sell sell : sells) {
            int hour = sell.getSellTime().toLocalTime().getHour();

            if (hour >= 8 && hour <= 22) { // Solo consideramos de 08:00 a 22:00 como en tu ejemplo
                String hourKey = String.format("%02d:00", hour);
                salesByHour.get(hourKey).add(sell.getTotalPrice());
            }
        }

        // Convertimos a DTOs con el promedio de ventas por hora
        List<SalesTimeChartDTO> chartData = salesByHour.entrySet().stream()
                .map(entry -> {
                    List<Double> ventas = entry.getValue();
                    double promedio = ventas.isEmpty() ? 0.0
                            : ventas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                    return new SalesTimeChartDTO(entry.getKey(), promedio);
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(new Message(chartData, "Average sales per hour retrieved", TypesResponse.SUCCESS),
                HttpStatus.OK);
    }

}
