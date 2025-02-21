package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 7576. 토마토
public class BOJ_7576 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[];
        int M, N, map[][];
        str = br.readLine().split(" ");
        M = Integer.parseInt(str[0]);
        N = Integer.parseInt(str[1]);
        map = new int[N][M];
        Queue<Point> q = new LinkedList<Point>();
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(str[j]);
                if(map[i][j] == 1) {
                    q.offer(new Point(j, i));
                }
            }
        }
        // 저장될 때부터 모든 토마토가 익어있는 상태
        if(q.size() == N*M) {
            System.out.println(0);
            return;
        }
        // [ 동, 서, 남, 북 ]
        int dx[] = new int[] {1, -1, 0, 0};
        int dy[] = new int[] {0, 0, 1, -1};
        int time = 0;
        while(!q.isEmpty()) {
            int queueSize = q.size();
            while(queueSize-->0) {
                Point p = q.poll();
                for(int i=0; i<4; i++) {
                    int mx = p.x + dx[i];
                    int my = p.y + dy[i];
                    if(mx >= M || my >= N || mx < 0 || my < 0) continue;
                    if(map[my][mx] == 0) {
                        map[my][mx] = 1;
                        q.offer(new Point(mx, my));
                    }
                }
            }
            time++;
        }
        time--;
        // 토마토가 모두 익지는 못하는 상황
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(map[i][j] == 0) {
                    System.out.println(-1);
                    return;
                }
            }
        }
        System.out.println(time);
    }
}
