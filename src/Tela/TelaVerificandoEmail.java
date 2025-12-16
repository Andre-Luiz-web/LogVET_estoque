
package Tela;

import javax.swing.*;
import Tela.dao.UsuarioDAO;
import java.awt.*;

public class TelaVerificandoEmail extends JFrame {

    private JLabel lblMensagem;

    private String email;

    public TelaVerificandoEmail(String email2) {
        // Armazena o e-mail recebido na variável da classe
        this.email = email2;

        // Define o título da janela
        setTitle("LogiVet - Verificando");

        // Define o tamanho da janela
        setSize(400, 300);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Fecha apenas esta janela ao clicar em "X", sem encerrar o programa inteiro
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Define o layout principal como BorderLayout
        setLayout(new BorderLayout());

        // Define o fundo da tela como branco
        getContentPane().setBackground(Color.WHITE);

        // ---------------- PAINEL CENTRAL ----------------

        // Cria o painel principal onde os componentes serão adicionados
        JPanel painelCentral = new JPanel();

        // Define o fundo branco para o painel
        painelCentral.setBackground(Color.WHITE);

        // Define o layout como vertical (empilhado)
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        // Adiciona margem interna ao painel (espaçamento nas bordas)
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // ---------------- LOGO DO SISTEMA ----------------

        // Carrega a imagem da logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));

        // Redimensiona a imagem para 200x80 pixels
        Image logoImg = logoIcon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);

        // Cria um JLabel com a imagem redimensionada
        JLabel logo = new JLabel(new ImageIcon(logoImg));

        // Centraliza horizontalmente o logo no painel
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona o logo ao painel central
        painelCentral.add(logo);

        // ---------------- MENSAGEM DE VERIFICAÇÃO ----------------

        // Cria o texto informando que o e-mail está sendo verificado
        lblMensagem = new JLabel("Verificando email...");
        lblMensagem.setFont(new Font("Arial", Font.PLAIN, 16)); // Define a fonte do texto
        lblMensagem.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o texto
        lblMensagem.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Espaço superior

        // Adiciona o texto ao painel
        painelCentral.add(lblMensagem);

        // Adiciona o painel central na parte central do BorderLayout
        add(painelCentral, BorderLayout.CENTER);

        // ---------------- INICIAR A VERIFICAÇÃO EM SEGUNDO PLANO ----------------

        // Cria uma nova thread para realizar a verificação sem travar a interface
        new Thread(this::verificarEmail).start();

        // Exibe a janela na tela
        setVisible(true);
    }

    // ---------------- MÉTODO PARA VERIFICAR O EMAIL NO BANCO ----------------
    private void verificarEmail() {
        try {
            // Aguarda 2 segundos simulando carregamento (efeito visual)
            Thread.sleep(2000);

            // Mensagem no console para depuração
            System.out.println("Verificando o email: " + email);

            // Chama o método do DAO para verificar se o e-mail existe no banco
            boolean existe = UsuarioDAO.emailExiste(email);

            // Atualiza a interface gráfica com base no resultado da verificação
            SwingUtilities.invokeLater(() -> {
                // Se o e-mail foi encontrado no banco de dados
                if (existe) {
                    // Exibe mensagem de sucesso
                    JOptionPane.showMessageDialog(this, "Email encontrado!");

                    // Abre a tela de alteração de senha, passando o e-mail
                    new TelaAlterarSenha(email);

                    // Fecha a tela de verificação
                    dispose();
                } else {
                    // Se o e-mail não existir, exibe aviso e fecha a tela
                    JOptionPane.showMessageDialog(this, "Email não encontrado.");
                    dispose();
                }
            });

        } catch (Exception e) {
            // Em caso de erro, imprime no console
            e.printStackTrace();
        }
    }
}
