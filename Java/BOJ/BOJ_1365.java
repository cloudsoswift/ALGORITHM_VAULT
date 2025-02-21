package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1365. 꼬인 전깃줄
public class BOJ_1365 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String strArr[] = br.readLine().split(" ");
        // LIS[0] = 각 index의 값이 LIS에서 몇 번째 위치인지
        // LIS[1] = LIS를 이루는 i번째 위치의 값
        int LIS[][] = new int[2][N+1];
        // LIS 최고 길이 카운팅
        int count = 0;
        for(int i=0; i<N; i++) {
            int num = Integer.parseInt(strArr[i]);
            // LIS 이루는 값과 비교 검사
            for(int j=0; j<N; j++) {
                // LIS의 현재 위치값 보다 큰 경우
                if(LIS[1][j] < num) {
                    // LIS의 다음 위치값이 아직 없거나, j < num < j+1인 경우
                    if(LIS[1][j+1] == 0 || num < LIS[1][j+1]) {
                        LIS[1][j+1] = num;
                        LIS[0][i] = j+1;
                        break;
                    }

                }
            }
            count = Math.max(count, LIS[0][i]);
        }
        System.out.println(N-count);
    }
}
