const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  // input: process.stdin,
  input: fs.createReadStream("./input/BOJ_1939.txt"),
  output: process.stdout,
});

let ln = 0, result = 0, N = 0, M = 0;
rl.on('line', (line) => {
  if (ln === 0) { 
    [N, M] = line.split(" ").map(Number);
  }
});

rl.on('close', () => {

});