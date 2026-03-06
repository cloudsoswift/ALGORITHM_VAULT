package BOJ;

// Gemini 도움받아 아이디어 수정한 문제

// ================ 원 아이디어 =================
// 1. 1 ~ N/2(N = 문자열 길이)까지, 부분문자열 a의 크기를 한칸씩 늘림.
//  부분문자열 a_i는 문자열 s를 맨 앞부터 i자만 뽑아낸 부분 문자열.
// 2. 뽑아낸 부분문자열 a_i가 s에서 몇 번 등장하는지 KMP 기법을 사용해 계산
//  위 작업 중, 부분문자열과 문자열에서 일치하지 않는 경우 발생시 0회 반환하도록 설정
// 3. 만약 0이 아닌 수를 반환한 경우 해당 값을 바로 결과 값으로 출력

// ================ 위 아이디어의 문제점 ==========
// 부분문자열 a_i를 만들 때 마다 이를 KMP 기법을 사용해 매 번 s를 순회하는게 반복되기 때문에 작업 시간이 초과됨
// 그리고 이를 Gemini를 통해 알아낸, 상태변이함수의 규칙을 사용해 단축함

// ================ 답 아이디어 ==================
// KMP 기법 사용을 위해 계산해둔, LPS의 마지막 위치, 즉 LPS[len-1]에는 s의 최장 접두 및 접미사의 길이가 기록되어 있음.
// 따라서, 전체 길이에서 해당 값을 뺀 `마디 후보 길이`(즉, `len - LPS[len-1]`)를 추출
// 만약, 해당 값이 L의 배수라면, 즉  `len % patternLen = 0` 이라면 문자열 S는 특정 마디 a가 `len / patterLen` 번 반복된 형태라는 뜻
// 즉, 위 상황에서 patterLen은 '반복되는 마디 중 가장 작은 것의 길이'를 나타냄

// ----- 규칙 -----
// `마디 후보 길이`가 len의 약수라는 것은 접두/접미사 길이(LPS[len-1])가 적어도 len의 절반은 되어야 함.
// (그래야 2 이상의 수를 곱했을 때 len이 될 수라도 있기 때문)
// 이때, 접두/접미사 길이가 원 문자열의 절반 이상이라는 것은 다음을 의미
// (0 ~ len - 1 - LPS[len-1]) 구간, (LPS[len-1] ~ len-1) 구간의 문자열이 같음. 즉, 최소 2번 이상 반복되는 패턴이 하나는 있음
// ex) len = 10, LPS[len-1] = 5 라면 0~4 / 5~9 구간이 같음. len = 9, LPS[len-1] = 6라면, 0~5 / 3~8 구간이 같음.
// 여기에 남은 구간 길이(len -  LPS[len-1])가 len으로 나누어 떨어진다는 조건까지 합치면, 중간에 반복이 깨지는 마디 구간이 없음을 의미함.
// 예를 들어, len = 12일때 남은 구간 길이가 3일때와 5일때를 비교해보겠음
// ========== ex) len = 12, LPS[len-1] = 7, len - LPS[len-1] = 5 (예를 들어, abcdeabcdeab)
// 이때, s = a^n 이려면 접두(또는 접미)를 뺀 구간(즉, 마디 후보 구간)이 접두/접미사와 같거나, 접두/접미사의 부분문자열이어야 함.
// 하지만, 마디 후보 구간이 접두/접미사와 같다면 LPS[len-1] == len-LPS[len-1], 즉 앞 뒤가 같은 경우이고,
// 접두/접미사의 부분문자열일려면 len-LPS[len-1] 이 LPS[len-1]로 나누어 떨어져야 함.
// 그리고 결정적으로, 남은 마디 후보 구간을 반복해서 원 문자열 s를 만들려면, 마디 후보 구간 길이가 s의 길이로 나누어 떨어져야 하는데 그렇지 못함.
// ========== ex) len = 12, LPS[len-1] = 8, len - LPS[len-1] = 4 (예를 들어, abcdabcdabcd)
// 위 케이스에서, 접두사와 접미사는 4자만큼 겹침 ( 접두/접미사를 ㅅ, 그 외를 ㅁ라고 표기할 경우, 다음과 같음)
// ㅅㅅㅅㅅ[ㅅㅅㅅㅅ]ㅁㅁㅁㅁ / ㅁㅁㅁㅁ[ㅅㅅㅅㅅ]ㅅㅅㅅㅅ
// 따라서, 접두/접미에서 겹치는 [ㅅㅅㅅㅅ]가 접두사/접미사에서 반복적으로 나타남을 알 수 있음
// 전체 문자열을 구간 1, 2, 3, 즉 [ㅅㅅㅅㅅ][ㅅㅅㅅㅅ][ㅅㅅㅅㅅ] 로 나눈다고 하면,
// 접두 = 접미 이므로
// 구간 1과 구간 2가 같아야 하고, (s1[0] == s2[0] (s[0] == s[4]), s1[1] == s2[1] (s[1] == s[5]), ... )
// 구간 2와 구간 3이 같아야 함. (s1[4] == s2[4] (s[4] == s[8]), s1[5] == s2[5] (s[5] == s[9]), ... )
// 따라서 연쇄적으로 구간 1 = 구간 2 = 구간 3이 성립하여 s = [ㅅㅅㅅㅅ]^3 임을 알 수 있음.

