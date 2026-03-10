package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 22251. 빌런 호석
public class BOJ_22251 {
    static int count, N, K;
    static int[][] ledsNeedToReversal;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        // 엘리베이터는 1층 ~ N층까지 이용 가능
        N = Integer.parseInt(strArr[0]);
        visited = new boolean[N+1];
        // 엘리베이터의 층수를 보여주는 K자리 수
        // 만약 실제 수가 K자리 미만의 수라면, 앞의 빈 공간은 0으로 채워진다.
        K = Integer.parseInt(strArr[1]);
        // 호석이 반전시킬 수 있는 LED 개수 P (1~P개 반전 가능)
        int P = Integer.parseInt(strArr[2]);
        // 현재 엘리베이터가 멈춰있는 층수 X
        int X = Integer.parseInt(strArr[3]);
        visited[X] = true;
        visited[0] = true;
        // ledsNeeedToReversal[i][j] : 현재 숫자 i가 표시되어 있을때, 이를 j로 바꾸기위해 반전시켜야 하는 led 개수
        ledsNeedToReversal = new int[][] {
                {0, 4, 3, 3, 4, 3, 2, 3, 1, 2},
                {4, 0, 5, 3, 2, 5, 6, 1, 5, 4},
                {3, 5, 0, 2, 5, 4, 3, 4, 2, 3},
                {3, 3, 2, 0, 3, 2, 3, 2, 2, 1},
                {4, 2, 5, 3, 0, 3, 4, 3, 3, 2},
                {3, 5, 4, 2, 3, 0, 1, 4, 2, 1},
                {2, 6, 3, 3, 4, 1, 0, 5, 1, 2},
                {3, 1, 4, 2, 3, 4, 5, 0, 4, 3},
                {1, 5, 2, 2, 3, 2, 1, 4, 0, 1},
                {2, 4, 3, 1, 2, 1, 2, 3, 1, 0}
        };
        int[] arr = new int[K];
        int pos = K-1;
        while(X != 0) {
            arr[pos] = X % 10;
            pos--;
            X /= 10;
        }
        comb(0, arr, P);
        System.out.println(count);
    }
    public static void comb(int digit, int[] arr, int remainP) {
        if(digit == K || remainP == 0) {
            int v = 0;
            for(int i=0; i<K; i++) {
                v *= 10;
                v += arr[i];
            }
            if(v > N) return;
            if(visited[v]) return;
            visited[v] = true;
            count++;
            return;
        }

        for(int i=0; i<=9; i++) {
            if(ledsNeedToReversal[arr[digit]][i] <= remainP) {
                int tmp = arr[digit];
                arr[digit] = i;
                comb(digit + 1, arr, remainP - ledsNeedToReversal[tmp][i]);
                arr[digit] = tmp;
            }
        }
    }
}
