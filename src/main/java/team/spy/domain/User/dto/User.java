package team.spy.domain.User.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import team.spy.domain.board.Board;
import team.spy.domain.enums.SocialType;


@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_User")
public class User{

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;
	
	@Column
	private String nickname;
	
	@Column
	private String userid;
	
	@Column
	@JsonIgnore
	private String password;
	
	@Column
	private String email;
	
	@Column
	private String pincipal;
	
	@Column
	@Enumerated(EnumType.STRING)
	private SocialType socialType;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime updateDate;
	
	@Column
	private Integer permission;
	
	
	@Builder
	public User(String nickname, Integer permission, String userid, String password, String email, LocalDateTime createDate, 
			LocalDateTime updateDate, SocialType socialType, String pincipal)
	{
		this.permission = permission;
		this.nickname = nickname;
		this.userid = userid;
		this.password = password;
		this.email = email;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.pincipal = pincipal;
		this.socialType = socialType;
	}
}
