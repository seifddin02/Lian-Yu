import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javafx.event.Event;
import java.util.Scanner;

public class GUI extends Game implements ActionListener
{
    private JFrame frame;
    private JTextArea textArea;
    private JButton button;
    private JLabel label;
    private JPanel picPanel;
    private JLabel picLabel;
    private ImageIcon image;
    private Parser parser;
    private Room currentRoom;
    private Container con;
    private JPanel titleName,mainTextPanel, buttonPanel;
    private JLabel titleLabel;
    private JTextArea textAreaInput;

    
    public GUI(){

        frame = new JFrame();
        // frame.setLayout(new FlowLayout());
        frame.setLayout(null);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1280,840);
        frame.getContentPane().setBackground(Color.black);
        
        con = frame.getContentPane();
        
        titleName = new JPanel();
        titleName.setBounds(100,100,600,150);
        titleName.setBackground(Color.black);
        titleLabel = new JLabel("LIAN YU");
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        
        buttonPanel = new JPanel();
        buttonPanel.setBounds(300,400,200,100);
        buttonPanel.setBackground(Color.black);
        
        button = new JButton("Okay");
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        
        titleName.add(titleLabel);
        buttonPanel.add(button);
        
        con.add(titleName);
        con.add(buttonPanel);
        
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(50,300,600,250);
        mainTextPanel.setBackground(Color.black);
        
        con.add(mainTextPanel);
        
        
        textArea = new JTextArea();
        textArea.setBounds(50,300,600,250);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.white);
        textArea.setLineWrap(true);
        mainTextPanel.add(textArea);
        
        
        
        
        textAreaInput = new JTextArea("Your Command");
        textAreaInput.setBounds(600,400,400,50);
        textAreaInput.setBackground(Color.white);
        textAreaInput.setForeground(Color.black);

        
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(600,400,400,50);
        inputPanel.setBackground(Color.white);
        inputPanel.add(textAreaInput);
        con.add(inputPanel);
    
       
        // label = new JLabel("");
        // label.setBounds(50,350,430,250);
        // frame.add(label);
        
        picPanel = new JPanel();
        picPanel.setBounds(150,100,500,300);
        picPanel.setBackground(Color.blue);
        frame.add(picPanel);
        
        picLabel = new JLabel();
        
        image = new ImageIcon("D:/lianyu.jpg");
        
        picLabel.setIcon(image);
        picPanel.add(picLabel);
        
        button.addActionListener(this);
        
        frame.setVisible(true);
               
        Game game1 = new Game();
        

        game1.play();
    }
    
    
    
    
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==button)
        {
        String text = textAreaInput.getText();
        
        textArea.setText(text);
        parser = new Parser(textAreaInput.getText());
        
        
        }
    
    }
    
   

}
