package BOJ;

import java.util.Scanner;

// 2615. 오목
public class BOJ_2615 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] arr = new int[19][19];
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        // { 우측, 우하단, 하단, 우상단 }
        // 탐색을 왼쪽에서 오른쪽, 위에서 아래로 하므로 좌측과 상단은 탐색할 필요 x
        int[] dx = new int[] { 1, 1, 0, 1 };
        int[] dy = new int[] { 0, 1, 1, -1 };
        int mx, my;
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) {
                if(arr[i][j] == 0) {
                    continue;
                }
                if(arr[i][j] == 1) {
                    for(int k=0; k<dx.length; k++) {
                        int cnt = 1;
                        mx = j + dx[k];
                        my = i + dy[k];
                        int nx = j - dx[k];
                        int ny = i - dy[k];
                        //반대편에 무언가 둘 수 있는 경우
                        if(nx >= 0 && nx < arr.length && ny >= 0 && ny < arr.length) {
                            // 반대편에 검은 돌이 똑같이 존재하는 경우
                            if(arr[ny][nx] == 1)
                                continue;
                        }
                        while(mx >= 0 && mx < arr.length && my >= 0 && my < arr.length) {
                            if(arr[my][mx] == 1) {
                                mx += dx[k];
                                my += dy[k];
                                cnt++;
                            } else {
                                break;
                            }
                        }
                        if(cnt==5) {
                            // 반대방향에 무언가 둘수 없는 경우 > 딱 5개인 경우
                            System.out.println("1");
                            System.out.printf("%d %d\n", i+1, j+1);
                            return;
                        }
                    }
                }
                if(arr[i][j] == 2) {
                    for(int k=0; k<dx.length; k++) {
                        int cnt = 1;
                        mx = j + dx[k];
                        my = i + dy[k];
                        int nx = j - dx[k];
                        int ny = i - dy[k];
                        // 반대방향에 무언가 있을수 있는 경우
                        if(nx >= 0 && nx < arr.length && ny >= 0 && ny < arr.length) {
                            //그게 흰 돌인 경우
                            if(arr[ny][nx] == 2) {
                                continue;
                            }
                        }
                        while(mx >= 0 && mx < arr.length && my >= 0 && my < arr.length) {
                            if(arr[my][mx] == 2) {
                                mx += dx[k];
                                my += dy[k];
                                cnt++;
                            } else {
                                break;
                            }
                        }
                        if(cnt==5) {
                            // 반대방향에 무언가 둘수 없는 경우 > 딱 5개인 경우
                            System.out.println("2");
                            System.out.printf("%d %d\n", i+1, j+1);
                            return;
                        }
                    }
                }
            }
        }
        System.out.println(0);
    }
}
