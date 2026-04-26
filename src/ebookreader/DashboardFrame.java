package ebookreader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class DashboardFrame extends JFrame {

    private final Color PRIMARY   = new Color(33, 97, 140);
    private final Color SECONDARY = new Color(245, 248, 250);
    private final Color SUCCESS   = new Color(39, 174, 96);
    private final Color DANGER    = new Color(192, 57, 43);
    private final Color WARNING   = new Color(211, 84, 0);
    private final Color PURPLE    = new Color(142, 68, 173);
    private final Color WHITE     = Color.WHITE;
    private final Color BORDER    = new Color(200, 210, 220);

    private final String username;
    private final int    userId;

    private DefaultTableModel tableModel;
    private JTable            bookTable;
    private JTextField        txtSearch;
    private JLabel            lblTotal;
    private JLabel            lblRead;
    private JLabel            lblUnread;

    public DashboardFrame(String username, int userId) {
        this.username = username;
        this.userId   = userId;
        initComponents();
        loadBooks();
        setTitle("E-Book Reader — " + username + "'s Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 640);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(SECONDARY);
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainPanel(),   BorderLayout.CENTER);
    }

    // ── Header ───────────────────────────────────────────────
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY);
        panel.setBorder(
            BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);

        JLabel title = new JLabel("📚   E-Book Reader");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(WHITE);

        JLabel welcome = new JLabel(
            "Welcome back, " + username + "!");
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        welcome.setForeground(new Color(200, 220, 240));

        left.add(title);
        left.add(welcome);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setBackground(DANGER);
        btnLogout.setForeground(WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setBorder(
            BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        panel.add(left,      BorderLayout.WEST);
        panel.add(btnLogout, BorderLayout.EAST);
        return panel;
    }

    // ── Main panel ───────────────────────────────────────────
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(
            BorderFactory.createEmptyBorder(12, 15, 12, 15));

        panel.add(createStatsPanel(),  BorderLayout.NORTH);
        panel.add(createTablePanel(),  BorderLayout.CENTER);
        panel.add(createActionPanel(), BorderLayout.SOUTH);

        return panel;
    }

    // ── Stats cards ──────────────────────────────────────────
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setOpaque(false);
        panel.setBorder(
            BorderFactory.createEmptyBorder(0, 0, 8, 0));

        lblTotal  = addStatCard(panel, "Total Books", "0", PRIMARY);
        lblRead   = addStatCard(panel, "Read",        "0", SUCCESS);
        lblUnread = addStatCard(panel, "Unread",      "0", WARNING);

        return panel;
    }

    private JLabel addStatCard(JPanel parent, String title,
                                String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel titleLbl = new JLabel(title, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLbl.setForeground(new Color(100, 120, 140));

        JLabel valueLbl = new JLabel(value, SwingConstants.CENTER);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLbl.setForeground(color);

        card.add(titleLbl);
        card.add(valueLbl);
        parent.add(card);
        return valueLbl;
    }

    // ── Search + Table ───────────────────────────────────────
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.add(createSearchBar(), BorderLayout.NORTH);
        panel.add(createTable(),     BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSearchBar() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setOpaque(false);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        txtSearch.setToolTipText("Search by title or author");

        JButton btnSearch = makeButton("Search",   PRIMARY);
        JButton btnAll    = makeButton("Show All", new Color(100,110,120));

        btnSearch.addActionListener(e -> searchBooks());
        btnAll.addActionListener(e -> {
            txtSearch.setText("");
            loadBooks();
        });

        JPanel btnPanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 6, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSearch);
        btnPanel.add(btnAll);

        panel.add(txtSearch, BorderLayout.CENTER);
        panel.add(btnPanel,  BorderLayout.EAST);
        return panel;
    }

    private JScrollPane createTable() {
        String[] cols = {"ID","Title","Author","Size","Status","Date Added"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        bookTable = new JTable(tableModel);
        bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        bookTable.setRowHeight(30);
        bookTable.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 13));
        bookTable.getTableHeader().setBackground(PRIMARY);
        bookTable.getTableHeader().setForeground(WHITE);
        bookTable.setGridColor(BORDER);
        bookTable.setSelectionBackground(new Color(210, 230, 250));
        bookTable.setShowVerticalLines(false);

        bookTable.getColumnModel().getColumn(0)
            .setPreferredWidth(40);
        bookTable.getColumnModel().getColumn(1)
            .setPreferredWidth(290);
        bookTable.getColumnModel().getColumn(2)
            .setPreferredWidth(160);
        bookTable.getColumnModel().getColumn(3)
            .setPreferredWidth(70);
        bookTable.getColumnModel().getColumn(4)
            .setPreferredWidth(80);
        bookTable.getColumnModel().getColumn(5)
            .setPreferredWidth(110);

        DefaultTableCellRenderer center =
            new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int col : new int[]{0, 3, 4, 5}) {
            bookTable.getColumnModel()
                     .getColumn(col)
                     .setCellRenderer(center);
        }

        JScrollPane scroll = new JScrollPane(bookTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER));
        return scroll;
    }

    // ── Action buttons ───────────────────────────────────────
    private JPanel createActionPanel() {
        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 4));
        panel.setOpaque(false);

        JButton btnAdd      = makeButton("+ Add Book",    SUCCESS);
        JButton btnRead     = makeButton("Read Book",     PRIMARY);
        JButton btnMarkRead = makeButton("Mark as Read",  PURPLE);
        JButton btnDelete   = makeButton("Delete Book",   DANGER);

        btnAdd.addActionListener(e -> {
            AddBookDialog dlg = new AddBookDialog(this, userId);
            dlg.setVisible(true);
            if (dlg.isBookAdded()) loadBooks();
        });

        btnRead.addActionListener(e     -> readSelected());
        btnMarkRead.addActionListener(e -> markSelected());
        btnDelete.addActionListener(e   -> deleteSelected());

        panel.add(btnAdd);
        panel.add(btnRead);
        panel.add(btnMarkRead);
        panel.add(btnDelete);

        return panel;
    }

    // ── Data methods ─────────────────────────────────────────
    private void loadBooks() {
        tableModel.setRowCount(0);
        int total = 0, read = 0, unread = 0;

        try {
            ResultSet rs = DatabaseManager.getAllBooks(userId);
            while (rs != null && rs.next()) {
                String date = rs.getString("added_date");
                if (date != null && date.length() >= 10)
                    date = date.substring(0, 10);

                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("file_size"),
                    rs.getString("status"),
                    date
                });
                total++;
                if ("Read".equals(rs.getString("status"))) read++;
                else unread++;
            }
        } catch (Exception e) {
            System.out.println("Load error: " + e.getMessage());
        }

        lblTotal.setText(String.valueOf(total));
        lblRead.setText(String.valueOf(read));
        lblUnread.setText(String.valueOf(unread));
    }

    private void searchBooks() {
        String kw = txtSearch.getText().trim();
        if (kw.isEmpty()) { loadBooks(); return; }

        tableModel.setRowCount(0);
        try {
            ResultSet rs = DatabaseManager.searchBooks(kw, userId);
            while (rs != null && rs.next()) {
                String date = rs.getString("added_date");
                if (date != null && date.length() >= 10)
                    date = date.substring(0, 10);

                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("file_size"),
                    rs.getString("status"),
                    date
                });
            }
        } catch (Exception e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    private void readSelected() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            showWarn("Please select a book from the list first.");
            return;
        }
        int    id       = (int)    tableModel.getValueAt(row, 0);
        String title    = (String) tableModel.getValueAt(row, 1);
        String filePath = DatabaseManager.getFilePath(id);

        DatabaseManager.updateStatus(id, "Read");
        new PDFViewerFrame(filePath, title).setVisible(true);
        loadBooks();
    }

    private void markSelected() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            showWarn("Please select a book from the list first.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        DatabaseManager.updateStatus(id, "Read");
        loadBooks();
        JOptionPane.showMessageDialog(this,
            "Book marked as Read!",
            "Updated", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteSelected() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            showWarn("Please select a book to delete.");
            return;
        }
        String title = (String) tableModel.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete:\n" + title,
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            DatabaseManager.deleteBook(id);
            loadBooks();
            JOptionPane.showMessageDialog(this,
                "Book deleted successfully.",
                "Deleted", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ── Helpers ──────────────────────────────────────────────
    private void showWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg,
            "Notice", JOptionPane.WARNING_MESSAGE);
    }

    private JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(
            BorderFactory.createEmptyBorder(9, 16, 9, 16));
        return btn;
    }
}