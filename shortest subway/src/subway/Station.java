package subway;

public class Station {
	
	String stationName;
	int pre=-1;
	int Visted=0;
	int stationId;
	
	Station(String stationName,int stationId){
		this.stationName=stationName;
		this.stationId=stationId;
	}
	
	public boolean equals(Object obj) {
		Station station=(Station) obj;
		return (this.stationId == station.stationId && this.stationName.equals(station.stationName));
	}
	
	
	public String toString(){
	        return stationName;
	}
	
}
