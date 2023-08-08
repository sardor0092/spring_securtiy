package uz.securty.securty.service.Ipml;

import org.hibernate.loader.internal.AliasConstantsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.securty.securty.entity.Lavozim;
import uz.securty.securty.entity.User;
import uz.securty.securty.repository.UserRepository;
import uz.securty.securty.service.dto.UserDTO;
import uz.securty.securty.service.vm.UserPasswordVM;
import uz.securty.securty.service.vm.UserService;
import java.util.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Optional<User> getByIdEntity(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAllByLavozimsContains(Lavozim.MANAGER)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(User user) {
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public Optional<UserDTO> getBuId(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }

    @Override
    public UserDTO create(User user) {
        user.setParol(encoder.encode(user.getParol()));
//        user.setLavozims(Set.of(Lavozim.MANAGER));
        return new UserDTO(userRepository.save(user));

    }

    @Override
    public User getCurrenUserEntity() {

        String username = getPricipal();
        if (username != null)
            return userRepository.findByLogin(username).orElse(null);
        return null;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private String getPricipal() {
        String userName = null;

        Object priObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (priObject instanceof UserDetails) {
            userName = ((UserDetails) priObject).getUsername();
        } else {

            userName = priObject.toString();
        }

        return userName;
    }

    @Override
    public void changePassword(UserPasswordVM userPasswordVM) {

    }

    @Override
    public UserDTO getCurrenUser() {
        String username = getPricipal();
        if (username != null)
            return userRepository.findByLogin(username).map(UserDTO::new).orElse(null);
        return null;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public String getCurrenLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
