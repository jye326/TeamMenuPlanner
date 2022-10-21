package repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;	
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Store;

/*유저 데이터 파일 관련*/
public class StoreRepository implements Repository<Store> {
	
	private HashMap<String, Store> storeMap;
	
	/*텍스트 파일 디렉토리, ##수정 필수##*/
	private File file = new File("data/storeList.txt");
	
	
	public StoreRepository() {
		storeMap = this.readFile();
	}

	public HashMap<String, Store> getStoreMap() {
		return storeMap;
	}

	public void setStoreMap(HashMap<String, Store> storeMap) {
		this.storeMap = storeMap;
	}

	public Store findByStoreName(String name) {
		return this.storeMap.get(name);
	}
	
	public Store findByStoreNameAndLocation(String name, String loc) {
		Store st = this.storeMap.get(name);
		if(st != null) {
			if(st.getLocation().equals(loc)) return st;
		}
		
		return null;
	}
	@Override
	public boolean create(Object o) {
		Store store = (Store)o;
		this.storeMap.put(store.getStoreName(), store);
		
		return true;
	}

	@Override
	public boolean delete(Object o) {
		Store store = (Store)o;
		return false;
	}

	@Override
	public List<Store> findAll() {
		return null;
	}
	
	/*
	 * 파일 생성 및 초기화
	 * 생성되면 TRUE
	 * 이미 있거나 생성 안되면 FALSE
	 */
	public boolean createFile() {
		try {
			return file.createNewFile();
		} catch (IOException e) {
			return false;
		}
	}

	/*파일 읽기*/
	public HashMap<String, Store> readFile(){
		try {
			Scanner sc = new Scanner(file);
			HashMap<String, Store> storeMaptemp = new HashMap<String, Store>();
			while (sc.hasNextLine()) {
				String[] str = sc.nextLine().split("/");
				Store store = new Store(str[0].trim(), str[1].trim(), str[2].trim());
				storeMaptemp.put(store.getStoreName() ,store);
			}
			storeMap = storeMaptemp;
			return storeMap;

		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
			createFile();
		}
		catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		
		return null;
	}

	/*파일 쓰기*/
	public void saveFile(ArrayList<Store> stores) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			for (Store store:stores) {
				String str = (store.getStoreName() + "/" 
						+ store.getLocation() + "/" 
						+ store.getMenu());
				bw.write(str);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}