package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 10026. 적록색약
public class BOJ_10026 {
    // N이 최대 100까지 될 수 있어서, DFS(재귀)로 짜면 함수 stackoverflow 발생.
    // BFS로 하면 메모리는 많이 써도 터지진 않음.
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
        // [ 동, 서, 남, 북 ]
        int dx[] = new int[] { 1, -1, 0, 0 };
        int dy[] = new int[] { 0, 0, 1, -1 };
        char map[][] = new char[N][N];
        String str;
        boolean v[][] = new boolean[N][N];
        for(int i=0; i<N; i++) {
            str = br.readLine();
            for(int j=0; j<N; j++) {
                map[i][j] = str.charAt(j);
            }
        }
        Queue<Point> queue = new LinkedList<>();
        int Ncnt = 0, cnt = 0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(v[i][j]) continue;
                char color = map[i][j];
                queue.offer(new Point(j, i));
                v[i][j] = true;
                while(!queue.isEmpty()) {
                    Point p = queue.poll();
                    for(int k=0; k<4; k++) {
                        int mx = p.x + dx[k];
                        int my = p.y + dy[k];
                        if(mx >= N || my >= N || mx < 0 || my < 0) continue;
                        if(v[my][mx] || map[my][mx] != color) continue;
                        v[my][mx] = true;
                        queue.offer(new Point(mx, my));
                    }
                }
                Ncnt++;
            }
        }
        v = new boolean[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(v[i][j]) continue;
                char color = map[i][j];
                queue.offer(new Point(j, i));
                while(!queue.isEmpty()) {
                    Point p = queue.poll();
                    for(int k=0; k<4; k++) {
                        int mx = p.x + dx[k];
                        int my = p.y + dy[k];
                        if(mx >= N || my >= N || mx < 0 || my < 0) continue;
                        if(v[my][mx]) continue;
                        if(color == 'B' && map[my][mx] != color) continue;
                        if(map[my][mx] == 'B' && map[my][mx] != color) continue;
                        v[my][mx] = true;
                        queue.offer(new Point(mx, my));
                    }
                }
                cnt++;
            }
        }
        System.out.println(Ncnt + " "+cnt);
    }
}
