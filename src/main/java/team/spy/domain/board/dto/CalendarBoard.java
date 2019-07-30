package team.spy.domain.board.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.spy.domain.User.dto.User;
import team.spy.domain.enums.BoardType;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_CalendarBoard")
public class CalendarBoard extends Board{
	
	@Column
	private String homepage;
	
	@Column
	private String address;
	
	@Column
	private LocalDateTime startDate;
	
	@Column
	private LocalDateTime endDate;

	@Builder
	public CalendarBoard(String title, String content, BoardType boardType,
			LocalDateTime createDate, LocalDateTime updateDate, User user,
			String homepage, String address, LocalDateTime startDate, LocalDateTime endDate)
	{
		super(title, content, boardType, createDate, updateDate, user);
		this.homepage = homepage;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	public void update(CalendarBoard calendarBoard)
	{
		super.update(calendarBoard);
		this.homepage = calendarBoard.homepage;
		this.address = calendarBoard.address;
		this.startDate = calendarBoard.startDate;
		this.endDate = calendarBoard.endDate;
	}
	
	
}
