/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import static net.sf.jasperreports.engine.data.JRXmlaDataSource.url;

/**
 *
 * @author Usuario
 */
/*
public class SingleConnectionBanco {
  
    private static String url = "jdbc:mysql://localhost:3306/opme?useTimezone=true&serverTimezone=UTC";
	private static String password = "";
	private static String user = "root";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {  // Quando tiver uma isntância vai conectar
		conectar();
	}
	
	private static void conectar() {
		try {
			
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conectou com sucesso!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}

*/
public class SingleConnectionBanco {
  
    // Acesso MySQL TRIADE
    private static String driver = "com.mysql.cj.jdbc.Driver";
    //Informações referentes ao banco de dados
    private static String caminho = "jdbc:mysql://bd.comseder.com.br:3306/opme?useTimezone=true&serverTimezone=UTC";
    //String caminho = "jdbc:mysql://167.249.71.54:3306/opme?useTimezone=true&serverTimezone=UTC";
    private static String usuario = "coop_comseder";
    private static String senha = "@C0mseder2020*";
    private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {  // Quando tiver uma isntância vai conectar
		conectar();
	}
	
	private static void conectar() {
		try {
			
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(caminho, usuario, senha);
				connection.setAutoCommit(false);
				System.out.println("Conectou com sucesso!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}

