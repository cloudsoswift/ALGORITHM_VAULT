package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 6808. 규영이와 인영이의 카드게임
public class SWEA_6808 {
    static int kyu[], in[], v[], winCount, lossCount;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= T; tc++) {
            kyu = new int[9];
            v = new int[19];
            in = new int[9];
            winCount = 0;
            lossCount = 0;
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i=0; i<9; i++) {
                kyu[i] = Integer.parseInt(st.nextToken());
                v[kyu[i]] = 1;
            }
            perm(0);
            System.out.printf("#%d %d %d\n", tc, winCount, lossCount);
        }
    }
    public static void perm(int cnt) {
        if(cnt == 9) {
            int inScore = 0;
            int kyuScore = 0;
            for(int i=0; i<9; i++) {
                if(kyu[i] > in[i]) {
                    kyuScore += kyu[i] + in[i];
                } else if(kyu[i] < in[i]) {
                    inScore += kyu[i] + in [i];
                }
            }
            if(kyuScore > inScore) {
                winCount++;
            } else if(kyuScore < inScore) {
                lossCount++;
            }
            return;
        }
        for(int i=1; i<=18; i++) {
            if(v[i] == 1) continue;
            v[i] = 1;
            in[cnt] = i;
            perm(cnt+1);
            v[i] = 0;
        }
    }
}
