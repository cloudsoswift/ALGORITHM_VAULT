package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 1225. [S/W 문제해결 기본] 7일차 - 암호생성기
public class SWEA_1225 {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input_1225.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int arr[] = new int[8];
        int T = 10, pos, cnt;
        for(int tc = 1; tc <= T; tc ++) {
            br.readLine();
            pos = 0;
            cnt = 1;
            String[] str = br.readLine().split(" ");
            for(int i=0; i<str.length; i++) {
                arr[i] = Integer.parseInt(str[i]);
            }
            while((arr[pos] = arr[pos]-cnt) > 0) {
                pos++;
                if(pos > 7) {
                    pos = 0;
                }
                cnt++;
                if(cnt > 5) {
                    cnt = 1;
                }
            }
            arr[pos] = 0;
            System.out.printf("#%d ", tc);
            for(int i = 1; i < 9; i++) {
                System.out.printf("%d ", arr[(i+pos)%8]);
            }
            System.out.println();
        }
    }
}
