package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1987. 알파벳
public class BOJ_1987 {
    static char[][] arr;
    static int dc[], dr[], MAX_CNT, R, C;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        R = Integer.parseInt(str[0]);
        C = Integer.parseInt(str[1]);
        arr = new char[R][C];
        // 상, 하, 좌, 우
        dc = new int[] {0, 0, -1 ,1};
        dr = new int[] {-1, 1, 0, 0};
        MAX_CNT = 0;
        for(int i=0; i<R; i++) {
            String tmp = br.readLine();
            for(int j=0; j<C; j++) {
                arr[i][j] = tmp.charAt(j);
            }
        }
        search(0, 0, 0, 0);
        System.out.println(MAX_CNT);
    }
    static void search(int c, int r, int cnt, int v) {
        char now = arr[r][c];
        if((v & 1<<(now-'A')) != 0) {
            // 비트마스킹을 통해 알파벳 중복 검사
            if(cnt > MAX_CNT)
                MAX_CNT = cnt;
            return;
        }
        for(int i=0; i<4; i++) {
            int mr = r + dr[i];
            int mc = c + dc[i];
            if(mr < 0 || mc < 0 || mr >= R || mc >= C) {
                continue;
            }
            search(mc, mr, cnt+1, (v | 1<<(now-'A')));
        }
    }
}
