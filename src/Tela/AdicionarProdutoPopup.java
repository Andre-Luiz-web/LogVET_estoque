package Tela;

import Tela.entities.Produto;
import Tela.dao.ProdutoDAO;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class AdicionarProdutoPopup extends JDialog {

    private JTextField txtCodigoBarras;
    private JTextField txtNome;
    private JTextField txtQuantidade;
    private JFormattedTextField txtValidade;

    private TelaMenuItens parentFrame; // Agora o tipo é TelaMenuItens

    public AdicionarProdutoPopup(TelaMenuItens owner) { // O construtor recebe TelaMenuItens
        super(owner, "Cadastrar Novo Produto", true); // Passa o owner diretamente
        this.parentFrame = owner;
        initUI();
    }

    private void initUI() {
        Color verdeFundo = new Color(15, 106, 74);
        Color laranja = new Color(244, 122, 32);
        Color cinzaClaro = new Color(220, 220, 220);
        Color branco = Color.WHITE;
        Color preto = Color.BLACK;

        // Main panel with dark green background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(verdeFundo);

        // Header Label
        JLabel headerLabel = new JLabel("CADASTRAR NOVO PRODUTO", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(laranja);
        headerLabel.setForeground(branco);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerLabel.setPreferredSize(new Dimension(0, 40));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Content panel (now also dark green and rounded)
        RoundedPanel contentPanel = new RoundedPanel(20, verdeFundo);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Codigo de Barras
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(createLabel("Código de Barras:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        txtCodigoBarras = createTextField("");
        contentPanel.add(txtCodigoBarras, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(createLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        txtNome = createTextField("");
        contentPanel.add(txtNome, gbc);

        // Quantidade
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(createLabel("Quantidade:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        txtQuantidade = createTextField("");
        txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();
                }
            }
        });
        contentPanel.add(txtQuantidade, gbc);

        // Validade (DD/MM/YYYY mask)
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(createLabel("Validade (DD/MM/YYYY):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.weightx = 1.0;
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            txtValidade = new JFormattedTextField(dateFormatter);
            txtValidade.setFont(new Font("SansSerif", Font.PLAIN, 14));
            txtValidade.setBackground(cinzaClaro);
            txtValidade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xCCCCCC)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } catch (ParseException e) {
            txtValidade = new JFormattedTextField(); // Fallback
            System.err.println("Error creating date formatter: " + e.getMessage());
        }
        contentPanel.add(txtValidade, gbc);

        // Add a vertical strut to push content up and center form vertically
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(contentPanel, BorderLayout.CENTER);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(wrapperPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonsPanel.setBackground(verdeFundo);
        JButton btnCancelar = new JButton("CANCELAR");
        styleButton(btnCancelar, laranja, branco);
        btnCancelar.addActionListener(e -> dispose());
        buttonsPanel.add(btnCancelar);

        JButton btnSalvar = new JButton("SALVAR");
        styleButton(btnSalvar, laranja, branco);
        btnSalvar.addActionListener(e -> salvarNovoProduto());
        buttonsPanel.add(btnSalvar);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setSize(550, 450);
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createTextField(String defaultText) {
        JTextField textField = new JTextField(defaultText);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBackground(new Color(0xD9D9D9));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(110, 35));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void salvarNovoProduto() {
        String codigoBarras = txtCodigoBarras.getText().trim();
        String nome = txtNome.getText().trim();
        String quantidadeStr = txtQuantidade.getText().trim();
        String validade = txtValidade.getText().trim();

        if (codigoBarras.isEmpty() || nome.isEmpty() || quantidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios (Código de Barras, Nome, Quantidade).", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade < 0) {
                 JOptionPane.showMessageDialog(this, "A quantidade não pode ser negativa.", "Valor Inválido", JOptionPane.WARNING_MESSAGE);
                 return;
            }

            String formattedValidade = null;
            if (!validade.isEmpty() && !validade.equals("  /  /    ")) {
                try {
                    String[] parts = validade.split("/");
                    if (parts.length == 3) {
                        formattedValidade = parts[2] + "-" + parts[1] + "-" + parts[0];
                    } else {
                        throw new ParseException("Formato de data inválido.", 0);
                    }
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(this, "Formato de validade inválido. Use DD/MM/YYYY.", "Erro de Data", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {
                 formattedValidade = "";
            }

            Produto novoProduto = new Produto(codigoBarras, nome, quantidade, formattedValidade);
            ProdutoDAO dao = new ProdutoDAO();

            dao.adicionarProduto(novoProduto);

            JOptionPane.showMessageDialog(this, "Produto '" + nome + "' adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.carregarItens(); // Chama o método na TelaMenuItens para recarregar a lista
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Por favor, insira um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("já existe")) {
                 JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Duplicidade", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o produto: " + ex.getMessage(), "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }
}