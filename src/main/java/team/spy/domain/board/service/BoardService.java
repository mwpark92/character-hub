package team.spy.domain.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import team.spy.domain.board.Board;
import team.spy.domain.board.BoardRepository;

@Service
public class BoardService {

	private final BoardRepository boardRepository;
	
	
	public BoardService(BoardRepository boardRepository)
	{
		this.boardRepository = boardRepository;
	}
	
	
	public Page<Board> findBoardList(Pageable pageable)
	{
		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
		
		return boardRepository.findAll(pageable);
	}
	
	
	public Board findBoardByIdx(Long idx)
	{
		return boardRepository.findById(idx).orElse(new Board());
	}
	
	
	public void generateBoard(Board board)
	{
		boardRepository.save(board);
	}
	
	public void updateBoard(Long idx, Board board)
	{
		Board originBoard = boardRepository.getOne(idx);
		originBoard.Update(board);
		boardRepository.save(originBoard);
	}
	
	public void deleteBoard(Long idx)
	{
		boardRepository.deleteById(idx);
	}
	
	public List<Board> findBoardView()
	{
		List<Object> list = boardRepository.findBoardListByQuery();
		System.out.println(list.stream().count());
		
//		for(Object object : list)
//		{
//			Object[] results = (Object[])object;
//			for(Object result : results)
//			{
//				System.out.println(result);
//			}
//		}
		
		return null; 
	}
}
