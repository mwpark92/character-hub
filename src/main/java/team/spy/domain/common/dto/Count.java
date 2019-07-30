package team.spy.domain.common.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_Count")
public class Count {
	
	@EmbeddedId
	private CountProjectId idx;
	
	@ColumnDefault("0")
	private int count;
	
	@Builder
	public Count(CountProjectId idx, int count)
	{
		this.idx = idx;
		this.count = count;
	}

}
