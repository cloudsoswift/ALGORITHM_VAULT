package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 2117. [모의 SW 역량테스트] 홈 방범 서비스
// 교수님 풀이 카피
public class SWEA_2117 {
    static int MAX_COUNT, arr[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_2117.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++) {
            String str[] = br.readLine().split(" ");
            int N = Integer.parseInt(str[0]);
            int M = Integer.parseInt(str[1]);
            MAX_COUNT = 1;
            arr = new int[N][N];
            for(int i=0; i<N; i++) {
                str = br.readLine().split(" ");
                for(int j=0; j<N; j++) {
                    arr[i][j] = Integer.parseInt(str[j]);
                }
            }
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    bfs(j, i, N, M);
                }
            }
            System.out.printf("#%d %d\n", test_case, MAX_COUNT);
        }
    }
    static void bfs(int x, int y, int N, int M) {
        int k = 1, cnt = 0;
        // [ 상, 하, 좌, 우 ]
        int dx[] = new int[] {0, 0, -1, 1};
        int dy[] = new int[] {-1, 1, 0, 0};
        int v[][] = new int[N][N];
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{x, y});
        v[y][x] = 1;
        cnt = 0;
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            while(queueSize-->0) {
                int now[] = queue.poll();
                if(arr[now[1]][now[0]] == 1) cnt++;
                for(int i=0; i<4; i++) {
                    int mx = now[0] + dx[i];
                    int my = now[1] + dy[i];
                    if(mx<0 || my<0 || mx >= N || my >= N) continue;
                    if(v[my][mx] == 1) continue;
                    v[my][mx] = 1;
                    queue.offer(new int[] {mx, my});
                }
            }
            int cost = k*k+(k-1)*(k-1);
            if(cnt*M >= cost && cnt > MAX_COUNT)
                MAX_COUNT = cnt;
            k++;
        }
    }
}
