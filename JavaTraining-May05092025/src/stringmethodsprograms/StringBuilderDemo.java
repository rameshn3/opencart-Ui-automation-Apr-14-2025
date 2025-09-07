package stringmethodsprograms;

public class StringBufferDemo{
    public static void main(String[] args){
        StringBuffer sb = new StringBuffer("java");
        System.out.println("sb value is:"+sb);
        //append()
        sb.append("webdriver");
        System.out.println("sb value after append() is:"+sb);
        //insert()
        sb.insert(4," ");
        System.out.println("sb value after insert() is:"+sb);
    //replace()
        sb.replace(0,4,"ruby");
        System.out.println("sb value after replace() is:"+sb);

        //delete():
        sb.delete(4,14);
        System.out.println("sb value after delete() is:"+sb);
        //reverse():
        sb.reverse();
        System.out.println("sb value after reverse() is:"+sb);
    }
}
