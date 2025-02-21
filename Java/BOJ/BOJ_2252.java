package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 2252. 줄 세우기

// 답이 여러 가지인 경우에는 아무거나 출력한다.
public class BOJ_2252 {
    //	static class Node{
//		int to;
//		Node next;
//		Node(int to, Node next){
//			this.to = to;
//			this.next = next;
//		}
//	}
    static ArrayList<Integer> result = new ArrayList<Integer>();
    static int N, M; // 학생 수, 관계 수
    // Node 만들어서 써서 짜보기
    static ArrayList<Integer> list[] = null;
    //위상정렬
    //inDegree 배열, 큐 자료구조
    static int inDegree[] = null;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        list = new ArrayList[N+1];
        // step 1. 진입차수 관리배열 생성
        inDegree = new int[N+1];

        for(int i=0; i<=N; i++) {
            list[i] = new ArrayList<>();
        }

        // step 2. 입력 받으면서 진입차수 관리 배열에 1씩 증가
        for(int i=0; i<M; i++) {
            str = br.readLine().split(" ");
            int x = Integer.parseInt(str[0]);
            int y = Integer.parseInt(str[1]);
            list[x].add(y);
            inDegree[y]++;
        }

        // step 3. 진입차수가 0인 객체를 Queue에 삽입
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i=1; i<=N; i++) {
            if(inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 가지치기 ) Queue 사이즈가 0 이면 종료
        if(queue.size() == 0) {
            return;
        }

        // step 4. Queue가 빌 때까지 반복
        while(!queue.isEmpty()) {
            // step 4-1. Queue에서 하나 빼서 작업 진행
            int cur = queue.poll();
            result.add(cur);
            // step 4-2. 현재 지점과 연결 되어있는 정점의 진입차수 배열에서 1씩 감소시킴.
            for(Integer num : list[cur]) {
                inDegree[num]--;
                // step 4-3. 1 감소시켰던 정점의 진입차수가 0이 되면, queue에 넣음.
                // 선행작업 다 끝낸 작업 Queue에 넣는 것
                if(inDegree[num] == 0) {
                    queue.offer(num);
                }
            }
        }
        //result.size != N 이면, 사이클 존재
        //큐에 들어갔다 나온 정점들 개수 != 전체 정점 개수
        if(result.size() != N) return;

        for(Integer num : result) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
