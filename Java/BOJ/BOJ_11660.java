package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 11660. 구간 합 구하기 5
public class BOJ_11660 {
    public static void main(String[] args) throws IOException {
        int N, M;
        long arr[][];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        arr = new long[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
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
        int x1, y1, x2, y2;
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            y1 = Integer.parseInt(st.nextToken()) - 1;
            x1 = Integer.parseInt(st.nextToken()) - 1;
            y2 = Integer.parseInt(st.nextToken()) - 1;
            x2 = Integer.parseInt(st.nextToken()) - 1;
            long r1 = 0, r2 = 0, r3 = 0;
            int mx = x1 - 1;
            int my = y1 - 1;
            if(mx >= 0) {
                r1 = arr[y2][mx];
            }
            if(my >= 0) {
                r2 = arr[my][x2];
            }
            if(mx >= 0 && my >= 0) {
                r3 = arr[my][mx];
            }
//			System.out.println(arr[y2][x2]-r1-r2+r3);
            sb.append(arr[y2][x2]-r1-r2+r3 + "\n");
        }
        System.out.println(sb.toString());
    }
}