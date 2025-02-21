package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 17406. 배열 돌리기 4
public class BOJ_17406 {
    static int res[][], arr[][], arrOrig[][], ALL_MIN, inputs[][], v[], numbers[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        int N = Integer.parseInt(str[0]);
        int M = Integer.parseInt(str[1]);
        int K = Integer.parseInt(str[2]);
        inputs = new int[K][3];
        v = new int[K];
        numbers = new int[K];
        arr = new int [N][M];
        arrOrig = new int [N][M];
        res = new int [N][M];
        StringTokenizer st;
        int r, c, s;
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                arrOrig[i][j] = arr[i][j];
                res[i][j] = arr[i][j];
            }
        }
        for(int kcnt = 0; kcnt < K; kcnt++) {
            str = br.readLine().split(" ");
            r = Integer.parseInt(str[0]);
            c = Integer.parseInt(str[1]);
            s = Integer.parseInt(str[2]);
            inputs[kcnt][0] = r;
            inputs[kcnt][1] = c;
            inputs[kcnt][2] = s;
//			turn(c-s-1, r-s-1, c+s-1, r+s-1);
//			for(int j=0; j<N; j++) {
//				System.arraycopy(res[j], 0, arr[j], 0, arr[j].length);
//			}
        }
        ALL_MIN = Integer.MAX_VALUE;
//		int MIN = Integer.MAX_VALUE;
//		for(int i=0; i<N; i++) {
//			int sum = 0;
//			boolean isPossible = false;
//			for(int j=0; j<M; j++) {
//				if(i!=0 && sum > MIN) {
//					isPossible = true;
//					break;
//				}
//				sum += arr[i][j];
//			}
//			if(!isPossible && sum < MIN) {
//				MIN = sum;
//			}
//		}
        perm(0, K);
        System.out.println(ALL_MIN);
    }
    public static void perm(int cnt, int K) {
        if(cnt == K) {
            int c, s, r;
            for(int j=0; j<arrOrig.length; j++) {
                System.arraycopy(arrOrig[j], 0, arr[j], 0, arr[j].length);
                System.arraycopy(arr[j], 0, res[j], 0, res[j].length);
            }
            for(int i=0; i<K; i++) {
                r =  inputs[numbers[i]][0];
                c =  inputs[numbers[i]][1];
                s =  inputs[numbers[i]][2];
                turn(c-s-1, r-s-1, c+s-1, r+s-1);
                for(int j=0; j<res.length; j++) {
                    System.arraycopy(res[j], 0, arr[j], 0, arr[j].length);
                }
            }
            int MIN = Integer.MAX_VALUE;
            for(int i=0; i<arr.length; i++) {
                int sum = 0;
                boolean isPossible = false;
                for(int j=0; j<arr[i].length; j++) {
                    if(i!=0 && sum > MIN) {
                        isPossible = true;
                        break;
                    }
                    sum += arr[i][j];
                }
                if(!isPossible && sum < MIN) {
                    MIN = sum;
                }
            }
            if(MIN < ALL_MIN) {
                ALL_MIN = MIN;
            }
            return;
        }
        for(int i=0; i<K; i++) {
            if(v[i] == 1) continue;
            numbers[cnt] = i;
            v[i] = 1;
            perm(cnt+1, K);
            v[i] = 0;
        }
    }
    public static void turn(int srtX, int srtY, int endX, int endY) {
        if(srtX >= endX || srtY >= endY) {
            return;
        }
        // 상
        for(int i=srtX; i<endX; i++) {
            res[srtY][i+1] = arr[srtY][i];
        }
        // 좌
        for(int i=endY; i>srtY; i--) {
            res[i-1][srtX] = arr[i][srtX];
        }
        // 하
        for(int i=endX; i>srtX; i--) {
            res[endY][i-1] = arr[endY][i];
        }
        // 우
        for(int i=srtY; i<endY; i++) {
            res[i+1][endX] = arr[i][endX];
        }
        turn(srtX+1, srtY+1, endX-1, endY-1);
    }
}
