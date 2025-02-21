package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

// 7465. 창용 마을 무리의 개수
public class SWEA_7465 {
    static int knows[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        int N, M;
        int A, B, count;
        String str[];
        for(int test_case = 1; test_case <= T; test_case++) {
            str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            M = Integer.parseInt(str[1]);
            knows = new int[N+1];
            count = 0;
//			makeSet()
            for(int i=1; i<=N; i++) {
                knows[i] = i;
            }
            for(int i=0; i<M; i++) {
                str = br.readLine().split(" ");
                A = Integer.parseInt(str[0]);
                B = Integer.parseInt(str[1]);
                Union(A, B);
            }
            // 자신의 부모 != 자신 인 경우 << 다른 무리에 속해 있으므로 -> count++ X
            // 자신의 부모 == 자신 인 경우 << 한 무리가 존재하는 것 -> count++
            for(int i=1; i<=N; i++) {
                if(knows[i] == i) count++;
            }
            System.out.printf("#%d %d\n", test_case, count);
        }
    }
    static boolean Union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if(aRoot == bRoot) return false;
        knows[bRoot] = aRoot;
        return true;
    }
    static int find(int a) {
        if(knows[a] == a) return a;
        return knows[a] = find(knows[a]);
    }
}
