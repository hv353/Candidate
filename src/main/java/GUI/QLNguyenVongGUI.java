package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLNguyenVongGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtCCCD, txtNV;
    JTextField txtDiemThi, txtDiemUT, txtDiemCong, txtTong;

    JComboBox<String> cbNganh, cbToHop, cbPT;

    JButton btnAdd, btnEdit, btnDelete, btnXetTuyen, btnRefresh;

    public QLNguyenVongGUI(){

        setTitle("Quản lý nguyện vọng & xét tuyển");
        setSize(1200,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- INPUT PANEL -------- */

        JPanel panel = new JPanel(new GridLayout(5,3,10,10));

        txtCCCD = new JTextField();
        txtNV = new JTextField();

        txtDiemThi = new JTextField();
        txtDiemUT = new JTextField();
        txtDiemCong = new JTextField();
        txtTong = new JTextField();

        txtTong.setEditable(false);

        cbNganh = new JComboBox<>(new String[]{
                "7480201","7340101"
        });

        cbToHop = new JComboBox<>(new String[]{
                "A00","A01","D01"
        });

        cbPT = new JComboBox<>(new String[]{
                "THPT","VSAT","DGNL"
        });

        panel.add(new JLabel("CCCD"));
        panel.add(txtCCCD);

        panel.add(new JLabel("Ngành"));
        panel.add(cbNganh);

        panel.add(new JLabel("Nguyện vọng"));
        panel.add(txtNV);

        panel.add(new JLabel("Tổ hợp"));
        panel.add(cbToHop);

        panel.add(new JLabel("Phương thức"));
        panel.add(cbPT);

        panel.add(new JLabel("Điểm thi"));
        panel.add(txtDiemThi);

        panel.add(new JLabel("Điểm UT"));
        panel.add(txtDiemUT);

        panel.add(new JLabel("Điểm cộng"));
        panel.add(txtDiemCong);

        panel.add(new JLabel("Tổng"));
        panel.add(txtTong);

        add(panel, BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID","CCCD","Ngành","NV","Tổ hợp","PT",
                "Điểm XT","Kết quả"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        /* -------- BUTTON -------- */

        JPanel btnPanel = new JPanel();

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnXetTuyen = new JButton("Xét tuyển");
        btnRefresh = new JButton("Refresh");

        btnPanel.add(btnAdd);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);
        btnPanel.add(btnXetTuyen);
        btnPanel.add(btnRefresh);

        add(btnPanel, BorderLayout.SOUTH);

        /* -------- AUTO TÍNH -------- */

        txtDiemThi.addActionListener(e -> tinhTong());
        txtDiemUT.addActionListener(e -> tinhTong());
        txtDiemCong.addActionListener(e -> tinhTong());

        /* -------- DEMO -------- */

        model.addRow(new Object[]{
                1,"012345678","7480201",1,"A00","THPT",26.5,"Đậu"
        });

    }

    public void tinhTong(){

        try{
            double a = Double.parseDouble(txtDiemThi.getText());
            double b = Double.parseDouble(txtDiemUT.getText());
            double c = Double.parseDouble(txtDiemCong.getText());

            txtTong.setText(String.valueOf(a + b + c));
        }catch(Exception e){
            txtTong.setText("0");
        }

    }

    public static void main(String[] args) {

        new QLNguyenVongGUI().setVisible(true);

    }
}