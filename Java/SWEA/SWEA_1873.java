package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 1873. 상호의 배틀필드
public class SWEA_1873 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_1873.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T, W, H, N;
        // 방향은 상 : 0, 하 : 1, 좌 : 2, 우 : 3
        int posX = -1, posY = -1, direction = -1;
        char[] tanks = new char[] {'^', 'v', '<', '>'};
        int[] dx = new int[] {0, 0, -1, 1};
        int[] dy = new int[] {-1, 1, 0, 0};
        char[][] map;
        String[] str;
        T = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= T; tc++) {
            str = br.readLine().split(" ");
            H = Integer.parseInt(str[0]);
            W = Integer.parseInt(str[1]);
            map = new char[H][W];
            for(int i=0; i<H; i++) {
                str = br.readLine().split("");
                for(int j=0; j<W; j++) {
                    for(int k=0; k<tanks.length; k++) {
                        if(str[j].charAt(0) == tanks[k]) {
                            direction = k;
                            posX = j;
                            posY = i;
                        }
                    }
                    map[i][j] = str[j].charAt(0);
                }
            }
            N = Integer.parseInt(br.readLine());
            str = br.readLine().split("");
            for(int i=0; i<N; i++) {
                boolean needmove = false;
                switch(str[i]) {
                    case "U":
                        direction = 0;
                        needmove = true;
                        break;
                    case "D":
                        direction = 1;
                        needmove = true;
                        break;
                    case "L":
                        direction = 2;
                        needmove = true;
                        break;
                    case "R":
                        direction = 3;
                        needmove = true;
                        break;
                    case "S":
                        int shootX = posX, shootY = posY;
                        while(shootX >= 0 && shootX < W && shootY >= 0 && shootY < H) {
                            if(map[shootY][shootX] == '*') {
                                map[shootY][shootX] = '.';
                                break;
                            } else if(map[shootY][shootX] == '#') {
                                break;
                            }
                            shootX += dx[direction];
                            shootY += dy[direction];
                        }
                }
                if(needmove) {
                    int mvX = posX+dx[direction];
                    int mvY = posY+dy[direction];
                    if(mvX >=0 && mvX < W && mvY >= 0 && mvY < H && map[mvY][mvX] == '.') {
                        map[posY][posX] = '.';
                        posX = mvX;
                        posY = mvY;
                    }
                    map[posY][posX] = tanks[direction];
                }
            }
            System.out.printf("#%d ", tc);
            for(int i=0; i<H; i++) {
                for(int j=0; j<W; j++) {
                    System.out.printf("%c", map[i][j]);
                }
                System.out.println();
            }
        }
    }
}
