package rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerGUI extends JFrame {
    private JTextArea logArea;
    private JButton startButton;

    public ServerGUI() {
        // إعداد النافذة الرئيسية
        setTitle("RMI Serveur - Inversion");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // توسيط النافذة
        setLayout(new BorderLayout());

        // منطقة عرض السجلات (Logs)
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.GREEN);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // لوحة التحكم السفلية
        JPanel bottomPanel = new JPanel();
        startButton = new JButton("Démarrer le Serveur");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(startButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // إعادة توجيه System.out.println إلى شاشة النصوص في الواجهة
        redirectSystemOut();

        // حدث ضغط الزر
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
    }

    private void startServer() {
        // تعطيل الزر لمنع التشغيل المزدوج
        startButton.setEnabled(false);
        startButton.setText("Serveur en cours d'exécution...");
        
        // تشغيل السيرفر في خيط منفصل لتجنب تجميد الواجهة
        new Thread(() -> {
            try {
                log("Attempt to start RMI Registry...");
                // إنشاء السجل على المنفذ 1099
                Registry registry = LocateRegistry.createRegistry(1099);
                log("RMI Registry It was successfully created on the port 1099.");

                log("Creating the remote object (ServeurInversion)...");
                ServeurInversion objetDistant = new ServeurInversion();

                log("Register the object as'ServiceInversion'...");
                registry.rebind("ServiceInversion", objetDistant);

                log("======================================");
                log("The server is fully ready to receive orders.!");
                log("======================================");

            } catch (Exception ex) {
                log("Serious server error: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Server failed to start:\n" + ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                startButton.setEnabled(true);
                startButton.setText("Démarrer le Serveur");
            }
        }).start();
    }

    // دالة مساعدة لإضافة نصوص للشاشة
    private void log(String message) {
        System.out.println(message);
    }

    // حيلة تقنية لإعادة توجيه مخرجات الكونسول إلى الـ JTextArea
    private void redirectSystemOut() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateLogArea(String.valueOf((char) b));
            }
            @Override
            public void write(byte[] b, int off, int len) {
                updateLogArea(new String(b, off, len));
            }
            @Override
            public void write(byte[] b) {
                write(b, 0, b.length);
            }
        };
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    private void updateLogArea(final String text) {
        SwingUtilities.invokeLater(() -> logArea.append(text));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServerGUI().setVisible(true));
    }
}