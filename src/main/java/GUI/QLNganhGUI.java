package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLNganhGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JButton btnImport;
    JButton btnAdd;
    JButton btnEdit;
    JButton btnDelete;

    public QLNganhGUI(){

        setTitle("Quản lý ngành tuyển sinh");
        setSize(1100,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- TOP PANEL -------- */

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnImport = new JButton("Import");
        btnAdd = new JButton("Thêm ngành");
        JButton btnRefresh = new JButton("Refresh");

        topPanel.add(btnImport);
        topPanel.add(btnAdd);
        topPanel.add(btnRefresh);

        add(topPanel,BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID",
                "Mã ngành",
                "Tên ngành",
                "Tổ hợp",
                "Chỉ tiêu",
                "Điểm sàn",
                "Điểm trúng tuyển",
                "Tuyển thẳng",
                "ĐGNL",
                "VSAT",
                "THPT"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);

        /* -------- BOTTOM PANEL -------- */

        JPanel bottomPanel = new JPanel();

        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");

        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        add(bottomPanel,BorderLayout.SOUTH);

        /* -------- Demo Data -------- */

        model.addRow(new Object[]{
                1,
                "7480201",
                "Công nghệ thông tin",
                "A00",
                200,
                18.5,
                24.0,
                "N",
                "Y",
                "N",
                "Y"
        });

    }

    public static void main(String[] args) {

        new QLNganhGUI().setVisible(true);

    }

}