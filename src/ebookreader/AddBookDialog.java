package ebookreader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class AddBookDialog extends JDialog {

    private final Color PRIMARY   = new Color(33, 97, 140);
    private final Color SUCCESS   = new Color(39, 174, 96);
    private final Color SECONDARY = new Color(245, 248, 250);
    private final Color WHITE     = Color.WHITE;
    private final Color BORDER    = new Color(200, 210, 220);
    private final Color TEXT_DARK = new Color(30, 30, 30);

    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtFilePath;
    private JButton    btnBrowse;
    private JButton    btnAdd;
    private JButton    btnCancel;

    private final int userId;
    private boolean bookAdded = false;

    public AddBookDialog(JFrame parent, int userId) {
        super(parent, "Add New Book", true);
        this.userId = userId;
        initComponents();
        setSize(480, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(WHITE);
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(),   BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    // ── Header ───────────────────────────────────────────────
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JLabel title = new JLabel("Add New Book");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(WHITE);

        JLabel subtitle = new JLabel(
            "Browse and add a PDF book to your library");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(200, 220, 240));

        panel.add(title);
        panel.add(subtitle);
        return panel;
    }

    // ── Form ─────────────────────────────────────────────────
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx   = 0;
        gbc.insets  = new Insets(6, 5, 6, 5);

        gbc.gridy = 0;
        panel.add(makeLabel("Book Title *"), gbc);

        gbc.gridy = 1;
        txtTitle  = makeTextField("Enter book title");
        panel.add(txtTitle, gbc);

        gbc.gridy = 2;
        panel.add(makeLabel("Author Name"), gbc);

        gbc.gridy   = 3;
        txtAuthor   = makeTextField("Enter author name (optional)");
        panel.add(txtAuthor, gbc);

        gbc.gridy = 4;
        panel.add(makeLabel("PDF File *"), gbc);

        // File browser row
        JPanel fileRow = new JPanel(new BorderLayout(8, 0));
        fileRow.setOpaque(false);

        txtFilePath = makeTextField("No file selected");
        txtFilePath.setEditable(false);
        txtFilePath.setBackground(new Color(240, 243, 246));

        btnBrowse = new JButton("Browse");
        btnBrowse.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBrowse.setBackground(PRIMARY);
        btnBrowse.setForeground(WHITE);
        btnBrowse.setFocusPainted(false);
        btnBrowse.setBorderPainted(false);
        btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBrowse.setBorder(
            BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btnBrowse.addActionListener(e -> browseFile());

        fileRow.add(txtFilePath, BorderLayout.CENTER);
        fileRow.add(btnBrowse,   BorderLayout.EAST);

        gbc.gridy = 5;
        panel.add(fileRow, gbc);

        return panel;
    }

    // ── Buttons ──────────────────────────────────────────────
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        panel.setBackground(SECONDARY);
        panel.setBorder(BorderFactory.createMatteBorder(
            1, 0, 0, 0, BORDER));

        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setBorder(
            BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnCancel.addActionListener(e -> dispose());

        btnAdd = new JButton("Add Book");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(SUCCESS);
        btnAdd.setForeground(WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setBorder(
            BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnAdd.addActionListener(e -> addBook());

        panel.add(btnCancel);
        panel.add(btnAdd);
        return panel;
    }

    // ── Logic ────────────────────────────────────────────────
    private void browseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a PDF File");
        chooser.setFileFilter(
            new FileNameExtensionFilter("PDF Files (*.pdf)", "pdf"));

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            txtFilePath.setText(file.getAbsolutePath());

            if (txtTitle.getText().trim().isEmpty()) {
                String name = file.getName()
                    .replace(".pdf", "")
                    .replace("_", " ")
                    .replace("-", " ");
                txtTitle.setText(name);
            }
        }
    }

    private void addBook() {
        String title    = txtTitle.getText().trim();
        String author   = txtAuthor.getText().trim();
        String filePath = txtFilePath.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter the book title.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (filePath.isEmpty() ||
            filePath.equals("No file selected")) {
            JOptionPane.showMessageDialog(this,
                "Please select a PDF file.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this,
                "The selected file does not exist.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        long   bytes    = file.length();
        String fileSize = bytes < 1024 * 1024
            ? String.format("%.1f KB", bytes / 1024.0)
            : String.format("%.1f MB", bytes / (1024.0 * 1024));

        if (author.isEmpty()) author = "Unknown Author";

        boolean success = DatabaseManager.addBook(
            title, author, filePath, fileSize, userId);

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Book added successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            bookAdded = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to add book. Please try again.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isBookAdded() { return bookAdded; }

    // ── Helpers ──────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(TEXT_DARK);
        return lbl;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField f = new JTextField(20);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        return f;
    }
}