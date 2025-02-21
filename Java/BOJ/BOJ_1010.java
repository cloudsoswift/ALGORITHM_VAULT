package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1010. 다리 놓기
public class BOJ_1010 {
    static int N, M, cnt;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int tc = 1; tc <= T; tc++) {
            String str[] = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            M = Integer.parseInt(str[1]);
            cnt = 0;
            comb(0, 1);
            sb.append(cnt);
            sb.append("\n");
//			System.out.println(cnt);
        }
        System.out.print(sb.toString());
    }
    public static void comb(int count, int start) {
        if(count == N) {
            cnt++;
            return;
        }
        else {
            for(int i=start; i<=M; i++) {
                comb(count+1, i+1);
            }
        }
    }
}