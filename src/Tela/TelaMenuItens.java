package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import Tela.dao.ProdutoDAO;
import Tela.entities.Produto;
import Prontuario.gui.LogVetProntuarioGUI;
import Prontuario.gui.LogRegistro;
import Tela.config.LogVetConfigScreen; // Importa a nova tela de configurações
import Tela.model.Usuario; // Importa o modelo de usuário

public class TelaMenuItens extends JFrame {

    private JPanel mainContentPanel;
    private CardLayout cardLayout;

    private JPanel estoquePanelContent;
    private LogVetProntuarioGUI prontuarioPanel;
    private LogRegistro logRegistroPanel;
    private LogVetConfigScreen configPanel;

    private JTextField campoBusca;
    private JComboBox<String> filtro;
    private JPanel painelItens;

    private Usuario usuarioLogado;

    // Construtor agora recebe o Usuario logado
    public TelaMenuItens(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("LogiVet");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        criarBarraNavegacao();

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        add(mainContentPanel, BorderLayout.CENTER);

        estoquePanelContent = criarEstoquePanelContent();
        mainContentPanel.add(estoquePanelContent, "ITENS");

        prontuarioPanel = new LogVetProntuarioGUI();
        mainContentPanel.add(prontuarioPanel, "PRONTUÁRIO");

        logRegistroPanel = new LogRegistro();
        mainContentPanel.add(logRegistroPanel, "LOG");

        configPanel = new LogVetConfigScreen(usuarioLogado);
        mainContentPanel.add(configPanel, "CONFIGURAÇÕES");

        cardLayout.show(mainContentPanel, "ITENS");

        carregarItens();
    }

