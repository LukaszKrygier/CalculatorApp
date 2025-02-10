package com.Calculator;
import javax.swing.*;
import java.awt.*;

public class GradientButton extends JButton {
    private Color startColor;
    private Color endColor;

    public GradientButton(String text, Color startColor, Color endColor) {
        super(text);
        this.startColor = startColor;
        this.endColor = endColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a gradient from startColor to endColor
        GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
        g2.setPaint(gradient);
        //int size = Math.min(getWidth(), getHeight());
        //g2.fillRoundRect(0, 0, size, size, size, size);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);

        g2.dispose();
        super.paintComponent(g);
    }
}