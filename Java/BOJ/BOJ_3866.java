package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 3866. 풍선 수집
public class BOJ_3866 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr;
        int MAXIMUM = 1_000_000_000;
        while(true) {
            int N = Integer.parseInt(br.readLine());
            if(N == 0) break;
            // dp[i][j] -> i번째 풍선을 받았을 때, 현재 j개의 풍선을 들고 있을 수 있는 최소 이동거리
            int[][] dp = new int[N+1][4];
            for(int i=0; i<=N; i++) {
                Arrays.fill(dp[i], MAXIMUM);
            }
            int[][] ballons = new int[N][2];
            int minTime = Integer.MAX_VALUE;
            for(int i=0; i<N; i++) {
                strArr = br.readLine().split(" ");
                // 풍선의 위치 pi
                ballons[i][0] = Integer.parseInt(strArr[0]);
                // 땅에 닿는 시간 ti
                ballons[i][1] = Integer.parseInt(strArr[1]);
            }
            if(ballons[0][0] > ballons[0][1]) {
                System.out.println("NG 1");
                continue;
            }
            dp[0][1] = ballons[0][0];
            for(int i=1; i<N; i++) {
                // 이전 i번 풍선(ballons[i-1])을 받고, i+1번 풍선(ballons[i])을 받으러 가는 케이스 검사
                int distance = Math.abs(ballons[i][0] - ballons[i-1][0]);
                int timeGap = ballons[i][1] - ballons[i-1][1];
                boolean possible = false;
                for(int j=1; j<=3; j++) {
                    // 이전 풍선을 받았을 때, 풍선을 j개 들고있는 상황들을 다 확인
                    if(dp[i-1][j] != MAXIMUM) {
                        // 1. 풍선들 들고있는 채로 다음 풍선 가기
                        // 이때, 이전 풍선 위치에서 현재 풍선 위치까지 시간 갭 이내에 이동할 수 있어야 함
                        if(j < 3 && distance * (j+1) <= timeGap) {
                            dp[i][j+1] = Math.min(dp[i][j+1], dp[i-1][j] + distance);
                            possible = true;
                        }
                        // 2. 집에 들렀다가 풍선으로 가기
                        // 이때, 이전 풍선 위치에서 집까지 갔다가(ballons[i-1][0] * (j+1)),
                        // 현재 풍선 위치까지 오는데에(ballons[i][0]) 시간 갭 이내에 이동할 수 있어야 함
                        if(ballons[i-1][0] * (j+1) + ballons[i][0] <= timeGap) {
                            dp[i][1] = Math.min(dp[i][1], dp[i-1][j] + ballons[i-1][0] + ballons[i][0]);
                            possible = true;
                        }
                    }
                }
                if(!possible) {
                    System.out.printf("NG %d\n", i + 1);
                    break;
                }
            }
            // 위 순회를 끝내면, dp[N-1][1~3]에 마지막 풍선 위치까지의 최소거리들이 기록되어 있음
            // 이때, 마지막 풍선에서 집까지 오는거리(ballons[N-1][0])까지 더해서 계산해야 함.
            for(int i=1; i<4; i++) {
                if(dp[N-1][i] != MAXIMUM)
                    minTime = Math.min(minTime, dp[N-1][i] + ballons[N-1][0]);
            }
            // 위에서 NG를 찍었을 경우, minTime이 초기값 그대로일테므로
            // 아래와 같이 필터링하여 OK를 찍지 않도록 해야함.
            if(minTime != Integer.MAX_VALUE) {
                System.out.printf("OK %d\n", minTime);
            }
        }
    }
}
