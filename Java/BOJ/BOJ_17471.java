package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 17471. 게리맨더링
public class BOJ_17471 {
    static int people[], graph[][], N, MIN_DIFFER;
    static boolean isSelected[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        people = new int[N];
        isSelected = new boolean[N];
        graph = new int[N][N];
        MIN_DIFFER = Integer.MAX_VALUE;
        String str[] = br.readLine().split(" ");
        for(int i=0; i<N; i++) {
            people[i] = Integer.parseInt(str[i]);
        }
        for(int i=0; i<N; i++){
            str = br.readLine().split(" ");
            int M = Integer.parseInt(str[0]);
            for(int j=0; j<M; j++){
                int x = Integer.parseInt(str[1+j]);
                graph[i][x-1] = 1;
                graph[x-1][i] = 1;
            }
        }
        subset(0);
        if(MIN_DIFFER == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(MIN_DIFFER);
    }
    static void subset(int index) {
        if(index == N) {
            int A = 0, B = 0;
            int f = -1, t = -1;
            for(int i=0; i<N; i++) {
                if(isSelected[i]) {
                    if(t == -1) t = i;
                    A += people[i];
                } else {
                    if(f == -1) f = i;
                    B += people[i];
                }
            }
            if(A == 0 || B == 0) return;
            int difference = Math.abs(A-B);
            if(difference > MIN_DIFFER) return;
            boolean v[] = new boolean[N];
            // A팀, B팀 별로 탐색
            search(t, v);
            search(f, v);
            boolean canConnect = true;
            for(int i=0; i<N; i++) {
                // 방문하지 못한 정점이 있음 -> 팀 내에서 이어지지 못하는 구간 발생 -> 선거구 발생 X
                if(!v[i]) {
                    canConnect = false;
                    break;
                }
            }
            if(canConnect) {
                if(MIN_DIFFER > difference)
                    MIN_DIFFER = difference;
            }
            return;
        }
        isSelected[index] = true;
        subset(index+1);
        isSelected[index] = false;
        subset(index+1);
    }
    static void search(int pos, boolean[] v) {
        v[pos] = true;
        for(int i=0; i<N; i++) {
            if(v[i]) continue;
            // 같은 선거구이면서, 이어져있는 선거구 방문해 탐색
            if(isSelected[pos] == isSelected[i] && graph[pos][i] == 1) {
                search(i, v);
            }
        }
    }
}
