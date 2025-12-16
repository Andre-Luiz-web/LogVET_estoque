package Tela;

import Tela.database.ConexaoBD; // Importa a classe responsável pela conexão com o banco de dados

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class TelaCadastro extends JFrame {

    private JTextField campoNome, campoEmail; // Campos de texto para nome e e-mail
    private JComboBox<String> comboFuncao;    // ComboBox para selecionar a função do usuário
    private JPasswordField campoSenha;        // Campo de senha com ocultação de caracteres

    
    public TelaCadastro() {
        
        setTitle("LogiVet - Cadastro");
        
        setSize(1200, 700);
        
        setLocationRelativeTo(null);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(null);
        
        getContentPane().setBackground(Color.WHITE);
       
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagemRedimensionada));
        
        logo.setBounds(475, 20, 250, 100);
        
        add(logo);
       
        int baseX = 450;                   // Posição horizontal inicial dos campos
        int larguraCampo = 300;           // Largura dos campos
        int alturaCampo = 32;             // Altura dos campos
        int espacamentoVertical = 50;     // Espaço entre os campos
        int yAtual = 140;                 // Posição vertical inicial

        // ------------------------ CAMPO NOME ------------------------
        JPanel painelNome = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/icone_email.png");

        // Cria o campo de texto para o nome
        campoNome = criarCampoTexto(32, 5, 260, 24);
        campoNome.setForeground(Color.GRAY); // Cor inicial do texto

        // Define o comportamento ao ganhar ou perder o foco
        campoNome.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoNome.getText().equals("Digite seu nome")) {
                    campoNome.setText("");              // Apaga o texto de dica
                    campoNome.setForeground(Color.BLACK); // Define cor real do texto
                }
            }
            public void focusLost(FocusEvent e) {
                if (campoNome.getText().isEmpty()) {
                    campoNome.setText("Digite seu nome");  // Restaura dica se vazio
                    campoNome.setForeground(Color.GRAY);  // Volta a cor da dica
                }
            }
        });

        painelNome.add(campoNome);  // Adiciona o campo ao painel
        add(painelNome);            // Adiciona o painel à janela
        yAtual += espacamentoVertical;  // Atualiza posição vertical

        // ------------------------ CAMPO EMAIL ------------------------
        JPanel painelEmail = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/gmail 1.png");
        campoEmail = criarCampoTexto(32, 5, 260, 24);
        campoEmail.setText("Digite seu e-mail");
        campoEmail.setForeground(Color.GRAY);

        campoEmail.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoEmail.getText().equals("Digite seu e-mail")) {
                    campoEmail.setText("");
                    campoEmail.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campoEmail.getText().isEmpty()) {
                    campoEmail.setText("Digite seu e-mail");
                    campoEmail.setForeground(Color.GRAY);
                }
            }
        });

        painelEmail.add(campoEmail);
        add(painelEmail);
        yAtual += espacamentoVertical;

        // ------------------------ CAMPO FUNÇÃO ------------------------
        JPanel painelFuncao = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/mala.png");
        String[] funcoes = {"Estoquista"};  // Lista de opções da função
        comboFuncao = new JComboBox<>(funcoes);
        comboFuncao.setBounds(32, 5, 260, 24);
        comboFuncao.setBackground(new Color(200, 240, 230));
        comboFuncao.setBorder(null);
        comboFuncao.setSelectedItem(null); // Inicia sem nenhum valor selecionado
        painelFuncao.add(comboFuncao);
        add(painelFuncao);
        yAtual += espacamentoVertical;

        // ------------------------ CAMPO SENHA ------------------------
        JPanel painelSenha = criarPainelCampo(baseX, yAtual, larguraCampo, alturaCampo, "/Imagens/icone_senha.png");
        campoSenha = new JPasswordField();
        campoSenha.setBounds(32, 5, 260, 24);
        campoSenha.setOpaque(false);
        campoSenha.setBorder(null);
        campoSenha.setText("Digite sua senha");
        campoSenha.setForeground(Color.GRAY);
        campoSenha.setEchoChar((char) 0); // Não oculta a senha inicialmente

        campoSenha.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(campoSenha.getPassword()).equals("Digite sua senha")) {
                    campoSenha.setText("");
                    campoSenha.setForeground(Color.BLACK);
                    campoSenha.setEchoChar('•'); // Oculta caracteres da senha
                }
            }
            public void focusLost(FocusEvent e) {
                if (new String(campoSenha.getPassword()).isEmpty()) {
                    campoSenha.setText("Digite sua senha");
                    campoSenha.setForeground(Color.GRAY);
                    campoSenha.setEchoChar((char) 0); // Mostra a dica novamente
                }
            }
        });

        painelSenha.add(campoSenha);
        add(painelSenha);
        yAtual += espacamentoVertical;

        // ------------------------ BOTÃO CADASTRAR ------------------------
        JButton botaoCadastrar = new JButton("CADASTRAR") {
            // Sobrescreve o método paintComponent para desenhar cantos arredondados
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };

        botaoCadastrar.setBounds(baseX + 30, yAtual, 240, 40);
        botaoCadastrar.setBackground(new Color(242, 129, 72));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.setContentAreaFilled(false);
        botaoCadastrar.setOpaque(true);
        add(botaoCadastrar);

        // Associa o botão ao método de cadastro
        botaoCadastrar.addActionListener(this::cadastrarUsuario);

        // Exibe a janela na tela
        setVisible(true);
    }

    // Método acionado ao clicar no botão "Cadastrar"
    private void cadastrarUsuario(ActionEvent e) {
        // Captura os dados inseridos pelo usuário
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String funcao = (String) comboFuncao.getSelectedItem();
        String senha = new String(campoSenha.getPassword());

        // Validação de campos vazios ou com texto de dica
        if (nome.isEmpty() || nome.equals("Digite seu nome (sem números)") ||
            email.isEmpty() || email.equals("Digite seu e-mail") ||
            senha.isEmpty() || senha.equals("Digite sua senha") ||
            funcao == null) {

            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida se o nome contém apenas letras e espaços
        if (!nome.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
            JOptionPane.showMessageDialog(this, "O nome não pode conter números ou caracteres especiais.");
            return;
        }

        // Valida o formato do e-mail (gmail, hotmail ou institucional)
        if (!email.matches("^[^@\\s]+@(gmail\\.com|hotmail\\.com|([a-zA-Z0-9.-]+\\.)?edu\\.br)$")) {
            JOptionPane.showMessageDialog(this, "Digite um e-mail válido (gmail, hotmail ou institucional .edu.br).", "Email inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida a senha com regras de segurança
        if (!senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.,_\\-@#$%&*?!])[A-Za-z\\d.,_\\-@#$%&*?!]{8,15}$")) {
            JOptionPane.showMessageDialog(this,
                "<html>A senha deve conter:<br>- De 8 a 15 caracteres<br>- Pelo menos uma letra maiúscula<br>- Pelo menos uma letra minúscula<br>- Pelo menos um número<br>- Pelo menos um caractere especial: . , _ - @ # $ % & * ? !</html>",
                "Senha inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // SQL de inserção no banco de dados
        String sql = "INSERT INTO usuarios (nome, email, funcao, senha) VALUES (?, ?, ?, ?)";

        try (
            Connection con = ConexaoBD.conectar();               // Abre conexão
            PreparedStatement stmt = con.prepareStatement(sql)  // Prepara a inserção
        ) {
            // Define os valores nos parâmetros da query
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, funcao);
            stmt.setString(4, senha);

            // Executa o comando
            stmt.executeUpdate();

            // Exibe mensagem de sucesso
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");

            // Fecha a janela atual
            dispose();

            // Abre a tela de login
            new TelaLogin();

        } catch (Exception ex) {
            ex.printStackTrace(); // Mostra o erro no console
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método utilitário para criar painéis de campos com ícones e estilo
    private JPanel criarPainelCampo(int x, int y, int largura, int altura, String caminhoIcone) {
        JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Borda arredondada
            }
        };
        painel.setBounds(x, y, largura, altura);
        painel.setOpaque(false);
        painel.setBackground(new Color(200, 240, 230));

        // Adiciona o ícone ao painel
        JLabel icone = new JLabel(new ImageIcon(getClass().getResource(caminhoIcone)));
        icone.setBounds(5, 5, 24, 24);
        painel.add(icone);

        return painel;
    }

    // Método utilitário para criar campos de texto personalizados
    private JTextField criarCampoTexto(int x, int y, int largura, int altura) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBorder(null);
        return campo;
    }
}
