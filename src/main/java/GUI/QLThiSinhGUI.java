package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QLThiSinhGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtSearch;

    JButton btnSearch;
    JButton btnImport;
    JButton btnEdit;

    JButton btnPrev;
    JButton btnNext;

    JLabel lblPage;

    int currentPage = 1;
    int totalPage = 1;

    public QLThiSinhGUI(){

        setTitle("Quản lý thí sinh");
        setSize(1100,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        /* -------- SEARCH PANEL -------- */

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtSearch = new JTextField(20);

        btnSearch = new JButton("Search");

        btnImport = new JButton("Import");

        JButton btnRefresh = new JButton("Refresh");

        topPanel.add(new JLabel("CCCD / Họ tên:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnImport);
        topPanel.add(btnRefresh);

        add(topPanel,BorderLayout.NORTH);

        /* -------- TABLE -------- */

        String[] columns = {
                "ID",
                "CCCD",
                "SBD",
                "Họ",
                "Tên",
                "Ngày sinh",
                "SĐT",
                "Giới tính",
                "Email",
                "Nơi sinh",
                "Đối tượng",
                "Khu vực"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane,BorderLayout.CENTER);

        /* -------- BOTTOM PANEL -------- */

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel pagePanel = new JPanel();

        btnPrev = new JButton("Prev");

        btnNext = new JButton("Next");

        lblPage = new JLabel("Page 1/1");

        pagePanel.add(btnPrev);
        pagePanel.add(lblPage);
        pagePanel.add(btnNext);

        bottomPanel.add(pagePanel,BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();

        btnEdit = new JButton("Edit");

        actionPanel.add(btnEdit);

        bottomPanel.add(actionPanel,BorderLayout.EAST);

        add(bottomPanel,BorderLayout.SOUTH);

        /* -------- Demo Data -------- */

        for(int i=1;i<=20;i++){

            model.addRow(new Object[]{
                    i,
                    "0123456789",
                    "SBD"+i,
                    "Nguyen",
                    "A"+i,
                    "2000-01-01",
                    "090000000"+i,
                    "Nam",
                    "mail@gmail.com",
                    "TPHCM",
                    "UT1",
                    "KV1"
            });

        }

    }

    
}