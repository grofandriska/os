package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.user.Role;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import org.hibernate.validator.internal.engine.groups.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByFirstName (String firstName);
    Optional<User> findByFullName (String FullName);
    List<User> findByGroup (UserGroup group);
    Optional<User> findByRole (Role rolaName);
}
