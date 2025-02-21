// 답 보고 푼 문제
// 원래 아이디어
// ====================
// 0. ['z'-'a'] 만큼의 일차원 배열 선언
// 1. 슬라이딩 윈도우 길이를 1부터 W 길이만큼 늘린다.
// 1.1. 슬라이딩 윈도우의 앞, 뒤 포인터들을 한 칸씩 전진하면서 앞의 문자값은 빼고 뒤의 문자값은 카운트한다.
// 1.2. 만약 뒤의 문자 값이 K와 같다면 짧은 문자열 길이 기록하고 탈출
// 2. 슬라이딩 윈도우 길이를 W 길이부터 "짧은 문자열 길이" 까지 줄여간다.
// 2.1. 슬라이딩 윈도우의 앞, 뒤 포인터들을 한 칸씩 전진하면서 앞의 문자값은 빼고 뒤의 문자값은 카운트한다.
// 2.2. 만약 뒤의 문자 값이 K와 같고, 앞과 뒤의 문자가 같으면 최장 문자열 길이 기록하고 탈출
// ====================
// 위와 같이 푸니 시간 초과 발생
// 
// 답 아이디어
// ====================
// 1. ['z'-'a'][] 만큼의 이차원 배열 선언 (뒤의 배열은 push로 값을 계속 넣는다.)
// 2. 각 문자를 만날 때 마다, 해당 문자 아스키코드 위치에 현재 index 값을 추가한다.
// 3. 각 문자들을 돌아보며, 배열 길이가 K 이상인 경우 탐색 시작한다.
// 4. [0, K] 투 포인터를 두고, 전진하며 뒤의 값 index - 앞의 값 index
// (즉, 해당 알파벳을 K개 포함하는 문자열 길이 값이다) 를 계산한다.
// 5. 그렇게 계산한 값으로 최단 연속 문자열, 최장 연속 문자열 길이를 갱신해나간다.
const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_20437.txt"
).toString().trim().split('\n').map(str => str.trim());
const T = +input[0];
const start_ascii = "a".charCodeAt(), end_ascii = "z".charCodeAt() - start_ascii;
for (let tc = 0; tc < T; tc++) { 
  const W = input[tc * 2 + 1].split("");
  const K = +input[tc * 2 + 2];
  const Wlen = W.length;
  const count_arr = new Array(end_ascii + 1).fill().map(_ => new Array());
  let shortest_len = Wlen + 1, longest_len = 0;
  for (let i = 0; i < Wlen; i++) { 
    count_arr[W[i].charCodeAt() - start_ascii].push(i);
  }
  for (let i = 0; i <= end_ascii; i++) { 
    if (count_arr[i].length >= K) { 
      let start = 0, end = K - 1;
      while (end <= count_arr[i].length) { 
        const len = count_arr[i][end] - count_arr[i][start] + 1;
        if (shortest_len > len) shortest_len = len;
        if (longest_len < len) longest_len = len;
        start++;
        end++;
      }
    }
  }
  if (shortest_len === Wlen + 1) {
    console.log(-1);
  } else { 
    console.log(shortest_len + " " + longest_len);
  }
}