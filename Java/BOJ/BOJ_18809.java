package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 18809. Gaaaaaaaaaarden
public class BOJ_18809 {
    static int MAXIMUM_FLOWERS, N, M, possiblePosCount;
    static final int RED_FLUID = 4, GREEN_FLUID = 5, FLOWER = 6;
    static int[][] map, flowerMap, cfPosition;
    // [동, 서, 남, 북]
    static int[] dr = new int[]{0, 0, 1, -1};
    static int[] dc = new int[]{1, -1, 0 ,0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        N = Integer.parseInt(strArr[0]);
        M = Integer.parseInt(strArr[1]);
        int G = Integer.parseInt(strArr[2]);
        int R = Integer.parseInt(strArr[3]);
        map = new int[N][M];
        // 배양액 및 꽃에 대한 정보를 갖는 배열.
        // flowermap[i][j] => 4: 빨간색 배양액 / 5: 초록색 배양액 / 6: 꽃
        flowerMap = new int[N][M];
        // 배양액을 뿌릴 수 있는 땅들의 [r, c]를 기록하는 배열.
        cfPosition = new int[10][2];
        int cfCount = 0;
        // 배양액이 이동가능한 지점(1 or 2)의 개수를 카운트하는 변수
        possiblePosCount = 0;
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(strArr[j]);
                if(map[i][j] == 2) {
                    cfPosition[cfCount] = new int[]{i, j};
                    cfCount++;
                    possiblePosCount++;
                } else if(map[i][j] == 1) {
                    possiblePosCount++;
                }
            }
        }
        simulate(0, cfCount, G, R);
        System.out.println(MAXIMUM_FLOWERS);
    }
    public static void simulate(int pos, int cfCount, int g, int r) {
        if(pos == cfCount) {
            if(g > 0 || r > 0)
                return;
            int pp = possiblePosCount;
            // 배양액 시뮬레이팅을 위해 flowerMap을 복사해올 배열
            int[][] copiedMap = new int[N][];
            // 각 배양액이 해당 지점에 몇초에 도착했는지 기록하는 배열
            int[][] timeMap = new int[N][M];
            for(int i=0; i<N; i++) {
                copiedMap[i] = flowerMap[i].clone();
            }
            Queue<int[]> queue = new LinkedList<>();
            for(int i=0; i<cfCount; i++) {
                // 가능한 위치에 배양액 안뿌린 경우는 생략
                if(copiedMap[cfPosition[i][0]][cfPosition[i][1]] == 0)
                    continue;
                queue.offer(cfPosition[i]);
                pp--;
            }
            int time = 1;
            int flowerCount = 0;
            while(!queue.isEmpty()) {
                // N초 별 이동을 위해 현재의 queueSize를 추출한 뒤 그 만큼만 뽑아냄
                int queueSize = queue.size();
                while(queueSize-- > 0) {
                    int[] now = queue.poll();
                    int color = copiedMap[now[0]][now[1]];
                    if(color == FLOWER) continue;
                    for(int i=0; i<4; i++) {
                        int mr = now[0] + dr[i];
                        int mc = now[1] + dc[i];
                        if(mr < 0 || mc < 0 || mr >= N || mc >= M) continue;
                        if(map[mr][mc] == 0) continue;
                        if(copiedMap[mr][mc] > 0) {
                            // 같은 색의 배양액이 있거나, 꽃이 있는 곳은 스킵
                            if(copiedMap[mr][mc] == color || copiedMap[mr][mc] == FLOWER) continue;
                            if(timeMap[mr][mc] == time) {
                                flowerCount++;
                                copiedMap[mr][mc] = FLOWER;
                            }
                        } else {
                            copiedMap[mr][mc] = color;
                            timeMap[mr][mc] = time;
                            queue.offer(new int[]{mr, mc});
                            pp--;
                        }
                    }
                }
                // 이전에 기록된 가능한 최대 꽃 개수 > 현재 꽃 개수 + 남은 배양액이 이동가능한 칸 개수 인 경우
                // 더 시뮬레이팅을 진행해도 최대 꽃 개수를 갱신할 수 없으므로 함수 탈출
                if (MAXIMUM_FLOWERS > flowerCount + pp) return;
                time++;
            }
            MAXIMUM_FLOWERS = Math.max(MAXIMUM_FLOWERS, flowerCount);
            return;
        }
        if(g > 0) {
            // 초록색 배양액이 1개 이상 남아있는 경우 뿌려봄
            flowerMap[cfPosition[pos][0]][cfPosition[pos][1]] = GREEN_FLUID;
            simulate(pos + 1, cfCount, g-1, r);
            flowerMap[cfPosition[pos][0]][cfPosition[pos][1]] = 0;
        }
        if(r > 0) {
            // 빨간색 배양액이 1개 이상 남아있는 경우 뿌려봄
            flowerMap[cfPosition[pos][0]][cfPosition[pos][1]] = RED_FLUID;
            simulate(pos + 1, cfCount, g, r-1);
            flowerMap[cfPosition[pos][0]][cfPosition[pos][1]] = 0;
        }
        if(cfCount - pos > g + r) {
            // 현재 위치에 아무 배양액을 뿌리지 않을 수 있는 경우,
            // 즉, 배양액 뿌릴 수 있는 땅 갯수 - pos 가 g + r 보다 큰 경우
            // 현재 위치에 배양액을 뿌리지 않는 경우도 시도
            simulate(pos + 1, cfCount, g, r);
        }
    }
}
