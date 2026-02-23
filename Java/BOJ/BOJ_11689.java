package BOJ;

// GCD(n, k) = 1, 즉 두 수 n, k간의 최대공약수가 1을 만족하는 1 이상 n 이하의 모든 k를 찾아야 함.

// 알고리즘 분류 보고 푼 문제

// 위 문제는 오일러 피(파이) 함수를 사용해야 함.
// 오일러 피 함수란, phi(n) 과 같은 형태로 나타내며, 1부터 n까지, n과 서로수인 수들의 개수를 말함.
// 오일러 피 함수는 곱셈적 함수( f(mn) = f(m)f(n) (이때, m과 n은 서로소) 과 같이 서로소인 두 정수 간 곱셈을 보존하는 함수 ) 이므로,
// 이를 활용하면 작은 수에 대해 구한 오일러 피 함수 값들의 곱을 통해 더 큰 수의 오일러 피 함수 값을 구할 수 있음.

// 또한, 임의의 소수 a에 대해, 다음과 같은 성질을 갖고 있음.
// phi(a^n) = a^n - a^{n-1}
// a^n의 소인수는 a밖에 없으므로, phi(a^n)을 구할 땐 a^n 에서 a의 배수의 개수만큼만 빼주면 됨.
// 이때, a^n 이하인 a의 배수의 개수는 a^{n-1} 임.
// (ex. 2^4(16)보다 작은 2의 배수의 개수 = 2^{4-1} = 8 (2, 4, 6, 8, 10, 12, 14, 16))
// 1 ~ 16까지의 수에서 위 수들을 뺴주면 나머지는 16과 서로소임. (1, 3, 5, 7, 9, 11, 13, 15)

// 또한, 임의의 소수 a에 대해 다음과 같은 성질이 있음
// phi(a) = a - 1
// a 이하의 수 중 a와 소인수인 수는 a를 제외한 모든 수이기 때문.
// (ex. phi(11) = 10 (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// 위 성질들을 모두 합하면, 오일러 피 함수를 쉽게 구할 수 있음
// 1. 임의의 수 n을 소인수분해 함(a_1^{b_1} * a_2^{b_2} * ... * a_k^{b_k}
// 이때, 각각의 소수(a_1, a_2, ... )는 서로소이므로 오일러 피 함수의 곱셈적 성질을 활용할 수 있음.
// 즉, phi(n) = phi(a_1^{b_1}) * phi(a_2^{b_2}) * ... * phi(a_k^{b_k}) 임.
// 2. 이때, 각각의 수 phi(a_k^{b_k})는 앞서 본 `phi(a^n)` 성질을 활용하면 됨.
// 즉, phi(a_k^{b_k}) = a_k^{b_k} - a_k^{b_k - 1}이 됨.
// 3. 따라서, 먼저 n을 소인수분해한 뒤, 각각의 phi를 구해 곱으로 누적하면 그 값이 phi(n)이 됨.


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 11689. GCD(n, k) = 1
public class BOJ_11689 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n이 최대 10^12(1조)가 될 수 있으므로 long 타입으로 받음
        long n = Long.parseLong(br.readLine());
        long result = 1;
        for(long i=2; i*i<=n; i++) {
            // 2이상인 수 중, n의 소인수가 있는 경우
            if(n % i == 0) {
                // 해당 소인수의 지수만큼 n을 나눠주고, 지수 카운트
                int exponent = 0;
                while(n % i == 0) {
                    n /= i;
                    exponent++;
                }
                // phi(a^n) = a^n - a^{n-1} << 이 값을 결과 값에 곱으로 누적
                result *= (long) (Math.pow(i, exponent) - Math.pow(i, exponent - 1));
            }
        }
        if(n > 1) {
            // 남은 소인수가 있는 경우, 해당 소인수의 phi를 곱으로 누적
            result *= n - 1;
        }
        System.out.println(result);
    }
}