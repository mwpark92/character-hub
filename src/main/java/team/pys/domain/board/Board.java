package team.pys.domain.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.pys.domain.User.User;
import team.pys.domain.enums.BoardType;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table
public class Board{


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
	@Enumerated(EnumType.STRING)
	private BoardType boardType;
	
	@Column
	private LocalDateTime createTime;
	
	@Column
	private LocalDateTime updateDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Builder
	public Board(String title, String subTitle, String content, BoardType boardType,
			LocalDateTime createTime, LocalDateTime updateDate, User user)
	{
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.boardType = boardType;
		this.createTime = createTime;
		this.updateDate = updateDate;
		this.user = user;
	}
	
	
}
