package team.spy.domain.board.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import team.spy.domain.board.dto.BoardSummary;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.repository.BoardRepository;

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
		List<BoardSummary> boardList = boardRepository.findBoardListByQuery(pageable);

		log.debug("listSize : {}", boardList.size());
		boardList.forEach(i -> log.debug("listValue : {}", i.toString() ));
		
		System.out.println("not cacheable!");
		
		return boardList;
	}

	/**
	 * 구체적 페이지 
	 * @param idx
	 * @return
	 */

    @Cacheable(cacheNames = "userCache", key = "#idx")
	public BoardSummary findBoardByIdx(Long idx) {
		return boardRepository.findBoardByBoardId(idx);
	}
	
	public List<Board> readBoardList()
	{
		return boardRepository.findAll();
	}
	
	public Board readBoardByBoardIdx(Long idx)
	{
		return boardRepository.findById(idx).orElse(null);
	}
	
	
	// need finding img name and moving calendar directory
	public void generateBoard(Board board) {
		boardRepository.save(board);
	}

	// need finding img name and moving calendar directory
	@CacheEvict(cacheNames = "userCache", key = "#idx")
	public void updateBoard(Board board) {
		boardRepository.save(board);
	}

	@CacheEvict(cacheNames = "userCache", key = "#idx")
	public boolean deleteBoard(Long idx) {
		
		Board originBoard = boardRepository.findById(idx).orElse(null);
		
		if(originBoard != null) {
			boardRepository.deleteById(idx);
			return true;
		}
		
		return false;
	}
	
}
