package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 2468. 안전 영역
public class BOJ_2468 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int arr[][] = new int[N][N];
        // [ 동, 서, 남, 북 ]
        int dx[] = { 1, -1, 0, 0 };
        int dy[] = { 0, 0, 1, -1 };
        String str[];
        int MAX_HEIGHT = Integer.MIN_VALUE;
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                arr[i][j] = Integer.parseInt(str[j]);
                if(arr[i][j] > MAX_HEIGHT) {
                    MAX_HEIGHT = arr[i][j];
                }
            }
        }
        boolean v[][];
        int MAX_COUNT = 0;
        MAX_HEIGHT = MAX_HEIGHT > 100 ? 100 : MAX_HEIGHT;
        for(int i=1; i<MAX_HEIGHT; i++) {
            v = new boolean[N][N];
            int count = 0;
            Queue<Point> q = new LinkedList<>();
            for(int j=0; j<N; j++) {
                for(int k=0; k<N; k++) {
                    if(arr[j][k] <= i) continue;
                    if(v[j][k]) continue;
                    q.offer(new Point(k, j));
                    v[j][k] = true;
                    while(!q.isEmpty()) {
                        Point p = q.poll();
                        for(int l=0; l<4; l++) {
                            int mx = p.x + dx[l];
                            int my = p.y + dy[l];
                            if(mx < 0 || my < 0 || mx >= N || my >= N) continue;
                            if(v[my][mx]) continue;
                            if(arr[my][mx] <= i) continue;
                            q.offer(new Point(mx, my));
                            v[my][mx] = true;
                        }
                    }
                    count++;
                }
            }
            if(count > MAX_COUNT) {
                MAX_COUNT = count;
            }
        }
        System.out.println(MAX_COUNT);
    }
}
