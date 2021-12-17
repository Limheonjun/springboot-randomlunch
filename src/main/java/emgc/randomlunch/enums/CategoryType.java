package emgc.randomlunch.enums;

public enum CategoryType {
	CHICKEN("치킨"),
	DESSERT("카페/디저트"),
	SNACK("분식"),
	PIZZA("피자"),
	ASIAN("아시안"),
	CHINESE("중국집"),
	PORK_FEET("족발/보쌈"),
	STEAMED_SOUP("찜/탕"),
	BURGER("버거"),
	MEAT("고기"),
	KOREAN("한식"),
	WESTERN("양식"),
	SUSHI("회/초밥"),
	ETC("기타")
	;

	private String value;

	CategoryType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
