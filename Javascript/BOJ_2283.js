// 누적합 계산을 위한 우선순위 큐 구현체 코드
class PriorityQueue { 
  constructor() {
    this.heap = [];
  }

  peek() { 
    if (this.heap.length === 0) return null;
    return this.heap[0];
  }
  enqueue(item) { 
    this.heap.push(item);
    this.heapifyUp();
  }

  dequeue() { 
    if (this.heap.length === 0) return null;
    const item = this.heap[0];
    this.heap[0] = this.heap[this.heap.length - 1];
    this.heap.pop();
    this.heapifyDown();
    return item;
  }
  
  heapifyUp() { 
    // (방금 원소가 삽입된)
    // 배열의 마지막 위치(this.heap[this.heap.length - 1])에서부터 위로 탐색해나가며
    // Heap 조건을 유지하도록 하는 작업
    let idx = this.heap.length - 1;
    while (this.hasParent(idx) && this.parent(idx) > this.heap[idx]) { 
      const pIdx = this.getParentIdx(idx);
      this.swap(pIdx, idx);
      idx = pIdx;
    }
  }

  heapifyDown() { 
    // 루트(this.heap[0])에서부터 아래로 탐색해나가며
    // Heap 조건을 유지하도록 하는 작업
    let idx = 0;
    while (this.hasLeftChild(idx)) { 
      let smallerChildIdx = this.getLeftChildIdx(idx);
      if (this.hasRightChild(idx) && (this.rightChild(idx) < this.leftChild(idx))) { 
        smallerChildIdx = this.getRightChildIdx(idx);
      }
      if (this.heap[idx] < this.heap[smallerChildIdx]) break;
      else this.swap(idx, smallerChildIdx);
      idx = smallerChildIdx;
    }
  }

  // 헬퍼 메서드

  getLeftChildIdx(parentIdx) { 
    return 2 * parentIdx + 1;
  }
  getRightChildIdx(parentIdx) { 
    return 2 * parentIdx + 2;
  }
  getParentIdx(childIdx) { 
    return Math.floor((childIdx - 1) / 2);
  }

  hasLeftChild(idx) { 
    return this.getLeftChildIdx(idx) < this.heap.length;
  }
  hasRightChild(idx) { 
    return this.getRightChildIdx(idx) < this.heap.length;
  }
  hasParent(idx) { 
    return this.getParentIdx(idx) >= 0;
  }

  leftChild(idx) { 
    return this.heap[this.getLeftChildIdx(idx)];
  }
  rightChild(idx) { 
    return this.heap[this.getRightChildIdx(idx)];
  }
  parent(idx) { 
    return this.heap[this.getParentIdx(idx)];
  }

  swap(idxA, idxB) { 
    const tmp = this.heap[idxA];
    this.heap[idxA] = this.heap[idxB];
    this.heap[idxB] = tmp;
  }
}
// ========================================================

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2283.txt'),
  output: process.stdout,
});

// [왼쪽 끝점, 오른쪽 끝점] 쌍을 저장하는 배열 lines 
let ln = 0, N = 0, K = 0, lines = [];
// 각 구간의 왼쪽 끝점 중 가장 작은 값인 min_a
// 각 구간의 오른쪽 끝점 중 가장 큰 값인 max_b
let min_a = 1_000_001, max_b = 0;

rl.on("line", (line) => {
  if (ln === 0) {
    [N, K] = line.split(" ").map(Number);
  } else { 
    const [A, B] = line.split(" ").map(Number);
    // min_a, max_b 갱신
    min_a = Math.min(min_a, A);
    max_b = Math.max(max_b, B);
    lines.push([A, B]);
  }
  ln++;
});

rl.on("close", () => {
  // 각 구간들을 맨 앞부터 1) 왼쪽 끝점이 더 작고, 2) 만약 왼쪽 끝점이 같을 경우 오른쪽 끝점이 더 작은게 앞에 오도록 정렬
  lines.sort((a, b) => a[0] > b[0] ? 1 : (a[0] === b[0] ? (
    a[1] > b[1] ? 1 : (a[1] === b[1] ? 0 : -1)
  ) : -1));
  const pq = new PriorityQueue();
  // 임의의 점 i를 포함하는 구간들의 개수 count, 현재 검사중인 구간의 lines에서의 인덱스 pos
  let count = 0, pos = 0;
  // 누적합 저장하는 배열 acc_arr
  // acc_arr[i] : [0, i] 구간을 잘라냈을 때 포함되는 길이의 합
  const acc_arr = new Array(max_b + 1).fill(0);
  // 구간 왼쪽 끝점 중 가장 작은 점 위치부터 시작해 오른쪽 끝점 중 가장 큰 점 위치까지 탐색
  for (let i = min_a; i <= max_b; i++) {
    // 1. lines의 각 구간에 대해, i를 포함하는 구간이라면 카운트
    // 즉, 한 구간의 왼쪽 끝점(lines[pos][0])이 i보다 왼쪽에 있고, 오른쪽 끝점(lines[pos][1])이 i와 겹치는 경우의 구간들을 카운트
    while (pos < lines.length && (i > lines[pos][0]) && i <= lines[pos][1]) { 
      // PQ에는 구간의 오른쪽 끝점을 넣음
      pq.enqueue(lines[pos][1]);
      pos++;
      count++;
    }
    // 2. PQ에서 i보다 작은 값 있으면 방출하고 count 감소
    // (즉, 카운트 된 구간 중 i 왼쪽에 있는 구간들을 제외)
    while (count > 0 && pq.peek() < i) { 
      pq.dequeue();
      count--;
    }
    // 누적합 배열에 현재 카운트된 구간 + 이전 값 저장
    acc_arr[i] = (i <= 0 ? 0 : acc_arr[i - 1]) + count;
  }
  // 스위핑을 위한 좌, 우 index
  let left = 0, right = 1;
  while (right <= max_b) { 
    // [left, right] 내부의 구간들 길이 합 v
    const v = acc_arr[right] - acc_arr[left];
    if (v === K) {
      // v가 정답인 경우 출력 후 종료
      console.log(left + " " + right);
      return;
    } else if (v < K) {
      // v가 K보다 작은 경우, right를 전진해 구간 확장
      right++;
    } else { 
      // v가 K보다 큰 경우, left를 전진해 구간 축소
      left++;
      // 만약 left가 right와 같아진 경우, right 전진
      if (left === right) right++;
    }
  }
  // 위 탐색에서 조건 만족하는 A, B 찾지 못한 경우 "0 0" 출력
  console.log("0 0");
});
