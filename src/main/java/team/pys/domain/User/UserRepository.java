package team.pys.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import team.pys.domain.User.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
