package com.wz.tool;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.javafx.collections.MappingChange.Map;

import sun.net.www.http.KeepAliveCache;

public class KeyList extends ArrayList<SelectionKey>{
	private ArrayList<String> nameList=new ArrayList<>();
	
	public KeyList()
	{
		super();
	}
	
	public boolean add(SelectionKey e) {
		nameList.add((String) e.attachment());
		return super.add(e);
	}
	
	public boolean remove(SelectionKey key) {
		nameList.remove(key.attachment());
		return super.remove(key);
	}
	
	
	public  boolean IsOnline(String username)
	{
		return nameList.contains(username);
	}
	
	public SelectionKey getUserKey(String username)
	{
		return get(nameList.indexOf(username));
	}
	
	
	
	
	
	
	
}
