package team.spy.domain.board.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.spy.domain.board.dto.BoardSummary;
import team.spy.domain.board.entity.Board;
import team.spy.domain.board.service.BoardService;


@RestController
@RequestMapping(value = "/api/boards/calendars")
public class BoardController 
{
	BoardService boardService;
	HttpHeaders httpHeaders;
	
	public BoardController(BoardService boardService)
	{
		this.boardService = boardService;
		
		httpHeaders = new HttpHeaders();
//		httpHeaders.set("Cache-Control", "max-age=3600");
	}
	 
	@GetMapping(value = "/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BoardSummary> getBoard(@PathVariable(value = "idx") Long idx)
	{
		BoardSummary board = boardService.findBoardByIdx(idx);
		
		if(board == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
//				.eTag(Integer.toString(board.hashCode()))
				.body(board);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<BoardSummary>> getBoardBoardList(@PageableDefault Pageable pageable)
	{
		return ResponseEntity.ok()
		.headers(httpHeaders)
		.body(findBoardList(pageable));
	}
	
	
	@PostMapping
	public ResponseEntity<String> postBoard(@RequestBody Board board)
	{
		boardService.generateBoard(board);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{idx}")
	public ResponseEntity<String> putBoard(@PathVariable(value = "idx") Long idx, @RequestBody Board board)
	{
		boardService.updateBoard(board);
		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{idx}")
	public ResponseEntity<String> deleteBoard(@PathVariable(value = "idx") Long idx)
	{
		return (boardService.deleteBoard(idx) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build());
	}
	
	private PagedResources<BoardSummary> findBoardList(Pageable pageable)
	{
		Page<BoardSummary> boards = new PageImpl<>( boardService.findBoardList(pageable) );
		
		PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), boards.getNumber(), boards.getTotalElements());
		PagedResources<BoardSummary> resources = new PagedResources<>(boards.getContent(), pageMetadata);
		resources.add(linkTo(methodOn(BoardController.class).getBoardBoardList(pageable)).withSelfRel());
	
		return resources;	
	}
}
