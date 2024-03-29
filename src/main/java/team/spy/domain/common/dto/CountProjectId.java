package team.spy.domain.common.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class CountProjectId implements Serializable{
	
	@Column
	private int type;
	
	@Column
	private long idx;
	
	@Builder
	public CountProjectId(int type, long idx) {
		this.type = type;
		this.idx = idx;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = type;
		result = (int) (prime * result + idx);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		CountProjectId other = (CountProjectId) obj;
		if(this.type != other.type) return false;
		return this.idx == other.idx;
	}
}
