package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 15961. 회전 초밥
public class BOJ_15961 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[];
        strArr = br.readLine().split(" ");
        int N = Integer.parseInt(strArr[0]);
        int d = Integer.parseInt(strArr[1]);
        int k = Integer.parseInt(strArr[2]);
        int c = Integer.parseInt(strArr[3]);
        int sushi[] = new int[N];
        int species[] = new int[d+1];
        for(int i=0; i<N; i++) {
            sushi[i] = Integer.parseInt(br.readLine());
        }
        int cnt = 0;
        int MAX_CNT = 0;
        int now = 0;
        for(int i=0; i<k; i++) {
            now = sushi[i];
            if(species[now] == 0) {
                cnt++;
            }
            species[now]++;
            MAX_CNT = Math.max(MAX_CNT, cnt);
        }
        for(int i=0; i<N; i++) {
            int before = sushi[i];
            species[before]--;
            if(species[before] == 0) cnt--;
            int after = sushi[(i+k)%N];
            if(species[after] == 0) cnt++;
            species[after]++;
            if(species[c] == 0) {
                MAX_CNT = Math.max(MAX_CNT, cnt + 1);
            } else {
                MAX_CNT = Math.max(MAX_CNT, cnt);
            }
        }
        System.out.println(MAX_CNT);
    }
}

