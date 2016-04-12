import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private final int N;
	private final String[] teams;
	private final int[] r;
	private final int[] l;
	private final int[] w;
	private final int[][] g;
	private final int maxWin; 
	private final String maxWinTeam;
	private static final int MAX_CAPACITY = Integer.MAX_VALUE;
	private boolean[] isCached;
	private boolean[] hasEleminated;
	private HashMap<Integer, HashSet<String>> subSets;
	private HashMap<String, Integer> name;
	
	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/homewok/src/hw3/src/baseball/teams5.txt");
	    
		//division.isEliminated("Philadelphia");
		
		for (String team : division.teams()) {
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
	    }
	}
	
	
	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename)   {
		In in = new In(filename);
		//int count = 0;
		int max = Integer.MIN_VALUE;
		String maxteam = "";
		name = new HashMap<String, Integer>();
		try {
			this.N = in.readInt();
			g = new int[N][N];
			r = new int[N];
			l = new int[N];
			w = new int[N];
			teams = new String[N];
			isCached = new boolean[N];
			this.hasEleminated = new boolean[N];
			this.subSets = new HashMap<Integer, HashSet<String>>();
			if (N < 0) throw new IllegalArgumentException("Number of team in a division must be nonnegative");
			for (int count = 0; count < N; count++) {
				teams[count] = in.readString();
				name.put(teams[count],count);
				w[count] = in.readInt();
				l[count] = in.readInt();
				r[count] = in.readInt();
				for (int j = 0; j < N; j++)
					g[count][j] = in.readInt();
				if (w[count] > max) { max = w[count]; maxteam = teams[count];}
			}
			maxWin = max;
			maxWinTeam = maxteam;
		} catch (NoSuchElementException e) {
            throw new InputMismatchException("Invalid input format in Digraph constructor");
        }
		
		printNameMapping();
	}
	
	// number of teams
	public int numberOfTeams() { 
		return N;
	}
	
	public Iterable<String> teams()     {                           // all teams
		return name.keySet();
	}
	
	public int wins(String team)       {               // number of wins for given team
		int teamnumber = this.getTeamNumber(team);
		return w[teamnumber];
	}
	
	public int losses(String team)     {               // number of losses for given team
		int teamnumber = this.getTeamNumber(team);
		return l[teamnumber];
	}
	
	public int remaining(String team) {// number of remaining games for given team
		int teamnumber = this.getTeamNumber(team);
		return r[teamnumber];
	}
	
	public int against(String team1, String team2)  {// number of remaining games between team1 and team2
		int teamnumber1 = this.getTeamNumber(team1);
		int teamnumber2 = this.getTeamNumber(team2);
		return g[teamnumber1][teamnumber2];
	}
	
	public boolean isEliminated(String team)  {// is given team eliminated?
		int teamX = getTeamNumber(team);
		if ( (w[teamX] + r[teamX]) < maxWin ){ 
			isCached[teamX] = true;
			hasEleminated[teamX] = true;
			HashSet<String> subset = new HashSet<String>();
			subset.add(maxWinTeam);
			subSets.put(teamX, subset);
			return true;
		}

		//see if this team has been calculated before
		if (this.isCached[teamX])
			return this.hasEleminated[teamX];

		
		int source = 0;
		int sink = N*(N-1)/2 + N + 1;
		
		int numberofpairs = N*(N-1)/2;
		FlowNetwork fn = new FlowNetwork(sink + 1);
		
		int tienumber = 1;
		
		int xWinLimit = w[teamX] + r[teamX];
		
		//keeps track of all right side nodes that have been added to flownetwork
		HashSet<Integer> added = new HashSet<Integer>();
		//to add tie nodes
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				int iNodeNumber = i + numberofpairs;
				int jNodeNumber = j + numberofpairs;
				int capacity = 0;
				if (i == teamX || j == teamX)
					continue;
				
				capacity = g[i][j];
				
				
				FlowEdge f = new FlowEdge(source, tienumber, capacity);
				fn.addEdge(f);
				
				if (!added.contains(iNodeNumber)) {
					FlowEdge f1;
					if (xWinLimit - w[i] >0)
						f1 = new FlowEdge(iNodeNumber, sink, xWinLimit - w[i]);
					else
						f1 = new FlowEdge(iNodeNumber, sink, 0);
					
					fn.addEdge(f1);
					added.add(iNodeNumber);
				}
				
				if (!added.contains(jNodeNumber)) {
					FlowEdge f2;
					if (xWinLimit - w[j] >0)
						f2 = new FlowEdge(jNodeNumber, sink, xWinLimit - w[j]);
					else
						f2 = new FlowEdge(jNodeNumber, sink, 0);
					fn.addEdge(f2);
					added.add(jNodeNumber);
				}
				
				fn.addEdge(new FlowEdge(tienumber, iNodeNumber, MAX_CAPACITY));
				fn.addEdge(new FlowEdge(tienumber, jNodeNumber, MAX_CAPACITY));
				
				tienumber++;
			}
		}
		FordFulkerson ff = new FordFulkerson(fn, source, sink);
		
		
		
		//System.out.println(fn.toString());
		
		boolean res = false;
		
		HashSet<String> subset = new HashSet<String>();
		
		for (int i = 0; i < N; i++ ) {
			if (i == teamX) continue;
			if (ff.inCut(i + numberofpairs)) { res = true; 
			//System.out.println("adding " + teams[i] + " to subset");
			subset.add(teams[i]); }
		}
		
		isCached[teamX] = true;
		hasEleminated[teamX] = res;
		subSets.put(teamX, subset);
		return res;
	}
	
	public Iterable<String> certificateOfElimination(String team)  {// subset R of teams that eliminates given team; null if not eliminated
		if (isEliminated(team)) return subSets.get(this.getTeamNumber(team));
		else return null;
	}
	
	private int getTeamNumber(String team) {
		if (!name.containsKey(team))
			throw new java.lang.IllegalArgumentException();
		return name.get(team).intValue();
	}
	
	private void printNameMapping() {
		for (int i = 0; i < teams.length; i++) {
			System.out.println( i + " : " + teams[i]);
		}
	}
}