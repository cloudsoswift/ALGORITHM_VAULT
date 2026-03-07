package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 15927. 회문은 회문아니야!!
public class BOJ_15927 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        // 1. 입력받은 문자열이 알파벳 하나로 이뤄져 있는지 확인
        int usedAlphabetsCount = 0;
        boolean v[] = new boolean['Z'-'A' + 1];
        for(int i = 0, len = str.length(); i < len; i++) {
            char c = (char)(str.charAt(i) - 'A');
            if(!v[c]) {
                v[c] = true;
                usedAlphabetsCount++;
            }
        }
        if(usedAlphabetsCount == 1) {
            System.out.println(-1);
            return;
        }
        // 2. 양끝이 같은지 확인
        // 다르면 바로 문자열 길이 출력
        // 만약 같다면, 전체가 회문인지 확인
        // 전체가 회문인 경우 문자열 길이 - 1, 그렇지 않은 경우 문자열 길이 출력
        int left = 0, right = str.length() - 1;
        if (str.charAt(left) != str.charAt(right)) {
            System.out.println(right - left + 1);
        } else {
            int tmpLeft = left, tmpRight = right;
            while(tmpLeft < tmpRight) {
                if(str.charAt(tmpLeft) == str.charAt(tmpRight)) {
                    tmpLeft++;
                    tmpRight--;
                } else {
                    System.out.println(right - left + 1);
                    return;
                }
            }
            System.out.println(right - left);
        }
    }
}
