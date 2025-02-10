package com.Calculator;
import javax.swing.*;
import java.awt.*;

public class GradientButton extends JButton {
    private Color startColor;
    private Color endColor;

    // Constructor that initializes the button with text and gradient colors
    public GradientButton(String text, Color startColor, Color endColor) {
        super(text);
        this.startColor = startColor;
        this.endColor = endColor;
        setContentAreaFilled(false);  // Prevents the default button area fill
        setFocusPainted(false);       // Removes focus indicator
        setBorderPainted(false);      // Removes the default border
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Create a Graphics2D object for advanced drawing capabilities
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a gradient that goes from the startColor to endColor across the button
        GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
        g2.setPaint(gradient);

        // Draw the rounded rectangle with the gradient fill (width and height of the button)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);

        // Release system resources after painting
        g2.dispose();
        
        // Call the superclass's paintComponent method to ensure the button's text and other properties are rendered
        super.paintComponent(g);
    }
}
