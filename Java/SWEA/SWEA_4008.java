package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 4008. [모의 SW 역량테스트] 숫자 만들기
public class SWEA_4008 {
    static int arr[], oper[], N, OPER_MAX, OPER_MIN;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String str[];
        for(int test_case=1; test_case<=T; test_case++) {
            N = Integer.parseInt(br.readLine());
            arr = new int[N];
            oper = new int[5]; // [ 덧셈, 뺄셈, 곱셈, 나눗셈 ]
            OPER_MAX = Integer.MIN_VALUE;
            OPER_MIN = Integer.MAX_VALUE;
            str = br.readLine().split(" "); // 연산자 갯수 입력
            for(int i=1; i<5; i++) {
                oper[i] = Integer.parseInt(str[i-1]);
            }
            str = br.readLine().split(" "); // 숫자 입력
            for(int i=0; i<N; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
            perm(0, new int[N-1]);
            System.out.printf("#%d %d\n",test_case,(OPER_MAX - OPER_MIN));
        }
    }
    public static void perm(int cnt, int operOrder[]) {
        if(cnt == N-1) {
            int sum = arr[0];
            for(int i=0; i<cnt; i++) {
                switch(operOrder[i]) {
                    case 1: // 덧셈
                        sum += arr[i+1];
                        break;
                    case 2: // 뺄셈
                        sum -= arr[i+1];
                        break;
                    case 3: // 곱셈
                        sum *= arr[i+1];
                        break;
                    case 4: // 나눗셈
                        sum /= arr[i+1];
                        break;
                }
            }
            if(sum > OPER_MAX) {
                OPER_MAX = sum;
            }
            if(sum < OPER_MIN) {
                OPER_MIN = sum;
            }
            return;
        }
        for(int i=1; i<5; i++) {
            if(oper[i]>0) {
                operOrder[cnt] = i;
                oper[i]--;
                perm(cnt+1, operOrder);
                oper[i]++;
                operOrder[cnt] = 0;
            }
        }
    }
}
