package chattingapplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{

    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();

    // here we are creating the object of JFrame class so we do not need to extend it
    static JFrame t = new JFrame();
    static DataOutputStream dout;

    Server(){

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,138,94));
        p1.setBounds(0,0,450,60);
        p1.setLayout(null);
        t.add(p1);

        // We are scaling the image i1 as per we want so we passed it to i2 but we can not direcly
        // pass the image to JLabel that will give an error(because JLabel cannot take direct an image)
        // so that's why we passed i2 image to i3 ImageIcon so that we pass it to the label
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/arrow.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/rengoku.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,0,60,60);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/vcall.png"));
        Image i8 = i7.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(310,20,25,25);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/call.png"));
        Image i11 = i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(360,20,25,25);
        p1.add(call);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3dot.png"));
        Image i14 = i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dots = new JLabel(i15);
        dots.setBounds(400,20,30,30);
        p1.add(dots);

        JLabel name = new JLabel("Rengoku");
        name.setBounds(110,10,100,20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Tahoma",Font.BOLD,16));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,15);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Tahoma",Font.BOLD,10));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(0,60,440,500);
        t.add(a1);

        text = new JTextField();
        text.setBounds(0,560,315,35);
        text.setFont(new Font("Tahoma",Font.PLAIN,14));
        t.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,560,127,35);
        send.setBackground(new Color(7,138,94));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        send.addActionListener(this);
        t.add(send);

        t.getContentPane().setBackground(Color.WHITE);
        t.setLayout(null);

        t.setSize(450,595);
        t.setLocation(200,70);
        t.setUndecorated(true);
        t.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){

        try{

            String out = text.getText();

            // instead of passing a Label and panel from here locally we are passing a separate
            // function for this.
            //JLabel output = new JLabel(out);
            JPanel p2 = formatLabel(out);
            //p2.add(output);

            // here we are not setting the layout as null because if we set it to null
            // the message will not appear cuz we set it to null and for next
            // panel we are using BorderLayout.LINE_END so that's why we have to set BorderLayout()
            a1.setLayout(new BorderLayout());

            // we cannot pass the out cuz it is string so we pass the out in the label
            // but label needs to be added on some panel so that's why we take a panel p2
            // and passed the panel
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);

            // we also need to show the message in a box manner so that's why we are using a Box
            // called vertical and in that box we are passing our right panel and then passing the
            // box to our a1 the main panel where we have to display the message
            vertical.add(right);

            // the gap between messages is because of the Box.createVerticalStrut()
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(out);

            // we removing the text present in the TextField which in this case is text TextField
            text.setText("");

            // we are adding repaint() to update the panel if we don't use repaint() then the message is there
            // but will not be displayed cuz the panel is not updated, also the validate() and invalidate()
            // methods are also necessary to display the message otherwise the message will not be displayed.
            t.repaint();
            t.invalidate();
            t.validate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        // we are adding some of the html to have a fix size for the box
        JLabel output = new JLabel("<html><p style = \"width: 150px\">"+out+ "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,14));
        output.setBackground(new Color(37,211,102));

        // setOpaque() method is used to display the background color of JLabel on the panel
        // if this method is not set to true then the background color of JLabel is not displayed
        output.setOpaque(true);

        // we are giving the border so that the message should be a little lengthy
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {

        new Server();

        try{

            ServerSocket skt = new ServerSocket(6001);

            // we are using infinite while loop for communication meaning the while loop will iterate
            // until we stop the application means stop the communication from server to client
            while (true){

                // we firstly need to accept the socket object here it is 's'
                Socket s = skt.accept();

                // DataInputStream object din is used to read the message that are coming from client
                DataInputStream din = new DataInputStream(s.getInputStream());

                // DataOutputStream object dout is used to send the message from server to client
                dout = new DataOutputStream(s.getOutputStream());

                while (true){

                    // String msg is storing the message which has been read by the din.readUTF() method
                    String msg = din.readUTF();

                    // following part is just like what we used to display the message from our
                    // TextField to the panel
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical,BorderLayout.PAGE_START);
                    t.validate();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

