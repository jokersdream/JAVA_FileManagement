import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Administrator extends User {
	
	public Administrator(String name,String password,String role){
		super(name,password,role);
	}
        
        @Override
        public void GUI(String name){
            MainGUI.GUI("Administrator", name);
        }
	
	public boolean changeUserInfo(String name_, String password_, String role_) throws SQLException{
		if (DataProcessing.updateUser(name_, password_, role_)){
			JOptionPane.showMessageDialog(null, "修改成功！");
			return true;
		} else {
                    return false;
                }
	}
	
	
	public boolean delUser(String name) throws SQLException{
            if (DataProcessing.deleteUser(name)){
                JOptionPane.showMessageDialog(null, "修改成功！");
                return true;
            } else {
                return false;
            }
	}
	
	public void addUser(String name,String password,String role) {
		try{
                    DataProcessing.insertUser(name,password,role);
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "数据库错误：" + e.getMessage());
                }
	}
	
	public void listUser() {
		Enumeration<User> e = null;
		try {
			e = DataProcessing.getAllUser();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "数据库错误：" + ex.getMessage());
		}
		User user;
		while(e.hasMoreElements()) {
			user = e.nextElement();
			System.out.println("用户名:" + user.getName() + " 密码:" + 
		user.getPassword() + " 角色:" + user.getRole());
		}
	}
	
	public void showMenu() {
		String tip_administrator = "******欢迎来到系统管理菜单******\n\t" + "1.修改用户\n\t" +
				"2.删除用户\n\t" + "3.新增用户\n\t" + "4.列出用户\n\t" + "5.下载文件\n\t" +
				"6.文件列表\n\t" + "7.修改（本人）密码\n\t" + "8.退出\n" + "***************************";
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
				try {
					if(changeUserInfo(name1,password,role))
						System.out.println("修改成功！");
					else
						System.out.println("修改失败！");
				} catch (SQLException e) {
					System.out.println("数据库错误" + e.getMessage());
				}
				break;
			case 2:
				System.out.println("删除用户");
				System.out.println("请输入用户名:");
				String name2;
				name2 = in.next();
				try {
					delUser(name2);
				} catch (SQLException e1) {
					System.out.println("数据库错误" + e1.getMessage());
				}
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
			case 5:
				System.out.println("下载文件");
				System.out.println("请输入档案号:");
				String id;
				id = in.next();
				try {
					downloadFile(id);
				} catch (IOException e) {
					System.out.println("IOException:" + e.getMessage());
				} catch (SQLException e) {
                                    System.out.println("SQLException:" + e.getMessage());
                                }
				break;
			case 6:
				try {
					showFileList();
				} catch (SQLException e) {
					System.out.println("SQLException:" + e.getMessage());
				}
				break;
			case 7:
				System.out.println("修改(本人)密码");
				System.out.println("请输入新口令:");
				String password2;
				password2 = in.next();
				try {
					changeSelfInfo(password2);
				} catch (SQLException e) {
					System.out.println("SQLException:" + e.getMessage());
				}
				break;
			case 8:
				exitSystem();
				break;
			}
		}
		
	}
}
