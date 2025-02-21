package SWEA;

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

// 2001. 파리 퇴치
public class SWEA_2001 {
    public static void main(String args[]) throws Exception
    {
        System.setIn(new FileInputStream("input_2001.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T;
        T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int MAX, N, M;
        int[][] arr;
        for(int test_case = 1; test_case <= T; test_case++)
        {
            arr = new int[15][15];
            String[] str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            M = Integer.parseInt(str[1]);
            MAX = 0;
            for(int i=0;i<N;i++) {
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<N;j++) {
                    if(i == 0) {
                        if(j == 0) {
                            arr[i][j] = Integer.parseInt(st.nextToken());
                            continue;
                        }
                        arr[i][j] = Integer.parseInt(st.nextToken()) + arr[i][j-1];
                    } else if(j == 0) {
                        arr[i][j] = Integer.parseInt(st.nextToken()) + arr[i-1][j];
                    } else {
                        arr[i][j] = Integer.parseInt(st.nextToken()) + arr[i-1][j] + arr[i][j-1] - arr[i-1][j-1];
                    }
                }
            }
            for(int i=0;i<=N-M;i++) {
                for(int j=0;j<=N-M;j++) {
                    int tmp = 0, r1 = 0, r2 = 0, r3 = 0;
                    int mi = i-1;
                    int mj = j-1;
                    int mx = j+M-1;
                    int my = i+M-1;
                    if(mj >= 0) {
                        r1 = arr[my][mj];
                    }
                    if(mi >= 0) {
                        r2 = arr[mi][mx];
                    }
                    if(mj >= 0 && mi >= 0) {
                        r3 = arr[mi][mj];
                    }
                    tmp = arr[my][mx] - r1 - r2 + r3;
                    if (tmp>MAX) {
                        MAX = tmp;
                    }
                }
            }
            System.out.println("#"+test_case+" "+MAX);
        }
    }
}
