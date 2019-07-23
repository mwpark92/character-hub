package team.spy.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import team.spy.domain.User.User;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Board findByUser(User user);

}
