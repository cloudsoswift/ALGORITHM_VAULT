package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 3282. 0/1 Knapsack
public class SWEA_3282 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1 ; test_case <= T; test_case++) {
            String str[] = br.readLine().split(" ");
            int N = Integer.parseInt(str[0]);
            int K = Integer.parseInt(str[1]);
            int arr[] = new int[K+1];
            Arrays.fill(arr, 0);
            for(int i=0; i<N; i++) {
                str = br.readLine().split(" ");
                int weight = Integer.parseInt(str[0]);
                int value = Integer.parseInt(str[1]);
                for(int j=K; j>=0; j--) {
                    if(j >= weight) {
                        arr[j] = Math.max(arr[j], arr[j-weight] + value);
                    }
                }
            }
            System.out.printf("#%d %d\n", test_case, arr[K]);
        }
    }
}
