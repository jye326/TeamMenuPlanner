package repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	File file = new File("data/teamMenuPlanner.txt");	//팀 주간 식단표 텍스트
//	private HashMap<String, Menu> menuMap;
	private Menu[] teamMenu = new Menu[7];

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
			Menu[] menus = new Menu[7];
			while (sc.hasNextLine()) {
				String[] str = sc.nextLine().split("/");
				Menu menu = new Menu(str[0].trim(), str[1].trim(), users.get(str[2].trim()), str[3].trim().equals("1"));
				if (this.checkContain(menus, (ArrayList<User>)users.values(), (ArrayList<Store>)stores.values(), menu)) {
					menus[menu.getDay()] =  menu;
				}
			}
			return menus;

		} catch (FileNotFoundException e1) {
			createFile();
		} catch (Exception e2) {
			
		}
		return null;
	}
	
	public boolean checkContain(Menu[] menus, ArrayList<User> users, ArrayList<Store> stores, Menu menu) {
		
		try {
			if (menus[menu.getDay()]!= null) {
				return false;
			}
		} catch (Exception e) {}
		try {
			if (!this.checkStore(stores, menu.getName()) || !this.checkUserId(users, menu.getUser().getId())) {
				return false;
			} 
		} catch (Exception e) {}
		try {
			if (menus[menu.getDay()-1].getName().equals(menu.getName())) {
				return false;
			}
		} catch (Exception e) {}
		try {
			if (menus[menu.getDay()+1].getName().equals(menu.getName()))
				return false;
		} catch (Exception e) {}
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