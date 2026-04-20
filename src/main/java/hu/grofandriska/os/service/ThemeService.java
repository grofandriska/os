package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository repository;

    private Theme saveTheme (Theme theme){
        return repository.save(theme);
    }
}
