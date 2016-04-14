package class3.hw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private final int teamN;
	private HashMap<String, Integer> name2id;
	private HashMap<Integer, String> id2name;
	private int[]  w, l, r;
	private int[][] left;
	private FordFulkerson ff ;
	
	//for maxflow calculation use
	private int vertexNumber;// = (teamN-1)*(teamN-2) / 2 + (teamN-1) + 2;
	private int edgeNumber;// = 3 * (teamN-1)*(teamN-2) / 2  + (teamN-1);
	private int source, sink;
	private int maxFlowValue;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseballElimination division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4.txt");
	    
		//for (String team : division.teams()) {
		String team = "Montreal";
			System.out.println("team = " + team);
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	   // }
		
	}
	
	public BaseballElimination(String filename)	{                 // create a baseball division from given filename in format specified below
		In in = new In(filename);
		teamN = in.readInt();
		name2id = new HashMap<String, Integer>();
		id2name = new HashMap<Integer, String>();
		
		r = new int[teamN];
		w = new int[teamN];
		l = new int[teamN];
		left = new int[teamN][teamN];
		
		for (int row = 0; row < teamN; row++) {
			String tName = in.readString();
			w[row] = in.readInt();
			l[row] = in.readInt();
			r[row] = in.readInt();
			for (int j = 0; j < teamN; j++)
				left[row][j] = in.readInt();
			name2id.put(tName, row);
			id2name.put(row, tName);
		}
		
		vertexNumber = (teamN-1)*(teamN) / 2 + (teamN) + 2;
		edgeNumber = 3 * (teamN-1)*(teamN) / 2  + (teamN - 1);
		source = vertexNumber - 2;
		sink = source + 1;
	}
	
	public int numberOfTeams() {                 // number of teams
		return teamN;
	}
	
	public Iterable<String> teams()   {                       // all teams
		return name2id.keySet();
	}
	
	public int wins(String team) {            // number of wins for given team
		if (team == null || teamId(team) < 0) throw new java.lang.IllegalArgumentException();
		return w[teamId(team)];
	}
	
	public int losses(String team)      {              // number of losses for given team
		if (team == null || teamId(team) < 0) throw new java.lang.IllegalArgumentException();
		return l[teamId(team)];
	}
	
	public int remaining(String team)  {               // number of remaining games for given team
		if (team == null || teamId(team) < 0) throw new java.lang.IllegalArgumentException();
		return r[teamId(team)];
	}
	
	public int against(String team1, String team2)   { // number of remaining games between team1 and team2
		if (team1 == null || teamId(team1) < 0 || team2 == null || teamId(team2) < 0 ) throw new java.lang.IllegalArgumentException();
		return left[teamId(team1)][teamId(team2)];
	}
	
	public boolean isEliminated(String team)  { // is given team eliminated?
		if (team == null || teamId(team) < 0) throw new java.lang.IllegalArgumentException();
		//FordFulkerson
		
		int teamId = teamId(team);
		
		if (simpleCheck(teamId)!=null) return true;
		maxFlowValue = 0;
		tryElimination(teamId(team));
		return ff.value() != (double) maxFlowValue;
	}
	
	/**
	 * Test if any team has won more games than the 
	 * team + remaining
	 * */
	private String simpleCheck(int teamId) {
		//check the simple case!
		for (int row = 0; row < teamN; row++)
			if (w[teamId] + r[teamId] - w[row] < 0) return id2name.get(row);
		return null;
	}
	
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team)  {
		
		if (team == null || teamId(team) < 0) throw new java.lang.IllegalArgumentException();
		int judgeTeam = teamId(team);
		if (simpleCheck(judgeTeam)!=null) return Arrays.asList(simpleCheck(judgeTeam));
		//ArrayList<String> teams; 
		maxFlowValue = 0;
		tryElimination(judgeTeam);
		
		
		if (Math.round(ff.value()) == Math.round(maxFlowValue)) return null;
		ArrayList<String> teams = new ArrayList<String>();
		for (int i = 0; i < teamN; i++)
			if (ff.inCut(i)) {
				if (judgeTeam != i)
				//int inCutTeamId = i < judgeTeam? i : i+1;
				teams.add(teamName(i));
			}
		if (teams.size() == 0) return null;
		return teams;
	}
	
	// reutrn -1 if a team name does not exist
	private int teamId(String name) {
		if (!name2id.containsKey(name)) return -1;
		return name2id.get(name);
	}
	
	private String teamName(int id) {
		return id2name.get(id);
	}
	
	private void tryElimination(int team) {
		FlowNetwork fn = createFlowNetwork(team, vertexNumber, source, sink);
		ff = new FordFulkerson(fn, source, sink);
		//System.out.println(ff.toString());
	}
	
	private FlowNetwork createFlowNetwork(int judgeTeam, int vertexNumber, int source, int sink) {
		FlowNetwork res = new FlowNetwork(vertexNumber);
		int gameVertex = teamN;
		
		for (int row = 0; row < teamN; row++) {
			for (int col = 0; col < teamN; col++) {
				if (col >= row) continue;
				// add two edges for tie col-row
				if (col != judgeTeam && row != judgeTeam) {
					res.addEdge(new FlowEdge(source, gameVertex, left[row][col]));
					res.addEdge(new FlowEdge(gameVertex, row, left[row][col]));
					res.addEdge(new FlowEdge(gameVertex, col, left[row][col]));
					maxFlowValue +=  left[row][col];
				} else {
					res.addEdge(new FlowEdge(source, gameVertex, 0));
					res.addEdge(new FlowEdge(gameVertex, row, 0));
					res.addEdge(new FlowEdge(gameVertex, col, 0));
				}
				gameVertex++;
			}
			
			//System.out.println("w[judgeTeam] + r[judgeTeam] - w[row] = " + (w[judgeTeam] + r[judgeTeam] - w[row]));
			res.addEdge(new FlowEdge(row, sink, w[judgeTeam] + r[judgeTeam] - w[row]));
		}
		//System.out.println(res.toString());
		return res;
	}
	
	/*
	//given a team id, get the team vertex in the flow network
	private int getTeamVertex(int team, int judgeTeam) {
		if (team < judgeTeam) return team;
		else return team -1;
	}
	
	//given two team ids, return the game tie's vertex in flow network
	private int getGameVertex(int team1, int team2, int judgeTeam) {
	}*/
}
