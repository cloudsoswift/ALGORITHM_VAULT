// 답 아이디어 보고 푼 문제
let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_3151.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = input[1].split(" ").map(Number).sort((a, b) => a > b ? 1 : (a == b ? 0 : -1));

function lower_bound(s, e, v, arr) { 
  let r = e;
  while (s <= e) { 
    const mid = Math.floor((s + e) / 2);
    if (arr[mid] === v && mid < r) { 
      r = mid;
    }
    if (arr[mid] < v) {
      s = mid + 1;
    } else { 
      e = mid - 1;
    }
  }
  return arr[r] === v ? r : -1;
}
function upper_bound(s, e, v, arr) { 
  let r = s;
  while (s <= e) { 
    const mid = Math.floor((s + e) / 2);
    if (arr[mid] === v && mid > r) { 
      r = mid;
    }
    if (arr[mid] <= v) {
      s = mid + 1;
    } else { 
      e = mid - 1;
    }
  }
  return arr[r] === v ? r : -1;
}
console.log(arr);
let count = 0;
for (let i = 0; i < N-1; i++) { 
  for (let j = i + 1; j < N; j++) { 
    const res = -(arr[i] + arr[j]);
    const lower = lower_bound(j + 1, N - 1, res, arr);
    const upper = upper_bound(j + 1, N - 1, res, arr);
    if (lower != -1 && upper != -1) { 
      count += upper - lower + 1;
    }
    // console.log(" < " + i + ", " + j + " > ");
    // console.log(lower + ", " + upper);
  }
}
console.log(count);