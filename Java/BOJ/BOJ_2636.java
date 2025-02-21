package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 2636. 치즈
public class BOJ_2636 {
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
        int R = Integer.parseInt(str[0]);
        int C = Integer.parseInt(str[1]);
        int map[][] = new int[R][C];
        int remainCheese = 0, beforeCheese = 0;
        // [ 동, 서, 남, 북 ]
        int dx[] = new int[] { 1, -1, 0, 0 };
        int dy[] = new int[] { 0, 0, 1, -1 };
        for(int i = 0; i<R; i++) {
            str = br.readLine().split(" ");
            for(int j = 0; j<C; j++) {
                map[i][j] = Integer.parseInt(str[j]);
                if(map[i][j] == 1) remainCheese++;
            }
        }
        Queue<Point> queue = new LinkedList<Point>();
        Queue<Point> deleteCheese = new LinkedList<>();
        boolean v[][];
        int time = 0;
        while(remainCheese>0) {
            queue.offer(new Point(0, 0));
            v = new boolean[R][C];
            v[0][0] = true;
            while(!queue.isEmpty()) {
                beforeCheese = remainCheese;
                Point p = queue.poll();
                for(int i=0; i<4; i++) {
                    int mx = p.x + dx[i];
                    int my = p.y + dy[i];
                    if(mx >= C || my >= R || mx < 0 || my < 0)continue;
                    if(v[my][mx]) continue;
                    v[my][mx] = true;
                    if(map[my][mx] == 1) {
                        deleteCheese.offer(new Point(mx, my));
                        continue;
                    }
                    queue.offer(new Point(mx, my));
                }
            }
            while(!deleteCheese.isEmpty()) {
                Point p = deleteCheese.poll();
                map[p.y][p.x] = 0;
                remainCheese--;
            }
            time++;
        }
        System.out.println(time);
        System.out.println(beforeCheese);

    }
}
