package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 16935. 배열 돌리기 3
public class BOJ_16935 {
    public static void main(String[] args) throws IOException {
        int N, M, R, arr[][], res[][];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        R = Integer.parseInt(str[2]);
        arr = new int[N][M];
        res = new int[N][M];
        for(int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        str = br.readLine().split(" ");
        for(int i=0; i<str.length; i++) {
            switch(str[i]) {
                case "1":
                    // 상하 반전
                    res = new int[N][M];
                    for(int j=0; j<N; j++) {
                        for(int k=0; k<M; k++) {
                            res[j][k] = arr[N-1-j][k];
                        }
                    }
                    for(int j=0; j<N; j++) {
                        System.arraycopy(res[j], 0, arr[j], 0, res[j].length);
                    }
                    break;
                case "2":
                    // 좌우 반전
                    res = new int[N][M];
                    for(int j=0; j<N; j++) {
                        for(int k=0; k<M; k++) {
                            res[j][k] = arr[j][M-1-k];
                        }
                    }
                    for(int j=0; j<N; j++) {
                        System.arraycopy(res[j], 0, arr[j], 0, res[j].length);
                    }
                    break;
                case "3":
                    // 오른쪽으로 90도 회전
                    if(arr.length==N) {
                        int tmp = M;
                        M = N;
                        N = tmp;
                    } else {
                        int tmp = M;
                        M = N;
                        N = tmp;
                    }
                    res = new int[N][M];
                    for(int j=0; j<N; j++) {
                        for(int k=0; k<M; k++) {
                            res[j][k] = arr[M-k-1][j];
                        }
                    }
                    arr = new int[N][M];
                    for(int j=0; j<arr.length; j++) {
                        System.arraycopy(res[j], 0, arr[j], 0, res[j].length);
                    }
                    break;
                case "4":
                    // 왼쪽으로 90도 회전
                    if(arr.length==N) {
                        int tmp = M;
                        M = N;
                        N = tmp;
                    } else {
                        int tmp = M;
                        M = N;
                        N = tmp;
                    }
                    res = new int[N][M];
                    for(int j=0; j<N; j++) {
                        for(int k=0; k<M; k++) {
                            res[j][k] = arr[k][N-j-1];
                        }
                    }
                    arr = new int[N][M];
                    for(int j=0; j<arr.length; j++) {
                        System.arraycopy(res[j], 0, arr[j], 0, res[j].length);
                    }
                    break;
                case "5":
                    // 배열을 4등분 한다.
                    // 왼쪽 위부터 시계방향으로 1,2,3,4번 그룹으로 나눈다.
                    // 1번 그룹 -> 2번 그룹 위치, 2번 그룹 -> 3번 그룹 위치
                    // 3번 그룹 -> 4번 그룹 위치, 4번 그룹 -> 1번 그룹 위치
                    // 이렇게 이동 시킨다.
                    res = new int[N][M];
                    for(int j=0; j<N; j++) {
                        for(int k=0; k<M; k++) {
                            if(j<N/2) {
                                if(k<M/2) {
                                    //1번 그룹
                                    res[j][k] = arr[j+N/2][k];
                                } else {
                                    //2번 그룹
                                    res[j][k] = arr[j][k-M/2];
                                }
                            }
                            else {
                                if(k<M/2) {
                                    //4번 그룹
                                    res[j][k] = arr[j][k+M/2];
                                } else {
                                    // 3번 그룹
                                    res[j][k] = arr[j-N/2][k];
                                }
                            }
                        }
                    }
                    for(int j=0; j<arr.length; j++) {
                        System.arraycopy(res[j], 0, arr[j], 0, res[j].length);
                    }
                    break;
                case "6":
                    // 1번 그룹 -> 4번 그룹 위치, 4번 그룹 -> 3번 그룹 위치
                    // 3번 그룹 -> 2번 그룹 위치, 2번 그룹 -> 1번 그룹 위치
                    res = new int[N][M];
                    for(int j=0; j<N; j++) {
                        for(int k=0; k<M; k++) {
                            if(j<N/2) {
                                if(k<M/2) {
                                    //1번 그룹
                                    res[j][k] = arr[j][k+M/2];
                                } else {
                                    //2번 그룹
                                    res[j][k] = arr[j+N/2][k];
                                }
                            }
                            else {
                                if(k<M/2) {
                                    //4번 그룹
                                    res[j][k] = arr[j-N/2][k];
                                } else {
                                    // 3번 그룹
                                    res[j][k] = arr[j][k-M/2];
                                }
                            }
                        }
                    }
                    for(int j=0; j<arr.length; j++) {
                        System.arraycopy(res[j], 0, arr[j], 0, res[j].length);
                    }
                    break;
            }
        }
        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                sb.append(res[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }
}
