package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 1012. 유기농 배추
public class BOJ_1012 {
    // 동, 서, 남, 북
    static int[] dr = new int[]{0, 0, 1, -1};
    static int[] dc = new int[]{1, -1, 0, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String[] strArr;
        Queue<int[]> queue = new LinkedList<>();
        for(int tc=0; tc<T; tc++){
            strArr = br.readLine().split(" ");
            int M = Integer.parseInt(strArr[0]);
            int N = Integer.parseInt(strArr[1]);
            int K = Integer.parseInt(strArr[2]);
            int[][] map = new int[N][M];
            for(int i=0; i<K; i++) {
                strArr = br.readLine().split(" ");
                int X = Integer.parseInt(strArr[0]);
                int Y = Integer.parseInt(strArr[1]);
                map[Y][X] = 1;
            }
            int wormCount = 0;
            for(int i=0; i<N; i++) {
                for(int j=0; j<M; j++) {
                    if(map[i][j] == 1) {
                        wormCount++;
                        queue.offer(new int[]{i, j});
                        map[i][j] = 0;
                        while(!queue.isEmpty()) {
                            int[] now = queue.poll();
                            for(int d=0; d<4; d++) {
                                int mr = now[0] + dr[d];
                                int mc = now[1] + dc[d];
                                if(mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
                                if(map[mr][mc] == 0) continue;
                                queue.offer(new int[]{mr, mc});
                                map[mr][mc] = 0;
                            }
                        }
                    }
                }
            }
            System.out.println(wormCount);
        }
    }
}
