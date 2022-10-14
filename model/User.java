package model;

public class User {
	private String id;
	private String pw;
	private String userName;
	private int order;
	private int lockStack;
	private boolean isReport;
	
	public User(String id, String pw, String userName, int order, int lockStack, boolean isReport) {
		this.id = id;
		this.pw = pw;
		this.userName = userName;
		this.order = order;
		this.lockStack = lockStack;
		this.isReport = isReport;
	}
	
	public int getLockStack() {
		return lockStack;
	}

	public boolean getIsReport() {
		return isReport;
	}

	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getUserName() {
		return userName;
	}
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}

	public void increaseLockStack(int lockStack) {
		this.lockStack += lockStack;
	}
	
	public void decreaseLockStack(int lockStack) {
		this.lockStack -= lockStack;
	}
}
