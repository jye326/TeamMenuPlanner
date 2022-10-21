package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import main.Validation;
import model.User;
import repository.UserRepository;

public class UserService {
	UserRepository usRepository;
	Validation v;	//일단 생략
	HashMap<String,User> userMap;
	
	public UserService(UserRepository usRepository, Validation v) {	
		this.usRepository = usRepository;
		this.userMap = this.usRepository.getUserlist();
		this.v = v;
	}

	static Scanner scan = new Scanner(System.in);
	
	
	public int changeOrder() {
		
		ArrayList<User> userList = (ArrayList<User>) usRepository.getUserlist().values();
		
		System.out.println("<서열관리>");
		System.out.println("아이디\t사용자 이름\t서열");
		for(int i = 0; i< userList.size(); i++) {
			System.out.println(userList.get(i).getId()+"\t"+userList.get(i).getUserName()+"\t"+userList.get(i).getOrder());
		}
		System.out.println("서열을 서로 바꿀 사람의 이름을 입력하세요.");
		String name = scan.nextLine();
		
		if(name.equals("exit")) return 0;
		int N = usRepository.getUserlist().get(name).getOrder();	//대상의 현재 서열 N
		usRepository.getUserlist().get(name).setOrder(usRepository.getCurrentUser().getOrder());//현재 유저 서열로 대상서열을 변경
		usRepository.getCurrentUser().setOrder(N);	// N으로 현재 유저의 서열 변경
		
		userList = (ArrayList<User>) usRepository.getUserlist().values();
		System.out.println("서열관리>");
		System.out.println("아이디\t사용자 이름\t서열");
		for(int i = 0; i< userList.size(); i++) {
			System.out.println(userList.get(i).getId()+"\t"+userList.get(i).getUserName()+"\t"+userList.get(i).getOrder());
		}
		System.out.println("정상적으로 바뀌었습니다!");
		return 1;
	}
	
	public int login() {
		System.out.print("로그인>아이디와 비밀번호를 입력해주세요.>");
		String login_input =scan.nextLine();
		login_input = login_input.trim();
		if(login_input.equals("exit")) return 0;
		
		if(v.loginInputCheck(login_input, userMap)=="inputfail") {
			System.out.println("입력형식이 올바르지 않습니다.");
		}else if(v.loginInputCheck(login_input, userMap)=="idfail") {
			System.out.println("아이디가 올바르지 않습니다.");
		}else if(v.loginInputCheck(login_input, userMap)=="pwfail") {
			System.out.println("비밀번호가 올바르지 않습니다.");
		}else{// "ID(공백)PW"를 반환
			String[] IDPW = v.loginInputCheck(login_input, userMap).split(" ");	//IDPW[0] : ID, IDPW[1] : PW
			userMap = usRepository.readFile();	//파일에서 유저 정보를 읽어서 Hashmap에 저장
			if(userMap.containsKey(IDPW[0])) {	//입력한 ID가 존재
				if(userMap.get(IDPW[0]).getPw().equals(IDPW[1])) {	//ID와 비밀번호가 일치하는 경우
					usRepository.setCurrentUser(userMap.get(IDPW[0]));//아이디로 찾아낸 유저를 currentUser로 변경
					System.out.println("로그인 되었습니다!");
					System.out.println("사용자 이름 : "+usRepository.getCurrentUser().getUserName());
					return 1;
				}else {
					System.out.println("존재하지 않는 아이디 또는 비밀번호입니다.");
				}
			}else {
				System.out.println("존재하지 않는 아이디 또는 비밀번호입니다.");
			}
		}
		
		return -1;
	}
	
	public int createAccount() {
		System.out.println("회원가입>사용하실 아이디, 비밀번호, 사용자 이름(닉네임), 서열 순위를 입력해주세요.>");
		String new_Account_input =scan.nextLine();
		
		if(new_Account_input.equals("exit")) return 0;
		
		if(v.userRegisterInputCheck(new_Account_input, userMap) == 0) {
			System.out.println("올바르지 않은 입력 형식입니다. 다시 입력해주세요.");			
		}else if(v.userRegisterInputCheck(new_Account_input, userMap) == 1) {
			System.out.println("이미 사용중인 아이디입니다. 다른 아이디를 입려해주세요.");
		}else if(v.userRegisterInputCheck(new_Account_input, userMap) == 2) {	//	올바른 입력
			String[] str = new_Account_input.split(" ");
			//순서대로 ID/PW/NAME/RANK/LOCKSTACK/ISREPORTED
			User user = new User(str[0],str[2],str[4],Integer.parseInt(str[6]), 0, false);
			usRepository.create(user);
			usRepository.saveFile((ArrayList<User>) usRepository.getUserlist().values());	//추가된 유저정보가 포함된 userlist객체를 ArrayList로 캐스팅해서 파일에 저장
			System.out.println("회원가입이 완료되었습니다!");
			return 1;
		}
		
		return -1;
	}
}
