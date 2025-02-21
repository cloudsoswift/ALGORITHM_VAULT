package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 1208. [S/W 문제해결 기본] 1일차 - Flatten
public class SWEA_1208 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_1208.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int arr[], MAX, MAX_idx, MIN, MIN_idx, dumpTime;
        arr = new int[100];
        MAX = Integer.MIN_VALUE;
        MAX_idx = 0;
        MIN = Integer.MAX_VALUE;
        MIN_idx = 0;
        for(int tc=1; tc<=10; tc++) {
            dumpTime = Integer.parseInt(br.readLine());
            arr = new int[100];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i=0; i<100; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                if( arr[i] > MAX) {
                    MAX = arr[i];
                    MAX_idx = i;
                }
                if( arr[i] < MIN) {
                    MIN = arr[i];
                    MIN_idx = i;
                }
            }

            int i = 1;
            MAX = arr[MAX_idx] -= 1;
            MIN = arr[MIN_idx] += 1;
            while(i<=dumpTime) {
                MAX = Integer.MIN_VALUE;
                MIN = Integer.MAX_VALUE;
                for(int j=0; j<arr.length; j++) {
                    if(arr[j] > MAX) {
                        MAX = arr[j];
                        MAX_idx = j;
                    }
                    if(arr[j] < MIN) {
                        MIN = arr[j];
                        MIN_idx = j;
                    }
                }
                arr[MAX_idx] -= 1;
                arr[MIN_idx] += 1;
                i++;
            }
            System.out.printf("#%d %d\n", tc, MAX-MIN);
        }

    }
}
