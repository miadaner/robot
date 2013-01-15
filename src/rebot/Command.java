/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rebot;

import java.util.Objects;

/**
 *
 * @author migle
 */
public class Command {

    public enum TYPE {

        SQL, SHELL
    };
    private String threadID;
    private String sender;
    private String cmd;
    private String result;
    private TYPE type;

    public Command(String threadID, String sender, String cmd) {
        this(threadID, sender, cmd, TYPE.SQL);
    }

    public Command(String threadID, String sender, String cmd, TYPE type) {
        this.threadID = threadID;
        this.sender = sender;
        this.cmd = cmd;
        this.type = type;
    }

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.threadID);
        hash = 97 * hash + Objects.hashCode(this.sender);
        hash = 97 * hash + Objects.hashCode(this.cmd);
        hash = 97 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Command other = (Command) obj;
        if (!Objects.equals(this.threadID, other.threadID)) {
            return false;
        }
        if (!Objects.equals(this.sender, other.sender)) {
            return false;
        }
        if (!Objects.equals(this.cmd, other.cmd)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Command{" + "threadID=" + threadID + ", sender=" + sender + ", cmd=" + cmd + ", result=" + result + '}';
    }
}
