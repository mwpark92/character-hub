package team.spy.domain.board.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import team.spy.domain.enums.BoardType;

@Data
@Builder
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
}
