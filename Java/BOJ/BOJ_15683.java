package BOJ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// 15683. 감시
public class BOJ_15683 {
    static int N, M; //(1 ≤ N, M ≤ 8)
    static int[][] map;
    static int result = Integer.MAX_VALUE;
    static ArrayList<CCTV> list = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N][M];
        for(int i =0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();
                if(1 <= map[i][j] && map[i][j] <= 5) {
                    list.add(new CCTV(j,i, map[i][j]));
                }
            }
        }
        dfs(0, map);
        System.out.println(result);
    }
    static void dfs(int idx, int[][] nMap) {
        //		종료
        if(idx == list.size()) {
            int cnt = 0;
            //			사작지대 갯수 세기

            for(int i = 0 ; i < N; i++) {
                for(int j = 0; j < M ;j++) {
                    if(nMap[i][j] == 0) {
                        cnt++;
                    }
                }
            }
            result = Math.min(result, cnt);
            return;
        }
        //		재귀호출
        //		리스트에서 CCTV 뽑아서 감시 솔루션
        CCTV cctv = list.get(idx);
        int x = cctv.x;
        int y = cctv.y;
        int[][] vMap = new int[N][M];
        switch(cctv.type) {
            case 1 : //  1번 감시 카메라
                for(int d = 0; d < 4 ; d++) {
                    //				감시
                    for(int i = 0; i < N; i++) {
                        vMap[i] = Arrays.copyOf(nMap[i],M);
                    }
                    detect(x, y,vMap, d);
                    //				다음번째  CCTV 호출
                    dfs(idx + 1, vMap);
                    //				백트래킹 (X)
                }
                break;
            case 2 : //  2번 감시 카메라
                for(int d = 0; d < 2 ; d++) {
                    //				감시
                    for(int i = 0; i < N; i++) {
                        vMap[i] = Arrays.copyOf(nMap[i],M);
                    }
                    detect(x, y,vMap, d);
                    detect(x, y,vMap, d+2);
                    //				다음번째  CCTV 호출
                    dfs(idx + 1, vMap);
                    //				백트래킹 (X)
                }
                break;
            case 3 : //  3번 감시 카메라
                for(int d = 0; d < 4 ; d++) {
                    //				감시
                    for(int i = 0; i < N; i++) {
                        vMap[i] = Arrays.copyOf(nMap[i],M);
                    }
                    detect(x, y,vMap, d);
                    detect(x, y,vMap, (d+1) % 4);
                    //				다음번째  CCTV 호출
                    dfs(idx + 1, vMap);
                    //				백트래킹 (X)
                }
                break;
            case 4 : //  4번 감시 카메라
                for(int d = 0; d < 4 ; d++) {
                    //				감시
                    for(int i = 0; i < N; i++) {
                        vMap[i] = Arrays.copyOf(nMap[i],M);
                    }
                    detect(x, y,vMap, d);
                    detect(x, y,vMap, (d+1) % 4);
                    detect(x, y,vMap, (d+2) % 4);
                    //				다음번째  CCTV 호출
                    dfs(idx + 1, vMap);
                    //				백트래킹 (X)
                }
                break;
            case 5 : //  5번 감시 카메라
                //				감시
                for(int i = 0; i < N; i++) {
                    vMap[i] = Arrays.copyOf(nMap[i],M);
                }
                detect(x, y,vMap, 0);
                detect(x, y,vMap, 1);
                detect(x, y,vMap, 2);
                detect(x, y,vMap, 3);
                //				다음번째  CCTV 호출
                dfs(idx + 1, vMap);
                //				백트래킹 (X)
                break;
        }

    }
    static void detect(int x, int y, int[][] cMap, int dir) {
        //	 0 : 왼쪽, 1 : 상, 2 : 오른쪽, 3 : 아래
        switch(dir) {
            case 0 : //왼쪽
                for(int i = x; i >= 0; i--) {
                    if(cMap[y][i] == 6) { // 벽이되면,
                        break;
                    }
                    cMap[y][i] = 9;
                }
                break;
            case 2 : //오른쪽
                for(int i = x; i < M; i++) {
                    if(cMap[y][i] == 6) { // 벽이되면,
                        break;
                    }
                    cMap[y][i] = 9;
                }
                break;
            case 1 : //위쪽
                for(int i = y; i >= 0; i--) {
                    if(cMap[i][x] == 6) { // 벽이되면,
                        break;
                    }
                    cMap[i][x] = 9;
                }
                break;
            case 3 : //아래쪽
                for(int i = y; i < N; i++) {
                    if(cMap[i][x] == 6) { // 벽이되면,
                        break;
                    }
                    cMap[i][x] = 9;
                }
                break;
        }
    }

    static class CCTV{
        int x, y;
        int type;
        public CCTV(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}
