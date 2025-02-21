package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 7699. 수지의 수지 맞는 여행
public class SWEA_7699 {
    static int dx[], dy[], R, C, MAX_COUNT;
    static char map[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String str[];
        // [동, 서, 남, 북]
        dx = new int[] {1, -1, 0, 0};
        dy = new int[] {0, 0, 1, -1};
        for(int test_case = 1; test_case <= T; test_case++) {
            str = br.readLine().split(" ");
            R = Integer.parseInt(str[0]);
            C = Integer.parseInt(str[1]);
            map = new char[R][C];
            MAX_COUNT = 0;
            for(int i=0; i<R; i++) {
                String line = br.readLine();
                for(int j=0; j<C; j++) {
                    map[i][j] = line.charAt(j);
                }
            }
            dfs(0, 0, 1<<map[0][0]-'@', 1);
            System.out.printf("#%d %d\n", test_case, MAX_COUNT);
        }
    }
    static void dfs(int x, int y, int alpha, int count) {
        if(count > MAX_COUNT) MAX_COUNT = count;
        for(int i=0; i<4; i++) {
            int mx = x + dx[i];
            int my = y + dy[i];
            if(mx >= C || my >= R || mx < 0 || my < 0) continue;
            // 방문한 적 있는 알파벳 명물이 있는 곳은 안감
            if((alpha & (1<<map[my][mx]-'@')) != 0) continue;
            dfs(mx, my, (alpha | (1<<map[my][mx]-'@')), count+1);
        }
    }
}
