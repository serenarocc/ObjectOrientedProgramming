package it.polito.tvseriesdb;

import java.util.ArrayList;

public class Season {

	int numEpisodes;
	String releaseDate;
	
	ArrayList<Episode>episodeList=new ArrayList<>();
	
	public Season( int numEpisodes, String releaseDate) {
		super();
		//this.tvSeriesTitle = tvSeriesTitle;
		this.numEpisodes = numEpisodes;
		this.releaseDate = releaseDate;
	}
	public String getReleaseDate() {
		return releaseDate;
	}

}
