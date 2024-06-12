import java util.ArrayList;
public class ArrayTest4{
	public static void main(String[] args) {
		ArrayList<Student> list=new ArrayList<>();
		Student s1=new Student(name:"zhangsan",age:23);
        Student s2=new Student(name:"lisi",age:33);
        Student s3=new Student(name:"wangwu",age:24);
        list.add(s1);	
        list.add(s2);	
        list.add(s3);	
        for (int i=0; i<list.size(); i++) {
        	Student stu=list.get(i);
        	System.out.println(stu.getName()+","+stu.getAge());
        }
        }
}