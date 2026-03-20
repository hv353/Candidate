package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLQuyDoiGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JComboBox<String> cbPT, cbToHop, cbMon;

    JTextField txtA, txtB, txtC, txtD;
    JTextField txtMaQD, txtPhanVi, txtSearch;

    JButton btnImport, btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    public QLQuyDoiGUI(){

        setTitle("Quản lý bảng quy đổi");
        setSize(1100,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- INPUT PANEL -------- */

        JPanel panel = new JPanel(new GridLayout(4,4,10,10));

        cbPT = new JComboBox<>(new String[]{"THPT","VSAT","DGNL"});
        cbToHop = new JComboBox<>(new String[]{"A00","A01","D01"});
        cbMon = new JComboBox<>(new String[]{"TO","LI","HO","VA"});

        txtA = new JTextField();
        txtB = new JTextField();
        txtC = new JTextField();
        txtD = new JTextField();

        txtMaQD = new JTextField();
        txtPhanVi = new JTextField();

        panel.add(new JLabel("Phương thức"));
        panel.add(cbPT);

        panel.add(new JLabel("Tổ hợp"));
        panel.add(cbToHop);

        panel.add(new JLabel("Môn"));
        panel.add(cbMon);

        panel.add(new JLabel("Điểm A"));
        panel.add(txtA);

        panel.add(new JLabel("Điểm B"));
        panel.add(txtB);

        panel.add(new JLabel("Điểm C"));
        panel.add(txtC);

        panel.add(new JLabel("Điểm D"));
        panel.add(txtD);

        panel.add(new JLabel("Mã quy đổi"));
        panel.add(txtMaQD);

        panel.add(new JLabel("Phân vị"));
        panel.add(txtPhanVi);

        add(panel, BorderLayout.NORTH);

        /* -------- SEARCH PANEL -------- */

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");

        searchPanel.add(new JLabel("Search"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID","PT","Tổ hợp","Môn",
                "A","B","C","D",
                "Mã QĐ","Phân vị"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        /* -------- BUTTON -------- */

        JPanel btnPanel = new JPanel();

        btnImport = new JButton("Import");
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Refresh");

        btnPanel.add(btnImport);
        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);

        add(btnPanel, BorderLayout.SOUTH);

        /* -------- DEMO -------- */

        model.addRow(new Object[]{
                1,"DGNL","A00","TO",
                700,800,900,1000,
                "QD01","Top 10%"
        });

    }

    public static void main(String[] args) {

        new QLQuyDoiGUI().setVisible(true);

    }
}