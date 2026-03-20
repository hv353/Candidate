package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLToHopGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JButton btnImport;
    JButton btnAdd;
    JButton btnEdit;
    JButton btnDelete;
    JButton btnRefresh;

    public QLToHopGUI(){

        setTitle("Quản lý tổ hợp môn xét tuyển");
        setSize(900,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- TOP PANEL -------- */

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnImport = new JButton("Import");
        btnAdd = new JButton("Thêm tổ hợp");
        btnRefresh = new JButton("Refresh");

        topPanel.add(btnImport);
        topPanel.add(btnAdd);
        topPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID",
                "Mã tổ hợp",
                "Môn 1",
                "Môn 2",
                "Môn 3",
                "Tên tổ hợp"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        /* -------- BUTTON PANEL -------- */

        JPanel bottomPanel = new JPanel();

        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");

        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        add(bottomPanel, BorderLayout.SOUTH);

        /* -------- Demo Data -------- */

        model.addRow(new Object[]{
                1,
                "A00",
                "Toán",
                "Lý",
                "Hóa",
                "Toán Lý Hóa"
        });

        model.addRow(new Object[]{
                2,
                "D01",
                "Toán",
                "Văn",
                "Anh",
                "Toán Văn Anh"
        });

    }

    public static void main(String[] args) {

        new QLToHopGUI().setVisible(true);

    }

}