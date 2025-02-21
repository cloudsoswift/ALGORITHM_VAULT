package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 1158. 요세푸스 문제
public class _BOJ_1158 {
    public static void main(String[] args) throws IOException {
        int N = 0, K = 0, cnt = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        K = Integer.parseInt(str[1]);
        int arr[] = new int [N];
        int seq[] = new int [N];

        K -= 1;
        int i = 0;
        while(cnt!=N) {
            while(arr[i]!=0) {
                i+=1;
                i%=N;
            }
            i = (i + K) %  N;
            while(arr[i]!=0) {
                while(arr[i]!=1) {
                    i++;
                    i%=N;
                    continue;
                }
                i = (i + K) % N;
            }
            arr[i] = 1;
            seq[cnt] = i+1;
            i++;
            i%=N;
            cnt++;
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(Arrays.toString(seq));
    }
}