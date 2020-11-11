import java.util.Enumeration;
import java.util.Scanner;


public class Administrator extends User {
	
	public Administrator(String name,String password,String role){
		super(name,password,role);
	}
        
	//1.修改用户
	public boolean changeUserInfo(String name, String password, String role){
		if (DataProcessing.update(name, password, role)){
			setName(name);
			setPassword(password);
			setRole(role);
			System.out.println("修改成功\n");
			return true;
		}else
			return false;
	}
	
	//2.删除用户
	public void delUser(String name){
		DataProcessing.delete(name);
		System.out.println("删除成功\n");
	}
	
        //3.新增用户
	public void addUser(String name,String password,String role) {
		 DataProcessing.insert(name,password,role);
	}
	
        //4.列出用户
	public void listUser() {
		Enumeration<User> e = null;
                e = DataProcessing.getAllUser();
		User user;
		while(e.hasMoreElements()) {
			user = e.nextElement();
			System.out.println("用户名:" + user.getName() + " 密码:" + 
		user.getPassword() + " 角色:" + user.getRole());
		}
	}
	//5.下载文件——6.文件列表——7.修改本人密码——8.退出
	public void showMenu() {
		String tip_administrator = "******欢迎来到系统管理菜单******\n\t" + 
                                           "1.修改用户\n\t" +
                                           "2.删除用户\n\t" + 
                                           "3.新增用户\n\t" + 
                                           "4.列出用户\n\t" + 
                                           "5.下载文件\n\t" +
                                           "6.文件列表\n\t" + 
                                           "7.修改（本人）密码\n\t" + 
                                           "8.退出\n" + 
                                           "***************************";
		String tip_menu = "请选择菜单：";
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println(tip_administrator);
			System.out.println(tip_menu);
			
			int option;
			option = in.nextInt();
			
			switch(option) {
			case 1:
				System.out.println("修改用户");
				System.out.println("请输入用户名:");
				String name1,password,role;
				name1 = in.next();
				System.out.println("请输入密码:");
				password = in.next();
				System.out.println("请输入角色:");
				role = in.next();
				break;
			case 2:
				System.out.println("删除用户");
				System.out.println("请输入用户名:");
				String name2;
				name2 = in.next();
				delUser(name2);
				System.out.println("删除成功!");
                                break;
			case 3:
				System.out.println("新增用户");
				String name3,password1,role1;
				System.out.println("请输入用户名:");
				name3 = in.next();
				System.out.println("请输入密码:");
				password1 = in.next();
				System.out.println("请输入角色:");
				role1 = in.next();
				addUser(name3,password1,role1);
				break;
			case 4:
				System.out.println("列出用户:");
				listUser();
				break;
			case 8:
				exitSystem();
				break;
			}
		}
		
	}
}
