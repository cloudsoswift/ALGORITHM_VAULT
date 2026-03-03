package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 2671. 잠수함 식별
public class BOJ_2671 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // ^...$ : 문자열의 시작과 끝
        // 문제에서 패턴은 `100~1~ 또는 01`을 섞어 만들 수 있는 모든 경우의 집합임.
        // 여기서 100~1~는 10 뒤에 0이 하나 이상, 그리고 1이 하나 이상 이어서 등장하는 형태이다.
        // 이는 100+1+ ( 10 뒤에 0이 1개 이상(0+), 1이 1개 이상(1+) 등장)으로 나타낼 수 있다.
        // 또한 100+1+ 또는 01을 섞어서 만들 수 있으므로 (A|B) 와 같은 형태로 묶는다.
        // 이후, 둘 중 하나가 계속해서 이어져나오는 형태이므로 (A|B) 뒤에 +를 붙여, 해당 패턴이 한 번 이상 반복됨을 나타낸다.
        // 이를 종합하면 ^(100+1+|01)+$ 라는 패턴이 만들어 진다.
        // 위 패턴을 기반으로 '정규 표현식의 컴파일된 표현체'인 Pattern 객체를 만들고,
        Pattern pattern = Pattern.compile("^(100+1+|01)+$");
        String str = br.readLine();
        // Pattern 객체를 활용해, 입력받은 문자열을 인자로 넣어 '해석한 패턴을 바탕으로, 입력받은 문자열 시퀀스에 대해
        // 일치 작업을 수행하는 엔진'인 Matcher 객체를 만든다.
        Matcher mathcer = pattern.matcher(str);
        // 입력 시퀀스에서 패턴과 일치하는 다음 서브시퀀스를 찾았을 경우 true를 반환하는 find 메서드를 호출해
        // true일 경우, 잠수함. false일 경우, 잡음임을 출력한다.
        if(mathcer.find()) {
            System.out.println("SUBMARINE");
        } else {
            System.out.println("NOISE");
        }
    }
}
