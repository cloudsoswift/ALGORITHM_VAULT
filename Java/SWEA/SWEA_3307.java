package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 3307. 최장 증가 부분 수열
public class SWEA_3307 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case<=T; test_case++) {
            int N = Integer.parseInt(br.readLine());
            int arr[] = new int[N];
            int LIS[] = new int[N];
            String str[] = br.readLine().split(" ");
            for(int i=0; i<N; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
            int max = 0;
            for(int i=0; i<N; i++) {
                LIS[i] = 1;
                for(int j=0; j<i; j++) {
                    if(arr[i] > arr[j] && LIS[i] < LIS[j] + 1) {
                        LIS[i] = LIS[j] + 1;
                    }
                }
                max = Math.max(max, LIS[i]);
            }
            System.out.printf("#%d %d\n", test_case, max);
        }
    }
}
