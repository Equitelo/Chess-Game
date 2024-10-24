/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chess;

import java.awt.*;
import javax.swing.*;

public class Chess {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setLayout(new GridBagLayout()); 
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Board board = new Board();
        frame.add(board);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
