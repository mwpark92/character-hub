package team.spy.domain.calendar;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.spy.domain.User.dto.User;
import team.spy.domain.enums.BoardType;

@ToString
@Getter
@NoArgsConstructor
@Entity(name = "T_Schedule")
@Table
public class Schedule {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String title;
	
	
	@Column
	private String subTitle;
	
	
	@Column
	private String content;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime updateDate;
	
	@Column
	private LocalDateTime startDate;
	
	@Column
	private LocalDateTime terminateDate;
	
}
