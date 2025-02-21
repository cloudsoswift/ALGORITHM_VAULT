package SWEA;

// 1210. [S/W 문제해결 기본] 2일차 - Ladder1

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_1210 {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input_1210.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String T = "";
        int[][] arr;
        while (!T.equals("10")) {
            T = br.readLine();
            arr = new int[100][100];
            int posX = -1, posY = -1;
            for (int i = 0; i < 100; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 100; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    if (i == 99 && arr[i][j] == 2) {
                        posX = j;
                        posY = i;
                    }
                }
            }
            posY -= 1;
            while (posY > 0) {
                if (posX - 1 >= 0 && arr[posY][posX - 1] == 1) {
                    while (posX - 1 >= 0 && arr[posY][posX - 1] != 0) {
                        posX--;
                    }
                    posY--;
                } else if (posX + 1 < 100 && arr[posY][posX + 1] == 1) {
                    while (posX + 1 < 100 && arr[posY][posX + 1] != 0) {
                        posX++;
                    }
                    posY--;
                } else {
                    posY--;
                }
            }
            System.out.println("#" + T + " " + posX);
        }
    }
}
