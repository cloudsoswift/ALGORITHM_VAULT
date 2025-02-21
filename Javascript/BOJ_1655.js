// 내림차순 PQ, 오름차순 PQ
class PriorityQueue { 
  constructor(size, isMax) { 
    this.treeSize = Math.pow(2, Math.ceil(Math.log2(size)));
    this.arr = Array(this.treeSize);
    this.last = 0;
    this.isMax = isMax;
  }
  len() { 
    return this.last;
  }
  push(input) { 
    this.arr[this.last] = input;
    let parent = Math.floor((this.last - 1) / 2);
    let son = this.last;
    if (this.isMax) {
      while (parent >= 0 && son >= 0 && this.arr[parent] < this.arr[son]) { 
        const tmp = this.arr[parent];
        this.arr[parent] = this.arr[son];
        this.arr[son] = tmp;
        parent = Math.floor((parent - 1) / 2);
        son = Math.floor((son - 1) / 2);
      }
    } else { 
      while (parent >= 0 && son >= 0 && this.arr[parent] > this.arr[son]) { 
        const tmp = this.arr[parent];
        this.arr[parent] = this.arr[son];
        this.arr[son] = tmp;
        parent = Math.floor((parent - 1) / 2);
        son = Math.floor((son - 1) / 2);
      }
    }
    this.last++
  }
  pop() { 
    const p = this.arr[0];
    this.arr[0] = this.arr[this.last - 1];
    this.arr[this.last - 1] = undefined;
    this.last--;
    if (this.isMax) this.heapifyMax(0);
    else this.heapifyMin(0);
    return p;
  }
  top() { 
    return this.arr[0];
  }
  heapifyMin(idx) { 
    const left = idx * 2 + 1;
    const right = idx * 2 + 2;
    let pos = idx;
    if (left < this.last && this.arr[left] < this.arr[pos]) { 
      pos = left;
    }
    if (right < this.last && this.arr[right] < this.arr[pos]) { 
      pos = right;
    }
    if (pos != idx) { 
      const tmp = this.arr[idx];
      this.arr[idx] = this.arr[pos];
      this.arr[pos] = tmp;
      this.heapifyMin(pos);
    }
  }
  heapifyMax(idx) { 
    const left = idx * 2 + 1;
    const right = idx * 2 + 2;
    let pos = idx;
    if (left < this.last && this.arr[left] > this.arr[pos]) { 
      pos = left;
    }
    if (right < this.last && this.arr[right] > this.arr[pos]) { 
      pos = right;
    }
    if (pos != idx) { 
      const tmp = this.arr[idx];
      this.arr[idx] = this.arr[pos];
      this.arr[pos] = tmp;
      this.heapifyMax(pos);
    }
  }
}
// 답안 보고 푼 문제 (https://www.acmicpc.net/board/view/122374)
// 아이디어
// 현재까지 입력받은 수열 중 가운데의 값 x를 기준으로
// 1) x 이후의 수를 저장하는 오름차순 우선순위 큐 MinQueue ( 즉, top이 Queue 내의 최소값)
// 2) x를 포함해 x 이전의 수를 저장하는 내림차순 우선순위 큐 MaxQueue ( 즉, top이 Queue 내의 최대값)
// 두 가지 PQ를 관리
// 
const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1655.txt"
).toString().trim().split('\n');
const N = +input[0];
const MinQueue = new PriorityQueue(N, false);
const MaxQueue = new PriorityQueue(N, true);
const answers = [];
for (let i = 1; i <= N; i++) { 
  // MinQueue의 길이는 MaxQueue의 길이보다 1개 적거나 같음
  if (MaxQueue.len() === MinQueue.len()) MaxQueue.push(+input[i]);
  else MinQueue.push(+input[i]);
  if (MaxQueue.top() > MinQueue.top()) { 
    // MinQueue와 MaxQueue 규칙을 지키기 위해 swap
    const minTop = MinQueue.pop();
    const maxTop = MaxQueue.pop();
    MinQueue.push(maxTop);
    MaxQueue.push(minTop);
  }
  answers.push(MaxQueue.top());
}
console.log(answers.join("\n"));