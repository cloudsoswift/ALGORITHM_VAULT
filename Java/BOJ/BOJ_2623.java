package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 2623. 음악프로그램
public class BOJ_2623 {
    static class Node{
        int to;
        Node next;
        Node(int to, Node next){
            this.to = to;
            this.next = next;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, M;
        // 가수 순서 값 저장을 위한 list
        ArrayList<Integer> result = new ArrayList<>();
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        // 각 가수의 진입차수 ( 선행 되어야하는 가수 수 ) 배열
        int inDegree[] = new int[N+1];
        // 각 가수의 다음 가수 배열
        Node list[] = new Node[N+1];
        for(int i=0; i<=N; i++) {
            list[i] = new Node(0, null);
        }
        // 입력받으면서 진입차수 배열에 1씩 증가
        for(int i=0; i<M; i++) {
            str = br.readLine().split(" ");
            int before = -1;
            // 각 줄 별로 순서에 맞게 Node next 할당
            for(int j=1, K=Integer.parseInt(str[0]); j<=K; j++) {
                if(before==-1) {
                    before = Integer.parseInt(str[j]);
                    continue;
                }
                int now = Integer.parseInt(str[j]);
                inDegree[now]++;
                list[before].next = new Node(now, list[before].next);
                before = now;
            }
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i=1; i<=N; i++) {
            if(inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            result.add(cur);
            for(Node temp = list[cur].next; temp != null; temp = temp.next) {
                inDegree[temp.to]--;
                if(inDegree[temp.to] == 0) {
                    queue.offer(temp.to);
                }
            }
        }
        if(result.size() != N) {
            System.out.println(0);
            return;
        }
        for(int i : result) {
            System.out.println(i + " ");
        }
    }
}
