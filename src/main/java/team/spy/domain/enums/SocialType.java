package team.spy.domain.enums;

public enum SocialType {

	FACEBOOK("facebook"),
	GOOGLE("google"),
	KAKAO("kakao"),
	NAVER("naver");
	
	private final String ROLE_PREFIX = "ROLE_";
	private String name;
	
	SocialType(String name)
	{
		this.name = name;
	}
	
	public String getRoleType()
	{
		return this.ROLE_PREFIX + name.toUpperCase();
	}
	
	public String getValue()
	{
		return this.name;
	}
	
	public boolean equals(String authority)
	{
		return this.getValue().equals(authority);
	}
}
