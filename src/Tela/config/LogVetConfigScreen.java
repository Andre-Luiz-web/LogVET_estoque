package Tela.config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.HyperlinkEvent;

// Importações do back-end
import Tela.dao.UsuarioDAO; // Ajustado para o pacote 'Tela.dao'
import Tela.model.Usuario;   // Ajustado para o pacote 'Tela.model'

public class LogVetConfigScreen extends JPanel { // Continua como JPanel

    private static final long serialVersionUID = 1L;

    // Definição das cores personalizadas
    private final Color DARK_GREEN = new Color(30, 80, 50); 
    private final Color LIGHT_GREEN_FIELD = new Color(220, 240, 220);
    private final Color ORANGE_BUTTON = new Color(255, 128, 64);
    private final Color ACTIVE_NAV_GREEN = new Color(40, 100, 70);
    private final Color HOVER_NAV_GREEN = new Color(50, 120, 80);

    private JPanel activePanel = null;
    private JPanel cardsPanel;
    private CardLayout cardLayout;

    private JTextField txtNomeCampo;
    private JTextField txtEmailCampo;
    private JPasswordField txtSenhaAtualCampo;
    private JPasswordField txtNovaSenhaCampo;
    private JPasswordField txtConfirmarNovaSenhaCampo;
    private JLabel lblNomeExibicao;
    private JLabel lblEmailExibicao;

    private JLabel lblErroTamanho;
    private JLabel lblErroMaiuscula;
    private JLabel lblErroMinuscula;
    private JLabel lblErroNumero;
    private JLabel lblErroEspecial;
    private JLabel lblErroConfirmacao;
    private JLabel lblErroSenhaAtualIncorreta;
    private JLabel lblAvisoSenhaAtual;

    private JLabel lblDicaSenha;

    private Usuario usuarioLogado;

