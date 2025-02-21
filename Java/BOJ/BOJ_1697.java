package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 1697. 숨바꼭질
public class BOJ_1697 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        int N = Integer.parseInt(str[0]);
        int K = Integer.parseInt(str[1]);
        int V[] = new int[100001]; // 방문 배열
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(N);
        int time = 0;
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            while(queueSize-->0) {
                int now = queue.poll();
                if(now == K) {
                    System.out.println(time);
                    return;
                }
                // 3가지 수 ( 앞, 뒤로 이동하거나 순간이동하는 경우 ) BFS로 탐색
                if(now-1 >= 0 && V[now-1] != 1) {
                    queue.offer(now-1);
                    V[now-1] = 1;
                }
                if(now+1 <= 100000 && V[now+1] != 1) {
                    queue.offer(now+1);
                    V[now+1] = 1;
                }
                if(now*2 <= 100000 && V[now*2] != 1) {
                    queue.offer(now*2);
                    V[now*2] = 1;
                }
            }
            time++; // 탐색 깊이 = 시간
        }
    }
}
