package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1263. [S/W 문제해결 응용] 8일차 - 사람 네트워크2
public class SWEA_1263 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int map[][];
        String str[];
        for(int test_case = 1; test_case <= T; test_case++) {
            int res = Integer.MAX_VALUE;
            str = br.readLine().split(" ");
            int N = Integer.parseInt(str[0]);
            map = new int[N][N];
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    // 사람 네트워크의 인접 행렬이 행 우선 (row-by-row) 순으로 주어진다.
                    // < 입력이 한 행 한 행 주어진다는 뜻
                    map[i][j] = Integer.parseInt(str[1+i*N+j]);
                    // 자기자신으로는 비용 0이므로 안바꿔줌. 나머지 0은 그 경로로 가는길 없다는 뜻이므로
                    // 적당히 큰 값 넣기
                    if(i!=j && map[i][j] == 0) map[i][j] = 10000;
                }
            }
            for(int k=0; k<N; k++) {
                for(int i=0; i<N; i++) {
                    for(int j=0; j<N; j++) {
                        // k 정점을 경유한 경우와, 직접 가는 경우 둘 중 더 경로 짧은것으로 갱신
                        map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
                    }
                }
            }
            for(int i=0; i<N; i++) {
                int sum = 0;
                for(int j=0; j<N; j++) {
                    sum += map[i][j];
                }
                if(sum < res)
                    res = sum;
            }
            System.out.printf("#%d %d\n", test_case, res);
        }
    }
}
