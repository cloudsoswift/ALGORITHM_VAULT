package BOJ;

import java.util.Scanner;

// 2023. 신기한 소수
public class BOJ_2023 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int MAX = (int) Math.pow(10, N);
        int MIN = (int) Math.pow(10, N-1);
        for(int i=MIN; i<MAX; i++) {
            int first = i / MIN;
            //왼쪽에서 1번째 자리 소수인지 체크
            if(first != 2 && first != 3 && first != 5 && first != 7) {
                continue;
            }
            boolean isPrime = true;
            //왼쪽에서 N-j번째 자리 소수인지 체크
            for(int j=2; j<=N; j++) {
                int now = i / (int) Math.pow(10, N-j);
                int sq = (int) Math.sqrt(now);
                for(int k=2; k<=sq; k++) {
                    if(now%k==0) {
                        isPrime = false;
                        break;
                    }
                }
                if(!isPrime) {
                    break;
                }
            }
            if(isPrime) {
                System.out.println(i);
            }
        }
    }
}
