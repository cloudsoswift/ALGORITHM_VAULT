// 모르겠어서 답(https://ilmiodiario.tistory.com/166) 보고 푼 문제

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_7490.txt'),
  output: process.stdout,
})

let ln = 0, tc = 0, input = 0, answer = "";

rl.on('line', (line) => {
  if (ln === 0) {
    tc = Number(line);
  } else { 
    input = Number(line);
    answer = "";
    doCount(1, 1, 1, 0, "1");
    console.log(answer);
  }
  ln++;
});

rl.on('close', () => {
});

function doCount(step, prev, op, sum, exp) { 
  if (step === input) {
    sum += op * prev;
    if (sum === 0) {
      
      answer += exp;
      answer += "\n";
    }
    return;
  } else {
    const v = step + 1;
    // 앞의 결과에 ' ' + v를 붙이는 경우 
    doCount(v, prev * 10 + v, op, sum, exp + ' ' + v);
    // =====================================
    // 아래 두 경우는 현재 선택지에서 ' '를 고르는게 아니므로,
    // 앞의 값(prev)에 더 숫자가 붙는게 아니므로
    // 이전 값(prev)과 이전 값의 앞에 붙어있던 연산(op)을 토대로 
    // sum에 누적 시키기 위해 sum + (op * prev)를 수행
    // =====================================
    // 앞의 결과에 '+' + v를 붙이는 경우 
    doCount(v, v, 1, sum + (op * prev), exp + '+' + v);
    // 앞의 결과에 '-' + v를 붙이는 경우
    doCount(v, v, -1, sum + (op * prev ), exp + '-' + v);
  }
}