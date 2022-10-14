package model;

public class Menu {
	private Dates day;
	private String name;
	private User user;
	private boolean lock;
	
	public Menu(String day, String name, User user, boolean lock) {
		this.day = Dates.valueOf(day);
		this.name = name;
		this.user = user;
		this.lock = lock;
	}

	public int getDay() {
		return day.ordinal();
	}

	public void setDay(String day) {
		this.day = Dates.valueOf(day);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	
	
}