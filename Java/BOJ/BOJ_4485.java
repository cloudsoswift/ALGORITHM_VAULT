package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 4485. 녹색 옷 입은 애가 젤다지?
// BFS 활용해 풀이
public class BOJ_4485 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, count = 1;
        // [동, 남, 서, 북]
        int dr[] = new int[] {0, 1, 0, -1};
        int dc[] = new int[] {1, 0, -1, 0};
        while((N = Integer.parseInt(br.readLine())) != 0) {
            int map[][] = new int[N][N];
            int dp[][] = new int[N][N];
            String str[];
            for(int i=0; i<N; i++) {
                str = br.readLine().split(" ");
                for(int j=0; j<N; j++) {
                    map[i][j] = Integer.parseInt(str[j]);
                }
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            }
            dp[0][0] = map[0][0];
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[] {0, 0});
            while(!queue.isEmpty()) {
                int now[] = queue.poll();
                for(int i=0; i<4; i++) {
                    int mr = now[0] + dr[i];
                    int mc = now[1] + dc[i];
                    if(mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
                    int before = dp[now[0]][now[1]] + map[mr][mc];
                    if(dp[mr][mc] > before) {
                        dp[mr][mc] = before;
                        queue.offer(new int[] {mr, mc});
                    }
                }
            }
            System.out.printf("Problem %d: %d\n", count++, dp[N-1][N-1]);
        }
    }
}
