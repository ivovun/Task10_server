package com.websystique.springmvc.controller;

import com.websystique.springmvc.exceptions.NonUniqueSsoIdException;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*")
public class AppRestController {
    UserService userService;

    UserProfileService userProfileService;

    MessageSource messageSource;

    @Autowired
    public AppRestController(UserService userService, UserProfileService userProfileService,
                             MessageSource messageSource) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.messageSource = messageSource;
    }



    @GetMapping("/api/list")
    public ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        return new ResponseEntity(userService.findById(Long.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping("/api/users/byssoid/{ssoId}")
    public ResponseEntity<User> findBySsoId(@PathVariable String ssoId) {
        return new ResponseEntity(userService.findBySsoId(ssoId), HttpStatus.OK);
    }

    @GetMapping("/api/edit")
    public ResponseEntity<User> edit(@RequestParam("id") String id, ModelMap model) {
        return new ResponseEntity(userService.findById(Long.parseLong(id)), HttpStatus.OK);
    }



    @PostMapping("/api/save")
    public ResponseEntity<Void> save(@RequestBody User user) {
        if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
            throw new NonUniqueSsoIdException(messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
        }
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/delete/{ssoId}")
    public ResponseEntity<Void> delete(@PathVariable String ssoId) {
        userService.deleteUserBySsoId(ssoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
