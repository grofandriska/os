package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,String> {

    Optional<Application> findByName(String name);
    List<Application> findByOwner(User owner);
}