// 예시1) abcdddabc와 abcabcabc
// abcdddabc -> LPS[len-1] = 3, len - LPS[len-1] = 6 -> len % 6 != 0 -> 패턴 반복되지 않음
// abcabcabc -> LPS[len-1] = 6, len - LPS[len-1] = 3 -> len % 3 = 0 -> 패턴 반복됨 (abc)

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 4354. 문자열 제곱
public class BOJ_4354 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String s = br.readLine();
            if(s.equals(".")) return;
            int n = calcN(s);
            System.out.println(n);
        }
    }
    // LPS(Longest Prefix which is also Suffix)
    // 정수 배열을 반환하며, 배열의 각 값 pi[i]는
    // 해당 위치까지의 부분 문자열(즉, s[0]~s[i]까지의 문자로 이뤄진 문자열)에서
    // 가장 긴 접두사 및 접미사의 길이를 나타냄
    // ex. abaabc에 대해, pi[2] = 1
    // (s[2]까지의 부분 문자열 'aba'에 대해 'a'b'a', 즉 가장 긴 접두 및 접미사 길이는 1)
    public static int[] lps(String s) {
        int n = s.length();
        int[] pi = new int[n];
        for(int i=1, j=0; i < n; i++) {
            while(j > 0 && s.charAt(i) != s.charAt(j))
                // j가 1 이상이고, 현재 i와 j가 일치하지 않는 경우
                // j를 0 또는 상태변이함수에서 i와 일치하는 위치로 이동할 때까지
                // 뒤로 후퇴시킴
                j = pi[j-1];
            if(s.charAt(i) == s.charAt(j))
                pi[i] = ++j;
        }
        return pi;
    }
    public static int calcN(String s) {
        int len = s.length();
        int[] pi = lps(s);
        int patternLen = len - pi[len-1];
//        int halfLen = len / 2;
//        for(int i=1; i<=halfLen; i++) {
//            if(len % i != 0) continue;
//            int count = doKMP(s, len, i, pi);
//            if(count != 0)
//                return count;
//        }
        if(len % patternLen == 0)
            return len / patternLen;
        return 1;
    }
//    public static int doKMP(String s, int N, int size, int[] pi) {
//        int i = 0, j = 0;
//        int repeatedNumber = 0;
//        while (i < N) {
//            if(s.charAt(i) == s.charAt(j)) {
//                i++;
//                j++;
//                if(j == size) {
//                    repeatedNumber++;
//                    j = pi[j - 1];
//                }
//            } else {
//                return 0;
//            }
//        }
//        return repeatedNumber;
//    }
}
