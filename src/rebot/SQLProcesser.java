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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migle
 */
public class SQLProcesser {

    static {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String getReplay(Command cmd) throws Exception {
        Connection dbcon = null;
        StringBuilder rs = new StringBuilder();
        try {
            dbcon = DriverManager.getConnection("jdbc:db2://10.228.8.52:50001/QHFTDB", "qhftinst", "Emtf2010");
            Statement st = dbcon.createStatement();
            boolean hasResult = st.execute(cmd.getCmd());
            if (hasResult) {
                ResultSet resultSet = st.getResultSet();
                //记录数据较多的时候是不是应该以文件形式发送
                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        rs.append(resultSet.getString(i));
                        rs.append("\t");
                    }
                    rs.append("\n");
                }
            } else {
                rs.append("受影响行数:");
                rs.append(st.getUpdateCount());
            }
        } catch (SQLException ex) {
            rs.append("SQL执行出错：");
            rs.append(ex.getMessage());
            throw ex;
        } finally {
            try {
                dbcon.close();
            } catch (SQLException ex) {
                Logger.getLogger(Processer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs.toString();
    }
}
