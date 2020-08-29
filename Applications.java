    public class Applications implements Expressions{
    Expressions left = null;
    Expressions right = null;
    public void setLeft(Expressions left){
        this.left = left;
    }
    public void setRight(Expressions right){
        this.right = right;
    }
    public String toString(){
        return ("(" + left+ " " + right + ")");       
    }
}
