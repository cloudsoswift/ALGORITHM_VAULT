package SWEA;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

// 2819. 격자판의 숫자 이어 붙이기
public class SWEA_2819 {
    static StringBuilder sb;
    static Set<String> strSet;
    static int dx[], dy[], arr[][];
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        arr = new int[4][4];
        // [ 동, 서, 남, 북 ]
        dx = new int[] {1, -1, 0, 0};
        dy = new int[] {0, 0, 1, -1};
        for(int test_case=1; test_case<=T; test_case++) {
            strSet = new HashSet<>();
            sb = new StringBuilder();
            for(int i=0; i<4; i++) {
                String[] str = br.readLine().split(" ");
                for(int j=0; j<4; j++) {
                    arr[i][j] = Integer.parseInt(str[j]);
                }
            }
            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                    perm(0, i, j);
                }
            }
            System.out.printf("#%d %d\n", test_case, strSet.size());
        }
    }
    static void perm(int cnt, int x, int y) {
        if(cnt==7) {
            // 길이가 7자리이면서, 기존에 나오지 않은 ( = 서로 다른 ) 7자리수 이면 Set에 추가
            if(!strSet.contains(sb.toString())) {
                strSet.add(sb.toString());
            }
            return;
        }
        for(int i=0; i<4; i++) {
            int mx = x + dx[i];
            int my = y + dy[i];
            if(mx >= 4 || mx < 0 || my >= 4 || my < 0) continue;
            sb.append(arr[my][mx]);
            perm(cnt+1, mx, my);
            sb.delete(cnt, cnt+1);
        }
    }
}
