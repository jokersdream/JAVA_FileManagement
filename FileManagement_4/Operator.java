import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class Operator extends User{
	
	public Operator(String name,String password,String role) {
            super(name,password,role);
	}
        
        @Override
        public void GUI(String name){
            MainGUI.GUI("operator", name);
        }
	public void uploadFile(String id, String filepath, String description, String filename) throws IOException,SQLException{
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            DataProcessing.insertDoc(id, filepath, timestamp, description, filename);
            File src = new File(filepath);
            File up = new File(uploadpath + src.getName());
            BufferedWriter fos;
            try ( BufferedReader fis = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(src), "utf-8"))) {
                fos = new BufferedWriter(
                        new FileWriter(up));

                String s;
                s = fis.readLine();
                while (s != null) {
                    fos.write(s + '\n');
                    fos.flush();
                    s = fis.readLine();
                }
            }
            fos.close();
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
				String filename;
				filename = in.next();
				System.out.println("请输入档案号:");
				String id1;
				id1 = in.next();
				System.out.println("请输入文件路径:");
				String filepath;
				filepath = in.next() + '/' + filename;
				System.out.println("请输入档案描述:");
				String description;
				description = in.next();
                                System.out.println("上传文件... ...");
                                try {
                                    uploadFile(id1, filepath, description, filename);
                                } catch (IOException | SQLException e) {
                                    System.out.println("Exception:" + e.getMessage());
                                }
                                System.out.println("上传成功! ");
				break;
			case 2:
				System.out.println("下载文件");
				System.out.println("请输入档案号:");
				String id2;
				id2 = in.next();
				try {
                                    downloadFile(id2);
				} catch (IOException e) {
                                    System.out.println("文件错误");
				} catch (SQLException e) {
                                    System.out.println("SQLException:" + e.getMessage());
                                }
                                System.out.println("下载文件... ...\n" + "下载成功! ");
				break;
			case 5:
				exitSystem();
				break;	
			}
		
		}
		
	}
	
}

