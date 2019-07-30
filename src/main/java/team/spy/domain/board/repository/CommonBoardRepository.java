package team.spy.domain.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team.spy.domain.User.dto.User;
import team.spy.domain.board.dto.CommonBoard;

public interface CommonBoardRepository extends JpaRepository<CommonBoard, Long>{
	CommonBoard findByUser(User user);
	
	@Query("select "
//			+ "b.idx "
			+ "b.title "
			+ ",b.boardType "
			+ ",b.createDate "
			+ ",b.updateDate "
			+ ",u.nickname "
		   + "from CommonBoard b "
		   +"inner join b.user u "
		  ) 
	List<Object[]> findBoardListByQuery(Pageable pageable); 
}
