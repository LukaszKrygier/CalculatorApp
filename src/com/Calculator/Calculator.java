package src.com.Calculator;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Calculator {
    private JFrame frame;
    private JTextField textField;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton equalsButton, clearButton, backspaceButton, percentButton, plusMinusButton, decimalPointButton;

    private double num1, num2;
    private String operator = "";

    public Calculator() {
        
        try {
            // Set the system's look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the main JFrame
        frame = new JFrame("Calculator");
        frame.setIconImage(new ImageIcon(getClass().getResource("/images/calculator.png")).getImage());

        // Initialize the text field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24)); // Set font
        textField.setPreferredSize(new Dimension(200, 50)); 
        textField.setBackground(new Color(87, 87 , 87)); // Background color
        textField.setForeground(Color.WHITE); // Text color
        textField.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        textField.setEditable(false); // Disable editing in the text field

        // Initialize the number and operation buttons arrays
        numberButtons = new JButton[10];
        operationButtons = new JButton[4];

        // Set up the frame layout and add text field to the top of the frame
        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.NORTH);

        // Initialize the panel where buttons will be placed in a grid
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        // Initialize number buttons (0-9)
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new GradientButton(String.valueOf(i), new Color(87, 87, 87), new Color(120, 120, 120));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            numberButtons[i].setForeground(Color.WHITE);
            numberButtons[i].addActionListener(new NumberButtonListener());
            numberButtons[i].setBorderPainted(false);
        }

        // Initialize operation buttons (+, -, *, /)
        String[] operations = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            operationButtons[i] = new GradientButton(operations[i], new Color(46, 182, 44), new Color(131, 212, 117));
            operationButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            operationButtons[i].setForeground(Color.WHITE);
            operationButtons[i].addActionListener(new OperationButtonListener());
            operationButtons[i].setBorderPainted(false);
        }

        // Initialize other buttons (equals, clear, backspace, percent, plus/minus and decimal point)
        equalsButton = new GradientButton("=", new Color(46, 182, 44), new Color(131, 212, 117));
        equalsButton.setFont(new Font("Arial", Font.BOLD, 20));
        equalsButton.setBackground(new Color(40, 222, 16));
        equalsButton.setForeground(Color.WHITE);
        equalsButton.addActionListener(new EqualsButtonListener());
        equalsButton.setBorderPainted(false);


        clearButton = new GradientButton("C", new Color(0, 0, 255), new Color(120, 121, 255));
        clearButton.setFont(new Font("Arial", Font.BOLD, 20));
        clearButton.setBackground(new Color(87, 87, 87));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(new ClearButtonListener());
        clearButton.setBorderPainted(false);


        backspaceButton = new GradientButton("<<", new Color(255, 212, 0), new Color(255, 234, 97));
        backspaceButton.setFont(new Font("Arial", Font.BOLD, 20));
        backspaceButton.setBackground(new Color(168, 168, 168));
        backspaceButton.setForeground(Color.WHITE);
        backspaceButton.addActionListener(new BackspaceButtonListener());
        backspaceButton.setBorderPainted(false);


        percentButton = new GradientButton("%",new Color(255, 212, 0), new Color(255, 234, 97));
        percentButton.setFont(new Font("Arial", Font.BOLD, 20));
        percentButton.setBackground(new Color(168, 168, 168));
        percentButton.setForeground(Color.WHITE);
        percentButton.addActionListener(new PercentButtonListener());
        percentButton.setBorderPainted(false);


        plusMinusButton = new GradientButton("+/-", new Color(255, 212, 0), new Color(255, 234, 97));
        plusMinusButton.setFont(new Font("Arial", Font.BOLD, 20));
        plusMinusButton.setBackground(new Color(168, 168, 168));
        plusMinusButton.setForeground(Color.WHITE);
        plusMinusButton.addActionListener(new PlusMinusButtonListener());            
        plusMinusButton.setBorderPainted(false);


        decimalPointButton = new GradientButton(".", new Color(0, 0, 255), new Color(120, 121, 255));
        decimalPointButton.setFont(new Font("Arial", Font.BOLD, 20));
        decimalPointButton.setBackground(new Color(87, 87, 87));
        decimalPointButton.setForeground(Color.WHITE);
        decimalPointButton.addActionListener(new DecimalPointListener());
        decimalPointButton.setBorderPainted(false);


        // Add buttons to the panel

        panel.add(backspaceButton);
        panel.add(plusMinusButton);
        panel.add(percentButton);
        panel.add(operationButtons[3]); // "/"

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(operationButtons[2]); // "*"

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(operationButtons[1]); // "-"

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(operationButtons[0]); // "+"

        panel.add(clearButton);
        panel.add(numberButtons[0]);
        panel.add(decimalPointButton);
        panel.add(equalsButton);

        // Add panel to the frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    // Listener for operation buttons (+, -, *, /)
    class OperationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String currentText = textField.getText();

            if (!currentText.isEmpty() && !currentText.matches(".*[+\\-*/] .*")) {
                textField.setText(currentText + " " + command + " ");
            }
        }
    }

    // Listeners for other buttons (equals, clear, backspace, percent, plus/minus and decimal point)
    class EqualsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String expression = textField.getText();
                String[] tokens = expression.split(" ");

                // Handle case where there's only one value (num1) with a percentage
                if (tokens.length == 1) {
                    String num1Str = tokens[0];

                    // If num1 has a %, divide it by 100 and show the result
                    if (num1Str.contains("%")) {
                        num1Str = num1Str.replace("%", ""); // Remove the '%' symbol
                        double num1 = Double.parseDouble(num1Str) / 100;
                        textField.setText(String.valueOf(num1)); // Show the result
                        return;
                    } else {
                        // If there's no % symbol, simply display the value as is
                        double num1 = Double.parseDouble(num1Str);
                        textField.setText(String.valueOf(num1));
                        return;
                    }
                }

                // Proceed with the standard case (num1 operator num2)
                String num1Str = tokens[0];
                String num2Str = tokens[2];

                // Remove parentheses around negative numbers
                if (num1Str.startsWith("(") && num1Str.endsWith(")")) {
                    num1Str = num1Str.substring(1, num1Str.length() - 1);
                }
                if (num2Str.startsWith("(") && num2Str.endsWith(")")) {
                    num2Str = num2Str.substring(1, num2Str.length() - 1);
                }

                double num1 = Double.parseDouble(num1Str.replace("%", ""));
                double num2 = Double.parseDouble(num2Str.replace("%", ""));
                String operator = tokens[1];

                // Handle percentage symbol conversion
                if (num1Str.contains("%")) {
                    num1 = num1 / 100; // Convert num1 to decimal if it contains %
                }
                if (num2Str.contains("%")) {
                    num2 = num2 / 100; // Convert num2 to decimal if it contains %
                }

                double result = 0;

                // Perform the operation based on the operator
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            textField.setText("Error");
                            return;
                        }
                        result = num1 / num2;
                        break;
                    default:
                        textField.setText("Error");
                        return;
                }

                // Display the result
                textField.setText(String.valueOf(result));

            } catch (Exception ex) {
                textField.setText("Error");
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textField.setText("");
            num1 = 0;
            num2 = 0;
            operator = "";
        }
    }

    private class BackspaceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textField.getText();
            if (!currentText.isEmpty()) {
                textField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    private class PercentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textField.getText();
            String[] parts = currentText.split(" ");

            if (parts.length == 1) {
                textField.setText(currentText + "%");
            }

            else if (parts.length == 3) {
                String num2 = parts[2];
                if (!num2.contains("%")) {
                    textField.setText(currentText + "%");
                }
            }

        }
    }

    private class PlusMinusButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textField.getText();

            // If current text is empty, do nothing
            if (currentText.isEmpty()) {
                return;
            }

            // Check if there's an operator, we only toggle the sign of num1 or num2
            String[] parts = currentText.split(" ");

            if (parts.length == 1) {
                // If num1 is negative, display it as (-num1)
                double num1 = Double.parseDouble(parts[0]);
                num1 = -num1;  // Negate the value
                if (num1 < 0) {
                    textField.setText("(" + num1 + ")");
                } else {
                    textField.setText(String.valueOf(num1));
                }
            } else if (parts.length == 3) {
                // If there's num1, operator, and num2, toggle the sign for num2
                String num2Str = parts[2];
                double num2 = Double.parseDouble(num2Str);
                num2 = -num2;  // Negate the value
                if (num2 < 0) {
                    textField.setText(parts[0] + " " + parts[1] + " (" + num2 + ")");
                } else {
                    textField.setText(parts[0] + " " + parts[1] + " " + num2);
                }
            }
        }
    }

    private class DecimalPointListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textField.getText().trim(); // Trim spaces
            String[] parts = currentText.split(" ");

            // Case 1: If textField is empty, initialize num1 with "0."
            if (currentText.isEmpty()) {
                textField.setText("0.");
                return;
            }

            // Get the last character to check if it's an operator
            char lastChar = currentText.charAt(currentText.length() - 1);

            // Case 2: If the last character is an operator, we should start num2 with "0."
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                textField.setText(currentText + " 0.");
                return;
            }

            // Case 3: If there's num1, operator, and num2 already in parts
            if (parts.length == 3) {
                String num1 = parts[0];
                String num2 = parts[2];

                // If num1 doesn't contain a decimal, add one
                if (!num1.contains(".")) {
                    textField.setText(currentText + ".");
                }
                // If num1 has a decimal but num2 does not, add a decimal to num2
                else if (!num2.contains(".")) {
                    textField.setText(currentText + ".");
                }
            }
            // Case 4: If there's only num1 and no operator yet
            else if (parts.length == 1 && operator.isEmpty()) {
                String num1 = parts[0];
                if (!num1.contains(".")) {
                    textField.setText(currentText + ".");
                }
            }
        }
    }

    // Listener for number buttons (0-9 and decimal)
    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String buttonText = clickedButton.getText();
            textField.setText(textField.getText() + buttonText);
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
