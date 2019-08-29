package team.spy.domain.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_Board_Tag")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String tagName;
	
	@Builder
	public Tag(Long idx, String tagName)
	{
		this.idx = idx;
		this.tagName = tagName;
	}
}
