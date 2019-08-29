package team.spy.domain.board.entity;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.spy.domain.enums.BoardType;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_Board")
@SecondaryTable(
		name="T_Board_Calendar",
		pkJoinColumns = @PrimaryKeyJoinColumn(name="calendar_id", referencedColumnName = "idx")
)
public class Board{


	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	@Enumerated(EnumType.STRING)
	private BoardType boardType;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime updateDate;
	
	@Column
	private Long user;
	
	@Embedded
	@AttributeOverride(name="homepage", column = @Column(name = "homepage", table="T_Board_Calendar"))
	@AttributeOverride(name="address", column = @Column(name = "address", table="T_Board_Calendar"))
	@AttributeOverride(name="startDate", column = @Column(name = "startDate", table="T_Board_Calendar"))
	@AttributeOverride(name="endDate", column = @Column(name = "endDate", table="T_Board_Calendar"))
	private Calendar calendar;

	@Transient
	private String href;
	
	
	@Builder
	public Board(String title, String content, BoardType boardType,
			LocalDateTime createDate, LocalDateTime updateDate, 
			Long user)
	{
		this.title = title;
		this.content = content;
		this.boardType = boardType;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.user = user;
	}
	
	
	
	public void setHref(String href)
	{
		this.href = href;
	}
	
	public void update(Board board)
	{
		this.title = board.title;
		this.content = board.content;
		this.boardType = board.boardType;
		this.updateDate = LocalDateTime.now();
	}
	
}
