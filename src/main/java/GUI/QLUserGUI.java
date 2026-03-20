package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BUS.UserBUS;

import java.awt.*;

import entity.Role;
import entity.user;
import java.util.List;



public class QLUserGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JButton btnEdit;
    JButton btnPassword;
    JButton btnRole;
    JButton btnEnableDisable;
    JButton btnRefresh;

    JTextField txtSearch;
    UserBUS bus = new UserBUS();

    public QLUserGUI(){

        setTitle("Admin - Quản lý người dùng");
        setSize(900,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- TOP PANEL -------- */

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel lblSearch = new JLabel("Search:");
        txtSearch = new JTextField(20);

        btnRefresh = new JButton("Refresh");

        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnRefresh);

        add(topPanel,BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columnNames = {
                "ID",
                "Username",
                "Role",
                "Status"
        };

        model = new DefaultTableModel(columnNames,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);

        /* -------- BUTTON PANEL -------- */

        JPanel buttonPanel = new JPanel();

        btnEdit = new JButton("Edit");

        btnPassword = new JButton("Change Password");

        btnRole = new JButton("Change Role");

        btnEnableDisable = new JButton("Enable / Disable");

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnPassword);
        buttonPanel.add(btnRole);
        buttonPanel.add(btnEnableDisable);

        add(buttonPanel,BorderLayout.SOUTH);

        /* ---- Demo data (sau này xóa) ---- */

        loadData();
        btnRefresh.addActionListener(e -> {
            loadData();
        });
        btnEnableDisable.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn user trước!");
                return;
            }

            int id = (int) table.getValueAt(row, 0);

            boolean success = bus.toggleStatus(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Thất bại!");
            }
        });

    }
    public void loadData() {
        model.setRowCount(0);

      
     
        List<user> list = bus.getAllUser();

        // Duyệt list và add vào table
        for (user u : list) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getUsername(),
                    u.getRole() == Role.ADMIN ? "Admin" : "User",
                    u.getIsStatus() == 1 ? "Enabled" : "Disabled"
            });
        }
    }

}