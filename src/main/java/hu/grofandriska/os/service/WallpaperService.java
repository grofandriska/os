package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.theme.Wallpaper;
import hu.grofandriska.os.repository.WallpaperRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WallpaperService {

    private WallpaperRepository repository;

    private Wallpaper addWallpaper (Wallpaper wallpaper){
        return repository.save(wallpaper);
    }

    private Wallpaper findWallpaper(String name){
        return repository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Wallpaper not found"));
    }
}
