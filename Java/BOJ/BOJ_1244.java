package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 1244. 스위치 켜고 끄기
public class BOJ_1244 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int arr[] = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int T = Integer.parseInt(br.readLine());
        int category, num;
        for(int i=0; i<T; i++) {
            String[] line = br.readLine().split(" ");
            category = Integer.parseInt(line[0]);
            num = Integer.parseInt(line[1]);
            if(category == 1) {
                for(int j = num; j <= N; j += num) {
                    if(arr[j-1] == 0) {
                        arr[j-1] = 1;
                    } else
                        arr[j-1] = 0;
                }
            } else if(category == 2) {
                num -= 1;
                if(arr[num] == 0)
                    arr[num] = 1;
                else
                    arr[num] = 0;
                int up, down;
                for(int j=1; j<=N/2; j++) {
                    up = num+j;
                    down = num-j;
                    if(down >= 0 && up < N) {
                        if (arr[down] == arr[up]) {
                            if(arr[down] == 0) {
                                arr[down] = 1;
                                arr[up] = 1;
                            } else {
                                arr[down] = 0;
                                arr[up] = 0;
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        for(int i=0; i<N; i++) {
            System.out.printf("%d ", arr[i]);
            if((i+1)%20==0) {
                System.out.printf("\n");
            }
        }
    }
}
