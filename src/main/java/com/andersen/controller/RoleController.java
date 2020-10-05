package com.andersen.controller;

import com.andersen.controller.exception.RoleNotFoundException;
import com.andersen.domain.Role;
import com.andersen.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable Long id) {
        return roleService.getRole(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @PutMapping
    public void updateRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.getRole(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
        roleService.deleteRole(id);
    }
}
