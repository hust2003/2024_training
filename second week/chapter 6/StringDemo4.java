import java.util.Scanner;
 public class StringDemo4{
 	public static void main(String[] args) {
 		//定义两个变量记录正确的用户名和密码
 		String rightUsername="zhangsan";
 		String rightPassword="123456";

 		//键盘输入用户名和密码
 		Scanner sc=new Scanner(System.in);
 		System.out.println("请输入用户名");
 		String username=sc.next();//new 出来得到的
 		System.out.println("请输入密码");
 		String password=sc.next();

 		//比较
 		if(username.equals(rightUsername) && password.equals(rightPassword))
 		{
 			System.out.println("用户登录成功");
 		}
 		
 		else{
 			System.out.println("用户登录失败，用户名或者密码错误");
 		}

 		



 	}
 }