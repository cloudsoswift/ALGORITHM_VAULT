package BOJ;

import java.util.Scanner;

// 2630. 색종이 만들기
public class BOJ_2630 {
    static int N; // 맵사이즈
    static int[][] map;
    static int white, blue;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new int[N][N];
        for(int i = 0; i < N ; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        //구현
        dfs(0,0,N);
        //출력
        System.out.println(white);
        System.out.println(blue);
    }

    static void dfs(int x, int y, int size) {
        //종료조건
        // 현재 영역안의 모든 숫자를 합친다.
        int sum = 0;
        for(int  i = x; i < x+size; i++) {
            for(int j = y; j < y+size; j++) {
                sum += map[i][j];
            }
        }
        // 그 합이 0이면 한장의 흰색종이를 구한것이다.( white 값1 증가)
        if(sum == 0) {
            white++;
            return;
        }
        // 그 합이 size * size랑 같다면 파란색종이 구한것( blue 1 증가)
        if(sum == size * size) {
            blue++;
            return;
        }
        // 그렇지 않다면 현재 종이를 4분할해서 다시 같은 작업을 실행한다.
        int nSize = size/2;
        dfs(x,y, nSize); //좌상
        //원상복구
        dfs(x + nSize, y, nSize); //우상
        //원상복구
        dfs(x, y + nSize, nSize); //좌하
        //원상복구
        dfs(x + nSize, y + nSize, nSize); //우하
        //원상복구
    }
}
