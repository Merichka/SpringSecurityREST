package ru.merichka.appspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.merichka.appspringboot.entity.Role;
import ru.merichka.appspringboot.entity.User;
import ru.merichka.appspringboot.service.AppService;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AppRestController {

    private final AppService appService;

    @Autowired
    public AppRestController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(appService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(appService.getOneUser(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> insert(@Valid @RequestBody User user, BindingResult bindingResult) {
        return ResponseEntity.ok(appService.insertUser(user, bindingResult));
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@Valid @RequestBody User user, BindingResult bindingResult) {
        return ResponseEntity.ok(appService.updateUser(user, bindingResult));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Iterable<Role>> findAllRoles() {
        return ResponseEntity.ok(appService.findAllRoles());
    }
}

