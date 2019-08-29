package team.spy.domain.board.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import team.spy.domain.board.dto.BoardSummary;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.process.BoardImageProcess;
import team.spy.domain.board.repository.BoardRepository;
import team.spy.domain.common.service.ImageService;

/**
 * 일반적인 BoardService implement
 * Meta규칙에 따라서 Entity 기본 CRUD와 Cumstom Query는 Method명의 Meta가 다
 * @author kwanghoyeom
 *
 */
@Slf4j
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	private final BoardImageProcess boardImageProcess;
	
	public BoardService(BoardRepository boardRepository, ImageService imageService)
	{
		this.boardRepository = boardRepository;
		boardImageProcess = new BoardImageProcess(imageService);
	}
	
	/**
	 * List Page에 출력될 목록 
	 * @param pageable 페이지 지정
	 * @return BoardSummary의 페이지 List 리턴
	 */
	public List<BoardSummary> findBoardList(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		return boardRepository.findBoardListByQuery(pageable);
	}

	/**
	 * 특정 Post 호출, app cache 적용
	 * @param idx board를 불러올 Key값
	 * @return 해당 board를 출력한다.
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
		boardImageProcess.bringFileFromTemp(board.getIdx(), board.getContent());
	}

	// need finding img name and moving calendar directory
//	@CacheEvict(cacheNames = "userCache", key = "#idx")
	public void updateBoard(Board board) {
		boardRepository.save(board);
	}

//	@CacheEvict(cacheNames = "userCache", key = "#idx")
	public boolean deleteBoard(Long idx) {
		
		final Board originBoard = boardRepository.findById(idx).orElse(null);
		
		if(originBoard != null) {
			boardRepository.deleteById(idx);
			return true;
		}
		
		return false;
	}
	
}
