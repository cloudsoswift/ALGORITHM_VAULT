let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_17425.txt"
).toString().trim().split('\n');
const N_MAX = 1_000_000;
const T = Number(input[0]);
const g_func = new Array(1_000_001).fill(0);
for (let i = 1; i <= N_MAX; i++) { 
  for (let j = i; j <= N_MAX; j+= i) { 
    g_func[j] += i;
  }
  g_func[i] += g_func[i - 1];
}

let res = '';
for (let i = 0; i < T; i++) { 
  const N = Number(input[i + 1]);
  res += g_func[N] +'\n';
}

console.log(res.trim());