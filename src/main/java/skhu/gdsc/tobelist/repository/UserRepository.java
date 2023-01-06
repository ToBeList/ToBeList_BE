package skhu.gdsc.tobelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.gdsc.tobelist.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
