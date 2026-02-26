package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1182. 부분수열의 합
public class BOJ_1182 {
    static int N, S;
    static int[] arr;
    static int count;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        N = Integer.parseInt(strArr[0]);
        S = Integer.parseInt(strArr[1]);
        strArr = br.readLine().split(" ");
        arr = new int[N];
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        subSet(0, 0, 0);
        System.out.println(count);
    }
    public static void subSet(int step, int sum, int bit) {
        if (step == N) {
            if(bit != 0 && sum == S)
                count++;
            return;
        }
        subSet(step + 1, sum, bit);
        subSet(step + 1, sum + arr[step], (bit | (1 << step)));
    }
}
