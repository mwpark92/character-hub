package team.spy.enums;

public enum BoardType {

	NOTICE("공지사항"),
	FREE("자유게시판"),
	CALENDAR("캘린더");
	
	private final String value;
	
	BoardType(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
}
