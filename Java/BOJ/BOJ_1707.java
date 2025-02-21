package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// 1707. 이분 그래프

// 이분 그래프라면 한 정점에서 인접한 정점은 모두 현재 정점과 반댓값을 갖고 있어야 함.
public class BOJ_1707 {
    static int V, E, visited[];
    static List<Integer> edges[];
    static boolean isDivided;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트 케이스의 개수 K;
        int K = Integer.parseInt(br.readLine());
        for(int t=0; t<K; t++) {
            String strArr[] = br.readLine().split(" ");
            // 그래프 정점의 개수 V, 간선의 개수 E;
            V = Integer.parseInt(strArr[0]);
            E = Integer.parseInt(strArr[1]);
            // V는 최대 2만, E는 최대 20만이므로 V^2 > E 라서 인접 리스트 사용
            // 간선 정보 저장할 인접 리스트 배열 edges[];
            edges = new ArrayList[V+1];
            // 정점을 방문했는지, 방문할 때 어떤 trigger 였는지 기록하는 배열 visited[];
            visited = new int[V+1];
            for(int i=0; i<E; i++) {
                strArr = br.readLine().split(" ");
                int from = Integer.parseInt(strArr[0]);
                int to = Integer.parseInt(strArr[1]);
                // 간선 정보 저장할 인접리스트 초기화 한 적 없으면 생성
                if(edges[from] == null) edges[from] = new ArrayList<Integer>();
                if(edges[to] == null) edges[to] = new ArrayList<Integer>();
                // 무방향 그래프로 처리
                edges[from].add(to);
                edges[to].add(from);
            }
            // 이분 그래프인지 여부 기록하는 변수 isDivided;
            isDivided = true;
            // 아직 그래프 탐색 안 한 정점들 전부 탐색
            for(int i=1; i<=V; i++) {
                // 만약 이분 그래프가 아니라고 판정 났으면 더 탐색 X
                if(!isDivided) break;
                // 간선이 없는 그래프는 인접한 정점 없다는 뜻이므로 이분 그래프에 영향 없으므로 탐색 X
                if(edges[i] == null) continue;
                if(visited[i] == 0) {
                    visited[i] = 1;
                    dfs(i, true);
                }
            }
            System.out.println(isDivided ? "YES" : "NO");
        }
    }
    /**
     * 이분 그래프인지 탐색하는 함수
     * @param now : 그래프 탐색할 현재 정점 번호
     * @param trigger : 현재 정점이 어떤 트리거인지 ( 1번이면 트리거 true, 2번이면 트리거 false)
     */
    public static void dfs(int now, boolean trigger) {
        // 이미 이분그래프 아니라고 판가름 났으면 굳이 탐색 X
        if(!isDivided) return;
        // now 정점에서 인접한 정점들 탐색
        for(int i : edges[now]) {
            // 만약 방문하지 않은 정점이라면
            if(visited[i] != 0) {
                // 해당 정점은 인접해있으므로 현재 정점과 반댓값을 띄어야 함.
                // trigger == true이면 현재 정점이 1이고, 인접한 정점이 똑같이 1인 게 존재하거나,
                // trigger == false이면 현재 정점이 2인데, 인접한 정점이 똑같이 2인게 존재한다면 이분그래프 아님
                if((trigger && visited[i] == 1) || (!trigger && visited[i] == 2)) {
                    // 이분 그래프 아니라고 설정 후 함수 탈출
                    isDivided = false;
                    return;
                }
                continue;
            }
            // 현재 정점의 trigger와 반댓값 기록해야 하므로
            // 현재 trigger = true이면(= 현재 정점이 1이면) 해당 정점은 2로,
            // 현재 trigger = false면(= 현재 정점이 2이면) 해당 정점은 1로 기록 후 방문
            visited[i] = trigger ? 2 : 1;
            dfs(i, !trigger);
        }
    }
}
