package utils;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class Flow {
	// TODO: Maximum Bipartite Matching
	
	
	// not tested
	static class Maxflow {
		int[][] edges; // residual graph
		int source, sink;
		
		public Maxflow(int[][] edges, int source, int sink) {
			this.edges = edges;
			this.source = source;
			this.sink = sink;
		}
		
		public int computeMaxFlow() {
			int result = 0;
			int pathCapacity = findPath();
			
			while (pathCapacity > 0) {
				result += pathCapacity;
				pathCapacity = findPath();
			}
			
			return result;
		}
		
		private int findPath() {
			Queue<Integer> queue = new ArrayDeque<>();
			int[] prevs = new int[edges.length];
			Arrays.fill(prevs, -1);
			
			queue.add(source);
			
			while (!queue.isEmpty()) {
				int curr = queue.poll();
				if (curr == sink) {
					break;
				}
				
				for (int next = 0; next < edges.length; next++) {
					if (prevs[next] != -1 && edges[curr][next] > 0) {
						prevs[next] = curr;
						queue.add(next);
					}
				}
			}
			
			// compute the path capacity
			int pathCap = Integer.MAX_VALUE;
			int node = sink;
			while (node != -1) {
				int prev = prevs[node];
				pathCap = Math.min(pathCap, edges[prev][node]);
				node = prev;
			}
			
			if (pathCap == Integer.MAX_VALUE)
				return 0;
			
			// update the residual graph
			node = sink;
			while (node != -1) {
				int prev = prevs[node];
				edges[prev][node] -= pathCap;
				edges[node][prev] += pathCap;
				node = prev;
			}
			
			return pathCap;
		}
	}
}
