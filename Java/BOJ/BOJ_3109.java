package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

// 3109. 빵집
// 메모리랑 작동시간 왤케 길게 나오지?
public class BOJ_3109 {
    static int dx[], dy[], R, C, count;
    static char[][] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        R = Integer.parseInt(str[0]);
        C = Integer.parseInt(str[1]);
        arr = new char[R][C];
        dx = new int[] {1, 1, 1};
        dy = new int[] {1, 0, -1};
        count = 0;
        for(int i=0; i<R; i++) {
            str = br.readLine().split("");
            for(int j=0; j<C; j++) {
                arr[i][j] = str[j].charAt(0);
            }
        }
        Stack<int[]> stack = new Stack<>();
        // 각 열 첫 행부터 탐색 시작
        for(int i=0; i<R; i++) {
            stack.clear();
            stack.push(new int[] {0, i});
            while(!stack.isEmpty()) {
                int[] now = stack.pop();
                int x = now[0];
                int y = now[1];
                arr[y][x] = '=';
                // 현재 위치가 열의 맨 끝이라면 파이프라인 개수 추가하고 탈출
                if(x==C-1) {
                    count++;
                    break;
                }
                for(int j=0; j<3; j++) {
                    int mx = x + dx[j];
                    int my = y + dy[j];
                    if(mx >= C || my < 0 || my >= R) continue;
                    if(arr[my][mx] != '.') continue;
                    stack.push(new int[] {mx, my});
                }
            }
        }

        System.out.println(count);
    }
//	static void dfs(int x, int y) {
//		arr[y][x] = '=';
//		if(x==C-1) {
//			count++;
//			return;
//		}
//		for(int i=0; i<3; i++) {
//			int mx = x + dx[i];
//			int my = y + dy[i];
//			if(mx >= C || my < 0 || my >= R) continue;
//			if(arr[my][mx] != '.') continue;
//			dfs(mx, my);
//		}
//	}
}
