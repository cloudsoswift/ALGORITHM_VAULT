package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 2805. 농작물 수확하기
public class SWEA_2805 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_2805.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str; // 한 줄, 한 줄 저장용
        int T, N, arr[][];
        T = Integer.parseInt(br.readLine());
        for(int test_case=1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine());
            arr = new int[N][N];
            for(int i=0; i<N; i++) {
                str = br.readLine().split("");
                for(int j=0; j<str.length; j++) {
                    arr[i][j] = Integer.parseInt(str[j]);
                }
            }
            int sum = 0;
            for(int i=0; i<N; i++) {
                int pos = Math.abs(N/2-i); // 이번 줄의 마름모 시작점
                for(int j=0; j<N-pos*2; j++) {
                    sum += arr[i][j+pos];
                }
            }
            System.out.printf("#%d %d\n", test_case, sum);
        }
    }
}
