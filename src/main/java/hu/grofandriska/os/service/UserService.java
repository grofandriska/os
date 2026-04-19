package hu.grofandriska.os.service;

import hu.grofandriska.os.entity.theme.Theme;
import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.company.CompanyMember;
import hu.grofandriska.os.entity.user.family.FamilyMember;
import hu.grofandriska.os.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class UserService {

    private UserRepository repository;

    private User saveUser(User user) {
        return repository.save(user);
    }

    private void deleteUser(String fullName) {
        User user = repository.findByFullName(fullName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        repository.deleteById(user.getId());
    }

    private User modifyUser (String fullName, User user){
        User oldUser = repository.findByFullName(fullName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        oldUser.setRole(user.getRole());
        oldUser.setUpdatedAt(LocalDate.now());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setFullName(oldUser.getFirstName() + " " +oldUser.getLastName());

        return repository.save(oldUser);
    }

}
