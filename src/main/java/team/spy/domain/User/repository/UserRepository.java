package team.spy.domain.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.spy.domain.User.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
