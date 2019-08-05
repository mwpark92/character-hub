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
 * Meta규칙에 따라서 Entity 기본 CRUD와 Cumstom Query는 Method명의 Meta가 다
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
	
	/**
	 * List Page에 출력될 목록 
	 * @param pageable
	 * @return
	 */
	public List<BoardSummary> findBoardList(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		
		List<BoardSummary> boardList = new ArrayList<>();
		boardRepository.findBoardListByQuery(pageable).forEach(i -> {
			BoardSummary board = new BoardSummary();
			board.setBoardList(i);
			boardList.add(board);
		});
		
		log.debug("listSize : {}", boardList.size());
		boardList.forEach(i -> log.debug("listValue : {}", i.toString() ));
		
		return boardList;
	}

	/**
	 * 구체적 페이지 
	 * @param idx
	 * @return
	 */
	public BoardSummary findBoardByIdx(Long idx) {
		BoardSummary boardSummary = new BoardSummary();
		boardSummary.setBoard(boardRepository.findBoardByBoardId(idx).get(0));
		return boardSummary;
	}
	
	
	public List<Board> readBoardList()
	{
		return boardRepository.findAll();
	}
	
	public Board readBoardByBoardIdx(Long idx)
	{
		return boardRepository.findById(idx).orElse(null);
	}
	
	public void generateBoard(Board board) {
		boardRepository.save(board);
	}

	public void updateBoard(Board board) {
		boardRepository.save(board);
	}

	public void deleteBoard(Long idx) {
		Board originBoard = boardRepository.getOne(idx);
		boardRepository.delete(originBoard);
	}
	
}
