package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 2563. 색종이
public class BOJ_2563 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        int arr[][] = new int[100][100];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int cnt = 0;
        for(int i=0; i<N; i++) {
            String str[] = br.readLine().split(" ");
            int X = Integer.parseInt(str[0]);
            int Y = Integer.parseInt(str[1]);
            for(int j=Y; j<Y+10; j++) {
                for(int k=X; k<X+10; k++) {
                    if(arr[j][k] == 0) {
                        arr[j][k] = 1;
                        cnt++;
                    }
                }
            }
        }
        System.out.println(cnt);
    }
}
