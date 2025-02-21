package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1717. 집합의 표현
public class BOJ_1717 {
    static int parent[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        int n = Integer.parseInt(strArr[0]);
        int m = Integer.parseInt(strArr[1]);
        parent = new int[n+1];
        int command, a, b;
        for(int i=1; i<=n; i++) {
            parent[i] = i;
        }
        for(int i=0; i<m; i++) {
            strArr = br.readLine().split(" ");
            command = Integer.parseInt(strArr[0]);
            a = Integer.parseInt(strArr[1]);
            b = Integer.parseInt(strArr[2]);
            if(command == 0) {
                // 합집합
                union(a, b);
            } else if(command == 1) {
                // 두 원소가 같은 집합인지 확인
                if(find(a) == find(b)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }
    public static int find(int origin) {
        if(parent[origin] == origin) {
            return origin;
        }
        return parent[origin] = find(parent[origin]);
    }
    public static void union(int left, int right) {
        int lparent = find(left);
        int rparent = find(right);
        if(lparent != rparent) {
            parent[rparent] = lparent;
        }
    }
}
