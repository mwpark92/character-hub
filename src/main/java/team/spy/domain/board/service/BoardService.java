package team.spy.domain.board.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import team.spy.domain.User.dto.User;
import team.spy.domain.board.dto.BoardSummary;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.repository.BoardRepository;
import team.spy.domain.enums.BoardType;

/**
 * 일반적인 BoardService implement
 * @author kwanghoyeom
 *
 */
@Slf4j
@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository)
	{
		this.boardRepository = boardRepository;
	}
	
	public List<BoardSummary> findBoardList(Pageable pageable) {
		// TODO Auto-generated method stub
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		
		List<BoardSummary> boardList = new ArrayList<>();
		
		boardRepository.findBoardListByQuery(pageable).forEach(i -> {
			
			BoardSummary board = BoardSummary.builder()
			.boardIdx((long)i[0])
			.boardTitle((String)i[1])
			.boardType((BoardType)i[2])
			.boardCreateDate((LocalDateTime)i[3])
			.boardUpdateDate((LocalDateTime)i[4])
			.userIdx((long)i[5])
			.userNickname((String)i[6])
			.build();
			
			board.setHref("/boards/commons/" + i[0]);
			boardList.add(board);
		});
		
		
		log.debug("listSize : {}", boardList.size());
		boardList.stream().forEach(i -> log.debug("listValue : {}", i.toString() ));
		
		return boardList;
	}

	public BoardSummary findBoardByIdx(Long idx) {
		
		BoardSummary boardSummary = null;
		Object[] i = boardRepository.findBoardByBoardId(idx);
		
		boardSummary = BoardSummary.builder()
				.boardIdx((long)i[0])
				.boardTitle((String)i[1])
				.boardContent((String)i[2])
				.boardType((BoardType)i[3])
				.boardCreateDate((LocalDateTime)i[4])
				.boardUpdateDate((LocalDateTime)i[5])
				.calendarHomepage((String)i[6])
				.calendarAddress((String)i[7])
				.calendarStartDate((LocalDate)i[8])
				.calendarEndDate((LocalDate)i[9])
				.userIdx((long)i[10])
				.userNickname((String)i[11])
				.build();
		
		return boardSummary;
	}

	public void generateBoard(Board board) {
		// TODO Auto-generated method stub
		boardRepository.save(board);
	}

	public void updateBoard(Board board) {
		// TODO Auto-generated method stub
		boardRepository.save(board);
	}

	public void deleteBoard(Long idx) {
		// TODO Auto-generated method stub
		Board originBoard = boardRepository.getOne(idx);
		boardRepository.delete(originBoard);
	}
	
}
