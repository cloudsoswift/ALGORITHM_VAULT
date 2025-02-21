let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_14719.txt"
).toString().trim().split('\n');
const [H, W] = input[0].split(" ").map(Number);
const slots = input[1].split(" ").map(Number) || [];
let v = Array(W).fill(false);
let stack = [];
let beforeHeight = -1;
let beforeX = 0;
let wholeWater = 0;
for (let i = 0; i < W; i++) { 
  console.log(i + " ]] " + slots[i] + " , " + beforeHeight);
  if (slots[i] >= beforeHeight) {
    console.log(i + " ] " + slots[i] + " >= " + beforeHeight);
    while (stack.length > 0) {
      const p = stack.pop();
      wholeWater += beforeHeight - p;
    }
    for (let j = beforeX; j < i; j++) { 
      v[j] = true;
    }
    beforeHeight = slots[i];
    beforeX = i;
  } else { 
    stack.push(slots[i]);
  }
}
stack = [];
beforeHeight = -1;
for (let i = W - 1; i >= 0; i--) { 
  if (slots[i] >= beforeHeight && !v[i]) {
    while (stack.length > 0) {
      const p = stack.pop();
      wholeWater += beforeHeight - p;
    }
    beforeHeight = slots[i];
  } else { 
    stack.push(slots[i]);
  }
}
console.log(wholeWater);