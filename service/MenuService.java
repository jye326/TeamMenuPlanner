package service;

import java.util.Scanner;

import main.Validation;
import model.Menu;
import repository.MenuRepository;
import repository.UserRepository;

public class MenuService {
	private MenuRepository mr;
	private UserRepository ur;
	private Validation v;
	private Menu[] wm;
	private String[] week = {"월", "화", "수", "목", "금", "토", "일"};
	
	public MenuService(MenuRepository mr, UserRepository ur, Validation v) {
		this.mr = mr;
		this.ur = ur;
		this.v = v;
	}

	static Scanner scan = new Scanner(System.in);
	
	
	/*
	 * 식단표 조정 함수
	 */
	
	public void teamMenuPlan() {
		int cCount = 0;	//기능 사용했는지
		Menu[] origin = new Menu[7];
//		origin = mr.getTeamMenu().clone();
		if(checkReported()) {	//제출 안 했으면
			if(inspectTeamMenu()) {	//조정 할거면
				while(true) {
//					showTeamMenu();
					String str = scan.nextLine();
					if() {
						
					}
					if(cCount != 1) {
						//검증할 거: 요일 같은 요일 입력 안했는지, 선택한 요일에 메뉴 있는지
						cCount++;
					}
					
				}
			}else {	//조정 안하면
				System.out.println("=====[식단표가 확정 되었습니다.]=====");
			}
		}
	}

	/*
	 * 요일 / 메뉴이름 / (lock)
	 * 사용자가 요일의 메뉴를 생성 혹은 갱신 하는 함수
	 */
	public boolean changeMenu(String d, String menuName, boolean lock) {
		int day = getDay(d);
		wm = mr.getTeamMenu();
		
		if(!wm[day].getLock()) {	//락이 안 걸려 있으면
			if(wm[day].getUser().getOrder() < ur.getCurrentUser().getOrder()) {	//현재 사용자가, 바꾸고자 하는 메뉴를 신청한 사람보다 서열이 높으면
				if(checkNearDays(day, wm[day].getName(), wm)) {	//이틀 연속 같은 메뉴가 아닌 경우
					if(lock) {	//락 거는 경우
						if(ur.getCurrentUser().getLockStack() >= 2) {	//락 스택이 2 이상 있으면
							if(wm[day] != null) {	//이미 메뉴가 있으면
								wm[day].getUser().increaseLockStack(1);	//뺏긴 사람 1스택 주기	
								wm[day] = new Menu(d, menuName, ur.getCurrentUser(), lock);
								ur.getCurrentUser().decreaseLockStack(2);	//락 사용한 사람 락 2스택 감소
								return true;
							}else {		//그냥 빈 날이면
								wm[day] = new Menu(d, menuName, ur.getCurrentUser(), lock);
								ur.getCurrentUser().decreaseLockStack(2);
								return true;
							}
						}else {
							System.out.println("=====락 스택이 부족합니다.=====");
							return false;
						}
					}else {	//락 안 거는 경우
						if(wm[day] !=null) {	//이미 메뉴가 있으면
							wm[day].getUser().increaseLockStack(1);	//뺏긴 사람 1스택 주기	
							wm[day] = new Menu(d, menuName, ur.getCurrentUser(), lock);
							return true;
						}else {		//그냥 빈 날이면
							wm[day] = new Menu(d, menuName, ur.getCurrentUser(), lock);
							return true;
						}
					}
				}
			}else {
				System.out.println("해당 메뉴를 신청한 사람이 사용자보다 서열이 높습니다.");
				return false;
			}
		}else { 	//메뉴에 락 걸려있는 경우
			System.out.println("해당 메뉴는 락이 걸려있어 변경이 불가합니다.");
			return false;
		}
		return false;
	}	
	
	public boolean changeDayToDay(String d1, String d2, boolean lock) {
		int day1 = getDay(d1);
		int day2 = getDay(d2);
		Menu[] wm = mr.getTeamMenu();
		
		if(wm[day1] != null & wm[day2] != null) {	//두 요일 모두 메뉴가 있는 경우
			if(!wm[day1].getLock() && !wm[day2].getLock()) {	//락이 안걸려 있는 경우 
				if(wm[day1].getUser() == ur.getCurrentUser()) {	//현재 사용자가 신청한 메뉴를 바꾸는 경우
					if(wm[day1].getUser().getOrder() > wm[day2].getUser().getOrder()) {	//바꾸려고 하는 메뉴를 신청한 사람보다 현재 사용자가 서열이 높은 경우
						if(!(checkNearDays(day1, wm[day1].getName(), wm) && checkNearDays(day2, wm[day2].getName(), wm))) {	//이틀 연속 같은 메뉴를 신청한 경우  우
							System.out.println("이틀 연속 같은 메뉴를 신청할 수 없습니다.");
							return false;
						}else {	//이틀 연속 신청이 아닌 경우
							if(lock) {	//락 거는 경우
								if(ur.getCurrentUser().getLockStack() >= 2) {	//락 스택이 2 이상 있으면, day1: 바꾸는 요일, day2: 바꿈 당하는 요일
									wm[day2].getUser().increaseLockStack(1);	//뺏긴 사람 1스택 주기
									ur.getCurrentUser().decreaseLockStack(2);	//락 사용한 사람 락 2스택 감소
									wm[day1].setLock(lock);	//락 걸고
									Menu temp = wm[day2];										
									wm[day2] = wm[day1];
									wm[day1] = temp;
									return true;																		
								}else {
									System.out.println("=====락 스택이 부족합니다.=====");
									return false;
								}
							}else {	//락 안 거는 경우
								wm[day2].getUser().increaseLockStack(1);	//뺏긴 사람 1스택 주기
								Menu temp = wm[day2];										
								wm[day2] = wm[day1];
								wm[day1] = temp;
								return true;
							}
							
						}
					}else {
						System.out.println("해당 메뉴를 신청한 사람이 사용자보다 서열이 높습니다.");
						return false;
					}
				}else {
					System.out.println("사용자가 신청한 메뉴만 수정 가능합니다.");
					return false;
				}
			}else {
				System.out.println("해당 메뉴 중 하나 이상이 락이 걸려있어 변경이 불가합니다.");
				return false;
			}
		}else {
			System.out.println("선택하신 요일 중 메뉴가 없는 요일이 있습니다.");
			return false;
		}
	}
	
