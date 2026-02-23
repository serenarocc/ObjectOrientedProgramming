package it.polito.tvseriesdb;

import java.util.ArrayList;
import java.util.TreeMap;

public class User {
	String username;
	String genre;
	
	ArrayList<Serie>seriePrefeList=new ArrayList<>();
	
	public User(String username, String genre) {
		super();
		this.username = username;
		this.genre = genre;
	}
	

	TreeMap<String, Integer> ratingsMap=new TreeMap<>();
	
	int getAverageRating () {
		return ratingsMap.values().stream()
				.mapToInt(v -> v).sum() / ratingsMap.size();
	}
	
	TreeMap<String, Integer> getSkillsRatingsMap() {
		return ratingsMap;
	}
	
}
