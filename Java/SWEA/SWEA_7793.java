package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 7793. 오! 나의 여신님
public class SWEA_7793 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String strArr[];
        String str;
        // [ 동, 서, 남, 북 ]
        int dx[] = new int[] { 1, -1, 0, 0 };
        int dy[] = new int[] { 0, 0, 1 ,-1 };
        Queue<Point> devil = new LinkedList<Point>(), suyeon = new LinkedList<Point>();
        loop:
        for(int test_case = 1; test_case <= T; test_case++) {
            // 이전 케이스에서 큐에 남겨놓은 데이터 clear
            devil.clear();
            suyeon.clear();
            strArr = br.readLine().split(" ");
            int N = Integer.parseInt(strArr[0]);
            int M = Integer.parseInt(strArr[1]);
            char map[][] = new char[N][M];
            boolean v[][] = new boolean[N][M];
            for(int i=0; i<N ;i++) {
                str = br.readLine();
                for(int j=0; j<M; j++) {
                    map[i][j] = str.charAt(j);
                    if(map[i][j] == '*') {
                        devil.offer(new Point(j, i));
                    } else if(map[i][j] == 'S') {
                        suyeon.offer(new Point(j, i));
                        v[i][j] = true;
                    }
                }
            }
            int time = 1;
            while(!suyeon.isEmpty()) {
                // 먼저 악마의 손아귀 퍼져나가며 부식시키기
                int devilSize = devil.size();
                while(devilSize-->0) {
                    Point p = devil.poll();
                    for(int i=0; i<4; i++) {
                        int mx = p.x + dx[i];
                        int my = p.y + dy[i];
                        if(my >= N || mx >= M || my < 0 || mx < 0) continue;
                        // 해당 위치가 빈 칸이거나, 수연의 시작점인 경우, 맵에서 *로 바꾸고 그 위치 queue에 추가
                        if(map[my][mx] == '.' || map[my][mx] == 'S') {
                            devil.offer(new Point(mx, my));
                            map[my][mx] = '*';
                        }
                    }
                }
                int suyeonSize = suyeon.size();
                while(suyeonSize-->0) {
                    Point p = suyeon.poll();
                    for(int i=0; i<4; i++) {
                        int mx = p.x + dx[i];
                        int my = p.y + dy[i];
                        if(my >= N || mx >= M || my < 0 || mx < 0) continue;
                        if(v[my][mx]) continue;
                        if(map[my][mx] != '*' && map[my][mx] != 'X') {
                            if(map[my][mx] == 'D') {
                                // 여신에게 도착한 경우 시간 출력하고 탈출
                                System.out.printf("#%d %d\n", test_case, time);
                                continue loop;
                            }
                            suyeon.offer(new Point(mx, my));
                            v[my][mx] = true;
                        }
                    }
                }
                time++;
            }
            System.out.printf("#%d GAME OVER\n", test_case);
        }
    }
}
