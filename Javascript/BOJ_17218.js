const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_17218.txt'),
  output: process.stdout,
});

let ln = 0, A = "", B = "";

rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      A = line;
      break;
    case 1:
      B = line;
      break;
  }
  ln++;
});

rl.on("close", () => {
  findLCS();
});

function findLCS() { 
  let answer = [];
  const aLen = A.length, bLen = B.length;
  // d[i][j] = 첫 번째 문자열의 i-1번째 인덱스까지, 두 번째 문자열의 j-1번째 인덱스까지 비교했을때
  // 찾을 수 있는 LCS의 최대 길이
  const d = new Array(aLen + 1).fill().map(_ => new Array(bLen + 1).fill(0));
  for (let i = 0; i < aLen; i++) { 
    for (let j = 0; j < bLen; j++) { 
      if (A.charAt(i) === B.charAt(j)) {
        // 일치하는 문자를 찾은 경우
        d[i + 1][j + 1] = d[i][j] + 1;
      } else { 
        // 그렇지 않은 경우,
        // i-1까지, j-1까지에서 찾을 수 있는 LCS 최대 길이는
        // d[i][j]의 오른쪽 (i-1까지, j까지 찾은 LCS) 또는 d[i][j]의 아랫쪽(i까지, j-1까지 찾은 LCS) 값 중 더 큰 값으로 올림
        d[i + 1][j + 1] = d[i][j + 1] > d[i + 1][j] ? d[i][j + 1] : d[i + 1][j];
      }
    }
  }
  let r = aLen, c = bLen;
  while (r > 0 && c > 0) { 
    if (d[r][c] === d[r - 1][c]) {
      // 현재 LCS 길이가 i-1, j에서 온 경우
      r--;
    } else if (d[r][c] === d[r][c - 1]) {
      // 현재 LCS 길이가 i, j-1에서 온 경우
      c--;
    } else { 
      // 현재 LCS 길이가 i-1,j-1에서 온 경우, 즉 두 문자열에서 모두 등장한 문자인 경우
      answer.push(A.charAt(r - 1));
      r--;
      c--;
    }
  }
  // 위 while문에서 역순으로 탐색하면서 answer에 문자들을 넣었기 때문에 뒤집어주어야 함
  console.log(answer.reverse().join(""));
}