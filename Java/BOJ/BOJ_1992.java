package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1992. 쿼드트리
public class BOJ_1992 {
    static StringBuilder sb;
    static int[][] arr;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        sb = new StringBuilder();
        for(int i=0; i<N; i++) {
            String str[] = br.readLine().split("");
            for(int j=0; j<N; j++) {
                arr[i][j] = Integer.parseInt(str[j]);
            }
        }
        recursive(N, 0, 0);
        System.out.println(sb.toString());
    }
    public static void recursive(int N, int x, int y) {
//		System.out.println(N + ", " + x + ", " + y);
//		System.out.println(sb.toString());
        if(N==2) {
            boolean isZero = false, isOne = false;
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    if(arr[y+i][x+j] == 0) {
                        isZero = true;
                        if(isOne)
                            break;
                    } else if(arr[y+i][x+j] == 1) {
                        isOne = true;
                        if(isZero)
                            break;
                    }
                }
            }
            if(isZero && !isOne) {
                sb.append("0");
                return;
            } else if(isOne && !isZero) {
                sb.append("1");
                return;
            } else {
                sb.append("(");
                for(int i=0; i<N; i++) {
                    for(int j=0; j<N; j++) {
                        if(arr[y+i][x+j] == 0) {
                            sb.append("0");
                        } else if(arr[y+i][x+j] == 1) {
                            sb.append("1");
                        }
                    }
                }
            }
            sb.append(")");
            return;
        } else {
            boolean isZero = false, isOne = false;
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    if(arr[y+i][x+j] == 0) {
                        isZero = true;
                        if(isOne) {
                            break;
                        }
                    } else if(arr[y+i][x+j] == 1) {
                        isOne = true;
                        if(isZero) {
                            break;
                        }
                    }
                }
            }
            if(isZero && !isOne) {
                sb.append("0");
                return;
            } else if(isOne && !isZero) {
                sb.append("1");
                return;
            } else{
                sb.append("(");
                int tmp = N/2;
                recursive(tmp, x, y);
                recursive(tmp, x+tmp, y);
                recursive(tmp, x, y+tmp);
                recursive(tmp, x+tmp, y+tmp);
                sb.append(")");
            }
        }
    }
}
