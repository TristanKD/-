package subway;

import java.util.*;

public class Graph {
	public List<Station> vertices=new ArrayList<>();			//站点集
	public List<List<Edge>> edges=new ArrayList<>();   		//邻接矩阵
	
	public Graph() {
		
	}
	public Graph(List<Station> vertices, List<Edge> edges) {
		for(int i=0;i<vertices.size();i++)
			addVertex(vertices.get(i));
		createAdjacencyLists(edges);
	}
	
	
	
	public void createAdjacencyLists(List<Edge> Edges){		//建立邻接矩阵
		for(int i=0;i<Edges.size();i++) 
			edges.get(Edges.get(i).start.stationId).add(Edges.get(i));
	}
	
	
	public void addVertex(Station v) {					//添加节点
		vertices.add(v);
		edges.add(new ArrayList<Edge>());
	}
}
