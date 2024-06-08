package baranow.laba2.telebot.repository;

import baranow.laba2.telebot.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
}
