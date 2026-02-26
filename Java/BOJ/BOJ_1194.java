package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 1194. 달이 차오른다, 가자.
public class BOJ_1194 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        String str;
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        char[][] map = new char[N][M];
        // v[i][j][k] : 어떠한 키들을 갖고 있을때(i = 비트마스크),[j][k]에 방문한 적 있는지 기록하는 배열
        boolean[][][] v = new boolean[1 << 6][N][M];
        // [동, 서, 남, 북]
        int[] dr = new int[]{0, 0, 1, -1};
        int[] dc = new int[]{1, -1, 0, 0};
        Queue<int[]> queue = new LinkedList<>();
        for(int i=0; i<N; i++) {
            str = br.readLine();
            for(int j=0, len=str.length(); j<len; j++) {
                map[i][j] = str.charAt(j);
                if(map[i][j] == '0')
                    // 시작위치 queue에 넣음
                    queue.offer(new int[]{i, j, 0});
            }
        }
        int time = 1;
        while(!queue.isEmpty()) {
            int queueSize = queue.size();
            while(queueSize-- > 0) {
                int[] now = queue.poll();
                for(int i=0; i<4; i++) {
                    int mr = now[0] + dr[i];
                    int mc = now[1] + dc[i];
                    // 범위 밖이거나,
                    if(mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
                    // 현재 키 상태로 이미 해당 위치 방문한 적 있거나,
                    if(v[now[2]][mr][mc]) continue;
                    // 해당 위치가 벽인경우 스킵
                    if(map[mr][mc] == '#')continue;
                    switch(map[mr][mc]) {
                        // 이동할 위치가 문인 경우,
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            // 해당 문에 대응되는 키를 들고 있는 경우, 즉 now[2]에 기록되어있는 경우
                            if (((1 << (map[mr][mc] - 'A')) & now[2]) != 0) {
                                // 해당 위치 방문
                                queue.offer(new int[]{mr, mc, now[2]});
                                v[now[2]][mr][mc] = true;
                            }
                            break;
                        // 이동할 위치가 열쇠인 경우,
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            // 해당 열쇠를 현재 열쇠 목록에 반영
                            int newKeys = now[2] | (1 << (map[mr][mc] - 'a'));
                            queue.offer(new int[]{mr, mc, newKeys});
                            v[newKeys][mr][mc] = true;
                            break;
                        // 출구에 도착한 경우
                        case '1':
                            System.out.println(time);
                            return;
                        // 이외의 경우 (즉, 빈칸에 도달한 경우)
                        default:
                            queue.offer(new int[]{mr, mc, now[2]});
                            v[now[2]][mr][mc] = true;
                    }
                }
            }
            time++;
        }
        System.out.println(-1);
    }
}
