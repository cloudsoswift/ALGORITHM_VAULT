package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ_1700 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        int N = Integer.parseInt(strArr[0]);
        int K = Integer.parseInt(strArr[1]);
        int[] arr = new int[K];
        Queue[] queues = new Queue[K + 1];
        for(int i=0; i<=K; i++)
            queues[i] = new LinkedList<Integer>();
        strArr = br.readLine().split(" ");
        for(int i=0; i<K; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
            queues[arr[i]].offer(i);
        }
        boolean[] v = new boolean[K + 1];
        int capacity = 0;
        int count = 0;
        for(int i=0; i<K; i++) {
            queues[arr[i]].poll();
            if (v[arr[i]]) {
                // 이미 쓰고있는 경우
            } else {
                if (capacity < N) {
                    // 가용한 멀티탭 구멍이 있는 경우
                    capacity++;
                    v[arr[i]] = true;
                } else {
                    // 가용한 멀티탭 구멍이 없는 경우,
                    // 뽑을 플러그를 골라야 함
                    int target = 0;
                    int nextTime = 0;
                    for(int j=1; j<=K; j++) {
                        // 사용중이지 않는 플러그는 스킵
                        if(!v[j]) continue;
                        if (queues[j].isEmpty()) {
                            // 현재 시점 이후로 다시 사용할 일 없는 플러그라면
                            // 해당 플러그 뽑음
                            target = j;
                            break;
                        }
                        if((Integer) queues[j].peek() > nextTime) {
                            // '해당 플러그를 다시 사용할 가장 빠른 시간'이
                            // 이전에 뽑을려고 했던 플러그보다 더 먼 경우
                            // 해당 플러그를 뽑을 플러그로 기록
                            target = j;
                            nextTime = (Integer) queues[j].peek();
                        }
                    }
                    v[target] = false;
                    v[arr[i]] = true;
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
