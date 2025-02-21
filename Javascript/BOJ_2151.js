// 다익스트라를 위한 PQ
class PriorityQueue {
  constructor() {
      this.heap = [];
  }

  // Helper Methods
  getLeftChildIndex(parentIndex) {
      return 2 * parentIndex + 1;
  }

  getRightChildIndex(parentIndex) {
      return 2 * parentIndex + 2;
  }

  getParentIndex(childIndex) {
      return Math.floor((childIndex - 1) / 2);
  }

  hasLeftChild(index) {
      return this.getLeftChildIndex(index)
          < this.heap.length;
  }

  hasRightChild(index) {
      return this.getRightChildIndex(index)
          < this.heap.length;
  }

  hasParent(index) {
      return this.getParentIndex(index) >= 0;
  }

  leftChild(index) {
      return this.heap[this.getLeftChildIndex(index)];
  }

  rightChild(index) {
      return this.heap[this.getRightChildIndex(index)];
  }

  parent(index) {
      return this.heap[this.getParentIndex(index)];
  }

  swap(indexOne, indexTwo) {
      const temp = this.heap[indexOne];
      this.heap[indexOne] = this.heap[indexTwo];
      this.heap[indexTwo] = temp;
  }

  peek() {
      if (this.heap.length === 0) {
          return null;
      }
      return this.heap[0];
  }
  size() { 
      return this.heap.length;
  }

  // remove 호출시 가장 높은 우선순위를 가지는 top 요소를 삭제하고
  // heapifyDown를 호출함
  remove() {
      if (this.heap.length === 0) {
          return null;
      }
      const item = this.heap[0];
      this.heap[0] = this.heap[this.heap.length - 1];
      this.heap.pop();
      this.heapifyDown();
      return item;
  }

  add(item) {
      this.heap.push(item);
      this.heapifyUp();
  }

  heapifyUp() {
      let index = this.heap.length - 1;
      while (this.hasParent(index) && this.parent(index)[0]
          > this.heap[index][0]) {
          this.swap(this.getParentIndex(index), index);
          index = this.getParentIndex(index);
      }
  }

  heapifyDown() {
      let index = 0;
      while (this.hasLeftChild(index)) {
          let smallerChildIndex = this.getLeftChildIndex(index);
          if (this.hasRightChild(index) && this.rightChild(index)[0]
              < this.leftChild(index)[0]) {
              smallerChildIndex = this.getRightChildIndex(index);
          }
          if (this.heap[index][0] < this.heap[smallerChildIndex][0]) {
              break;
          } else {
              this.swap(index, smallerChildIndex);
          }
          index = smallerChildIndex;
      }
  }
}

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2151.txt'),
  output: process.stdout,
})

let ln = 0;
let N = 0;
let map = [], MINIMUM_DOORS_COUNT = Number.MAX_SAFE_INTEGER;
const doors = [], mirrors = [];
// [동, 남, 서, 북]
const dr = [0, 1, 0, -1];
const dc = [1, 0, -1, 0];

rl.on('line', (line) => {
  if (ln === 0) {
    N = Number(line);
    map = new Array(N).fill().map(_ => new Array(N).fill(0));
  } else { 
    const splited = line.split("");
    for (let i = 0; i < splited.length; i++) { 
      switch (splited[i]) { 
        case "*":
          // 벽
          map[ln - 1][i] = 1;
          break;
        case "#":
          // 문
          map[ln - 1][i] = 2 + doors.length;
          doors.push([ln - 1, i]);
          break;
        case "!":
          // 거울을 놓을 수 있는 곳
          map[ln - 1][i] = mirrors.length + 4;
          mirrors.push([ln - 1, i]);
          break;
        case ".":
          // 빛이 통과할 수 있는 곳
          map[ln - 1][i] = 0;
          break;
      }
    }
  }
  ln++;
});

function connect_graph(r, c, edge_list) { 
  const num = map[r][c] - 2;
  for (let i = 0; i < 4; i++) { 
    let mr = r + dr[i];
    let mc = c + dc[i];

    while (mr >= 0 && mc >= 0 && mr < N && mc < N) { 

      if (map[mr][mc] === 1) {
        break;
      } else if (map[mr][mc] > 1) { 
        edge_list[num].push(map[mr][mc] - 2);
      }
      mr += dr[i];
      mc += dc[i];
    }
  }
}

function dijkstra(cost, vertex_list) {
  const visited = new Array(cost.length).fill(false);
  cost[0] = 0;
  visited[0] = true;
  const pq = new PriorityQueue();
  pq.add([0, 0]);
  while (pq.size() > 0) { 
    const node = pq.remove();
    console.log(node);
    
    vertex_list[node[1]].forEach(e => { 
      if (visited[e]) return;
      if (cost[e] > cost[node[1]] + 1) { 
        cost[e] = cost[node[1]] + 1;
        pq.add([cost[e], e]);
      }
    })
  }
}

rl.on('close', () => {
  // 각 문, 거울에 대해 [동/서/남/북] 일직선 상에 놓인 다른 문, 거울을 기록한 인접 리스트
  // vertext_list[0], [1]는 첫 번째, 두 번째 문에 대한 인접 리스트이며, 그 이후는 거울들에 대한 인접 리스트
  const vertex_list = new Array(mirrors.length + 2).fill().map(_ => []);
  //첫 번째 문(doors[0]) 에서 각 문, 거울까지 거쳐야 하는 거울의 갯수 기록하는 비용 배열
  const cost = new Array(vertex_list.length).fill(Number.MAX_SAFE_INTEGER);
  // 각 문에 대한 인접 리스트 기록
  for (let i = 0; i < 2; i++) { 
    connect_graph(...doors[i], vertex_list);
  }
  // 각 거울에 대한 인접 리스트 기록
  for (let i = 0; i < mirrors.length; i++) { 
    connect_graph(...mirrors[i], vertex_list);
  }
  // 다익스트라를 통해 각 문, 거울에 대한 최소 비용 계산
  dijkstra(cost, vertex_list);

  // 두 번째 문에 대한 최소 비용 - 1 (cost[1]에 기록된 거울 갯수에는 "두 번째 문"도 거울로 계산되어 있기 때문)
  console.log(cost[1]-1);
});