import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class TestSSH {

	/**
	 * @param args
	 * @throws JSchException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws JSchException, IOException, InterruptedException {
		JSch jsch = new JSch();
		Session session = jsch.getSession("ipd", "135.191.27.50");
		session.setPassword("ipd_2012");
		session.setUserInfo(new MyUserInfo());
		session.connect();
		
		Channel channel = session.openChannel("shell");
		
		PipedOutputStream pout = new PipedOutputStream();
		channel.setInputStream(new PipedInputStream(pout));
		
		
		PipedInputStream rin = new PipedInputStream();
		channel.setOutputStream(new PipedOutputStream(rin));
		BufferedReader br = new BufferedReader(new InputStreamReader(rin));
		
		
		
		
		pout.write("ls -l\r".getBytes());
		Thread.sleep(1000);
		pout.write("ls -l|wc -l\r".getBytes());
		channel.connect(3*1000);
		
		while(true){
			System.out.println(br.readLine());
			Thread.sleep(1000);
		}
		
		
		
//		Channel channel=session.openChannel("exec");
//		((ChannelExec)channel).setCommand("ls -l");
//		InputStream in=channel.getInputStream();
//	     channel.connect();
//	      
//		byte[] tmp=new byte[1024];
//	      while(true){
//	        while(in.available()>0){
//	          int i=in.read(tmp, 0, 1024);
//	          if(i<0)break;
//	          System.out.print(new String(tmp, 0, i));
//	        }
//	        if(channel.isClosed()){
//	          System.out.println("exit-status: "+channel.getExitStatus());
//	          break;
//	        }
//	        try{Thread.sleep(1000);}catch(Exception ee){}
//	      }
	}
}
