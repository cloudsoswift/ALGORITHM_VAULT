package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1654. 랜선 자르기
public class BOJ_1654 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        int K = Integer.parseInt(strArr[0]);
        int N = Integer.parseInt(strArr[1]);
        int[] lines = new int[K];
        long left = 0;
        long right = Long.MAX_VALUE;
        int maximum = 0;
        for(int i=0; i<K; i++) {
            lines[i] = Integer.parseInt(br.readLine());
        }
        while(left <= right) {
            long mid = (left + right) / 2;
            int count = 0;
            for(int i=0; i<K; i++) {
                count += lines[i] / mid;
            }
            if (count >= N) {
                maximum = Math.max(maximum, (int)mid);
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(maximum);
    }
}
