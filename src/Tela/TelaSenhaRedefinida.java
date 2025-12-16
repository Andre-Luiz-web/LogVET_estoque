// Comentários padrão do NetBeans indicando onde está localizado o template da classe
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Caminho original do arquivo no projeto (não afeta o código em execução)
// src/Prontuario/gui/LogVetProntuarioGUI.java

// Define o pacote onde a classe está localizada
package Tela;

// Importa as bibliotecas necessárias para criação da interface gráfica
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Define a classe da tela que será exibida após a redefinição da senha, herdando de JFrame
public class TelaSenhaRedefinida extends JFrame {

    // Construtor da classe (inicializa a tela)
    public TelaSenhaRedefinida() {

        // Define o título da janela
        setTitle("Senha Redefinida");

        // Define o tamanho da janela (largura x altura)
        setSize(1200, 700);

        // Define que o programa será encerrado ao fechar essa janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define o layout como nulo para posicionamento absoluto
        setLayout(null);

        // Centraliza a janela no meio da tela
        setLocationRelativeTo(null);

        // Define a cor de fundo da tela como branca
        getContentPane().setBackground(Color.WHITE);

        // ----------------------- CENTRALIZAÇÃO HORIZONTAL -----------------------

        // Calcula o centro da tela com base na largura atual da janela
        int centerX = getWidth() / 2;

        // ----------------------- LOGO DO LOGIVET -----------------------

        // Carrega a imagem da logo do sistema
        ImageIcon logoIcon = new ImageIcon("src/Imagens/logovet.png.png");

        // Redimensiona a imagem para 350x140 pixels
        Image logoImg = logoIcon.getImage().getScaledInstance(350, 140, Image.SCALE_SMOOTH);

        // Cria um JLabel com a imagem da logo redimensionada
        JLabel logo = new JLabel(new ImageIcon(logoImg));

        // Define a posição e tamanho do logo (centralizado horizontalmente)
        logo.setBounds(centerX - 175, 50, 350, 140);

        // Adiciona o logo à tela
        add(logo);

        // ----------------------- IMAGEM DO GATINHO -----------------------

        // Carrega a imagem do gatinho
        ImageIcon gatinhoIcon = new ImageIcon("src/Imagens/gatinho.png");

        // Redimensiona a imagem para 150x150 pixels
        Image gatinhoImg = gatinhoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        // Cria um JLabel com a imagem do gatinho
        JLabel gatinho = new JLabel(new ImageIcon(gatinhoImg));

        // Define a posição e tamanho da imagem (centralizado horizontalmente)
        gatinho.setBounds(centerX - 75, 220, 150, 150);

        // Adiciona a imagem à tela
        add(gatinho);

        // ----------------------- TÍTULO -----------------------

        // Cria o texto "SUA SENHA FOI REDEFINIDA"
        JLabel titulo = new JLabel("SUA SENHA FOI REDEFINIDA");

        // Define posição e tamanho do título (centralizado)
        titulo.setBounds(centerX - 200, 400, 400, 30);

        // Define a fonte, tamanho e estilo do texto
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        // Centraliza o texto dentro da label
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Adiciona o título à tela
        add(titulo);

        // ----------------------- TEXTO EXPLICATIVO -----------------------

        // Cria uma explicação para o usuário
        JLabel texto = new JLabel("Clique abaixo para entrar com sua nova senha");

        // Define posição e tamanho do texto explicativo
        texto.setBounds(centerX - 220, 440, 440, 25);

        // Define a fonte e tamanho
        texto.setFont(new Font("Arial", Font.PLAIN, 18));

        // Centraliza o texto dentro da label
        texto.setHorizontalAlignment(SwingConstants.CENTER);

        // Adiciona o texto explicativo à tela
        add(texto);

        // ----------------------- BOTÃO "ENTRAR" -----------------------

        // Cria o botão "Entrar"
        JButton btnEntrar = new JButton("Entrar");

        // Define a posição e tamanho do botão
        btnEntrar.setBounds(centerX - 100, 490, 200, 40);

        // Define a cor de fundo do botão
        btnEntrar.setBackground(new Color(255, 119, 51)); // Laranja

        // Define a cor do texto do botão
        btnEntrar.setForeground(Color.WHITE);

        // Remove o contorno de foco ao clicar
        btnEntrar.setFocusPainted(false);

        // Define a fonte do texto do botão
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 18));

        // Adiciona o botão à tela
        add(btnEntrar);

        // ----------------------- AÇÃO DO BOTÃO -----------------------

        // Define o que acontece quando o botão é clicado
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de login
                new TelaLogin().setVisible(true);

                // Fecha a tela atual de "Senha Redefinida"
                dispose();
            }
        });

        // ----------------------- LOGO UNICEPLAC -----------------------

        // Carrega a imagem da logo da Uniceplac
        ImageIcon uniceplac = new ImageIcon("src/Imagens/uniceplac.pngx"); // OBS: o caminho parece ter erro (".pngx")

        // Cria o JLabel com a imagem da Uniceplac
        JLabel logoUniceplac = new JLabel(uniceplac);

        // Define posição e tamanho da imagem
        logoUniceplac.setBounds(30, 650, 160, 35);

        // Adiciona a logo da Uniceplac à tela
        add(logoUniceplac);
    }
}
