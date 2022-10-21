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

import main.Validation;
import model.User;

/*유저 데이터 파일 관련*/
public class UserRepository implements Repository<User> {
	
	private User currentUser;	//현재 사용자
	private HashMap <String, User> userMap;
	private Validation valid;
	/* 텍스트 파일 디렉토리, ##수정 필수## */
	File file = new File(".\\src\\data\\userList.txt");
	
	
	public UserRepository(Validation valid) {
		this.valid = valid;
		userMap = this.readFile();
	}

	/*
	 * 파일 생성 및 초기화 생성되면 TRUE 이미 있거나 생성이 안되면 FALSE
	 */
	public boolean createFile() {
		try {
			return file.createNewFile();
		} catch (IOException e) {
			return false;
		}
	}

	/* 파일 읽기 */
	   // ArrayList -> HashMap 으로 수정
	   public HashMap<String,User> readFile() {
	      HashMap<String,User> users = new HashMap<>();         //ArrayList<User> users = new ArrayList<>();
	      try {
	         Scanner sc = new Scanner(file);
	         int n = Integer.parseInt(sc.nextLine());
	         int i = 0;
	         while (sc.hasNextLine()) {
	            String[] str = sc.nextLine().split("/");
	            
	            User user = new User(str[0].trim(), str[1].trim(), str[2].trim(), Integer.parseInt(str[3].trim()), Integer.parseInt(str[4].trim()), str[5].trim().equals("1"));
	            
	            
	            ArrayList<User> userArr = new ArrayList<User>();
	            userArr.addAll(users.values());
	            for(User u:userArr) {
	               if(u.getUserName().equals(user.getUserName()) || u.getOrder() == user.getOrder())
	                  throw new IndexOutOfBoundsException(); //1
	            }
	            
	            if(valid.idCheck(user.getId()) && !users.containsKey(user.getId())
	                  && valid.pwCheck(user.getPw()) && valid.userNameCheck(user.getUserName())) {
	               users.put(user.getId(), user);   // users.add(user);
	             //  System.out.println("추가");
	            }
	            else 
	               throw new NullPointerException();//2
	               
	         }
	      } catch (FileNotFoundException e1) { 
	         createFile();
	      } catch (IndexOutOfBoundsException e2) {//1
		         System.out.println("1에 문제가 있습니다. 강제 종료합니다.");
		         System.exit(0);
		      }
	      catch (NullPointerException e3) {//2
		         System.out.println("u2에 문제가 있습니다. 강제 종료합니다.");
		         System.exit(0);
		      }
	      
	      catch (Exception e4) {
	         System.out.println("userList 텍스트 파일에 문제가 있습니다. 강제 종료합니다.");
	         System.exit(0);
	      }
	      userMap = users;
	      return users;
	   }

	/* 파일 쓰기 */
	
	public void saveFile(ArrayList<User> users) {
		try {
			BufferedWriter fiOut = new BufferedWriter(new FileWriter(file));

			fiOut.write(Integer.toString(users.size()));
			fiOut.newLine();

			for (User user : users) {
				String str = (user.getId() + "/" + user.getPw() + "/" + user.getUserName() + "/" + user.getOrder()+ "/" + user.getLockStack() + "/" + user.getIsReport());
				fiOut.write(str);
				fiOut.newLine();
			}
			fiOut.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean create(Object o) {
		User user;
		user = (User)o;
		userMap.put(user.getId(), user);
		return true;
	}
	
	@Override
	public boolean delete(Object o) {
		User user = (User)o;
		
		return this.userMap.remove(user.getId(), user);
	}

	@Override
	public List findAll() {
		List<User> list = (List<User>) this.userMap.values();
		return list;
	}
	
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	public void changeCurrentUsersOrder(int i) {
		currentUser.setOrder(i);
	}
	
	public HashMap<String, User> getUserlist() {
		return userMap;
	}

	public void setUserlist(HashMap<String, User> userMap) {
		userMap = userMap;
	}
	
	public HashMap<String, User> getUserMap() {
		return this.readFile();
	}

	public void setUserMap(HashMap<String, User> userMap) {
		userMap = userMap;
	}

}