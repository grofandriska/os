package hu.grofandriska.os.service;


import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import hu.grofandriska.os.repository.UserGroupRepository;
import hu.grofandriska.os.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    private final UserGroupRepository userGroupRepository;

    public UserService(UserRepository repository, UserGroupRepository userGroupRepository) {
        this.repository = repository;
        this.userGroupRepository = userGroupRepository;
    }

    public User saveUser(User user) {
        return repository.save(user);
    }


    public User findUserByName(String fullName) {
        User user = repository.findByFullName(fullName).orElse(null);
        if (user != null) {
            Hibernate.initialize(user.getInstalledApplications());
        }
        return user;
    }

    public List<User> findUsersByGroupId(String groupId) {

        UserGroup userGroup = userGroupRepository.findById(groupId).orElse(null);
        if (userGroup != null) {
            return repository.findByGroup(userGroup);
        }
        return Collections.emptyList();
    }

    public void deleteUser(String fullName) {
        repository.findByFullName(fullName).ifPresent(user -> repository.deleteById(user.getId()));

    }

    @Transactional
    public User modifyUser(String fullName, User user) {
        User oldUser = repository.findByFullName(fullName).orElse(null);
        if (oldUser != null){
            oldUser.setRole(user.getRole());
            oldUser.setUpdatedAt(LocalDate.now());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setFullName(oldUser.getFirstName() + " " + oldUser.getLastName());
            // HIBA JAVÍTÁSA:
            // 1. Ürítsd ki a meglévő listát
            oldUser.getInstalledApplications().clear();

            // 2. Add hozzá az újakat a meglévő listához (nem a listát cseréljük!)
            if (user.getInstalledApplications() != null) {
                oldUser.getInstalledApplications().addAll(user.getInstalledApplications());
            }
            oldUser.setUpdatedAt(LocalDate.now());
            return repository.save(oldUser);
        }
        return null;
    }

}
