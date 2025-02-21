package BOJ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

// 1708. 볼록 껍질
public class BOJ_1708 {

    static class L{
        long x;
        long y;
        public L(long x, long y) {
            super();
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "L [x=" + x + ", y=" + y + "]";
        }

    }
    static class LC implements Comparator<L>{

        @Override
        public int compare(L p1, L p2) {
            int re=Long.compare(p1.y, p2.y);
            if(re==0) {
                return Long.compare(p1.x, p2.x);
            }else {
                return re;
            }
        }

    }
    static class LTC implements Comparator<L>{  // 첫번째를 기준으로

        L p0;
        public LTC(L p0) {
            this.p0 = p0;
        }

        @Override
        public int compare(L p1, L p2) {
            int re=ccw(p0,p1,p2);      // 작으면 평형에 가깝다 -> 각도가 작다 ->수평(외적=0)
            if(re==0) {
                long distance1 = dist(p0, p1);  // 같은 각도면 거리 작은것 먼저 실행
                long distance2 = dist(p0, p2);

                if (distance1 > distance2) {    // 거리가 더 가까운 순으로 정렬
                    return 1;
                }else return -1;
            }else {
                return re>0 ? -1:1;// 앞에가 크면 뒤집어 -> ASC 각도순으로 첫 좌표에서
            }
        }

    }
    static int N;
    public static void main(String[] args) {
        Scanner scann=new Scanner(System.in);
        N=scann.nextInt();
        ArrayList<L> points=new ArrayList<>();
        Stack<L> hull=new Stack<>();
        for (int i = 0; i < N; i++) {
            long x=scann.nextLong();
            long y=scann.nextLong();
            points.add(new L(x,y));
        }
        points.sort(new LC());      // 1. 가장 아래 왼쪽 찾기
        L p0=points.remove(0);
        hull.push(p0);                // 스택에 넣고
        points.sort(new LTC(p0)); // 2. p0를 기준으로 가장 각도 거리순 -> 각도가 가장 작은것(p0와 평행에 가까운순)
        L p1=points.remove(0);
        hull.push(p1);                // 스택에 넣고
        for (int i = 0; i < N-2; i++) {
            L b=points.get(i);
            while(hull.size()>=2) {       // 두개이상
                L a=hull.pop();   //위의것이 뒤에 들어간것. ->삭제
                L p=hull.peek();  //먼저 들어간것 -> 아래것 삭제아님
                int ccwvalue=ccw(p,a,b);
                if(ccwvalue>0) {         // ccw가 아니면 버림
                    hull.push(a); // ccw면 다시 넣기
                    break;
                }
            }
            hull.push(b);    //AB -> BC 일때 C는 무조건 저장
        }
        System.out.println(hull.size());
/*		int size=hull.size();
		for (int i = 0; i < size; i++) {
			L a=hull.pop();
			System.out.println(a.x+" "+a.y);
		}*/
    }

    static int ccw(L p, L a, L b) {
        long result =cross(vec(p,a),vec(a,b));
        if (result > 0) {   // 반시계 방향
            return 1;
        } else if (result < 0) {    // 시계 방향
            return -1;
        } else {
            return 0;
        }
    }
    static long cross(L a, L b) {
        return a.x*b.y-a.y*b.x;
    }
    //ab-> ob-oa 상대벡터
    static L vec(L a, L b) {
        long x=b.x-a.x;
        long y=b.y-a.y;
        return new L(x,y);
    }
    static long dist(L p1, L p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }
}