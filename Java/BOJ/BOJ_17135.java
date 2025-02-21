package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 17135. 캐슬 디펜스
public class BOJ_17135 {
    static int N, M, D, archerPos[], map[][], MAX_COUNT, dx[], dy[];
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        D = Integer.parseInt(str[2]);
        // [ 동, 북, 서 ]
        dx = new int[]{ -1, 0, 1 };
        dy = new int[]{ 0, 1, 0 };
        MAX_COUNT = 0;
        map = new int[N][M];
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(str[j]);
            }
        }
        archerPos = new int[3];
        comb(0, 0);
        System.out.println(MAX_COUNT);
    }
    public static int getDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1-r2) + Math.abs(c1-c2);
    }
    public static void comb(int cnt, int start) {
        if(cnt == 3) {
            doPlay(N, 0, new boolean[N][M]);
            return;
        }
        for(int i=start; i<M; i++) {
            archerPos[cnt] = i;
            comb(cnt+1, i+1);
        }
    }
    public static void doPlay(int line, int count, boolean h[][]) {
        if(line == 0) {
            if(count > MAX_COUNT) {
                MAX_COUNT = count;
            }
            return;
        }
        int hunted[][] = new int[3][3]; // [공격한 적 x좌표, 공격한 적 y좌표, 거리]
        for(int i=0; i<3; i++) {
            Arrays.fill(hunted[i], -1);
            hunted[i][2] = D+1;
        }
        // 마지막 라인부터 위로 진행 = 적들이 한칸씩 아래로 내려오는 효과
        for(int i=line-1, max = (line > D ? line-D : 0); i >= max; i--) {
            // 높이
            for(int j=0; j<M; j++) {
                if(map[i][j] == 1 && !h[i][j]) {
                    for(int k=0; k<3; k++) {
                        int dis = getDistance(i, j, line, archerPos[k]);
                        // 이전에 정해놓은 적 보다 거리는 같지만, 좀 더 왼쪽인 경우 갱신
                        if(dis == hunted[k][2] && j < hunted[k][0]) {
                            hunted[k][0] = j;
                            hunted[k][1] = i;
                            hunted[k][2] = dis;
                        }
                        // 이전에 정해놓은 적 보다 거리가 가까운 경우 갱신
                        if(dis < hunted[k][2]) {
                            hunted[k][0] = j;
                            hunted[k][1] = i;
                            hunted[k][2] = dis;
                        }
                    }
                }
            }
        }
        for(int i=0; i<3; i++) {
            if(hunted[i][0] == hunted[i][1] && hunted[i][0] == -1) continue;
            if(!h[hunted[i][1]][hunted[i][0]]) {
                count++;
                h[hunted[i][1]][hunted[i][0]] = true;
            }
        }
//		Queue<Point> queue = new LinkedList<Point>();
//		boolean v[][] = new boolean[N][M];
//		for(int i=0; i<3; i++) {
//			int t = 1;
//			queue.offer(new Point(archerPos[i], line-1));
//			loop:
//			while(t <= D && !queue.isEmpty()) {
//				int queueSize = queue.size();
//				while(queueSize-->0) {
//					Point now = queue.poll();
//					if(map[now.y][now.x] == 1) {
//						shoted[i] = true;
//						if(!h[now.y][now.x]) {
//							h[now.y][now.x] = true;
//							count++;
//						}
//						queue.clear();
//						break;
//					}
//					for(int j=0; j<3; j++) {
//						int mx = now.x + dx[j];
//						int my = now.y + dy[j];
//						if(mx >= M || my >= N || mx < 0 || my < 0) continue;
//						if(v[my][mx]) continue;
//						v[my][mx] = true;
//						queue.offer(new Point(mx, my));
//					}
//				}
//				t++;
//			}
//		}
        doPlay(line-1, count, h);
    }
}
