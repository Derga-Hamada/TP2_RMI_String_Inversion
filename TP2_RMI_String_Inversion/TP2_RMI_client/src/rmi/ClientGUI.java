package rmi;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientGUI extends JFrame {
    private JTextField inputField;
    private JTextField outputField;
    private JButton processButton;
    private JLabel statusLabel;
    
    // الكائن الذي سيمثل الخدمة البعيدة (Stub)
    private ServiceInversion stub;

    public ClientGUI() {
        // إعداد النافذة الرئيسية
        setTitle("RMI Client - Inversion");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // الصف الأول: الإدخال
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        inputPanel.add(new JLabel("Enter the text here:"), BorderLayout.WEST);
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputPanel.add(inputField, BorderLayout.CENTER);
        add(inputPanel);

        // الصف الثاني: الزر
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        processButton = new JButton("reverse the text (Inverser)");
        processButton.setFont(new Font("Arial", Font.BOLD, 14));
        processButton.setEnabled(false); // معطل حتى يتم الاتصال بالخادم
        buttonPanel.add(processButton);
        add(buttonPanel);

        // الصف الثالث: النتيجة
        JPanel outputPanel = new JPanel(new BorderLayout(10, 10));
        outputPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        outputPanel.add(new JLabel("The reverse result:"), BorderLayout.WEST);
        outputField = new JTextField();
        outputField.setFont(new Font("Arial", Font.BOLD, 16));
        outputField.setEditable(false); // لا يمكن الكتابة فيه
        outputField.setForeground(Color.BLUE);
        outputPanel.add(outputField, BorderLayout.CENTER);
        add(outputPanel);
        
        // الصف الرابع: الحالة
        statusLabel = new JLabel("Connecting to the server...", SwingConstants.CENTER);
        add(statusLabel);

        // إعداد الأحداث
        processButton.addActionListener(e -> processInversion());
        inputField.addActionListener(e -> processInversion()); // عند الضغط على Enter

        // محاولة الاتصال بالخادم في الخلفية
        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                // البحث عن السجل على نفس الجهاز
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                
                // البحث عن الخدمة باسمها
                stub = (ServiceInversion) registry.lookup("ServiceInversion");
                
                // تحديث الواجهة عند نجاح الاتصال
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Status: Connected to server successfully.");
                    statusLabel.setForeground(new Color(0, 150, 0));
                    processButton.setEnabled(true);
                });
                
            } catch (Exception e) {
                // تحديث الواجهة عند الفشل
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Error: Server not found. Please ensure it is running first..");
                    statusLabel.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(this, "Connection to server failed.\nMake sure it is turned on ServerGUI firstly.", "Connection error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void processInversion() {
        String input = inputField.getText();
        if (input.isEmpty()) return;

        try {
            // استدعاء الدالة البعيدة
            String result = stub.inverserChaine(input);
            outputField.setText(result);
        } catch (Exception e) {
            outputField.setText("Processing error!");
            JOptionPane.showMessageDialog(this, "An error occurred while connecting to the server.:\n" + e.getMessage(), "error RMI", JOptionPane.ERROR_MESSAGE);
            // ربما الخادم توقف، نعيد محاولة الاتصال
            processButton.setEnabled(false);
            connectToServer();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI().setVisible(true));
    }
}