package ihm;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;

public class Fenetre
{
 public static void main(String[] args)
 {
  JFrame f = new JFrame();
	JPanel panel = new JPanel();
	panel.setPreferredSize(new Dimension(250, 150));
  f.setVisible(true);
  f.setTitle("Gestion des ligues");
  f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  f.setContentPane(panel);
  panel.setBackground(Color.gray);
  f.setBackground(Color.gray);
  panel.setLayout(new FlowLayout());
  f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  JLabel label = new JLabel("Maison des ligues");
  panel.add(label);
  label.setFont(new Font("Serif", Font.BOLD, 40));
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}
