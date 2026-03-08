package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

// 1525. 퍼즐
public class BOJ_1525 {
    // [상, 하, 좌, 우]
    static int dr[] = new int[] {-1, 1, 0, 0};
    static int dc[] = new int[] {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Long, Boolean> dict = new HashMap<>();
        String[] strArr;
        int n = 3;
        // 초기 상태의 퍼즐을 나타내는 arr
        long arr = 0;
        // 목표 상태의 퍼즐을 나타내는 goal
        long goal = 0;
        // long 변수의 36비트(9 * 4)를 9칸짜리 배열로 사용
        // 또한, 3*3인 퍼즐을 1차원 배열에 나타내기 위해 [[0,0],[0,1],[0,2],[1,0],...] 과 같은 형태로
        // 늘어뜰여 전개
        for(int i=0; i<n; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<n; j++) {
                int p = Integer.parseInt(strArr[j]);
                arr |= ((long)p << ((i*3+j) * 4));
            }
        }
        // 목표 상태도 초기화
        for(int i=0; i<8; i++) {
            goal |= ((long) (i+1) << (i * 4));
        }
        // 시작상태를 넣고 시작
        dict.put(arr, true);
        ArrayDeque<Long> queue = new ArrayDeque<>();
        queue.offer(arr);
        long time = 0;
        while(!queue.isEmpty()) {
            // BFS 형식으로 진행하기 위해, 각 step 별로 queue에 담겨있던 양 만큼만 진행
            int queueSize = queue.size();
            while(queueSize-- > 0) {
                long now = queue.poll();
                if(now == goal) {
                    System.out.println(time);
                    return;
                }
                for(int i=0; i<9; i++) {
                    // 빈칸(0이 있는 칸) 찾기
                    if(((now >> (i * 4)) & 0xFL) == 0) {
                        // 상,하,좌,우 방향 각각 탐색하면서 이동 가능한지 확인
                        int r = i / 3;
                        int c = i % 3;
                        for(int j=0; j<4; j++) {
                            int mr = r + dr[j];
                            int mc = c + dc[j];
                            if(mr < 0 || mc < 0 || mr >= n || mc >= n) continue;
                            // 이동 가능할 경우 해당 위치 값과 0을 스왑
                            long swapped = longSwap(now, i, mr * 3 + mc);
                            if(!dict.containsKey(swapped)) {
                                dict.put(swapped, true);
                                queue.offer(swapped);
                            }
                        }
                        break;
                    }
                }
            }
            time++;
        }
        System.out.println(-1);
    }
    public static Long longSwap(long value, int left, int right) {
        int l = (int) ((value >> (left * 4)) & 0xFL);
        int r = (int) ((value >> (right * 4)) & 0xFL);

        value &= ~(0xFL << (left * 4));
        value &= ~(0xFL << (right * 4));

        value |= ((long) l << (right * 4));
        value |= ((long) r << (left * 4));

        return value;
    }
}
