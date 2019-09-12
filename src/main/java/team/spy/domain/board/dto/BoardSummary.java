package team.spy.domain.board.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;
import team.spy.enums.BoardType;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardSummary implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// board
	private Long boardIdx;
	private String boardTitle;
	private String boardContent;
	private BoardType boardType;
	private LocalDateTime boardCreateDate;
	private LocalDateTime boardUpdateDate;
	
	// calendar
	private String calendarHomepage;
	private String calendarAddress;
	private LocalDate calendarStartDate;
	private LocalDate calendarEndDate;

	// user
	private Long userIdx;
	private String userNickname;
	
	// HATEOS
	private String href;
	
	public BoardSummary(Long boardIdx, String boardTitle, String boardContent, BoardType boardType,
			LocalDateTime boardCreateDate, LocalDateTime boardUpdateDate, String calendarHomepage,
			String calendarAddress, LocalDate calendarStartDate, LocalDate calendarEndDate, Long userIdx, String userNickname) {

		this.boardIdx = boardIdx;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardType = boardType;
		this.boardCreateDate = boardCreateDate;
		this.boardUpdateDate = boardUpdateDate;
		this.calendarHomepage = calendarHomepage;
		this.calendarAddress = calendarAddress;
		this.calendarStartDate = calendarStartDate;
		this.calendarEndDate = calendarEndDate;
		this.userIdx = userIdx;
		this.userNickname = userNickname;
		href = "/'boards/calendars/" + this.boardIdx;
	}
	
	public BoardSummary(Long boardIdx, String boardTitle, BoardType boardType, 
			LocalDateTime boardCreateDate, LocalDateTime boardUpdateDate, Long userIdx,
			String userNickname
			)
	{
		this(boardIdx, boardTitle, null, boardType, boardCreateDate, boardUpdateDate, 
				null, null, null, null, userIdx, userNickname);
	}	
}
