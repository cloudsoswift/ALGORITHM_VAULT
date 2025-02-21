package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 8382. 방향 전환
public class SWEA_8382 {
    static int res = 0;
    static int x1, y1, x2, y2;
    static boolean v[][][]; // 가로로 오는지, 세로로 오는지에 따라 다른 맵 쓰기위해 3차원 배열 사용
    // 가로이동 , 세로이동
    static int dx[] = {-1, 1, 0, 0}, dy[] = {0, 0, -1, 1};
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String str[];
        for(int test_case = 1; test_case<= T; test_case++) {
            res = 0;
            str = br.readLine().split(" ");
            v = new boolean[201][201][2];
            x1 = Integer.parseInt(str[0]) + 100;
            y1 = Integer.parseInt(str[1]) + 100;
            x2 = Integer.parseInt(str[2]) + 100;
            y2 = Integer.parseInt(str[3]) + 100;
            // 맵 데이터 읽어서,
            bfs();

            System.out.printf("#%d %d\n", test_case, res);

        }
    }
    static void bfs() {
        Queue<Data> queue = new LinkedList<Data>();
        // 가장 첫 이동은 어떤 이동 이어도 상관 없다.
        queue.offer(new Data(x1, y1, 0, 0)); // 가로 이동
        queue.offer(new Data(x1, y1, 0, 1)); // 가로 이동
        v[y1][x1][0] = true;
        v[y1][x1][1] = true;
        Data cur;
        //큐가 빌 때까지
        while(!queue.isEmpty()) {
            // 무조건 첫 번째 것 꺼내기
            cur = queue.poll();
            // 할 일 하기
            if(x2 == cur.x && y2 == cur.y) {
                res = cur.cnt;
                return;
            }
            // 현재 진행되었던 것에 대한 4방위 탐색
            int nx, ny;
            if(cur.dir == 0) { // 가로 이동
                for(int d = 2; d < 4; d++) {
                    nx = cur.x + dx[d];
                    ny = cur.y + dy[d];
                    // 배열 범위 넘어갔는지
                    if(nx < 0 || nx >= 201 || ny < 0 || ny >= 201) { continue; }
                    // 방문 했는지
                    if(v[ny][nx][1]) {
                        continue;
                    }
                    queue.offer(new Data(nx, ny, cur.cnt+1, 1)); // 세로 이동
                    v[ny][nx][1] = true;
                }
            } else if(cur.dir == 1) { // 세로 이동
                for(int d = 0; d < 2; d++) {
                    nx = cur.x + dx[d];
                    ny = cur.y + dy[d];
                    // 배열 범위 넘어갔는지
                    if(nx < 0 || nx >= 201 || ny < 0 || ny >= 201) { continue; }
                    // 방문 했는지
                    if(v[ny][nx][0]) {
                        continue;
                    }
                    queue.offer(new Data(nx, ny, cur.cnt+1, 0)); // 세로 이동
                    v[ny][nx][0] = true;
                }
            }
        }
    }
    static class Data{
        int x, y;
        int cnt;
        int dir; // 0 : 가로 , 1 : 세로
        public Data(int x, int y, int cnt, int dir) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.dir = dir;
        }

    }
}
