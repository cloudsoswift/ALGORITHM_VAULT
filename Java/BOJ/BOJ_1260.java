package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 1260. DFS와 BFS
public class BOJ_1260 {
    static int v[];
    static List<Integer> list[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, M, V;
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        V = Integer.parseInt(str[2]);
        list = new ArrayList[N+1];
        v = new int[N+1];
        for(int i=0; i<N+1; i++) {
            list[i] = new ArrayList<>();
        }
        for(int i=0; i<M; i++) {
            str = br.readLine().split(" ");
            int f = Integer.parseInt(str[0]);
            int s = Integer.parseInt(str[1]);
            list[f].add(s);
            list[s].add(f);
        }
        for(int i=0; i<N+1; i++) {
            Collections.sort(list[i]);
        }
        v[V] = 1;
        dfs(V);
        System.out.println();
        v = new int[N+1];
        bfs(V);
    }
    static void dfs(int start) {
        System.out.print(start + " ");
        for(int i=0, size=list[start].size(); i<size; i++) {
            int to = list[start].get(i);
            if(v[to] == 1) continue;
            v[to] = 1;
            dfs(to);
        }
    }
    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        v[start] = 1;
        while(!queue.isEmpty()) {
            int now = queue.poll();
            System.out.print(now + " ");
            for(int i=0, size=list[now].size(); i<size; i++) {
                int to = list[now].get(i);
                if(v[to] == 1) continue;
                v[to] = 1;
                queue.offer(to);
            }
        }
    }
}
