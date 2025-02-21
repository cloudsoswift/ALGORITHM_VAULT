package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 9205. 맥주 마시면서 걸어가기
public class BOJ_9205 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String strArr[];
        for(int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine()) + 2;
            int pos[][] = new int[N][2];
            double map[][] = new double[N][N];
            for(int i = 0; i<N; i++) {
                strArr = br.readLine().split(" ");
                pos[i][0] = Integer.parseInt(strArr[0]);
                pos[i][1] = Integer.parseInt(strArr[1]);
            }
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    map[i][j] = (double)(Math.abs(pos[i][0] - pos[j][0]) + Math.abs(pos[i][1] - pos[j][1]))/50;
                }
            }
            for(int k=0; k<N; k++) {
                for(int i=0; i<N; i++) {
                    for(int j=0; j<N; j++) {
                        map[i][j] = Math.min(map[i][j], map[i][k] <= 20 && map[k][j] <= 20 ? 20 : map[i][k]+map[k][j]);
                    }
                }
            }
            if(map[0][N-1] <= 20) {
                System.out.println("happy");
            } else {
                System.out.println("sad");
            }
        }
    }
}
