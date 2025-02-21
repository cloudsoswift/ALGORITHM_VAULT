package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 1106. 호텔
public class BOJ_1106 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        int C = Integer.parseInt(strArr[0]);
        int N = Integer.parseInt(strArr[1]);
        int cities[][] = new int[N][2]; // [ [홍보 비용, 고객 수], ... ]
        int minCost[] = new int[C+1]; // n명 늘리는데 필요한 최소값
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[0] = 0;
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            cities[i][0] = Integer.parseInt(strArr[0]); // 홍보 비용
            cities[i][1] = Integer.parseInt(strArr[1]); // 고객 수
        }
        // 홍보를 통해 1명 ~ C명의 고객 유치하는 최소비용 knapsack 계산
        for(int i=1; i<=C; i++) {
            for(int j=0; j<N; j++) {
                // ( i - 현재 도시를 통해 얻는 고객수) 가 음수일 경우 해당 도시만 홍보한 경우만 검사
                if(i < cities[j][1]) {
                    if(C <= cities[j][1])
                        minCost[C] = Math.min(minCost[C], cities[j][0]);
                    minCost[i] = Math.min(minCost[i], cities[j][0]);
                    continue;
                }
                // ( i - 현재 도시를 통해 얻는 고객수 ) 명의 고객을 유치하는 경우가 없을땐 넘김  
                if(minCost[i-cities[j][1]] == Integer.MAX_VALUE) continue;
                minCost[i] = Math.min(minCost[i], cities[j][0] + minCost[i-cities[j][1]]);
                // 현재 도시 한번 더 홍보 했을 때, 유치한 고객이 C명 넘어가면 C명 유치하는 최소 비용 될수 있는지 검사
                if(i + cities[j][1] >= C) {
                    if(minCost[i] == Integer.MAX_VALUE) continue;
                    minCost[C] = Math.min(minCost[C], cities[j][0] + minCost[i]);
                }
            }
        }
        System.out.println(minCost[C]);
    }
}
