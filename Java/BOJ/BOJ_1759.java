package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 1759. 암호 만들기
public class BOJ_1759 {
    static int L, C;
    static char ans[], candidate[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        L = Integer.parseInt(str[0]);
        C = Integer.parseInt(str[1]);
        candidate = new char[C];
        ans = new char[L];
        str = br.readLine().split(" ");
        for(int i=0; i<C; i++) {
            candidate[i] = str[i].charAt(0);
        }
        Arrays.sort(candidate);
        comb(0, 0, 0, 0);
    }
    public static void comb(int cnt, int start, int vowel, int consonant) {
        // vowel : 모음 , consonant : 자음
        if(cnt==L) {
            if(vowel >= 1 && consonant >= 2) {
                for(int i=0; i<cnt; i++) {
                    System.out.print(ans[i]);
                }
                System.out.println();
            }
            return;
        }
        for(int i=start; i<C; i++) {
            ans[cnt] = candidate[i];
            if(candidate[i] == 'a' || candidate[i] =='e' || candidate[i] == 'i' || candidate[i] =='o' || candidate[i] == 'u') {
                comb(cnt+1, i+1, vowel+1, consonant);
            }else {
                comb(cnt+1, i+1, vowel, consonant+1);
            }
        }
    }
}
