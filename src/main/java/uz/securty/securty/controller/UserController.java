package uz.securty.securty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.securty.securty.entity.User;
import uz.securty.securty.service.dto.UserDTO;
import uz.securty.securty.service.vm.UserService;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*" ,maxAge = 3600)
@PreAuthorize("hasAnyAuthority(\"ADMIN\",\"MANAGER\")")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDTO> getall(){
        return userService.getAll();
    }

    @PostMapping
    public UserDTO create(@RequestBody User user){
        return userService.create(user);

    }


    @PutMapping
    public UserDTO update(@RequestBody User user){
        return userService.update(user);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.deleteById(id);
    }





}
