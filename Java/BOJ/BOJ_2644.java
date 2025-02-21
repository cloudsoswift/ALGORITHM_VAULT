package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 2644. 촌수계산
public class BOJ_2644 {
    static int n; // 사람의 입력 수
    //인접리스트 배열
    static ArrayList<Integer>[] list;
    static boolean v[];
    static int s, e; // 시작점, 도착점
    static int m; // 간선의 개수

    static int res; // 결과값 저장

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        list = new ArrayList[n+1];
        v = new boolean[n+1];
        res = -1;
        for(int i=0; i<n+1; i++) {
            list[i] = new ArrayList<>();
        }
        String str[] = br.readLine().split(" ");
        s = Integer.parseInt(str[0]);
        e = Integer.parseInt(str[1]);
        m = Integer.parseInt(br.readLine());
        int x, y;
        for(int i=0; i<m; i++) {
            str = br.readLine().split(" ");
            x = Integer.parseInt(str[0]);
            y = Integer.parseInt(str[1]);
            // 양방향, 단방향 ( 유향 )
            list[x].add(y);
            list[y].add(x);
        }
        bfs();

        System.out.println(res);
    }
    static void bfs() {
//		큐 자료구조 이용
        Queue<Integer> q = new LinkedList<Integer>();
//		첫 번째 삽입, 방문체크
        q.offer(s);
        v[s] = true;
        int cnt = 0;
        int depth = 0;
//		큐가 빌때까지 반복
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {

                //			큐에 있는 것 무조건 하나빼기
                int cur = q.poll();
                //			목적지인지 판단, 목적지면 종료
                if(cur == e) {
                    // 마무리 할 일 남아있음
                    res = depth;
                    return;
                }
                //			그렇지 않다면, 지금 내것과 연결된 모든 애들 큐에 삽입
                for(Integer idx : list[cur]) {
                    //연결 여뷰 확인할 필요 X ( 리스트라서, 인접 배열은 확인 필요 )
                    //이미 방문이 완료된 애들은 제외
                    //				이미 방문이 완료된 애들은 제외하고
                    if(v[idx]) continue;
                    q.offer(idx);
                    v[idx] = true;
                }
            }
            depth++;
        }

    }

    static class Data{
        int idx;
        int cnt;
        public Data(int idx, int cnt) {
            this.idx = idx;
            this.cnt = cnt;
        }
    }
    // Data 클래스 이용
    static void bfs2() {
//		큐 자료구조 이용
        Queue<Data> q = new LinkedList<Data>();
//		첫 번째 삽입, 방문체크
        q.offer(new Data(s, 0));
        v[s] = true;
//		큐가 빌때까지 반복
        int cnt = 0;
        while(!q.isEmpty()) {
//			큐에 있는 것 무조건 하나빼기
            Data cur = q.poll();
            cnt = cur.cnt;
//			목적지인지 판단, 목적지면 종료
            if(cur.idx == e) {
                // 마무리 할 일 남아있음
                res = cur.cnt;
                return;
            }
//			그렇지 않다면, 지금 내것과 연결된 모든 애들 큐에 삽입
            for(Integer idx : list[cur.idx]) {
                //연결 여뷰 확인할 필요 X ( 리스트라서, 인접 배열은 확인 필요 )
                //이미 방문이 완료된 애들은 제외
                if(v[idx]) continue;
                q.offer(new Data(idx, cnt+1));
                v[idx] = true;
            }
//		이미 방문이 완료된 애들은 제외하고
        }

    }
}
