package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1937. 욕심쟁이 판다
public class BOJ_1937 {
    // [동, 서, 남, 북]
    static int[] dr = new int[] {0, 0, 1, -1};
    static int[] dc = new int[] {1, -1, 0, 0};
    static int N;
    static int MAXIMUM;
    // 각 위치의 대나무 길이 bamboos, 각 위치에서 출발했을 때 이동 가능한 최대 길이 v
    static int[][] bamboos, v;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String[] strArr;
        bamboos = new int[N][N];
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                bamboos[i][j] = Integer.parseInt(strArr[j]);
            }
        }
        v = new int[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                MAXIMUM = Math.max(MAXIMUM, search(i, j));
            }
        }
        System.out.println(MAXIMUM);
    }
    // 현재 위치에서 4방향으로 DFS 탐색하며, "해당 위치에서 시작했을 때 갈 수 있는 최장 거리" + 1을 현재 값과 비교 및 갱신하는 형태의 탐색
    public static int search(int r, int c) {
        // 이미 현재 위치에서 시작했을 때 갈 수 있는 최장거리 값이 기록된 경우, 해당 값을 반환
        if(v[r][c] != 0) return v[r][c];
        // 현재 위치에서 시작하면 1로 초기화
        v[r][c] = 1;
        for(int i=0; i<4; i++) {
            int mr = r + dr[i];
            int mc = c + dc[i];
            if (mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
            if (bamboos[mr][mc] <= bamboos[r][c]) continue;
            v[r][c] = Math.max(v[r][c], search(mr, mc) + 1);
        }
        return v[r][c];
    }
}
