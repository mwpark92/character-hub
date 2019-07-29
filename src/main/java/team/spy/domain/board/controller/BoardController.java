package team.spy.domain.board.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import team.spy.domain.User.annotation.SocialUser;
import team.spy.domain.User.dto.User;
import team.spy.domain.board.Board;
import team.spy.domain.board.service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

	// free
	// notice
	//
	
	BoardService boardService;
	
	public BoardController(BoardService boardService)
	{
		this.boardService = boardService;
	}
	
	// @RequestParam
	@RequestMapping(value = "/{idx}", method=RequestMethod.GET)
	public Board board(@PathVariable(value = "idx") Long idx)
	{
		return boardService.findBoardByIdx(idx);
	}
	
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBoards(@SocialUser User user, @PageableDefault Pageable pageable)
	{
		Page<Board> boards = boardService.findBoardList(pageable);
		PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boards.getNumber(), boards.getTotalElements());
		PagedResources<Board> resources = new PagedResources<>(boards.getContent(), pageMetadata);
		resources.add(linkTo( methodOn(BoardController.class).getBoards(user, pageable)).withSelfRel());
		
		return ResponseEntity.ok(resources); 
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> postBoard(@RequestBody Board board)
	{
		boardService.generateBoard(board);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{idx}", method=RequestMethod.PUT)
	public ResponseEntity<?> putBoard(@PathVariable(value = "idx") Long idx, @RequestBody Board board)
	{
		boardService.updateBoard(idx, board);
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{idx}", method=RequestMethod.DELETE)
	public ResponseEntity<?> putBoard(@PathVariable(value = "idx") Long idx)
	{
		boardService.deleteBoard(idx);
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}
