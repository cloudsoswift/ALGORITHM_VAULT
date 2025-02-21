package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 4179. 불!
public class BOJ_4179 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        String str;
        int R = Integer.parseInt(strArr[0]);
        int C = Integer.parseInt(strArr[1]);
        char map[][] = new char[R][C];
        boolean v[][] = new boolean[R][C];
        // [동, 서, 남, 북]
        int dx[] = new int[] { 1, -1, 0, 0 };
        int dy[] = new int[] { 0, 0, 1, -1 };
        Queue<Point> queue = new LinkedList<Point>();
        Queue<Point> fireQueue = new LinkedList<Point>();
        for(int i=0; i<R; i++) {
            str = br.readLine();
            for(int j=0; j<C; j++) {
                map[i][j] = str.charAt(j);
                if(map[i][j] == 'J')
                    queue.offer(new Point(j, i));
                else if(map[i][j] == 'F')
                    fireQueue.offer(new Point(j, i));
            }
        }
        int time = 1;
        while(true) {
            int fireSize = fireQueue.size();
            while(fireSize-->0) {
                Point f = fireQueue.poll();
                for(int i=0; i<4; i++) {
                    int mx = f.x + dx[i];
                    int my = f.y + dy[i];
                    if(mx >= C || my >= R || mx < 0 || my < 0) continue;
                    if(map[my][mx] != '#') {
                        map[my][mx] = 'F';
                        fireQueue.offer(new Point(mx, my));
                    }
                }
            }
            int queueSize = queue.size();
            if(queueSize == 0) break;
            while(queueSize-->0) {
                Point now = queue.poll();
                for(int i=0; i<4; i++) {
                    int mx = now.x + dx[i];
                    int my = now.y + dy[i];
                    if(mx >= C || my >= R || mx < 0 || my < 0) {
                        System.out.println(time);
                        return;
                    };
                    if(v[my][mx]) continue;
                    if(map[my][mx] == '.' || map[my][mx] == 'J') {
                        queue.offer(new Point(mx, my));
                        v[my][mx] = true;
                    }
                }
            }
            time++;
        }
        System.out.println("IMPOSSIBLE");
    }
}
