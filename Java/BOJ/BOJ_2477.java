package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 2477. 참외밭
public class BOJ_2477 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());
        int cp[] = new int[6], length[] = new int[6], count[] = new int[5];
        for(int i=0; i<6 ;i++) {
            String str[] = br.readLine().split(" ");
            cp[i] = Integer.parseInt(str[0]);
            length[i] = Integer.parseInt(str[1]);
            count[cp[i]]++;
        }
        int sum = 0, all = 1, rest = 1;
        int allFirst = 0, allSecond = 0, restFirst = 0, restSecond = 0;
        if(count[1]<count[2]) {
            // 서쪽 2개, 동쪽 1개
            if(count[3]<count[4]) {
                // 서쪽 2개, 동쪽 1개, 북쪽 2개, 남쪽 1개
                // 서쪽->북쪽 연속될 때 그 둘의 길이 곱하면 빈칸의 너비
                allFirst = 1;
                allSecond = 3;
                restFirst = 2;
                restSecond = 4;
            } else {
                // 서쪽 2개, 동쪽 1개, 북쪽 1개, 남쪽 2개
                // 남쪽->서쪽 연속될 때 그 둘의 길이 곱하면 빈칸의 너비
                allFirst = 1;
                allSecond = 4;
                restFirst = 3;
                restSecond = 2;
            }
        } else {
            // 서쪽 1개, 동쪽 2개
            if(count[3] < count[4]) {
                // 서쪽1개, 동쪽2개, 북쪽 2개, 남쪽 1개
                // 북쪽->동쪽 연속될 때 그 둘의 길이 곱하면 빈칸의 너비
                allFirst = 3;
                allSecond = 2;
                restFirst = 4;
                restSecond = 1;
            } else {
                // 서쪽1개, 동쪽2개, 북쪽 1개, 남쪽 2개
                // 동쪽->남쪽 연속될 때 그 둘의 길이 곱하면 빈칸의 너비
                allFirst = 4;
                allSecond = 2;
                restFirst = 1;
                restSecond = 3;
            }
        }
        for(int i=0; i<cp.length; i++) {
            if(cp[i] == allFirst || cp[i] == allSecond) {
                all *= length[i];
            } else {
                if(cp[i] == restFirst && cp[(i+1)%cp.length] == restSecond) {
                    rest *= length[i] * length[i+1%cp.length];
                }
            }
        }
        System.out.println(K*(all-rest));
    }
}
