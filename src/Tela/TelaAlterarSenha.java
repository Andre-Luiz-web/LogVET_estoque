// Define o pacote onde a classe está localizada
package Tela;

// Importa as bibliotecas necessárias para interface gráfica
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Importa a classe responsável por interações com o banco de dados dos usuários
import Tela.dao.UsuarioDAO;

// Classe principal da tela de alteração de senha, que herda de JFrame
public class TelaAlterarSenha extends JFrame {

    // Construtor que recebe o e-mail do usuário como parâmetro
    public TelaAlterarSenha(String email) {

        // Define o título da janela
        setTitle("Alterar Senha - LogiVet");

        // Define o tamanho da janela
        setSize(1200, 700);

        // Define que o programa será encerrado ao fechar essa janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define layout nulo (absoluto) para posicionamento manual dos elementos
        setLayout(null);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Define a cor de fundo da janela
        getContentPane().setBackground(Color.WHITE);

        // ------------------- LOGO DO LOGIVET -------------------

        // Carrega a imagem da logo do LogiVet
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));

        // Redimensiona a imagem para 250x100 pixels
        Image logoImg = logoIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);

        // Cria o JLabel com a logo redimensionada
        JLabel logo = new JLabel(new ImageIcon(logoImg));

        // Define a posição e o tamanho do logo na tela
        logo.setBounds(475, 30, 250, 100);

        // Adiciona o logo à tela
        add(logo);

        // ------------------- ÍCONE DE CADEADO -------------------

        // Carrega o ícone de cadeado
        ImageIcon cadeadoIcon = new ImageIcon(getClass().getResource("/Imagens/cadeado.png"));

        // Redimensiona a imagem do cadeado
        Image cadeadoImg = cadeadoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        // Cria o JLabel com o ícone
        JLabel cadeado = new JLabel(new ImageIcon(cadeadoImg));

        // Define posição e tamanho
        cadeado.setBounds(560, 120, 80, 80);
        add(cadeado);

        // ------------------- TÍTULO DA TELA -------------------

        JLabel titulo = new JLabel("ALTERE SUA SENHA:");
        titulo.setBounds(520, 210, 300, 25);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo);

        // ------------------- CAMPO NOVA SENHA -------------------

        // Cria painel visual com ícone de cadeado reset
        JPanel painelNovaSenha = criarPainelCampo(450, 250, 300, 32, "/Imagens/cadeadoreset.png");

        // Cria o campo de nova senha com placeholder
        JPasswordField novaSenha = criarCampoSenha(32, 5, 260, 24, "Digite sua nova senha");

        // Adiciona o campo ao painel e o painel à tela
        painelNovaSenha.add(novaSenha);
        add(painelNovaSenha);

        // ------------------- CAMPO REPETIR SENHA -------------------

        JPanel painelRepetirSenha = criarPainelCampo(450, 300, 300, 32, "/Imagens/icone_senha.png");
        JPasswordField repetirSenha = criarCampoSenha(32, 5, 260, 24, "Repita sua nova senha");
        painelRepetirSenha.add(repetirSenha);
        add(painelRepetirSenha);

        // ------------------- BOTÃO "REDEFINIR SENHA" -------------------

        JButton btnRedefinir = new JButton("REDEFINIR SENHA") {
            // Sobrescreve o método de pintura para deixar o botão com cantos arredondados
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Raio dos cantos
                super.paintComponent(g);
            }
        };
        btnRedefinir.setBounds(500, 350, 200, 40);
        btnRedefinir.setBackground(new Color(255, 119, 51)); // Cor laranja
        btnRedefinir.setForeground(Color.WHITE); // Texto branco
        btnRedefinir.setFocusPainted(false); // Remove o contorno ao clicar
        btnRedefinir.setContentAreaFilled(false); // Necessário para funcionar o paintComponent
        btnRedefinir.setOpaque(true); // Necessário para aplicar cor
        add(btnRedefinir);

        // ------------------- AÇÃO DO BOTÃO -------------------

        btnRedefinir.addActionListener(e -> {
            // Obtém os valores digitados nos dois campos de senha
            String senha1 = new String(novaSenha.getPassword());
            String senha2 = new String(repetirSenha.getPassword());

            // Verifica se os campos estão vazios ou ainda com placeholder
            if (senha1.equals("Digite sua nova senha") || senha2.equals("Repita sua nova senha") || senha1.isEmpty() || senha2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha os dois campos.");
                return;
            }

            // Verifica se as senhas coincidem
            if (!senha1.equals(senha2)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem.");
                return;
            }

            // Valida a força da senha com regex
            if (!senha1.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.,_\\-@#$%&*?!])[A-Za-z\\d.,_\\-@#$%&*?!]{8,15}$")) {
                JOptionPane.showMessageDialog(this,
                    "<html>A senha deve conter:<br>- De 8 a 15 caracteres<br>- Pelo menos uma letra maiúscula<br>- Pelo menos uma letra minúscula<br>- Pelo menos um número<br>- Pelo menos um caractere especial: . , _ - @ # $ % & * ? !</html>",
                    "Senha inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Chama o método do DAO para atualizar a senha no banco de dados
            boolean sucesso = UsuarioDAO.atualizarSenha(email, senha1);

            // Se deu certo, mostra mensagem e vai para tela final
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Senha redefinida com sucesso!");
                new TelaSenhaRedefinida().setVisible(true);
                dispose(); // Fecha esta tela
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar senha. Tente novamente.");
            }
        });

        // ------------------- LOGO UNICEPLAC -------------------

        // Adiciona a imagem da Uniceplac no rodapé
        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png")));
        logoUniceplac.setBounds(70, 550, 250, 60);
        add(logoUniceplac);

        // Exibe a janela
        setVisible(true);
    }

    // Método auxiliar para criar painel arredondado com ícone
    private JPanel criarPainelCampo(int x, int y, int largura, int altura, String caminhoIcone) {
        JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painel.setBounds(x, y, largura, altura);
        painel.setOpaque(false); // Transparente
        painel.setBackground(new Color(200, 240, 230)); // Azul claro

        JLabel icone = new JLabel(new ImageIcon(getClass().getResource(caminhoIcone)));
        icone.setBounds(5, 5, 24, 24);
        painel.add(icone);

        return painel;
    }

    // Método auxiliar para criar campo de senha com placeholder e lógica de foco
    private JPasswordField criarCampoSenha(int x, int y, int largura, int altura, String placeholder) {
        // Cria campo de senha com o texto inicial como placeholder
        JPasswordField campo = new JPasswordField(placeholder);
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false); // Transparente
        campo.setBorder(null); // Sem borda
        campo.setForeground(Color.GRAY); // Texto cinza (indicando placeholder)
        campo.setEchoChar((char) 0); // Não oculta os caracteres (mostra texto)

        // Adiciona foco para lidar com placeholder
        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                // Quando ganha foco, limpa placeholder
                if (new String(campo.getPassword()).equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                    campo.setEchoChar('●'); // Ativa a ocultação dos caracteres
                }
            }

            public void focusLost(FocusEvent e) {
                // Se o campo estiver vazio ao perder o foco, restaura o placeholder
                if (new String(campo.getPassword()).isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                    campo.setEchoChar((char) 0); // Mostra como texto normal
                }
            }
        });

        return campo;
    }
}
