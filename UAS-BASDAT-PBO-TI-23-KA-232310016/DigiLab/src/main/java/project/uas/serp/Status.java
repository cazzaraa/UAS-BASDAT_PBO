package project.uas.serp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Status {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DigiLib";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void FrameStatus() {
        JFrame fmes = new JFrame("Pinjam Buku");
        fmes.setSize(300, 350);
        fmes.setLocationRelativeTo(null);
        fmes.setLayout(null);
        fmes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(0, 0, 300, 40);
        fmes.add(panel);

        JLabel Judul = new JLabel("Pinjam Buku");
        Judul.setBounds(90, 10, 150, 20);
        Judul.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(Judul);

        JLabel idpengunjung = new JLabel("ID Pengunjung");
        idpengunjung.setBounds(25, 60, 90, 15);
        fmes.add(idpengunjung);
        
        JLabel idbook = new JLabel("ID Buku");
        idbook.setBounds(25, 100, 80, 15);
        fmes.add(idbook);

        JLabel pinjam = new JLabel("Tgl Pinjam");
        pinjam.setBounds(25, 140, 80, 15);
        fmes.add(pinjam);

        JLabel kembali = new JLabel("Tgl Kembali");
        kembali.setBounds(25, 180, 80, 15);
        fmes.add(kembali);

        JTextField TxPengunjung = new JTextField();
        TxPengunjung.setBounds(130, 60, 120, 20);
        fmes.add(TxPengunjung);
        
        JTextField TxIdbook = new JTextField();
        TxIdbook.setBounds(130, 100, 120, 20);
        fmes.add(TxIdbook);

        JTextField TxPinjam = new JTextField();
        TxPinjam.setBounds(130, 140, 120, 20);
        fmes.add(TxPinjam);

        JTextField TxKembali = new JTextField();
        TxKembali.setBounds(130, 180, 120, 20);
        fmes.add(TxKembali);

        JButton PinjamBuku = new JButton("Submit");
        PinjamBuku.setBounds(100, 240, 100, 30);
        fmes.add(PinjamBuku);

        PinjamBuku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int Vpengunjung = Integer.parseInt(TxPengunjung.getText());
                    int Vbook = Integer.parseInt(TxIdbook.getText());
                    Date Vpinjam = convertToDate(TxPinjam.getText());
                    Date Vkembali = convertToDate(TxKembali.getText());

                    if (!isPengunjungExists(Vpengunjung)) {
                        JOptionPane.showMessageDialog(fmes, "ID Pengunjung Tidak Ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String sql = "UPDATE Status SET status_pinjam = 'dipinjam', idPengunjung = ?, tanggal_pinjam = ?, tanggal_kembali = ? WHERE idBuku = ?";
                        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                            statement.setInt(1, Vpengunjung);
                            statement.setDate(2, Vpinjam);
                            statement.setDate(3, Vkembali);
                            statement.setInt(4, Vbook);

                            int rowsUpdated = statement.executeUpdate();
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(fmes, "Berhasil Dipinjam!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                fmes.dispose();
                            } else {
                                JOptionPane.showMessageDialog(fmes, "ID Buku Tidak Ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(fmes, "Input Harus Berupa Integer", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(fmes, "Input Harus Berupa Tahun-Bulan-Tanggal", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fmes, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private boolean isPengunjungExists(int idPengunjung) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "SELECT COUNT(*) FROM pengunjungPerpustakaan WHERE idPengunjung = ?";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setInt(1, idPengunjung);
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                return resultSet.getInt(1) > 0;
                            }
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                return false;
            }

            private Date convertToDate(String dateTx) throws ParseException {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date tanggal = format.parse(dateTx);
                return new Date(tanggal.getTime());
            }
        });

        fmes.setVisible(true);
    }
}
