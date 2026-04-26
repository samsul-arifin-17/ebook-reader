package ebookreader;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends JFrame {

    // Colors — same as LoginFrame
    private final Color BG_LEFT      = new Color(20, 30, 48);
    private final Color BG_RIGHT     = new Color(245, 247, 250);
    private final Color ACCENT       = new Color(99, 102, 241);
    private final Color ACCENT_HOVER = new Color(79, 82, 221);
    private final Color SUCCESS      = new Color(34, 197, 94);
    private final Color SUCCESS_HOV  = new Color(22, 163, 74);
    private final Color TEXT_WHITE   = new Color(255, 255, 255);
    private final Color TEXT_DARK    = new Color(30, 35, 50);
    private final Color TEXT_GRAY    = new Color(120, 130, 150);
    private final Color BORDER_COLOR = new Color(220, 223, 230);
    private final Color INPUT_BG     = new Color(255, 255, 255);

    // Components
    private JTextField     txtFullName;
    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JCheckBox      chkShowPassword;
    private JButton        btnRegister;
    private JButton        btnBackToLogin;

    public RegisterFrame() {
        initComponents();
        setTitle("E-Book Reader — Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 600);
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
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
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

        // Icon
        JLabel icon = new JLabel("📚");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        icon.setHorizontalAlignment(SwingConstants.CENTER);

        // App name
        JLabel appName = new JLabel("E-Book Reader");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 26));
        appName.setForeground(TEXT_WHITE);
        appName.setHorizontalAlignment(SwingConstants.CENTER);

        // Tagline
        JLabel tagline = new JLabel(
            "<html><div style='text-align:center;'>" +
            "Create your free account today.<br>" +
            "Start building your digital library.</div></html>");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tagline.setForeground(new Color(180, 195, 215));
        tagline.setHorizontalAlignment(SwingConstants.CENTER);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(99, 102, 241, 120));
        sep.setPreferredSize(new Dimension(180, 1));

        // Steps
        JLabel s1 = makeStepLabel("1️⃣", "Enter your full name");
        JLabel s2 = makeStepLabel("2️⃣", "Choose a unique username");
        JLabel s3 = makeStepLabel("3️⃣", "Set a secure password");
        JLabel s4 = makeStepLabel("4️⃣", "Start reading!");

        panel.add(icon,    gbc);
        panel.add(appName, gbc);

        gbc.insets = new Insets(4, 30, 16, 30);
        panel.add(tagline, gbc);

        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 50, 16, 50);
        panel.add(sep, gbc);

        gbc.fill   = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 50, 5, 30);
        panel.add(s1, gbc);
        panel.add(s2, gbc);
        panel.add(s3, gbc);
        panel.add(s4, gbc);

        return panel;
    }

    private JLabel makeStepLabel(String emoji, String text) {
        JLabel lbl = new JLabel(emoji + "  " + text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(160, 180, 210));
        return lbl;
    }

    // ── Right panel — registration form ──────────────────────
    private JPanel createRightPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_RIGHT);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setPreferredSize(new Dimension(320, 520));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx   = 0;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Heading
        JLabel heading = new JLabel("Create Account");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(TEXT_DARK);
        gbc.gridy  = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        form.add(heading, gbc);

        JLabel sub = new JLabel("Fill in your details to get started");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_GRAY);
        gbc.gridy  = 1;
        gbc.insets = new Insets(0, 0, 22, 0);
        form.add(sub, gbc);

        // Full name
        gbc.gridy  = 2;
        gbc.insets = new Insets(0, 0, 6, 0);
        form.add(makeFormLabel("Full Name"), gbc);

        gbc.gridy  = 3;
        gbc.insets = new Insets(0, 0, 14, 0);
        txtFullName = makeInputField("Enter your full name");
        form.add(txtFullName, gbc);

        // Username
        gbc.gridy  = 4;
        gbc.insets = new Insets(0, 0, 6, 0);
        form.add(makeFormLabel("Username"), gbc);

        gbc.gridy  = 5;
        gbc.insets = new Insets(0, 0, 4, 0);
        txtUsername = makeInputField("At least 4 characters, no spaces");
        form.add(txtUsername, gbc);

        // Username hint
        gbc.gridy  = 6;
        gbc.insets = new Insets(0, 2, 14, 0);
        form.add(makeHintLabel("Min 4 characters · No spaces allowed"), gbc);

        // Password
        gbc.gridy  = 7;
        gbc.insets = new Insets(0, 0, 6, 0);
        form.add(makeFormLabel("Password"), gbc);

        gbc.gridy  = 8;
        gbc.insets = new Insets(0, 0, 4, 0);
        txtPassword = makePasswordField("At least 6 characters");
        form.add(txtPassword, gbc);

        // Password hint
        gbc.gridy  = 9;
        gbc.insets = new Insets(0, 2, 6, 0);
        form.add(makeHintLabel("Min 6 characters"), gbc);

        // Confirm password
        gbc.gridy  = 10;
        gbc.insets = new Insets(0, 0, 6, 0);
        form.add(makeFormLabel("Confirm Password"), gbc);

        gbc.gridy  = 11;
        gbc.insets = new Insets(0, 0, 6, 0);
        txtConfirmPassword = makePasswordField("Re-enter your password");
        form.add(txtConfirmPassword, gbc);

        // Show password checkbox
        gbc.gridy  = 12;
        gbc.insets = new Insets(0, 2, 20, 0);
        chkShowPassword = new JCheckBox("Show passwords");
        chkShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkShowPassword.setForeground(TEXT_GRAY);
        chkShowPassword.setOpaque(false);
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkShowPassword.addActionListener(e -> {
            char echo = chkShowPassword.isSelected()
                        ? (char) 0 : '●';
            txtPassword.setEchoChar(echo);
            txtConfirmPassword.setEchoChar(echo);
        });
        form.add(chkShowPassword, gbc);

        // Register button
        gbc.gridy  = 13;
        gbc.insets = new Insets(0, 0, 12, 0);
        btnRegister = makeAccentButton(
            "Create Account", SUCCESS, SUCCESS_HOV);
        btnRegister.addActionListener(e -> register());
        form.add(btnRegister, gbc);

        // Divider
        gbc.gridy  = 14;
        gbc.insets = new Insets(0, 0, 12, 0);
        form.add(makeDivider(), gbc);

        // Back to login button
        gbc.gridy        = 15;
        gbc.insets       = new Insets(0, 0, 0, 0);
        btnBackToLogin   = makeOutlineButton("Back to Login");
        btnBackToLogin.addActionListener(e -> backToLogin());
        form.add(btnBackToLogin, gbc);

        outer.add(form);
        return outer;
    }

    // ── Logic ────────────────────────────────────────────────
    private void register() {
        String fullName  = txtFullName.getText().trim();
        String username  = txtUsername.getText().trim();
        String password  = new String(
                             txtPassword.getPassword()).trim();
        String confirm   = new String(
                             txtConfirmPassword.getPassword()).trim();

        // Empty check
        if (fullName.isEmpty() || username.isEmpty() ||
            password.isEmpty() || confirm.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        // Username length
        if (username.length() < 4) {
            showError("Username must be at least 4 characters.");
            txtUsername.requestFocus();
            return;
        }

        // Username no spaces
        if (username.contains(" ")) {
            showError("Username cannot contain spaces.");
            txtUsername.requestFocus();
            return;
        }

        // Password length
        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            txtPassword.requestFocus();
            return;
        }

        // Passwords match
        if (!password.equals(confirm)) {
            showError("Passwords do not match. Please try again.");
            txtConfirmPassword.setText("");
            txtConfirmPassword.requestFocus();
            return;
        }

        // Save to database
        boolean success = DatabaseManager.registerUser(
                            fullName, username, password);

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Account created successfully!\n" +
                "You can now login with your credentials.",
                "Welcome to E-Book Reader!",
                JOptionPane.INFORMATION_MESSAGE);
            backToLogin();
        } else {
            showError(
                "Username already taken.\n" +
                "Please choose a different username.");
            txtUsername.requestFocus();
        }
    }

    private void backToLogin() {
        new LoginFrame().setVisible(true);
        dispose();
    }

    // ── Component helpers ────────────────────────────────────
    private JLabel makeFormLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        return lbl;
    }

    private JLabel makeHintLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(new Color(160, 170, 185));
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
                                        Font.PLAIN, 12));
                    g2.drawString(placeholder, 12, 21);
                }
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COLOR),
            BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));
        field.setPreferredSize(new Dimension(300, 40));

        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, ACCENT),
                    BorderFactory.createEmptyBorder(9, 12, 9, 12)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, BORDER_COLOR),
                    BorderFactory.createEmptyBorder(9, 12, 9, 12)
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
                if (getPassword().length == 0 &&
                    !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(180, 185, 200));
                    g2.setFont(new Font("Segoe UI",
                                        Font.PLAIN, 12));
                    g2.drawString(placeholder, 12, 21);
                }
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT_DARK);
        field.setBackground(INPUT_BG);
        field.setEchoChar('●');
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, BORDER_COLOR),
            BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));
        field.setPreferredSize(new Dimension(300, 40));

        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, ACCENT),
                    BorderFactory.createEmptyBorder(9, 12, 9, 12)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, BORDER_COLOR),
                    BorderFactory.createEmptyBorder(9, 12, 9, 12)
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
            "Registration Error", JOptionPane.ERROR_MESSAGE);
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