package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 16987. 계란으로 계란치기
public class BOJ_16987 {
    static int arr[][], MIN_rest, N;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N][2];
        MIN_rest = N;
        String str[];
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            // [계란 내구도, 계란 무게]
            arr[i][0] = Integer.parseInt(str[0]);
            arr[i][1] = Integer.parseInt(str[1]);
        }
        for(int i=1; i<N; i++) {
            dfs(0, i, N);
        }
        System.out.println(N-MIN_rest);
    }
    static void dfs(int now, int next, int rest) {
        //가장 최근에 든 계란이 가장 오른쪽에 위치한 계란일 경우
        if(now == N) {
            return;
        }
        //손에 든 계란이 깨졌거나 깨지지 않은 다른 계란이 없으면 치지 않고 넘어간다.
        if(arr[now][0] <= 0 || rest <= 1) {
            for(int i=0; i<N; i++) {
                if(arr[i][0] <=0 || now+1 == i) continue;
                dfs(now+1, i, rest);
            }
            return;
        }
        int m = 0;
        //손에 들고 있는 계란으로 깨지지 않은 다른 계란 중에서 하나를 친다.
        arr[now][0] -= arr[next][1];
        if(arr[now][0] <= 0) m++;
        arr[next][0] -= arr[now][1];
        if(arr[next][0] <= 0) m++;
        if(rest-m < MIN_rest) MIN_rest = rest-m;
        for(int i=0; i<N; i++) {
            if(arr[i][0]<=0 || now+1 == i) continue;
            //가장 최근에 든 계란의 한 칸 오른쪽 계란을 손에 들고 2번 과정을 다시 진행한다.
            dfs(now+1, i, rest-m);
        }
        arr[now][0] += arr[next][1];
        arr[next][0] += arr[now][1];
    }
}
