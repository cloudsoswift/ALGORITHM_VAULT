package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// 1062. 가르침
public class BOJ_1062 {
    static int N, K, arr[], MAX_COUNT, WORD_COUNT;
    static boolean v[], list[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        N = Integer.parseInt(strArr[0]);
        K = Integer.parseInt(strArr[1]) - 5;
        arr = new int[N];
        v = new boolean[26];
        list = new boolean[26];
        MAX_COUNT = 0;
        WORD_COUNT = 0;
        int antic = 0;
        antic = antic | (1 << ('a'-'a'));
        antic = antic  | (1 << ('n'-'a'));
        antic = antic  | (1 << ('t'-'a'));
        antic = antic  | (1 << ('i'-'a'));
        antic = antic  | (1 << ('c'-'a'));
        String str;
        for(int i=0; i<N; i++) {
            str = br.readLine();
            str = str.substring(4, str.length() - 4);
            int v = 0;
            char charArr[] = str.toCharArray();
            for(char c : charArr) {
                int now = 1 << (c - 'a');
                v = v | now;
                list[c-'a'] = true;
            }
            arr[i] = v & ~antic;
        }
        // K가 0 이하, 즉 antic도 못 구성하는 경우 학생들이 읽을 수 있는 단어는 0개
        if(K < 0) {
            System.out.println(0);
            return;
        }
        for(int i=0; i<26; i++){
            if(list[i]) WORD_COUNT++;
        }
        if(WORD_COUNT <= K) {
            System.out.println(N);
            return;
        }
        dfs(0, 0, 0);
        System.out.println(MAX_COUNT);
    }
    public static void dfs(int path, int start, int cnt) {
        System.out.println(Integer.toBinaryString(path) + " , " + cnt);
        if(cnt == K) {
            int anscnt = 0;
            for(int i=0; i<N; i++) {
                if((arr[i] & path) == arr[i]) {
                    anscnt++;
                }
            }
            if(anscnt > MAX_COUNT) {
                MAX_COUNT = anscnt;
            }
            return;
        }
        for(int i=start; i<26; i++) {
            // 이미 방문한 글자(=path에 기록된 글자)는 방문 X
            if(!list[i]) continue;
            int now = 1 << i;
            if((path & now) != 0) continue;
            list[i] = false;
            // 단어에 포함되는 글자면 넣음
            dfs(path | now, i + 1, cnt + 1);
            list[i] = true;
        }
    }
}
