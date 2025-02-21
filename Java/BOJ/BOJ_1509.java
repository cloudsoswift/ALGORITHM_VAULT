package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1509. 팰린드롬 분할

// 어떤 알고리즘 쓰는지 찾아본 문제
// + 그냥 답까지 찾아본 문제
// ============ 원 아이디어 ===============
// 1. 각 알파벳이 나타나는 위치를 저장하는 pos 2차원 배열을 둚 ( [26][N] )
// 2. 각 알파벳이 나온 갯수를 저장하는 idx 1차원 배열을 둚 ( [26] )
// 3. 문자열 처음부터 시작, 각 위치의 알파벳에 대해 나타나는 위치 배열 확인
// 4. 해당 배열에서 현재 idx 보다 높은 위치값 지닌 값부터 시작해서 팰린드롬 여부 검사
// 위 아이디어의 문제점 -> 시간 초과할 엣지 케이스가 너무 많음
// 그래서 Manacher's algorithm을 통해 각 idx에서의 팔린드롬 길이 배열에 저장한 뒤, pq로 팬린드롬 길이
// 긴 순서대로 탐색 하려 했으나 이미 방문한 곳인지 체크할 때 시간초과 날 것 같음
// ============ 답의 아이디어 =============
// 1. dp[i][j] -> i번째 문자부터 j번째 문자까지의 부분 문자열이 팰린드롬을 이루는지 여부 저장하는 배열
// 길이가 1인 문자열 -> 그 자체로 팰린드롬이므로 dp[1][1], dp[2][2], ... 는 전부 true 이다
// 길이가 2인 문자열 -> str[A]와 str[A+1]이 서로 같은 문자열이면 팰린드롬 문자열이다
// 길이가 3 이상인 문자열 -> 위치 A부터 B까지의 부분 문자열이 팰린드롬인지 검사한다면,
// str[A]==str[B] 이면서, dp[A+1][B-1]이 true라면 해당 부분 문자열은 팰린드롬일 것이다.
public class BOJ_1509 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char strArr[] = br.readLine().toCharArray();
        int N = strArr.length;
        // pd[i][j] ->  i 번째 문자 부터 j 번째 문자까지의 부분 문자열이 팰린드롬을 이루는지 여부 저장하는 배열
        boolean pd[][] = new boolean[N][N];
        // dp[i] -> i번째 문자 까지의 부분 문자열(길이 i)의 팰린드롬 분할 갯수 최솟값 저장하는 배열
        int dp[] = new int[N];
        for(int i=0; i<N; i++) {
            // 길이가 1인 부분 문자열들 팰린드롬 처리 
            pd[i][i] = true;
            if(i<N-1) {
                // 길이가 2인 부분 문자열들 팰린드롬 처리
                if(strArr[i] == strArr[i+1]) {
                    pd[i][i+1] = true;
                }
            }
        }
        // 길이가 3 이상인 부분 문자열들 팰린드롬 처리
        for(int len = 3; len <= N; len++) {
            for(int i=0; i+len-1 < N; i++) {
                int e = i+len-1;
                if(strArr[i] == strArr[e] && pd[i+1][e-1]) {
                    pd[i][e] = true;
                }
            }
        }
        dp[0] = 1;
        // 길이 e인 부분 문자열의 팰린드롬 분할 개수 최솟값 계산
        for(int e=1; e<N; e++) {
            // 일단 가능한 최댓값(해당 문자열의 길이) 저장해놓고 탐색
            dp[e] = e+1;
            // pd[0][e], pd[1][e], ... pd[e][e] 까지 팰린드롬 되는거 있는지 탐색
            for(int s=0; s<=e; s++) {
                if(pd[s][e] == true) {
                    // s부터 e까지의 부분문자열이 팰린드롬인 경우
                    // s-1 문자 까지의 팰린드롬 분할 최소값 + 1 과, 기존의 값 중 최소 인 것으로 갱신
                    if(s == 0) {
                        dp[e] = Math.min(dp[e], 1);
                    } else {
                        dp[e] = Math.min(dp[e], dp[s-1]+1);
                    }
                }
            }
        }
        // 문자열의 팰린드롬 분할 갯수의 최솟값 출력
        System.out.println(dp[N-1]);
    }
}
