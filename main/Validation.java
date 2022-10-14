package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import model.*;
import repository.MenuRepository;
import repository.StoreRepository;
import repository.UserRepository;

public class Validation {
   private static final String[] locs = new String[] { "정문", "중문", "후문" }; // 화이트 라벨링 방식 음식점 위치
   private static final String[] submenu_input = new String[] { "1", "2", "3", "로그인", "회원가입", "종료" }; // 화이트 라벨링 방식 도입메뉴 프롬프트 input
   private static final String[] mainmenu_input = new String[] { "1", "2", "3", "4", "5", "음식점 입력", "음식점 검색", "식단표 제출", "서열관리",
         "뒤로 가기" }; // 화이트 라벨링 방식 메인메뉴 프롬프트 input


   /*
    * 식단표 조정 시 사용자가 입력하는 문자열 체크
    * 요일 / 음식 이름 / 락
    * 요일 -> 요일 / 락
    */
   public boolean teamMenuPlannerInputCheck(String str) {
      String[] strArr = str.split("/|->");
      for(int i=0; i<strArr.length; i++) {
         strArr[i] = strArr[i].trim();
      }
      
      return false;
   }
   
   // >/화 / 짜장면
   
   public boolean checkGRule(String str) {
      str = str.trim();   //앞 뒤 공백 제거
      if(str.length() != 0) {   //빈 문자열이 아닌 경우(공백만 입력 포함)
            String[] strArr = str.split("/|->");   //구분자로 자르고
            for(int i=0; i<strArr.length; i++) {
               if(i == 0) {   //첫 번째 인자면
                  
               } 
               char[] cStr = strArr[i].toCharArray();
               
            }
         }
      return false;
   }
   
   /*
    * id 체크 함수
    * 아이디 길이: 6~16, 숫자, 영어 대소문자 가능
    */
   public boolean idCheck(String str) {
      if (str.length() < 6 || str.length() > 16)
         return false;
      for (int i = 0; i < str.length(); i++) {
         if ((str.charAt(i) < 48 || str.charAt(i) > 57) && (str.charAt(i) < 65 || str.charAt(i) > 90)
               && (str.charAt(i) < 97 || str.charAt(i) > 122)) // 0~9 , 영문 대소문자
         {
            return false;
         }
      }
      
      return true;
   }


   public boolean pwCheck(String str) {// 무조건 영문자(대소문 상관 x)와 숫자로 구성됨
      int num = 0;
      int c = 0;

      if (str.length() < 8 || str.length() > 20)
         return false;
      for (int i = 0; i < str.length(); i++) {
         if ((str.charAt(i) < 48 || str.charAt(i) > 57) && (str.charAt(i) < 65 || str.charAt(i) > 90)
               && (str.charAt(i) < 97 || str.charAt(i) > 122)) // 0~9 , 영문 대소문자
         {
            return false;
         } else if (str.charAt(i) >= 48 && str.charAt(i) <= 57)
            num++;
         else
            c++;
      }
      if (num == 0 || c == 0) {
         return false;
      }
      
      return true;
   }

   public boolean userNameCheck(String str, HashMap<String, User> users) {
      if (str.length() < 1 || str.length() > 10)
         return false;
      for (int i = 0; i < str.length(); i++) {
         if (Character.getType(str.charAt(i)) == 5)
            continue; // 한글인지 check
         if ((str.charAt(i) < 48 || str.charAt(i) > 57) && (str.charAt(i) < 65 || str.charAt(i) > 90)
               && (str.charAt(i) < 97 || str.charAt(i) > 122)) // 0~9 , 영문 대소문자
         {
            return false;
         }
      }
      ArrayList<User> userArr = (ArrayList<User>) users.values();
      for(User u:userArr) {
         if(u.getUserName().equals(str))
            return false;
      }
      return true;
   }
   
   public boolean userNameCheck(String str) {   //레포지토리 전용
      if (str.length() < 1 || str.length() > 10)
         return false;
      for (int i = 0; i < str.length(); i++) {
         if (Character.getType(str.charAt(i)) == 5)
            continue; // 한글인지 check
         if ((str.charAt(i) < 48 || str.charAt(i) > 57) && (str.charAt(i) < 65 || str.charAt(i) > 90)
               && (str.charAt(i) < 97 || str.charAt(i) > 122)) // 0~9 , 영문 대소문자
         {
            return false;
         }
      }
      return true;
   }

   public boolean menuNameCheck(String str) {
      if (str.length() < 1 || str.length() > 12)
         return false;
      for (int i = 0; i < str.length(); i++) {
         if (Character.getType(str.charAt(i)) == 5)
            continue; // 한글인지 check
         if (str.charAt(i) == ' ')
            continue; // 공백인지 check
         if ((str.charAt(i) < 48 || str.charAt(i) > 57) && (str.charAt(i) < 65 || str.charAt(i) > 90)
               && (str.charAt(i) < 97 || str.charAt(i) > 122)) // 0~9 , 영문 대소문자
         {
            return false;
         }
      }
      return true;
   }

