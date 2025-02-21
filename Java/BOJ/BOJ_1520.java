package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 1520. 내리막 길
public class BOJ_1520 {
    static int M, N, map[][], vMap[][], cnt, dx[], dy[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        M  = Integer.parseInt(strArr[0]);
        N  = Integer.parseInt(strArr[1]);
        map = new int[M][N];
        vMap = new int[M][N];
        cnt = 0;
        for(int i=0; i<M; i++) {
            Arrays.fill(vMap[i], -1); // DP를 위한 배열 -1로 초기화. -> 경우의 수가 0인 경우도 있을 수 있으므로.
            strArr = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(strArr[j]);
            }
        }
        // [ 동, 서, 남, 북 ]
        dx = new int[]{ 1, -1, 0, 0 };
        dy = new int[]{ 0, 0, 1, -1 };
        System.out.println(dfs(0, 0));
    }
    public static int dfs(int x, int y) {
        if(x == N-1 && y == M-1) {
            return 1;
        }
        // 한번 탐색한 경우 ( 초기값이 아닌 경우 )
        // 해당 정점에서 도착점 까지 가는 경로는 언제든 똑같으므로 2번 이상 계산하지 않기위해 가지치기.
        if(vMap[y][x] != -1) return vMap[y][x];
        vMap[y][x] = 0;
        int now = map[y][x];
        for(int i=0; i<4; i++) {
            int mx = x + dx[i];
            int my = y + dy[i];
            if(mx >= N || my >= M || mx < 0 || my < 0) continue;
            if(now > map[my][mx]) {
                // 갈수있는 각 방향에서 도착점까지 갈 수 있는 경우의 수를 더 함.
                vMap[y][x] += dfs(mx, my);
            }
        }
        return vMap[y][x];
    }
}
