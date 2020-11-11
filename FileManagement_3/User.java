import java.sql.SQLException;
import java.io.IOException;
import java.io.*;
import java.util.Enumeration;


public abstract class User {
	private String name;
	private String password;
	private String role;
	String uploadpath = "d://desktop//up//";
        String downloadpath = "d://desktop//down//";
        
	User(String name,String password,String role){
            this.name=name;
            this.password=password;
            this.role=role;				
	}
	
	public boolean changeSelfInfo(String password) throws SQLException{
            //写用户信息到存储
            if (DataProcessing.updateUser(name, password, role)){
		this.password=password;
		System.out.println("修改成功");
		return true;
            }else
		return false;
	}
	
	public boolean downloadFile(String ID) throws SQLException,IOException{
            Doc doc = DataProcessing.searchDoc(ID);
            
            if (doc == null)
                return false;
            File up = new File(uploadpath + doc.getFilename());
            File down = new File(downloadpath + doc.getFilename());
            if(!down.exists()){
                down.createNewFile();
            }
            byte[] buf = new byte[1024];
            int len = 0;
            FileInputStream fis = new FileInputStream(up);
            FileOutputStream fos = new FileOutputStream(down);
            while((len = fis.read(buf)) != -1) {		
		fos.write(buf, 0, len);
            }  
            fis.close();
            fos.close();
            return true;
	}
	
	public void showFileList() throws SQLException{
            Enumeration<Doc> e = DataProcessing.getAllDocs();
            Doc doc;
            while(e.hasMoreElements() ){
                doc = e.nextElement();
                System.out.println("ID: "+doc.getID()+"\t Creator: "+doc.getCreator());
                System.out.println("Description: "+doc.getDescription());
            }
	}
	
	public abstract void showMenu();
	
	public void exitSystem(){
            System.out.println("系统退出, 谢谢使用 !");
            System.exit(0);
	}

	public String getName() {
            return name;
	}

	public void setName(String name) {
            this.name = name;
	}

	public String getPassword() {
            return password;
	}

	public void setPassword(String password) {
            this.password = password;
	}

	public String getRole() {
            return role;
	}

	public void setRole(String role) {
            this.role = role;
	}
	

}
