package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 5656. [모의 SW 역량테스트] 벽돌 깨기
public class _SWEA_5656 {
    static class Pair{
        int x, y;
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String str[];
        // [ 동, 서, 남, 북 ]
        int dx[] = { 1, -1, 0, 0 };
        int dy[] = { 0, 0, 1, -1 };
        for(int test_case=1; test_case<=T; test_case++) {
            str = br.readLine().split(" ");
            int N = Integer.parseInt(str[0]);
            int W = Integer.parseInt(str[1]);
            int H = Integer.parseInt(str[2]);
            int map[][] = new int[H][W];
            for(int i=0; i<H; i++) {
                str = br.readLine().split(" ");
                for(int j=0; j<W; j++) {
                    map[i][j] = Integer.parseInt(str[j]);
                }
            }
            for(int i=0; i<N; i++) {
                int maxDestroy = 0;
                for(int j=0;j<W; j++) {
                    boolean v[][] = new boolean[H][W];
                    int high = 0;
                    while(high < H && map[high][j]==0) {
                        high++;
                    }
                    if(high==H-1)
                        continue;
                    if(map[high][j] == 1) {
                        maxDestroy = Math.max(maxDestroy, map[high][j]);
                    }
                    else{
                        Queue<Pair> queue = new LinkedList<Pair>();
                        queue.offer(new Pair(j, high));
                        v[high][j] = true;
                        while(!queue.isEmpty()) {
                            Pair p = queue.poll();
                            int num = map[high][j] - 1;
                            for(int k=0; k<4; k++) {
                                int copyNum = num;
                                while(copyNum-->0) {
                                    int mx = p.x + dx[k];
                                    int my = p.y + dy[k];
                                    if(mx >= W || my >= H || mx < 0 || my < 0) continue;
                                    if(v[my][mx]) continue;
                                    queue.offer(new Pair(mx, my));
                                    v[my][mx] = true;
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
