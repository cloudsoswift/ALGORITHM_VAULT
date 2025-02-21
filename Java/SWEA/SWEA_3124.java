package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 3124. 최소 스패닝 트리
public class SWEA_3124 {
    static class Edge implements Comparable<Edge>{
        int from, to, cost;
        Edge(int from, int to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
    static int parents[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[];
        int T = Integer.parseInt(br.readLine());
        int V, E; // 정점의 개수, 간선의 개수
        int A, B, C; // A번 정점과 B번 정점이 가중치 C인 간선으로 연결되어 있음.
        for(int test_case = 1; test_case <= T; test_case++) {
            str = br.readLine().split(" ");
            V = Integer.parseInt(str[0]);
            E = Integer.parseInt(str[1]);
            parents = new int[V+1];
            // MakeSet
            for(int i=1; i<V; i++) {
                parents[i] = i;
            }
            Edge[] arr = new Edge[E];
            for(int i=0; i<E; i++) {
                str = br.readLine().split(" ");
                A = Integer.parseInt(str[0]);
                B = Integer.parseInt(str[1]);
                C = Integer.parseInt(str[2]);
                arr[i] = new Edge(A, B, C);
            }
            Arrays.sort(arr);
            int count = 0, result = 0;
            for(int i=0;i<E; i++) {
                Edge e = arr[i];
                if(Union(e.from, e.to)) {
                    result += e.cost;
                    if(count++==V-1) break;
                }
            }
            System.out.printf("#%d %d\n", test_case, result);
        }
    }
    static boolean Union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if(aRoot == bRoot) return false;
        parents[bRoot] = aRoot;
        return true;
    }
    static int find(int a) {
        if(parents[a] == a) return a;
        return parents[a] = find(parents[a]);
    }
}