   public boolean storeNameCheck(String str) {
      if (str.length() < 1 || str.length() > 12)
         return false;
      for (int i = 0; i < str.length(); i++) {
         if (Character.getType(str.charAt(i)) == 5)
            continue; // 한글인지 check
         if (str.charAt(i) == ' ')
            continue;
         if ((str.charAt(i) < 48 || str.charAt(i) > 57) && (str.charAt(i) < 65 || str.charAt(i) > 90)
               && (str.charAt(i) < 97 || str.charAt(i) > 122)) // 0~9 , 영문 대소문자
         {
            return false;
         }
      }
      return true;
   }

   public boolean storeLocCheck(String str) {

      for (int i = 0; i < locs.length; i++) {
         if (str.equals(locs[i]))
            return true;
      }
      return false;
   }

   public boolean submenuInputCheck(String str) { // 도입메뉴 프롬프트 input

      for (int i = 0; i < submenu_input.length; i++) {
         if (str.equals(submenu_input[i]))
            return true;
      }
      return false;
   }

   public String loginInputCheck(String str, HashMap<String, User> users) { // 로그인 할 떄 검사하는 입력 검사하는 함수
      // 나중에 int형으로 바꾸어서 error표시해야할듯
      String[] ArraysStr = str.split(" "); // '/'(슬래쉬 기호) 앞뒤 요소를 공백으로 쪼개어 문자열 배열에 넣을게
      if (ArraysStr.length != 3)// 아이디, '/' , 비밀번호 요소가 3개가 아니면
         return "inputfail";
      if (!idCheck(ArraysStr[0]) && users.containsKey(ArraysStr[0]))
         return "idfail";
      if (!pwCheck(ArraysStr[2])  && users.get(ArraysStr[0]).getPw().equals(str))
         return "pwfail";
      return ArraysStr[0]+" "+ArraysStr[2];
   }

   public int userRegisterInputCheck(String str, HashMap<String, User> users) { // 회원가입 할 떄 검사하는 입력 검사하는 함수
      // 나중에 int형으로 바꾸어서 error표시해야할듯
      String[] ArraysStr = str.split(" "); // '/'(슬래쉬 기호) 앞뒤 요소를 공백으로 쪼개어 문자열 배열에 넣을게
      if (ArraysStr.length != 7)// 아이디, '/' , 비밀번호 , '/' , 사용자이름 , '/', 서열 요소가 7개가 아니면
         return 0;   //문법규칙위반
      if (!idCheck(ArraysStr[0]) && !users.containsKey(ArraysStr[0]))
         return 0;
      if (!pwCheck(ArraysStr[2]))
         return 0;
      if (!userNameCheck(ArraysStr[4], users))
         return 0;
      ArrayList<User> userArr = (ArrayList<User>) users.values();
      for(User u: userArr) {
         if(u.getOrder() == Integer.parseInt(ArraysStr[6]))
               return 1;
      }
      return 2;   //유효한 아이디
   }

   public boolean mainmenuInputCheck(String str) { // 도입메뉴 프롬프트 input

      for (int i = 0; i < mainmenu_input.length; i++) {
         if (str.equals(mainmenu_input[i]))
            return true;
      }
      return false;
   }

   public boolean storeRegisterCheck(String str, HashMap<String ,Store> stores) {
      String[] ArraysStr = str.split(" / "); // '/'(슬래쉬 기호) 앞뒤 요소를 공백으로 쪼개어 문자열 배열에 넣을게
      if (ArraysStr.length != 3)// 음식점 메뉴 , 음식점 이름 ,음식점 위치 요소가 3개가 아니면
         return false;
      ArraysStr[0] = ArraysStr[0].trim();
      ArraysStr[1] = ArraysStr[1].trim();
      if (!menuNameCheck(ArraysStr[0]))
         return false;
      if (!storeNameCheck(ArraysStr[1]))
         return false;
      if (!storeLocCheck(ArraysStr[2]))
         return false;
      if(stores.containsKey(str))   //이름이 같은 음식점이 있는 경우
         return false;
   
      return true;
   }

   /*
    * 음식점 검색 시 입력된 문자열 검증
    * return : -1 -> 오류
    * return : 1 -> 전체 검색
    * return : 2 -> 음식점 이름만 검색
    * return : 3 -> 음식점 이름, 위치 검색
    */
   public int storeSearchCheck(String str) {
      String[] ArraysStr = str.split(" / ");
      
      if (ArraysStr.length == 1 && ArraysStr[0].equals("*")) {
         return 1; // 리턴 1이되면 전체 리스트 보여주기
      }
      
      if (ArraysStr.length == 1) {         //인자가 1개인 경우
         ArraysStr[0] = ArraysStr[0].trim();
         if(this.storeNameCheck(ArraysStr[0])){   //인자 1개가 올바른 입력인 경우
            return 2;
         }
      }
      
      if (ArraysStr.length == 2) {      //인자가 2개인 경우
         if(this.storeNameCheck(ArraysStr[0])) {
            if(ArraysStr[1] == "중문" || ArraysStr[1] == "정문" || ArraysStr[1] == "후문") {
               return 3;
            }
         }
      }
      
      //return 값이 1,2,3중 하나가 아니면 
      return -1;
   }
   
}