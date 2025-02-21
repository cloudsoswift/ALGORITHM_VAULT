package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 17070. 파이프 옮기기 1
public class BOJ_17070 {
    static int N, res = 0;
    static int map[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String str[];
        map = new int[N+1][N+1]; // 문제에서 주어진대로, (1, 1), (1, 2)를 차지하고 있다고 가정하기 위해 맵 한 줄씩 늘림 ( 0 번째 줄은 버림 )
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                map[i+1][j+1] = Integer.parseInt(str[j]);
            }
        }

        dfs(1, 2, 0);
        System.out.println(res);
    }
    static void dfs(int r, int c, int dir) {
        // dir : 0 -> 가로, 1 -> 세로, 2 -> 대각선
        if(r == N && c == N) {
            res++;
            return;
        }
        if(dir == 0) {
            // 가로 -> 우측 or 우하단
            // 우측
            if(c+1 <= N && map[r][c+1] == 0) {
                dfs(r, c+1, 0);
            }
            if(r+1 <= N && c+1 <= N && map[r+1][c+1] == 0 && map[r][c+1] == 0 && map[r+1][c] == 0) {
                dfs(r+1, c+1, 2);
            }
            // 우하단
        } else if(dir == 1) {
            // 세로 -> 하단 or 우하단
            // 하단
            if(r+1 <= N && map[r+1][c] == 0) {
                dfs(r+1, c, 1);
            }
            if(r+1 <= N && c+1 <= N && map[r+1][c+1] == 0 && map[r][c+1] == 0 && map[r+1][c] == 0) {
                dfs(r+1, c+1, 2);
            }
            // 우하단

        } else if(dir == 2) {
            // 우하단 -> 우측 or 하단 or 우하단
            if(c+1 <= N && map[r][c+1] == 0) {
                dfs(r, c+1, 0);
            }
            if(r+1 <= N && map[r+1][c] == 0) {
                dfs(r+1, c, 1);
            }
            if(r+1 <= N && c+1 <= N && map[r+1][c+1] == 0 && map[r][c+1] == 0 && map[r+1][c] == 0) {
                dfs(r+1, c+1, 2);
            }
        }
    }
}
