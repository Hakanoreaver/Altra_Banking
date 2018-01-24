
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.*;import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.*;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hakanoreaver
 */
public class aOnly extends javax.swing.JPanel implements DocumentListener{

    boolean checking = false;
    File file;
    Desktop desktop;
    
    private void initListeners() {
        aCashField.getDocument().addDocumentListener(this);
        aCashField.getDocument().putProperty("owner", aCashField);
        aVarField.getDocument().addDocumentListener(this);
        aVarField.getDocument().putProperty("owner", aVarField);
        aCardField.getDocument().addDocumentListener(this);
        aCardField.getDocument().putProperty("owner", aCardField);
        aNoSalesField.getDocument().addDocumentListener(this);
        aNoSalesField.getDocument().putProperty("owner", aNoSalesField);
        aBankedField.getDocument().addDocumentListener(this);
        aBankedField.getDocument().putProperty("owner", aBankedField);
        
        aCashField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });
        
        aVarField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_MINUS)) {
                    e.consume();  // ignore event
                }
            }
        });
        
        aCardField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });
        
        aCustomersField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });
        
        aNoSalesField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });
        
        aBankedField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });
    }
    
    
    private void check(DocumentEvent ev) {

    Runnable doHighlight = new Runnable() {
        @Override
        public void run() {
            
            if (!checking) {
                
            JTextField txtField = (JTextField) ev.getDocument().getProperty("owner");
            String check = txtField.getText();
            
            if (check.length() == 1) {
                Double ss = Double.parseDouble(txtField.getText());
                String s = String.format("%1$,.2f", (ss/100));
                txtField.setText(s);
            }
            else {
                Double ss = Double.parseDouble(txtField.getText());
                String s = String.format("%1$,.2f", (ss*10));
                txtField.setText(s);
            }
            
            }
            System.out.println("String changed");
            checking = true;
        }
    };       
    SwingUtilities.invokeLater(doHighlight);
}
    
    public void insertUpdate(DocumentEvent ev) {
        check(ev);
        checking = false;
    }
    
    public void removeUpdate(DocumentEvent ev) {
        check(ev);
        checking = false;
    }
    
    public void changedUpdate(DocumentEvent ev) {
        
    }
    
    private void initResources() {
        file = new File("Banking.txt");
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            System.out.println("done");
        }
    }
    /**
     * Creates new form aOnly
     */
    public aOnly() {
        initComponents();
        initListeners();
        initResources();
    }
    
    private void print() throws IOException {
        
        Double aCash, aVar, aCard, aNoSales, aBanked;
        int aCustomers;
        
        try {
             aCash = Double.parseDouble(aCashField.getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }
        try {
             aVar = Double.parseDouble(aVarField.getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }
        try {
             aCard = Double.parseDouble(aCardField.getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }
        try {
             aCustomers = Integer.parseInt(aCustomersField.getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }
        try {
             aNoSales = Double.parseDouble(aNoSalesField.getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }
        try {
            aBanked = Double.parseDouble(aBankedField.getText());
        } catch (NumberFormatException numberFormatException) {
            return;
        }
        
        LocalDate a = LocalDate.now();
        
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(file))) {
            buff.write("Date - " + a + "/r/n");
            buff.write("Till A Takings Report \r\n");
            buff.write("Total Cash - " + aCash);
            buff.write("Total Variance - " + aVar + "/r/n");
            buff.write("Total Credit - " + aCard + "/r/n");
            buff.write("Total Customers - " + aCustomers + "/r/n");
            buff.write("Total No Sales- " + aNoSales + "/r/n");
            buff.write("Total Banked - " + aBanked + "/r/n");
            buff.write("Days Total - " + (aCash + aCard - aNoSales) + "/r/n");
            buff.write("Average - " + String.format("%1$,.2f", ((aCash + aCard - aNoSales)/aCustomers)) + "/r/n");
        }
       
        desktop.print(file);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        aCashField = new javax.swing.JTextField();
        aVarField = new javax.swing.JTextField();
        aCardField = new javax.swing.JTextField();
        aCustomersField = new javax.swing.JTextField();
        aNoSalesField = new javax.swing.JTextField();
        aBankedField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Print");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Till A", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        jPanel1.setLayout(null);

        jPanel2.setLayout(new java.awt.GridLayout(6, 1));

        aCashField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aCashField.setNextFocusableComponent(aVarField);
        aCashField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aCashFieldActionPerformed(evt);
            }
        });
        jPanel2.add(aCashField);

        aVarField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aVarField.setNextFocusableComponent(aCardField);
        jPanel2.add(aVarField);

        aCardField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aCardField.setNextFocusableComponent(aCustomersField);
        aCardField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aCardFieldActionPerformed(evt);
            }
        });
        jPanel2.add(aCardField);

        aCustomersField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aCustomersField.setNextFocusableComponent(aNoSalesField);
        jPanel2.add(aCustomersField);

        aNoSalesField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        aNoSalesField.setNextFocusableComponent(aBankedField);
        aNoSalesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aNoSalesFieldActionPerformed(evt);
            }
        });
        jPanel2.add(aNoSalesField);

        aBankedField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jPanel2.add(aBankedField);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(90, 30, 190, 260);

        jPanel3.setLayout(new java.awt.GridLayout(6, 1));

        jLabel1.setText("Cash");
        jPanel3.add(jLabel1);

        jLabel2.setText("Variance");
        jPanel3.add(jLabel2);

        jLabel3.setText("Card");
        jPanel3.add(jLabel3);

        jLabel4.setText("Customers");
        jPanel3.add(jLabel4);

        jLabel5.setText("No Sales");
        jPanel3.add(jLabel5);

        jLabel6.setText("Banked");
        jPanel3.add(jLabel6);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 30, 80, 260);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Till A Only");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(283, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Main.showCard("4");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void aCashFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aCashFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aCashFieldActionPerformed

    private void aCardFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aCardFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aCardFieldActionPerformed

    private void aNoSalesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aNoSalesFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aNoSalesFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aBankedField;
    private javax.swing.JTextField aCardField;
    private javax.swing.JTextField aCashField;
    private javax.swing.JTextField aCustomersField;
    private javax.swing.JTextField aNoSalesField;
    private javax.swing.JTextField aVarField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
