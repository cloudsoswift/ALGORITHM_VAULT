const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2195.txt'),
  output: process.stdout,
});

let ln = 0, S = "", P = "";

rl.on("line", (line) => {
  if (ln === 0) {
    S = line;
  } else { 
    P = line;
  }
  ln++;
});

rl.on("close", () => {
  const P_len = P.length, S_len = S.length;
  let count = 0;
  for (let i = 0; i < P_len; i++) { 
    // 현재 위치 i부터 길이 n(0 <= n < P_len - i)의 부분 문자열들 중
    // P의 부분 문자열과 일치하는 것 중 가장 길이가 긴 것( = 길이가 jumpTo 인 것)
    // 을 찾는 기능
    let jumpTo = 0;
    for (let j = 0; j < S_len; j++) { 
      if (S.charAt(j) === P.charAt(i)) { 
        let tmp = i;
        while (j < S_len && S.charAt(j) === P.charAt(tmp)) { 
          tmp++;
          j++;
        }
        tmp = tmp - 1;
        if (tmp - i > jumpTo) { 
          jumpTo = tmp - i;
        }
      }
    }
    count++;
    // 찾은 가장 길이가 긴 부분 문자열 길이-1 만큼 i 증가
    // (어차피 for 반복문의 i++을 할 것이므로 -1을 line 34에서 미리 처리)
    i += jumpTo;
  }
  console.log(count);
});