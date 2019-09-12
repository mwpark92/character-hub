package team.spy.enums;

public enum SocialType {

	FACEBOOK("facebook"),
	GOOGLE("google"),
	KAKAO("kakao"),
	NAVER("naver");
	
	private static final String ROLE_PREFIX = "ROLE_";
	private String name;
	
	SocialType(String name)
	{
		this.name = name;
	}
	
	public String getRoleType()
	{
		return ROLE_PREFIX + name.toUpperCase();
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
