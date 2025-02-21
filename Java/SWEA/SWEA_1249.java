package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 1249. 보급로
class Solution {
    public static void main(String args[]) throws Exception {
        //System.setIn(new FileInputStream("input_1249.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        // [ 동, 서, 남, 북 ]
        int dx[] = {1, -1, 0, 0};
        int dy[] = {0, 0, 1, -1};
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());
            int map[][] = new int[N][N];
            int anotherMap[][] = new int[N][N];
            String str;
            for (int i = 0; i < N; i++) {
                str = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = Character.digit(str.charAt(j), 10);
                    anotherMap[i][j] = -1;
                }
            }
            Queue<int[]> queue = new LinkedList<int[]>(); // [ x, y ]
            queue.offer(new int[]{0, 0});
            anotherMap[0][0] = map[0][0];
            while (!queue.isEmpty()) {
                int[] now = queue.poll();
                for (int i = 0; i < 4; i++) {
                    int mx = now[0] + dx[i];
                    int my = now[1] + dy[i];
                    if (mx >= N || my >= N || mx < 0 || my < 0) continue;
                    int sum = anotherMap[now[1]][now[0]] + map[my][mx];
                    if (anotherMap[my][mx] == -1) {
                        // 아직 도달한 적 없는 곳은 지금의 sum값으로 할당
                        anotherMap[my][mx] = sum;
                        queue.offer(new int[]{mx, my});
                    } else if (sum < anotherMap[my][mx]) {
                        // 도달한 적 있는 경우 ( anotherMap[i][j]의 값이 -1이 아닌 경우 )
                        // 현재 경로의 복구시간 총합이 기존의 해당 위치까지 도달하는 복구시간의 최소값보다 작은경우
                        // 갱신함.
                        anotherMap[my][mx] = sum;
                        queue.offer(new int[]{mx, my});
                    }
                }
            }
            System.out.printf("#%d %d\n", test_case, anotherMap[N - 1][N - 1]);
        }
    }
}