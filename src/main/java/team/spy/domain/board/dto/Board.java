package team.spy.domain.board.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.spy.domain.User.dto.User;
import team.spy.domain.common.dto.Count;
import team.spy.domain.enums.BoardType;

@ToString
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Board{


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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	// private Tag tag;
	
	@OneToOne
	private Count count;
	
	
	public Board(String title, String content, BoardType boardType,
			LocalDateTime createDate, LocalDateTime updateDate, User user)
	{
		this.title = title;
		this.content = content;
//		this.boardType = boardType;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.user = user;
	}
	
	public void update(Board board)
	{
		this.title = board.title;
		this.content = board.content;
//		this.boardType = board.boardType;
		this.updateDate = LocalDateTime.now();
	}
	
}
