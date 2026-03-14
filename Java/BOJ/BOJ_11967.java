package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 11967. 불켜기
public class BOJ_11967 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        // [동, 서, 남, 북]
        int[] dr = new int[] {0, 0, 1, -1};
        int[] dc = new int[] {1, -1, 0, 0};
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        // map[i][j][0]: 해당 위치에 불이 들어와있는지
        // map[i][j][1]: 해당 위치가 1,1에서 출발해 도달할 수 있는 곳인지
        boolean[][][] map = new boolean[N][N][2];
        boolean[][] visited = new boolean[N][N];
        // switches[i][j]: (i, j)에 있는 스위치들에 연결된 지점의 좌표들을 보관하는 리스트
        List<int[]>[][] switches = new List[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                switches[i][j] = new ArrayList<>();
            }
        }
        for(int i=0; i<M; i++) {
            strArr = br.readLine().split(" ");
            int x = Integer.parseInt(strArr[0]);
            int y = Integer.parseInt(strArr[1]);
            int a = Integer.parseInt(strArr[2]);
            int b = Integer.parseInt(strArr[3]);
            switches[x-1][y-1].add(new int[] {a-1, b-1});
        }
        // 켜진 전구 개수
        int count = 1;
        // BFS를 위한 Queue
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {0, 0});
        map[0][0][0] = true;
        visited[0][0] = true;
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            while(queueSize-- > 0) {
                int[] now = queue.poll();
                // 1. 먼저, 현재 위치에 있는 스위치들을 모두 킨다
                for(int[] next : switches[now[0]][now[1]]) {
                    // 만약 켜진적 없는 곳의 불을 킨다면,
                    if(!map[next[0]][next[1]][0]) {
                        // 켠 전구 개수 증가
                        count++;
                    }
                    // 해당위치 전구가 켜져있음을 기록
                    map[next[0]][next[1]][0] = true;
                    // 해당 위치에 도달할 수 있고, 방문한 적 없는 곳이라면
                    if(map[next[0]][next[1]][1] && !visited[next[0]][next[1]]) {
                        // 방문할 곳으로 Queue에 추가
                        queue.offer(new int[] {next[0], next[1]});
                        // 및 방문 기록
                        visited[next[0]][next[1]] = true;
                    }
                }
                // 2. 현재 위치에서 4방향으로 인접한 곳 중 불 켜져있으면서, 방문한 적 없는 곳 찾음
                for(int i=0; i<4; i++) {
                    int mr = now[0] + dr[i];
                    int mc = now[1] + dc[i];
                    if(mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
                    // 도달할 수 있는 위치임을 기록
                    map[mr][mc][1] = true;
                    // 이미 방문했거나, 불이 켜져 있지 않으면 스킵
                    if(visited[mr][mc] || !map[mr][mc][0]) continue;
                    visited[mr][mc] = true;
                    queue.offer(new int[] {mr, mc});
                }
            }
        }
        System.out.println(count);
    }
}
