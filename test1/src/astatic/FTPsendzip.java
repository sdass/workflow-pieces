package astatic;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPFile;

// ftp example https://stackoverflow.com/questions/6527664/how-do-you-upload-a-file-to-an-ftp-server
// https://stackoverflow.com/questions/6651158/apache-commons-ftp-problems
public class FTPsendzip {
	
	public static void main(String[] args){
	
		zipFileFtp();
		//ftpListcommand();
		
	}

	public static void zipFileFtp(){ //works
		String host = "";
		String username = "";
		String password = "";
		Properties prop = new Properties();
		try(InputStream in = new FileInputStream("c:/apps/appdata/my/propertyfile.txt")){ //)  //C:\apps\appdata\my
			prop.load(in);
			host = prop.getProperty("host");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		FTPClient client = new FTPClient();
		FileInputStream fis = null;

		
		try(BufferedInputStream fin = new BufferedInputStream (new FileInputStream("C:/Temp/destinationZip/bundle.zip"))){
			System.out.println("fin =" + fin != null);
			client.connect(host);
			client.login(username, password);
			client.setFileType(FTP.BINARY_FILE_TYPE);
	        client.enterLocalPassiveMode();        

			client.storeFile("bundle.zip", fin);
			

			client.logout();
			client.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				client.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	
	
	public static void ftpListcommand(){
		String host = "";
		String username = "";
		String password = "";
		Properties prop = new Properties();
		try(InputStream in = new FileInputStream("c:/apps/appdata/my/propertyfile.txt")){ //)  //C:\apps\appdata\my
			prop.load(in);
			host = prop.getProperty("host");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			//System.out.println(host + " " + username + " " + password);
		}catch (Exception e) {
			e.printStackTrace();
		}
		FTPClient client = new FTPClient();
		FileInputStream fis = null;
		try{
			InetAddress remotehostent = InetAddress.getByName(host);
			InetAddress localInetAddress = InetAddress.getByName("localhost");
			int ftpserviceport = 21;
			
			//client.connect(remotehostent, ftpserviceport, localInetAddress, 1030);
			client.connect(host);
			client.login(username, password);
			//client.doCommand(command, params)
			FTPFile[] files = client.listFiles();
			Arrays.asList(files).forEach(x->System.out.println(x.getName()));
			client.logout();
			client.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				client.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	public static void oneFileFtp(){
		FTPClient client = new FTPClient();
		FileInputStream fis = null;
		try{
			client.connect("ftp.domain.com");
			client.login("admin", "secret");
			String filename = "Touch.dat"; //create a file
			fis =  new FileInputStream(filename); 
			client.storeFile(filename, fis); //remote filename will be this filename
		}catch(Exception e){
			e.printStackTrace();
		}
		//client.logout();
		
	}

}
