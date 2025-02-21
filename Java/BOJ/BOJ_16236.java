package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 16236. 아기 상어
public class BOJ_16236 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    static int N, map[][], size, eatCount, posX, posY, dx[], dy[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        int v[][] = new int[N][N];
        posX=0;
        posY=0;
        size = 2;
        eatCount = 0;
        String str[];
        // [ 동, 서, 남, 북 ]
        dx = new int[] { 1, -1, 0, 0 };
        dy = new int[] { 0, 0, 1, -1 };
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(str[j]);
                if(map[i][j] == 9) {
                    posX = j;
                    posY = i;
                    map[i][j] = 0;
                }
            }
        }
        Point target = search_bfs();
        if(target == null) {
            System.out.println(0);
            return;
        }
        int time = 0;
        Queue<Point> queue = new LinkedList<Point>();
        queue.offer(new Point(posX, posY));
        loop:
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            while(queueSize-->0) {
                Point p = queue.poll();
                if(p.x == target.x && p.y == target.y) {
                    eatCount++;
                    if(eatCount == size) {
                        size++;
                        eatCount = 0;
                    }
                    map[p.y][p.x] = 0;
                    posX = p.x;
                    posY = p.y;
                    queue.clear();
                    queue.offer(new Point(p.x, p.y));
                    target = search_bfs();
                    v = new int[N][N];
                    if(target == null) break loop;
                    continue loop;
                }
                for(int i=0; i<4; i++) {
                    int mx = p.x + dx[i];
                    int my = p.y + dy[i];
                    if(mx >= N || my >= N || mx < 0 || my < 0) continue;
                    if(map[my][mx] > size) continue;
                    if(v[my][mx] == 1) continue;
                    v[my][mx] = 1;
                    queue.offer(new Point(mx, my));
                }
            }
            time++;
        }
        System.out.println(time);
    }
    static Point search_bfs() {
        int cX = -1, cY = -1, dist = Integer.MAX_VALUE;
        Queue<Point> q = new LinkedList<>();
        q.offer(new Point(posX, posY));
        boolean v[][] = new boolean[N][N];
        int time = 0;
        while(!q.isEmpty()) {
            int qSize = q.size();
            while(qSize-->0) {
                Point p = q.poll();
                if(map[p.y][p.x] != 0 && map[p.y][p.x] < size) {
                    if(time < dist) {
                        cX = p.x;
                        cY = p.y;
                        dist = time;
                    } else if(time == dist) {
                        if(p.y < cY) {
                            cX = p.x;
                            cY = p.y;
                            dist = time;
                        } else if(p.y == cY) {
                            if(p.x < cX) {
                                cX = p.x;
                                cY = p.y;
                                dist = time;
                            }
                        }
                    }
                }
                for(int i=0; i<4; i++) {
                    int mx = p.x + dx[i];
                    int my = p.y + dy[i];
                    if(mx >= N || my >= N || mx < 0 || my < 0) continue;
                    if(map[my][mx] > size) continue;
                    if(v[my][mx]) continue;
                    v[my][mx] = true;
                    q.offer(new Point(mx, my));
                }
            }
            time++;
        }
        if(cX == -1) return null;
        return new Point(cX, cY);
    }
//	문제에 제시된 대로가 아닌, 단순히 x값 차이 + y값 차이가 적은 걸 먼저 가게 했었음.
//	static Point search() {
//		int cX = -1, cY = -1, dist = -1;
//		for(int i=0; i<N; i++) {
//			for(int j=0; j<N; j++) {
//				if(map[i][j] != 0 && map[i][j] < size) {
//					if(cX == -1) {
//						cX = j;
//						cY = i;
//						dist = Math.abs(posX-cX) + Math.abs(posY - cY);
//						continue;
//					}
//					int differ = Math.abs(posX-j) + Math.abs(posY-i);
//					if(differ < dist) {
//						cX = j;
//						cY = i;
//						dist = differ;
//					}
//				}
//			}
//		}
//		if(cX == -1)	return null;
//		System.out.println(cY + ", " + cX + ", " + map[cY][cX] + " vs " + size);
//		return new Point(cX, cY);
//	}
}
