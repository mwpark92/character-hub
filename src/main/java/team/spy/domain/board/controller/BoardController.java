package team.spy.domain.board.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import team.spy.domain.board.dto.Board;
import team.spy.domain.board.dto.CommonBoard;
import team.spy.domain.board.service.CommonBoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

	// free
	// notice
	//
	
	CommonBoardService boardService;
	
	public BoardController(CommonBoardService boardService)
	{
		this.boardService = boardService;
	}
	
	// @RequestParam
	@RequestMapping(value = "/{idx}", method=RequestMethod.GET)
	public Board board(@PathVariable(value = "idx") Long idx)
	{
		return boardService.findBoardByBoardIdx(idx);
	}
	
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBoards(@SocialUser User user, @PageableDefault Pageable pageable)
	{
		Page<CommonBoard> boards = new PageImpl<>(boardService.findBoardList(pageable));
		PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boards.getNumber(), boards.getTotalElements());
		PagedResources<CommonBoard> resources = new PagedResources<>(boards.getContent(), pageMetadata);
		resources.add(linkTo( methodOn(BoardController.class).getBoards(user, pageable)).withSelfRel());
		
		return ResponseEntity.ok(resources); 
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> postBoard(@RequestBody CommonBoard board)
	{
		boardService.generateBoard(board);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{idx}", method=RequestMethod.PUT)
	public ResponseEntity<?> putBoard(@PathVariable(value = "idx") Long idx, @RequestBody CommonBoard board)
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
