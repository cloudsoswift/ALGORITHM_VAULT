// 최대 유량이라는 콘셉트에 대해 이해가 잘 안되어 찾아보고 푼 문제
// https://www.geeksforgeeks.org/dsa/ford-fulkerson-algorithm-for-maximum-flow-problem/
// https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
// 대강 에드먼드-카프 알고리즘이 벨만-포드 알고리즘이랑 비슷하단 느낌 듦.

// 일반적인 BFS와 최대 유량 문제가 다른 점은 '역방향 간선' 과 '잔여 그래프'임
// 역방향 간선(Backward Edge): 이미 파이프에 흘려보낸 유량을 취소한다는 의미의 가상의 통로.
//      기존에 존재하는 에지의 역방향으로 `-에지  유량` 만큼의 유량을 갖는 가상의 에지를 만듦.
// 잔여 그래프(residual Graph): 가능한 추가 유량을 나타내는 그래프. 즉, 특정 위치에 추가로 보낼 수 있는 유량들을 기록한 그래프
//      잔여 그래프의 각 에지들은 잔여 용량(reisidual capacity)이라는 값을 갖는데, 이는 '에지의 원래 유량 - 현재 유량'을 나타냄.
//      예시 1) 유량 20짜리 파이프를 통해 현재 유량이 0인 곳에 20만큼 더 흘려보낼 수 있으므로 잔여 용량은 20
//      예시 2) 유량 10짜리 파이프를 통해 현재 유량이 5인 곳에 5만큼 더 흘려보낼 수 있으므로 잔여 용량은 5

// 위 두가지 개념을 사용하여 다음과 같은 방법으로 최대 유량을 구함
// 1. 먼저 각 에지들을 입력받아, 인접 행렬을 통해 연결해줌. 이때, 문제에서 병렬로 연결된 파이프는 그들의 용량 합만큼 유량이 늘어난다 했으므로
//     동일한 출발-목적지를 갖는 파이프는 인접 행렬에 그 유량을 누적해주는 형태로 처리
// 2. 시작지점 S('A')에서 시작하여, 도착지점('Z')에 도달할 때까지 인접한 파이프 중 하나를 선택해나가는 BFS를 수행.
//      이때, 추후 도착지점에서 시작지점까지 되돌아가기 위해 이전 노드(parent[i])를 기록함.
//      또한, 이동할 파이프는 잔여 용량이 0 초과, 즉 유량을 최소한 1이라도 더 흘려보낼 수 있어야 함.
// 3. 만약, 위 탐색 후 도착저점의 이전노드(parent['Z'])가 존재하지 않을 경우, 위 탐색에서 시작지점->도착지점 까지 갈 수 있는
//      유효한 새로운 루트를 찾지 못했음을 의미하므로 탈출
//      그렇지 않다면, 유효한 새 루트를 발견한 것이므로 다음 작업을 수행
//  3.1. 도착지점에서 시작지점으로 되돌아가며, '이전 지점 -> 현재 지점'의 잔여 유량들 중 최소값을 구함(minimumFlow)
//       이 값이 이번에 구한 루트에서 시작->도착지점까지 흐를 수 있는 유량의 크기임.
//  3.2. 3.1에서 구한 최소 유량을 마찬가지로 도착지점에서 시작지점으로 되돌아가며, '이전 지점 -> 현재 지점'의 유량에 반영시킴.
//       즉, '현재 A->B 로 연결된 파이프에 이만큼의 유량을 이미 흘려보냈음'을 기록해놓는 것.
//       또한, 역방향(현재 지점 -> 이전 지점)의 유량에 그 유량을 -로 반영시킴.
//       이를 통해 '이 파이프를 선택한 경우와, 이 파이프를 선택하지 않은 경우 모두를 계산할 수 있게 해줌'
//       ex) 예를 들어, A, B, C, D가 있고 최대 유량을 구하기 위해 A->B->D, 그리고 A->C->D 루트로 물을 흘려보내야 한다고 가정
//           이때, 실수로 A->B->C->D 루트를 선택해버린다면, C->D 루트가 막혀 더 이득인 루트들을 선택할 수 없게 됨.
//           하지만, 역방향 간선을 도입하게 되면 A->B->C->D 루트를 탐색한 이후, A->C->B->D라는 새로운 루트를 선택할 수 있음.
//              이 A->C->B->D라는 루트는 실제로 불가능한 루트(실제로는 C->B라는 에지가 없음)이며, C->B는 역방향 간선임.
//              즉, 사실상 이전 루트의 A->[B->C]->D 부분을 취소하는 셈인것.
//              이를 통해 이전 루트를 A->C->D로 만들고, 이번 선택에서는 A->B->D를 선택하는 것이 됨.
//  3.3  최소 유량을 최대 유량(totalFlow) 변수에 누적시킨 후, 2로 돌아가 다시 탐색 반복함.
// 4. 3에서 탈출한 경우 지금까지 구한 최대 유량을 출력한 뒤 종료.

package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 6086. 최대 유량
public class BOJ_6086 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr;
        int N = Integer.parseInt(br.readLine());
        int S = 'A', E = 'Z';
        // edges[A][B]: A에서 B로 가는 파이프의 유량 합을 기록
        // flow[A][B]: A에서 B로 현재 흘러가고 있는 유량을 기록
        int[][] edges = new int[128][128];
        int[][] flow = new int[128][128];
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            char A = strArr[0].charAt(0);
            char B = strArr[1].charAt(0);
            int F = Integer.parseInt(strArr[2]);
            edges[A][B] += F;
            edges[B][A] += F;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        int totalFlow = 0;
        while(true) {
            queue.offer(S);
            // 탐색 경로에서 각각 노드의 이전 노드를 기록하는 배열
            int[] parent = new int[128];
            while(!queue.isEmpty() && parent[E] == 0) {
                int now = queue.poll();
                for(int i='a'; i<='z'; i++) {
                    int norm = i, capped = i - 32;
                    // 해당 노드에 방문한 적이 없으며, 잔여 용량이 남아있는 경우 이동
                    if(parent[norm] == 0 && edges[now][norm] - flow[now][norm] > 0) {
                        parent[norm] = now;
                        queue.offer(norm);
                    }
                    if(parent[capped] == 0 && edges[now][capped] - flow[now][capped] > 0) {
                        parent[capped] = now;
                        queue.offer(capped);
                    }
                }
            }
            if(parent[E] == 0) break;
            int minimumFlow = Integer.MAX_VALUE;
            for (int i = E; i != S; i = parent[i]) {
                minimumFlow = Math.min(minimumFlow, edges[parent[i]][i] - flow[parent[i]][i]);
            }

            for (int i = E; i != S; i = parent[i]) {
                flow[parent[i]][i] += minimumFlow;
                flow[i][parent[i]] -= minimumFlow;
            }
            totalFlow += minimumFlow;
            queue.clear();
        }
        System.out.println(totalFlow);
    }
}
