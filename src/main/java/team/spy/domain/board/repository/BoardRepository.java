package team.spy.domain.board.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.spy.domain.board.dto.BoardSummary;
import team.spy.domain.board.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("select "
			+ "b.idx "
			+ ",b.title "
			+ ",b.boardType "
			+ ",b.createDate "
			+ ",b.updateDate "
			+ ",u.idx "
			+ ",u.nickname "
		   + "from Board b "
		   + "inner join User u on b.user = u.idx"
		  )
// (@Param("idx") Long idx
	List<Object[]> findBoardListByQuery(Pageable pageable);
	
	@Query("select "
			+ "b.idx "
			+ ", b.title "
			+ ", b.boardType "
			+ ", b.content "
			+ ", b.createDate "
			+ ", b.updateDate "
			+ ", c.homepage "
			+ ", c.address "
			+ ", c.startDate "
			+ ", c.endDate "
			+ ", u.idx "
			+ ", u.nickname"
			+ "from Board b "
			+ "inner join User u on b.user = u.idx "
			+ "inner join Calendar c on b.calendar = c.calendar_id"
			+ "where b.idx = :idx")
	Object[] findBoardByBoardId(@Param("idx") Long idx);
}
