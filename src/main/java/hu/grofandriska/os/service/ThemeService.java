package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.repository.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private ThemeRepository repository;

    private Theme saveTheme (Theme theme){
        return repository.save(theme);
    }
}
