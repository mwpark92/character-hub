package team.spy.domain.board.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.spy.domain.User.dto.User;
import team.spy.domain.common.dto.Count;
import team.spy.domain.enums.BoardType;


@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_Board")
public class CommonBoard extends Board{

	@Builder
	public CommonBoard(String title, String content, BoardType boardType,
			LocalDateTime createDate, LocalDateTime updateDate, User user)
	{
		super(title, content, boardType, createDate, updateDate, user);
	}
	
	public void update(CommonBoard board)
	{
		super.update(board);
	}
	
}
