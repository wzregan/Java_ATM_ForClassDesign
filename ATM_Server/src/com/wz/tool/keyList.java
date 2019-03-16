package com.wz.tool;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.javafx.collections.MappingChange.Map;

public class keyList {
	private static HashMap<String,SelectionKey> keyMap=new HashMap<>();
	public void addKey(String username,SelectionKey key)
	{
		keyMap.put(username, key);
	}
	
	public boolean IsLoging(String username)
	{
		return keyMap.containsKey(username);
	}

}
