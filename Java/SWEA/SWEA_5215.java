package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 5215. 햄버거 다이어트
public class SWEA_5215 {
    static int T, N, L, MAX_POINT; // TestCase 수, 재료의 수, 제한 칼로리, 가장 높았던 점수
    static int ingredients[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[];
        T = Integer.parseInt(br.readLine());
        for(int tc=1; tc<=T; tc++) {
            str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            L = Integer.parseInt(str[1]);
            ingredients = new int[N][2];
            MAX_POINT = Integer.MIN_VALUE;
            for(int i=0; i<N; i++) {
                str = br.readLine().split(" ");
                ingredients[i][0] = Integer.parseInt(str[0]); // 맛에 대한 점수
                ingredients[i][1] = Integer.parseInt(str[1]); // 칼로리			
            }
            comb(0, 0, 0, 0);
            System.out.printf("#%d %d\n", tc, MAX_POINT);
        }
    }
    public static void comb(int start, int cnt, int kcal, int points) {
        if(cnt == N) {
            if(points > MAX_POINT)
                MAX_POINT = points;
            return;
        }
        if(points > MAX_POINT) {
            MAX_POINT = points;
        }
        for(int i=start; i<N; i++) {
            if(kcal+ingredients[i][1] > L) continue;
            comb(i+1, cnt+1, kcal+ingredients[i][1], points+ingredients[i][0]);
        }
    }
}

