package hu.grofandriska.os.service;


import hu.grofandriska.os.entity.user.User;
import hu.grofandriska.os.entity.user.UserGroup;
import hu.grofandriska.os.repository.UserGroupRepository;
import hu.grofandriska.os.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
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
        return repository.findByFullName(fullName).orElse(null);
    }

    public List<User> findUsersByGroupId(String groupId) {

        UserGroup userGroup = userGroupRepository.findById(groupId).orElse(null);
        if (userGroup != null) {
            return repository.findByGroup(userGroup);
        }
        return null;
    }

    public void deleteUser(String fullName) {
        repository.findByFullName(fullName).ifPresent(user -> repository.deleteById(user.getId()));

    }

    public User modifyUser(String fullName, User user) {
        User oldUser = repository.findByFullName(fullName).orElse(null);
        if (oldUser != null){
            oldUser.setRole(user.getRole());
            oldUser.setUpdatedAt(LocalDate.now());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setFullName(oldUser.getFirstName() + " " + oldUser.getLastName());
            return repository.save(oldUser);
        }
        return null;
    }

}
