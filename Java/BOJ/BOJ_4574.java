package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 4574. 스도미노쿠
public class BOJ_4574 {
    static int[] rowBitmask, columnBitmask, sectionBitmask;
    static int[][] sudoku;
    static boolean isFinished;
    static boolean[][] dominos;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr;
        int count = 1;
        while (true) {
            int N = Integer.parseInt(br.readLine());
            if (N == 0) break;
            isFinished = false;
            sudoku = new int[9][9];
            dominos = new boolean[10][10];
            // 행 별  1~9 숫자 등장 기록
            rowBitmask = new int[9];
            // 열 별 1~9 숫자 등장 기록
            columnBitmask = new int[9];
            // 구획 별 1~9 숫자 등장 기록 ( 0 1 2 / 3 4 5 / 6 7 8 순)
            sectionBitmask = new int[9];
            for(int i=0; i<N; i++) {
                strArr = br.readLine().split(" ");
                int U = Integer.parseInt(strArr[0]);
                int[] LU = translatePosition(strArr[1]);
                int V = Integer.parseInt(strArr[2]);
                int[] LV = translatePosition(strArr[3]);
                sudoku[LU[0]][LU[1]] = U;
                rowBitmask[LU[0]] |= (1 << U);
                columnBitmask[LU[1]] |= (1 << U);
                sectionBitmask[(LU[0]/3) * 3 + LU[1]/3] |= (1 << U);
                sudoku[LV[0]][LV[1]] = V;
                rowBitmask[LV[0]] |= (1 << V);
                columnBitmask[LV[1]] |= (1 << V);
                sectionBitmask[(LV[0]/3) * 3 + LV[1]/3] |= (1 << V);
                dominos[U][V] = dominos[V][U] = true;
            }
            strArr = br.readLine().split(" ");
            for(int i=0; i<9; i++) {
                int[] pos = translatePosition(strArr[i]);
                sudoku[pos[0]][pos[1]] = i+1;
                rowBitmask[pos[0]] |= (1 << (i+1));
                columnBitmask[pos[1]] |= (1 << (i+1));
                sectionBitmask[(pos[0]/3) * 3 + pos[1]/3] |= (1 << i+1);
            }
            System.out.printf("Puzzle %d\n", count);
            count++;
            loop:
            for(int i=0; i<9; i++) {
                for(int j=0; j<9; j++) {
                    if(sudoku[i][j] == 0) {
                        trySudoku(i, j);
                        break loop;
                    }
                }
            }
        }
    }
    public static int[] translatePosition(String pos) {
        int[] rtn = new int[2];
        rtn[0] = pos.charAt(0) - 'A';
        rtn[1] = Character.digit(pos.charAt(1), 10) - 1;
        return rtn;
    }
    public static void trySudoku(int r, int c) {
        if(isFinished) return;
        if(r == 8 && c == 8) {
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<9; i++) {
                for(int j=0; j<9; j++) {
                    sb.append(sudoku[i][j]);
                }
                if(i < 8)
                    sb.append("\n");
            }
            System.out.println(sb.toString());
            isFinished = true;
            return;
        }
        int nextR = c == 8 ? r + 1 : r;
        int nextC = c == 8 ? 0 : c + 1;
        if(sudoku[r][c] != 0) {
            trySudoku(nextR, nextC);
            return;
        }
        for(int i=1; i<=9; i++) {
            // 현재 섹션 내에도, 행에도, 열에도 없는 수인 경우
            if(isPossiblePutNumber(r, c, i)) {
                marking(r, c, i, true);
                if(c+1 < 9 && sudoku[r][c+1] == 0) {
                    // 오른쪽으로 뻗는 도미노를 놓을 수 있는 경우
                    for(int j=1; j<=9; j++) {
                        if(i == j || dominos[i][j]) continue;
                        if(isPossiblePutNumber(r, c+1, j)) {
                            marking(r, c+1, j, true);
                            dominos[i][j] = dominos[j][i] = true;

                            trySudoku(nextR, nextC);

                            marking(r, c+1, j, false);
                            dominos[i][j] = dominos[j][i] = false;
                        }
                    }
                }
                if(r+1 < 9 && sudoku[r+1][c] == 0) {
                    // 아래로 뻗는 도미노를 놓을 수 있는 경우
                    for(int j=1; j<=9; j++) {
                        if(i == j || dominos[i][j]) continue;
                        if(isPossiblePutNumber(r+1, c, j)) {
                            marking(r+1, c, j, true);
                            dominos[i][j] = dominos[j][i] = true;

                            trySudoku(nextR, nextC);

                            marking(r+1, c, j, false);
                            dominos[i][j] = dominos[j][i] = false;
                        }
                    }
                }
                marking(r, c, i, false);
            }
        }
    }
    public static boolean isPossiblePutNumber(int r, int c, int num) {
        return ((sectionBitmask[(r/3) * 3 + c/3] & (1 << num)) == 0) &&
            ((rowBitmask[r] & (1 << num)) == 0) &&
            ((columnBitmask[c] & (1 << num)) == 0);
    }
    public static void marking(int r, int c, int num, boolean isMark) {
        if (isMark) {
            sudoku[r][c] = num;
            rowBitmask[r] |= (1 << num);
            columnBitmask[c] |= (1 << num);
            sectionBitmask[(r/3) * 3 + c/3] |= (1 << num);
        } else {
            sudoku[r][c] = 0;
            rowBitmask[r] &= ~(1 << num);
            columnBitmask[c] &= ~(1 << num);
            sectionBitmask[(r/3) * 3 + c/3] &= ~(1 << num);
        }
    }
}
