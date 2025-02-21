package SWEA;

// 1954. 달팽이 숫자

import java.util.Scanner;

public class SWEA_1954 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt(), N = 0;
        int cnt = 1, arr[][];
        int nowX, nowY;
        // 우, 하, 좌, 상
        int dx[] = new int[] {1, -1};
        int dy[] = new int[] {1, -1};
        for(int tc=1; tc<=T; tc++) {
            N = sc.nextInt();
            arr = new int[N][N];
            nowX = 0;
            nowY = 0;
            cnt = 1;
            while(cnt < N*N) {
                // 우
                while(nowX + dx[0]< N && (arr[nowY][nowX + dx[0]] == 0)) {
                    arr[nowY][nowX] = cnt;
                    nowX += dx[0];
                    cnt++;
                }
                // 하
                while(nowY + dy[0] < N && arr[nowY + dy[0]][nowX] == 0) {
                    arr[nowY][nowX] = cnt;
                    nowY += dy[0];
                    cnt++;
                }
                // 좌
                while(nowX + dx[1] >= 0 && arr[nowY][nowX + dx[1]] == 0) {
                    arr[nowY][nowX] = cnt;
                    nowX += dx[1];
                    cnt++;
                }
                // 상
                while(nowY + dy[1] >= 0 && arr[nowY + dy[1]][nowX] == 0) {
                    arr[nowY][nowX] = cnt;
                    nowY += dy[1];
                    cnt++;
                }
            }
            //마지막 지점
//			arr[nowY][nowX] = cnt;
            System.out.println("#"+tc);
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    System.out.printf("%d ",arr[i][j]);
                }
                System.out.println();
            }
        }
    }
}
