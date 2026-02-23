package it.polito.tvseriesdb;
import java.util.*;
public class Serie {

	String title;
	String tService;
	String genre;

	ArrayList<Season>seasonList=new ArrayList<>();
	ArrayList<String>actorList=new ArrayList<>();
	
	public Serie(String title, String tService, String genre) {
		super();
		this.title = title;
		this.tService = tService;
		this.genre = genre;
	}
	TreeMap<String, Integer> ratingsMap;
	
	int getAverageRating () {
		return ratingsMap.values().stream()
				.mapToInt(v -> v).sum() / ratingsMap.size();
	}
	
}
