/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rebot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPException;

/**
 *
 * @author migle
 */
public class Processer implements Runnable {

    private final ArrayBlockingQueue commands;
    private final ChatManager chatManager;

    public Processer(ArrayBlockingQueue commands, ChatManager chatManager) {
        this.commands = commands;
        this.chatManager = chatManager;
    }

    @Override
    public void run() {
        Command c = null;
        while (true) {
            try {
                c = (Command) commands.take();
                if (c.getType() == Command.TYPE.SQL) {
                    c.setResult(new SQLProcesser().getReplay(c));
                }
                chatManager.getThreadChat(c.getThreadID()).sendMessage(c.toString());
            }  catch (InterruptedException ex) {
                Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                commands.add(c);
            }
        }
    }

    public static void main(String[] args) {
        Connection dbcon = null;
        try {
            dbcon = DriverManager.getConnection("jdbc:db2://10.228.8.52:50001/QHFTDB", "qhftinst", "Emtf2010");
        } catch (SQLException ex) {
            Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
        }

        Statement st;
        try {
            st = dbcon.createStatement();
            st.execute("select count(1) from aips.user_user");
            ResultSet resultSet = st.getResultSet();
            resultSet.next();
            System.out.println(resultSet.getString(1));
        } catch (SQLException ex) {
            System.out.println("执行命令出错");
        }
    }
}
