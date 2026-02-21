package BOJ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 30797. 가희와 여행가요
public class BOJ_30797 {
    // union-find를 위한 Parent 배열
    static int[] parent;
    // union시, tree 높이 최적화를 위해 각 집단에 속한 노드의 개수를 기록하는 배열 childSize
    static int[] childsSize;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        int n = Integer.parseInt(strArr[0]);
        int time = 0;
        int Q = Integer.parseInt(strArr[1]);
        // Q개의 철도 노선 정보를 기록하는 배열 bridge
        int[][] bridge = new int[Q][4];
        // union-find를 위한 배열들 초기화
        parent = new int[n + 1];
        childsSize = new int[n + 1];
        for(int i=1; i<=n; i++) {
            parent[i] = i;
            childsSize[i] = 1;
        }
        // 출력할 총 건설비용 cost
        long cost = 0;
        // 철도 노선 정보들 입력 받아 저장
        for(int i=0; i<Q; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<4; j++) {
                bridge[i][j] = Integer.parseInt(strArr[j]);
            }
        }
        // 건설 비용(bridge[i][2]) 순으로 오름차순 정렬하되, 비용이 같은 경우 건설 시간(bridge[i][3])순으로 오름차순 정렬
        Arrays.sort(bridge, (o1, o2) -> o1[2] < o2[2] ? -1 :
                (o1[2] == o2[2] ? (
                        o1[3] < o2[3] ? -1 : 1
                ) : 1));
        // 각 철도 노선을 순회하며,
        for(int i=0; i<Q; i++) {
            // 노선이 잇는 양 끝 도시가 같은 집합이 아닌 경우
            if(find(bridge[i][0]) != find(bridge[i][1])) {
                // 노선을 건설 ( 둘을 같은 집합으로 잇고, 노선 비용 및 건설 시간 반영)
                union(bridge[i][0], bridge[i][1]);
                cost += bridge[i][2];
                time = Math.max(time, bridge[i][3]);
            }
        }
        int count = find(1);
        for(int i=2; i<=n; i++) {
            if(find(i) != count) {
                // n개의 도시 중 연합에 포함되지 않은 도시가 있을 경우 -1 출력
                System.out.println(-1);
                return;
            }
        }
        // 연합을 완료하는 시점 및 총 건설 비용을 출력
        System.out.println(time + " " + cost);
    }
    public static int find(int n) {
        if (parent[n] == n)
            return n;
        else return parent[n] = find(parent[n]);
    }
    public static void union(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);

        if(parentA != parentB) {
            // a와 b가 같은 집합이 아닌 경우,
            // 소속 노드가 더 많은(즉, childsSize가 더 큰) 쪽을 바라보도록 union
            if(childsSize[parentA] > childsSize[parentB]) {
                parent[parentB] = parent[parentA];
                childsSize[parentA] += childsSize[parentB];
            } else {
                parent[parentA] = parent[parentB];
                childsSize[parentB] += childsSize[parentA];
            }
        }
    }
}
