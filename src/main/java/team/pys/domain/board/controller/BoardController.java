package team.pys.domain.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.pys.domain.board.Board;
import team.pys.domain.board.service.BoardService;

@RestController("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public Board board(@RequestParam(value = "idx", defaultValue = "0") Long idx)
	{
		System.out.println("test : " + boardService.findBoardByIdx(idx).toString());
		return boardService.findBoardByIdx(idx);
	}
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public Page<Board> list(@PageableDefault Pageable pageable)
	{
		return boardService.findBoardList(pageable);
	}
	
	
}
