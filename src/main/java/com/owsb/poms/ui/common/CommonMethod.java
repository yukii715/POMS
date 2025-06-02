package com.owsb.poms.ui.common;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CommonMethod {
    public void setLabelIcon(String filePath, int width, int height, int hints, JLabel label){
        ImageIcon icon = new ImageIcon(getClass().getResource(filePath));
        Image img = (icon).getImage().getScaledInstance(width, height, hints);
        icon = new ImageIcon(img);
        label.setIcon(icon);
    }
    
    public void setFrameIcon(String filePath, int width, int height, int hints, JFrame frame){
        ImageIcon icon = new ImageIcon(getClass().getResource(filePath));
        Image img = (icon).getImage().getScaledInstance(width, height, hints);
        frame.setIconImage(img);
    }
    
    public static ImageIcon getRoundedImageIcon(Image img, int size) {
        BufferedImage rounded = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rounded.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size); //draw a circle starts at (0,0)
        g2.setClip(circle);
        g2.drawImage(img, 0, 0, size, size, null);
        g2.dispose();

        return new ImageIcon(rounded);
    }
    
    public static void setRoundedLabelIcon(String filePath, JLabel label, int size){
        try {
            Image img = ImageIO.read(new File(filePath));
            img = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            ImageIcon circularIcon = getRoundedImageIcon(img, size);
            label.setIcon(circularIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
