package BOJ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 1292. 쉽게 푸는 문제
public class BOJ_1292 {
    public static void main(String[] args) throws Exception {
        int A, B, num, cnt, sum;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken()) - 1;
        B = Integer.parseInt(st.nextToken()) - 1;
        sum = 0;
        cnt = 0;
        num = 1;
        for(int i=0;i<=B;i++) {
            if(cnt == num) {
                num++;
                cnt = 1;
            } else {
                cnt++;
            }
            if ( i >= A ) {
                sum += num;
            }
        }
        System.out.println(sum);
    }
}