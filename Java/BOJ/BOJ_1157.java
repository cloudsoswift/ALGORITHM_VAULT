package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1157. 단어 공부
public class BOJ_1157 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String word = br.readLine();
        int[] dict = new int[26];
        int maximum = 0;
        char maxWord = ' ';
        int duplicatedWord = 0;
        for(int i=0, len=word.length(); i<len; i++) {
            char c = word.charAt(i);
            if(c >= 'a' && c <= 'z') c -= 32;
            c -= 'A';
            dict[c]++;
            if(dict[c] > maximum) {
                maximum = dict[c];
                duplicatedWord = 0;
                maxWord = (char) (c + 'A');
            } else if(dict[c] == maximum) {
                duplicatedWord++;
            }
        }
        if(duplicatedWord > 0) {
            System.out.println("?");
        } else {
            System.out.println(maxWord);
        }
    }
}
