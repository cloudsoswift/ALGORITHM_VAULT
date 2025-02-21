package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//import java.util.PriorityQueue;

// 1753. 최단경로
public class BOJ_1753 {
    static class Edge{
        int to, weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V, E, K;
        String str[] = br.readLine().split(" ");
        V = Integer.parseInt(str[0]);
        E = Integer.parseInt(str[1]);
        K = Integer.parseInt(br.readLine()) - 1;
        List<Edge> list[] = new ArrayList[V];
        for(int i=0; i<V; i++) {
            list[i] = new ArrayList<Edge>();
        }
        for(int i=0; i<E; i++) {
            str = br.readLine().split(" ");
            int u = Integer.parseInt(str[0]) - 1;
            int v = Integer.parseInt(str[1]) - 1;
            int w = Integer.parseInt(str[2]);
            list[u].add(new Edge(v, w));
        }

        int dist[] = new int[V];
        boolean v[] = new boolean[V];
        StringBuilder sb = new StringBuilder();
        int min, minVertex;
//		PriorityQueue<Edge> pQueue = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.weight, e2.weight));

        for(int i=0; i<V; i++) {
            dist[i] = 1000000001;
        }
//		dist[K] = 0;
//		pQueue.offer(new Edge(K, 0));
//		while(!pQueue.isEmpty()) {
//			Edge e = pQueue.poll();
//			if(v[e.to]) continue;
//			v[e.to] = true;
//			int c = e.weight;
//			int p = e.to;
//			for(Edge e2 : list[p]) {
//				int nextPos = e2.to;
//				int nextCost = e2.weight;
//				if(dist[nextPos] > c + nextCost) {
//					dist[nextPos] = c + nextCost;
//					pQueue.offer(new Edge(nextPos, dist[nextPos]));
//				}
//			}
//		}
        dist[K] = 0;
        for(int i=0; i<V; i++) {
            min = Integer.MAX_VALUE;
            minVertex = -1;
            for(int j=0; j<V; j++) {
                if(!v[j] && min > dist[j]) {
                    min = dist[j];
                    minVertex = j;
                }
            }

            v[minVertex] = true;

            for(Edge e : list[minVertex]) {
                if(dist[e.to] > dist[minVertex] + e.weight) {
                    dist[e.to] = dist[minVertex] + e.weight;

                }
            }
        }
        for(int i=0; i<V; i++) {
            if(dist[i] > 1000000000) {
                sb.append("INF");
            } else {
                sb.append(dist[i]);
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
}
