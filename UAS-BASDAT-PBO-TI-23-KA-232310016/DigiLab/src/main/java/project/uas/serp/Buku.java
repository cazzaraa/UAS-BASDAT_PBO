package project.uas.serp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Buku {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/DigiLib";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void SERPApp() {
        JFrame fme = new JFrame();
        fme.setSize(900, 500);
        fme.setLocationRelativeTo(null);
        fme.setLayout(null);
        fme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(0, 0, 900, 30);
        panel.setOpaque(true);
        fme.add(panel);

        JLabel Judul = new JLabel("Cari Buku");
        Judul.setBounds(300, 0, 400, 30);
        Judul.setFont(new Font("arial", Font.BOLD, 20));
        panel.add(Judul);

        JLabel Kata = new JLabel("Kata Kunci :");
        Kata.setBounds(30, 50, 100, 15);
        Kata.setFont(new Font("arial", Font.ITALIC, 16));
        fme.add(Kata);

        JTextField TxKata = new JTextField();
        TxKata.setBounds(130, 45, 630, 30);
        fme.add(TxKata);

        JButton Search = new JButton("Search");
        Search.setBounds(780, 45, 80, 30);
        fme.add(Search);
        
        JButton pinjam = new JButton("Pinjam Buku");
        pinjam.setBounds(740, 410, 130, 30);
        fme.add(pinjam);

        String[] columnNames = {"ID", "Judul", "Penulis", "Tanggal Pinjam", "Tanggal Kembali", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 100, 840, 300);
        fme.add(scrollPane);
        
        pinjam.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Status StatusBuku = new Status();
				StatusBuku.FrameStatus();
				
			}
		});
        
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = TxKata.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(fme, "Masukkan Kata Kunci Terlebih Dahulu!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "SELECT Buku.idBuku, Buku.judul, Buku.penulis, Status.tanggal_pinjam, Status.tanggal_kembali, Status.status_pinjam "
                            + "FROM Buku "
                            + "JOIN Status ON Status.idBuku = Buku.idBuku "
                            + "WHERE Buku.judul LIKE ?";
                    	
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, "%" + keyword + "%");

                        try (ResultSet resultSet = statement.executeQuery()) {
                            tableModel.setRowCount(0);
                            boolean found = false;

                            while (resultSet.next()) {
                                found = true;
                                int idbuku = resultSet.getInt("idBuku");
                                String judul = resultSet.getString("judul");
                                String penulis = resultSet.getString("penulis");
                                Date tgl_pinjam = resultSet.getDate("tanggal_pinjam");
                                Date tgl_kembali = resultSet.getDate("tanggal_kembali");
                                String status = resultSet.getString("status_pinjam");
                                tableModel.addRow(new Object[]{idbuku, judul, penulis, tgl_pinjam, tgl_kembali, status});
                            }

                            if (!found) {
                                tableModel.addRow(new Object[]{"Tidak ada hasil ditemukan", "", "", "", ""});
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(fme, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fme.setVisible(true);
    }

}