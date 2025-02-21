package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 11726. 2xn 타일링
public class BOJ_11726 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int dp[] = new int[N+1];
        for(int i=0; i<N+1; i++) {
            if(i==0||i==1) {
                dp[i] = 1;
            }
            if(i-2 >= 0) {
                dp[i] = (dp[i-1] + dp[i-2]) % 10007;
            }
        }
        System.out.println(dp[N]);
    }
}
