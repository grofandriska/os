package hu.grofandriska.os.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Wallpaper extends JpaRepository<Wallpaper,String> {
}
