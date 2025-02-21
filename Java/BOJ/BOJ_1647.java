package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

// 1647. 도시 분할 계획
public class BOJ_1647 {
    static class Edge {
        int to, from, cost;
        public Edge(int to, int from, int cost) {
            this.to = to;
            this.from = from;
            this.cost = cost;
        }
    }
    static int parent[], N, M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        int N = Integer.parseInt(strArr[0]);
        parent = new int[N+1];
        for(int i=1; i<=N; i++) {
            parent[i] = i;
        }
        int M = Integer.parseInt(strArr[1]);
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost > o2.cost ? 1 : (o1.cost == o2.cost ? 0 : -1);
            }
        }
        );
        for(int i=0; i<M; i++) {
            strArr = br.readLine().split(" ");
            int A = Integer.parseInt(strArr[0]);
            int B = Integer.parseInt(strArr[1]);
            int C = Integer.parseInt(strArr[2]);
            pq.offer(new Edge(A, B, C));
        }
        int costSum = 0, lastCost = 0;
        boolean isConnected;
        while(!pq.isEmpty()) {
            // 모든 집이 이어져있는지 검사
            isConnected = true;
            for(int i=2; i<=N; i++) {
                if(parent[1] != find(i)) {
                    isConnected = false;
                    break;
                };
            }
            if(isConnected) {
                System.out.println(costSum - lastCost);
                return;
            }
            while(!pq.isEmpty()) {
                Edge e = pq.poll();
                if(find(e.to) != find(e.from)) {
                    union(e.to, e.from);
                    costSum += e.cost;
                    lastCost = e.cost;
                    break;
                }
            }
        }
    }
    static int find(int idx) {
        if(parent[idx] == idx) {
            return idx;
        }
        return parent[idx] = find(parent[idx]);
    }
    static void union(int left, int right) {
        int lparent = find(left);
        int rparent = find(right);
        if(lparent < rparent) {
            parent[rparent] = lparent;
            find(right);
        } else {
            parent[lparent] = rparent;
            find(left);
        }
    }
}
