package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 1238. 파티
public class BOJ_1238 {
    static class Pair {
        int p1, p2;
        Pair(int p1, int p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        // minCost[i][j] -> i번 마을에서 j번 마을까지 가는 비용 최솟값
        int minCost[][] = new int [N+1][N+1];
        int X = Integer.parseInt(strArr[2]);
        // list[i] -> i번 마을에서 갈 수 있는 길이 있는 마을들 정보 갖고있는 list
        List<Pair> list[] = new List[N+1];
        for(int i=1; i<=N; i++) {
            list[i] = new ArrayList<Pair>();
            // 마을 스스로를 제외한 모든 마을의 최소 비용을 최댓값으로 초기화
            Arrays.fill(minCost[i], Integer.MAX_VALUE);
            minCost[i][i] = 0;
        }
        // 각 마을에서 갈 수 있는 마을 간선들을 list에 추가
        for(int i=0; i<M; i++) {
            strArr = br.readLine().split(" ");
            int from = Integer.parseInt(strArr[0]);
            int to = Integer.parseInt(strArr[1]);
            int cost = Integer.parseInt(strArr[2]);
            list[from].add(new Pair(to, cost));
        }
        // 탐색을 위한 Queue
        Queue<Integer> queue = new LinkedList<Integer>();
        // 탐색을 통해 i번 마을에서 각 마을까지의 최소비용 계산
        for(int i=1; i<=N; i++) {
            queue.offer(i);
            while(!queue.isEmpty()) {
                int now = queue.poll();
                // 현재 마을(now)에서 갈 수 있는 정점들에 대해 비용 계산
                for(int j=0, size=list[now].size(); j<size; j++) {
                    Pair p = list[now].get(j);
                    // i번 마을 -> p1 마을 비용 갱신한 적 없으면 지금의 비용이
                    // 최솟값이므로 진행
                    if(minCost[i][p.p1] == Integer.MAX_VALUE) {
                        minCost[i][p.p1] = minCost[i][now] + p.p2;
                        queue.offer(p.p1);
                        continue;
                    }
                    // i번 마을 -> now 마을 까지 비용 + now 마을 -> p1 마을 까지의 비용
                    // 이 값이 더 최소면 갱신 및 탐색할 마을에 추가
                    int c = minCost[i][now] + p.p2;
                    if(c < minCost[i][p.p1]) {
                        minCost[i][p.p1] = c;
                        queue.offer(p.p1);
                    }
                }
            }
        }
        int MAX = 0;
        // 각 마을에서 X 마을 까지 비용 + X 마을에서 각 마을까지 비용 합 중 최대 구하기
        for(int i=1; i<=N; i++) {
            MAX = Math.max(MAX, minCost[i][X] + minCost[X][i]);
        }
        System.out.println(MAX);
    }
}
