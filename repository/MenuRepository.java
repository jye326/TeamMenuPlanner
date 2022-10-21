package repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Dates;
import model.Menu;
import model.Store;
import model.User;

/*식단 데이터 파일 관련*/
public class MenuRepository implements Repository<Menu> {

	/* 텍스트 파일 디렉토리, ##수정 필수## */
	File file = new File("datateamMenuPlanner.txt");	//팀 주간 식단표 텍스트
//	private HashMap<String, Menu> menuMap;
	private Menu[] teamMenu = new Menu[8];

	public MenuRepository(HashMap<String, User> users, HashMap<String, Store> stores) {
		teamMenu = this.readFile(users, stores);
	}
	
	/*
	 * 파일 생성 및 초기화 생성되면 TRUE 이미 있거나 생성 안되면 FALSE
	 */
	public boolean createFile() {
		try {
			return file.createNewFile();
		} catch (IOException e) {
			return false;
		}
	}
	
	@Override
	public void saveFile(ArrayList<Menu> obj) {
		// TODO Auto-generated method stub
		
	}
	
	
	public Menu[] readFile(HashMap<String, User> users, HashMap<String, Store> stores) {
		int n = 0;
		try {
			Scanner sc = new Scanner(file);
			//example
			while (sc.hasNextLine()) {
				String[] str = sc.nextLine().split("/");
				Menu menu = new Menu(str[0].trim(), str[1].trim(), users.get(str[2].trim()), str[3].trim().equals("1"));
				if (this.checkContain(teamMenu, users, stores, menu)) {
					teamMenu[menu.getDay()] =  menu;
					System.out.println(menu.getDay()+" "+menu.getName());
				}
			}
			//example
//			for(Menu i: teamMenu) {
//				System.out.println(i.getDay()+" "+i.getName());
//			}
			return teamMenu;

		} catch (FileNotFoundException e1) {
			createFile();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return null;
	}
	
	public boolean checkContain(Menu[] menus, HashMap<String, User> users, HashMap<String, Store> stores, Menu menu) {
        
		ArrayList<User> us = new ArrayList<User>();
		us.addAll(users.values());
		
		ArrayList<Store> st = new ArrayList<Store>();
		st.addAll(stores.values());

		System.out.println("here1");
		try {
			if (menus[menu.getDay()]!= null) {
				return false;
			}
		} catch (Exception e) {return false;}
		System.out.println("here2");
		try {
			if (!this.checkStore(st, menu.getName()) || !this.checkUserId(us, menu.getUser().getId())) {
				return false;
			} 
		} catch (Exception e) {return false;}
		System.out.println("here3");
		try {
			if (menus[menu.getDay()-1].getName().equals(menu.getName())) {
				return false;
			}
		} catch (Exception e) {}
		System.out.println("here4");
		try {
			if (menus[menu.getDay()+1].getName().equals(menu.getName()))
				return false;
		} 
		catch (Exception e2) {}
		System.out.println("here5");
		return true;
	}

	public boolean checkUserId(ArrayList<User> users, String id) {
		for (User u : users) {
			if (u.getId().equals(id))
				return true;
		}
		return false;
	}

	public boolean checkStore(ArrayList<Store> stores, String name) {
		for (Store s : stores) {
			if (s.getStoreName().equals(name))
				return true;
		}
		return false;
	}
	
	public Menu[] getTeamMenu() {
		return teamMenu;
	}
	
	public void setTeamMenu(Menu[] teamMenu) {
		this.teamMenu = teamMenu;
	}
	
	@Override
	public boolean create(Object o) {
		return true;
	}

	@Override
	public boolean delete(Object o) {
		return false;
	}

	@Override
	public List findAll() {
		return null;
	}

	@Override
	public HashMap<String, Menu> readFile() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}