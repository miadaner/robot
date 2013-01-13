/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rebot;

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
        Command c;
        while (true) {
            try {
                c = (Command) commands.take();
                c.setResult(c.getCmd().length() + "");
                chatManager.getThreadChat(c.getThreadID()).sendMessage(c.toString());
            } catch (XMPPException | InterruptedException ex) {
                Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
}
