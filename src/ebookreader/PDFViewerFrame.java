package ebookreader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PDFViewerFrame extends JFrame {

    private final Color PRIMARY = new Color(33, 97, 140);
    private final Color WHITE   = Color.WHITE;

    private final String filePath;
    private final String bookTitle;

    public PDFViewerFrame(String filePath, String bookTitle) {
        this.filePath  = filePath;
        this.bookTitle = bookTitle;
        initComponents();
        setTitle("Reading: " + bookTitle);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(680, 460);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 248, 250));
        add(createHeaderPanel(),  BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    // ── Header ───────────────────────────────────────────────
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY);
        panel.setBorder(
            BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("📖   " + bookTitle);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(WHITE);

        JButton btnOpen = new JButton("Open in PDF Reader");
        btnOpen.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnOpen.setBackground(new Color(39, 174, 96));
        btnOpen.setForeground(WHITE);
        btnOpen.setFocusPainted(false);
        btnOpen.setBorderPainted(false);
        btnOpen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOpen.setBorder(
            BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btnOpen.addActionListener(e -> openPDF());

        panel.add(title,   BorderLayout.WEST);
        panel.add(btnOpen, BorderLayout.EAST);
        return panel;
    }

    // ── Content ──────────────────────────────────────────────
    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 248, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx  = 0;
        gbc.gridy  = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel icon = new JLabel("📄");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));

        JLabel titleLbl = new JLabel(bookTitle, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(PRIMARY);

        File   file = new File(filePath);
        String info = file.exists()
            ? "File ready — " + String.format("%.1f MB",
                file.length() / (1024.0 * 1024))
            : "File not found at saved location";

        JLabel infoLbl = new JLabel(info, SwingConstants.CENTER);
        infoLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        infoLbl.setForeground(new Color(100, 120, 140));

        JLabel instrLbl = new JLabel(
            "Click the button below to open this book",
            SwingConstants.CENTER);
        instrLbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        instrLbl.setForeground(new Color(100, 120, 140));

        JButton btnOpenBig = new JButton("Open PDF Now");
        btnOpenBig.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnOpenBig.setBackground(PRIMARY);
        btnOpenBig.setForeground(WHITE);
        btnOpenBig.setFocusPainted(false);
        btnOpenBig.setBorderPainted(false);
        btnOpenBig.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOpenBig.setBorder(
            BorderFactory.createEmptyBorder(12, 28, 12, 28));
        btnOpenBig.addActionListener(e -> openPDF());

        panel.add(icon,       gbc);
        panel.add(titleLbl,   gbc);
        panel.add(infoLbl,    gbc);
        panel.add(Box.createVerticalStrut(6), gbc);
        panel.add(instrLbl,   gbc);
        panel.add(Box.createVerticalStrut(6), gbc);
        panel.add(btnOpenBig, gbc);

        return panel;
    }

    // ── Logic ────────────────────────────────────────────────
    private void openPDF() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this,
                    "PDF file not found at:\n" + filePath +
                    "\n\nThe file may have been moved or deleted.",
                    "File Not Found",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Could not open PDF.\n" +
                "Please make sure you have a PDF reader installed.\n" +
                "You can download Adobe Acrobat Reader for free " +
                "from adobe.com",
                "Cannot Open PDF",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}