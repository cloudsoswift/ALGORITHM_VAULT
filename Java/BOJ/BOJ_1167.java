package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// 1167. 트리의 지름
public class BOJ_1167 {
    static class Edge {
        int to, cost;
        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
    static int MAX, V, MAX_POINT;
    static List<Edge> list[];
    static boolean visited[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        V = Integer.parseInt(br.readLine());
        list = new ArrayList[V+1];
        visited = new boolean[V+1];
        MAX = 0;
        for(int i=0; i<V; i++) {
            String strArr[] = br.readLine().split(" ");
            int n = Integer.parseInt(strArr[0]);
            list[n] = new ArrayList<Edge>();
            int j = 1;
            while(j < strArr.length && !strArr[j].equals("-1")) {
                int to = Integer.parseInt(strArr[j++]);
                int cost = Integer.parseInt(strArr[j++]);
                list[n].add(new Edge(to, cost));
            }
        }
        // 트리의 임의의 한 점에서 가장 먼 점 탐색
        for(int i=1; i<=V; i++) {
            if(list[i].size() >= 1) {
                visited[i] = true;
                search(i, 0);
                break;
            }
        }
        // 방문배열과 최대비용값 초기화
        visited = new boolean[V+1];
        MAX = 0;
        // 가장 멀었던 점에서 또 다른 가장 먼 점 탐색
        // 이 두 점 사이가 가장 거리가 긴 점들(=트리의 지름을 이루는 점들)임.
        visited[MAX_POINT] = true;
        search(MAX_POINT, 0);
        System.out.println(MAX);
    }
    public static void search(int now, int cost) {
        if(cost > MAX) {
            MAX = cost;
            MAX_POINT = now;
        }
        for(int i=0, s=list[now].size(); i<s; i++) {
            Edge e = list[now].get(i);
            if(visited[e.to]) continue;
            visited[e.to] = true;
            search(e.to, cost + e.cost);
        }
    }
}
