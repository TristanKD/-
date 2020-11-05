package subway;

public class Edge {
	Station start;
	Station end;
	Edge(Station start,Station end){
		this.start=start;
		this.end=end;
	}
	
	public int hashCode() {
		int hash=start.stationId;
		hash=hash*131+end.stationId;
        return hash;
    }
	
	public boolean equals(Object obj) {
		Edge edge=(Edge) obj;
		return (this.start.equals(edge.start) && this.end.equals(edge.end));
	}
}
