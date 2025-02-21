package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

// 1600. 말이 되고픈 원숭이
public class BOJ_1600 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        // K번 말의 행동으로 이동하는 것 때문에 3차원 배열 사용
        // [ 동, 서, 남, 북 ]
        int dy[] = { 0, 0, 1, -1 };
        int dx[] = { 1, -1, 0, 0 };
        // 말 움직이는거
        // 왼쪽위부터 시계방향으로
        int hdy[] = { -1, -2, -2, -1, 1, 2, 2, 1 };
        int hdx[] = { -2, -1, 1, 2, 2, 1, -1, -2 };

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Queue<Point> queue = new ArrayDeque<>();
        int K = Integer.parseInt(br.readLine());
        String str[] = br.readLine().split(" ");
        int W = Integer.parseInt(str[0]);
        int H = Integer.parseInt(str[1]);
        int map[][] = new int[H][W];
        // 방문 체크를 말의 행동한 횟수 만큼으로 구분해서 3차원 배열로 체크
        // 0번 ~ K번 이므로 K+1 크기의 배열 생성
        boolean v[][][] = new boolean[K+1][H][W];
        for(int i=0; i<H; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<W; j++) {
                map[i][j] = Integer.parseInt(str[j]);
            }
        }
        queue.offer(new Point(0, 0, 0));
        v[0][0][0] = true;
        int time = 0;
        int ans = -1;
        while(!queue.isEmpty()) {
            int QueueSize = queue.size();
            while(QueueSize-->0) {
                Point p = queue.poll();
                if(p.x == W-1 && p.y == H-1) {
                    ans = time;
                    queue.clear();
                    break;
                }
                // 탐색
                // 원숭이 탐색
                for(int i=0; i<4; i++) {
                    int mx = p.x + dx[i];
                    int my = p.y + dy[i];
                    if(mx >= W || my >= H || mx < 0 || my <0) continue;
                    if(map[my][mx] == 1) continue;
                    if(v[p.k][my][mx]) continue;
                    v[p.k][my][mx] = true;
                    queue.offer(new Point(mx, my, p.k));
                }
                // 말 탐색
                if(p.k<K) {
                    for(int i=0; i<8; i++) {
                        int mx = p.x + hdx[i];
                        int my = p.y + hdy[i];
                        if(mx >= W || my >= H || mx < 0 || my <0) continue;
                        if(map[my][mx] == 1) continue;
                        if(v[p.k+1][my][mx]) continue;
                        v[p.k+1][my][mx] = true;
                        queue.offer(new Point(mx, my, p.k+1));
                    }
                }
            }
            time++;
        }
        System.out.println(ans);
    }
    static class Point{
        int x, y, k;

        public Point(int x, int y, int k) {
            super();
            this.x = x;
            this.y = y;
            this.k = k;
        }
    }
}
