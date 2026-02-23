package it.polito.tvseriesdb;

import java.util.*;


public class TVSeriesDB {
	HashSet<String> trasmissionList=new HashSet<String>();
	TreeMap<String,Serie> serieMap=new TreeMap<>();
	TreeMap<String,Actor> actorMap=new TreeMap<>();
	TreeMap<String,User> userMap=new TreeMap<>();
	TreeMap<String,Double> rateMap=new TreeMap<>();
	// R1
	/**
	 * adds a list of transmission services.
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param tServices the transmission services
	 * @return number of transmission service inserted so far
	 */
	public int addTransmissionService(String...tServices) {
		for(String servizio:tServices) {
			if(!trasmissionList.contains(servizio)) {
			trasmissionList.add(servizio);
			}
		}
		
		return trasmissionList.size();
	}
	
	
	/**
	 * adds a TV series whose name is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param title the title of the TV Series
	 * @param tService the name of the transmission service 
	 * broadcasting the TV series.
	 * @param genre the genre of the TV Series
	 * @return number of TV Series inserted so far
	 * @throws TSException if TV Series is already inserted or transmission service does not exist.
	 */
	public int addTVSeries(String title, String tService, String genre) throws TSException {
		if(!trasmissionList.contains(tService)) throw new TSException(); //servizio trasmissione non disponibile
	
		if(serieMap.containsKey(title)) throw new TSException();//esiste serie con stesso nome
		Serie s=new Serie(title, tService, genre);
		serieMap.put(title, s);
		return serieMap.size();
	}
	/**
	 * adds an actor whose name and surname is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param name the name of the actor
	 * @param surname the surname of the actor
	 * @param nationality the nationality of the actor
	 * @return number of actors inserted so far
	 * @throws TSException if actor has already been inserted.
	 */
	public int addActor(String name, String surname, String nationality) throws TSException {
		//La coppia nome e cognome dell'attore è univoca??????????????????
		if(actorMap.containsKey(name))throw new TSException();//attore gia' presente
		Actor a=new Actor(name, surname, nationality);
		actorMap.put(name, a);
		return actorMap.size();
	}
	


	/**
	 * adds a cast of actors to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the cast is inserted
	 * @param actors	list of actors to add to a TV series, format of 
	 * 					each actor identity is "name surname"
	 * @return number of actors in the cast
	 * @throws TSException in case of non-existing actor or TV Series does not exist
	 */
	public int addCast(String tvSeriesTitle, String...actors) throws TSException {
		if(!serieMap.containsKey(tvSeriesTitle)) throw new TSException();//serie tv non esiste
		Serie se=serieMap.get(tvSeriesTitle);
		
		for(String nominativo:actors) {
			String[] substring=nominativo.split(" ");
			String name=substring[0];
			
			se.actorList.add(nominativo);
			
			if(!actorMap.containsKey(name)) throw new TSException();//attore non esiste
		}
		
	
	//	se.actorList=actors;
		return actors.length;
	}
      
	// R2
	
	/**
	 * adds a season to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numEpisodes	number of episodes of the season
	 * @param releaseDate	release date for the season (format "gg:mm:yyyy")
	 * @return number of seasons inserted so far for the TV series
	 * @throws TSException in case of non-existing TV Series or wrong releaseDate
	 */
	public int addSeason(String tvSeriesTitle, int numEpisodes, String releaseDate) throws TSException {
		if(!serieMap.containsKey(tvSeriesTitle)) throw new TSException();//serie tv non esiste
		String[] substring=releaseDate.split(":");
				int ggCurr=Integer.parseInt(substring[0]);
				int mmCurr=Integer.parseInt(substring[1]);
				int aaCurr=Integer.parseInt(substring[2]);
		
		Season s=new Season(numEpisodes,releaseDate);
		Serie se=serieMap.get(tvSeriesTitle);
		
		int num=se.seasonList.size();
		if(num>0) {
			String data= se.seasonList.get(num-1).releaseDate;
			String[] stringa=data.split(":");
			int gg=Integer.parseInt(stringa[0]);
			int mm=Integer.parseInt(stringa[1]);
			int aa=Integer.parseInt(stringa[2]);
			if(aaCurr<aa) {throw new TSException();}
			if(aaCurr==aa && mmCurr<mm) {throw new TSException();}
			if(aaCurr==aa && mmCurr==mm && ggCurr<gg) {throw new TSException();}
		}
		se.seasonList.add(s);
		return se.seasonList.size();
	}
	

