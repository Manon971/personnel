package ihm;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class Fenetre
{
 public static void main(String[] args)
 {
  JFrame f = new JFrame();
	JPanel panneau = new JPanel();
	panneau.setPreferredSize(new Dimension(250, 150));
  f.setVisible(true);
  f.setTitle("Gestion des ligues");
  f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  f.setContentPane(panneau);
  panneau.setBackground(Color.pink);
  f.setBackground(Color.pink);
  //f.setSize(200, 200);
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}
