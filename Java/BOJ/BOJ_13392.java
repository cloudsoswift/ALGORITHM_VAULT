package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_13392 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String from = br.readLine();
        String to = br.readLine();
        // dp[i][j]: `숫자나사 i+1` 에 대해, 이전나사까지 왼쪽으로 j번 회전시킨 상태에서,
        // 현재 나사가 가리키고 있는 수(from[i] + j % 10)를 목표 수(to[i])로 만들기 위해
        // 필요한 회전의 최소값을 저장
        // 이때, j는 0~9까지 범위로 관리하는데, 이전 나사들에서 10번 이상 회전해도 실제로는 0번(10번), 1번(11번)
        // 한 것과 현재 위치가 가리키는 값은 차이가 없기 때문
        int[][] dp = new int[N+1][10];
        for(int i=0; i<=N; i++) {
            for(int j=0; j<10; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<10; j++) {
                if (dp[i][j] == Integer.MAX_VALUE) continue;
                int current = (Character.digit(from.charAt(i), 10) + j) % 10;
                int target = Character.digit(to.charAt(i), 10);

                int left, right;
                if(current > target) {
                    left = target - current + 10;
                    right = current - target;
                } else if(current < target) {
                    left = target - current;
                    right = current - target + 10;
                } else {
                    left = right = 0;
                }
                // 왼쪽으로 돌릴때
                dp[i+1][(j + left) % 10] = Math.min(dp[i+1][(j + left) % 10], dp[i][j] + left);
                // 오른쪽으로 돌릴때
                dp[i+1][j] = Math.min(dp[i+1][j], dp[i][j] + right);
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int i=0; i<10; i++) {
            ans = Math.min(ans, dp[N][i]);
        }
        System.out.println(ans);
    }
}
