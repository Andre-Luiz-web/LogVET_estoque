package Tela.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane; // Para exibir mensagens de erro

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/logivet"; 
    private static final String USER = "root"; // Seu usuário do MySQL
    private static final String PASSWORD = "AndreLps2005"; // Sua senha do MySQL

    
    public static Connection getConnection() {
        Connection connection = null;
        try {
          Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } 
        catch (ClassNotFoundException e) {
            
            JOptionPane.showMessageDialog(null, "Driver JDBC do MySQL não encontrado. Adicione o JAR ao seu projeto.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime o stack trace para depuração
            } 
        catch (SQLException e) {
            
// Captura erros relacionados à conexão SQL (usuário/senha errados, banco não existe, etc.)
            
        	JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage() + "\nVerifique as credenciais e a URL.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime o stack trace para depuração
            }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            
        	try {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada.");
           
        	} catch (SQLException e) {
                
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(); // Imprime o stack trace para depuração
            }
        }
    }

    // Método main para testar a conexão separadamente
    
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        
        if (conn != null) {
            System.out.println("Teste de conexão bem-sucedido!");
            DatabaseConnection.closeConnection(conn);
        
        } else {
            System.out.println("Falha no teste de conexão.");
        }
    }
}