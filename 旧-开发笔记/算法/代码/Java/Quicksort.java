import java.util.Arrays;

/**
 * @author: beiyuan
 * @className: First
 * @date: 2022/3/26  23:22
 */
public class Quicksort {
    public static void main(String[] args) {
        int []A={3,1,2,4,5};
        sort(A,0,A.length-1);
        System.out.println(Arrays.toString(A));
    }

    public static void sort(int []arr,int low ,int high){
        if(arr.length==0){
            return;
        }
        if(low>=high)//因为一直推向左边，i和j会和low相等。sort(arr,low,j-1)就会出错
            //没有if判断而直接递归
            return;
        int base=arr[low];
        int i=low;
        int j=high;
        boolean isRight=true;//从右边
        while (i!=j){
            if(isRight){
                if(arr[j]<base){
                    int tmp=arr[j];
                    arr[j]=arr[i];
                    arr[i]=tmp;
                    isRight=false;
                    i++;
                    continue;
                }
                j--;

            }
            if(!isRight){
                if(arr[i]>base) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                    isRight=true;
                    j--;
                    continue;
                }
                i++;
            }

        }
        sort(arr,j+1,high);
        sort(arr,low,j-1);
    }





}
