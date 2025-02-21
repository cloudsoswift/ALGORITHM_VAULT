package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

// 1233. [S/W 문제해결 기본] 9일차 - 사칙연산 유효성 검사
public class SWEA_1233 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_1233.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, idx;
        String[] str;
        char[] arr;
        for(int test_case = 1; test_case <= 10; test_case++) {
            N = Integer.parseInt(br.readLine());
            arr = new char[N+1];
            for(int i=0; i<N; i++) {
                str = br.readLine().split(" ");
                idx = Integer.parseInt(str[0]);
                arr[idx] = str[1].charAt(0);
            }
            int d = 2;
            while(d<=N) {
                d *= 2;
            }
            d/=2;
            boolean isPossible = true;
            for(int i=d; i<N; i+=2) {
                if(arr[i] >= '0' && arr[i] <= '9') {
                    if(arr[i+1] >= '0' && arr[i+1] <= '9') {
                        switch(arr[i/2]) {
                            case '+':
                                arr[i/2] = '1';
                                break;
                            case '-':
                                arr[i/2] = '1';
                                break;
                            case '*':
                                arr[i/2] = '1';
                                break;
                            case '/':
                                arr[i/2] = '1';
                                break;
                            default:
                                isPossible = false;
                                break;
                        }
                        if(!isPossible) {
                            break;
                        }
                        continue;
                    }
                }
                isPossible = false;
                break;
            }
            if(isPossible) {
                loop:
                while(d!=2) {
                    for(int i=d/2; i<d; i+=2) {
                        if(arr[i] >= '0' && arr[i] <= '9') {
                            if(arr[i+1] >= '0' && arr[i+1] <= '9') {
                                switch(arr[i/2]) {
                                    case '+':
                                        arr[i/2] = '1';
                                        break;
                                    case '-':
                                        arr[i/2] = '1';
                                        break;
                                    case '*':
                                        arr[i/2] = '1';
                                        break;
                                    case '/':
                                        arr[i/2] = '1';
                                        break;
                                    default:
                                        isPossible = false;
                                        break;
                                }
                                if(!isPossible) {
                                    break loop;
                                }
                                continue;
                            } else {
                                isPossible = false;
                                break loop;
                            }
                        }
                        if(!isPossible) {
                            break loop;
                        }
                    }
                    d/=2;
                }
                if(isPossible) {
                    System.out.printf("#%d %d\n", test_case, 1);
                }
            }
            else {
                System.out.printf("#%d %d\n", test_case, 0);
                continue;
            }
        }
    }
}
