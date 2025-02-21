package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_2482 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());
        if(N/2 < K) {
            System.out.println(0);
            return;
        }
        int DIVIDER = 1_000_000_003;
        int count[][] = new int[N+1][N+1];
        for(int i = 1; i <= N; i++){
            // i개 중에서 1개를 선택하는 방법은 i개이다.
            count[i][1] = i;
            // 0개를 선택한 경우는 1로 초기화한다.
            // 점화식을 위해서는 1로 초기화해야한다.
            count[i][0] = 1;
        }

        // i가 3미만인 경우는 계산할 필요가 없다.
        for(int i = 3; i <= N; i++){
            // n개의 수 중 n/2개 보다 더 많이 고르는 경우는 0가지이다.
            // 그렇기 때문에 j의 범위를 다음과 같이 설정한다.
            for(int j = 2; j <= (i + 1) / 2; j++){
                // i번째 색을 선택하지 않은 경우 + i번째 색을 선택한 경우
                count[i][j] = (count[i - 1][j] + count[i - 2][j - 1]) % DIVIDER;
            }
        }
        System.out.println((count[N - 3][K - 1] + count[N - 1][K]) % DIVIDER);
    }
}