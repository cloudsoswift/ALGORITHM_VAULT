const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1943.txt'),
  output: process.stdout,
});

let cnt = 0;
let coins = [];
let answer = "";
let N = 0, total_value = 0, half_value = 0;
let isPossible = false;

rl.on('line', (line) => {
  if (N === 0) {
    N = Number(line);
    coins = [];
    cnt = 1;
    total_value = 0;
    half_value = 0;
    isPossible = false;
  } else { 
    const splited = line.split(" ").map(Number);
    coins.push([...splited]);
    total_value += (splited[0] * splited[1]);
    if (cnt === N) { 
      half_value = total_value / 2;
      calcPossibility();
      N = 0;
    }
    cnt++;
  }
});

rl.on('close', () => {
  console.log(answer);
});

function calcPossibility() { 
  combination(0, 0);
  answer = answer.concat(`${isPossible ? "1" : "0"}\n`);
}

function combination(coin_num, accu_value) { 
  if (isPossible) return;
  if (coin_num === N) { 
    if (half_value === accu_value)
      isPossible = true;
    return;
  }
  const now_coin_sum = coins[coin_num][0] * coins[coin_num][1];
  for (let i = 0; i <= now_coin_sum; i += coins[coin_num][0]) { 
    if (accu_value + i <= half_value) {
      combination(coin_num + 1, accu_value + i);
    }
  }
}