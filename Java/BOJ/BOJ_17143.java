package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 17143. 낚시왕
public class BOJ_17143 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        int dx[] = new int[] { 0, 0, 1, -1 };
        int dy[] = new int[] { -1, 1, 0, 0 };
        int R = Integer.parseInt(str[0]);
        int C = Integer.parseInt(str[1]);
        int M = Integer.parseInt(str[2]);
        int remainSharks = M;
        int map[][] = new int[R][C];
        // [[속력, 이동방향, 크기],...,[속력, 이동방향, 크기]]
        int sharks[][] = new int[M+1][5];
        for(int i=1; i<=M; i++) {
            str = br.readLine().split(" ");
            int r = Integer.parseInt(str[0])-1;
            int c = Integer.parseInt(str[1])-1;
            sharks[i][0] = r;
            sharks[i][1] = c;
            sharks[i][2] = Integer.parseInt(str[2]); // 속력
            sharks[i][3] = Integer.parseInt(str[3])-1; // 이동 방향
            sharks[i][4] = Integer.parseInt(str[4]); // 크기
            map[r][c] = i;
        }
        int sizeSum = 0;
        // 1. 낚시왕이 오른쪽으로 한 칸 이동한다.
        for(int i=0; i<C; i++) {
            if(remainSharks == 0) break;
            int j = 0;
//			for(int k=0; k<R; k++) {
//				System.out.println(Arrays.toString(map[k]));
//			}
            while(j<R && map[j][i] == 0) {
                j++;
            }
            if(j!=R) {
                // 2. 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다.
                if(map[j][i] != 0)	{
                    sizeSum += sharks[map[j][i]][4];
                    //상어를 잡으면 격자판에서 잡은 상어가 사라진다.
                    sharks[map[j][i]] = null;
                    map[j][i] = 0;
                    remainSharks--;
                }
            }
            // 3. 상어가 이동한다.
            for(int k=1; k<=M; k++) {
                if(sharks[k] == null) continue;
                int mr = sharks[k][0];
                int mc = sharks[k][1];
                int cnt = sharks[k][2];
                int dir = sharks[k][3];
                mr += (dy[dir] * cnt);
                mc += (dx[dir] * cnt);
                if(mr < 0 || mr >= R) {
                    // y축으로 위 또는 아래에 한 번 이상 부딪혀 방향 전환을 하는 경우
                    boolean isRight = dir == 1? true : false;
                    if(mr<0) isRight = true;
                    mr = Math.abs(mr);
                    int div = mr/(R-1);
                    int mod = mr%(R-1);
                    if(div != 0) {
                        if(div % 2 != 0) {
                            isRight = !isRight;
                        }
                    }
                    if(isRight) {
                        mr = mod;
                        dir = 1;
                    } else {
                        mr = R-1-mod;
                        dir = 0;
                    }
                }
                if(mc < 0 || mc >= C) {
                    boolean isRight = dir == 2 ? true : false;
                    if(mc<0) isRight = true;
                    mc = Math.abs(mc);
                    int div = mc/(C-1);
                    int mod = mc%(C-1);
                    if(div != 0) {
                        if(div % 2 != 0) {
                            isRight = !isRight;
                        }
                    }
                    if(isRight) {
                        mc = mod;
                        dir = 2;
                    } else {
                        mc = C-1-mod;
                        dir = 3;
                    }
                }
                sharks[k][3] = dir;
                sharks[k][0] = mr;
                sharks[k][1] = mc;
            }
            map = new int[R][C];
            for(int k=1; k<=M; k++) {
                if(sharks[k]==null) continue;
                int mr = sharks[k][0];
                int mc = sharks[k][1];
                int preoccupy = map[mr][mc];
                if(preoccupy != 0) {
                    if(sharks[k][4] > sharks[preoccupy][4]) {
                        map[mr][mc] = k;
                        sharks[preoccupy] = null;
                        remainSharks--;
                    } else {
                        map[mr][mc] = preoccupy;
                        sharks[k] = null;
                        remainSharks--;
                    }
                } else {
                    map[mr][mc] = k;
                }
            }
//			System.out.println(i + " ) " + sizeSum);
        }
        System.out.println(sizeSum);
    }
}
