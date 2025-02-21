package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1562. 계단 수

// 모르겠어서 문제 유형, 답 다 찾아본 문제
// ========= 내가 생각한 아이디어 ========
// DFS로 조합 풀듯이 접근
// 이 방식의 문제점 -> N이 커질수록 시간이 너무 오래걸림 (중복되는게 있어서)
// ========= 답안의 아이디어 ===========
// 3차원 배열을 사용해 dp로 계산.
// 첫 번째 차원은 숫자의 자릿 수 길이, 두 번째 차원은 마지막 숫자가 무엇인지(0~9),
// 세 번째 차원은 0~9까지 현재 수에 포함되어 있는지 여부를 비트마스킹으로 나타내고, 그것을 인덱스로 사용하는 배열
// 즉, 길이가 10이고, 0~9까지 모두 체크된 수를 확인하고 싶으면, [10][i][1023] (i는 0~9까지 증가하는 숫자)
// 의 값들을 합쳐보면 됨 ( 1023 -> 1 << 0 ~ 1 << 9 까지 다 or 연산한 값. 2진수로 보면 모든 비트가 1이다)

public class BOJ_1562 {
    static final int MOD = 1000000000;
    static int N, count, dp[][][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        // i자리 수의 마지막 숫자가 j인 경우 ( k(비트마스킹)만큼 만족 되어있는 경우 ) 의 수를 저장하는 배열 dp 
        dp = new int [N+1][10][(1<<10) + 1];
        for(int i=1; i<=9; i++) {
            dp[1][i][1 << i] = 1;
        }
        if(N < 10) {
            System.out.println(0);
            return;
        }
        for(int i=2; i<=N; i++) {
            for(int j=0; j<=9; j++) {
                for(int k=0; k<1024; k++) {
                    int b = k | 1 << j;
                    if(j == 0) {
                        dp[i][j][b] = (dp[i][j][b] + dp[i-1][j+1][k]) % MOD;
                    } else if(j == 9) {
                        dp[i][j][b] = (dp[i][j][b] + dp[i-1][j-1][k]) % MOD;
                    } else {
                        dp[i][j][b] = (dp[i][j][b] + dp[i-1][j-1][k] + dp[i-1][j+1][k]) % MOD;
                    }
                }
            }
        }
        count = 0;
        for(int i=0; i<=9; i++) {
            count += dp[N][i][1023];
        }
        System.out.println(count);
    }
//	public static void search(int len, int visited, int lastNum) {
//		if(len == N) {
//			for(int i=0; i<=9; i++) {
//				if((visited & 1 << i) == 0) {
//					return;
//				}
//			}
//			count++;
//			return;
//		}
//		if(lastNum == 0) {
//			search(len + 1, visited | 1 << (lastNum+1), lastNum+1);
//		} else if(lastNum == 9) {
//			search(len + 1, visited | 1 << (lastNum-1), lastNum-1);
//		} else {
//			search(len + 1, visited | 1 << (lastNum+1), lastNum+1);
//			search(len + 1, visited | 1 << (lastNum-1), lastNum-1);
//		}
//	}
}
