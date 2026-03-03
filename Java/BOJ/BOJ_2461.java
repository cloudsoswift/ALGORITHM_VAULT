package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

// 2461. 대표 선수
public class BOJ_2461 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        int[][] arr = new int[N][M];
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(strArr[j]);
            }
            // 각 팀의 선수들 능력치 오름차순으로 정렬
            Arrays.sort(arr[i]);
        }
        int MIN = Integer.MAX_VALUE;
        int MAX = Integer.MIN_VALUE;
        // [능력치, 해당 선수가 속한 팀] 들로 이뤄진 PriorityQueue
        // 능력치 오름차순, 능력치가 같을 경우 속한 팀 오름차순으로 정렬됨
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] < o2[0] ? -1 : (
                o1[0] == o2[0] ? (o1[1] < o2[1] ? -1 : 1) : 1
                ));
        // 일단 각 팀에서 가장 능력치가 낮은 선수들 뽑아서 PQ에 넣고, 대표 선수 중 능력치 최소, 최댓값 갱신
        for(int i=0; i<N; i++) {
            pq.offer(new int[]{arr[i][0], i});
            MIN = Math.min(MIN, arr[i][0]);
            MAX = Math.max(MAX, arr[i][0]);
        }
        // 현재 각 팀의 몇 번째 선수를 보고있는지, 각 index들이 기록된 배열
        int[] indexes = new int[N];
        // 대표선수 중 최댓값 - 최솟값 갭이 최소인 경우의 값을 기록하는 변수
        int MINIMUM_GAP = MAX - MIN;
        while(true) {
            // 현재 대표선수 중 능력치가 가장 적은 선수 뽑음
            int[] victim = pq.poll();
            // 해당 선수 팀의 index 한칸 증가시킴
            int nextIndex = ++indexes[victim[1]];
            if(nextIndex == M)
                // 그 index가 M, 즉 해당 팀의 선수 다 본 경우 반복 종료
                break;
            // 해당 팀의 다음 선수 능력치
            int nextValue = arr[victim[1]][nextIndex];
            // 그 선수의 능력치를 PQ에 넣음
            pq.offer(new int[]{nextValue, victim[1]});
            // 해당 선수를 선택한 기준으로 MAX, MIN 값 갱신
            MAX = Math.max(MAX, nextValue);
            MIN = pq.peek()[0];
            // 최댓값 - 최솟값 갭 갱신
            MINIMUM_GAP = Math.min(MINIMUM_GAP, MAX - MIN);
        }
        System.out.println(MINIMUM_GAP);
    }
}
