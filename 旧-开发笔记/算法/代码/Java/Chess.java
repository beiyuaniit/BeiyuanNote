/**
 * @author: beiyuan
 * @className: Chess
 * @date: 2022/3/27  10:43
 */
public class Chess {
    public static void main(String[] args) {
        ChessBoard(0,0,3,2,8);
        for(int i=0;i<boardSize;i++){
            for(int num:chessBoard[i]){
                System.out.printf("%4d",num);
            }
            System.out.println("\n");
        }

    }

    private static int boardSize=8;
    private static int[][]  chessBoard=new int[boardSize][boardSize];//成员变量，默认都初始化为0
    private static int title=0;
    public static void ChessBoard(int left,int top,int i,int j,int size){//左上角加size可以确定整个棋盘
        if(size==1){
            return;
        }

        int s=size/2;
        int num=++title;

        //左上角
        if(left+s>i&&top+s>j){
            ChessBoard(left,top,i,j,s);
        }else {
            chessBoard[left+s-1][top+s-1]=num;
            ChessBoard(left,top,left+s-1,top+s-1,s);
        }

        //右上角
        if(left+s<=i&&top+s>j){
            ChessBoard(left+s,top,i,j,s);
        }else {
            chessBoard[left+s][top+s-1]=num;
            ChessBoard(left+s,top,left+s,top+s-1,s);
        }

        //左下角
        if(left+s>i&&top+s<=j){
            ChessBoard(left,top+s,i,j,s);
        }else {
            chessBoard[left+s-1][top+s]=num;
            ChessBoard(left,top+s,left+s-1,top+s,s);
        }

        if(left+s<=i&&top+s<=j){
            ChessBoard(left+s,top+s,i,j,s);
        }else {
            chessBoard[left+s][top+s]=num;
            ChessBoard(left+s,top+s,left+s,top+s,s);
        }
    }
}
