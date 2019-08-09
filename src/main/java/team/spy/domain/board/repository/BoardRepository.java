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
			+ " new team.spy.domain.board.dto.BoardSummary(b.idx, b.title, b.boardType, b.createDate, b.updateDate,"
			+ " u.idx, u.nickname) "
		   + "from Board b "
		   + "inner join User u on b.user = u.idx"
		  )
	List<BoardSummary> findBoardListByQuery(Pageable pageable);
	
	@Query("select "
			+ " new team.spy.domain.board.dto.BoardSummary(b.idx, b.title, b.content, b.boardType, b.createDate, b.updateDate, "
			+ " b.calendar.homepage, b.calendar.address, b.calendar.startDate, b.calendar.endDate, u.idx, u.nickname) "
			+ "from Board b "
			+ "inner join User u on b.user = u.idx "
			+ "where b.idx = :idx")
	BoardSummary findBoardByBoardId(@Param("idx") Long idx);
}


