package topcoder.dp;

import java.util.Arrays;

public class BadNeighborsRonny
{
	 private static final class Neighbor {
	        private int index;
	        private int donation;
	        public Neighbor(int index, int donation) {
	            super();
	            this.index = index;
	            this.donation = donation;
	        }
	    }

	    public int maxDonations(int[] donations) {
	        if (donations == null || donations.length == 0) {
	            return 0;
	        }
	        int n = donations.length;
	        Neighbor[] neighbors = new Neighbor[n];
	        boolean[] collected = new boolean[n];
	        for (int i = 0; i < n; i++) {
	            neighbors[i] = new Neighbor(i, donations[i]);
	        }
	        Arrays.sort(neighbors, (o1, o2) -> {
	            return o2.donation - o1.donation;
	        });
	        int maxDonation = 0;
	        for (Neighbor neighbor : neighbors) {
	            if (avaliable(neighbor, collected, neighbors)) {
	                maxDonation += neighbor.donation;
	            }
	        }
	        return maxDonation;
	    }

	    private boolean avaliable(Neighbor neighbor, boolean[] collected, Neighbor[] neighbors) {
	        if (!collected[neighbor.index]) {
	            int n = neighbors.length;
	            int last = n - 1;
	            if (neighbor.index == 0) {
	                collected[1] = true;
	                collected[last] = true;
	            } else if (neighbor.index == last) {
	                collected[0] = true;
	                collected[last - 1] = true;
	            } else {
	                collected[neighbor.index - 1] = true;
	                collected[neighbor.index + 1] = true;
	            }
	            collected[neighbor.index] = true;
	            return true;
	        }
	        return false;
	    }
}
