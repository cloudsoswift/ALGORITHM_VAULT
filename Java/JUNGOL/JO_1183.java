package JUNGOL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 1183. 동전 자판기(下)
public class JO_1183 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int targetMoney = Integer.parseInt(br.readLine());

        int[] units = {500, 100, 50, 10, 5, 1};
        int[] counts = new int[6];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<6; i++) {
            counts[i] = Integer.parseInt(st.nextToken());
        }

        int totalMoney = 0;
        for(int i=0, size = units.length; i<size; i++) {
            // 보유하고 있는 모든 동전들로 만들 수 있는 금액 계산
            totalMoney += units[i] * counts[i];
        }

        int remainMnoey = totalMoney - targetMoney; // 갖고있는 돈에서 음료수 값을 지불하고 남은 나머지 금액

        // 음료수값을 지불하는 데 동전을 최대로 사용하려면, 지불하고 낲은 금액의 동전수를 최소로 하면 됨.

        int sum = 0, maxCnt, availCnt; // availCnt : 현재 사용 가능한 동전 수
        for(int i=0, size = units.length; i < size; i++) {
            maxCnt = remainMnoey/units[i]; // 해당 금액을 i 화폐단위를 가장 많이 쓸 때의 개수
            availCnt = maxCnt<=counts[i] ? maxCnt : counts[i];

            counts[i] -= availCnt; // 사용하고 남은 갯수 -> 음료수 값 지불에 사용될동전 수
            remainMnoey -= availCnt * units[i];

            sum += counts[i]; // 사용되고 낲은 동전이 지불하기 위해 사용될 동전 수
        }
        System.out.println(sum);

        for(int i=0, size = counts.length; i<size; i++) {
            System.out.print(counts[i] + " ");
        }
    }
}
