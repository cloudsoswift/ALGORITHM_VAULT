package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 2206. 벽 부수고 이동하기

// 단순히 방문 배열 하나로만 체크하면, 벽을 한 번도 뚫지 않고 진행할 때와, 벽을 한 번 뚫고 진행할 때의 경로가
// 겹칠 수 있는 상황이 발생 함. 이때 한 쪽의 경우에서는 해당 경로를 방문 할 수 없게됨.
// 이를 위해, 벽을 한 번도 뚫지 않았을 때, 벽을 한 번 뚫었을 때의 방문 배열을 따로 구분하여 풂.
public class BOJ_2206 {
    static int N, M, map[][], dx[], dy[];
    static boolean v[][][];
    static class Point{
        int x, y, block;
        Point(int x, int y, int block){
            this.x = x;
            this.y = y;
            this.block = block;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        // [ 상, 하, 좌, 우 ]
        dx = new int[] { 0, 0, -1, 1 };
        dy = new int[] { -1, 1, 0, 0 };
        map = new int[N][M];
        // [y][x][0] -> 벽을 한 번도 뚫지 않고 진행한 상황의 루트
        // [y][x][1] -> 벽을 한 번이라도 뚫고 진행한 상황의 방문 배열
        v = new boolean[N][M][2];
        int MIN_COUNT = Integer.MAX_VALUE;
        for(int i=0; i<N; i++) {
            String s = br.readLine();
            for(int j=0; j<M; j++) {
                map[i][j] = Character.digit(s.charAt(j), 10);
            }
        }
        Queue<Point> queue = new LinkedList<>();
        int time = 1;
        queue.offer(new Point(0, 0, 0));
        boolean isBreak = false;
        while(!queue.isEmpty()) {
            int qSize = queue.size();
            while(qSize-->0) {
                Point p = queue.poll();
                if(p.x == M-1 && p.y == N-1) {
                    MIN_COUNT = time;
                    queue.clear();
                    break;
                }
                for(int i=0; i<4; i++) {
                    int mx = p.x + dx[i];
                    int my = p.y + dy[i];
                    if(mx < 0 || my < 0 || mx >= M || my >= N) continue;
                    if(p.block == 0) {
                        if(v[my][mx][0]) continue;
                        if(map[my][mx] == 1) {
                            // 벽을 안 뚫다가, 처음 뚫기 시작했을 때
                            // 벽을 안 뚫은 다른 애들과 경로 겹쳐서 방문배열 이상해지는 거 방지하기 위해 따로 분기 탐.
                            v[my][mx][0] = true;
                            v[my][mx][1] = true;
                            isBreak = true;
                            queue.offer(new Point(mx, my, p.block+1));
                        } else {
                            v[my][mx][0] = true;
                            // 벽을 한 번도 뚫지 않았을 때 까진 둘 다 함께 진행 함.
                            if(!isBreak)
                                v[my][mx][1] = true;
                            queue.offer(new Point(mx, my, p.block));
                        }
                    } else {
                        if(v[my][mx][1]) continue;
                        if(map[my][mx] == 1) continue;
                        else {
                            v[my][mx][1] = true;
                            queue.offer(new Point(mx, my, p.block));
                        }
                    }
                }
            }
            time++;
        }
        if(MIN_COUNT == Integer.MAX_VALUE) MIN_COUNT = -1;
        System.out.println(MIN_COUNT);
    }
}
