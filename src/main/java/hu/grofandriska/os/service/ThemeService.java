package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository repository;

    public Theme saveTheme(Theme theme) {
        return repository.save(theme);
    }

    public List<Theme> listThemes() {
        return repository.findAll();
    }
}
