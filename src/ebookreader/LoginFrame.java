package ebookreader;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class LoginFrame extends JFrame {

    // Colors
    private final Color BG_LEFT       = new Color(20, 30, 48);
    private final Color BG_RIGHT      = new Color(245, 247, 250);
    private final Color ACCENT        = new Color(99, 102, 241);
    private final Color ACCENT_HOVER  = new Color(79, 82, 221);
    private final Color TEXT_WHITE    = new Color(255, 255, 255);
    private final Color TEXT_DARK     = new Color(30, 35, 50);
    private final Color TEXT_GRAY     = new Color(120, 130, 150);
    private final Color BORDER_COLOR  = new Color(220, 223, 230);
    private final Color INPUT_BG      = new Color(255, 255, 255);
    private final Color LINK_COLOR    = new Color(99, 102, 241);

    // Components
    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JButton        btnCreateAccount;
    private JCheckBox      chkShowPassword;

    public LoginFrame() {
        initComponents();
        setTitle("E-Book Reader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 540);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        setLayout(new GridLayout(1, 2));
        add(createLeftPanel());
        add(createRightPanel());
    }

    // ── Left panel — dark branding side ──────────────────────
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);

                // Dark gradient background
                GradientPaint gp = new GradientPaint(
                    0, 0,            new Color(20, 30, 48),
                    0, getHeight(), new Color(36, 59, 85)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circles
                g2.setColor(new Color(99, 102, 241, 40));
                g2.fillOval(-60, -60, 250, 250);

                g2.setColor(new Color(99, 102, 241, 25));
                g2.fillOval(getWidth() - 120,
                            getHeight() - 120, 200, 200);

                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillOval(50, getHeight() - 180, 150, 150);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx  = 0;
        gbc.gridy  = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.anchor = GridBagConstraints.CENTER;

        // Book icon
        JLabel iconLabel = new JLabel("📚");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // App name
        JLabel appName = new JLabel("E-Book Reader");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        appName.setForeground(TEXT_WHITE);
        appName.setHorizontalAlignment(SwingConstants.CENTER);

        // Tagline
        JLabel tagline = new JLabel(
            "<html><div style='text-align:center;'>" +
            "Your personal digital library.<br>" +
            "Read. Explore. Learn.</div></html>");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tagline.setForeground(new Color(180, 195, 215));
        tagline.setHorizontalAlignment(SwingConstants.CENTER);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(99, 102, 241, 120));
        sep.setPreferredSize(new Dimension(180, 1));

        // Feature bullets
        JLabel f1 = makeFeatureLabel("📄  Manage your PDF books");
        JLabel f2 = makeFeatureLabel("🔍  Search your library instantly");
        JLabel f3 = makeFeatureLabel("✅  Track what you have read");

        panel.add(iconLabel, gbc);
        panel.add(appName,   gbc);

        gbc.insets = new Insets(4, 30, 16, 30);
        panel.add(tagline, gbc);

        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 50, 16, 50);
        panel.add(sep, gbc);

        gbc.fill   = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 50, 4, 30);
        panel.add(f1, gbc);
        panel.add(f2, gbc);
        panel.add(f3, gbc);

        return panel;
    }

    private JLabel makeFeatureLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(160, 180, 210));
        return lbl;
    }

    // ── Right panel — login form ──────────────────────────────
    private JPanel createRightPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_RIGHT);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(320, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx   = 0;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Welcome heading
        JLabel welcome = new JLabel("Welcome back");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcome.setForeground(TEXT_DARK);
        gbc.gridy  = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        form.add(welcome, gbc);

        JLabel sub = new JLabel("Login to access your library");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_GRAY);
        gbc.gridy  = 1;
        gbc.insets = new Insets(0, 0, 28, 0);
        form.add(sub, gbc);

        // Username label
        gbc.gridy  = 2;
        gbc.insets = new Insets(0, 0, 6, 0);
        form.add(makeFormLabel("Username"), gbc);

        // Username field
        gbc.gridy  = 3;
        gbc.insets = new Insets(0, 0, 16, 0);
        txtUsername = makeInputField("Enter your username");
        form.add(txtUsername, gbc);

        // Password label
        gbc.gridy  = 4;
        gbc.insets = new Insets(0, 0, 6, 0);
        form.add(makeFormLabel("Password"), gbc);

        // Password field
        gbc.gridy  = 5;
        gbc.insets = new Insets(0, 0, 6, 0);
        txtPassword = makePasswordField("Enter your password");
        form.add(txtPassword, gbc);

        // Show password checkbox
        gbc.gridy  = 6;
        gbc.insets = new Insets(0, 2, 20, 0);
        chkShowPassword = new JCheckBox("Show password");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPassword.setForeground(TEXT_GRAY);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.setCursor(
            new Cursor(Cursor.HAND_CURSOR));
        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('●');
            }
        });
        form.add(chkShowPassword, gbc);

        // Login button
        gbc.gridy  = 7;
        gbc.insets = new Insets(0, 0, 14, 0);
        btnLogin   = makeAccentButton("Login", ACCENT, ACCENT_HOVER);
        btnLogin.addActionListener(e -> login());
        form.add(btnLogin, gbc);

        // Divider
        gbc.gridy  = 8;
        gbc.insets = new Insets(0, 0, 14, 0);
        form.add(makeDivider(), gbc);

        // Create account button
        gbc.gridy        = 9;
        gbc.insets       = new Insets(0, 0, 18, 0);
        btnCreateAccount = makeOutlineButton("Create New Account");
        btnCreateAccount.addActionListener(e -> openRegister());
        form.add(btnCreateAccount, gbc);

        // Default login hint
        gbc.gridy  = 10;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel hint = new JLabel(
            "Default:  admin  /  admin123",
            SwingConstants.CENTER);
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hint.setForeground(new Color(180, 185, 200));
        form.add(hint, gbc);

        outer.add(form);
        return outer;
    }

    // ── Logic ────────────────────────────────────────────────
    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(
                            txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        if (DatabaseManager.loginUser(username, password)) {
            int userId = DatabaseManager.getUserId(username);
            new DashboardFrame(username, userId).setVisible(true);
            dispose();
        } else {
            showError(
                "Incorrect username or password.\n" +
                "Please try again.");
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    private void openRegister() {
        new RegisterFrame().setVisible(true);
        dispose();
    }

    // ── Component helpers ────────────────────────────────────
    private JLabel makeFormLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        return lbl;
    }

    private JTextField makeInputField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(180, 185, 200));
                    g2.setFont(new Font("Segoe UI",
                                        Font.PLAIN, 13));
                    g2.drawString(placeholder, 12, 22);
                }
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COLOR),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(300, 42));

        // Highlight on focus
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, ACCENT),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, BORDER_COLOR),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        return field;
    }

    private JPasswordField makePasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(180, 185, 200));
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    g2.drawString(placeholder, 12, 22);
                }
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);
        field.setEchoChar('●');
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COLOR),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        field.setPreferredSize(new Dimension(300, 42));

        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, ACCENT),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, BORDER_COLOR),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        return field;
    }

    private JButton makeAccentButton(String text,
                                      Color normal,
                                      Color hover) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover()
                            ? hover : normal);
                g2.fillRoundRect(0, 0,
                    getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(TEXT_WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 44));
        btn.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private JButton makeOutlineButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(99, 102, 241, 15));
                    g2.fillRoundRect(0, 0,
                        getWidth(), getHeight(), 10, 10);
                }
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1,
                    getWidth() - 2, getHeight() - 2, 10, 10);
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(ACCENT);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 42));
        btn.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private JPanel makeDivider() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        JSeparator left  = new JSeparator();
        JSeparator right = new JSeparator();
        left.setForeground(BORDER_COLOR);
        right.setForeground(BORDER_COLOR);

        JLabel orLabel = new JLabel("  or  ");
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orLabel.setForeground(TEXT_GRAY);

        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        panel.add(left, gbc);

        gbc.fill    = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(orLabel, gbc);

        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        panel.add(right, gbc);

        return panel;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg,
            "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    // ── Rounded border helper class ──────────────────────────
    private static class RoundedBorder implements Border {
        private final int   radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color  = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g,
                                 int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x, y, w - 1, h - 1,
                              radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }

        @Override
        public boolean isBorderOpaque() { return false; }
    }
}