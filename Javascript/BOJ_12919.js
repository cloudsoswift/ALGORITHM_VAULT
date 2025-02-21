const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_12919.txt"
).toString().trim().split('\n').map(str => str.trim());
const S = input[0];
const T = input[1];
let isPossible = false;
const N = S.length;

trying(T);
console.log(isPossible ? 1 : 0);

function trying(str) { 
  console.log(str);
  if (isPossible) return;
  if (str.length === N) { 
    if (str === S) isPossible = true;
    return;
  }
  if (str.at(-1) === "A") { 
    trying(str.substring(0, str.length-1));
  }
  if (str.at(0) === "B") {
    trying(str.substring(1, str.length).split("").reverse().join(""));
  }
}