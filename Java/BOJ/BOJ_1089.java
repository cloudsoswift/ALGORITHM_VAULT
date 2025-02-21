package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1089. 스타트링크 타워
public class BOJ_1089 {
    static int N;
    static double TOTAL_SUM, TOTAL_COUNT;
    static boolean isP[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr;
        int totalCount = 1;
        double totalSum = 0;
        N = Integer.parseInt(br.readLine());
        char arr[][] = new char[5][4*N-1];
        isP = new boolean[N][10];
        for(int i=0; i<5; i++) {
            strArr = br.readLine();
            for(int j=0, length = strArr.length(); j < length; j++) {
                arr[i][j] = strArr.charAt(j);
            }
        }
        for(int i=0; i<N; i++) {
            int pos = i*4;
            // 가운데 두 부분은 숫자 만드는데 못 씀
            if(arr[1][pos+1] == '#' || arr[3][pos+1] == '#') {
                System.out.println(-1);
                return;
            }
            // ================= 첫 번째 줄 =====================
            if(arr[0][pos] == '#') {
                isP[i][1] = true;
            }
            if(arr[0][pos+1] == '#') {
                isP[i][1] = isP[i][4] = true;
            }
            //			if(arr[0][pos+2] == '#') {
            //				isP[i][1] = isP[i][4] = true;
            //			}
            // ================= 두 번째 줄 =====================			
            if(arr[1][pos] == '#') {
                isP[i][7] = isP[i][3] = isP[i][2] = isP[i][1] = true;
            }
            if(arr[1][pos+2] == '#') {
                isP[i][5] = isP[i][6] = true;
            }
            // ================= 세 번째 줄 =====================						
            if(arr[2][pos] == '#') {
                isP[i][7] = isP[i][1] = true;
            }
            if(arr[2][pos+1] == '#') {
                isP[i][7] = isP[i][1] = isP[i][0] = true;
            }
            //			if(arr[2][pos+2] == '#') {
            //				isP[i][7] = isP[i][1] = isP[i][0] = true; 
            //			}
            // ================= 네 번째 줄 =====================						
            if(arr[3][pos] == '#') {
                isP[i][1] = isP[i][3] = isP[i][4] = isP[i][5] = isP[i][7] = isP[i][9] = true;
            }
            if(arr[3][pos+2] == '#') {
                isP[i][2] = true;
            }
            // ================= 다섯 번째 줄 =====================						
            if(arr[4][pos] == '#') {
                isP[i][7] = isP[i][4] = isP[i][1] = true;
            }
            if(arr[4][pos+1] == '#') {
                isP[i][7] = isP[i][4] = isP[i][1] = true;
            }
            //			if(arr[4][pos+2] == '#') {
            //				isP[i][7] = isP[i][4] = isP[i][1] = true;
            //			}
        }
        // 가능한 경우의 수 카운트
        for(int i=0; i<N; i++) {
            int placeCount = 0;
            for(int j=0; j<10; j++) {
                if(isP[i][j]) continue;
                placeCount++;
            }
            totalCount *= placeCount;
        }
        // 각 자리수의 경우 합치기
        double placeCnt, numSum;
        for(int i=0; i<N; i++) {
            placeCnt = 0;
            numSum = 0;
            // 10^(N-i)번째 자리 수 구하기
            for(int j=0; j<10; j++) {
                if(isP[i][j]) continue;
                placeCnt++;
                numSum += j;
            }
            // 모든 경우의 수 / 현재 자리수의 경우의 수 
            // => 현재 자리에서 하나 결정했을 때, 다른 자리수 바뀔 수 있는 경우 구하는 것
            double tmp = totalCount / placeCnt;
            // 모든 경우의 수 / 현재 자리 경우의 수 * 현재 자리에서 선택 가능한 값들의 합  
            totalSum += numSum * tmp;
            // 현재 연산한거 자리 업그레이드
            totalSum *= 10;
        }
        totalSum /= 10;
        System.out.printf("%.5f",totalSum/totalCount);
    }

}