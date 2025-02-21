const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_7682.txt"
).toString().trim().split('\n');
let l = 0;
loop:
while (input[l] !== "end") { 
  const tictactoe = new Array(3);
  let x_count = 0, o_count = 0;
  for (let i = 0; i < 3; i++) { 
    tictactoe[i] = new Array(3).fill("");
    for (let j = 0; j < 3; j++) { 
      tictactoe[i][j] = input[l].at(i * 3 + j);
      if (tictactoe[i][j] === "O") o_count++;
      else if (tictactoe[i][j] === "X") x_count++;
    }
  }
  l++;
  // 최종 상태가 아닌 케이스
  // 1. 모든 칸에 말이 놓여있으면서, X가 O보다 적게 놓인 경우
  if (x_count + o_count === 9 && x_count - o_count !== 1) {
    console.log("invalid");
    continue;
  }
  const [o_line_count, x_line_count] = getLines(tictactoe);
  let whole_o_line = 0, whole_x_line = 0;
  // 2. 완성된 줄이 평행하게 여러 개 존재하는 경우
  for (let i = 0; i < 4; i++) { 
    if (o_line_count[i] > 1) { 
      console.log("invalid");
      continue loop;
    }
    else if (x_line_count[i] > 1) { 
      console.log("invalid");
      continue loop;
    }
    whole_o_line += o_line_count[i];
    whole_x_line += x_line_count[i];
  }
  // 3. 완성된 줄이 없고, 빈 칸이 남아있는 경우
  if (o_count + x_count < 9 && whole_o_line + whole_x_line === 0) { 
    console.log("invalid");
    continue;
  }
  // 4. 둘 다 이기는 경우
  if (whole_o_line > 0 && whole_x_line > 0) { 
    console.log("invalid");
    continue;
  }
  // 5. O가 완성해서 끝났는데, X가 O보다 많이 놓인 경우.
  // 즉, 게임이 끝났음에도 X가 한 번 더 놓은 경우
  if (whole_o_line > 0 && o_count !== x_count) { 
    console.log("invalid");
    continue;
  }
  if (whole_x_line > 0 && x_count !== o_count+1) { 
    console.log("invalid");
    continue;
  }
  console.log("valid");
}

function getLines(arr) { 
  const x_line_count = new Array(4).fill(0), o_line_count = new Array(4).fill(0);
  // [우상, 우, 우하, 하]
  const dr = [-1, 0, 1, 1];
  const dc = [1, 1, 1, 0];
  for (let i = 0; i < 3; i++){
    for (let j = 0; j < 3; j++) { 
      for (let k = 0; k < 4; k++) { 
        const maxr = i + dr[k] * 2, maxc = j + dc[k] * 2;
        if (maxr >= 3 || maxc >= 3 || maxr < 0 || maxc < 0 || arr[i][j] === ".") continue;
        let mr = i, mc = j;
        let count = 0;
        while (mr < 3 && mc < 3 && mr >= 0 && mc >= 0 && arr[mr][mc] === arr[i][j]) { 
          count++;
          mr += dr[k];
          mc += dc[k];
        }
        if (count === 3) {
          if (arr[i][j] === "O") o_line_count[k]++;
          else x_line_count[k]++;
        }
      }
    }
  }
  return [o_line_count, x_line_count];
}