package main;

import java.util.Scanner;

import repository.MenuRepository;
import repository.StoreRepository;
import repository.UserRepository;
import service.MenuService;
import service.StoreService;
import service.UserService;

public class Main {
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		UserService us;
		MenuService ms;
		StoreService ss;
		
//		if(파일 무결성 검사 결과 문제 있으면) {
//			return false;
//		}
		
		Validation v = new Validation();
		UserRepository ur = new UserRepository(v);		
		StoreRepository sr = new StoreRepository();
		MenuRepository mr = new MenuRepository(ur.getUserlist(), sr.getStoreMap());
		
		us = new UserService(ur, v);
		ms = new MenuService(mr, ur, v);
		ss = new StoreService(sr, v);
		
		while(true) {
			showLoginMenu();
			String menu = scan.nextLine();
			if(v.submenuInputCheck(menu)) {	//메뉴 입력이 옳으면
				if(menu.equals("1")) {	//로그인
					exit: while(true) {
						int rs = us.login();
						if(rs == 1) {	//로그인 성공 -> 주 프롬프트로 이동
							while(true) {
								showMainMenu();
								String mainMenu = scan.nextLine();
								if(mainMenu.equals("1")) {
									ss.registerStore();
								}else if(mainMenu.equals("2")) {
									ss.searchStore();
								}else if(mainMenu.equals("3")) {
									ms.teamMenuPlan();
								}else if(mainMenu.equals("4")) {
									us.changeOrder();
								}else if(mainMenu.equals("5")) {
									break exit;
								}else if(mainMenu.equals("exit")){
									break exit;
								}else {
									System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
								}
							}
						}
						else if(rs == -1);
						else if(rs == 0) break;
					}
				}else if(menu.equals("2")){	//회원가입
					while(true) {
						int rs = us.createAccount();
						if(rs == 1) break;
						else if(rs == -1);
						else if(rs == 0) continue;
					}
				}else if(menu.equals("3")) {	//종료
					System.out.println("프로그램을 종료합니다.");
					return;
				}
			}
		}
		
		
		
	}
	
	public static void showLoginMenu() {
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료");
		System.out.println("원하시는 메뉴를 입력하세요.");
	}
	
	public static void showMainMenu() {
		System.out.println("1. 음식점 입력");
		System.out.println("2. 음식점 검색");
		System.out.println("3. 식단표 제출/검수");
		System.out.println("4. 서열 관리");
		System.out.println("5. 뒤로가기");
		System.out.println("원하시는 메뉴를 입력해주세요.");
	}
	
}
