package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

// 2239. 스도쿠
public class BOJ_2239 {
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    static boolean isPrinted = false;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int map[][] = new int[9][9];
        String str;
        Queue<Point> queue = new LinkedList<Point>();
        for(int i=0; i<9; i++) {
            str = br.readLine();
            for(int j=0; j<9; j++) {
                map[i][j] = Character.digit(str.charAt(j), 10);
                if(map[i][j]==0)
                    queue.offer(new Point(j, i));
            }
        }
        dfs(map, queue);
    }
    public static void dfs(int map[][], Queue<Point> queue) {
        if(isPrinted) return;
        if(queue.isEmpty()) {
            for(int i=0; i<9; i++) {
                for(int j=0; j<9; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
            isPrinted = true;
            return;
        }
        Point now = queue.poll();
        boolean avaliable[] = new boolean[10];
        avaliable[0] = true;
        int availCount = 9;
        for(int i=0; i<9; i++) {
            int pos = map[now.y][i];
            if(!avaliable[pos]) {
                avaliable[pos] = true;
                availCount--;
            }
            pos = map[i][now.x];
            if(!avaliable[pos]) {
                avaliable[pos] = true;
                availCount--;
            }
        }
        int startX = 0, startY = 0;
        if(now.x >= 3) {
            if(now.x >= 6) {
                startX = 6;
            } else {
                startX = 3;
            }
        }
        if(now.y >= 3) {
            if(now.y >= 6) {
                startY = 6;
            } else {
                startY = 3;
            }
        }
        for(int i=startY; i<startY+3; i++) {
            for(int j=startX; j<startX+3; j++) {
                int pos = map[i][j];
                if(!avaliable[pos]) {
                    avaliable[pos] = true;
                    availCount--;
                }
            }
        }
        if(availCount == 0) return;
        for(int i=1; i<10; i++) {
            if(!avaliable[i]) {
                Queue<Point> newqueue = new LinkedList<>(queue);
                map[now.y][now.x] = i;
                dfs(map, newqueue);
                map[now.y][now.x] = 0;
            }
        }
    }
}