    public LogVetConfigScreen(Usuario usuario) {
        this.usuarioLogado = usuario;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600)); 

        // --- REMOVIDO: Painel Superior (Barra de Navegação Superior) ---
        // Todo o código que cria e adiciona o 'topPanel' foi removido daqui.
        // Isso inclui:
        // - JPanel topPanel = new JPanel(new BorderLayout());
        // - topPanel.setBackground(DARK_GREEN);
        // - topPanel.setPreferredSize(new Dimension(800, 60));
        // - JLabel logVetLabel = new JLabel(); (e todo o try-catch para logo)
        // - JPanel menuButtonsPanel = new JPanel(new FlowLayout(...)); (e todo o loop de botões de menu)
        // - JLabel gearIconLabel = new JLabel(); (e todo o try-catch para gear icon)
        // - add(topPanel, BorderLayout.NORTH);

        // --- Painel Lateral Esquerdo (Barra de Navegação Lateral) ---
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(DARK_GREEN);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(180, 500)); 
        sidebarPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        String[] navItems = {"Perfil", "Política e Privacidade"};
        String[] cardNames = {"Perfil", "PoliticaPrivacidade"};

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(Color.WHITE);

        JPanel profilePanel = createProfilePanel();
        cardsPanel.add(profilePanel, cardNames[0]);

        String privacyPolicyText = "<html>"
                + "<body style='font-family: Arial, sans-serif; font-size: 13px; color: #333; margin: 20px; line-height: 1.6;'>"
                + "<h2 style='text-align: center; color: " + DARK_GREEN.darker() + "; margin-bottom: 5px;'>Política de Privacidade - LogiVet</h2>" 
                + "<p style='text-align: center; font-size: 11px; color: #666; margin-bottom: 20px;'>Última atualização: [INSIRA A DATA AQUI]</p>" 
                
                + "<p style='margin-bottom: 15px;'><strong>Bem-vindo(a) à Política de Privacidade do LogiVet.</strong></p>" 
                + "<p style='margin-bottom: 15px;'>Este documento descreve, de forma transparente e objetiva, como o LogiVet, um sistema de gestão para clínicas veterinárias desenvolvido por <strong>[Nome da Empresa/Equipe]</strong>, realiza a coleta, uso, armazenamento e proteção dos dados pessoais dos usuários do sistema (funcionários da clínica), dos clientes (tutores) e dos animais sob seus cuidados. Nosso compromisso é com a privacidade e a segurança das informações, em total conformidade com a Lei Geral de Proteção de Dados Pessoais (Lei nº 13.709/2018 - LGPD) e demais normas aplicáveis.</p>" 
                
                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Quem Somos</h3>" 
                + "<p style='margin-bottom: 15px;'>Este sistema é operado por <strong>[Nome da Empresa Responsável]</strong>, inscrita no CNPJ sob o nº [CNPJ], com sede em [Endereco Completo]. Esta é a entidade responsável pelo tratamento dos dados pessoais coletados.</p>" 
                
                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Informações que Coletamos</h3>" 
                + "<p style='margin-bottom: 10px;'>Coletamos apenas os dados estritamente necessários para o funcionamento do sistema e para a melhoria contínua dos serviços prestados:</p>" 
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li><strong>a) Dados de Identificação e Contato:</strong>"
                + "<ul style='margin-top: 5px; margin-bottom: 5px; padding-left: 20px;'>"
                + "<li>Nome, e-mail, telefone, cargo, dados de login dos usuários;</li>"
                + "<li>Razão social, CNPJ e endereço da clínica.</li>"
                + "</ul>"
                + "</li>"
                + "<li><strong>b) Dados de Clientes e Animais:</strong>"
                + "<ul style='margin-top: 5px; margin-bottom: 5px; padding-left: 20px;'>"
                + "<li>Dados dos tutores: nome, CPF/CNPJ, endereço, telefone e e-mail;</li>"
                + "<li>Dados dos animais: nome, espécie, raça, idade, sexo, histórico clínico, vacinas, exames e procedimentos realizados.</li>"
                + "</ul>"
                + "</li>"
                + "<li><strong>c) Dados Operacionais:</strong>"
                + "<ul style='margin-top: 5px; margin-bottom: 5px; padding-left: 20px;'>"
                + "<li>Informações de uso do sistema (ações realizadas, horários, IP de acesso);</li>"
                + "<li>Dados financeiros e controle de estoque;</li>"
                + "<li>Logs de segurança para prevenção de fraudes e rastreabilidade.</li>"
                + "</ul>"
                + "</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Como Coletamos as Informações</h3>" 
                + "<p style='margin-bottom: 10px;'>A coleta ocorre de duas formas:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Diretamente fornecidas por você ou pela clínica, ao realizar cadastros, registros e operações no sistema;</li>"
                + "<li>Automaticamente, por meio do registro de interações para fins de auditoria, segurança e desempenho.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Para Que Usamos os Seus Dados</h3>" 
                + "<p style='margin-bottom: 10px;'>Utilizamos as informações exclusivamente para:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Viabilizar todas as funcionalidades do sistema LogiVet;</li>"
                + "<li>Oferecer suporte técnico e melhorar a experiência do usuário;</li>"
                + "<li>Garantir a segurança e integridade dos dados;</li>"
                + "<li>Cumprir obrigações legais, regulatórias e contratuais.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Base Legal para o Tratamento de Dados</h3>" 
                + "<p style='margin-bottom: 10px;'>O tratamento dos dados pessoais é realizado com base nas hipóteses legais previstas na LGPD, tais como:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Execução de contrato;</li>"
                + "<li>Cumprimento de obrigação legal ou regulatória;</li>"
                + "<li>Exercício regular de direitos;</li>"
                + "<li>Consentimento do titular, quando aplicável;</li>"
                + "<li>Legítimo interesse, respeitados os direitos e liberdades fundamentais do titular.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Compartilhamento de Dados</h3>" 
                + "<p style='margin-bottom: 10px;'>O LogiVet não vende nem aluga os seus dados. O compartilhamento ocorre apenas quando:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Houver consentimento explícito do titular;</li>"
                + "<li>Com parceiros e prestadores de serviços contratados para operar o sistema;</li>"
                + "<li>Em cumprimento de determinações legais ou ordens judiciais;</li>"
                + "<li>Em transações societárias, mantendo o compromisso com esta Política.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Direitos dos Titulares (LGPD)</h3>" 
                + "<p style='margin-bottom: 10px;'>Você pode, a qualquer momento, solicitar:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Acesso aos dados;</li>"
                + "<li>Correção de dados incompletos, inexatos ou desatualizados;</li>"
                + "<li>Exclusão ou anonimização dos dados, quando possível;</li>"
                + "<li>Portabilidade;</li>"
                + "<li>Informações sobre o compartilhamento;</li>"
                + "<li>Revogação do consentimento, quando esta for a base legal.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Segurança dos Dados</h3>" 
                + "<p style='margin-bottom: 10px;'>Implementamos medidas técnicas e administrativas rigorosas para proteger seus dados, incluindo:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Criptografia de dados sensíveis;</li>"
                + "<li>Controle de acesso e autenticação por senha;</li>"
                + "<li>Auditorias periódicas;</li>"
                + "<li>Backup e monitoramento contínuo de segurança.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Retenção de Dados</h3>" 
                + "<p style='margin-bottom: 10px;'>Seus dados são armazenados apenas pelo tempo necessário para:</p>"
                + "<ul style='margin-bottom: 20px; padding-left: 25px;'>"
                + "<li>Cumprir as finalidades para as quais foram coletados;</li>"
                + "<li>Atender obrigações legais e regulatórias;</li>"
                + "<li>Preservar o exercício regular de direitos em processos administrativos ou judiciais.</li>"
                + "</ul>"

                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Alterações a Esta Política</h3>" 
                + "<p style='margin-bottom: 15px;'>Esta Política poderá ser atualizada periodicamente. Alterações relevantes serão notificadas no sistema ou por e-mail. Recomendamos que consulte esta página regularmente.</p>" 
                + "<h3 style='color: " + DARK_GREEN.darker() + "; margin-top: 25px; margin-bottom: 10px;'>Encarregado pelo Tratamento de Dados (DPO)</h3>" 
                + "<p style='margin-bottom: 10px;'>Caso tenha dúvidas, sugestões ou deseje exercer seus direitos conforme a LGPD, entre em contato com o nosso Encarregado de Proteção de Dados:</p>" 
                + "<p style='margin-bottom: 5px;'>[Nome do DPO ou responsável]</p>" 
                + "<p style='margin-bottom: 5px;'>E-mail: <a href='mailto:[Seu E-mail de Contato para Privacidade]' style='color:" + ORANGE_BUTTON + ";'>[Seu E-mail de Contato para Privacidade]</a></p>" 
                + "<p>Telefone: <strong>[Seu Telefone de Contato]</strong></p>" 
                + "</body></html>";

            JPanel privacyPolicyPanel = createPlaceholderPanel(privacyPolicyText); 
            cardsPanel.add(privacyPolicyPanel, cardNames[1]);

        add(cardsPanel, BorderLayout.CENTER); // Adiciona o painel de cards ao centro da LogVetConfigScreen

        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            String cardName = cardNames[i];

            JPanel navItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
            navItemPanel.setBackground(DARK_GREEN);
            navItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            navItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel navLabel = new JLabel(item);
            navLabel.setForeground(Color.WHITE);
            navLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            navLabel.setBorder(new EmptyBorder(0, 20, 0, 20));

            if (item.equals("Perfil")) {
                navItemPanel.setBackground(ACTIVE_NAV_GREEN);
                activePanel = navItemPanel;
                cardLayout.show(cardsPanel, cardName);
            }

            navItemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (activePanel != null) {
                        activePanel.setBackground(DARK_GREEN);
                    }
                    navItemPanel.setBackground(ACTIVE_NAV_GREEN);
                    activePanel = navItemPanel;

                    cardLayout.show(cardsPanel, cardName);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (navItemPanel != activePanel) {
                        navItemPanel.setBackground(HOVER_NAV_GREEN);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (navItemPanel != activePanel) {
                        navItemPanel.setBackground(DARK_GREEN);
                    }
                }
            });

            navItemPanel.add(navLabel);
            navItemPanel.setMaximumSize(navItemPanel.getPreferredSize());
            sidebarPanel.add(navItemPanel);
        }
        add(sidebarPanel, BorderLayout.WEST); // Adiciona a barra lateral à LogVetConfigScreen
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Ajustei o padding para ser positivo e consistente

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel estofistaTitle = new JLabel("ESTOQUISTA"); 
        estofistaTitle.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(estofistaTitle, gbc);

        gbc.gridy++;
        lblNomeExibicao = new JLabel(usuarioLogado.getNome());
        lblNomeExibicao.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(lblNomeExibicao, gbc);

        gbc.gridy++; 
        lblEmailExibicao = new JLabel(usuarioLogado.getEmail());
        lblEmailExibicao.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(lblEmailExibicao, gbc);

        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(Box.createVerticalStrut(20), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel editProfileTitle = new JLabel("EDITAR DADOS DO PERFIL:");
        editProfileTitle.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(editProfileTitle, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel alterNameLabel = new JLabel("Alterar nome:");
        panel.add(alterNameLabel, gbc);

        gbc.gridx = 1;
        txtNomeCampo = new JTextField("", 25);
        txtNomeCampo.setBackground(LIGHT_GREEN_FIELD);
        txtNomeCampo.setBorder(new LineBorder(Color.GRAY, 1));
        txtNomeCampo.setPreferredSize(new Dimension(250, 30));
        panel.add(txtNomeCampo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel alterEmailLabel = new JLabel("Alterar Email:");
        panel.add(alterEmailLabel, gbc);

        gbc.gridx = 1;
        txtEmailCampo = new JTextField("", 25);
        txtEmailCampo.setBackground(LIGHT_GREEN_FIELD);
        txtEmailCampo.setBorder(new LineBorder(Color.GRAY, 1));
        txtEmailCampo.setPreferredSize(new Dimension(250, 30));
        panel.add(txtEmailCampo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(Box.createVerticalStrut(20), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel changePasswordTitle = new JLabel("ALTERAR SENHA:");
        changePasswordTitle.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(changePasswordTitle, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel currentPasswordLabel = new JLabel("Senha atual:");
        panel.add(currentPasswordLabel, gbc);

        gbc.gridx = 1;
        txtSenhaAtualCampo = new JPasswordField(25);
        txtSenhaAtualCampo.setBackground(LIGHT_GREEN_FIELD);
        txtSenhaAtualCampo.setBorder(new LineBorder(Color.GRAY, 1));
        txtSenhaAtualCampo.setPreferredSize(new Dimension(250, 30));
        panel.add(txtSenhaAtualCampo, gbc);

        lblAvisoSenhaAtual = criarLabelErroSenha("Para alterar a senha, você deve informar a Senha Atual.");
        gbc.gridy++;
        gbc.gridx = 1;
        panel.add(lblAvisoSenhaAtual, gbc);

        lblErroSenhaAtualIncorreta = criarLabelErroSenha("Senha atual incorreta.");
        gbc.gridy++;
        panel.add(lblErroSenhaAtualIncorreta, gbc);

        gbc.gridy++; 
        gbc.gridx = 0; 
        JLabel newPasswordLabel = new JLabel("Nova senha:");
        panel.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        txtNovaSenhaCampo = new JPasswordField(25);
        txtNovaSenhaCampo.setBackground(LIGHT_GREEN_FIELD);
        txtNovaSenhaCampo.setBorder(new LineBorder(Color.GRAY, 1));
        txtNovaSenhaCampo.setPreferredSize(new Dimension(250, 30));
        panel.add(txtNovaSenhaCampo, gbc);

        String dicaSenhaHtml = "<html><font color='gray'>Sua senha deve conter:</font><br>"
                              + "&bull; De 8 à 15 caracteres<br>"
                              + "&bull; Pelo menos um Caractere maiúsculo<br>"
                              + "&bull; Pelo menos um Caractere minúsculo<br>"
                              + "&bull; Pelo menos um Número<br>"
                              + "&bull; Pelo menos um Caractere especial (., _, -, @, #, $, %, &, *, ?, !)"
                              + "</html>";

        lblDicaSenha = new JLabel(dicaSenhaHtml);
        lblDicaSenha.setFont(new Font("Arial", Font.PLAIN, 10)); 
        lblDicaSenha.setForeground(Color.BLACK);
        lblDicaSenha.setBorder(new EmptyBorder(0, 5, 0, 0)); 
        
        gbc.gridy++; 
        gbc.gridx = 1; 
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.WEST; 
        panel.add(lblDicaSenha, gbc);

        lblErroTamanho = criarLabelErroSenha("• Mínimo 8 caracteres e máximo 15."); 
        gbc.gridy++; 
        panel.add(lblErroTamanho, gbc);

        lblErroMaiuscula = criarLabelErroSenha("• Pelo menos um caractere maiúsculo.");
        gbc.gridy++;
        panel.add(lblErroMaiuscula, gbc);

        lblErroMinuscula = criarLabelErroSenha("• Pelo menos um caractere minúsculo.");
        gbc.gridy++;
        panel.add(lblErroMinuscula, gbc);

        lblErroNumero = criarLabelErroSenha("• Pelo menos um número.");
        gbc.gridy++;
        panel.add(lblErroNumero, gbc);

        lblErroEspecial = criarLabelErroSenha("• Pelo menos um caractere especial (., _, -, @, #, $, %, &, *, ?, !).");
        gbc.gridy++;
        panel.add(lblErroEspecial, gbc);

        gbc.gridy++; 
        gbc.gridx = 0; 
        gbc.gridwidth = 1; 
        JLabel confirmNewPasswordLabel = new JLabel("Confirmar nova senha:");
        panel.add(confirmNewPasswordLabel, gbc);

        gbc.gridx = 1;
        txtConfirmarNovaSenhaCampo = new JPasswordField(25);
        txtConfirmarNovaSenhaCampo.setBackground(LIGHT_GREEN_FIELD);
        txtConfirmarNovaSenhaCampo.setBorder(new LineBorder(Color.GRAY, 1));
        txtConfirmarNovaSenhaCampo.setPreferredSize(new Dimension(250, 30));
        panel.add(txtConfirmarNovaSenhaCampo, gbc);

        lblErroConfirmacao = criarLabelErroSenha("As novas senhas não coincidem.");
        gbc.gridx = 1;
        gbc.gridy++;
        panel.add(lblErroConfirmacao, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(Box.createVerticalStrut(30), gbc);

        JButton saveButton = new JButton("SALVAR ALTERAÇÕES");
        saveButton.setBackground(ORANGE_BUTTON);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(200, 30));
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarAlteracoes();
            }
        });
        panel.add(saveButton, gbc);

        return panel;
    }

    private JLabel criarLabelErroSenha(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.RED); 
        label.setFont(new Font("Arial", Font.PLAIN, 10)); 
        label.setVisible(false); 
        return label;
    }

    private void limparMensagensDeErroSenha() {
        lblErroTamanho.setVisible(false);
        lblErroMaiuscula.setVisible(false);
        lblErroMinuscula.setVisible(false);
        lblErroNumero.setVisible(false);
        lblErroEspecial.setVisible(false);
        lblErroConfirmacao.setVisible(false);
        lblErroSenhaAtualIncorreta.setVisible(false);
        lblAvisoSenhaAtual.setVisible(false); 
    }

    private void salvarAlteracoes() {
        String novoNome = txtNomeCampo.getText().trim();
        String novoEmail = txtEmailCampo.getText().trim();
        String senhaAtualDigitada = new String(txtSenhaAtualCampo.getPassword());
        String novaSenhaDigitada = new String(txtNovaSenhaCampo.getPassword());
        String confirmarNovaSenhaDigitada = new String(txtConfirmarNovaSenhaCampo.getPassword());

        limparMensagensDeErroSenha();

        if (novoNome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome não pode estar vazio.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        }
        if (novoEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Email não pode estar vazio.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        }
        if (!novoEmail.isEmpty() && !novoEmail.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um email válido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean tentandoAlterarSenha = (novaSenhaDigitada.length() > 0 || confirmarNovaSenhaDigitada.length() > 0);
        
        if (tentandoAlterarSenha) {
            if (senhaAtualDigitada.isEmpty()) {
                lblAvisoSenhaAtual.setVisible(true);
                return;
            }

            if (!novaSenhaDigitada.equals(confirmarNovaSenhaDigitada)) {
                lblErroConfirmacao.setVisible(true);
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO(); 
            if (!usuarioDAO.verificarSenhaAtual(usuarioLogado.getId(), senhaAtualDigitada)) {
                lblErroSenhaAtualIncorreta.setVisible(true);
                txtSenhaAtualCampo.setText(""); 
                return;
            }

            boolean senhaValidaParaAlteracao = true;

            if (novaSenhaDigitada.length() < 8 || novaSenhaDigitada.length() > 15) { 
                lblErroTamanho.setVisible(true);
                senhaValidaParaAlteracao = false;
            }

            if (!novaSenhaDigitada.matches(".*[A-Z].*")) {
                lblErroMaiuscula.setVisible(true);
                senhaValidaParaAlteracao = false;
            }

            if (!novaSenhaDigitada.matches(".*[a-z].*")) {
                lblErroMinuscula.setVisible(true);
                senhaValidaParaAlteracao = false;
            }

            if (!novaSenhaDigitada.matches(".*[0-9].*")) {
                lblErroNumero.setVisible(true);
                senhaValidaParaAlteracao = false;
            }

            if (!novaSenhaDigitada.matches(".*[.,_\\-@#$%&*?!].*")) {
                lblErroEspecial.setVisible(true);
                senhaValidaParaAlteracao = false;
            }

            if (!senhaValidaParaAlteracao) {
                return;
            }

            usuarioLogado.setSenha(novaSenhaDigitada); 

        } 
        
        if (!novoNome.isEmpty()) {
            usuarioLogado.setNome(novoNome);
        }
        if (!novoEmail.isEmpty()) {
            usuarioLogado.setEmail(novoEmail);
        }
       
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        boolean sucesso = usuarioDAO.atualizarUsuario(usuarioLogado);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Dados do perfil atualizados com sucesso!");
            
            lblNomeExibicao.setText(usuarioLogado.getNome());
            lblEmailExibicao.setText(usuarioLogado.getEmail());

            txtSenhaAtualCampo.setText("");
            txtNovaSenhaCampo.setText("");
            txtConfirmarNovaSenhaCampo.setText("");
            limparMensagensDeErroSenha();

            txtNomeCampo.setText(""); 
            txtEmailCampo.setText(""); 

        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar dados do perfil. Verifique o console para mais detalhes (conexão com BD, ID do usuário, etc.).", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JEditorPane editorPane = new JEditorPane("text/html", text);
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE); 
        editorPane.setFont(new Font("Arial", Font.PLAIN, 12));
        
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    System.err.println("Erro ao abrir link: " + ex.getMessage());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(null); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, BorderLayout.CENTER);
       
        return panel;
    }
}