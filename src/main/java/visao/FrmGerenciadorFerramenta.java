package visao;

import dao.FerramentaDAO;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Ferramenta;
import servico.FerramentaService;
import controle.GerenciadorFerramentaController;

/**
 * Classe responsável pela interface gráfica do gerenciador de ferramentas.
 */
public class FrmGerenciadorFerramenta extends javax.swing.JFrame {

    private FerramentaService FerramentaService = new FerramentaService();
    private GerenciadorFerramentaController controller;
    private String mensagem;

    public FrmGerenciadorFerramenta(GerenciadorFerramentaController controller) {
        initComponents();
        this.controller = controller;

    }

    public FrmGerenciadorFerramenta() {
        initComponents();

        FerramentaService ferramentaService = new FerramentaService();

        controller = new GerenciadorFerramentaController(this, FerramentaService);

        carregaTabela();

    }

    public void calculaValorTotal() {
        controller.calcularValorTotal();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTableFerramenta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        JTFValor = new javax.swing.JTextField();
        JTFNome = new javax.swing.JTextField();
        JTFMarca = new javax.swing.JTextField();
        JBApagar = new javax.swing.JButton();
        JBEditar = new javax.swing.JButton();
        JBCancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciador de Ferramentas");

        JTableFerramenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Marca", "Valor"
            }
        ));
        JTableFerramenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableFerramentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTableFerramenta);
        if (JTableFerramenta.getColumnModel().getColumnCount() > 0) {
            JTableFerramenta.getColumnModel().getColumn(0).setMinWidth(50);
            JTableFerramenta.getColumnModel().getColumn(0).setPreferredWidth(50);
            JTableFerramenta.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Gerenciador de Ferramentas");

        jLabel2.setText("Nome:");

        jLabel3.setText("Marca:");

        jLabel4.setText("Valor:");

        JTFValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFValorActionPerformed(evt);
            }
        });

        JTFNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFNomeActionPerformed(evt);
            }
        });

        JTFMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTFMarcaActionPerformed(evt);
            }
        });

        JBApagar.setText("Apagar");
        JBApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBApagarActionPerformed(evt);
            }
        });

        JBEditar.setText("Editar");
        JBEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBEditarActionPerformed(evt);
            }
        });

        JBCancelar.setText("Cancelar");
        JBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCancelarActionPerformed(evt);
            }
        });

        jLabel5.setText("Valor total:");

        jLabelTotal.setText("jLabel6");
        jLabelTotal.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabelTotalAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(JBApagar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JBEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JBCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JTFMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(JTFNome, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(JTFValor, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(11, 11, 11)
                        .addComponent(jLabelTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTFNome, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTFMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTFValor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JBApagar)
                            .addComponent(JBEditar)
                            .addComponent(JBCancelar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTFValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFValorActionPerformed

    }//GEN-LAST:event_JTFValorActionPerformed

    private void JTFNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFNomeActionPerformed

    }//GEN-LAST:event_JTFNomeActionPerformed

    private void JTFMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTFMarcaActionPerformed

    }//GEN-LAST:event_JTFMarcaActionPerformed

    private void JBApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBApagarActionPerformed
        controller.apagarFerramenta();

    }//GEN-LAST:event_JBApagarActionPerformed

    private void JBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_JBCancelarActionPerformed

    // Método executado quando ocorre um clique na tabela de ferramentas
    private void JTableFerramentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableFerramentaMouseClicked
        controller.carregarTabelaGerenciador();

    }//GEN-LAST:event_JTableFerramentaMouseClicked

    private void JBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBEditarActionPerformed
        controller.editarFerramenta();
    }//GEN-LAST:event_JBEditarActionPerformed

    private void jLabelTotalAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabelTotalAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelTotalAncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBApagar;
    private javax.swing.JButton JBCancelar;
    private javax.swing.JButton JBEditar;
    private javax.swing.JTextField JTFMarca;
    private javax.swing.JTextField JTFNome;
    private javax.swing.JTextField JTFValor;
    private javax.swing.JTable JTableFerramenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void clearTable() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) JTableFerramenta.getModel();
        modelo.setNumRows(0);
    }

    public void addRowToTable(int id, String nome, String marca, int valor) {
        DefaultTableModel modelo = (DefaultTableModel) JTableFerramenta.getModel();
        modelo.addRow(new Object[]{id, nome, marca, valor});
    }

    public void clearFields() {
        JTFNome.setText("");
        JTFMarca.setText("");
        JTFValor.setText("");
    }

    public void carregaTabela() {
        controller.carregarTabela();
    }

    public void carregarTabelaGerenciador() {
        controller.carregarTabelaGerenciador();
    }

    public JButton getJBApagar() {
        return JBApagar;
    }

    public JButton getJBCancelar() {
        return JBCancelar;
    }

    public JButton getJBEditar() {
        return JBEditar;
    }

    public String getJTFMarca() {
        return JTFMarca.getText();
    }

    public void setJTFMarca(String marca) {
        JTFMarca.setText(marca);
    }

    public String getJTFNome() {
        return JTFNome.getText();
    }

    public void setJTFNome(String nome) {
        JTFNome.setText(nome);
    }

    public String getJTFValor() {
        return JTFValor.getText();
    }

    public void setJTFValor(String valor) {
        JTFValor.setText(valor);
    }

    public JTable getJTableFerramenta() {
        return JTableFerramenta;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public JLabel getjLabelTotal() {
        return jLabelTotal;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setJBApagar(JButton JBApagar) {
        this.JBApagar = JBApagar;
    }

    public void setJBCancelar(JButton JBCancelar) {
        this.JBCancelar = JBCancelar;
    }

    public void setJBEditar(JButton JBEditar) {
        this.JBEditar = JBEditar;
    }

    public void setJTFMarca(JTextField JTFMarca) {
        this.JTFMarca = JTFMarca;
    }

    public void setJTFValor(JTextField JTFValor) {
        this.JTFValor = JTFValor;
    }

    public void setJTableFerramenta(JTable JTableFerramenta) {
        this.JTableFerramenta = JTableFerramenta;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public void setLabelTotal(String texto) {
        this.jLabelTotal.setText(texto);
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setJTableAmigos(JTable JTableFerramenta) {
        this.JTableFerramenta = JTableFerramenta;
    }

}
