package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 11659. 구간 합 구하기 4
public class BOJ_11659 {
    public static void main(String[] args) throws IOException {
        int N, M, l, m;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        long arr[] = new long[N];
        st = new StringTokenizer(br.readLine());
        arr[0] = Integer.parseInt(st.nextToken());
        for(int i=1; i<N; i++) {
            arr[i] = Integer.parseInt(st.nextToken()) + arr[i-1];
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            l = Integer.parseInt(st.nextToken())-1;
            m = Integer.parseInt(st.nextToken())-1;
            long sum = 0;
            if(l == 0)
                sum = arr[m];
            else
                sum = arr[m] - arr[l-1];
            System.out.println(sum);
        }
    }
}
