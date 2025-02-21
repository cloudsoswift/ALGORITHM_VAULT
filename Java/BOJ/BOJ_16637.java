package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 16637. 괄호 추가하기
public class BOJ_16637 {
    static int arr[], N, MAX;
    static char oper[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N/2+1];
        oper = new char[N/2];
        String str = br.readLine();
        int j = 0;
        for(int i=0; i<N; i++) {
            if(i%2 == 0) {
                arr[i/2] = Character.digit(str.charAt(i), 10);
            } else {
                oper[j++] = str.charAt(i);
            }
        }
        MAX = Integer.MIN_VALUE;
        dfs(0);
        System.out.println(MAX);
    }
    public static void dfs(int cnt) {
        if(N/2 == cnt) {
            System.out.println(Arrays.toString(arr));
            System.out.println(Arrays.toString(oper));
            int sum = arr[0];
            for(int i=0; i<cnt; i++) {
                if(oper[i] != 0) {
                    switch(oper[i]) {
                        case '+':
                            sum += arr[i+1];
                            break;
                        case '-':
                            sum -= arr[i+1];
                            break;
                        case '*':
                            sum *= arr[i+1];
                            break;
                        case '/':
                            sum /= arr[i+1];
                            break;
                    }
                }
            }
            if(sum > MAX) {
                MAX = sum;
            }
            return;
        }
        boolean isPossible = true;
        // 현재 연산자와 앞 뒤 숫자를 괄호로 묶기전,
        // 현재 연산자 앞의 연산자와 뒤의 연산자가 괄호로 묶였었는지 검사
        // ( 이미 묶인 경우, 현재 연산자도 괄호로 묶으면 괄호가 중첩되므로 X )
        if(cnt-1 >= 0) {
            if(oper[cnt-1] == 0)
                isPossible = false;
        }
        if(cnt+1 < N/2) {
            if(oper[cnt+1] == 0)
                isPossible = false;
        }
        if(isPossible) {
            // 앞, 뒤 연산자가 괄호로 묶이지 않은 경우
            int tmpInt = arr[cnt];
            int tmpIntNext = arr[cnt+1];
            char tmpChar = oper[cnt];
            switch(oper[cnt]) {
                case '+':
                    arr[cnt] += arr[cnt+1];
                    arr[cnt+1] = 0;
                    break;
                case '-':
                    arr[cnt] -= arr[cnt+1];
                    arr[cnt+1] = 0;
                    break;
                case '*':
                    arr[cnt] *= arr[cnt+1];
                    arr[cnt+1] = 0;
                    break;
                case '/':
                    arr[cnt] /= arr[cnt+1];
                    arr[cnt+1] = 0;
                    break;
            }
            oper[cnt] = 0;
            dfs(cnt+1);
            arr[cnt] = tmpInt;
            oper[cnt] = tmpChar;
            arr[cnt+1] = tmpIntNext;
        }

        dfs(cnt+1);
    }
}
