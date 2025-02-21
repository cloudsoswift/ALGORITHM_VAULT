package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 1227. [S/W 문제해결 기본] 7일차 - 미로2
public class SWEA_1227 {
    static class Pair{
        int x;
        int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input_1227.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char arr[][];
        int v[][];
        Queue<Pair> queue = new LinkedList<Pair>();
        // [ 상, 하, 좌, 우 ]
        int dx[] = new int[] { 0, 0, -1, 1 };
        int dy[] = new int[] { -1, 1, 0, 0 };
        int N = 100, M = 100;
        boolean isArrived;
        for(int tc = 1; tc <= 10; tc++) {
            arr = new char[N][M];
            v = new int[N][M];
            isArrived = false;
            br.readLine();
            for(int i=0; i<arr.length; i++) {
                String str = br.readLine();
                for(int j=0; j<arr[i].length; j++) {
                    arr[i][j] = str.charAt(j);
                    if(arr[i][j]=='2') {
                        queue.offer(new Pair(j, i));
                    }
                }
            }
            while(!queue.isEmpty()) {
                int queueSize = queue.size();
                while(queueSize-- > 0) {
                    Pair p = queue.poll();
                    if(arr[p.y][p.x] == '3') {
                        queueSize = 0;
                        queue.clear();
                        isArrived = true;
                        continue;
                    }
                    v[p.y][p.x] = 1;
                    for(int i=0; i<4; i++) {
                        int mx = p.x + dx[i];
                        int my = p.y + dy[i];
                        if(mx < 0 || my < 0 || mx >= M || my >= N) {
                            continue;
                        }
                        if(arr[my][mx] != '1' && v[my][mx] != 1) {
                            queue.offer(new Pair(mx, my));
                        }
                    }
                }
            }
            if(isArrived) {
                System.out.printf("#%d 1\n", tc);
            } else {
                System.out.printf("#%d 0\n", tc);
            }
        }
    }
}
