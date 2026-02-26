package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1100. 하얀 칸
public class BOJ_1100 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        for(int i=0; i<8; i++) {
            String board = br.readLine();
            for(int j=0; j<8; j++) {
                char c = board.charAt(j);
                if(c == 'F') {
                    // 하얀 칸은 0, 2, .. 번째 줄에서는 0, 2, ... 번째로,
                    // 1, 3, .. 번째 줄에서는 1, 3, ... 번째로 등장함
                    if(i % 2 ==  j % 2)
                        count++;
                }
            }
        }
        System.out.println(count);
    }
}
