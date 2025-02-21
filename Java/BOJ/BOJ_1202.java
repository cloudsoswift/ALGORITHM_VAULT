package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// 1202. 보석 도둑
public class BOJ_1202 {
    public static void main(String[] args) throws IOException {
//		------답 보기 전 아이디어 -----
//		일단, 각 가방에는 보석이 하나만 들어간다.
//		각 가방은 담을 수 있는 최대 무게가 정해져있다
//		즉, 보석들을 가치 순으로 정렬(pq 이용)한 뒤, 하나씩 뽑아서 제일 작은 무게의 가방부터 차례대로 대입해보면서
//		모든 보석을 순회하면? 각 가방에는 그 가방에 들어갈 수 있는 가장 가치 높은 보석이 들어갈 것이다.
//		-------------------------
//		하지만 위 아이디어 대로 구현하니 이미 보석이 있는 가방까지 매 번 체크하다보니 시간초과가 발생했다.
//		따라서 참고한 답의 아이디어를 차용했다.
//		1. 가방들을 무게가 적은 순으로 순회한다.
//		2. 각 가방별로 해당 가방 무게보다 높은 무게의 보석을 만날 때 까지 보석을 탐색(index 초기화X)한다.
//		3. 그러면서 해당 보석들의 가치를 pq에 저장한다. pq는 가장 높은 가치 순서대로 원소를 poll 한다.
//		4. 탐색이 끝나면(=>현재 가방 무게 < 현재 index의 보석 무게), pq에서 보석의 가치를 poll한다.
//		5. poll한 보석의 가치는 현재 가방에 넣을 수 있는 가장 높은 가치를 지닌 보석의 가치임이 보장된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        int N = Integer.parseInt(strArr[0]);
        int K = Integer.parseInt(strArr[1]);
        // [가방의 무게]를 저장하는 배열
        int bags[] = new int[K];
        // [보석의 무게, 보석의 가치] 배열들을 저장하는 2차원 배열
        int jewels[][] = new int[N][2];
        // 현재 가방의 무게 이하의 무게를 지닌 보석들의 가치를 보관하고 있는 PQ.
        // 가장 높은 가치가 먼저 나와야 하기때문에 Comparator 반대로 설정해줌.
        Queue<Integer> highCostJewel = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                return o1 > o2 ? -1 : (o1 == o2 ? 0 : 1);
            }
        });
        // 보석의 정보
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            int M = Integer.parseInt(strArr[0]);
            int V = Integer.parseInt(strArr[1]);
            jewels[i] = new int[] {M, V};
        }
        // 가방의 정보
        for(int i=0; i<K; i++) {
            int C = Integer.parseInt(br.readLine());
            bags[i] = C;
        }
        // 가방 무게 작은 순으로 정렬
        Arrays.sort(bags);
        // 보석 무게 작은 순으로 정렬
        Arrays.sort(jewels, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] < o2[0] ? -1 : (o1[0] == o2[0] ? 0 : 1);
            }
        });
        // 가치들 총합 저장할 변수
        long sum = 0;
        // 보석 탐색에 사용하는 index
        int j = 0;
        for(int i=0; i<K; i++) {
            while(j < N && bags[i] >= jewels[j][0]) {
                highCostJewel.offer(jewels[j][1]);
                j++;
            }
            if(!highCostJewel.isEmpty()) {
                sum += highCostJewel.poll();
            }
        }
        System.out.println(sum);
    }
}
