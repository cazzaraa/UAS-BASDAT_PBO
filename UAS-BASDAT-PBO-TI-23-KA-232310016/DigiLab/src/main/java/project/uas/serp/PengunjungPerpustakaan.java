package project.uas.serp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PengunjungPerpustakaan {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DigiLib";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void Masuk() {
        JFrame fmm = new JFrame();
        fmm.setSize(300, 250);
        fmm.setLayout(null);
        fmm.setLocationRelativeTo(null);
        fmm.setDefaultCloseOperation(fmm.EXIT_ON_CLOSE);

        JLabel Email = new JLabel("Username");
        Email.setBounds(30, 30, 60, 15);
        fmm.add(Email);

        JLabel Password = new JLabel("Password");
        Password.setBounds(30, 80, 60, 15);
        fmm.add(Password);

        JButton Login = new JButton("Login");
        Login.setBounds(100, 140, 80, 30);
        fmm.add(Login);

        JTextField TxEmail = new JTextField();
        TxEmail.setBounds(120, 30, 120, 20);
        fmm.add(TxEmail);

        JPasswordField TxPassword = new JPasswordField();
        TxPassword.setBounds(120, 80, 120, 20);
        fmm.add(TxPassword);

        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = TxEmail.getText();
                String password = new String(TxPassword.getPassword());

                if (checkUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Berhasil!");
                    fmm.dispose();
                    Buku Cari = new Buku();
                    Cari.SERPApp();
                } else {
                	JOptionPane.showMessageDialog(null, "Username/Password Salah!");
                }
            }
        });

        fmm.setVisible(true);
    }

    private boolean checkUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM pengunjungPerpustakaan WHERE namaPengunjung = ? AND idPengunjung = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

	public static void main(String[] args) {
		PengunjungPerpustakaan Login = new PengunjungPerpustakaan();
		Login.Masuk();
	}

}
