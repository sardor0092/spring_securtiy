package uz.securty.securty.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.securty.securty.entity.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMaxsus implements UserDetails {

    private String login;
    private String parol;
    private Set<SimpleGrantedAuthority> lavozimlar;
    private Boolean aktiv;

    public UserMaxsus(){}


    public UserMaxsus(User user){
        this.login = user.getLogin();
        this.parol = user.getParol();

        this.lavozimlar =
                user.getLavozims()
                        .stream()
                        .map(l->new SimpleGrantedAuthority(l.name()))
                        .collect(Collectors.toSet());
        this.aktiv = user.getActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return lavozimlar;
    }

    @Override
    public String getPassword() {
        return parol;
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
        return aktiv;
    }




    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }

    public Set<SimpleGrantedAuthority> getLavozimlar() {
        return lavozimlar;
    }

    public void setLavozimlar(Set<SimpleGrantedAuthority> lavozimlar) {
        this.lavozimlar = lavozimlar;
    }

    public Boolean getAktiv() {
        return aktiv;
    }

    public void setAktiv(Boolean aktiv) {
        this.aktiv = aktiv;
    }
}