	/**
	 * adds an episode to a season of a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numSeason	number of season to which add an episode
	 * @param episodeTitle	title of the episode (unique)
	 * @return number of episodes inserted so far for that season of the TV series
	 * @throws TSException in case of non-existing TV Series, season, repeated title 
	 * 			of the episode or exceeding number of episodes inserted
	 */
	public int addEpisode(String tvSeriesTitle, int numSeason, String episodeTitle) throws TSException {
		if(!serieMap.containsKey(tvSeriesTitle)) throw new TSException();//serie tv non esiste
		Serie se=serieMap.get(tvSeriesTitle);
		if(se.seasonList.get(numSeason-1)==null)throw new TSException();//stagione non esiste
		Season s=se.seasonList.get(numSeason-1);
		
		int dimList=s.episodeList.size();//num di episodi inseriti nella lista di episodi
		System.out.print(s.numEpisodes);
		System.out.println(dimList);
		int numEp=s.numEpisodes;
		if(dimList ==numEp)throw new TSException();//si sono già inseriti tutti gli episodi previsti per quella stagione
		
		//se esiste già un episodio con lo stesso nome in quella stagione, viene generata un'eccezione
	    Episode e=new Episode(episodeTitle);
		if(s.episodeList.contains(e)) throw new TSException();
		
	    s.episodeList.add(e);
		
		return s.episodeList.size();
	}

	/**
	 * check which series and which seasons are still lacking
	 * episodes information
	 * 
	 * @return map with TV series and a list of seasons missing episodes information
	 * 
	 */
	public Map<String, List<Integer>> checkMissingEpisodes() {
		TreeMap<String, List<Integer>> map=new TreeMap<>();
		ArrayList<Integer> list=new ArrayList<>();
		
		for(String titoloSerie: serieMap.keySet()) {//x titoli serie
			Serie se=serieMap.get(titoloSerie); // accedo a classe serie
			for(int i=0;i<se.seasonList.size();i++) {
				Season s=se.seasonList.get(i);
				System.out.println("num ep "+ s.numEpisodes+ " stag: "+ i);
				System.out.println("lung list "+s.episodeList.size()+ " stag: "+ i);
				if( s.episodeList.size()!=s.numEpisodes) {
					list.add(i);
				}
			}
			System.out.println("lista:");
			System.out.println(list);
			System.out.println(titoloSerie);
			map.put(titoloSerie, list);
		}
		return map;
	}

	// R3
	
	/**
	 * Add a new user to the database, with a unique username.
	 * 
	 * @param username	username of the user
	 * @param genre		user favourite genre
	 * @return number of registered users
	 * @throws TSException in case username is already registered
	 */
	public int addUser(String username, String genre) throws TSException {
		if(userMap.containsKey(username)) throw new TSException();//esiste serie con stesso nome
		
		User u=new User(username,genre);
		userMap.put(username, u);
		return userMap.size();
	}

	

	/**
	 * Adds a series to the list of favourite
	 * series of a user.
	 * 
	 * @param username	username of the user
	 * @param tvSeriesTitle	 title of TV series to add to the list of favourites
	 * @return number of favourites TV series of the users so far
	 * @throws TSException in case user is not registered or TV series does not exist
	 */
	public int likeTVSeries(String username, String tvSeriesTitle) throws TSException {
		if(!serieMap.containsKey(tvSeriesTitle)) throw new TSException();//serie tv non esiste
		if(!userMap.containsKey(username)) throw new TSException();//utente non esiste
		
		Serie se=serieMap.get(tvSeriesTitle);
		
		User u=userMap.get(username);
		if(	u.seriePrefeList.contains(se)) throw new TSException();	//serie gia' nella lista

		u.seriePrefeList.add(se);
		System.out.println("lista prefv: ");
		System.out.println(username +tvSeriesTitle+ u.seriePrefeList );
		
		return 	u.seriePrefeList.size();
	}
	
	/**
	 * Returns a list of suggested TV series. 
	 * A series is suggested if it is not already in the user list and if it is of the user's favourite genre 
	 * 
	 * @param username name of the user
	 * @throws TSException if user does not exist
	 */
	public List<String> suggestTVSeries(String username) throws TSException {
		if(!userMap.containsKey(username)) throw new TSException();//utente non esiste
		ArrayList<String>suggestList=new ArrayList<>();
		User u=userMap.get(username);
		
		for(String titoloMap: serieMap.keySet()) {
			Serie se=serieMap.get(titoloMap);
			if(!suggestList.contains(titoloMap)) {//Una serie TV viene suggerita all'utente se non è ancora nella sua lista di serie tv preferite
				if(u.genre.equals(se.genre)) {
					suggestList.add(titoloMap);
				}
			}
		}
		if(suggestList.size()==0) {
			suggestList.add("");
		}
		return suggestList;
	}
	
