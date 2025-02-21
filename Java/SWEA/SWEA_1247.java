package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 1247. [S/W 문제해결 응용] 3일차 - 최적 경로
public class SWEA_1247 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    static Point home, office, customers[];
    static int SHORTEST_DISTANCE, N;
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_1247.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[];
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine());
            customers = new Point[N];
            SHORTEST_DISTANCE = Integer.MAX_VALUE;
            str = br.readLine().split(" ");
            office = new Point(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
            home = new Point(Integer.parseInt(str[2]), Integer.parseInt(str[3]));
            for(int i=0; i<N; i++) {
                customers[i] = new Point(Integer.parseInt(str[4+2*i]), Integer.parseInt(str[4+2*i+1]));
            }
            perm(office, 0, 0, 0);
            System.out.printf("#%d %d\n", test_case, SHORTEST_DISTANCE);
        }
    }
    // 방문할 고객 순서를 순열로 정함.
    static void perm(Point before, int cnt, int v, int sum) {
        if(cnt==N) {
            int differ = Math.abs(before.x - home.x) + Math.abs(before.y - home.y);
            if(SHORTEST_DISTANCE > (differ+sum)) {
                SHORTEST_DISTANCE = differ + sum;
            }
            return;
        }
        for(int i=0; i<N; i++) {
            if((v & (1<<i)) != 0) continue;
            // 이전 위치와 거리차이 계산
            int differ = Math.abs(before.x - customers[i].x) + Math.abs(before.y - customers[i].y);
            if(sum+differ > SHORTEST_DISTANCE) continue; // 최단거리보다 현재 거리합이 크면 진행 X
            perm(customers[i], cnt+1, v|(1<<i), sum + differ);
        }
    }
}
