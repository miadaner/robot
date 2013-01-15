/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rebot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author migle
 */
public class Robot {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XMPPException, IOException {
        ConnectionConfiguration conf = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
        final ArrayBlockingQueue<Command> commands = new ArrayBlockingQueue<Command>(20);
        final Executor ex =  Executors.newFixedThreadPool(2);
        
        conf.setSASLAuthenticationEnabled(false);
        final XMPPConnection connection = new XMPPConnection(conf);
        connection.connect();
        connection.login("qhbirobot", "iamrobot");

//        connection.getRoster().addRosterListener(new RosterListener() {
//            @Override
//            public void entriesAdded(Collection<String> addresses) {
//            }
//
//            @Override
//            public void entriesUpdated(Collection<String> addresses) {
//            }
//
//            @Override
//            public void entriesDeleted(Collection<String> addresses) {
//            }
//
//            @Override
//            public void presenceChanged(Presence presence) {
//                System.out.println(
//                        "Presence changed: "
//                        + presence.getFrom() + ":"
//                        + presence.getStatus() + ":"
//                        + presence.getType() + ":"
//                        + presence.getMode());
//            }
//        });
        
        connection.getChatManager().addChatListener(new ChatManagerListener() {
           
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                
               ex.execute(  new Processer(commands,connection.getChatManager()));
               ex.execute(  new Processer(commands,connection.getChatManager()));
             
                chat.addMessageListener(new MessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
//                        System.out.println(message.getBody());
//                        System.out.println(chat.getThreadID());
                        //a
                        /*  :h 帮助信息
                         *  :shell shell模式
                         *  :sql sql模式
                         *  :master 管理模式
                         */
                        if(!message.getBody().startsWith(":")){
                             commands.add(new Command(chat.getThreadID(), chat.getParticipant(), message.getBody()));
                        }
                        
//                        try {
//                            chat.sendMessage(message.getBody() + ":" + chat.getThreadID());
//                        } catch (XMPPException ex) {
//                            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                });
            }
        });

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"exit".equals(reader.readLine())) { //do something
            System.out.println(commands.size());
        }
        
        connection.disconnect();

    }
}