    private void criarBarraNavegacao() {
        JPanel barraNav = new JPanel();
        barraNav.setBackground(new Color(0x00744F));
        barraNav.setPreferredSize(new Dimension(getWidth(), 50));
        barraNav.setLayout(new BorderLayout());

        JLabel logo;
        try {
            // Caminho para o logo LogiVet (agora em /Imagens/)
            ImageIcon logiVetLogo = new ImageIcon(getClass().getResource("/Imagens/LogiVetName4.png"));
            Image image = logiVetLogo.getImage();
            Image scaledImage = image.getScaledInstance(-1, 40, Image.SCALE_SMOOTH);
            logiVetLogo = new ImageIcon(scaledImage);
            logo = new JLabel(logiVetLogo);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem LogiVetName4.png: " + e.getMessage());
            logo = new JLabel("  🐾 LogiVet");
            logo.setForeground(Color.WHITE);
            logo.setFont(new Font("Arial", Font.BOLD, 18));
        }
        barraNav.add(logo, BorderLayout.WEST);

        JPanel menuItens = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 12));
        menuItens.setOpaque(false);

        String[] itensMenu = {"ITENS", "PRONTUÁRIO", "CONFIGURAÇÕES", "LOG", "SAIR"};

        Map<String, String> panelNames = new HashMap<>();
        panelNames.put("ITENS", "ITENS");
        panelNames.put("PRONTUÁRIO", "PRONTUÁRIO");
        panelNames.put("CONFIGURAÇÕES", "CONFIGURAÇÕES");
        panelNames.put("LOG", "LOG");

        for (String itemText : itensMenu) {
            JLabel menuItem = new JLabel(itemText);
            menuItem.setForeground(Color.WHITE);
            menuItem.setFont(new Font("Arial", Font.BOLD, 12));
            menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (itemText.equals("SAIR")) {
                        System.exit(0);
                    } else {
                        String panelToShow = panelNames.get(itemText);
                        if (panelToShow != null) {
                            if (itemText.equals("ITENS")) {
                                carregarItens();
                            } else if (itemText.equals("PRONTUÁRIO")) {
                                // Se o painel de prontuário precisar recarregar dados, chame o método aqui
                                // Ex: prontuarioPanel.carregarAtendimentosDoBanco();
                            } else if (itemText.equals("LOG")) {
                                logRegistroPanel.recarregarRegistros();
                            } else if (itemText.equals("CONFIGURAÇÕES")) {
                                configPanel.revalidate();
                                configPanel.repaint();
                            }
                            cardLayout.show(mainContentPanel, panelToShow);
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setForeground(new Color(0xF07F3C));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setForeground(Color.WHITE);
                }
            });
            menuItens.add(menuItem);
        }

        barraNav.add(menuItens, BorderLayout.EAST);
        add(barraNav, BorderLayout.NORTH);
    }

    private JPanel criarEstoquePanelContent() {
        JPanel painelConteudoEstoque = new JPanel();
        painelConteudoEstoque.setLayout(new BorderLayout());
        painelConteudoEstoque.setBackground(new Color(0xE0E0E0));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(new Color(0xE0E0E0));
        topo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel painelEsquerdaTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelEsquerdaTopo.setOpaque(false);

        JLabel labelClassificar = new JLabel("Classificar por:");
        labelClassificar.setFont(new Font("Arial", Font.PLAIN, 14));
        painelEsquerdaTopo.add(labelClassificar);

        String[] opcoes = {"Ordem Alfabética", "Maior Estoque", "Mais Próximo do Vencimento"};
        filtro = new JComboBox<>(opcoes);
        filtro.setFont(new Font("Arial", Font.PLAIN, 14));
        filtro.setBackground(Color.WHITE);
        filtro.setBorder(BorderFactory.createLineBorder(new Color(0xCCCCCC)));
        painelEsquerdaTopo.add(filtro);

        filtro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarItensComFiltro((String) filtro.getSelectedItem());
            }
        });

        topo.add(painelEsquerdaTopo, BorderLayout.WEST);

        JPanel painelDireitaTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelDireitaTopo.setOpaque(false);

        campoBusca = new JTextField(20);
        campoBusca.setFont(new Font("Arial", Font.PLAIN, 14));
        campoBusca.setBackground(new Color(0xA6D5C1));
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x7ABDA4), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campoBusca.setText("Buscar item...");
        campoBusca.setForeground(Color.GRAY);
        campoBusca.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoBusca.getText().equals("Buscar item...")) {
                    campoBusca.setText("");
                    campoBusca.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campoBusca.getText().isEmpty()) {
                    campoBusca.setText("Buscar item...");
                    campoBusca.setForeground(Color.GRAY);
                }
            }
        });
        painelDireitaTopo.add(campoBusca);

        // Caminho da imagem para lupa.png
        JButton btnBuscar = new JButton(new ImageIcon(getClass().getResource("/Imagens/lupa.png")));
        btnBuscar.setPreferredSize(new Dimension(30, 30));
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(e -> carregarItensComFiltro((String) filtro.getSelectedItem()));
        painelDireitaTopo.add(btnBuscar);

        campoBusca.addActionListener(e -> carregarItensComFiltro((String) filtro.getSelectedItem()));

        JButton btnNovoProduto = new JButton("CADASTRAR NOVO PRODUTO");
        btnNovoProduto.setBackground(new Color(0xF07F3C));
        btnNovoProduto.setForeground(Color.WHITE);
        btnNovoProduto.setFont(new Font("Arial", Font.BOLD, 14));
        btnNovoProduto.setFocusPainted(false);
        btnNovoProduto.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnNovoProduto.addActionListener(e -> {
            AdicionarProdutoPopup popup = new AdicionarProdutoPopup(this);
            popup.setVisible(true);
        });
        painelDireitaTopo.add(btnNovoProduto);

        topo.add(painelDireitaTopo, BorderLayout.EAST);
        painelConteudoEstoque.add(topo, BorderLayout.NORTH);

        painelItens = new JPanel();
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        painelItens.setBackground(new Color(0xE0E0E0));
        painelItens.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(painelItens);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        painelConteudoEstoque.add(scrollPane, BorderLayout.CENTER);

        return painelConteudoEstoque;
    }

    public void carregarItens() {
        carregarItensComFiltro("Ordem Alfabética");
    }

    private void carregarItensComFiltro(String tipoFiltro) {
        Tela.dao.ProdutoDAO dao = new Tela.dao.ProdutoDAO();
        List<Tela.entities.Produto> lista;

        String termoBusca = campoBusca.getText().trim();
        if (termoBusca.isEmpty() || termoBusca.equals("Buscar item...")) {
            lista = dao.listarProdutos();
        } else {
            lista = dao.buscarPorNome(termoBusca);
        }

        if (lista != null && !lista.isEmpty()) {
            switch (tipoFiltro) {
                case "Ordem Alfabética":
                    lista.sort((p1, p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));
                    break;
                case "Maior Estoque":
                    lista.sort((p1, p2) -> Integer.compare(p2.getQuantidade(), p1.getQuantidade()));
                    break;
                case "Mais Próximo do Vencimento":
                    final LocalDate DEFAULT_FUTURE_DATE = LocalDate.parse("9999-12-31");

                    lista.sort((p1, p2) -> {
                        LocalDate date1;
                        try {
                            String val1 = p1.getValidade();
                            if (val1 != null && val1.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                String[] parts = val1.split("-");
                                val1 = parts[2] + "/" + parts[1] + "/" + parts[0];
                            }
                            date1 = (val1 != null && !val1.trim().isEmpty() && !val1.equals("N/A")) ? LocalDate.parse(val1, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : DEFAULT_FUTURE_DATE;
                        } catch (DateTimeParseException ex) {
                            System.err.println("Erro ao parsear validade do produto " + p1.getCodigoBarras() + ": '" + p1.getValidade() + "' -> Usando data padrão para ordenação.");
                            date1 = DEFAULT_FUTURE_DATE;
                        }

                        LocalDate date2;
                        try {
                            String val2 = p2.getValidade();
                            if (val2 != null && val2.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                String[] parts = val2.split("-");
                                val2 = parts[2] + "/" + parts[1] + "/" + parts[0];
                            }
                            date2 = (val2 != null && !val2.trim().isEmpty() && !val2.equals("N/A")) ? LocalDate.parse(val2, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : DEFAULT_FUTURE_DATE;
                        } catch (DateTimeParseException ex) {
                            System.err.println("Erro ao parsear validade do produto " + p2.getCodigoBarras() + ": '" + p2.getValidade() + "' -> Usando data padrão para ordenação.");
                            date2 = DEFAULT_FUTURE_DATE;
                        }

                        return date1.compareTo(date2);
                    });
                    break;
            }
        }

        painelItens.removeAll();

        painelItens.add(criarCabecalho());
        painelItens.add(Box.createVerticalStrut(10));

        if (lista.isEmpty() && (!termoBusca.isEmpty() && !termoBusca.equals("Buscar item..."))) {
            JLabel noResultsLabel = new JLabel("Nenhum produto encontrado com o termo: '" + termoBusca + "'", SwingConstants.CENTER);
            noResultsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noResultsLabel.setForeground(Color.DARK_GRAY);
            painelItens.add(Box.createVerticalGlue());
            painelItens.add(noResultsLabel);
            painelItens.add(Box.createVerticalGlue());
        } else {
            for (Tela.entities.Produto produto : lista) {
                painelItens.add(criarLinhaProduto(produto));
                painelItens.add(Box.createVerticalStrut(10));
            }
        }

        painelItens.revalidate();
        painelItens.repaint();
    }

    private JPanel criarCabecalho() {
        JPanel header = new JPanel(new GridBagLayout());
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.setBackground(new Color(0x00744F));
        header.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 5);

        // Ajuste dos pesos das colunas para mais largura nos campos de info
        // Ajuste de pesos: Soma deve ser 1.0 ou proporcional para GridBagLayout.
        // Redistribuí para dar mais espaço ao Código e Produto, e menos aos botões de ação
        double[] columnWeights = {0.05, 0.28, 0.32, 0.1, 0.1, 0.15}; // Total ~1.0

        String[] headers = {"", "CÓDIGO DE BARRAS", "PRODUTO", "QUANT. ESTOQUE", "VENCIMENTO", "AÇÕES"};

        for (int i = 0; i < headers.length; i++) {
            gbc.gridx = i;
            gbc.weightx = columnWeights[i];
            JLabel label = new JLabel(headers[i], SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setForeground(Color.WHITE);
            header.add(label, gbc);
        }

        return header;
    }

    private JPanel criarLinhaProduto(Tela.entities.Produto produto) {
        // A linha externa (linha) terá a cor verde escura do design
        RoundedPanel linha = new RoundedPanel(20, new Color(0x00744F));
        // MUDANÇA: Usar BorderLayout para que o container interno se estenda em largura
        linha.setLayout(new BorderLayout()); 
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        linha.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // O container interno é que terá a cor laranja (sempre laranja)
        RoundedPanel container = new RoundedPanel(15, new Color(0xF07F3C)); // Cor padrão laranja para o container
        container.setLayout(new GridBagLayout());
        // IMPORTANTE: Remover preferredSize e maximumSize para permitir que o container se estenda
        // container.setPreferredSize(new Dimension(900, 40)); 
        // container.setMaximumSize(new Dimension(900, 40));
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding interno

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Faz com que os componentes preencham o espaço disponível
        gbc.insets = new Insets(0, 3, 0, 3); // Espaçamento interno das células

        // Pesos das colunas para controle da proporção - DEVE SER IGUAL AO DO CABEÇALHO
        double[] columnWeights = {0.05, 0.28, 0.32, 0.1, 0.1, 0.15}; 

        boolean estoqueBaixo = produto.getQuantidade() < 20;
        String validadeProdutoStr = produto.getValidade();
        boolean vencido = false;

        String displayValidade = validadeProdutoStr;
        if (validadeProdutoStr != null && validadeProdutoStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                LocalDate validadeProdutoDate = LocalDate.parse(validadeProdutoStr);
                LocalDate dataAtual = LocalDate.now();
                vencido = validadeProdutoDate.isBefore(dataAtual) || validadeProdutoDate.isEqual(dataAtual);
                displayValidade = validadeProdutoDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex) {
                System.err.println("Erro ao parsear validade do produto " + produto.getCodigoBarras() + ": '" + produto.getValidade() + "' -> Usando data padrão para ordenação.");
                vencido = true;
                displayValidade = "N/A";
            }
        } else if (validadeProdutoStr == null || validadeProdutoStr.trim().isEmpty()) {
            vencido = true;
            displayValidade = "N/A";
        } else {
             try {
                LocalDate validadeProdutoDate = LocalDate.parse(validadeProdutoStr, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataAtual = LocalDate.now();
                vencido = validadeProdutoDate.isBefore(dataAtual) || validadeProdutoDate.isEqual(dataAtual);
            } catch (DateTimeParseException ex) {
                System.err.println("Erro ao parsear validade do produto " + produto.getCodigoBarras() + ": '" + produto.getValidade() + "' -> Tratando como vencido para alerta e exibindo como está.");
                vencido = true;
            }
            displayValidade = validadeProdutoStr;
        }

        // Coluna 1: Alerta (ícone) - Mantenha um JLabel vazio para ocupar o espaço
        gbc.gridx = 0;
        gbc.weightx = columnWeights[0];
        JLabel alertaIcone = new JLabel(); 
        container.add(alertaIcone, gbc);

        // Coluna 2: Código de Barras
        gbc.gridx = 1;
        gbc.weightx = columnWeights[1];
        container.add(estilizarCelula(produto.getCodigoBarras()), gbc);

        // Coluna 3: Produto
        gbc.gridx = 2;
        gbc.weightx = columnWeights[2];
        container.add(estilizarCelula(produto.getNome()), gbc);

        // Coluna 4: Quant. Estoque (com destaque vermelho se houver alerta)
        gbc.gridx = 3;
        gbc.weightx = columnWeights[3];
        container.add(estilizarCelulaAlerta(String.valueOf(produto.getQuantidade()), estoqueBaixo), gbc);

        // Coluna 5: Vencimento (com destaque vermelho se houver alerta)
        gbc.gridx = 4;
        gbc.weightx = columnWeights[4];
        container.add(estilizarCelulaAlerta(displayValidade, vencido), gbc);

        // Coluna 6: Ações (botões)
        gbc.gridx = 5;
        gbc.weightx = columnWeights[5];
        container.add(criarBotoesAcaoProduto(produto), gbc);

        // Adiciona o container (que agora se expande) à linha
        linha.add(container, BorderLayout.CENTER); // Adiciona ao BorderLayout da linha
        return linha;
    }

    // Método para estilizar células de texto padrão (fundo cinza claro)
    private RoundedPanel estilizarCelula(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK); // Texto preto em fundo cinza claro

        RoundedPanel celula = new RoundedPanel(10, new Color(0xD9D9D9)); // Fundo cinza claro
        celula.setLayout(new BorderLayout());
        // REMOVIDO preferredSize fixo. GridBagLayout e weightx/fill farão o trabalho.
        celula.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding interno
        celula.add(label, BorderLayout.CENTER);
        return celula;
    }

    // Método para estilizar células com alerta (fundo vermelho) ou padrão (fundo cinza claro)
    private RoundedPanel estilizarCelulaAlerta(String texto, boolean alerta) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Texto em negrito para destaque
        label.setForeground(alerta ? Color.WHITE : Color.BLACK); // Texto branco em vermelho, preto em cinza

        Color bgColor = alerta ? Color.RED : new Color(0xD9D9D9); // Fundo vermelho se alerta, senão cinza
        
        RoundedPanel celula = new RoundedPanel(10, bgColor);
        celula.setLayout(new BorderLayout());
        // REMOVIDO preferredSize fixo. GridBagLayout e weightx/fill farão o trabalho.
        celula.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding interno
        celula.add(label, BorderLayout.CENTER);
        return celula;
    }

    private JPanel criarBotoesAcaoProduto(Tela.entities.Produto produto) {
        // Usa um GridBagLayout para organizar os botões e campo de texto de forma mais controlada
        JPanel painelAcoes = new JPanel(new GridBagLayout());
        painelAcoes.setOpaque(false); // Transparente para mostrar o fundo da célula

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 1, 0, 1); // Espaçamento pequeno entre os componentes
        gbc.fill = GridBagConstraints.NONE; // Não preenche, deixa os componentes com tamanho preferencial

        // Campo de Quantidade para Adicionar
        JTextField txtQuantidadeAdicionar = new JTextField("1", 3);
        txtQuantidadeAdicionar.setHorizontalAlignment(JTextField.CENTER);
        txtQuantidadeAdicionar.setFont(new Font("Arial", Font.PLAIN, 12));
        txtQuantidadeAdicionar.setPreferredSize(new Dimension(30, 24)); // Tamanho fixo
        txtQuantidadeAdicionar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xCCCCCC), 1),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        gbc.gridx = 0; gbc.gridy = 0;
        painelAcoes.add(txtQuantidadeAdicionar, gbc);

        // Botão Adicionar Estoque
        JButton btnAddEstoque = new JButton(new ImageIcon(getClass().getResource("/Imagens/adicionar.png")));
        btnAddEstoque.setPreferredSize(new Dimension(24, 24));
        btnAddEstoque.setBorderPainted(false);
        btnAddEstoque.setContentAreaFilled(false);
        btnAddEstoque.setFocusPainted(false);
        btnAddEstoque.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddEstoque.setToolTipText("Adicionar Estoque");
        btnAddEstoque.addActionListener(e -> {
            try {
                int quantidadeParaAdicionar = Integer.parseInt(txtQuantidadeAdicionar.getText());
                if (quantidadeParaAdicionar <= 0) {
                    JOptionPane.showMessageDialog(this, "A quantidade a adicionar deve ser um número positivo.", "Erro de Entrada", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Tela.dao.ProdutoDAO dao = new Tela.dao.ProdutoDAO();
                dao.adicionarQuantidadeProduto(produto.getCodigoBarras(), quantidadeParaAdicionar);

                carregarItens();
                JOptionPane.showMessageDialog(this, quantidadeParaAdicionar + " unidades adicionadas ao estoque de " + produto.getNome(), "Estoque Atualizado", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um número válido para a quantidade.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque: " + ex.getMessage(), "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        gbc.gridx = 1; gbc.gridy = 0;
        painelAcoes.add(btnAddEstoque, gbc);

        JButton btnEditar = new JButton(new ImageIcon(getClass().getResource("/Imagens/editar.png")));
        btnEditar.setPreferredSize(new Dimension(24, 24));
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setFocusPainted(false);
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.setToolTipText("Editar Produto");
        btnEditar.addActionListener(e -> {
            EditarProdutoPopup popup = new EditarProdutoPopup(this, produto);
            popup.setVisible(true);
        });
        gbc.gridx = 2; gbc.gridy = 0;
        painelAcoes.add(btnEditar, gbc);

        return painelAcoes;
    }
}