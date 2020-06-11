/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcgs.gui;

import com.fcgs.service.util.GCResults;
import com.pids.core.utils.FileDataReader;
import com.pids.core.utils.NumberUtils;
import com.pids.core.utils.PathUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author wuhaotian
 */
public class FcgsResultFrame extends javax.swing.JFrame {

    private Logger log = LoggerFactory.getLogger(FcgsResultFrame.class);

    private GCResults gcResults;
    private String matrixCsvPath;

    public GCResults getGcResults() {
        return gcResults;
    }

    public void setGcResults(GCResults gcResults) {
        this.gcResults = gcResults;

        String[][] datas = {};
        String[] titles = {"Parameter", "Results"};
        DefaultTableModel model = new DefaultTableModel(datas, titles) {
            boolean[] canEdit = new boolean[]{
                    false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        jTable1.setModel(model);
        model.addRow(new String[]{"Data format", gcResults.getTagType()});
        model.addRow(new String[]{"Source fingerprint number", gcResults.getsGeneCount() + ""});
        model.addRow(new String[]{"Contrast fingerprint number", gcResults.gettGeneCount() + ""});

        Integer minDiff = gcResults.getMinDiff();
        String min = minDiff == null ? "" : minDiff + "";
        model.addRow(new String[]{"Minimum number of difference loci", min});

        Integer maxDiff = gcResults.getMaxDiff();
        String max = maxDiff == null ? "" : maxDiff + "";
        model.addRow(new String[]{"Maximum number of difference loci", max});
        model.addRow(new String[]{"Average number of difference loci", gcResults.getAvgDiffCount() + ""});
        model.addRow(new String[]{"Compare result count", gcResults.getResultCount() + ""});
        model.addRow(new String[]{"Total comparison time(ms)", NumberUtils.format(gcResults.getTimes(), 4)});
        model.addRow(new String[]{"Average comparison time(ms)", NumberUtils.format(gcResults.getAvgTimes(), 6)});
        model.addRow(new String[]{"Average time of the difference loci(ms)", NumberUtils.format(gcResults.getAvgDiffTimes(), 6)});

        String classesPath = PathUtils.getCurrentFilePath(this.getClass());
        matrixCsvPath = classesPath.substring(0, classesPath.lastIndexOf("/") + 1) + gcResults.getMatrixCsvPath();

        jScrollPane1.setViewportView(jTable1);
    }

    /**
     * Creates new form FcgsResultFrame
     */
    public FcgsResultFrame() {
        initComponents();

        this.saveDialog = new FileDialog(this, "Save Gene Result File", FileDialog.SAVE);

        // 关闭窗口不退出整个程序
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {},
                        {},
                        {},
                        {}
                },
                new String[]{

                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Download Result");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.saveDialog.setVisible(true);//显示打开文件对话框
        String dirpath = saveDialog.getDirectory();//获取打开文件路径并保存到字符串中。
        if (StringUtils.isBlank(dirpath)) {//判断路径和文件是否为空
            return;
        }

        log.info("开始下载数据比对结果文件...");

        //下载比对结果文件
        try {
            System.out.println(matrixCsvPath);
            File file = new File(matrixCsvPath);
            if (file.isFile() && file.exists()) {
                byte[] contents = FileDataReader.readBytes(file);
                FileUtils.writeByteArrayToFile(new File(dirpath, getFileName()), contents);
            }
        } catch (IOException e) {
            log.error("下载数据比对结果文件出错", e.getMessage());
        }
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * 获取带时间后缀的CSV文件名
     *
     * @return java.lang.String 文件名
     * <br/><br/>
     * Create by WuHaotian on 2020-05-25 09:09:01
     **/
    private String getFileName() {
        String datePrefix = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        return String.format("%s%s.%s", "CompareResult-", datePrefix, "csv");
    }

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
            java.util.logging.Logger.getLogger(FcgsResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FcgsResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FcgsResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FcgsResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FcgsResultFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private FileDialog saveDialog;
}