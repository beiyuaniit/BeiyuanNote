/**
 * @author: beiyuan
 * @className: SortK
 * @date: 2022/3/28  20:30
 */
public class SortK {
    public static void main(String[] args) {
        int []arr={3,1,2,4,5};
        System.out.println(sort(arr,0,4,3));
    }

    //java是引用传递，arr是同一个，所以直接把第k个元素放好位置即可
    public static int  sort(int []arr,int low,int high,int k) {

        if (low == high)//因为有了if判断，选择满足的一个递归。所以用==即可
            return arr[low];
        int base = arr[low];
        int i = low;
        int j = high;
        boolean isRight = true;//从右边
        while (i != j) {
            if (isRight) {
                if (arr[j] < base) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                    isRight = false;
                    i++;
                    continue;
                }
                j--;

            }
            if (!isRight) {
                if (arr[i] > base) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                    isRight = true;
                    j--;
                    continue;
                }
                i++;
            }

        }

        if (j == k - 1) {
            return arr[j];
        }
        else if(j<k-1){
            return sort(arr,j+1,high,k);
        }
        else {
            return sort(arr,low,j-1,k);
        }

    }
}
