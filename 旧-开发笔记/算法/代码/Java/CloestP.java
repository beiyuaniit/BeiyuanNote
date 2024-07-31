/**
 * @author: beiyuan
 * @className: CloestP
 * @date: 2022/3/28  21:47
 */
public class CloestP {
    public static void main(String[] args) {
        //有些算法总是想得太理想，不考虑过程开销
        //2的n次方个点
        double []arrx={2,1,3,4};
        double []arry={19,240,30,40};

        System.out.println(dist(arrx,arry,0,3,4));

    }

    public static double  dist(double []arrx,double []arry,int low ,int high,int size){
        if(size==2){
            return xToy(arrx,arry,low,high);
        }

        int s=size/2;
        double dist1=dist(arrx,arry,low,low+s-1,s);//左边最小
        double dist2=dist(arrx,arry,low+s,high,s);//右边最小
        double Min=dist1;
        if(Min>dist2){
            Min=dist2;
        }
        double result=combine(arrx,arry,low,high,s,Min);//结合后最小

        return result;
    }

    //计算2点距离
    public static double xToy(double []arrx,double []arry,int i,int j){//第i和第j个点的距离
        return Math.sqrt(Math.pow(arrx[i]-arrx[j],2)+Math.pow(arry[i]-arry[j],2));
    }

    //还原为原问题
    public static double combine(double []arrx,double[]arry,int low,int high,int s,double Min){
        //左边与右边
        int right=low+s;
        int i=low+s-1;
        /**
         * i>=low    不越界
         * arrx[i]>(arrx[low+s-1]+arrx[low+s])/2-Min       x轴小于Min
         *并没有限制y轴的
         */
        while (i>=low && arrx[i]>(arrx[low+s-1]+arrx[low+s])/2-Min){
            while (right<=high && arrx[right]<Min+(arrx[low+s-1]+arrx[low+s])/2){
                if(xToy(arrx,arry,i,right)<Min){
                    Min=xToy(arrx,arry,i,right);
                }
                right++;
            }
            i--;
        }
        return Min;
    }

}
