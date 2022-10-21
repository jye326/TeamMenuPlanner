package service;

import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import main.Validation;
import model.Store;
import repository.StoreRepository;

public class StoreService {
	StoreRepository sr;
	Validation v;
	
	public StoreService(StoreRepository sr, Validation v) {
		this.sr = sr;
		this.v = v;
	}

	static Scanner scan = new Scanner(System.in);
	
	public void registerStore() {
		while(true) {
			System.out.println("등록할 음식점 정보를 입력하세요.");
			System.out.println("ex) 음식점 이름 / 음식점 위치 / 음식점 메뉴(카테고리)");
			String str = scan.nextLine();
			
			if(str.equals("exit")) return;
			
			if(v.storeRegisterCheck(str, sr.getStoreMap())) {		//입력 문자열이 문법 규칙과 중복된 음식점 데이터가 없음을 만족하는 경우
				sr.create(str);
				System.out.println(str + "이(가) 등록되었습니다.");	
			}else System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		}
	}
	
	/*
	 * 음식점 검색 함수
	 * '*' 혹은 음식점 이름 혹은 음식점 이름 / 음식점 위치 입력 가능.
	 */
	public void searchStore() {
		while(true) {
			System.out.println("찾으시는 음식점 정보를 입력하세요.");
			String str = scan.nextLine();
			
			if(str.equals("exit")) return;	//뒤로가기
			
			str = str.trim();
			String[] aStr = str.split("/"); 
			int result = v.storeSearchCheck(str);
			
			if(result == 1){	// 전체 리스트
				List<Store> arr = sr.findAll();
				for(Store store : arr) {
					System.out.println(store);
				}
	
			}else if(result == 2){	//인자가 1개인 경우(이름만 입력)
				Store store = sr.findByStoreName(str);
				if(store != null)
					System.out.println(store);
				else
					System.out.println("존재하지 않는 데이터 입니다. 다시 시도해주세요.");
				
			}else if(result ==3){	//인자가 2개인 경우(이름, 위치 입력)
				Store store = sr.findByStoreNameAndLocation(aStr[0], aStr[1]);
				if(store != null)	
					System.out.println(store);
				else
					System.out.println("존재하지 않는 데이터 입니다. 다시 시도해주세요.");
			}
		}
		
	}
}
