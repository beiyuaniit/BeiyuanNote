// 本题为考试单行多行输入输出规范示例，无需提交，不计分。

import java.util.*;

public class Main {
    static int max = 1000;//别Integer.MAX_VALUE  相加后为负数

    public static void main(String[] args) {

        int n = 6;
//        int dist[][]={
//               {0,2,3,6,max,max},
//               {2,0,max,max,4,6},
//               {3,max,0,1,max,max},
//               {6,max,1,0,1,3},
//               {max,4,max,1,0,max},
//               {max,6,max,3,max,0}
//        };
        int dist[][] = {
                {0, 3, 1, max, max, max},
                {3, 0, max, max, max, 2},
                {1, max, 0, 9, max, max},
                {max, max, 9, 0, 2, max},
                {max, max, max, 2, 0, 1},
                {max, 2, max, max, 1, 0}
        };

        //Floyd求所有的最短路径    可求负边  ,判断负环
        for (int l = 1; l < n; l++) {   //长度不超过l的边
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    for (int k = 0; k < n; k++) {

                        //经过k到达j
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);

                    }

                }
            }
        }

        System.out.println("Floyd:");
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(dist[i]));
        }

        /*
        [0, 2, 3, 4, 5, 7]
        [2, 0, 5, 5, 4, 6]
        [3, 5, 0, 1, 2, 4]
        [4, 5, 1, 0, 1, 3]
        [5, 4, 2, 1, 0, 4]
        [7, 6, 4, 3, 4, 0]
         */

        //Bellman-Ford求A（单源）最短路径     可求负边  ,判断负环
        //边数组
        int[][] dist1 = new int[][]{{0, 1, 3}, {0, 2, 1}, {1, 5, 2}, {5, 4, 1}, {2, 3, 10}, {3, 4, 2}};//第三个是权值
        int[] pathA = new int[n];
        int inf = 1000;   //无穷大
        Arrays.fill(pathA, inf);
        pathA[0] = 0;


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int pre = dist1[j][0];  //边的源头
                int next = dist1[j][1];  //边的目的地
                int val = dist1[j][2]; //  权值
                if (pathA[pre] != inf) {
                    // 已经可达
                    pathA[next] = Math.min(pathA[next], pathA[pre] + val);
                }
                //双向边，再判断一次，有向图就不用了
                pre = dist1[j][1];
                next = dist1[j][0];
                if (pathA[pre] != inf) {
                    // 已经可达
                    pathA[next] = Math.min(pathA[next], pathA[pre] + val);
                }
            }
        }

        System.out.println("Bellman-Ford:");
        System.out.println(Arrays.toString(pathA));
    }
}