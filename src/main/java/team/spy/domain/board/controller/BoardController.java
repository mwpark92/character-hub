package team.spy.domain.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.spy.domain.User.User;
import team.spy.domain.User.annotation.SocialUser;
import team.spy.domain.board.Board;
import team.spy.domain.board.service.BoardService;

@RestController(value = "/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/boards/", method=RequestMethod.GET)
	public Board board(@RequestParam(value = "idx", defaultValue = "0") Long idx)
	{
		System.out.println("test : " + boardService.findBoardByIdx(idx).toString());
		return boardService.findBoardByIdx(idx);
	}
	
	@RequestMapping(value = "/boards/list", method=RequestMethod.GET)
	public Page<Board> list(@SocialUser User user, @PageableDefault Pageable pageable)
	{
		return boardService.findBoardList(pageable);
	}
	
	
}
