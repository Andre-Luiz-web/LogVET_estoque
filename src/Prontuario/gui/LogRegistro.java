/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prontuario.gui; //

import javax.swing.*;
import javax.swing.border.*;

import Prontuario.dao.RegistroDAO; //
import Prontuario.model.Registro; //

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors; //

public class LogRegistro extends JPanel { //

    private JPanel painelRegistros;
    private RegistroDAO registroDAO;
    private JComboBox<String> comboFiltro;

    public LogRegistro() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        registroDAO = new RegistroDAO();

        // ================= Area de cartões =================
        painelRegistros = new JPanel();
        painelRegistros.setLayout(new BoxLayout(painelRegistros, BoxLayout.Y_AXIS));
        painelRegistros.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(painelRegistros);
        scrollPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Header for the log entries
        JPanel cabecalho = new JPanel(new GridLayout(1, 7, 10, 0));
        cabecalho.setBackground(new Color(75, 0, 130));
        cabecalho.setBorder(new EmptyBorder(5, -20, 5, 10));

        String[] titulos = {
                "DATA", "HORÁRIO", "PROFESSOR / VETERINÁRIO",
                "ANIMAL", "ESPÉCIE", "PRODUTOS / QUANTIDADE"
        };

        for (String titulo : titulos) {
            JLabel label = new JLabel(titulo, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 13));
            cabecalho.add(label);
        }

        painelRegistros.add(cabecalho);
        painelRegistros.add(Box.createVerticalStrut(10));

        // ================= Filtro SelecBOX =================
        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtro.setBackground(Color.WHITE);

        String[] opcoes = {"Recentes", "Mais Antigos"};
        comboFiltro = new JComboBox<>(opcoes);
        comboFiltro.setFont(new Font("SansSerif", Font.BOLD, 14));
        comboFiltro.setBackground(new Color(189, 189, 189));
        comboFiltro.setForeground(Color.BLACK);
        comboFiltro.setFocusable(false);
        comboFiltro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        comboFiltro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBackground(isSelected ? new Color(189, 189, 189) : Color.WHITE);
                c.setForeground(isSelected ? Color.WHITE : Color.BLACK);
                return c;
            }
        });

        comboFiltro.addActionListener(e -> {
            recarregarRegistros();
        });

        filtro.add(new JLabel(" Selecionar : "));
        filtro.add(comboFiltro);
        add(filtro, BorderLayout.SOUTH);

        recarregarRegistros();
    }

    public void recarregarRegistros() {
        painelRegistros.removeAll();

        JPanel cabecalho = new JPanel(new GridLayout(1, 7, 10, 0));
        cabecalho.setBackground(new Color(75, 0, 130));
        cabecalho.setBorder(new EmptyBorder(5, -20, 5, 10));
        String[] titulos = {
                "DATA", "HORÁRIO", "PROFESSOR / VETERINÁRIO",
                "ANIMAL", "ESPÉCIE", "PRODUTOS / QUANTIDADE", "RESPONSÁVEL"
        };
        for (String titulo : titulos) {
            JLabel label = new JLabel(titulo, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 13));
            cabecalho.add(label);
        }
        painelRegistros.add(cabecalho);
        painelRegistros.add(Box.createVerticalStrut(10));

        List<Registro> registros = registroDAO.buscarTodos();

        String selectedOption = (String) comboFiltro.getSelectedItem();
        if ("Mais Antigos".equals(selectedOption)) {
            registros.sort((r1, r2) -> {
                String[] dateParts1 = r1.getData().split("/");
                String[] dateParts2 = r2.getData().split("/");
                String formattedDate1 = dateParts1[2] + dateParts1[1] + dateParts1[0];
                String formattedDate2 = dateParts2[2] + dateParts2[1] + dateParts2[0];

                String formattedTime1 = r1.getHorario().replace(":", "");
                String formattedTime2 = r2.getHorario().replace(":", "");

                int dateCompare = formattedDate1.compareTo(formattedDate2);
                if (dateCompare != 0) {
                    return dateCompare;
                }
                return formattedTime1.compareTo(formattedTime2);
            });
        }

        for (Registro r : registros) {
            painelRegistros.add(new RegistroLogPanel(
                    r.getData(), r.getHorario(), r.getVeterinario(), r.getAnimal(),
                    r.getEspecie(), r.getMedicamentos(), r.getResponsavel()
            ));
            painelRegistros.add(Box.createVerticalStrut(70));
        }
        painelRegistros.revalidate();
        painelRegistros.repaint();
    }

    private JTextField criarCampo(String texto) {
        JTextField campo = new JTextField(texto);
        campo.setEditable(false);
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        campo.setBackground(new Color(245, 245, 245));
        campo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        campo.setPreferredSize(new Dimension(100, 20));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        campo.setMinimumSize(new Dimension(100, 20));

        return campo;
    }

    // REMOVIDOS campos e métodos duplicados de RegistroLogPanel
    // private JPanel detalhesMedicamentos;
    // private boolean expandido = false;
    // private void toggleMedicamentos(List<String> medicamentos) { ... }
    // private Component placeholderCampo() { ... }
}
