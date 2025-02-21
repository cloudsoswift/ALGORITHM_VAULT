package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 3040. 백설 공주와 일곱 난쟁이
public class BOJ_3040 {
    static int arr[], numbers[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        arr = new int[9];
        numbers = new int [7];
        for(int i=0; i<9; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        comb(0, 0, 0);
    }
    public static void comb(int start, int cnt, int sum) {
        if(cnt==7) {
            if(sum == 100) {
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<numbers.length; i++) {
                    sb.append(numbers[i]);
                    sb.append("\n");
                }
                System.out.print(sb.toString());
            }
            return;
        }
        for(int i=start;i<9;i++) {
            if(arr[i]+sum<=100) {
                numbers[cnt] = arr[i];
                comb(i+1, cnt+1, sum + arr[i]);
            }
        }
    }
}
