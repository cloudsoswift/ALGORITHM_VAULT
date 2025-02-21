package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 2961. 도영이가 만든 맛있는 음식
public class BOJ_2961 {
    static int sour[], bitter[], N;
    static long differ;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        sour = new int[N]; // 재료별 신맛 저장 
        bitter = new int[N]; // 재료별 쓴맛 저장
        differ = Long.MAX_VALUE;
        for(int i=0; i<N; i++) {
            String str[] = br.readLine().split(" ");
            sour[i] = Integer.parseInt(str[0]);
            bitter[i] = Integer.parseInt(str[1]);
        }
        for(int i=1; i<=N; i++) {
            comb(i, 0, 0, 1, 0);
        }
        System.out.println(differ);
    }
    public static void comb(int K, int start, int cnt, long sourSum, long bitterSum) {
        if(K == cnt) {
            long d;
            if(sourSum > bitterSum) {
                d = sourSum - bitterSum;
            } else {
                d = bitterSum - sourSum;
            }
            if(d < differ) {
                differ = d;
            }
            return;
        }
        for(int i=start; i<N; i++) {
            comb(K, i+1, cnt+1, sourSum*sour[i], bitterSum+bitter[i]);
        }
    }
}
