package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 2667. 단지번호붙이기
public class BOJ_2667 {
    static int map[][], count[], dx[], dy[], N;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        count = new int[N*N];
        // [ 동, 서, 남, 북 ]
        dx = new int[] { 1, -1, 0, 0 };
        dy = new int[] { 0, 0, 1, -1 };
        for(int i=0; i<N; i++) {
            str = br.readLine();
            for(int j=0; j<N; j++) {
                map[i][j] = Character.digit(str.charAt(j), 10);
            }
        }
        int M = 2;
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(map[i][j] == 1) {
                    dfs(j, i, M);
                    M++;
                }
            }
        }
        count = Arrays.copyOfRange(count, 2, M);
        Arrays.sort(count);
        System.out.println(M-2);
        for(int i=0; i<M-2; i++) {
            System.out.println(count[i]);
        }
    }
    static void dfs(int x, int y, int num) {
        map[y][x] = num;
        count[num]++;
        for(int i=0; i<4; i++) {
            int mx = x + dx[i];
            int my = y + dy[i];
            if(my >= N || mx >= N || my < 0 || mx < 0) continue;
            if(map[my][mx] == 1) {
                dfs(mx, my, num);
            }
        }
    }
}
