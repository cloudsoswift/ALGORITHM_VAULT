package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 14502. 연구소
public class BOJ_14502 {
    static int N, M, map[][], dx[], dy[], MAX_SAFEAREA;
    static Queue<Point> queue;
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        map = new int[N][M];
        queue = new LinkedList<>();
        // [ 동, 서, 남, 북 ]
        dx = new int[] { 1, -1, 0, 0 };
        dy = new int[] { 0, 0, 1, -1 };
        MAX_SAFEAREA = Integer.MIN_VALUE;
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(str[j]);
                if(map[i][j] == 2)
                {
                    queue.offer(new Point(j, i));
                }
            }
        }
        search(0);
        System.out.println(MAX_SAFEAREA);
    }
    public static void search(int cnt) {
        if(cnt == 3) {
            // 가벽을 모두 설치한 경우
            int tmpMap[][] = new int[N][M];
            copy(map, tmpMap);
            Queue<Point> tmpQueue = new LinkedList<>();
            for(Point p : queue) {
                tmpQueue.offer(p);
            }
            filling(tmpQueue);
            int res = calc();
            if(res > MAX_SAFEAREA) {
                MAX_SAFEAREA = res;
            }
            map = tmpMap;
            return;
        }
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(map[i][j] == 0) {
                    map[i][j] = 1;
                    search(cnt+1);
                    map[i][j] = 0;
                }
            }
        }
    }
    public static void filling(Queue<Point> queue) {
        while(!queue.isEmpty()) {
            Point p = queue.poll();
            map[p.y][p.x] = 2;
            for(int i=0; i<4; i++) {
                int mx = p.x + dx[i];
                int my = p.y + dy[i];
                if(mx < 0 || my < 0 || mx >= M || my >= N) continue;
                if(map[my][mx] != 0) continue;
                map[my][mx] = 2;
                queue.offer(new Point(mx, my));
            }
        }
    }
    public static int calc() {
        int res = 0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(map[i][j] == 0)
                    res++;
            }
        }
        return res;
    }
    public static void copy(int[][] from, int[][] to) {
        for(int i=0; i<from.length; i++) {
            for(int j=0; j<from[i].length; j++) {
                to[i][j] = from[i][j];
            }
        }
    }
}
