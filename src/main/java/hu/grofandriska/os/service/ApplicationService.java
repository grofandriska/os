package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.app.Application;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ApplicationService {

    private ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public Application saveApplication(Application application) {
        return repository.save(application);
    }

    public Application editApplication(String name) {
        Application app = repository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Application not found"));
        app.setName(name);
        return repository.save(app);
    }

    public void deleteApplication(String name) {
        Application app = repository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Application not found"));
        repository.delete(app);

    }

    public Set<Application> listAllApps(User user) {
        return repository.findByOwner(user);
    }
}
