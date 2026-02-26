package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1652. 누울 자리를 찾아라
public class BOJ_1652 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int verticalCount = 0;
        int horizontalCount = 0;
        int[] verticalStraightCount = new int[N];
        for(int i=0; i<N; i++) {
            String s = br.readLine();
            int horizontalStraightCount = 0;
            for(int j=0; j<N; j++) {
                if(s.charAt(j) == '.') {
                    horizontalStraightCount++;
                    verticalStraightCount[j]++;
                } else {
                    if(horizontalStraightCount >= 2) {
                        horizontalCount++;
                    }
                    horizontalStraightCount = 0;
                    if(verticalStraightCount[j] >= 2) {
                        verticalCount++;
                    }
                    verticalStraightCount[j] = 0;
                }
            }
            if(horizontalStraightCount >= 2) {
                horizontalCount++;
            }
        }
        for(int i=0; i<N; i++) {
            if(verticalStraightCount[i] >= 2) {
                verticalCount++;
            }
        }
        System.out.printf("%d %d\n", horizontalCount, verticalCount);
    }
}
