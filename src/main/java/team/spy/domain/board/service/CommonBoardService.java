package team.spy.domain.board.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.spy.domain.User.dto.User;
import team.spy.domain.board.dto.CommonBoard;
import team.spy.domain.board.repository.CommonBoardRepository;
import team.spy.domain.enums.BoardType;

/**
 * 일반적인 BoardService implement
 * @author kwanghoyeom
 *
 */
@Service
public class CommonBoardService {

	private final CommonBoardRepository boardRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonBoardService.class);
	
	public CommonBoardService(CommonBoardRepository boardRepository)
	{
		this.boardRepository = boardRepository;
	}
	
	public List<CommonBoard> findBoardList(Pageable pageable)
	{
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		List<CommonBoard> boardList = new ArrayList<>();
		List<Object[]> list = boardRepository.findBoardListByQuery(pageable);
		
		if(list == null) list = Collections.emptyList();
		
		list.forEach(i -> {
			boardList.add(CommonBoard.builder()
					.title((String)i[0])
					.boardType((BoardType)i[1])
					.createDate((LocalDateTime)i[2])
					.updateDate((LocalDateTime)i[3])
					.user(User.builder().nickname((String)i[4]).build())
					.build());
		});
		
		logger.debug("list : {}", boardList.toArray().toString());
		
		return boardList; 
	}
	
	public CommonBoard findBoardByBoardIdx(Long idx)
	{
		return boardRepository.findById(idx).orElse(new CommonBoard());
	}
	
	
	public void generateBoard(CommonBoard board)
	{
		boardRepository.save(board);
	}
	
	public void updateBoard(Long idx, CommonBoard board)
	{
		CommonBoard originBoard = boardRepository.getOne(idx);
		originBoard.update(board);
		boardRepository.save(originBoard);
	}
	
	public void deleteBoard(Long idx)
	{
		boardRepository.deleteById(idx);
	}
	
	
}
