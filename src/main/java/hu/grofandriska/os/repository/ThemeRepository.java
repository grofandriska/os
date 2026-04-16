package hu.grofandriska.os.repository;

import hu.grofandriska.os.entity.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,String> {
}
