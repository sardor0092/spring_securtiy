package uz.securty.securty.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.securty.securty.entity.User;
import uz.securty.securty.repository.UserRepository;

import java.util.Optional;

@Service
public class UserProvider implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user  = userRepository.findByLogin(username);
        if(user.isPresent()){

              return new UserMaxsus(user.get());

        }
              throw new UsernameNotFoundException(username);
    }
}
