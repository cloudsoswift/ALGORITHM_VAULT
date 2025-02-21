package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 1107. 리모컨
public class BOJ_1107 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        boolean isBroken[] = new boolean[10];
        int M = Integer.parseInt(br.readLine());
        // 고장난 버튼 있을 경우, 기록
        if(M > 0) {
            String strArr[] = br.readLine().split(" ");
            for(String str : strArr) {
                int broken = Integer.parseInt(str);
                isBroken[broken] = true;
            }
        }
        // 목표 지점 = 시작 채널(100번) 이면 0 출력
        if(N == 100) {
            System.out.println(0);
            return;
        }
        int min = Math.max(N, Math.abs(100-N));
        Queue<Integer> queue = new LinkedList<Integer>();
        int time = 0;
        // 고장나지 않은 버튼들 한번 씩 누른 상태 BFS 준비
        for(int i=0; i<=9; i++) {
            if(isBroken[i]) continue;
            queue.offer(i);
            min = Math.min(min, Math.abs(i - N) + time + 1);
        }
        time++;
        // BFS 시작
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            while(queueSize-- > 0) {
                int now = queue.poll();
                // 현재 채널에서 고장나지 않는 각 채널 버튼 추가로 누르기
                for(int i=0; i<=9; i++) {
                    if(isBroken[i]) continue;
                    int nm = now * 10 + i;
                    int diff = Math.abs(nm - N) + time + 1;
                    if(diff < min) {
                        min = Math.min(min, diff);
                        queue.offer(nm);
                    }
                }
            }
            time++;
        }
        min = Math.min(Math.abs(100 - N), min);
        System.out.println(min);
    }
}
