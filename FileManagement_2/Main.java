import java.util.*;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tip_system = "档案系统";
		String tip_menu = "请选择菜单:";
		String tip_exit = "系统退出，谢谢使用！";
		String infos = "****欢迎进入" + tip_system + "****\n\t       " +
				"1.登录\n\t       2.退出\n"  +
                                "*************************";
		String name,password;
		
		System.out.println (infos);
		System.out.println (tip_menu);
		
		//用户登录
		Scanner scanner = new Scanner (System.in);
		
		String input = null;
		while (true)
		{
			input = scanner.next().trim();
			if (!(input).matches ("1|2")) {
				System.err.print (tip_menu);
			}else {
				int nextInt = Integer.parseInt (input);
				switch (nextInt) {
				case 1://登录
					System.out.println("请输入用户名：");
					name = scanner.next();
					System.out.println("请输入口令：");
					password = scanner.next();
					
                                        User user = null;
                                        try{
                                            user = DataProcessing.search(name, password);
                                        }catch(SQLException e){
                                            
                                        }
                                        
					if (user == null)
						System.out.println("用户名口令错误");
					else
						user.showMenu();
					
					break;
                                case 2://退出
                                        System.out.println(tip_exit);
                                        System.exit(0);
                                        break;	
				}
			}
		}
	}
}
