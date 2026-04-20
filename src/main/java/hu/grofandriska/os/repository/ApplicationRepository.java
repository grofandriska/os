package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface ApplicationRepository extends JpaRepository<Application,String> {

    Optional<Application> findByName(String name);
    Set<Application> findByOwner(User owner);
}
