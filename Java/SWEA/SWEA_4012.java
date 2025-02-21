package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 4012. [모의 SW 역량테스트] 요리사
public class SWEA_4012 {
    static int T, N, arr[][], numbers[], MIN_DIFFER;
    // 테스트 케이스 갯수, 식재료의 수, 시너지 저장 배열, 식재료 순서 배열, 요리 간 맛 차이 최소값
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_4012.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        T = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            arr = new int[N][N];
            numbers = new int[N];
            MIN_DIFFER = Integer.MAX_VALUE;
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            comb(0, 0, 0);
            System.out.printf("#%d %d\n", tc, MIN_DIFFER);
        }
    }
    public static void comb(int start, int cnt, int flag) {
        if(cnt == N/2) {
            // A음식에 들어갈 재료 번호를 조합으로 뽑아 낸 뒤, 선택되지 않은 나머지 재료는 B음식에 사용
            for(int i=0; i<N; i++) {
                if((flag&1<<i)!=0) { continue; }
                // 선택되지 않은 재료들 번호로 배열 마저 채움
                numbers[cnt++] = i;
            }
            int ASum = 0, BSum = 0;
            int iNum = 0, jNum = 0;
            // A 음식에 사용될 재료들간의 시너지 합 다 더함
            for(int i=0; i<N/2; i++) {
                iNum = numbers[i];
                for(int j=i; j<N/2; j++) {
                    jNum = numbers[j];
                    ASum += arr[iNum][jNum];
                    ASum += arr[jNum][iNum];
                }
            }
            // B 음식에 사용될 재료들간의 시너지 합 다 더함
            for(int i=N/2; i<N; i++) {
                iNum = numbers[i];
                for(int j=i; j<N; j++) {
                    jNum = numbers[j];
                    BSum += arr[iNum][jNum];
                    BSum += arr[jNum][iNum];
                }
            }
            if(Math.abs(ASum-BSum) < MIN_DIFFER)
                MIN_DIFFER = Math.abs(ASum-BSum);
            return;
        }
        for(int i=start; i<N; i++) {
            numbers[cnt] = i;
            comb(i+1, cnt+1, (flag|1<<i));
        }
    }
}
