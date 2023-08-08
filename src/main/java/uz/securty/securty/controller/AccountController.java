package uz.securty.securty.controller;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.securty.securty.entity.User;
import uz.securty.securty.repository.UserRepository;
import uz.securty.securty.security.JwtTokenUtil;
import uz.securty.securty.security.Token;
import uz.securty.securty.security.UserMaxsus;
import uz.securty.securty.security.UserProvider;
import uz.securty.securty.service.dto.UserDTO;
import uz.securty.securty.service.vm.UserPasswordVM;
import uz.securty.securty.service.vm.UserService;

@RestController
@RequestMapping("/api/account")
public class AccountController {


    @Autowired
    UserProvider userProvider;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;



    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/authenticate")
    public ResponseEntity<Token> login(@RequestBody UserMaxsus userMaxsus) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(

                    userMaxsus.getUsername() ,userMaxsus.getPassword()));

        }  catch(DisabledException e) {
            throw  new Exception("USER_DISABLED" ,e);

        } catch (BadCredentialsException e) {
             throw new Exception("INVALID_CREDENTIALS" ,e);


        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        UserDetails userDetails = userProvider.loadUserByUsername(userMaxsus.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails ,true);
        return ResponseEntity.ok(new Token(token));

    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User userDTO){
        if(userDTO.getId() !=null)
            return  ResponseEntity.badRequest().build();
        return ResponseEntity.ok(userService.create(userDTO));
    }




    @GetMapping()
    public ResponseEntity<UserDTO> getCurrentUser(){
        return  ResponseEntity.ok(userService.getCurrenUser());
    }


    @PutMapping
    public UserDTO update (@RequestBody UserDTO userDTO) {
        User current  = userService.getCurrenUserEntity();
        current.setIsm(userDTO.getIsm());
        current.setFamiliya(userDTO.getFamiliya());

        return userService.update(current);
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UserPasswordVM parol){
        User current= userService.getCurrenUserEntity();
        if(current.getParol().equals(encoder.encode(parol.getOldPassword()))) {
            current.setParol(encoder.encode(parol.getNewPassword()));
            return ResponseEntity.ok(userService.update(current));

        }
        return ResponseEntity.badRequest().build();

    }











}
