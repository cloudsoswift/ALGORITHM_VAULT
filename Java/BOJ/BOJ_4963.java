package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

// 4963. 섬의 개수
public class BOJ_4963 {
    static class Pair{
        int x;
        int y;
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    static int arr[][], v[][], w, h;
    public static void main(String[] args) throws IOException {
        // 상, 우상, 우, 우하, 하, 좌하, 좌, 좌상
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String str[] = br.readLine().split(" ");
        StringBuilder sb = new StringBuilder();
        while(!str[0].equals("0") && !str[1].equals("0")) {
            w = Integer.parseInt(str[0]);
            h = Integer.parseInt(str[1]);
            arr = new int[h][w];
            v = new int [h][w];
            int cnt = 0; // 섬 갯수
            for(int i=0; i<h; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<w; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for(int i=0; i<h; i++) {
                for(int j=0; j<w; j++) {
                    if(arr[i][j]==1 && v[i][j] ==0) {
                        search(j, i);
                        cnt++;
                    }
                }
            }
            sb.append(cnt);
            sb.append("\n");
            str = br.readLine().split(" ");
        }
        System.out.printf(sb.toString());
    }
    public static void search(int x, int y) {
        int[] dx = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = new int[] {-1, -1, 0, 1, 1, 1, 0, -1};
        Stack<Pair> queue = new Stack<Pair>();
        queue.push(new Pair(x, y));
        while(!queue.isEmpty()) {
            Pair p = queue.pop();
            // 해당 섬으로부터 8방위 탐색
            for(int i=0; i<8; i++) {
                int mx = p.x + dx[i];
                int my = p.y + dy[i];
                // 지도 바깥인 경우
                if(mx < 0 || my < 0 || mx >= w || my >= h) {
                    continue;
                }
                if(arr[my][mx] == 1 && v[my][mx] == 0) {
                    v[my][mx] = 1;
                    queue.push(new Pair(mx, my));
                }
            }
        }
    }
}
