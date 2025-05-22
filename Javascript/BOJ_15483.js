const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_15483.txt'),
  output: process.stdout
});

let first = "", second = "";

rl.on("line", (line) => {
  if (first === "") {
    first = line;
  } else { 
    second = line;
  }
});

rl.on("close", () => {
  console.log(calc_edit_distance(first, second));
});

function calc_edit_distance(first, second) { 
  const dp = Array(first.length + 1).fill().map(_ => Array(second.length + 1).fill(0));
  // [i][j]에서 i 또는 j가 0일때는 아무것도 없는 문자열에서 길이 i(또는 j)까지 일치시키기 위한
  // 거리이므로 그 길이인 i(또는 j)가 편집 길이임
  for (let i = 0, len = first.length; i <= len; i++) { 
    dp[i][0] = i;
  }
  for (let i = 0, len = second.length; i <= len; i++) { 
    dp[0][i] = i;
  }

  for (let i = 1, len = first.length; i <= len; i++) { 
    for (let j = 1, llen = second.length; j <= llen; j++) { 
      if (first[i - 1] === second[j - 1]) {
        // (여담. 처음에 String.at()을 사용해 접근했는데, 이 메서드가 Node.js 16.6에 추가되어
        // BOJ에서는 사용할 수 없어 []로 접근하는 것으로 수정함)
        
        // first[i-1] == second[j-1] 인 경우, 
        // 각각의 한칸 전(dp[i-1][j-1]) 편집거리를 그대로 가져옴
        dp[i][j] = dp[i - 1][j - 1];
      } else { 
        // first[i] != second[j] 인 경우,
        // 1. 삽입 (dp[i-1][j]을 가져오는 경우), 
        // 2. 삭제 (dp[i][j-1]을 가져오는 경우),
        // 3. 수정 (dp[i-1][j-1]을 가져오는 경우) 중 가장 적은 값에 1을 추가한 값이 편집거리
        dp[i][j] = Math.min(dp[i - 1][j],
          Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
      }
    }
  }
  return dp[first.length][second.length];
}