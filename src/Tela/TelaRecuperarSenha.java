// Define o pacote onde esta classe pertence
package Tela;

// Importações de bibliotecas Swing e AWT para construção da interface gráfica
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Define a classe da tela de recuperação de senha, herdando de JFrame
public class TelaRecuperarSenha extends JFrame {

    // Construtor da tela de recuperação de senha
    public TelaRecuperarSenha() {
        // Define o título da janela
        setTitle("LogiVet - Recuperar Senha");

        // Define o tamanho da janela (largura x altura)
        setSize(1200, 700);

        // Define que o programa será encerrado ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define o layout como absoluto (sem gerenciador de layout)
        setLayout(null);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Define o fundo da janela como branco
        getContentPane().setBackground(Color.WHITE);

        // ---------------- LOGO PRINCIPAL ----------------

        // Carrega a imagem da logo do LogiVet
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));

        // Redimensiona a imagem da logo para 250x100 pixels
        Image logoImg = logoIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);

        // Cria um JLabel com a imagem redimensionada
        JLabel logo = new JLabel(new ImageIcon(logoImg));

        // Define a posição e tamanho da logo na tela
        logo.setBounds(475, 30, 250, 100);

        // Adiciona a logo à janela
        add(logo);

        // ---------------- TEXTOS INFORMATIVOS ----------------

        // Cria o título "ESQUECEU A SENHA?"
        JLabel instrucao = new JLabel("ESQUECEU A SENHA?");
        instrucao.setBounds(500, 140, 250, 25); // Posição e tamanho
        instrucao.setFont(new Font("Arial", Font.BOLD, 18)); // Fonte e estilo
        add(instrucao); // Adiciona à janela

        // Cria um texto explicativo abaixo do título
        JLabel info = new JLabel("Enviaremos as instruções para você recuperar sua senha.");
        info.setBounds(410, 170, 400, 20);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        add(info);

        // ---------------- CAMPO DE EMAIL ----------------

        // Cria um painel com ícone para o campo de e-mail
        JPanel painelEmail = criarPainelCampo(450, 210, 300, 32, "/Imagens/gmail 1.png");

        // Cria o campo de texto para digitar o e-mail
        JTextField campoEmail = criarCampoTexto(32, 5, 260, 24);
        campoEmail.setText("Digite seu email"); // Texto de dica

        // Adiciona evento para limpar o campo quando o usuário clicar
        campoEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Se estiver com o texto de dica, limpa o campo
                if (campoEmail.getText().equals("Digite seu email")) {
                    campoEmail.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Se o usuário não digitou nada, restaura o texto de dica
                if (campoEmail.getText().isEmpty()) {
                    campoEmail.setText("Digite seu email");
                }
            }
        });

        // Adiciona o campo de texto ao painel
        painelEmail.add(campoEmail);

        // Adiciona o painel à tela
        add(painelEmail);

        // ---------------- BOTÃO ENVIAR ----------------

        // Cria o botão "ENVIAR" com visual personalizado (arredondado)
        JButton btnEnviar = new JButton("ENVIAR") {
            @Override
            protected void paintComponent(Graphics g) {
                // Personaliza a pintura do botão
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground()); // Usa a cor de fundo
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Borda arredondada
                super.paintComponent(g); // Chama o método padrão
            }
        };

        // Define posição e tamanho do botão
        btnEnviar.setBounds(500, 260, 200, 40);

        // Define a cor de fundo e do texto do botão
        btnEnviar.setBackground(new Color(255, 119, 51)); // Laranja
        btnEnviar.setForeground(Color.WHITE); // Texto branco

        // Define comportamento visual do botão
        btnEnviar.setFocusPainted(false);         // Remove contorno ao focar
        btnEnviar.setContentAreaFilled(false);    // Necessário para o botão personalizado
        btnEnviar.setOpaque(true);                // Permite pintar o fundo

        // Adiciona o botão à janela
        add(btnEnviar);

        // ---------------- AÇÃO DO BOTÃO ENVIAR ----------------

        // Define o que acontece ao clicar no botão "ENVIAR"
        btnEnviar.addActionListener(e -> {
            // Captura o e-mail digitado e remove espaços
            String email = campoEmail.getText().trim();

            // Verifica se o campo está vazio, sem @ ou ainda com o texto de dica
            if (email.isEmpty() || !email.contains("@") || email.equals("Digite seu email")) {
                // Exibe mensagem de erro
                JOptionPane.showMessageDialog(this, "Digite um e-mail válido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return; // Encerra a ação
            }

            // Abre a tela de verificação de e-mail passando o e-mail informado
            new TelaVerificandoEmail(email);

            // Comentado: poderia desabilitar ou fechar essa janela se quiser
            // setEnabled(false);
            // dispose();
        });

        // ---------------- LOGO UNICEPLAC ----------------

        // Cria um JLabel com a imagem da logo da Uniceplac
        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png")));

        // Define a posição e tamanho do logo
        logoUniceplac.setBounds(70, 550, 250, 60);

        // Adiciona o logo da Uniceplac à tela
        add(logoUniceplac);

        // Exibe a janela na tela
        setVisible(true);
    }

    // ---------------- MÉTODO AUXILIAR: Criar painel com ícone e estilo ----------------
    private JPanel criarPainelCampo(int x, int y, int largura, int altura, String caminhoIcone) {
        // Cria um painel com layout nulo (posição manual dos componentes)
        JPanel painel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                // Personaliza a pintura do fundo com bordas arredondadas
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground()); // Usa a cor do fundo
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Borda arredondada
                super.paintComponent(g); // Garante que os filhos (text field) serão desenhados
            }
        };

        // Define a posição e tamanho do painel
        painel.setBounds(x, y, largura, altura);

        // Torna o painel transparente para permitir customização
        painel.setOpaque(false);

        // Define a cor de fundo do painel
        painel.setBackground(new Color(200, 240, 230)); // Verde-água claro

        // Carrega o ícone especificado pelo caminho
        ImageIcon icon = new ImageIcon(getClass().getResource(caminhoIcone));

        // Redimensiona o ícone para 24x24 pixels
        Image scaledIconImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

        // Cria o JLabel com o ícone redimensionado
        JLabel icone = new JLabel(new ImageIcon(scaledIconImage));
        icone.setBounds(5, 5, 24, 24); // Posição do ícone dentro do painel

        // Adiciona o ícone ao painel
        painel.add(icone);

        return painel; // Retorna o painel configurado
    }

    // ---------------- MÉTODO AUXILIAR: Criar campo de texto personalizado ----------------
    private JTextField criarCampoTexto(int x, int y, int largura, int altura) {
        // Cria um novo campo de texto
        JTextField campo = new JTextField();

        // Define a posição e tamanho do campo
        campo.setBounds(x, y, largura, altura);

        // Deixa o campo transparente (sem fundo)
        campo.setOpaque(false);

        // Remove a borda padrão
        campo.setBorder(null);

        return campo; // Retorna o campo configurado
    }
}
