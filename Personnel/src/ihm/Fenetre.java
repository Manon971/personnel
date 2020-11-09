package ihm;

import javax.swing.*;

public class Fenetre
{
 public static void main(String[] args)
 {
  JFrame f = new JFrame();
  f.setVisible(true);
  f.setTitle("Gestion des ligues");
  f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  
  //f.setSize(200, 200);
  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}
