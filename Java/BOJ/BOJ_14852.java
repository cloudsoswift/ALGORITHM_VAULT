package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 14852. 타일 채우기 3
public class BOJ_14852 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long dp[] = new long[N+1];
        dp[1] = 2;
        if(N>=2) dp[2] = 7;
        if(N>=3) dp[3] = 22;
//			tmp = arr[1]~arr[3] * 2
//			tmp += arr[2] + 2

        if(N>=4) {
            long sum = 0;
            sum = dp[1] + dp[2] + dp[3];
            for(int i=4; i<=N; i++) {
                long tmp = 0;
                tmp = (sum * 2) % 1000000007;
                tmp += (dp[i-2] + 2) % 1000000007;
                dp[i] = tmp;
                sum += tmp;
            }
        }
        System.out.println(dp[N] % 1000000007);
    }
}
