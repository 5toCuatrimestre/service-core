package jbar.service_core.Waiter.Controller;

import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Waiter.Model.Waiter;
import jbar.service_core.Waiter.Model.WaiterDTO;
import jbar.service_core.Waiter.Model.WaiterRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WaiterService {
    @Autowired
    private WaiterRepository waiterRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Message> findAll() {
        List<Waiter> waiters = waiterRepository.findAll();
        return new ResponseEntity<>(new Message(waiters, "Waiters retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Waiter> waiter = waiterRepository.findById(id);
        return waiter.map(value -> new ResponseEntity<>(new Message(value, "Waiter found", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Message(null, "Waiter not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Message> create(WaiterDTO waiterDTO) {
        Optional<User> user = userRepository.findById(waiterDTO.getUserId());
        if (user.isEmpty()) {
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Waiter waiter = new Waiter();
        waiter.setUser(user.get());
        waiter.setHireDate(waiterDTO.getHireDate());
        waiter.setStatus(waiterDTO.getStatus());

        waiterRepository.save(waiter);
        return new ResponseEntity<>(new Message(waiter, "Waiter created successfully", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Waiter> waiter = waiterRepository.findById(id);
        if (waiter.isPresent()) {
            waiterRepository.delete(waiter.get());
            return new ResponseEntity<>(new Message(null, "Waiter deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(null, "Waiter not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
