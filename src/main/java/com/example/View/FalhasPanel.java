package com.example.View;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.example.controllers.FalhaController; // Certifique-se de que o controlador está implementado
import com.example.models.Falha; // Certifique-se de que a model está implementada

public class FalhasPanel extends JPanel {
    // Atributos
    private FalhaController falhaController;
    private JTable falhasTable;
    private DefaultTableModel tableModel;
    private JButton btnSalvarAlteracoes;
    private JButton btnCadastrarFalha;

    public FalhasPanel() {
        super(new BorderLayout());

        // Inicializando o controlador
        falhaController = new FalhaController();

        // Inicializando o model da tabela com as colunas
        tableModel = new DefaultTableModel(new Object[] {
                "ID", "Máquina ID", "Data", "Problema", "Prioridade", "Operador"
        }, 0);  // Número de linhas inicial: 0

        // Criar JTable com o model
        falhasTable = new JTable(tableModel);

        // Preenchendo a tabela com as falhas do controlador
        carregarFalhas();

        // Adicionando a JTable a um JScrollPane
        JScrollPane scrollPane = new JScrollPane(falhasTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Criando painel inferior com botões
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCadastrarFalha = new JButton("Cadastrar");
        btnSalvarAlteracoes = new JButton("Atualizar");
        painelInferior.add(btnCadastrarFalha);
        painelInferior.add(btnSalvarAlteracoes);
        this.add(painelInferior, BorderLayout.SOUTH);

        // Adicionando ActionListeners para os botões
        addActionListeners();
    }

    private void carregarFalhas() {
        // Recupera a lista de falhas e preenche a tabela
        List<Falha> falhas = falhaController.readFalhas(); // Supondo que o método readFalhas retorna uma lista de Falha
        for (Falha falha : falhas) {
            tableModel.addRow(new Object[] {
                    falha.getId(),
                    falha.getMaquinaID(),
                    falha.getData().toString(),
                    falha.getProblema(),
                    falha.getPrioridade(),
                    falha.getOperador()
            });
        }
    }

    private void addActionListeners() {
        // ActionListener para o botão "Cadastrar"
        btnCadastrarFalha.addActionListener(e -> abrirDialogCadastro());

        // ActionListener para o botão "Salvar"
        btnSalvarAlteracoes.addActionListener(e -> editarFalha());
    }

    private void abrirDialogCadastro() {
        // Cria um novo JDialog para o cadastro de falha
        JDialog dialog = new JDialog((JDialog) null, "Cadastrar Nova Falha", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(0, 2));

        // Adiciona campos de texto para os atributos da falha
        JTextField txtMaquinaId = new JTextField();
        JTextField txtData = new JTextField(); // Sugere formato "yyyy-MM-dd"
        JTextField txtProblema = new JTextField();
        JTextField txtPrioridade = new JTextField();
        JTextField txtOperador = new JTextField();

        // Adiciona rótulos e campos ao dialog
        dialog.add(new JLabel("Máquina ID:"));
        dialog.add(txtMaquinaId);
        dialog.add(new JLabel("Data (yyyy-MM-dd):"));
        dialog.add(txtData);
        dialog.add(new JLabel("Problema:"));
        dialog.add(txtProblema);
        dialog.add(new JLabel("Prioridade:"));
        dialog.add(txtPrioridade);
        dialog.add(new JLabel("Operador:"));
        dialog.add(txtOperador);

        // Botão para cadastrar a falha
        JButton btnSubmit = new JButton("Cadastrar");
        dialog.add(btnSubmit);

        // Quando o botão for clicado, valida e envia os dados
        btnSubmit.addActionListener(ev -> {
            try {
                // Recupera os dados dos campos de texto
                String maquinaId = txtMaquinaId.getText();
                LocalDate data = LocalDate.parse(txtData.getText());
                String problema = txtProblema.getText();
                String prioridade = txtPrioridade.getText();
                String operador = txtOperador.getText();

                // Cria um novo objeto Falha
                Falha novaFalha = new Falha(null, maquinaId, data, problema, prioridade, operador);

                // Envia para a API
                Falha falhaCriada = falhaController.createFalha(novaFalha);

                // Se a falha criada não for nula, atualiza a tabela e fecha o diálogo
                if (falhaCriada != null) {
                    tableModel.addRow(new Object[]{
                            falhaCriada.getId(),
                            maquinaId,
                            data.toString(),
                            problema,
                            prioridade,
                            operador
                    });
                    JOptionPane.showMessageDialog(dialog, "Falha cadastrada com sucesso!");
                    dialog.dispose(); // Fecha o diálogo
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erro ao cadastrar falha.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao preencher os dados: " + ex.getMessage());
            }
        });

        // Mostra o formulário
        dialog.setVisible(true);
    }

    private void editarFalha() {
        // Verifica se uma linha está selecionada
        int selectedRow = falhasTable.getSelectedRow();
        if (selectedRow != -1) {
            // Cria um novo JDialog para editar a falha
            JDialog dialog = new JDialog((JDialog) null, "Editar Falha", true);
            dialog.setSize(400, 400);
            dialog.setLayout(new GridLayout(0, 2));

            // Pega os valores da linha selecionada
            String id = String.valueOf(tableModel.getValueAt(selectedRow, 0)); // ID da falha
            String maquinaId = (String) tableModel.getValueAt(selectedRow, 1);
            LocalDate data = LocalDate.parse((String) tableModel.getValueAt(selectedRow, 2));
            String problema = (String) tableModel.getValueAt(selectedRow, 3);
            String prioridade = (String) tableModel.getValueAt(selectedRow, 4);
            String operador = (String) tableModel.getValueAt(selectedRow, 5);

            // Adiciona campos de texto para os atributos da falha
            JTextField txtMaquinaId = new JTextField(maquinaId);
            JTextField txtData = new JTextField(data.toString()); // Formato "yyyy-MM-dd"
            JTextField txtProblema = new JTextField(problema);
            JTextField txtPrioridade = new JTextField(prioridade);
            JTextField txtOperador = new JTextField(operador);

            // Adiciona rótulos e campos ao dialog
            dialog.add(new JLabel("Máquina ID:"));
            dialog.add(txtMaquinaId);
            dialog.add(new JLabel("Data (yyyy-MM-dd):"));
            dialog.add(txtData);
            dialog.add(new JLabel("Problema:"));
            dialog.add(txtProblema);
            dialog.add(new JLabel("Prioridade:"));
            dialog.add(txtPrioridade);
            dialog.add(new JLabel("Operador:"));
            dialog.add(txtOperador);

            // Botão para salvar as alterações
            JButton btnSubmit = new JButton("Salvar");
            dialog.add(btnSubmit);

            // Quando o botão "Salvar" for clicado, valida e envia os dados
            btnSubmit.addActionListener(ev -> {
                try {
                    // Recupera os dados dos campos de texto
                    String newMaquinaId = txtMaquinaId.getText();
                    LocalDate newData = LocalDate.parse(txtData.getText());
                    String newProblema = txtProblema.getText();
                    String newPrioridade = txtPrioridade.getText();
                    String newOperador = txtOperador.getText();

                    // Atualiza os dados da falha
                    Falha falhaAtualizada = new Falha(id, newMaquinaId, newData, newProblema, newPrioridade, newOperador);

                    // Envia para a API para atualizar a falha
                    falhaController.updateFalha(falhaAtualizada); // Supondo que esse método não retorne nada

                    // Exibe mensagem de sucesso
                    JOptionPane.showMessageDialog(dialog, "Alterações salvas com sucesso!");

                    // Atualiza a tabela para refletir as mudanças
                    tableModel.setValueAt(newMaquinaId, selectedRow, 1);
                    tableModel.setValueAt(newData.toString(), selectedRow, 2);
                    tableModel.setValueAt(newProblema, selectedRow, 3);
                    tableModel.setValueAt(newPrioridade, selectedRow, 4);
                    tableModel.setValueAt(newOperador, selectedRow, 5);

                    dialog.dispose(); // Fecha o diálogo
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erro ao preencher os dados: " + ex.getMessage());
                }
            });

            // Mostra o formulário
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma falha para salvar alterações.");
        }
    }
}