package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1074. Z
public class BOJ_1074 {
    static int seq, realSeq;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, r, c;
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        r = Integer.parseInt(str[1]);
        c = Integer.parseInt(str[2]);
        seq = 0;
        realSeq = -1;
        visit((int) Math.pow(2, N), 0, 0, r, c);
        System.out.println(realSeq);
    }
    public static void visit(int N, int r, int c, int destR, int destC) {
        if(N==2) {
            for(int i=r; i<r+N; i++) {
                for(int j=c; j<c+N; j++) {
                    System.out.println(i + ", " + j);
                    if(i == destR && j == destC) {
                        System.out.println("REAL " + seq);
                        realSeq = seq;
                        return;
                    }
                    seq++;
                }
            }
        } else {
            if(realSeq==-1) {
                int tmp = N/2;
                System.out.println(destR + ", " + destC + ", " + tmp);
                System.out.println(r + ", " + c);
                int mr = r+tmp, mc = c+tmp;
                if(destR < mr && destC < mc) {
                    visit(tmp, r, c, destR, destC);
                }
                seq+=tmp*tmp;
                if(destR < mr && destC >= mc) {
                    visit(tmp, r, c+tmp, destR, destC);
                }
                seq+=tmp*tmp;
                if(destR >= mr && destC < mc) {
                    visit(tmp, r+tmp, c, destR, destC);
                }
                seq+=tmp*tmp;
                if(destR >= mr && destC >= mc) {
                    visit(tmp, r+tmp, c+tmp, destR, destC);
                }
            }
        }
    }
}
