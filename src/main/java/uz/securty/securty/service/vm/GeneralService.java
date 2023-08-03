package uz.securty.securty.service.vm;

import java.util.List;
import java.util.Optional;

public interface GeneralService<Entity,IdType> {

    List<Entity> getAll();

    Optional<Entity> getById(IdType id);
    Entity  create(Entity entity);
    Entity update(Entity entity);

    Entity delete(Entity entity);
    void deleteById(IdType id);


}