	//R4 

	/**
	 * Add reviews from a user to a tvSeries
	 * 
	 * @param username	username of the user
	 * @param tvSeries		name of the participant
	 * @param score		review score assigned
	 * @return the average score of the series so far from 0 to 10, extremes included
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double addReview(String username, String tvSeries, int score) throws TSException {
		if(!userMap.containsKey(username)) throw new TSException();//utente non esiste
		if(!serieMap.containsKey(tvSeries)) throw new TSException();//serie tv non esiste
		if(score>10 || score<0) {
			throw new TSException();//punteggio non valido
		}
		
		Serie se=serieMap.get(tvSeries);
		User u=userMap.get(username);
		TreeMap<String, Integer> ratingsMappa = new TreeMap<>();
		ratingsMappa.put(tvSeries, score);
		u.ratingsMap=ratingsMappa;
		System.out.println("mappa punteggi: ");
			System.out.println(username+ratingsMappa + score);
		
		int sum=0;
		int contatore=0;
		for(String nomeUser: userMap.keySet()) {
			User us=userMap.get(nomeUser);
			if(us.ratingsMap.containsKey(tvSeries)) {
				contatore++;
				int punteggio=us.ratingsMap.get(tvSeries);
				sum+=punteggio;
			}
		}
		int media=sum/contatore;
	
		//System.out.println("media "+media);
		return media;	
	}

	/**
	 * Average rating of tv series in the favourite list of a user
	 * 
	 * @param username	username of the user
	 * @return the average score of the series in the list of favourites of the user
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double averageRating(String username) throws TSException {
		User u=userMap.get(username);
		if(u.ratingsMap.size()==0) {
			return 0;
		}
	
		int contatore=0,sum=0;
		for(String nomeUser: userMap.keySet()) {
			User us=userMap.get(nomeUser);
			for(Serie se: us.seriePrefeList) {
				String titolo=se.title;
				if(us.ratingsMap.containsKey(titolo)) {
					contatore++;
					int punteggio=us.ratingsMap.get(titolo);
					sum+=punteggio;
			   }	
			}
		}
		int media= sum/ u.seriePrefeList.size();
		System.out.println(username + media);
		
		return media;
	}
	
	// R5

	/**
	 * Returns most awaited season of a tv series using format "TVSeriesName seasonNumber", the last season of the best-reviewed TV series who has not come out yet with
	 * respect to the current date passed in input. In case of tie, select using alphabetical order. Date format: dd::mm::yyyy
	 * 
	 * @param currDate	currentDate
	 * @return the most awaited season of TV series who still has to come out
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public String mostAwaitedSeason(String currDate) throws TSException {
		//	if() throws TSException ;
		return null;
	}
	
	/**
	 * Computes the best actors working in tv series of a transmission service passed
	 * in input. The best actors have worked only in tv series of that transmission service
	 * with average rating higher than 8.
	 * @param transmissionService	transmission service to consider
	 * @return the best actors' names as "name surname" list
	 * @throws TSException	in case of transmission service not in the DB
	 */
	public List<String> bestActors(String transmissionService) throws TSException {
	  if(!trasmissionList.contains(transmissionService)) throw new TSException();
		  
	   ArrayList<String>bestActors=new ArrayList<>();
	   
	 //troviamo serie con valutazione media superiore a 8
	   int contatore=0,sum=0;
	  // double media;
	   for(String titolo: serieMap.keySet()) {
			Serie se=serieMap.get(titolo);
			for(String nomeUser: userMap.keySet()) {
				User us=userMap.get(nomeUser);
				if(us.ratingsMap.containsKey(titolo)) {
					contatore++;
					int punteggio=us.ratingsMap.get(titolo);
					sum+=punteggio;
			   }	
			}
			if(contatore>0) {
				 double media= sum/contatore;
				 if(media>=8) {
	        	    rateMap.put(titolo, media);
	        	    media=0;
	             }
			}
	        sum=0;
	        contatore=0;
	        //media=0;
	   }
	   System.out.println(rateMap);
			
			
			
			
			
	  for(String titolo:rateMap.keySet()) {
		  Serie se=serieMap.get(titolo);
		  if(se.tService.equals(transmissionService)) {
			  
						for(int i=0;i<se.actorList.size();i++) {
							bestActors.add(se.actorList.get(i));
						}
					}
			  }
		  
	  
	  if(bestActors.size()==0) bestActors=null;
		return bestActors;
	}

	
}
