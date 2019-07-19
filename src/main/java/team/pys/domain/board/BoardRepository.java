package team.pys.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import team.pys.domain.User.User;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Board findByUser(User user);

}
