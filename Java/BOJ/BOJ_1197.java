package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

// 1197. 최소 스패닝 트리
public class BOJ_1197 {
    static class Edge{
        int from, to, cost;
        Edge(int from, int to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        int V = Integer.parseInt(strArr[0]);
        int E = Integer.parseInt(strArr[1]);
        int parent[] = new int[V+1];
        for(int i=1; i<=V; i++) {
            parent[i] = i;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost < o2.cost ? -1 : (o1.cost == o2.cost ? 0 : 1);
            }
        });
        for(int i=0; i<E; i++) {
            strArr = br.readLine().split(" ");
            int from = Integer.parseInt(strArr[0]);
            int to = Integer.parseInt(strArr[1]);
            int cost = Integer.parseInt(strArr[2]);
            pq.offer(new Edge(from, to, cost));
        }
        int sumCost = 0;
        while(!pq.isEmpty()) {
            Edge now = pq.poll();
            if(find(now.from, parent) == find(now.to, parent)) continue;
            union(now.from, now.to, parent);
            sumCost += now.cost;
        }
        System.out.println(sumCost);
    }

    static void union(int l, int r, int parent[]) {
        parent[find(r, parent)] = find(l, parent);
    }
    static int find(int v, int parent[]) {
        if(v == parent[v]) return v;
        return parent[v] = find(parent[v], parent);
    }
}