	/*
	 * 현재 주간 식단표 출력 함수
	 * 바뀐 거 실시간으로 보여줌
	 */
	public void showTeamMenu() {
		Menu[] teamMenues = mr.getTeamMenu();
		int total = getTotalNameLength(wm);
		int space = (total-9)/7;
		int iter = 7;
		System.out.println("*******[식단표 조정]*******\n\n");
		System.out.print("### 나의 서열 : " + ur.getCurrentUser().getOrder() + " ###");
		System.out.println(printLayout(total + 14 ,0));
		for(int i=0; i<7; i++) {
			System.out.print(printLayout(week[i], space));
		}
		System.out.println(printLayout(total + 14 ,0));		//월화수목금 출력까지 완
		System.out.println(printLayout(iter ,space));
		for(int i=0; i<7; i++) {
			System.out.print(printLayout(String.valueOf( wm[i].getUser().getOrder()), space));
		}
		System.out.println(printLayout(iter ,space));	//서열 출력까지 완
		System.out.println(printLayout(total + 14 ,0));
		System.out.println(printLayout(iter ,space));
		for(int i=0; i<7; i++) {
			System.out.print(printLayout(String.valueOf( wm[i].getName()), space));
		}
		System.out.println(printLayout(iter ,space));	//메뉴 이름까지 출력 완
		System.out.println(printLayout(total + 14 ,0));
	}	
	
	public boolean checkNearDays(int day, String menuName , Menu[] wm) {
	
		if(day == 0) {	//월요일인 경우
			if(wm[day+1].getName().equals(menuName)) {
				System.out.println("이틀 연속 같은 메뉴를 신청하실 수 없습니다.");
				return false;
			}
		}else if(day == 6) {	//일요일인 경우
			if(wm[day-1].getName().equals(menuName)) {
				System.out.println("이틀 연속 같은 메뉴를 신청하실 수 없습니다.");
				return false;
			}
		}else {
			if(wm[day+1].getName().equals(menuName) || wm[day-1].getName().equals(menuName)) {
				System.out.println("이틀 연속 같은 메뉴를 신청하실 수 없습니다.");
				return false;
			}
		}

		return true;
	}
	
	/*
	 * 식단표 제출했는지 여부 확인 함수
	 * 서열 정보랑 락 스택 가져오는 거 추가 해야함.
	 */
	public boolean checkReported() {
		if(ur.getCurrentUser().getIsReport()) {	//식단표 제출했으면
			System.out.println("=======이미 주간 식단표를 제출하였습니다.=======");
			return false;
		}
		
		return true;
	}
	
	/*
	 * 이전 식단표를 조정할지 여부 체크 함수
	 */
	public boolean inspectTeamMenu() {
		while(true) {
//			showTeamMenu();
			System.out.println("식단표를 조정하시겠습니까?(y/n)");
			String ans = scan.nextLine();
			if(ans.equals("exit")) return false;
			
			if(ans.equals("y") || ans.equals("Y")) {
				return true;
			}else if(ans.equals("n") || ans.equals("N")) {
				return false;
			}
		}
	}

	public int getDay(String day) {
		for(int i=0; i<7; i++) {
			if(week[i].equals(day)) return i;
		}
		
		return -1;
	}
	
	public int getTotalNameLength(Menu[] m) {
		int max = 0;
		for(int i=0; i<m.length-1; i++) {
			max = Math.max(Integer.parseInt(m[i].getName()), Integer.parseInt(m[i+1].getName()));
		}
		
		return max*7 + 14;
	}
	
	/*
	 * space : 공백 개수
	 * i : 반복 횟수
	 */
	public String printLayout(int i, int space) {
		String str="";
		for(int j=0; j<i; j++) {
			for(int k=0; k<space; k++) {
				str += " ";
			}
			 str += "#";
		}
		return str;
	}
	
	public String printLayout(String target, int space) {
		String str = "#";
		for(int i=0; i<space/2; i++) {
			str += " ";
		}
		str += target;
		for(int i=space/2; i<space; i++) {
			str += " ";
		}
		
		return str;
	}
}
