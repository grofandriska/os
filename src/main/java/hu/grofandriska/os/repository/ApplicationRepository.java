package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.app.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,String> {

    Optional<Application> findByName(String name);
}
