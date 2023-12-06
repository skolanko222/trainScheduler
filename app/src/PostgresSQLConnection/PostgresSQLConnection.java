package PostgresSQLConnection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class PostgresSQLConnection {
    private Connection connection = null;
    final String urlParam = "jdbc:postgresql://";
    final String localHost = "localhost";

    final private Properties props;

    public PostgresSQLConnection(String username, String password) {

        props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
//      props.setProperty("ssl", "true");

    }

    public boolean forwardConnect(int localPort, String remoteHost, int remotePort, String username, String password) {
        try {
            forwardPort(localPort, remoteHost, remotePort, username, password);
            String fullURL = urlParam + localHost + ":" + localPort + "/";
            this.connection = DriverManager.getConnection(fullURL, props);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet executeCommand(String command) throws SQLException{
            Statement statement = connection.createStatement();
            return statement.executeQuery(command);
    }

    private void forwardPort(int localPort, String remoteHost, int remotePort, String username, String password) {
        System.out.println("Forwarding port " + localPort + " to " + remoteHost + ":" + remotePort);
        Session session;
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        try {
            session = new JSch().getSession(username, "pascal.fis.agh.edu.pl", 22);
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            session.setPortForwardingL(localPort, remoteHost, remotePort);
            System.out.println("Port Forwarded");
        } catch (JSchException e) {
                System.out.println("ERROR: while forwarding port:" +e.getMessage());
        }
    }
    public boolean checkConnection() {
        if(connection == null) {
            System.out.println("Connection is null");
            return false;
        }
        try {
            connection.createStatement().executeQuery("SELECT 1;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
