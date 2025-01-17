package jbar.service_core.User.Controller;

import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserDTO;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Enum.Status;
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
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<User> users = userRepository.findAll();
        log.info("All users retrieved successfully");
        return new ResponseEntity<>(new Message(users, "Users retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("User with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(user.get(), "User found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("User with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> create(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
        log.info("User created successfully: {}", user);
        return new ResponseEntity<>(new Message(user, "User created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> update(Integer id, UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
            log.info("User with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(user, "User updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("User with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setStatus(Status.INACTIVE); // Cambia el estado del usuario a inactivo
            userRepository.save(existingUser);
            log.info("User with id {} status changed to inactive", id);
            return new ResponseEntity<>(new Message(null, "User status changed to inactive", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("User with id {} not found for status change", id);
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
