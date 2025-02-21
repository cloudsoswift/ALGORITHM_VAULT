package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 1339. 단어 수학
public class BOJ_1339 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 알파벳 대문자는 0~9 까지의 숫자와 바꿀수 있으며, 두 개 이상의 알파벳이 같은 숫자로 바뀔수 없으므로
        // 알파벳 10개와, 그 알파벳의 등장 자리수 기록.
        char characters[] = new char[10];
        int Integers[] = new int[10], pos = 0;
        int N = Integer.parseInt(br.readLine());
        String[] arr = new String[N];
        int[] strToInt = new int[N];
        for(int i=0;i<N; i++) {
            arr[i] = br.readLine();
            for(int j = 0, size = arr[i].length(); j<size; j++) {
                char c = arr[i].charAt(j);
                boolean isExist = false;
                for(int k=0; k<pos; k++) {
                    if(characters[k]==c) {
                        // 원래는 각 알파벳이 등장했던 최대 자리수만 기록하려 했는데 그렇게 하면
//						10
//						ABB
//						BB
//						BB
//						BB
//						BB
//						BB
//						BB
//						BB
//						BB
//						BB
                        // 위와 같은 경우에 B를 9로, A를 8로 할당하는게 최고값인데 그렇게 할당하지 못함.
                        // 그래서 10^자리수 한 값을 계속 합산함.
//						Integers[k] = Integers[k] <size - j ? size-j : Integers[k];
                        Integers[k] += Math.pow(10, size-j);
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) {
                    characters[pos] = c;
//					Integers[pos++] = size-j;
                    Integers[pos++] = (int) Math.pow(10, size-j);
                }
            }
        }
        // 등장 빈도값 높은 순으로 정렬
        for(int i=0; i<pos; i++) {
            for(int j=i; j<pos; j++) {
                if(Integers[i] < Integers[j]) {
                    int tmp = Integers[i];
                    char tmpC = characters[i];
                    Integers[i] = Integers[j];
                    characters[i] = characters[j];
                    Integers[j] = tmp;
                    characters[j] = tmpC;
                }
            }
        }
        int sum = 0;
        for(int i=0; i<N; i++) {
            for(int j=0, size=arr[i].length(); j<size; j++) {
                char c = arr[i].charAt(j);
                int num = 0;
                for(int k = 0; k<pos; k++) {
                    if(characters[k]==c) {
                        // 9 - 빈도 순서 (0~9)
                        num = characters.length-1-k;
                    }
                }
                strToInt[i]*=10;
                strToInt[i]+=num;
            }
            sum += strToInt[i];
        }
        System.out.println(Arrays.toString(Integers));
        System.out.println(Arrays.toString(characters));
        System.out.println(sum);
    }
}
