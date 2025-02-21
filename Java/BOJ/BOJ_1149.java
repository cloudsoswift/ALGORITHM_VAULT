package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1149. RGB 거리
public class BOJ_1149 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int RGBCosts[][] = new int [N][3];
        String str[];
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<3; j++) {
                RGBCosts[i][j] = Integer.parseInt(str[j]);
            }
        }
//		[ i-1번째 집까지의 색칠 비용 최솟값 + i번째 집의 j번째 색칠 비용 ] 을 저장할 배열
        int countCosts[][] = new int [N][3];
        for(int i=0; i<N; i++) {
            // 첫 번째 집은 각 색깔별 색칠 비용 바로 기록
            if(i == 0) {
                for(int j=0; j<3; j++) {
                    countCosts[i][j] = RGBCosts[i][j];
                }
                continue;
            }
            for(int j=0; j<3; j++) {
                if(j==0) {
                    // 1 ) i번째 집을 빨강으로 칠하는 경우
                    // i-1번째 집을 초록색으로 칠했을 때의 최소비용과 파란색으로 칠했을때의 최소비용중 더 적은 쪽과
                    // i번째 집을 빨간색으로 칠하는 비용을 합한 값 기록
                    countCosts[i][j] = RGBCosts[i][j]
                            + (countCosts[i-1][1] < countCosts[i-1][2] ?
                            countCosts[i-1][1] : countCosts[i-1][2]);
                } else if(j==1) {
                    // 2 ) i번째 집을 초록으로 칠하는 경우
                    // i-1번째 집을 빨간색으로 칠했을 때의 최소비용과 파란색으로 칠했을때의 최소비용중 더 적은 쪽과
                    // i번째 집을 초록색으로 칠하는 비용을 합한 값 기록
                    countCosts[i][j] = RGBCosts[i][j]
                            + (countCosts[i-1][0] < countCosts[i-1][2] ?
                            countCosts[i-1][0] : countCosts[i-1][2]);
                } else {
                    // 3 ) i번째 집을 파랑으로 칠하는 경우
                    // i-1번째 집을 빨간색으로 칠했을 때의 최소비용과 초록색으로 칠했을때의 최소비용중 더 적은 쪽과
                    // i번째 집을 파란색으로 칠하는 비용을 합한 값 기록
                    countCosts[i][j] = RGBCosts[i][j]
                            + (countCosts[i-1][0] < countCosts[i-1][1] ?
                            countCosts[i-1][0] : countCosts[i-1][1]);
                }
            }
        }
        int lowestCost = Integer.MAX_VALUE;
        // N-1번째 집을 각 색깔로 칠했을때의 최소비용중, 가장 적은 값을 구해 출력
        for(int i=0; i<3; i++) {
            if(countCosts[N-1][i] < lowestCost)
                lowestCost = countCosts[N-1][i];
        }
        System.out.println(lowestCost);
    }
}
