package jbar.service_core.Role.Controller;

import jbar.service_core.Role.Model.Role;
import jbar.service_core.Role.Model.RoleDTO;
import jbar.service_core.Role.Model.RoleRepository;
import jbar.service_core.Util.Response.Message;
import jbar.service_core.Util.Enum.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final Logger log = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<Message> findAll() {
        List<Role> roles = roleRepository.findAll();
        log.info("All roles retrieved successfully");
        return new ResponseEntity<>(new Message(roles, "Roles retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> findById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            log.info("Role with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(role.get(), "Role found", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Role with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Role not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> create(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        role.setStatus(roleDTO.getStatus());
        roleRepository.save(role);
        log.info("Role created successfully: {}", role);
        return new ResponseEntity<>(new Message(role, "Role created", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    public ResponseEntity<Message> update(Integer id, RoleDTO roleDTO) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setName(roleDTO.getName());
            role.setDescription(roleDTO.getDescription());
            role.setStatus(roleDTO.getStatus());
            roleRepository.save(role);
            log.info("Role with id {} updated successfully", id);
            return new ResponseEntity<>(new Message(role, "Role updated", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Role with id {} not found for update", id);
            return new ResponseEntity<>(new Message(null, "Role not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Message> delete(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.delete(role.get());
            log.info("Role with id {} deleted successfully", id);
            return new ResponseEntity<>(new Message(null, "Role deleted", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Role with id {} not found for deletion", id);
            return new ResponseEntity<>(new Message(null, "Role not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}
