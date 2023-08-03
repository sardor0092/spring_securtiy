package uz.securty.securty.service.dto;

import uz.securty.securty.entity.Lavozim;
import uz.securty.securty.entity.User;

import java.util.Set;

public class UserDTO {

    private  Long id;
    private String ism;
    private String familiya;
    private  String login;
    private Set<Lavozim> lavozims;

    private Boolean activi;


    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = id;
        this.ism = ism;
        this.familiya = familiya;
        this.login = login;
        this.lavozims = lavozims;
        this.activi = activi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsm() {
        return ism;
    }

    public void setIsm(String ism) {
        this.ism = ism;
    }

    public String getFamiliya() {
        return familiya;
    }

    public void setFamiliya(String familiya) {
        this.familiya = familiya;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Lavozim> getLavozims() {
        return lavozims;
    }

    public void setLavozims(Set<Lavozim> lavozims) {
        this.lavozims = lavozims;
    }

    public Boolean getActivi() {
        return activi;
    }

    public void setActivi(Boolean activi) {
        this.activi = activi;
    }
}
