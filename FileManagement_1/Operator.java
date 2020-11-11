import java.util.Scanner;

public class Operator extends User{
	
	public Operator(String name,String password,String role) {
		super(name,password,role);
	}
	
	public void showMenu() {
		String tip_operator = "******欢迎来到档案录入人员菜单******\n\t" + "1.上传文件\n\t" +
				"2.下载文件\n\t" + "3.文件列表\n\t" + "4.修改密码\n\t" + "5.退出\n" +
				"****************************";
		String tip_menu = "请选择菜单：";
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println(tip_operator);
			System.out.println(tip_menu);
			int option;
			option = in.nextInt();
			switch(option) {
			case 1:
				System.out.println("上传文件");
				System.out.println("请输入源文件名:");
				String name;
				name = in.next();
				System.out.println("请输入档案号:");
				String ID;
				ID = in.next();
				System.out.println("请输入档案描述:");
				String describtion;
				describtion = in.next();
                               
                                System.out.println("上传文件... ...\n" + "上传成功! ");
				break;
			case 2:
				System.out.println("下载文件");
				System.out.println("请输入档案号:");
				String filename;
				filename = in.next();
                               
                                System.out.println("下载文件... ...\n" + "下载成功! ");
				break;
			case 5:
				exitSystem();
				break;	
			}
		
		}
		
	}
	
}

