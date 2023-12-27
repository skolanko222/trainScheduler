package PostgresSQLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


public class ConsolConnection {
    public ConsolConnection(PostgresSQLConnection c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            if(!c.checkConnection()) {
                System.out.println("Enter username: ");
                String username = br.readLine();
                System.out.println("Enter password: ");
                String password = br.readLine();
                if (c.forwardConnect(2138, "pascal.fis.agh.edu.pl", 5432, username, password)) {
                    System.out.println("Connection established");
                    break;
                } else {
                    System.out.println("Connection failed");
                }
            }
            else {
                System.out.println("Connection already established");
                break;
            }
        }
        while (true) {
            System.out.println("Enter command: ");
            String command = br.readLine();
            if (command.equals("exit")) {
                c.close();
                break;
            }
            try {
                ResultSet rs = c.executeCommand(command);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                int columnsNumber = resultSetMetaData.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + resultSetMetaData.getColumnName(i));
                    }
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        System.out.println("Connection closed");
        System.exit(0);
    }
}