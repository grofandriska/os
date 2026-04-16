package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.app.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,String> {
}
