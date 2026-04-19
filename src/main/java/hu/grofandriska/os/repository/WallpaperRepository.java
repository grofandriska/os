package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.theme.Wallpaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WallpaperRepository extends JpaRepository<Wallpaper,String> {

    Optional<Wallpaper> findByName(String name);
}
