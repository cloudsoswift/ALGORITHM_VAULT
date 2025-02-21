package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 5607. [Professional] 조합
public class SWEA_5607 {
    static int MOD = 1234567891;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String strArr[];
        for(int tc = 1; tc <= T; tc++) {
            strArr = br.readLine().split(" ");
            int N = Integer.parseInt(strArr[0]);
            int R = Integer.parseInt(strArr[1]);
            long factorial[] = new long[N+1];
            factorial[0] = 1;
            for(int i=1; i<=N; i++) factorial[i] = (factorial[i-1] * i) % MOD;
            // nCr = n!/((n-r)!r!)
            // nCr mod N => n! * ((n-r)!r!) ^ (N-2)
            // ((n-r)!r!) ^ (N-2)를 구하는 부분
            long powered = power((factorial[N-R] * factorial[R]) % MOD, MOD-2);
            System.out.printf("#%d %d\n", tc, (factorial[N] * powered) % MOD);
        }
    }
    private static long power(long l, int i) {
        if(i == 0) return 1;
        long half = power(l, i/2);
        long whole = half * half % MOD;
        if(i % 2 == 0) return whole;
        else return whole*l % MOD; // 지수가 홀수일 경우 한번 더 곱함
    }
}
