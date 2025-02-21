package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 9095. 1, 2, 3 더하기
public class BOJ_9095 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int i=1; i<=T; i++) {
            int N = Integer.parseInt(br.readLine());
            int dp[] = new int[N+1];
            dp[0] = 0;
            if(N>=1)
                dp[1] = 1;
            if(N>=2)
                dp[2] = 1;
            if(N>=3)
                dp[3] = 1;
            for(int j=2; j<=N; j++) {
                if(j-1>=0) {
                    dp[j] += dp[j-1];
                }
                if(j-2>=0) {
                    dp[j] += dp[j-2];
                }
                if(j-3>=0) {
                    dp[j] += dp[j-3];
                }
            }
            System.out.println(dp[N]);
        }
    }
}
