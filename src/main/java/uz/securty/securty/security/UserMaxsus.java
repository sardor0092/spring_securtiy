package uz.securty.securty.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.securty.securty.entity.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMaxsus implements UserDetails {

    private  String login;
    private String password;

    private Set<SimpleGrantedAuthority> lavozimlar;
    private  Boolean active;

    public UserMaxsus() {
    }


    public UserMaxsus(User user){


        this.login = user.getLogin();
        this.password = user.getPassword();

        this.lavozimlar = user.getLavozims()
                .stream()
                .map(l->new SimpleGrantedAuthority(l.name()))
                .collect(Collectors.toSet());

        this.active =  user.getActive();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return lavozimlar;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
