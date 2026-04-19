package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.repository.ApplicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ApplicationService {

    private ApplicationRepository repository;

    public Application saveApplication(Application application) {
        return repository.save(application);
    }

    private Application editApplication(String name) {
        Application app = repository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Application not found"));
        app.setName(name);
        return repository.save(app);
    }

    private void deleteApplication(String name) {
        Application app = repository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Application not found"));
        repository.delete(app);

    }

    private List<Application> listAllApps(User user) {
        return repository.findByOwner(user);
    }
}
