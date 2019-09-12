package team.spy.domain.board.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@DiscriminatorValue("Calendar")
@Entity
public class Calendar extends Board{

	@Column
	private String homepage;
	
	@Column
	private String address;
	
	@Column
	private LocalDate startDate;
	
	@Column
	private LocalDate endDate;
	
	public void update(Calendar calendarBoard)
	{
		this.homepage = calendarBoard.homepage;
		this.address = calendarBoard.address;
		this.startDate = calendarBoard.startDate;
		this.endDate = calendarBoard.endDate;
	}
}
