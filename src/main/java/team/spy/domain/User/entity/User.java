package team.spy.domain.User.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team.spy.enums.SocialType;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idx")
@Entity
@Table(name = "T_User")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User{

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	@Column
	private String nickname;
	
	@Column
	private String userId;
	
	@Column
	private String password;

	@Column
	private String email;

	@OneToOne
	@JoinColumn(name = "ROLE_ID")
	private MenuPermission role;

	@Column
	@Enumerated(EnumType.STRING)
	private SocialType socialType;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private LocalDateTime updateDate;

}
