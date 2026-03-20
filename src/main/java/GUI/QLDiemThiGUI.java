package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLDiemThiGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtCCCD, txtSBD;

    JComboBox<String> cbPhuongThuc;

    JButton btnImport, btnAdd, btnEdit, btnDelete, btnRefresh, btnThongKe;

    public QLDiemThiGUI(){

        setTitle("Quản lý điểm thí sinh");
        setSize(1200,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- TOP PANEL -------- */

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtCCCD = new JTextField(10);
        txtSBD = new JTextField(10);

        cbPhuongThuc = new JComboBox<>(new String[]{
                "THPT", "VSAT", "DGNL"
        });

        btnImport = new JButton("Import");
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Refresh");
        btnThongKe = new JButton("Thống kê");

        topPanel.add(new JLabel("CCCD"));
        topPanel.add(txtCCCD);

        topPanel.add(new JLabel("SBD"));
        topPanel.add(txtSBD);

        topPanel.add(new JLabel("Phương thức"));
        topPanel.add(cbPhuongThuc);

        topPanel.add(btnImport);
        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);
        topPanel.add(btnRefresh);
        topPanel.add(btnThongKe);

        add(topPanel, BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID","CCCD","SBD","PT",
                "TO","LI","HO","SI","SU","DI","VA",
                "NL1","NK1","NK2","TI","KTPL"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        /* -------- DEMO DATA -------- */

        model.addRow(new Object[]{
                1,
                "012345678",
                "SBD01",
                1,
                8.5,7.0,6.5,0,0,0,7.5,
                0,0,0,0,0
        });

    }

    public static void main(String[] args) {

        new QLDiemThiGUI().setVisible(true);

    }
}