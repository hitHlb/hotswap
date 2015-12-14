package controller;

import java.util.concurrent.ConcurrentHashMap;

public class ClassCache {
	private ConcurrentHashMap<String, byte[]> clazzNameToPath = new ConcurrentHashMap<String, byte[]>();
	
	public static ClassCache getInstance(){
		return ClassCacheHolder.instance;
	}
	
	public void saveBytes(String className,byte[] bytes){
		clazzNameToPath.put(className, bytes);
	}
	
	public byte[] getBytes(String className){
		return clazzNameToPath.get(className);
	}
	
	private static class ClassCacheHolder{
		private static ClassCache instance = new ClassCache();
	}
}
