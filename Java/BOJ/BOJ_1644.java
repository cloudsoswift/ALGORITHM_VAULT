package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1644. 소수의 연속합
public class BOJ_1644 {
    static List<Integer> primes;
    static boolean isPrime[];
    static int N;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        primes = new ArrayList<Integer>();
        isPrime = new boolean[N+1];
        Arrays.fill(isPrime, true);
        countPrime();
        for(int i=2; i<=N; i++) {
            if(isPrime[i]) primes.add(i);
        }
        // N을 만들 수 있는 연속된 소수의 합 경우의 수 cnt
        int cnt = 0;
        int primesLen = primes.size();
        // 투 포인터 사용을 위한 포인터 두 개 start, end
        int start = 0, end = 0;
        // 투 포인터를 통해 누적한 연속된 소수의 합 sum
        int sum = 0;
        while(end < primesLen && start <= end) {
            // N보다 작을 경우 연속된 소수 집합에 소수 하나 추가
            if(sum < N) {
                sum += primes.get(end++);
            }
            // N보다 클 경우 연속된 소수 집합의 맨 앞 소수 빼기
            if(start <= end && sum > N) {
                sum -= primes.get(start++);
            }
            // N과 같아진 경우 맨 앞의 소수 뺌
            if(sum == N) {
                cnt++;
                sum -= primes.get(start++);
            }
        }
        System.out.println(cnt);
    }
    // 소수 구하는 함수 (에라토스테네스의 체 활용)
    public static void countPrime() {
        if(N >= 2) {
            isPrime[2] = true;
        }
        if(N >= 3) {
            isPrime[3] = true;
        }
        // 2부터 N^(1/2)까지 해당 수가 소수이면 그 수의 곱들은 전부 소수 아닌것으로 표기
        for(int i=2, max=(int)Math.round(Math.sqrt(N)); i<=max; i++) {
            if(isPrime[i] == true) {
                for(int j=i+i; j<=N; j+=i) {
                    isPrime[j] = false;
                }
            }
        }
    }
}
