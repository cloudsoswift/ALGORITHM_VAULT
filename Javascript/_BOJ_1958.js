const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_1958.txt"),
  output: process.stdout,
});

let ln = 0;
let first, second, third;
rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      first = line;
      break;
    case 1:
      second = line;
      break;
    case 2:
      third = line;
      break;
  }
  ln++;
});

rl.on("close", () => {
  const result = getLCS(first, second, third);
  console.log(result);
});

function getLCS(fir, sec, trd) {
  const lcs = new Array(fir.length + 1).fill().map(_ =>
    new Array(sec.length + 1).fill().map(_ => 
    new Array(trd.length + 1).fill(0)
    ));
  let longestLCS = 0, longestLCSArr = [];
  for (let i = 0; i < fir.length; i++) { 
    for (let j = 0; j < sec.length; j++) { 
      for (let k = 0; k < trd.length; k++) { 
        if (fir.at(i) === sec.at(j) && sec.at(j) === trd.at(k)) {
          lcs[i + 1][j + 1][k + 1] = lcs[i][j][k] + 1;
          if (lcs[i + 1][j + 1][k + 1] > longestLCS) { 
            longestLCS = lcs[i + 1][j + 1][k + 1];
          }
        } else { 
          lcs[i + 1][j + 1][k + 1] = Math.max(
            Math.max(lcs[i + 1][j][k], lcs[i + 1][j + 1][k]),
            Math.max(
              Math.max(lcs[i][j + 1][k], lcs[i][j + 1][k + 1]),
              Math.max(lcs[i][j][k+1], lcs[i+1][j][k+1])
            )
          );
        }
      }
    }
  }
  console.log(lcs);
  // console.log(longestLCSArr);
  return longestLCS;
}