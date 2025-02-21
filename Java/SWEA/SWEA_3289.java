package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 3289. 서로소 집합
public class SWEA_3289 {
    static int parents[];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()), N, M, op, A, B;
        String str[];
        StringBuilder sb;
        for(int test_case=1; test_case<=T; test_case++) {
            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");
            str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            M = Integer.parseInt(str[1]);
            parents = new int[N+1];
            for(int i=1; i<N; i++) {
                parents[i] = i;
            }
            for(int i=0; i<M; i++) {
                str = br.readLine().split(" ");
                op = Integer.parseInt(str[0]);
                A = Integer.parseInt(str[1]);
                B = Integer.parseInt(str[2]);
                if(op == 0) {
                    // A가 속한 집합과 B가 속한 집합을 합침
                    Union(A, B);
                } else if(op == 1) {
                    // A, B 두 원소가 같은 집합에 있는지 확인
                    if(find(A) == find(B))
                        sb.append(1);
                    else
                        sb.append(0);
                }
            }
            sb.append("\n");
            System.out.print(sb.toString());
        }
    }
    static boolean Union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if(aRoot == bRoot) return false;
        parents[bRoot] = aRoot;
        return true;
    }
    static int find(int a) {
        if(parents[a] == a) return a;
        return parents[a] = find(parents[a]);
    }
}
