package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 4014. 활주로 건설
public class SWEA_4014 {
    static int N, X, map[][], map2[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        String strArr[];
        for(int test_case = 1; test_case <= T; test_case++) {
            strArr = br.readLine().split(" ");
            N = Integer.parseInt(strArr[0]); // 배열 크기
            X = Integer.parseInt(strArr[1]); // 경사로 밑변 길이 x
            map = new int[N][N];
            map2 = new int[N][N];

            for(int i=0; i<N; i++) {
                strArr = br.readLine().split(" ");
                for(int j=0; j<N; j++) {
                    map2[j][i] = map[i][j] = Integer.parseInt(strArr[j]);
                }
            }
            System.out.printf("#%d %d\n", test_case, process());
        }
    }
    private static int process() {
        int count = 0;
        for(int i=0; i<N; i++) {
            if(makeRoad(map[i])) count++;
            if(makeRoad(map2[i])) count++;
        }
        return count;
    }
    // 해당 지형 정보로 활주로 건설이 가능하면 true, 불가능하면 false 반환
    // process에서 각 행을 인자로 줄 예정
    private static boolean makeRoad(int[] road) {
        int beforeHeight = road[0], size = 0;
        int j = 0;
        while(j<N) {
            if(beforeHeight == road[j]) {
                size++;
                j++;
            } else if(beforeHeight+1 == road[j]) { // 이전 높이보다 1 높음 : 오르막 경사로 설치 체크
                if(size < X) return false; // X길이 미만이면 활주로 건설 불가
                beforeHeight++;
                size = 1;
                j++;
            } else if(beforeHeight-1 == road[j]) { // 이전 높이보다 1 작음
                int count = 0;
                for(int k=j; k<N; k++) {
                    if(road[k] != beforeHeight-1) return false; // 높이가 2 이상 차이나거나 다시 올라가는 경우
                    if(++count == X) break; // 최소길이 만족했으므로 탈출
                }
                // 여기까지 오면 연속길이 만족해서 내려오거나 끝까지 가서 내려오는 경우
                if(count < X) return false; // 끝까지 갔지만, 연속길이 보장하지 못하고 내려옴
                beforeHeight--; // 내리막 경사로 설치
                j += X; // 경사로 길이만큼 건너 뜀
                size = 0;
            } else { // 높이가 2 이상 차이
                return false;
            }
        }
        return true;
    }
}
