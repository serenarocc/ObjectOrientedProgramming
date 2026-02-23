package it.polito.tvseriesdb;

import java.util.TreeMap;

public class Rating {
	String username;
	String tvSeries;
	int score;
	
	TreeMap<String,Integer> ratingsMap=null;
	
	public Rating(String username, String tvSeries, int score) {
		super();
		this.username = username;
		this.tvSeries = tvSeries;
		this.score = score;
	}
	int getAverageRating() {
		return ratingsMap.values().stream()
				.mapToInt(v->v).sum()/ratingsMap.size();
		}
	
}
