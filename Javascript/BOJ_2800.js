const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2800.txt'),
  output: process.stdout
});

let str = "", chArr = [], brackets = [];
let answer = "";
// 나올 수 있는 수식들을 저장하는 Set
// 문제에서 "올바른 괄호 쌍을 제거해서 나올 수 있는 서로 다른 식"를 출력, 즉 중복 없이 출력하기를
// 원하므로 Set을 사용
const cases = new Set();

rl.on('line', (line) => {
  str = line;
  chArr = str.split("");
});

rl.on('close', () => {
  const stack = [];
  // 문자열 내에서 괄호 쌍이 등장하는 위치 쌍을 brackets 배열에 기록
  for (let i = 0, len = chArr.length; i < len; i++) { 
    if (chArr[i] === "(") {
      // 여는 괄호가 등장한 위치를 stack에 기록
      stack.push(i);
    } else if (chArr[i] === ")") { 
      // 가장 최근에 stack에 기록된 여는 괄호 위치를 꺼내
      const pos = stack.pop();
      // 괄호 쌍의 위치 쌍으로 기록
      brackets.push([pos, i]);
    }
  }
  // 괄호를 앞서 등장하는 순서대로 (지우지 않거나, 지우는) 방식으로 할 경우
  // 결과가 자동적으로 사전 순으로 나열되게 됨
  brackets.sort((a, b) => a[0] > b[0] ? 1 : -1);
  comb(chArr, 0, 0);
  // 경우의 수들이 기록된 Set인 cases를 배열로 변환한 뒤, join 메서드를 통해 한 문자열로 합침
  // ** 원래, 정렬없이 DFS 탐색 순서대로 괄호들을 제거/제거하지 않은 경우가 사전순과 유사해 그대로 사용하려 했으나
  // ((a)(b))와 같은 반례가 있어 정렬 사용 **
  answer = [...cases].sort().join('\n');
  console.log(answer);
});

function comb(arr, changed, cnt) { 
  if (cnt === brackets.length) { 
    // 최소 한 번은 괄호를 제거해야 하므로, 괄호 제거 횟수(changed)가 0인 경우는
    // 답으로 기록하지 않음
    if (changed > 0) { 
      cases.add(arr.join(""));
    }
    return;
  }
  // cnt 번째 괄호를 지우지 않고 그냥 넘어감
  comb(arr, changed, cnt + 1);
  // cnt 번째 괄호를 지움
  const [s, e] = brackets[cnt];
  arr[s] = "";
  arr[e] = "";
  comb(arr, (changed | (1 << cnt)), cnt + 1);
  // 지웠던 괄호들 원상 복구시킴
  arr[s] = "(";
  arr[e] = ")";
}