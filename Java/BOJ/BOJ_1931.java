package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1931. 회의실 배정
public class BOJ_1931 {
    static class Meeting implements Comparable<Meeting>{
        int start, end;
        Meeting(int start, int end){
            this.start = start;
            this.end = end;
        }
        @Override
        public int compareTo(Meeting o) {
            return this.end==o.end ? this.start-o.start : this.end-o.end;
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Meeting[] meetings = new Meeting[N];
        String str[];
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            meetings[i] = new Meeting(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
        }
        Arrays.sort(meetings);
        List<Meeting> list = new ArrayList<Meeting>();
        list.add(meetings[0]);
        for(int i=1; i<N; i++) {
            if(list.get(list.size()-1).end <= meetings[i].start){
                list.add(meetings[i]);
            }
        }
        System.out.println(list.size());
    }
}
