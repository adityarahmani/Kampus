package frame;

import helpers.Koneksi;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DosenInputFrame extends JFrame {
    private JPanel buttonPanel;
    private JButton simpanButton;
    private JButton batalButton;
    private JTextField idTextField;
    private JTextField namaTextField;
    private JPanel mainPanel;
    private JTextField nimTextField;
    private JTextField alamatTextField;
    private JTextField emailTextField;
    private JTextField teleponTextField;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public DosenInputFrame() {
        batalButton.addActionListener(e -> {
            dispose();
        });

        //simpan
        simpanButton.addActionListener(e -> {

            String nip = nimTextField.getText();

            if (nip.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Isi NIP",
                        "Validasi data kosong",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String nama = namaTextField.getText();

            if (nama.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Isi Nama Dosen",
                        "Validasi data kosong",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String alamat = alamatTextField.getText();

            if (alamat.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Isi Alamat",
                        "Validasi data kosong",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            //validasi email
            String email = emailTextField.getText();
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null,
                        "Isi dengan email yang valid",
                        "Validasi Email",
                        JOptionPane.WARNING_MESSAGE);
                emailTextField.requestFocus();
                return;
            }

            String telepon = teleponTextField.getText();

            if (telepon.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Isi Telepon",
                        "Validasi data kosong",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }


            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if (id == 0) {
                    String cekSQL = "SELECT * FROM dosen WHERE nama = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Data sama sudah ada"
                        );
                    } else {
                        String insertSQL = "INSERT INTO dosen (id, nip, nama, alamat, email, telepon) VALUES (NULL, ?, ?, ?, ?, ?)";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, nip);
                        ps.setString(2, nama);
                        ps.setString(3, alamat);
                        ps.setString(4, email);
                        ps.setString(5, telepon);
                        ps.executeUpdate();
                        dispose();
                    }
                } else {
                    String cekSQL = "SELECT * FROM dosen WHERE nama = ? AND id != ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, nama);
                    ps.setInt(2, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Data sama sudah ada"
                        );
                    } else {
                        String updateSQL = "UPDATE dosen SET nip = ?, nama = ?, alamat = ? , email = ?, telepon = ? WHERE id = ?";
                        ps = c.prepareStatement(updateSQL);
                        ps.setString(1, nip);
                        ps.setString(2, nama);
                        ps.setString(3, alamat);
                        ps.setString(4, email);
                        ps.setString(5, telepon);
                        ps.setInt(6, id);
                        ps.executeUpdate();
                        dispose();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        init();
    }

    public void init() {
        setContentPane(mainPanel);
        setTitle("Input Dosen");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiKomponen() {
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM dosen WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                namaTextField.setText(rs.getString("nama"));
                nimTextField.setText(String.valueOf(rs.getString("nip")));
                alamatTextField.setText(String.valueOf(rs.getString("alamat")));
                emailTextField.setText(String.valueOf(rs.getString("email")));
                teleponTextField.setText(String.valueOf(rs.getString("telepon")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
