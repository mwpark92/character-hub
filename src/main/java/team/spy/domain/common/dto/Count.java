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
	private CountProjectId countProjectId;
	
	@ColumnDefault("0")
	private int count;
	
	@Builder
	public Count(CountProjectId countProjectId, int count)
	{
		this.countProjectId = countProjectId;
		this.count = count;
	}

	public void increaseCount()
	{
		count = count + 1;
	}
}
