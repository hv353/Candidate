package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLNganhToHopGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JComboBox<String> cbNganh;
    JComboBox<String> cbToHop;

    JTextField txtMon1, txtMon2, txtMon3;
    JTextField txtHS1, txtHS2, txtHS3;
    JTextField txtDoLech;

    JButton btnAdd, btnEdit, btnDelete, btnRefresh;

    public QLNganhToHopGUI(){

        setTitle("Quản lý ngành - tổ hợp");
        setSize(1000,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- INPUT PANEL -------- */

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        cbNganh = new JComboBox<>(new String[]{
                "7480201 - CNTT",
                "7340101 - Quản trị kinh doanh"
        });

        cbToHop = new JComboBox<>(new String[]{
                "A00", "A01", "D01"
        });

        txtMon1 = new JTextField(15);
        txtMon2 = new JTextField(15);
        txtMon3 = new JTextField(15);

        txtHS1 = new JTextField(15);
        txtHS2 = new JTextField(15);
        txtHS3 = new JTextField(15);

        txtDoLech = new JTextField(15);

        JPanel panel1= new JPanel();
        panel1.add(new JLabel("Mã ngành"));
        panel1.add(cbNganh);
        panel1.add(new JLabel("Tổ hợp"));
        panel1.add(cbToHop);
        inputPanel.add(panel1);

        JPanel panel2= new JPanel();
        panel2.add(new JLabel("Môn 1"));
        panel2.add(txtMon1);
        panel2.add(new JLabel("HS 1"));
        panel2.add(txtHS1);
        inputPanel.add(panel2);

        JPanel panel3= new JPanel();
        panel3.add(new JLabel("Môn 2"));
        panel3.add(txtMon2);
        panel3.add(new JLabel("HS 2"));
        panel3.add(txtHS2);
        inputPanel.add(panel3);

        JPanel panel4= new JPanel();
        panel4.add(new JLabel("Môn 3"));
        panel4.add(txtMon3);
        panel4.add(new JLabel("HS 3"));
        panel4.add(txtHS3);
        inputPanel.add(panel4);

        JPanel panel5= new JPanel();
        panel5.add(new JLabel("Độ lệch"));
        panel5.add(txtDoLech);
        inputPanel.add(panel5);

        add(inputPanel, BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
        "ID",
        "Mã ngành",
        "Tổ hợp",
        "HS1",
        "HS2",
        "HS3",
        "TO",
        "LI",
        "HO",
        "SI",
        "VA",
        "SU",
        "DI",
        "TI",
        "KHAC",
        "KTPL",
        "Độ lệch"
};

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        /* -------- BUTTON PANEL -------- */

        JPanel buttonPanel = new JPanel();

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        /* -------- DEMO DATA -------- */

        

    }

    public static void main(String[] args) {

        new QLNganhToHopGUI().setVisible(true);

    }

}