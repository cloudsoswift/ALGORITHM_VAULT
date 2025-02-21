package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//  1463. 1로 만들기
public class BOJ_1463 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        int X = Integer.parseInt(br.readLine());
        int arr[] = new int[X+1];
        int cnt = 0;
        for(int i=2; i<=X; i++) {
            int low = Integer.MAX_VALUE;
            int now = 0;
            if(i%3==0) {
                low = arr[i/3] + 1;
            }
            if(i%2==0) {
                now = arr[i/2] + 1;
                if(low > now) {
                    low = now;
                }
            }
            if(i-1>0) {
                now = arr[i-1] + 1;
                if(low > now) {
                    low = now;
                }
            }
            arr[i] = low;
        }
        System.out.println( X == 1 ? 0 : arr[X]);
    }
}
