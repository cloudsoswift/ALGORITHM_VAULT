package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 16926. 배열 돌리기 1
public class BOJ_16926 {
    static int arr[][], res[][];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        int N = Integer.parseInt(str[0]);
        int M = Integer.parseInt(str[1]);
        int R = Integer.parseInt(str[2]);
        arr = new int[N][M];
        res = new int[N][M];
        for(int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<R; i++) {
            turn(0, 0, M-1, N-1);
            for(int j=0; j<N; j++) {
                System.arraycopy(res[j], 0, arr[j], 0, arr[j].length);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<res.length; i++) {
            for(int j=0; j<res[i].length; j++) {
                sb.append(arr[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
    public static void turn(int srtX, int srtY, int endX, int endY) {
        if(srtX >= endX || srtY >= endY) {
            return;
        }
        // 상
        for(int i=endX; i>srtX; i--) {
            res[srtY][i-1] = arr[srtY][i];
        }
        // 좌
        for(int i=srtY; i<endY; i++) {
            res[i+1][srtX] = arr[i][srtX];
        }
        // 하
        for(int i=srtX; i<endX; i++) {
            res[endY][i+1] = arr[endY][i];
        }
        // 우
        for(int i=endY; i>srtY; i--) {
            res[i-1][endX] = arr[i][endX];
        }
        turn(srtX+1, srtY+1, endX-1, endY-1);
    }
}

