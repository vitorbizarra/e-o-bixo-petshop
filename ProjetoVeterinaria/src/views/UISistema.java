/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import conexoes.MySQL;
import javax.swing.JOptionPane;
import objetos.Cliente;
import objetos.Servico;
import objetos.Veterinario;

/**
 *
 * @author aluno
 */
public class UISistema extends javax.swing.JFrame {

    MySQL conn = new MySQL();

    /**
     * Creates new form UISistema
     */
    public UISistema() {
        initComponents();
        UpdateCbxCliente();
        UpdateCbxVeterinario();
    }

    public void cadastrarCliente(Cliente novoCliente) {
        novoCliente.setRazao_social(txtRazaoSocialCadastroCl.getText());
        novoCliente.setCnpj(txtCnpjCadastroCl.getText());
        novoCliente.setEndereco(txtEnderecoCadastroCl.getText());

        this.conn.conectaBanco();

        try {
            this.conn.insertSQL("INSERT INTO `cliente` ("
                    + "`Razao_social`, "
                    + "`Cnpj`,"
                    + "`Endereco`"
                    + ") VALUES ("
                    + "'" + novoCliente.getRazao_social() + "',"
                    + "'" + novoCliente.getCnpj() + "',"
                    + "'" + novoCliente.getEndereco() + "'"
                    + ");"
            );
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o cliente.");
        } finally {
            this.conn.fechaBanco();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
            this.LimparCadastroCliente();
        }
    }

    private void AtualizarCliente(Cliente novoCliente) {

        String cliente_selecionado = String.valueOf(cbxClienteBusca.getSelectedItem());
        novoCliente.setRazao_social(txtRazaoSocialBuscaCl.getText());
        novoCliente.setCnpj(txtCnpjBuscaCl.getText());
        novoCliente.setEndereco(txtEnderecoBuscaCl.getText());

        this.conn.conectaBanco();

        try {
            this.conn.updateSQL("UPDATE cliente "
                    + "SET Razao_social = '" + novoCliente.getRazao_social() + "', "
                    + "Cnpj = '" + novoCliente.getCnpj() + "', "
                    + "Endereco = '" + novoCliente.getEndereco() + "' "
                    + "WHERE Razao_social = '" + cliente_selecionado + "'");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o cliente.");
        } finally {
            this.conn.fechaBanco();
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso.");
            this.LimparCadastroCliente();
            UpdateCbxCliente();
        }
    }

    private void ExcluirCliente() {
        String cliente_selecionado = String.valueOf(cbxClienteBusca.getSelectedItem());
        this.conn.conectaBanco();

        try {
            this.conn.insertSQL("DELETE FROM cliente WHERE Razao_social = '" + cliente_selecionado + "'");
        } catch (Exception e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao excluir o cliente.");
        } finally {
            this.conn.fechaBanco();
            JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso.");
            this.LimparCadastroCliente();
            UpdateCbxCliente();
        }

    }

    private void LimparCadastroCliente() {
        txtRazaoSocialCadastroCl.setText("");
        txtEnderecoCadastroCl.setText("");
        txtCnpjCadastroCl.setText("");
    }

    private void CadastrarVeterinario(Veterinario novoVeterinario) {
        String pass = txtSenhaCadastroVet.getText();
        if (pass.equals(txtConfirmSenhaCadastroVet.getText())) {
            novoVeterinario.setNome(txtNomeCadastroVet.getText());
            novoVeterinario.setCpf(txtCpfCadastroVet.getText());
            novoVeterinario.setSenha(pass);

            novoVeterinario.setCpf(txtCpfCadastroVet.getText());
            novoVeterinario.setEmail(txtEmailCadastroVet.getText());

            this.conn.conectaBanco();
            try {
                this.conn.insertSQL("INSERT INTO `veterinario` ("
                        + "`Nome`, "
                        + "`Cpf`, "
                        + "`Senha`, "
                        + "`Email`"
                        + ") VALUES ("
                        + "'" + novoVeterinario.getNome() + "', "
                        + "'" + novoVeterinario.getCpf() + "', "
                        + "'" + novoVeterinario.getSenha() + "', "
                        + "'" + novoVeterinario.getEmail() + "'"
                        + ")"
                );
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar veterinário: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar o veterinário.");
            } finally {
                this.conn.fechaBanco();
                JOptionPane.showMessageDialog(null, "Veterinário cadastrado com sucesso.");
                this.LimparCadastroVeterinario();
            }
        } else {
            JOptionPane.showMessageDialog(null, "As senhas informadas não conferem");
        }
    }

