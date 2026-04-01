package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

// 16954. 움직이는 미로 탈출
public class BOJ_16954 {
    static int N = 8;
    // [동, 남동, 남, 남서, 서, 북서, 북, 북동, 정가운데]
    static int[] dr = new int[] {0, 1, 1, 1, 0, -1, -1, -1, 0};
    static int[] dc = new int[] {1, 1, 0, -1, -1, -1, 0, 1, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[][] map = new int[8][8];
        // 체스판에 존재하는 벽 개수 wallCount
        int wallCount = 0;
        for(int i=0; i<N; i++) {
            String str = br.readLine();
            for(int j=0; j<N; j++) {
                if(str.charAt(j) == '#') {
                    map[i][j] = 1;
                    wallCount++;
                }
            }
        }
        if(wallCount == 0) {
            // 벽이 하나도 없는 경우, 무조건 가장 오른쪽 윗칸에 도달할 수 있으므로 1 출력
            System.out.println(1);
        } else {
            // BFS 탐색
            Deque<int[]> queue = new ArrayDeque<>();
            // 가장 왼쪽 아랫 칸에서 출발
            queue.offer(new int[] {N-1, 0});
            int time = 0;
            // 8초만 있으면 체스판의 모든 벽이 가장 아래행으로 흘러내려가 없어지므로
            // 8초까지 벽에 끼이지 않고 살아있을 수만 있다면 목적지에 도달할 수 있음
            // 따라서 time < 8 인 동안 탐색 짆애
            while(!queue.isEmpty() && time < 8) {
                // BFS를 위해 매 탐색마다 Queue에 들어있던 것들만 탐색
                int queueSize = queue.size();
                while(queueSize-- > 0) {
                    int[] now = queue.poll();
                    // 벽이 현재 캐릭터가 있는 칸으로 내려온 경우,
                    // 즉, 현재 행 번호(now[0])에서 time 만큼 위에 있는 칸에 벽이 있을 경우
                    // 해당 케이스는 이동 불가하므로 스킵
                    if(now[0] - time >= 0 && map[now[0] - time][now[1]] == 1) continue;
                    for(int i=0; i<9; i++) {
                        int mr = now[0] + dr[i];
                        int mc = now[1] + dc[i];
                        if(mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
                        // 해당 위치에 벽이 있는 경우 스킵
                        if(mr - time >= 0 && map[mr - time][mc] == 1) continue;
                        queue.offer(new int[] {mr, mc});
                    }
                }
                time++;
            }
            // 8초까지 진행했고, 그때까지 캐릭터가 살아있는 경우의 수가 있다면
            // 골인지점에 도달할 수 있는 것이므로 1 출력
            if(time == 8 && !queue.isEmpty()) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
    }
}
