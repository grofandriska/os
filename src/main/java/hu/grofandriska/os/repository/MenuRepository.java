package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,String> {

    Optional<Menu> findByUserId (String id);
}
