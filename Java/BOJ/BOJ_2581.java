package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_2581 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());
        boolean prime[] = makeArr(N);
        prime[1] = prime[0] = true;
        int lowestPrime = -1;
        int primeSum = 0;
        for(int i=M; i<=N; i++) {
            if(!prime[i]) {
                primeSum += i;
                if(lowestPrime == -1) {
                    lowestPrime = i;
                }
            }
        }
        if(lowestPrime <= 1) {
            System.out.println(-1);
            return;
        }
        System.out.println(primeSum);
        System.out.println(lowestPrime);
    }
    public static boolean[] makeArr(int max) {
        boolean arr[] = new boolean[max+1];
        if(max < 2) return arr;
        int sqrtMax = (int) Math.sqrt((double) max) + 1;
        for(int i=2; i<=sqrtMax; i++) {
            if(arr[i] == false) {
                for(int j = i+i; j<=max; j+=i) {
                    arr[j] = true;
                }
            }
        }
        return arr;
    }
}
