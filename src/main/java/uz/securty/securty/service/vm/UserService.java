package uz.securty.securty.service.vm;

import uz.securty.securty.entity.User;
import uz.securty.securty.service.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {


    public Optional<User> getByIdEntity(Long id);

    public List<UserDTO> getAll();

    UserDTO update(User user);

    public Optional<UserDTO> getBuId(Long id);

    UserDTO create(User user);

    User getCurrenUserEntity();

    public void deleteById(Long id);
    public void changePassword(UserPasswordVM userPasswordVM);

    UserDTO getCurrenUser();


    Optional<User> getByLogin(String login);

       public String getCurrenLogin();





}
