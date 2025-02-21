package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 3238. 이항계수 구하기
public class SWEA_3238 {
    static int P;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String strArr[];
        for(int tc = 1; tc <= T; tc++) {
            strArr = br.readLine().split(" ");
            long N = Long.parseLong(strArr[0]);
            long R = Long.parseLong(strArr[1]);
            P = Integer.parseInt(strArr[2]);
            long factorial[] = new long[P+1];
            factorial[0] = 1;
            for(int i=1; i<=P; i++) factorial[i] = (factorial[i-1] * i) % P;
            // 뤼카의 정리 활용
            // N과 R을 P에 대해 P진 전개
            // => N과 R을 P진수로 나타내는것과 비슷
            // => N과 R을 P진 전개해 얻은 각 자리수를 nCr의 n과 r로 넣어 구한뒤 곱하고
            // P로 나눈 나머지를 계속 누적함
            long ans = 1;
            int nDigit, rDigit;
            while(N != 0 || R != 0) {
                if(N == 0 && N == R) {
                    ans = 1;
                    break;
                }
                nDigit = (int) (N % P);
                N /= P;
                rDigit = (int) (R % P);
                R /= P;
                if(nDigit < rDigit) {
                    ans = 0;
                    break;
                } else {
                    // 페르마의 소정리를 이용해 nCr ( mod P ) 구하는 과정
                    // n! * ((n-r)!r!) ^ (N-2)
                    ans *= factorial[nDigit];
                    ans %= P;
                    ans *= power(factorial[nDigit - rDigit], P-2);
                    ans %= P;
                    ans *= power(factorial[rDigit], P-2);
                    ans %= P;
                }
            }
            System.out.printf("#%d %d\n", tc, ans);
        }
    }
    private static long power(long l, int i) {
        if(i == 0) return 1;
        long half = power(l, i/2);
        long whole = half * half % P;
        if(i % 2 == 0) return whole;
        else return whole*l % P; // 지수가 홀수일 경우 한번 더 곱함
    }
}
