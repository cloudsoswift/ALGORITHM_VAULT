package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 2112. [모의 SW 역량테스트] 보호 필름
public class SWEA_2112 {
    static int D, W, K, map[][], alchemy[], MIN_AMOUNT;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String[] strArr;
        for(int test_case = 1; test_case <= T; test_case++) {
            strArr = br.readLine().split(" ");
            D = Integer.parseInt(strArr[0]);
            W = Integer.parseInt(strArr[1]);
            K = Integer.parseInt(strArr[2]);
            map = new int[D][W];
            alchemy = new int[D];
            MIN_AMOUNT = Integer.MAX_VALUE;
            for(int i=0; i<D; i++) {
                strArr = br.readLine().split(" ");
                for(int j=0; j<W; j++) {
                    map[i][j] = Integer.parseInt(strArr[j]);
                }
            }
            if(K > 1) {
                subset(0, 0);
            } else {
                MIN_AMOUNT = 0;
            }
            System.out.printf("#%d %d\n", test_case, MIN_AMOUNT);
        }
    }
    public static void subset(int cnt, int amount) {
        if(cnt == D) {
            System.out.println(Arrays.toString(alchemy));
            if(check()) {
                System.out.println("asd" + Arrays.toString(alchemy));
                if(MIN_AMOUNT > amount) MIN_AMOUNT = amount;
            }
            return;
        }
        alchemy[cnt] = -1;
        subset(cnt+1, amount);
        if(amount<MIN_AMOUNT) {
            alchemy[cnt] = 0;
            subset(cnt+1, amount+1);
            alchemy[cnt] = 1;
            subset(cnt+1, amount+1);
        }
    }
    public static boolean check() {
        for(int i=0; i<W; i++) {
            int cnt = 0;
            for(int j=1; j<D; j++) {
                if((alchemy[j-1] == -1 ? map[j-1][i] : alchemy[j-1]) == (alchemy[j] == -1 ? map[j][i] : alchemy[j])) {
                    int tmpCnt = 1;
                    while(j < D && (alchemy[j-1] == -1 ? map[j-1][i] : alchemy[j-1]) == (alchemy[j] == -1 ? map[j][i] : alchemy[j])) {
                        j++;
                        tmpCnt++;
                    }
                    cnt = Math.max(cnt, tmpCnt);
                }
            }
            System.out.println(i + ", " + cnt);
            if(cnt < K) {
                return false;
            }
        }
        return true;
    }
}
