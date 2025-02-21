package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// 1005. ACM Craft
public class BOJ_1005 {
    public static void main(String[] args) throws IOException, NumberFormatException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[];
        int T = Integer.parseInt(br.readLine());
        int N, K, time[], dp[];
        Stack<Integer> dependencies[];
        for(int tc=0; tc<T; tc++) {
            strArr = br.readLine().split(" ");
            // 건물 개수 N, 건설순서 규칙 개수 K ( 건물은 1~N번까지 존재 )
            N = Integer.parseInt(strArr[0]);
            K = Integer.parseInt(strArr[1]);
            // 각 건물당 건설에 걸리는 시간 Dn
            time = new int[N+1];
            // 각 건물당 종속되는 건물들 다 짓는데 걸리는 시간 저장하는 배열 dp
            dp = new int[N+1];
            strArr = br.readLine().split(" ");
            for(int i=1; i<=N; i++) {
                time[i] = Integer.parseInt(strArr[i-1]);
            }
            // 건설 순서 X Y ( Y를 지을려면 X가 지어져 있어야 함 )
            dependencies = new Stack[N+1];
            int inDegree[] = new int[N+1];
            for(int i=0; i<K; i++) {
                strArr = br.readLine().split(" ");
                int X = Integer.parseInt(strArr[0]);
                int Y = Integer.parseInt(strArr[1]);
                if(dependencies[X] == null)
                    dependencies[X] = new Stack<Integer>();
                dependencies[X].push(Y);
                inDegree[Y]++;
            }
            // 승리하기 위해 지어야하는 건물 번호 W
            int W = Integer.parseInt(br.readLine());
            // inDegree == 0, 즉 해당 건물을 짓기 위한 종속성이 없는 건물들 먼저 짓기 시작.
            Queue<Integer> queue = new LinkedList<Integer>();
            for(int i=1; i<=N; i++) {
                if(inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
            while(!queue.isEmpty()) {
                // queue 안에 들어옴 = 해당 건물을 지을 수 있음
                int now = queue.poll();
                // 현재 건물 번호 == 승리하기 위해 지어야 하는 건물 번호 W인 경우
                // W를 짓기위해 선행되어야 하는 건물들 중 가장 시간 오래걸리는 것의 시간 + W를 짓는데 걸리는 시간 출력
                if(now == W) {
                    System.out.println(dp[W] + time[W]);
                    return;
                }
                // 현재 건물 번호가 선행되어야 하는 건물이 있는 경우
                if(dependencies[now] != null) {
                    while(!dependencies[now].isEmpty()) {
                        int next = dependencies[now].pop();
                        // 현재 건물을 짓기 위한 종속성의 최대 시간 + 현재 건물 건설시간이, 다음 건물을 짓기 위한 다른 종속성들의 최대시간 보다 높으면 갱신
                        int sum = dp[now] + time[now];
                        dp[next] = dp[next] > sum ? dp[next] : sum;
                        // 진입 차수가 0, 즉 해당 건물을 짓기 위한 종속성 건물들이 다 지어진 상태면 해당건물 건설 시작(=Queue에 삽입)
                        if(--inDegree[next] == 0) {
                            queue.offer(next);
                        }
                    }
                }
            }
        }
    }
}
