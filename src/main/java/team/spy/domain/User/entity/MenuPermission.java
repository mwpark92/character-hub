package team.spy.domain.User.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode(of = "no")
@ToString
@Entity
@Table(name = "T_User_Permission")
public class MenuPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;

	@OneToOne(mappedBy = "role")
	private User user;

	@OneToMany(mappedBy = "menuPermission")
	private List<Menu> menuPermissionList = new ArrayList<>();

	@Column
	private int permission;
	
	@Column
	private String menuCode;
	
	@Column
	private String menuName;


}
