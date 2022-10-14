package repository;

import java.io.*;
import java.util.*;

import model.User;

public interface Repository<T> {
	public boolean createFile() throws IOException;
	public HashMap<String, T> readFile() throws Exception;
	public void saveFile(ArrayList<T> obj);
	public boolean create(Object o);
	public boolean delete(Object o);
	public List findAll();
}
