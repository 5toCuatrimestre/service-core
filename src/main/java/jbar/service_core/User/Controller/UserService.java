package jbar.service_core.User.Controller;

import jbar.service_core.User.Model.User;
import jbar.service_core.User.Model.UserDTO;
import jbar.service_core.User.Model.UserRepository;
import jbar.service_core.Util.Enum.Status;
import jbar.service_core.Util.Enum.Rol;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
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
        return user.map(value -> {
            log.info("User with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "User found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("User with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return new ResponseEntity<>(new Message(users, "Users found", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAllUsersByRol(Rol rol) {
        List<User> users = userRepository.findByRol(rol);
        return new ResponseEntity<>(new Message(users, "Users found by role", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAllUsersBySite(Integer siteId) {
        List<User> users = userRepository.findBySite_SiteId(siteId);
        return new ResponseEntity<>(new Message(users, "Users found by site", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> create(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(Date.valueOf(LocalDate.now()));
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
            user.setUpdatedAt(Date.valueOf(LocalDate.now()));
            userRepository.save(user);
            log.info("User with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(user, "User updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("User with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> updateUserField(Integer id, String fieldName, Object value) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            switch (fieldName.toLowerCase()) {
                case "name" -> user.setName((String) value);
                case "email" -> user.setEmail((String) value);
                case "password" -> user.setPassword((String) value);
                case "rol" -> user.setRol(Rol.valueOf((String) value));
                default -> {
                    return new ResponseEntity<>(new Message(null, "Invalid field", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
                }
            }
            user.setUpdatedAt(Date.valueOf(LocalDate.now()));
            userRepository.save(user);
            return new ResponseEntity<>(new Message(user, "User field updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getStatus() == Status.ACTIVE) {
                existingUser.setStatus(Status.INACTIVE);
                existingUser.setDeletedAt(Date.valueOf(LocalDate.now()));
            } else {
                existingUser.setStatus(Status.ACTIVE);
                existingUser.setDeletedAt(null);
            }
            userRepository.save(existingUser);
            log.info("User with id {} status changed", id);
            return new ResponseEntity<>(new Message(null, "User status changed", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("User with id {} not found for status change", id);
            return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
