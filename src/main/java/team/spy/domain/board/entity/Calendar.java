package team.spy.domain.board.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Calendar{
	
	@Column
	private String homepage;
	
	@Column
	private String address;
	
	@Column
	private LocalDate startDate;
	
	@Column
	private LocalDate endDate;

	@Builder
	public Calendar(String homepage, String address, LocalDate startDate, LocalDate endDate)
	{
		this.homepage = homepage;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public void update(Calendar calendarBoard)
	{
		this.homepage = calendarBoard.homepage;
		this.address = calendarBoard.address;
		this.startDate = calendarBoard.startDate;
		this.endDate = calendarBoard.endDate;
	}
}
