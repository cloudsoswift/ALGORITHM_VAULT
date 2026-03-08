// BFS로 풀었으나, 휴리스틱 A* 방법으로 푸는 다른 풀이를 보고 다시 풀어본 문제

// ============= 기존 풀이 ==================================
// [퍼즐 상태(long), 이동하는데 소요한 비용]을 담는 PQ를 써서 이동 비용이 최소인 경우들을 계속 꺼냄
// 이때, 빈칸(0이 있는 칸)을 상하좌우 중 이동 가능한 곳으로 각각 이동했을때의 새로운 퍼즐 상태가
// Map에 기록되어 있는지, 만약 기록되어 있다면 그때의 이동 비용보다 현재 이동비용이 싼경우 해당 상태도 탐색해나가는 방법으로 풂

// ============= 참고한 풀이 ================================
// 해당 문제는 경험적 탐색 방법을 활용해야 함.
// 즉, BFS, DFS처럼 모든 방법을 시도하지 않고, 사전에 주어진 규칙, 데이터들을 이용해 더 빨리 탐색하는 방법임.
// 이 문제에서는 퍼즐의 목표 상태([[1, 2, 3], [4, 5, 6], [7, 8, 0]])를 알고 있고,
// 임의의 한 위치에 있는 퍼즐이 본인의 목표 자리에 가기까지 몇 번의 이동이 필요한지(즉, 맨하탄 거리)를 계산할 수 있으므로
// 특정 퍼즐을 이동시켰을 때, "각 퍼즐들의 목표 위치까지의 맨하탄 거리 합"이 적어지는 방향으로 가는게 당연히 좋을 것임.
// 이때, A* 알고리즘을 사용해 `f(n) = g(n) + h(n)` 이 낮은 칸을 우선적으로 탐색하도록 함.
// g(n) : 출발점에서 노드 n까지 오는데 걸린 비용. 이 문제에서는 `초기 상태에서 현재 상태가 되기 위해 이동한 횟수`임
// h(n) : 현재 노드 n에서 목적지까지 가는데 들 것으로 예상되는 추정 비용.
//      이 문제에서는 `제자리에 있지않은 타일 개수` 또는 `각 퍼즐들의 목표 위치까지 맨하탄 거리 합`을 사용
//      그리고 그 중, 맨하탄 거리 합이 더 구체적인 정보를 제공하므로 이를 주로 사용
// f(n) : 위 두 값을 합한 최종 점수.
// 따라서 f(n)을 기준으로 정렬하는 PQ를 사용해, 맨하탄 거리 합이 가장 낮은 것들부터 따져봄

package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// 1525. 퍼즐 - 휴리스틱 버전
public class BOJ_1525_heuristic {
    // [상, 하, 좌, 우]
    static int dr[] = new int[] {-1, 1, 0, 0};
    static int dc[] = new int[] {0, 0, -1, 1};
    // 0~8까지 각각 정답 위치가 어딘지 담고있는 배열
    static int answerPosition[][] = new int [][]{{2, 2}, {0, 0}, {0, 1}, {0, 2}, {1, 0},
            {1, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // [퍼즐 상태, 도달하는데 드는 최소 비용]을 담는 Map
        Map<Long, Long> dict = new HashMap<>();
        String[] strArr;
        int n = 3;
        // 초기 상태 -> 목표 상태까지의 맨하탄 거리 나타내는 변수 manhattan
        long manhattan = 0;
        // 초기 상태의 퍼즐을 나타내는 arr
        long arr = 0;
        // 목표 상태의 퍼즐을 나타내는 goal
        long goal = 0;
        // long 변수의 36비트(9 * 4)를 9칸짜리 배열로 사용
        // 또한, 3*3인 퍼즐을 1차원 배열에 나타내기 위해 [[0,0],[0,1],[0,2],[1,0],...] 과 같은 형태로
        // 늘어뜰여 전개
        for(int i=0; i<n; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<n; j++) {
                int p = Integer.parseInt(strArr[j]);
                arr |= ((long)p << ((i*3+j) * 4));
                // 맨하탄 거리 기록
                manhattan += Math.abs(answerPosition[p][0] - i) + Math.abs(answerPosition[p][1] - j);
            }
        }
        // 목표 상태도 초기화
        for(int i=0; i<8; i++) {
            goal |= ((long) (i+1) << (i * 4));
        }
        // 시작상태를 넣고 시작
        dict.put(arr, 0L);
        // [퍼즐 상태, 맨하탄 거리, 이동 비용] 들을 담으며,
        // f(n) = g(n) (현재 지점에 도달하는데 든 비용, 즉 o[2]) + h(n) (목표 지점까지 소요될 것으로 예상되는 비용, 즉 o[1]) 이므로
        // o[1] + o[2] 를 한 값을 기준으로 PQ 정렬
        PriorityQueue<long[]> queue = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1] + o1[2], o2[1] + o2[2]));
        queue.offer(new long[] {arr, manhattan, 0L});
        while(!queue.isEmpty()) {
            long[] now = queue.poll();
            if(now[0] == goal) {
                System.out.println((int) now[2]);
                return;
            }
            if(dict.get(now[0]) < now[2]) continue;
            for(int i=0; i<9; i++) {
                // 빈칸(0이 있는 칸) 찾기
                if(((now[0] >> (i * 4)) & 0xFL) == 0) {
                    // 상,하,좌,우 방향 각각 탐색하면서 이동 가능한지 확인
                    int r = i / 3;
                    int c = i % 3;
                    for(int j=0; j<4; j++) {
                        int mr = r + dr[j];
                        int mc = c + dc[j];
                        if(mr < 0 || mc < 0 || mr >= n || mc >= n) continue;
                        // 이동 가능할 경우 해당 위치 값과 0을 스왑
                        long swapped = longSwap(now[0], i, mr * 3 + mc);
                        if(dict.getOrDefault(swapped, 100000L) > now[2] + 1) {
                            dict.put(swapped, now[2] + 1);
                            int victim = (int) ((now[0] >> ((mr * 3 + mc) * 4)) & 0xFL);
                            int victimManGap = (Math.abs(answerPosition[victim][0] - r) + Math.abs(answerPosition[victim][1] - c))
                                    - (Math.abs(answerPosition[victim][0] - mr) + Math.abs(answerPosition[victim][1] - mc));
                            long newManhattan = now[1] + victimManGap;
                            queue.offer(new long[] {swapped, newManhattan, now[2] + 1});
                        }
                    }
                    break;
                }
            }
        }
        System.out.println(-1);
    }
    public static long longSwap(long mask, int left, int right) {
        int l = (int) ((mask >> (left * 4)) & 0xFL);
        int r = (int) ((mask >> (right * 4)) & 0xFL);

        mask &= ~(0xFL << (left * 4));
        mask &= ~(0xFL << (right * 4));

        mask |= ((long) l << (right * 4));
        mask |= ((long) r << (left * 4));

        return mask;
    }
}
