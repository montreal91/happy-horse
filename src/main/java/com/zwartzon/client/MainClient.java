package com.zwartzon.client;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainClient {

  public static void Main(String args[]) {
    JFrame frame = new JFrame("Happy Horse");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    JButton button = new JButton("Press");
    frame.getContentPane().add(button);
    frame.setVisible(true);
  }
}
