package team.spy.domain.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "T_Board")
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Builder
	public Board(String title, String content, BoardType boardType,
			LocalDateTime createDate, LocalDateTime updateDate, User user)
	{
		this.title = title;
		this.content = content;
		this.boardType = boardType;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.user = user;
	}
	
	public void Update(Board board)
	{
		this.title = board.title;
		this.content = board.content;
		this.boardType = board.boardType;
		this.updateDate = LocalDateTime.now();
	}
	
}
