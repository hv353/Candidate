package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLDiemCongGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtCCCD, txtNganh, txtToHop;
    JTextField txtDiemCC, txtDiemUT, txtTong;
    JTextArea txtGhiChu;

    JComboBox<String> cbPhuongThuc;

    JButton btnImport, btnAdd, btnEdit, btnDelete, btnRefresh;

    public QLDiemCongGUI(){

        setTitle("Quản lý điểm cộng");
        setSize(1100,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- INPUT PANEL -------- */

        JPanel inputPanel = new JPanel(new GridLayout(4,4,10,10));

        txtCCCD = new JTextField();
        txtNganh = new JTextField();
        txtToHop = new JTextField();

        txtDiemCC = new JTextField();
        txtDiemUT = new JTextField();
        txtTong = new JTextField();

        txtGhiChu = new JTextArea(2,20);

        cbPhuongThuc = new JComboBox<>(new String[]{
                "THPT","VSAT","DGNL"
        });

        inputPanel.add(new JLabel("CCCD"));
        inputPanel.add(txtCCCD);

        inputPanel.add(new JLabel("Ngành"));
        inputPanel.add(txtNganh);

        inputPanel.add(new JLabel("Tổ hợp"));
        inputPanel.add(txtToHop);

        inputPanel.add(new JLabel("Phương thức"));
        inputPanel.add(cbPhuongThuc);

        inputPanel.add(new JLabel("Điểm cộng"));
        inputPanel.add(txtDiemCC);

        inputPanel.add(new JLabel("Điểm ưu tiên"));
        inputPanel.add(txtDiemUT);

        inputPanel.add(new JLabel("Điểm tổng"));
        inputPanel.add(txtTong);

        add(inputPanel, BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID","CCCD","Ngành","Tổ hợp","PT", "dc_key",
                "Điểm CC","Điểm UT","Tổng","Ghi chú"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        /* -------- BUTTON PANEL -------- */

        JPanel buttonPanel = new JPanel();

        btnImport = new JButton("Import");
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnImport);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        /* -------- DEMO -------- */

        model.addRow(new Object[]{
                1,
                "012345678",
                "7480201",
                "A00",
                "THPT",
                "????",
                1.5,
                0.5,
                26.0,
                "Ưu tiên KV1"
        });

        /* -------- AUTO TÍNH TỔNG -------- */

        txtDiemCC.addActionListener(e -> tinhTong());
        txtDiemUT.addActionListener(e -> tinhTong());

    }

    /* -------- HÀM TÍNH TỔNG -------- */

    public void tinhTong(){

        try{

            double cc = Double.parseDouble(txtDiemCC.getText());
            double ut = Double.parseDouble(txtDiemUT.getText());

            txtTong.setText(String.valueOf(cc + ut));

        }catch(Exception e){
            txtTong.setText("0");
        }

    }

    public static void main(String[] args) {

        new QLDiemCongGUI().setVisible(true);

    }
}