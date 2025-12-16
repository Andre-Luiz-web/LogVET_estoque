package Tela.dao;

import Tela.database.ConexaoBD;
import Tela.entities.Produto;
import Tela.entities.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

// Retorna todos os produtos do banco de dados (já listando por nome)
    
	public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT codigo_barras, nome, quantidade, validade FROM produtos ORDER BY nome ASC";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getString("codigo_barras"),
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    rs.getString("validade")
                );
                produtos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

// Retorna um produto pelo código de barras 
    
	public Produto buscarPorCodigo(String codigo) {
        String sql = "SELECT codigo_barras, nome, quantidade, validade FROM produtos WHERE codigo_barras = ?";
        Produto produto = null;

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                    rs.getString("codigo_barras"),
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    rs.getString("validade")
                );
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao buscar produto por código: " + e.getMessage());
        }

        return produto;
    }

// Retorna uma lista de produtos cujo nome contém a string de busca.
    
	public List<Produto> buscarPorNome(String nomeBusca) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT codigo_barras, nome, quantidade, validade FROM produtos WHERE nome LIKE ? ORDER BY nome ASC";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeBusca + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getString("codigo_barras"),
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    rs.getString("validade")
                );
                
                produtos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao buscar produtos por nome: " + e.getMessage());
        }
        return produtos;
    }

    public void adicionarProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (codigo_barras, nome, quantidade, validade) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getCodigoBarras());
            stmt.setString(2, produto.getNome());
            stmt.setInt(3, produto.getQuantidade());

            if (produto.getValidade() != null && !produto.getValidade().isEmpty()) {
                stmt.setString(4, produto.getValidade());
            
            } else {
                stmt.setNull(4, Types.DATE);
            }

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto '" + produto.getNome() + "' adicionado com sucesso ao banco de dados.");
            
            } else {
                throw new SQLException("Nenhum produto foi adicionado. Verifique os dados.");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 19 || e.getSQLState().startsWith("23")) {
                throw new SQLException("Já existe um produto com o código de barras '" + produto.getCodigoBarras() + "'.", e);
            }
            
            throw new SQLException("Falha ao adicionar produto: " + e.getMessage(), e);
        }
    }

        public void atualizarProduto(Produto produto, String nomeAntigo) throws SQLException {
    
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, validade = ?, codigo_barras = ? WHERE nome = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            if (produto.getValidade() != null && !produto.getValidade().isEmpty()) {
                stmt.setString(3, produto.getValidade());
            
            } else {
                stmt.setNull(3, Types.DATE);
            }
            stmt.setString(4, produto.getCodigoBarras()); // Também atualiza o código de barras, se alterado
            stmt.setString(5, nomeAntigo);               // Nome para encontrar o produto no WHERE

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto(s) com nome '" + nomeAntigo + "' atualizado(s) com sucesso para '" + produto.getNome() + "'. Linhas afetadas: " + rowsAffected);
            
            } else {
                throw new SQLException("Nenhum produto foi atualizado. Nome '" + nomeAntigo + "' pode não existir.");
            }

        } catch (SQLException e) {
            throw new SQLException("Falha ao atualizar produto: " + e.getMessage(), e);
        }
    }
   
    public void adicionarQuantidadeProduto(String codigoBarras, int quantidadeAdicionar) throws SQLException {
        String sql = "UPDATE produtos SET quantidade = quantidade + ? WHERE codigo_barras = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeAdicionar);
            stmt.setString(2, codigoBarras);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Quantidade atualizada para o produto com código '" + codigoBarras + "'. Adicionado: " + quantidadeAdicionar);
            
            } else {
                throw new SQLException("Nenhum produto encontrado com o código de barras '" + codigoBarras + "' para atualizar a quantidade.");
            }

        } catch (SQLException e) {
            throw new SQLException("Falha ao adicionar quantidade ao produto: " + e.getMessage(), e);
        }
    }

    public void excluirProduto(String nomeProduto) throws SQLException {
        String sql = "DELETE FROM produtos WHERE nome = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeProduto);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto(s) com nome '" + nomeProduto + "' excluído(s) com sucesso. Linhas afetadas: " + rowsAffected);
            
            } else {
                throw new SQLException("Nenhum produto encontrado com o nome '" + nomeProduto + "' para exclusão.");
            }

        } catch (SQLException e) {
            throw new SQLException("Falha ao excluir produto: " + e.getMessage(), e);
        }
    }


    public List<Produto> listarOrdenadoPor(String criterio) {
        List<Produto> produtos = new ArrayList<>();
        String sqlBase = "SELECT codigo_barras, nome, quantidade, validade FROM produtos";
        String ordem;

        switch (criterio) {
            
            case "nome":
                ordem = " ORDER BY nome ASC";
                break;
            case "quantidade_desc":
                ordem = " ORDER BY quantidade DESC";
                break;
            case "validade_asc":
                ordem = " ORDER BY validade ASC";
                break;
            default:
                ordem = " ORDER BY nome ASC";
                break;
        }

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlBase + ordem);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getString("codigo_barras"),
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    rs.getString("validade")
                );
                
                produtos.add(p);
            }

        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao listar produtos ordenados: " + e.getMessage());
        }

        return produtos;
    }
}