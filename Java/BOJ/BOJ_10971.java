package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 10971. 외판원 순회 2
public class BOJ_10971 {
    static int N, cost[][], LOWEST_COST;
    static boolean v[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        cost = new int[N][N];
        v = new boolean[N];
        LOWEST_COST = Integer.MAX_VALUE;

        String strArr[];
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                cost[i][j] = Integer.parseInt(strArr[j]);
            }
        }
        for(int i=0; i<N; i++) {
            dfs(i, i, 0, 0);
        }
        System.out.println(LOWEST_COST);
    }
    public static void dfs(int start, int now, int sum, int cnt) {
        if(cnt == N && start == now) {
            LOWEST_COST = Math.min(LOWEST_COST, sum + cost[now][0]);
        }
        for(int i=0; i<N; i++) {
            if(cost[now][i]==0) continue;
            if(!v[i] && (sum + cost[now][i]) < LOWEST_COST) {
                v[i] = true;
                dfs(start, i, sum + cost[now][i], cnt+1);
                v[i] = false;
            }
        }
    }
}
