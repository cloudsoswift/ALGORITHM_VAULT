const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_16120.txt'),
  output: process.stdout,
});

let str = "";

rl.on("line", (line) => {
  str = line;
});

rl.on("close", () => {
  search(str);
});

function search(str) { 
  // 문자열의 각 문자들을 넣을 스택
  const stack = [];
  let n = str.length;
  
  for (let i = 0; i < n; i++) { 
    stack.push(str.charAt(i));
    // 스택에 4개 이상의 문자가 들어있고,
    // 가장 최근에 삽입된 4개 문자들을 이었을때 "PPAP"인 경우,
    // 해당 문자들을 stack으로부터 pop 한 뒤, P 추가
    while (stack.length >= 4 && isPossible(stack)) { 
      popitup(stack);
    }
  }
  // 위 순회를 끝냈을 때, 정상적인 PPAP 문자열이라면 stack에 P 하나만 남아있어야 함
  const answer = (stack.length === 1 && stack[0] === "P") ? "PPAP" : "NP";
  console.log(answer);
}

function isPossible(stack) { 
  // stack의 (삽입 순이 가장 최근인) 4개 원소가 PPAP 인지 확인
  const end = stack.length;
  return stack[end - 4] === "P" && stack[end - 3] === "P" && stack[end - 2] === "A" && stack[end - 1] === "P";
}

function popitup(stack) { 
  // stack의 가장 마지막 4개 원소를 날린 뒤, P 추가 ( 문자 내에 PPAP가 있다 = 해당 PPAP는 이전에 해당 위치에 존재했던 P를 바꾼 것이다 = P 는 PPAP이다 )
  for (let i = 0; i < 4; i++) { 
    stack.pop();
  }
  stack.push("P");
}