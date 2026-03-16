package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1025. 제곱수 찾기
public class BOJ_1025 {
    static int N, M, MAXIMUM;
    static int[][] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        N = Integer.parseInt(strArr[0]);
        M = Integer.parseInt(strArr[1]);
        arr = new int[N][M];
        MAXIMUM = -1;
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split("");
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(strArr[j]);
            }
        }
        // 임의의 위치 (i, j)에 대해 i를 k만큼, c를 l만큼 계속 더해가며
        // 각 위치에 있는 값을 이어붙여나가며 계산
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                // 각 위치의 요소값 단독으로도 검사 및 갱신
                if(isPossible(arr[i][j]) && arr[i][j] > MAXIMUM) MAXIMUM = arr[i][j];
                for(int k=-N; k<N; k++) {
                    for(int l=-M; l<M; l++) {
                        int mr = i + k;
                        int mc = j + l;
                        // 한 번 (i, j)를 증가시켜 봤을때 범위를 벗어나면 스킵
                        if(mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
                        if(k == 0 && l == 0) continue;
                        doCalc(i, j, k, l);
                    }
                }
            }
        }
        System.out.println(MAXIMUM);
    }
    public static void doCalc(int r, int c, int rInc, int cInc) {
        // (r,c) 에서 출발하여, r을 rInc만큼, c를 cInc만큼 계속 증가시켜가며
        // 각 위치에 있는 값을 순서대로 이어붙인 값 num를 구한 뒤,
        int num = 0;
        while(r >= 0 && c >= 0 && r < N && c < M) {
            num = num * 10 + arr[r][c];
            r += rInc;
            c += cInc;
        }
        // 해당 값을 10으로 나눠가며 그 값들을 비교 및 갱신
        // 즉, num이 1234라면, 123, 12, 1도 검사하는 셈
        while(num != 0 && num > MAXIMUM) {
            if(isPossible(num)) MAXIMUM = num;
            num /= 10;
        }
    }
    public static boolean isPossible(int num) {
        // num이 완전 제곱수인지 반환하는 함수
        int srt = (int) Math.sqrt(num);
        return srt * srt == num;
    }
}
