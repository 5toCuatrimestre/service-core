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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
    public ResponseEntity<Message> getAllUsersByRol(Rol rol) {
        List<User> users = userRepository.findByRol(rol);
        return new ResponseEntity<>(new Message(users, "Users found by role", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    //Recuerda poner el tipo de Transactional
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRol(userDTO.getRol());
        user.setStatus(Status.ACTIVE);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        //Siempre se recibe la fecha desde frontend :)
        user.setCreatedAt(userDTO.getCreatedAt());

        userRepository.save(user);

        log.info("User created successfully: {}", user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(user, "User created", TypesResponse.SUCCESS));
    }

    //SIEMPRE EL ID EN PATHVARIABLE
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, UserDTO userDTO) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User user = existingUserOptional.get();
            // Asignamos todos los valores desde el DTO al usuario existente
            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setStatus(userDTO.getStatus());
            user.setRol(userDTO.getRol());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setUpdatedAt(Date.valueOf(LocalDate.now()));

            userRepository.saveAndFlush(user); // Guarda y refresca en la BD

            log.info("User with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(user, "User updated", TypesResponse.SUCCESS));
        }

        log.warn("User with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }
    //SIEMPRE EL ID EN PATHVARIABLE
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getStatus() == Status.ACTIVE) {
                existingUser.setStatus(Status.INACTIVE);
                existingUser.setDeletedAt(Date.valueOf(LocalDate.now()));
            } else {
                existingUser.setStatus(Status.ACTIVE);
                //Hace falta que actualice la ultima fecha de updated_at
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
