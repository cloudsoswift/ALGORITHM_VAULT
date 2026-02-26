package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1051. 숫자 정사각형
public class BOJ_1051 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        String str;
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        int[][] map = new int[N][M];
        int size = 1;
        for(int i=0; i<N; i++) {
            str = br.readLine();
            for(int j=0; j<M; j++) {
                map[i][j] = str.charAt(j) - '0';
            }
        }
        int minLimit = Math.min(N, M);
        for(int s = 1; s<minLimit; s++) {
            for(int i=0; i<N; i++) {
                for(int j=0; j<M; j++) {
                    if(i+s >= N || j+s >= M) continue;
                    if(map[i][j] == map[i][j+s] && map[i][j] == map[i+s][j]
                        && map[i+s][j] == map[i+s][j+s])
                        size = (s + 1) * (s + 1);
                }
            }
        }
        System.out.println(size);
    }
}
