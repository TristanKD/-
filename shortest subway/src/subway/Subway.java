package subway;

import java.io.*;
import java.util.*;

public class Subway {
	static Map<String,Station> map=new HashMap<>();		//站点名到站点的映射
	static Map<Edge,String> eLine=new HashMap<>();			//边到所属线路的映射
	
	static Map<String,List<Station>> Lines=new HashMap<>();
	public static void main(String args[]) throws Exception {
		Scanner input=new Scanner(System.in);
		System.out.println("查询线路请按1，查询最短路径请按2");
		int u=input.nextInt();
		String filePath="C:\\Users\\DELL\\Desktop\\地铁线路信息.txt";
		if(u==1) {
			Graph graph=loadMap(filePath);
			System.out.println("请输入要查询的线路名：");
			String s=input.next();
			List<Station> a=Lines.get(s);
			if(a==null) {
				System.out.println("无此线路");
				return;
			}
			else {
				System.out.println(a);
			}
			return;
		}
		System.out.println("请输入 起点终点");
		int count=0;
		String start=input.next();
		String end=input.next();
		Graph graph=loadMap(filePath);
		List<Station> shortest= getShortestPath(start,end,graph);
		count=shortest.size();
		String preLine,inLine;
		preLine=eLine.get(new Edge(shortest.get(0),shortest.get(1)));
		inLine=eLine.get(new Edge(shortest.get(1),shortest.get(2)));
		System.out.println("经过"+count+"站");
		System.out.print("乘坐"+preLine+" ");
		System.out.print(shortest.get(0));
		System.out.print("->"+shortest.get(1));
		if(!preLine.equals(inLine))
			System.out.printf("\n换乘"+inLine);
		System.out.print("->"+shortest.get(2));
		for(int i=3;i<shortest.size();i++) {
			preLine=inLine;
			inLine=eLine.get(new Edge(shortest.get(i-1),shortest.get(i)));
			if(!preLine.equals(inLine))
				System.out.printf("\n换乘"+inLine);
			System.out.print("->"+shortest.get(i));
		}
	}
	
	
	
	
	public static List<Station> getShortestPath(String start, String end, Graph graph) throws Exception {
		List<Station> list=new ArrayList<>();
		if(!map.containsKey(start)) {
			System.out.println("您输入错误起始站，没有该起始站");
			System.exit(0);
			
		}
		if(!map.containsKey(end)) {
			System.out.println("您输入错误终点站，没有该终点站");
			System.exit(0);
		}
		if(start.equals(end)) {
			System.out.println("您已在该站");
			System.exit(0);
		}
		
		int pre=0,in=1;
		Station Start=map.get(start);
		Station End=map.get(end);
		List<Station> oper=new ArrayList<>();
		oper.add(Start);
		Start.Visted=1;
		List<List<Edge>> edges=graph.edges;
		while(pre!=in) {
			List<Edge> vedges=edges.get(oper.get(pre++).stationId);
			int flag=0;
			for(int i=0;i<vedges.size();i++) {
				if(vedges.get(i).end.Visted==1) {
					continue;
				}
				Station now=vedges.get(i).end;
				now.Visted=1;
				now.pre=pre-1;
				oper.add(now);
				in++;
				if(now.equals(End)) {
					flag=1;
					break;
				}
			}
			if(flag==1)
				break;
		}
		if(pre==in)
			return null;
		else {
			in--;
			Stack<Station> stack=new Stack<>();
			Station tmp;
			while((tmp=oper.get(in)).pre!=-1) {
				stack.push(tmp);
				in=tmp.pre;
			}
			stack.push(Start);
			while(!stack.isEmpty()) 
				list.add(stack.pop());
			
		}
		return list;
	}
	
	
	
	
	
	public static Graph loadMap(String filePath) {
		List<Station> vertices = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();
		BufferedReader bufferedReader=null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
			String lineTxt;
			int stationId=0;			//给读入的站点按顺序编号
			while((lineTxt = bufferedReader.readLine())!=null) {	
				String[] strs = lineTxt.split(" ");			//分离出各站点名
				List<Station> f=new ArrayList<>();
				for(int i=1;i<strs.length;i++) {			
					if(map.containsKey(strs[i])); 			//将不重复站点名创建节点
					else {
						Station s=new Station(strs[i],stationId++);
						vertices.add(s);
						map.put(strs[i], s);
					}
					f.add(new Station(strs[i],stationId));
				}
				Lines.put(strs[0], f);
			}
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));		
			while((lineTxt = bufferedReader.readLine())!=null) {	
				String[] strs = lineTxt.split(" ");	
				for(int i=1;i<strs.length-1;i++) { 		
					Edge e=new Edge(map.get(strs[i]),map.get(strs[i+1]));
					edges.add(e);
					eLine.put(e, strs[0]);
					e=new Edge(map.get(strs[i+1]),map.get(strs[i]));
					edges.add(e);
					eLine.put(e, strs[0]);
				}					
			}
		bufferedReader.close();
		}catch(Exception e) {
			System.out.println("failed to read map");
			System.exit(0);
		}
		return new Graph(vertices,edges);
	}
	
}
