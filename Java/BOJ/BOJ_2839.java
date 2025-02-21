package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2839 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int lowCnt = Integer.MAX_VALUE;
        if(N>=5) {
            for(int i=0; i<=N/5; i++) {
                int res = N-(5*i);
                if(res==0 && i < lowCnt) {
                    lowCnt = i;
                } else {
                    if(res%3==0) {
                        if(i+res/3 < lowCnt) {
                            lowCnt = i+res/3;
                        }
                    }
                }
            }
        } else {
            if(N%3==0) {
                lowCnt = 1;
            }
        }
        if(lowCnt == Integer.MAX_VALUE)
            lowCnt = -1;
        System.out.println(lowCnt);
    }
}