    private void LimparCadastroVeterinario() {
        txtNomeCadastroVet.setText("");
        txtCpfCadastroVet.setText("");
        txtSenhaCadastroVet.setText("");
        txtConfirmSenhaCadastroVet.setText("");
        txtEmailCadastroVet.setText("");
    }

    private void UpdateCbxCliente() {
        cbxClienteBusca.removeAllItems();
        cbxClienteCadastroServico.removeAllItems();
        cbxClientesVeterinario.removeAllItems();

        this.conn.conectaBanco();
        try {

            this.conn.executarSQL("SELECT Razao_social FROM cliente ORDER BY Razao_social");

            while (this.conn.getResultSet().next()) {
                cbxClienteBusca.addItem(this.conn.getResultSet().getString("Razao_social"));
                cbxClienteCadastroServico.addItem(this.conn.getResultSet().getString("Razao_social"));
                cbxClientesVeterinario.addItem(this.conn.getResultSet().getString("Razao_social"));
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        this.conn.fechaBanco();
    }

    private void UpdateCbxVeterinario() {
        cbxVeterinario.removeAllItems();
        cbxVeterinarioCadastroServico.removeAllItems();
        cbxVeterinarioBuscaServico.removeAllItems();

        this.conn.conectaBanco();
        try {

            this.conn.executarSQL("SELECT Nome FROM veterinario ORDER BY Nome");

            while (this.conn.getResultSet().next()) {
                String new_item = this.conn.getResultSet().getString("Nome");
//                System.out.println(new_item);
                cbxVeterinario.addItem(new_item);
                cbxVeterinarioCadastroServico.addItem(new_item);
                cbxVeterinarioBuscaServico.addItem(new_item);
            }

        } catch (Exception e) {
            System.out.println("Erro UpdateCbxVeterinario: " + e.getMessage());
        }
        this.conn.fechaBanco();
    }

    private void LimpaCadastroServico() {
        lblHorasCadastro.setText("0");
        sldHorasCadastro.setValue(0);
        txtTipoCadastro.setText("");
        txtDescCadastro.setText("");
    }

    private void CadastrarServico(Servico novoServico) {
        novoServico.setVeterinario(String.valueOf(cbxVeterinarioCadastroServico.getSelectedItem()));
        novoServico.setCliente(String.valueOf(cbxClienteCadastroServico.getSelectedItem()));
        novoServico.setTipo(txtTipoCadastro.getText());
        novoServico.setHoras(sldHorasCadastro.getValue());
        novoServico.setDescricao(txtDescCadastro.getText());

        this.conn.conectaBanco();
        try {

            this.conn.insertSQL("call insertServico("
                    + "'" + novoServico.getVeterinario() + "', "
                    + "'" + novoServico.getCliente() + "', "
                    + "'" + novoServico.getTipo() + "', "
                    + "'" + novoServico.getHoras() + "', "
                    + "'" + novoServico.getDescricao() + "')");

            this.conn.fechaBanco();
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar serviço: " + e.getMessage());
        } finally {
            JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso.");
            this.LimpaCadastroServico();
        }
    }

    private void getServico() {
        String nome_vet = String.valueOf(cbxVeterinarioBuscaServico.getSelectedItem());
        String nome_cliente = String.valueOf(cbxClientesVeterinario.getSelectedItem());

        this.conn.conectaBanco();
        try {

            this.conn.executarSQL("SELECT Cliente, Tipo, Horas, Descricao FROM ultimoServicoVeterinario WHERE Veterinario = '" + nome_vet + "' ORDER BY ID DESC LIMIT 1");
            while (this.conn.getResultSet().next()) {
                cbxClientesVeterinario.setSelectedItem(this.conn.getResultSet().getString("Cliente"));
                txtTipoBusca.setText(this.conn.getResultSet().getString("Tipo"));
                lblHorasBusca.setText(this.conn.getResultSet().getString("Horas"));
                sldHorasBusca.setValue(Integer.parseInt(this.conn.getResultSet().getString("Horas")));
                txtDescBusca.setText(this.conn.getResultSet().getString("Descricao"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar clientes: " + e.getMessage());
        }
        this.conn.fechaBanco();
    }

    private void updateServico(Servico novoServico) {
        novoServico.setVeterinario(String.valueOf(cbxVeterinarioBuscaServico.getSelectedItem()));
        novoServico.setCliente(String.valueOf(cbxClientesVeterinario.getSelectedItem()));
        novoServico.setTipo(txtTipoBusca.getText());
        novoServico.setHoras(sldHorasBusca.getValue());
        novoServico.setDescricao(txtDescBusca.getText());

        this.conn.conectaBanco();

        try {

            this.conn.updateSQL("call updateServico("
                    + "'" + novoServico.getVeterinario() + "', "
                    + "'" + novoServico.getCliente() + "', "
                    + "'" + novoServico.getTipo() + "', "
                    + "'" + novoServico.getHoras() + "', "
                    + "'" + novoServico.getDescricao() + "');");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o cliente.");
        } finally {
            this.conn.fechaBanco();
            JOptionPane.showMessageDialog(null, "Servico atualizado com sucesso.");
            this.LimparCadastroCliente();
            UpdateCbxCliente();
        }
    }

    private void deleteServico() {
        String vet_name = String.valueOf(cbxVeterinarioBuscaServico.getSelectedItem());

        try {
            this.conn.conectaBanco();
            this.conn.updateSQL("call deleteServico('" + vet_name + "')");
            this.conn.fechaBanco();
        } catch (Exception e) {
            System.out.println("Erro ao excluir o servico: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao excluir o servico.");
        } finally {
            JOptionPane.showMessageDialog(null, "Servico excluído com sucesso.");
            txtTipoBusca.setText("");
            lblHorasBusca.setText("0");
            sldHorasBusca.setValue(0);
            txtDescBusca.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtRazaoSocialCadastroCl = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEnderecoCadastroCl = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        btnLimparCadastroCl = new javax.swing.JButton();
        btnCadastrarCl = new javax.swing.JButton();
        txtCnpjCadastroCl = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtRazaoSocialBuscaCl = new javax.swing.JTextField();
        cbxClienteBusca = new javax.swing.JComboBox<>();
        btnBuscarCliente = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnAtualizarCliente = new javax.swing.JButton();
        btnDeletarCliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEnderecoBuscaCl = new javax.swing.JTextArea();
        txtCnpjBuscaCl = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtNomeCadastroVet = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCpfCadastroVet = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSenhaCadastroVet = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnLimparCadastroVet = new javax.swing.JButton();
        btnCadastrarVet = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtConfirmSenhaCadastroVet = new javax.swing.JPasswordField();
        btnShowPass = new javax.swing.JToggleButton();
        txtEmailCadastroVet = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cbxVeterinario = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtNomeConsultaVet = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCpfConsultaVet = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSenhaConsultaVet = new javax.swing.JPasswordField();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        txtEmailConsultaVet = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cbxClienteCadastroServico = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cbxVeterinarioCadastroServico = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtTipoCadastro = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        sldHorasCadastro = new javax.swing.JSlider();
        lblHorasCadastro = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnCadastrarServico = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescCadastro = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cbxVeterinarioBuscaServico = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cbxClientesVeterinario = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        txtTipoBusca = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        sldHorasBusca = new javax.swing.JSlider();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescBusca = new javax.swing.JTextArea();
        lblHorasBusca = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        btnUpdateServico = new javax.swing.JButton();
        btnExcluirServico = new javax.swing.JButton();
        btnBuscarServico = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("É o Bixo - Veterinária");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Razão Social:");

        txtRazaoSocialCadastroCl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("CNPJ:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Endereço:");

        txtEnderecoCadastroCl.setColumns(20);
        txtEnderecoCadastroCl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEnderecoCadastroCl.setRows(5);
        jScrollPane1.setViewportView(txtEnderecoCadastroCl);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLimparCadastroCl.setFont(new java.awt.Font("Segoe UI Symbol", 0, 14)); // NOI18N
        btnLimparCadastroCl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/limpar.png"))); // NOI18N
        btnLimparCadastroCl.setText("Limpar");
        btnLimparCadastroCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCadastroClActionPerformed(evt);
            }
        });

        btnCadastrarCl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCadastrarCl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/cadastrar.png"))); // NOI18N
        btnCadastrarCl.setText("Cadastrar");
        btnCadastrarCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarClActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLimparCadastroCl, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadastrarCl, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadastrarCl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimparCadastroCl, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap())
        );

        try {
            txtCnpjCadastroCl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCnpjCadastroCl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRazaoSocialCadastroCl)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCnpjCadastroCl, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazaoSocialCadastroCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCnpjCadastroCl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Consulta"));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Cliente:");

        txtRazaoSocialBuscaCl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbxClienteBusca.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxClienteBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1" }));
        cbxClienteBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxClienteBuscaActionPerformed(evt);
            }
        });

        btnBuscarCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/buscar.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Razão Social:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("CNPJ:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Endereço:");

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAtualizarCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAtualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/atualizar.png"))); // NOI18N
        btnAtualizarCliente.setText("Atualizar");
        btnAtualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarClienteActionPerformed(evt);
            }
        });

        btnDeletarCliente.setBackground(new java.awt.Color(255, 0, 0));
        btnDeletarCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeletarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/deletar.png"))); // NOI18N
        btnDeletarCliente.setText("Deletar");
        btnDeletarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAtualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeletarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAtualizarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnDeletarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtEnderecoBuscaCl.setColumns(20);
        txtEnderecoBuscaCl.setRows(5);
        jScrollPane2.setViewportView(txtEnderecoBuscaCl);

        try {
            txtCnpjBuscaCl.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCnpjBuscaCl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtRazaoSocialBuscaCl)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbxClienteBusca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarCliente))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtCnpjBuscaCl))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxClienteBusca))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazaoSocialBuscaCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCnpjBuscaCl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Clientes", jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro"));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nome:");

        txtNomeCadastroVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("CPF:");

        try {
            txtCpfCadastroVet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpfCadastroVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Senha:");

        txtSenhaCadastroVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Email:");

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLimparCadastroVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLimparCadastroVet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/limpar.png"))); // NOI18N
        btnLimparCadastroVet.setText("Limpar");
        btnLimparCadastroVet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCadastroVetActionPerformed(evt);
            }
        });

        btnCadastrarVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCadastrarVet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/cadastrar.png"))); // NOI18N
        btnCadastrarVet.setText("Cadastrar");
        btnCadastrarVet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarVetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLimparCadastroVet, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadastrarVet, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimparCadastroVet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadastrarVet, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Confirmar senha:");

        txtConfirmSenhaCadastroVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnShowPass.setFont(new java.awt.Font("Segoe UI Symbol", 0, 24)); // NOI18N
        btnShowPass.setText("🙈");
        btnShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowPassActionPerformed(evt);
            }
        });

        txtEmailCadastroVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmailCadastroVet)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCpfCadastroVet, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNomeCadastroVet)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(286, 286, 286))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtConfirmSenhaCadastroVet)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtSenhaCadastroVet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShowPass))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeCadastroVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpfCadastroVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmailCadastroVet, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnShowPass, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtSenhaCadastroVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmSenhaCadastroVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Consulta"));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Veterinário:");

        cbxVeterinario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxVeterinario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/buscar.png"))); // NOI18N
        jButton7.setText("Buscar");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Nome:");

        txtNomeConsultaVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("CPF:");

        try {
            txtCpfConsultaVet.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpfConsultaVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Senha:");

        txtSenhaConsultaVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Email:");

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/atualizar.png"))); // NOI18N
        jButton8.setText("Atualizar");

        jButton9.setBackground(new java.awt.Color(255, 0, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/deletar.png"))); // NOI18N
        jButton9.setText("Deletar");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtEmailConsultaVet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeConsultaVet)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cbxVeterinario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addComponent(txtCpfConsultaVet)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEmailConsultaVet)
                    .addComponent(txtSenhaConsultaVet)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeConsultaVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpfConsultaVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmailConsultaVet, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSenhaConsultaVet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Veterinários", jPanel1);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro"));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("Cliente:");

        cbxClienteCadastroServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxClienteCadastroServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Veterinário:");

        cbxVeterinarioCadastroServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxVeterinarioCadastroServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Tipo de serviço:");

        txtTipoCadastro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("Horas:");

        sldHorasCadastro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sldHorasCadastro.setMajorTickSpacing(10);
        sldHorasCadastro.setValue(0);
        sldHorasCadastro.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldHorasCadastroStateChanged(evt);
            }
        });

        lblHorasCadastro.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHorasCadastro.setText("0");

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/limpar.png"))); // NOI18N
        jButton1.setText("Limpar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCadastrarServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCadastrarServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/cadastrar.png"))); // NOI18N
        btnCadastrarServico.setText("Cadastrar");
        btnCadastrarServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadastrarServico, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadastrarServico, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Descrição:");

        txtDescCadastro.setColumns(20);
        txtDescCadastro.setRows(5);
        jScrollPane3.setViewportView(txtDescCadastro);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTipoCadastro)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHorasCadastro))
                            .addComponent(jLabel20)
                            .addComponent(jLabel22))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxVeterinarioCadastroServico, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(cbxClienteCadastroServico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane3)
                    .addComponent(sldHorasCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxClienteCadastroServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxVeterinarioCadastroServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lblHorasCadastro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldHorasCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Busca"));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Veterinário:");

        cbxVeterinarioBuscaServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxVeterinarioBuscaServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setText("Cliente:");

        cbxClientesVeterinario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setText("Tipo de serviço:");

        txtTipoBusca.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("Horas:");

        sldHorasBusca.setValue(0);
        sldHorasBusca.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldHorasBuscaStateChanged(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Descrição:");

        txtDescBusca.setColumns(20);
        txtDescBusca.setRows(5);
        jScrollPane4.setViewportView(txtDescBusca);

        lblHorasBusca.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHorasBusca.setText("0");

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnUpdateServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUpdateServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/atualizar.png"))); // NOI18N
        btnUpdateServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateServicoActionPerformed(evt);
            }
        });

        btnExcluirServico.setBackground(new java.awt.Color(255, 0, 0));
        btnExcluirServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExcluirServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/deletar.png"))); // NOI18N
        btnExcluirServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExcluirServico, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(btnUpdateServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUpdateServico, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluirServico, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnBuscarServico.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBuscarServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icones/buscar.png"))); // NOI18N
        btnBuscarServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxClientesVeterinario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTipoBusca)
                    .addComponent(sldHorasBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHorasBusca))
                            .addComponent(jLabel27)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(cbxVeterinarioBuscaServico, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarServico)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxVeterinarioBuscaServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarServico))
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxClientesVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lblHorasBusca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldHorasBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Serviços", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowPassActionPerformed
        if (btnShowPass.isSelected()) {
            btnShowPass.setText("🙉");
            txtSenhaCadastroVet.setEchoChar((char) 0);
            txtConfirmSenhaCadastroVet.setEchoChar((char) 0);
        } else {
            btnShowPass.setText("🙈");
            txtSenhaCadastroVet.setEchoChar('*');
            txtConfirmSenhaCadastroVet.setEchoChar('*');
        }
    }//GEN-LAST:event_btnShowPassActionPerformed

    private void btnCadastrarVetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarVetActionPerformed
        Veterinario novoVeterinario = new Veterinario();
        if (!txtNomeCadastroVet.getText().equals("")
                && !txtCpfCadastroVet.getText().equals("")
                && !txtSenhaCadastroVet.getText().equals("")
                && !txtConfirmSenhaCadastroVet.getText().equals("")
                && !txtEmailCadastroVet.getText().equals("")) {

            this.CadastrarVeterinario(novoVeterinario);
            UpdateCbxVeterinario();
        } else {
            JOptionPane.showMessageDialog(null, "Preencha corretamente o formulário.", "Cadastro Veterinário", HEIGHT);
        }
    }//GEN-LAST:event_btnCadastrarVetActionPerformed

    private void btnLimparCadastroVetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCadastroVetActionPerformed
        this.LimparCadastroVeterinario();
    }//GEN-LAST:event_btnLimparCadastroVetActionPerformed

    private void btnCadastrarClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarClActionPerformed
        Cliente novoCliente = new Cliente();
        if (!txtRazaoSocialCadastroCl.getText().equals("") && !txtCnpjCadastroCl.getText().equals("") && !txtEnderecoCadastroCl.getText().equals("")) {
            cadastrarCliente(novoCliente);
            UpdateCbxCliente();
        } else {
            JOptionPane.showMessageDialog(null, "Preencha corretamente o formulário.", "Cadastro Cliente", HEIGHT);
        }
    }//GEN-LAST:event_btnCadastrarClActionPerformed

    private void btnLimparCadastroClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCadastroClActionPerformed
        LimparCadastroCliente();
    }//GEN-LAST:event_btnLimparCadastroClActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        this.conn.conectaBanco();
        try {

            String client_name = String.valueOf(cbxClienteBusca.getSelectedItem());

            this.conn.executarSQL("SELECT Razao_social, Cnpj, Endereco FROM cliente WHERE Razao_social = '" + client_name + "'");

            while (this.conn.getResultSet().next()) {
                txtRazaoSocialBuscaCl.setText(this.conn.getResultSet().getString("Razao_social"));
                txtCnpjBuscaCl.setText(this.conn.getResultSet().getString("Cnpj"));
                txtEnderecoBuscaCl.setText(this.conn.getResultSet().getString("Endereco"));
            }

            this.conn.fechaBanco();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnAtualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarClienteActionPerformed
        Cliente novoCliente = new Cliente();
        AtualizarCliente(novoCliente);
    }//GEN-LAST:event_btnAtualizarClienteActionPerformed

    private void btnDeletarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarClienteActionPerformed
        ExcluirCliente();
        UpdateCbxCliente();
    }//GEN-LAST:event_btnDeletarClienteActionPerformed

    private void cbxClienteBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxClienteBuscaActionPerformed
        //System.out.println(cbxClienteBusca.getSelectedItem());
    }//GEN-LAST:event_cbxClienteBuscaActionPerformed

    private void sldHorasCadastroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldHorasCadastroStateChanged
        lblHorasCadastro.setText(Integer.toString(sldHorasCadastro.getValue()));
    }//GEN-LAST:event_sldHorasCadastroStateChanged

    private void sldHorasBuscaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldHorasBuscaStateChanged
        lblHorasBusca.setText(Integer.toString(sldHorasBusca.getValue()));
    }//GEN-LAST:event_sldHorasBuscaStateChanged

    private void btnCadastrarServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarServicoActionPerformed
        Servico novoServico = new Servico();
        CadastrarServico(novoServico);
    }//GEN-LAST:event_btnCadastrarServicoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LimpaCadastroServico();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUpdateServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateServicoActionPerformed
        Servico novoServico = new Servico();
        updateServico(novoServico);
    }//GEN-LAST:event_btnUpdateServicoActionPerformed

    private void btnBuscarServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarServicoActionPerformed
        getServico();
    }//GEN-LAST:event_btnBuscarServicoActionPerformed

    private void btnExcluirServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirServicoActionPerformed
        deleteServico();
    }//GEN-LAST:event_btnExcluirServicoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UISistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UISistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UISistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UISistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UISistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarCliente;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarServico;
    private javax.swing.JButton btnCadastrarCl;
    private javax.swing.JButton btnCadastrarServico;
    private javax.swing.JButton btnCadastrarVet;
    private javax.swing.JButton btnDeletarCliente;
    private javax.swing.JButton btnExcluirServico;
    private javax.swing.JButton btnLimparCadastroCl;
    private javax.swing.JButton btnLimparCadastroVet;
    private javax.swing.JToggleButton btnShowPass;
    private javax.swing.JButton btnUpdateServico;
    private javax.swing.JComboBox<String> cbxClienteBusca;
    private javax.swing.JComboBox<String> cbxClienteCadastroServico;
    private javax.swing.JComboBox<String> cbxClientesVeterinario;
    private javax.swing.JComboBox<String> cbxVeterinario;
    private javax.swing.JComboBox<String> cbxVeterinarioBuscaServico;
    private javax.swing.JComboBox<String> cbxVeterinarioCadastroServico;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblHorasBusca;
    private javax.swing.JLabel lblHorasCadastro;
    private javax.swing.JSlider sldHorasBusca;
    private javax.swing.JSlider sldHorasCadastro;
    private javax.swing.JFormattedTextField txtCnpjBuscaCl;
    private javax.swing.JFormattedTextField txtCnpjCadastroCl;
    private javax.swing.JPasswordField txtConfirmSenhaCadastroVet;
    private javax.swing.JFormattedTextField txtCpfCadastroVet;
    private javax.swing.JFormattedTextField txtCpfConsultaVet;
    private javax.swing.JTextArea txtDescBusca;
    private javax.swing.JTextArea txtDescCadastro;
    private javax.swing.JTextField txtEmailCadastroVet;
    private javax.swing.JTextField txtEmailConsultaVet;
    private javax.swing.JTextArea txtEnderecoBuscaCl;
    private javax.swing.JTextArea txtEnderecoCadastroCl;
    private javax.swing.JTextField txtNomeCadastroVet;
    private javax.swing.JTextField txtNomeConsultaVet;
    private javax.swing.JTextField txtRazaoSocialBuscaCl;
    private javax.swing.JTextField txtRazaoSocialCadastroCl;
    private javax.swing.JPasswordField txtSenhaCadastroVet;
    private javax.swing.JPasswordField txtSenhaConsultaVet;
    private javax.swing.JTextField txtTipoBusca;
    private javax.swing.JTextField txtTipoCadastro;
    // End of variables declaration//GEN-END:variables
}
