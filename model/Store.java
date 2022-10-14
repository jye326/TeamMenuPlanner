package model;

public class Store {
	private String storeName;
	private String location;
	private String menu;
	
	public Store(String storeName, String location, String menu) {
	
		this.storeName = storeName;
		this.location = location;
		this.menu = menu;
	}

	public String getStoreName() {
		return storeName;
	}
	
	public String getLocation() {
		return location;
	}

	public String getMenu() {
		return menu;
	}

	@Override
	public String toString() {
		String result = "#######################";
		result += "음식점 이름 : " + this.getStoreName();
		result += "메인 메뉴 : " + this.getMenu();
		result += "음식점 위치 : " + this.getLocation();
		result += "#######################\n";
		return result;
	}	
	
	
}