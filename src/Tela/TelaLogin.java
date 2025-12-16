package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Tela.TelaMenuItens;
import Tela.dao.UsuarioDAO;
import Tela.model.Usuario;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;

    public TelaLogin() {
        super("LogiVet - Login");

        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        new Thread(() -> {
            System.out.println("Iniciando carregamento da tela de login...");
            try {
              
                System.out.println("Verificando conexão com o servidor...");
                Thread.sleep(1500);
                System.out.println("Carregando configurações iniciais...");
                Thread.sleep(900); 

                System.out.println("Recursos de imagem pré-carregados...");
                Thread.sleep(700); 

            } catch (InterruptedException e) {
                e.printStackTrace();
               
            } finally {
                System.out.println("Tela de login pronta e carregada.");
            }
        }).start();

        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setBounds(0, 0, 600, 700);
        painelEsquerdo.setBackground(Color.WHITE);
        painelEsquerdo.setLayout(null);
        add(painelEsquerdo);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagens/logovet.png.png"));
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(250, 100, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(imagemRedimensionada));
        logo.setBounds(150, 120, 300, 80);
        painelEsquerdo.add(logo);

        int baseY = 250;

        JPanel painelEmail = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painelEmail.setBounds(150, 245, 300, 35);
        painelEmail.setOpaque(false);
        painelEmail.setBackground(new Color(0xA6D5C1));
        painelEsquerdo.add(painelEmail);

        JLabel iconEmail = new JLabel(new ImageIcon(getClass().getResource("/Imagens/gmail 1.png")));
        iconEmail.setBounds(5, 5, 24, 24);
        painelEmail.add(iconEmail);

        campoEmail = new JTextField("Digite seu email");
        campoEmail.setForeground(Color.GRAY);
        campoEmail.setBounds(35, 5, 260, 24);
        campoEmail.setBorder(null);
        campoEmail.setOpaque(false);
        painelEmail.add(campoEmail);

        campoEmail.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoEmail.getText().equals("Digite seu email")) {
                    campoEmail.setText("");
                    campoEmail.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (campoEmail.getText().isEmpty()) {
                    campoEmail.setForeground(Color.GRAY);
                    campoEmail.setText("Digite seu email");
                }
            }
        });

        JPanel painelSenha = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        painelSenha.setBounds(150, 300, 300, 35);
        painelSenha.setOpaque(false);
        painelSenha.setBackground(new Color(0xA6D5C1));
        painelEsquerdo.add(painelSenha);

        JLabel iconSenha = new JLabel(new ImageIcon(getClass().getResource("/Imagens/icone_senha.png")));
        iconSenha.setBounds(5, 5, 24, 24);
        painelSenha.add(iconSenha);

        campoSenha = new JPasswordField("Digite sua senha");
        campoSenha.setForeground(Color.GRAY);
        campoSenha.setEchoChar((char) 0);
        campoSenha.setBounds(35, 5, 260, 24);
        campoSenha.setBorder(null);
        campoSenha.setOpaque(false);
        painelSenha.add(campoSenha);

        campoSenha.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String senha = new String(campoSenha.getPassword());
                if (senha.equals("Digite sua senha")) {
                    campoSenha.setText("");
                    campoSenha.setForeground(Color.BLACK);
                    campoSenha.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                String senha = new String(campoSenha.getPassword());
                if (senha.isEmpty()) {
                    campoSenha.setForeground(Color.GRAY);
                    campoSenha.setText("Digite sua senha");
                    campoSenha.setEchoChar((char) 0);
                }
            }
        });

        JButton botaoLogin = new JButton("LOGIN") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        botaoLogin.setContentAreaFilled(false);
        botaoLogin.setFocusPainted(false);
        botaoLogin.setOpaque(true);
        botaoLogin.setBounds(150, baseY + 100, 300, 35);
        botaoLogin.setBackground(new Color(255, 127, 80));
        botaoLogin.setForeground(Color.WHITE);
        painelEsquerdo.add(botaoLogin);

        botaoLogin.addActionListener(e -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            String email = campoEmail.getText().trim();
            String senha = new String(campoSenha.getPassword());

            if (email.equals("Digite seu email")) email = "";
            if (senha.equals("Digite sua senha")) senha = "";

            Usuario usuarioLogado = usuarioDAO.buscarUsuarioPorEmailESenha(email, senha);

            if (usuarioLogado != null) {
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!");
                new TelaMenuItens(usuarioLogado).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel cadastrar = new JLabel("Cadastre-se");
        cadastrar.setFont(new Font("Arial", Font.PLAIN, 12));
        cadastrar.setBounds(150, baseY + 150, 150, 20);
        cadastrar.setForeground(Color.BLUE);
        painelEsquerdo.add(cadastrar);
        cadastrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaCadastro();
                dispose();
            }
        });

        JLabel esqueceuSenha = new JLabel("Esqueceu a senha?");
        esqueceuSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        esqueceuSenha.setForeground(Color.BLUE);
        esqueceuSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        esqueceuSenha.setBounds(375, baseY + 150, 150, 20);
        painelEsquerdo.add(esqueceuSenha);
        esqueceuSenha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaRecuperarSenha().setVisible(true);
                dispose();
            }
        });

        JLabel logoUniceplac = new JLabel(new ImageIcon(getClass().getResource("/Imagens/uniceplac.png")));
        logoUniceplac.setBounds(70, 550, 250, 60);
        painelEsquerdo.add(logoUniceplac);

        JPanel painelDireito = new JPanel();
        painelDireito.setBounds(600, 0, 600, 700);
        painelDireito.setLayout(null);
        add(painelDireito);

        JLabel imagemLateral = new JLabel(new ImageIcon(getClass().getResource("/Imagens/imagem_lateral.png")));
        imagemLateral.setBounds(0, 0, 600, 700);
        painelDireito.add(imagemLateral);

        setVisible(true);
    }
}