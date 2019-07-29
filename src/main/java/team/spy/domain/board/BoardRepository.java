package team.spy.domain.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.spy.domain.User.dto.User;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Board findByUser(User user);
	
	@Query("select "
//			+ "b.idx "
			+ "b.title "
			+ ",b.boardType "
			+ ",b.createDate "
			+ ",b.updateDate "
			+ ",u.nickname "
		   + "from Board b "
		   +"inner join b.user u "
		  )//+ "where b.idx = :idx")
//	List<Board> findBoardListByQuery(@Param("idx") Long idx); 
	List<Object> findBoardListByQuery(); 
}
