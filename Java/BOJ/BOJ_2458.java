package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 2458. 키 순서
public class BOJ_2458 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		int T = Integer.parseInt(br.readLine().trim());
        String str[];
//		for(int test_case = 1 ; test_case <= T ; test_case++) {
        str = br.readLine().trim().split(" ");
        int N = Integer.parseInt(str[0]);
        int conn[][] = new int[N+1][N+1];
        int M = Integer.parseInt(str[1]);
        for(int i=1; i<=N; i++) {
            Arrays.fill(conn[i], 10000);
            conn[i][i] = 0;
        }
        for(int j=0; j<M; j++) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            conn[a][b] = 1;
        }
        for(int k=1; k<=N; k++) {
            for(int i=1; i<=N; i++) {
                for(int j=1; j<=N; j++) {
                    conn[i][j] = Math.min(conn[i][j], conn[i][k] + conn[k][j]);
                }
            }
        }
//			for(int i=1; i<=N; i++) {
//				for(int j=1; j<=N; j++) {
//					System.out.printf("%d\t", conn[i][j]);
//				}
//				System.out.println();
//			}
        int sum = 0;
        for(int i=1; i<=N; i++) {
            boolean isNot = false;
            for(int j=1; j<=N; j++) {
                if(conn[i][j] >= 10000 && conn[j][i] >= 10000) {
                    isNot = true;
                    break;
                }
            }
            if(!isNot) {
                sum++;
            }
        }
        System.out.printf("%d\n", sum);
//		}
    }
}