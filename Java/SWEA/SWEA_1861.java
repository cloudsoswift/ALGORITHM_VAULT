package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 1861. 정사각형 방
public class SWEA_1861 {
    static int dx[], dy[], arr[][], pos, max;
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_1861.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        // [ 상, 하, 좌, 우 ]
        dx = new int[] { 0, 0, -1, 1};
        dy = new int[] { -1, 1, 0, 0 };
        for(int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            arr = new int[N][N];
            pos = Integer.MAX_VALUE;
            max = 0;
            for(int i=0; i<N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j=0; j<N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    int res = bfs(j, i, 1, N);
                    if(res > max){
                        pos = arr[i][j];
                        max = res;
                    } else if(res == max) {
                        if(pos > arr[i][j]) {
                            pos = arr[i][j];
                        }
                    }
                }
            }
            System.out.printf("#%d %d %d\n", tc, pos, max);
        }
    }
    public static int bfs(int x, int y, int cnt, int N) {
        for(int i=0; i<4; i++) {
            int ix = x + dx[i];
            int iy = y + dy[i];
            if(ix >= 0 && ix < N && iy >= 0 && iy < N) {
                if(arr[iy][ix] - arr[y][x] == 1) {
                    return bfs(ix, iy, cnt+1, N);
                }
            }
        }
        return cnt;
    }
}
