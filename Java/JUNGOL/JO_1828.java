package JUNGOL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

// 1828. 냉장고
public class JO_1828 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int temper[][] = new int[N][2];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            temper[i][0] = Integer.parseInt(st.nextToken());
            temper[i][1] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(temper, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });
//		for(int i=0; i<N; i++) {
//			System.out.println(temper[i][0] + ", " + temper[i][1]);
//		}
        int cnt = 0;
        for(int i=0; i<N; i++) {
            int max = temper[i][1];
            i++;
            while(i<N && temper[i][0] <= max) {
                if(temper[i][1] <= max)
                    max = temper[i][1];
                i++;
            }
            i--;
            cnt++;
        }
        System.out.println(cnt);
    }
}
