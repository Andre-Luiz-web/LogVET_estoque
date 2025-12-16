package Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.Dimension;

public class RoundedPanel extends JPanel {

    private int arcWidth;
    private int arcHeight;
    private Color backgroundColor;

    public RoundedPanel(int radius, Color bgColor) {
        this(radius, radius, bgColor);
    }

    public RoundedPanel(int arcWidth, int arcHeight, Color bgColor) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.backgroundColor = bgColor;
        setOpaque(false); // Make the JPanel non-opaque so we can paint our own background
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle background
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));

        g2.dispose();
    }

  /*  @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }*/
}