package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

// 1715. 카드 정렬하기
public class BOJ_1715 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int count = 0;
        // 카드 묶음은 작은 것들 끼리 합쳐나가는게 가장 적은 비교횟수를 가지므로,
        // 매 반복마다 가장 작은 것들을 뽑기 위해 PQ 사용
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i=0; i<N; i++) {
            pq.offer(Integer.parseInt(br.readLine()));
        }
        if(pq.size() == 1) {
            // 카드 묶음이 하나만 있을 경우, 비교할 일이 없으므로 0 출력
            System.out.println(0);
        } else {
            while(!pq.isEmpty()) {
                int left = pq.poll();
                if(!pq.isEmpty()) {
                    int right = pq.poll();
                    count += left + right;
                    pq.offer(left + right);
                } else {
                    // 하나만 뽑을 수 있는 경우,
                    // 카드 묶음들이 다 뭉쳐진 묶음 하나만 PQ에 들어있는 경우 이므로 별 작업 없이 탈출
                    break;
                }
            }
            System.out.println(count);
        }
    }
}
