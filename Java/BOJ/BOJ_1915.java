package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1915 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        int n = Integer.parseInt(strArr[0]);
        int m = Integer.parseInt(strArr[1]);
        int map[][] = new int[n][m];
        // dp[i][j] : (i, j) 위치를 정사각형의 오른쪽 아래 끝 점으로 하는 제일 긴 정사각형 한 변의 길이
        int dp[][] = new int[n][m];
        // 가장 긴 정사각형의 한 변의 길이 저장하는 변수
        int max = 0;
        for(int i=0; i<n; i++) {
            String str = br.readLine();
            for(int j=0; j<m; j++) {
                map[i][j] = Character.digit(str.charAt(j), 10);
                // 만약 현재 위치가 1, 즉 정사각형을 구성할 수 있는 경우
                if(map[i][j] == 1) {
                    // i, j 모두 1 이상, 즉 정사각형 크기가 2이상일 수 있는 경우
                    if(i > 0 && j > 0) {
                        // 왼쪽, 왼쪽 위, 위의 정사각형 크기 중 최솟값 + 1
                        // 정사각형 모양을 생각해보면, 가장 오른쪽 아래 끝 점을 빼게되면
                        // 끝 점의 왼쪽, 왼쪽 위, 위를 끝점으로하는 3개의 1칸씩 더 작은 정사각형이 존재함을 알 수 있음
                        // 이를 활용했기 때문에 왼쪽, 왼쪽 위, 위의 크기 중 가장 최솟값이 뭔지 계산하는 것
                        dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j-1])) + 1;
                    } else {
                        // (0, j) 또는 (i, 0) 은 한 칸짜리 정사각형 밖에 만들 수 없다.
                        dp[i][j] = 1;
                    }
                    // 최대 길이 갱신 가능한지 확인 후 갱신
                    if(max < dp[i][j]) max = dp[i][j];
                }
            }
        }
        // 가장 큰 정사각형의 넓이 출력
        System.out.println(max * max);
    }
}
