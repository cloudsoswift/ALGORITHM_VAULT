package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 2073. 수도배관공사
public class BOJ_2073 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        int D = Integer.parseInt(strArr[0]);
        int P = Integer.parseInt(strArr[1]);
        int[] dp = new int[D+1];
        // pipes[i][0] : i번째 파이프의 길이 / pipes[i][1] : i번째 파이프의 용량
        // 사실상 필요 없긴 함(pipes에 저장해 두고 따로 쓰질 않아서)
        int[][] pipes = new int[P][2];
        for(int i=0; i<P; i++) {
            strArr = br.readLine().split(" ");
            pipes[i][0] = Integer.parseInt(strArr[0]);
            pipes[i][1] = Integer.parseInt(strArr[1]);
            for(int j=D; j >= pipes[i][0]; j--) {
                if(j == pipes[i][0]) {
                    dp[j] = Math.max(dp[j], pipes[i][1]);
                } else {
                    if(dp[j - pipes[i][0]] != 0) {
                        dp[j] = Math.max(dp[j], Math.min(dp[j - pipes[i][0]], pipes[i][1]));
                    }
                }
            }
        }
        System.out.println(dp[D]);
    }
}
