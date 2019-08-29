package team.spy.domain.User.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "T_User_MenuPermission")
public class MenuPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int no;
	
	@Column
	private int permission;
	
	@Column
	private String menucode;
	
	@Column
	private String menuName;

	@Builder
	public MenuPermission(int permission, String menucode, String menuName)
	{
		this.permission = permission;
		this.menucode = menucode;
		this.menuName = menuName;
	}
	
}
