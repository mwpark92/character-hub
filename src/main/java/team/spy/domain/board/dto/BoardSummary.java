package team.spy.domain.board.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.spy.domain.enums.BoardType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardSummary {
	
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
	
	public void setBoard(Object[] object)
	{
		this.boardIdx  = (Long)object[0];
		this.boardTitle = (String)object[1];
		this.boardType = (BoardType)object[2];
		this.boardContent = (String)object[3];
		this.boardCreateDate = (LocalDateTime)object[4];
		this.boardUpdateDate = (LocalDateTime)object[5];
		this.calendarHomepage = (String)object[6];
		this.calendarAddress = (String)object[7];
		this.calendarStartDate = (LocalDate)object[8];
		this.calendarEndDate = (LocalDate)object[9];
		this.userIdx = (Long)object[10];
		this.userNickname = (String)object[11];
		this.href = "/boards/calendars/" + this.boardIdx;
	}
	
	public void setBoardList(Object[] object)
	{
		this.boardIdx = (Long)object[0];
		this.boardTitle = (String)object[1];
		this.boardType = (BoardType)object[2];
		this.boardCreateDate = (LocalDateTime)object[3];
		this.boardUpdateDate = (LocalDateTime)object[4];
		this.userIdx = (Long)object[5];
		this.userNickname = (String) object[6];
		this.href = "/'boards/calendars/" + this.boardIdx;
	}
}
