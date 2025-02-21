package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 9229. 한빈이와 Spot Mart
public class SWEA_9229 {
    static int max, N, M, arr[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= TC; test_case++) {
            String[] str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            M = Integer.parseInt(str[1]);
            arr = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }
            max = -1;
            for(int i=0; i<N; i++) {
                find(i, 1, 0);
            }
            System.out.printf("#%d %d\n", test_case, max);
        }
    }
    public static void find(int start, int cnt, int sum) {
        if(sum + arr[start] <= M) {
            if(cnt == 2) {
                sum += arr[start];
                if(sum > max) {
                    max = sum;
                    return;
                }
            }
            sum += arr[start];
            for(int i=start+1; i<N; i++) {
                find(i, cnt+1, sum);
            }
        }
    }
}
