let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2668.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = new Array(N);
const answer = new Array(N).fill(false);
for (let i = 0; i < N; i++) { 
  arr[i] = +input[i + 1];
}
for (let i = 0; i < N; i++) { 
  if (answer[arr[i] - 1]) continue;
  const v_up = new Array(N).fill(false);
  const v_down = new Array(N).fill(false);
  search(i, v_up, v_down);
  let count = 0;
  let isPossible = true;
  for (let j = 0; j < N; j++) { 
    if ((v_up[j] & !v_down[j]) || (!v_up[j] & v_down[j])) { 
      isPossible = false;
      break;
    }
  }
  if (isPossible) { 
    for (let j = 0; j < N; j++) { 
      answer[j] = answer[j] | v_up[j];
    }
  }
  console.log(" +======================== " + +(i+1) + " =======================+ ");
  console.log(v_up);
  console.log(v_down);
}
let count = 0;
let answerText = "";
for (let i = 0; i < N; i++) { 
  if (answer[i]) { 
    count++;
    answerText += (i + 1) + "\n";
  }
}
console.log(answer);
console.log(count);
console.log(answerText);

function search(idx, v_up, v_down) { 
  const down = arr[idx] - 1;
  v_up[idx] = true;
  v_down[down] = true;
  if (!v_up[down]) { 
    search(down, v_up, v_down);
  }
}