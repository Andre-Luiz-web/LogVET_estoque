
package Tela.dao;

import Tela.model.Usuario;
import Tela.database.ConexaoBD; // USANDO A CLASSE DE CONEXÃO DO SEU PROJETO

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ? WHERE ID = ?";
        try (Connection conn = Tela.database.ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean verificarSenhaAtual(int idUsuario, String senhaFornecida) {
        String sql = "SELECT senha FROM usuarios WHERE id = ?";
        try (Connection conn = Tela.database.ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHashArmazenada = rs.getString("senha");
                    return senhaFornecida.equals(senhaHashArmazenada);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar senha atual: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT id, nome, email, senha FROM usuarios WHERE id = ?";
        Usuario usuario = null;
        try (Connection conn = Tela.database.ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    // NOVO MÉTODO: Autentica e retorna o usuário completo
    public Usuario buscarUsuarioPorEmailESenha(String email, String senha) {
        String sql = "SELECT id, nome, email, senha FROM usuarios WHERE email = ? AND senha = ?";
        Usuario usuario = null;
        try (Connection conn = Tela.database.ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha); // CUIDADO: Comparação de senha em texto puro!

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha")); 
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email e senha: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    public static boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (Connection conn = Tela.database.ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int quantidade = rs.getInt(1);
                    return quantidade > 0; // se existir pelo menos 1, retorna true
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar se o email existe: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // caso dê erro ou não encontre
    }
    
    public static boolean atualizarSenha(String email, String novaSenha) {
        String sql = "UPDATE usuarios SET senha = ? WHERE email = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novaSenha);
            stmt.setString(2, email);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
}