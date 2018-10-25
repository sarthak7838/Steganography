/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package foreinsickit;

/**
 *
 * @author Sarthak Goel
  */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import sun.audio.*;
import java.applet.*;
import java.math.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.IOException;

class mainframe extends JFrame implements ActionListener, Runnable {

    JLabel Lfilename, Lmessage, Ldesign, Ltitle;
    JButton Bplay, Bopen, Bsave, Bstop, Bencrypt, Bdecrypt, Bsend, Bclear;
    JTextArea Amessage;
    JTextField Tfilename;
    Icon Iplay, Iopen, Istop, Isave;
    String Ekey, Dkey, address, name;
    JFileChooser filechooser;
    File Ofilename, Sfilename, tempfilename;
    InetAddress ipaddress;
    int Copened, Cencrypt, Cdecrypt, Cplay, Cstop, Csave;
    InputStream ins;
    AudioStream as;
    Thread t;

    public mainframe() throws Exception {

        // frame

        super("Steganography Using Audio");
        Container con = getContentPane();
        con.setLayout(null);

        // Basic

        Copened = 0;
        Cencrypt = 0;
        Cdecrypt = 0;
        Cplay = 0;
        Csave = 0;
        Cstop = 0;

        t = new Thread(this);
        t.start();

        // Icons

        Iplay = new ImageIcon("play.jpg");
        Isave = new ImageIcon("save.jpg");
        Iopen = new ImageIcon("open.jpg");
        Istop = new ImageIcon("stop.jpg");

        // file chooser

        filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // comp

        Ltitle = new JLabel("Steganography Using Audio");
        Ldesign = new JLabel("");
        Lfilename = new JLabel("File Name ");
        Lmessage = new JLabel("Message  ");
        Bplay = new JButton("Play", Iplay);
        Bopen = new JButton("Open", Iopen);
        Bsave = new JButton("Save", Isave);
        Bstop = new JButton("Stop", Istop);
        Bclear = new JButton("Clear");
        Bencrypt = new JButton("Encoding");
        Bdecrypt = new JButton("Decoding");
        Bsend = new JButton("Send");
        Amessage = new JTextArea();
        Tfilename = new JTextField();

        // tool tips

        Tfilename.setToolTipText("Opened filename");
        Bplay.setToolTipText("play");
        Bopen.setToolTipText("open");
        Bsave.setToolTipText("save");
        Bstop.setToolTipText("stop");

        Tfilename.setEditable(false);

        // Bounds

        Ltitle.setBounds(300, 30, 250, 25);
        Lfilename.setBounds(100, 100, 100, 25);
        Tfilename.setBounds(100, 125, 230, 25);
        Lmessage.setBounds(450, 100, 100, 25);
        Amessage.setBounds(510, 125, 250, 220);
        Bclear.setBounds(510, 370, 80, 22);
        Bplay.setBounds(100, 200, 90, 25);
        Bstop.setBounds(200, 200, 90, 25);
        Bopen.setBounds(300, 200, 90, 25);
        Bsave.setBounds(400, 200, 90, 25);
        Bencrypt.setBounds(100, 250, 110, 25);
        Bdecrypt.setBounds(220, 250, 110, 25);
        //Bsend.setBounds(160, 300, 110, 25);
        Ldesign.setBounds(350, 420, 400, 50);


        // add

        con.add(Ltitle);
        con.add(Ldesign);
        con.add(Lfilename);
        con.add(Tfilename);
        con.add(Lmessage);
        con.add(Amessage);
        con.add(Bclear);
        con.add(Bplay);
        con.add(Bopen);
        con.add(Bsave);
        con.add(Bstop);
        con.add(Bencrypt);
        con.add(Bdecrypt);
        //con.add(Bsend);

        // actionListener

        Bclear.addActionListener(this);
        Bplay.addActionListener(this);
        Bopen.addActionListener(this);
        Bsave.addActionListener(this);
        Bstop.addActionListener(this);
        Bencrypt.addActionListener(this);
        Bdecrypt.addActionListener(this);
        Bsend.addActionListener(this);

    } // constr of mainframe

    public void run() {
        try {
            recv r = new recv();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String encode(String s, String key) {
        return base64Encode(xorWithKey(s.getBytes(), key.getBytes()));
    }

    public String decode(String s, String key) {
        return new String(xorWithKey(base64Decode(s), key.getBytes()));
    }

    private byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i%key.length]);
        }
        return out;
    }

    private byte[] base64Decode(String s) {
        try {
            BASE64Decoder d = new BASE64Decoder();
            return d.decodeBuffer(s);
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private String base64Encode(byte[] bytes) {
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(bytes).replaceAll("\\s", "");

    }
    public void Audioencrypt(String message, File file, int key) throws
            Exception {
        byte b[] = new byte[1];
        BigInteger Abi, Mbi;
        int k, k1;
        InputStream ins = new FileInputStream(file);
        OutputStream outs = new FileOutputStream(new File("E:/pop8.wav"));
        
        for (int c = 0; c < key; c++) {
            int ch = ins.read();
            outs.write(ch);            
        }

        System.out.println(message);
        String encoded = encode(message,String.valueOf(key));
        message = encoded;
        int len = message.length();
        System.out.println(message);
        byte mess[] = new byte[1];
        char chmess[] = new char[len + 1];
        k = k1 = 0;
        for (int i = 0; i <= len; i++) {
            message.getChars(0, len, chmess, 0);
            if (i == 0) {
                BigDecimal bd = new BigDecimal(len);
                BigInteger Blen = bd.toBigInteger();
                String Slen = Blen.toString(2);
                char Clen[] = new char[Blen.bitLength()];
//                System.out.println(Blen.bitLength());
                Slen.getChars(0, Blen.bitLength(), Clen, 0);
                for (int j = 0; j <= 7; j++) {
                    if (j == 0) {
                        for (k = 0; k < 8 - Blen.bitLength(); k++) {
                            int n = ins.read(b);
                            Abi = new BigInteger(b);
                            String Aby = Abi.toString(2);
                            int Alen = Abi.bitLength();
//                            System.out.println("Alen"+Alen);
//                            System.out.println("N"+ n);
                            if (b[0] < 0) {
                                Alen++;
                            }
                            char Ach[] = new char[Alen + 1];
                            Aby.getChars(0, Alen, Ach, 0);

                            if (b[0] == 0) {
                            } else {
                                if (Ach[Alen - 1] == '1') {
                                    if (Alen == Abi.bitLength()) {
                                        BigInteger bi = new BigInteger("11111110", 2);
                                        BigInteger big = Abi.and(bi);
                                        b = big.toByteArray();
                                    } else {
                                        BigInteger bi = new BigInteger("-1", 2);
                                        BigInteger big = Abi.subtract(bi);
                                        b = big.toByteArray();
//                                        System.out.println("Big"+big +"Abi "+Abi +"Bi "+ bi) ;
                                    }
                                }
                                outs.write(b);
                            }
                        }  //for loop k
                        j = j + k - 1;
                    } // if of j
                    else {
                        int n = ins.read(b);
                        Abi = new BigInteger(b);
                        String Aby = Abi.toString(2);
                        int Alen = Abi.bitLength();
                        if (b[0] < 0) {
                            Alen++;
                        }
                        char Ach[] = new char[Alen + 1];
                        Aby.getChars(0, Alen, Ach, 0);
                        if (b[0] == 0) {
                            Alen = 1;
                        }
                        if (Clen[j - k] == '0' && Ach[Alen - 1] == '1') {
                            if (Alen == Abi.bitLength()) {
                                BigInteger bi = new BigInteger("11111110", 2);
                                BigInteger big = Abi.and(bi);
                                b = big.toByteArray();
                            } else {
                                BigInteger bi = new BigInteger("-1", 2);
                                BigInteger big = Abi.subtract(bi);
                                b = big.toByteArray();
                            }
                        } else if (Clen[j - k] == '1' && Ach[Alen - 1] == '0') {
                            if (Alen == Abi.bitLength()) {
                                BigInteger bi = new BigInteger("1", 2);
                                BigInteger big = Abi.add(bi);
                                b = big.toByteArray();
                            } else {
                                BigInteger bi = new BigInteger("-1", 2);
                                BigInteger big = Abi.add(bi);
                                b = big.toByteArray();
                            }

                        }
                        outs.write(b);
                    } // end else

                } // for loop j

            } // end of if
            else {
                String slen = String.valueOf(chmess[i - 1]);
                byte blen[] = slen.getBytes();
                BigInteger Blen = new BigInteger(blen);
                String Slen = Blen.toString(2);
//                System.out.println("Message is "+ slen+"Blen "+Blen +"Slen " +Slen);
                char Clen[] = new char[Blen.bitLength()];
                Slen.getChars(0, Blen.bitLength(), Clen, 0);
                for (int j = 0; j <= 7; j++) {
                    if (j == 0) {
                        for (k1 = 0; k1 < 8 - Blen.bitLength(); k1++) {
                            int n = ins.read(b);
                            Abi = new BigInteger(b);
                            String Aby = Abi.toString(2);
                            int Alen = Abi.bitLength();
                            if (b[0] < 0) {
                                Alen++;
                            }
                            char Ach[] = new char[Alen + 1];
                            Aby.getChars(0, Alen, Ach, 0);
                            if (b[0] == 0) {
                            } else {
                                if (Ach[Alen - 1] == '1') {
                                    if (Alen == Abi.bitLength()) {
                                        BigInteger bi = new BigInteger("11111110", 2);
                                        BigInteger big = Abi.and(bi);
                                        b = big.toByteArray();
                                    } else {
                                        BigInteger bi = new BigInteger("-1", 2);
                                        BigInteger big = Abi.subtract(bi);
                                        b = big.toByteArray();
                                    }
                                }
                            }
                            outs.write(b);

                        }  //for loop k

                        j = j + k1 - 1;

                    } // if of j
                    else {
                        int n = ins.read(b);
                        Abi = new BigInteger(b);
                        String Aby = Abi.toString(2);
                        int Alen = Abi.bitLength();
                        if (b[0] < 0) {
                            Alen++;
                        }
                        char Ach[] = new char[Alen + 1];
                        Aby.getChars(0, Alen, Ach, 0);
                        if (b[0] == 0) {
                            Alen = 1;
                        }

                        if (Clen[j - k1] == '0' && Ach[Alen - 1] == '1') {
                            if (Alen == Abi.bitLength()) {
                                BigInteger bi = new BigInteger("11111110", 2);
                                BigInteger big = Abi.and(bi);
                                b = big.toByteArray();
                            } else {
                                BigInteger bi = new BigInteger("-1", 2);
                                BigInteger big = Abi.subtract(bi);
                                b = big.toByteArray();
                            }
                        } else if (Clen[j - k1] == '1' && Ach[Alen - 1] == '0') {
                            if (Alen == Abi.bitLength()) {
                                BigInteger bi = new BigInteger("1", 2);
                                BigInteger big = Abi.add(bi);
                                b = big.toByteArray();
                            } else {
                                BigInteger bi = new BigInteger("-1", 2);
                                BigInteger big = Abi.add(bi);
                                b = big.toByteArray();
                            }
                        }
                        outs.write(b);
                    } // end else

                } // for loop j
            } // end of else

        } // for loop i

        while (true) {
            int i = ins.read();
            if (i == -1) {
                break;
            }
            outs.write(i);
        }
        ins.close();
        outs.close();
    }

    public void Audiodecrypt(File filename, int key) throws Exception {
        InputStream ins = new FileInputStream(filename);
        byte b[] = new byte[1];
        BigInteger bb1;
        char mess[] = new char[8];
        int c = 0;
        for (int i = 0; i < key; i++) {
            int n = ins.read();
        }
        for (int i = 0; i < 8; i++) {
            ins.read(b);
            bb1 = new BigInteger(b);
            String str = bb1.toString(2);
            int len = bb1.bitLength();
            if (b[0] < 0) {
                len++;
            }
            char ch[] = new char[len + 1];
            str.getChars(0, len, ch, 0);
            if (b[0] == 0) {
                mess[i] = '0';
            } else {
                mess[i] = ch[len - 1];
            }
        }
        String dd = new String(mess);
        BigInteger bb = new BigInteger(dd, 2);
        String s = bb.toString(2);
        int l = bb.intValue();

        char me[] = new char[l];
        int count = 0;

        for (int m = 0; m < l; m++) {
            for (int i = 0; i < 8; i++) {
                ins.read(b);
                bb1 = new BigInteger(b);
                String str = bb1.toString(2);
                int len = bb1.bitLength();
                if (b[0] < 0) {
                    len++;
                }
                char ch[] = new char[len + 1];
                str.getChars(0, len, ch, 0);
                if (b[0] == 0) {
                    mess[i] = '0';
                } else {
                    mess[i] = ch[len - 1];
                }
            }
            String dd1 = new String(mess);
            BigInteger bb2 = new BigInteger(dd1, 2);
            String s1 = bb2.toString(2);
            int l1 = bb2.intValue();
            me[count] = (char) l1;
            count++;
        }

        String message = new String(me);
         System.out.println(message);
         String finalmsg =message;
        String decoded = decode(finalmsg,String.valueOf(key));
        finalmsg = decoded;
        System.out.println(finalmsg);
        Amessage.setText(finalmsg);
        ins.close();
    }

    public void actionPerformed(ActionEvent ae) {

        try {

            // Action for encryption button

            if (ae.getSource() == Bencrypt) {
                if (Copened == 1) {
                    Ekey = JOptionPane.showInputDialog("Enter The Key For Encryption");
//String type
                    if (Ekey.trim().equals("")) {
                        JOptionPane.showMessageDialog(this, "Enter theKey", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // encrypt the message
                        int key = Integer.parseInt(Ekey);
                        Audioencrypt(Amessage.getText(), Ofilename, key);
                        Cencrypt = 1;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "File NotOpened", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } // end of Bencrypt
            // Action for Clear button
            else if (ae.getSource() == Bclear) {
                Amessage.setText("");
            } // end of clear button
            // Action for Decrypt button
            else if (ae.getSource() == Bdecrypt) {
                if (Copened == 1) {
                    Dkey = JOptionPane.showInputDialog("Enter The Key For Decryption");
//String type
                    if (Dkey.trim().equals("")) {
                        JOptionPane.showMessageDialog(this, "Enter theKey", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // decrypt message
                        int key = Integer.parseInt(Dkey);
                        try {
                            Audiodecrypt(Ofilename, key);
                        } catch (Exception ex) {
                            Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Cdecrypt = 1;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "File NotOpened", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } // end of Decrypt button
            // Action for Play button
            else if (ae.getSource() == Bplay) {
                if (Copened == 1) {
                    try {
                        ins = new FileInputStream(Ofilename);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        as = new AudioStream(ins);
                    } catch (IOException ex) {
                        Logger.getLogger(mainframe.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    AudioPlayer.player.start(as);
                    Cplay = 1;
                    Cstop = 0;

                } // start playing
                else {
                    JOptionPane.showMessageDialog(this, "File NotOpened", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } // end of play button
            // Action for Stop button
            else if (ae.getSource() == Bstop) {

                if (Cplay == 1) {

                    Cplay = 0;
                    Cstop = 1;
                    AudioPlayer.player.stop(as);

                } // stop plaing
                else {
                    JOptionPane.showMessageDialog(this, "No Audio Isplaying", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } // end of stop button
            // Action for open Button
            else if (ae.getSource() == Bopen) {
                int r = filechooser.showOpenDialog(this);
                tempfilename = filechooser.getSelectedFile(); //File type
                if (r == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(this, "File NotSelected", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    name = tempfilename.getName();

                    if (!(name.endsWith(".wav"))) {
                        JOptionPane.showMessageDialog(this, "Select OnlyWav", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Copened = 1;
                        Ofilename = tempfilename;
                        Tfilename.setEditable(true);
                        Tfilename.setText(name);
                        Tfilename.setEditable(false);
                    }

                }
            } // end of Open button
            // Action for Save Button
            else if (ae.getSource() == Bsave) {
                if (Copened == 1 && Cencrypt == 1 || Cdecrypt == 1) {
                    int r = filechooser.showSaveDialog(this);
                    Sfilename = filechooser.getSelectedFile(); //File type
                    InputStream in = new FileInputStream("E:/pop8.wav");
                    OutputStream out = new FileOutputStream(Sfilename);
                    Ofilename = Sfilename;
                    name = Sfilename.getName();
                    Tfilename.setEditable(true);
                    Tfilename.setText(name);
                    Tfilename.setEditable(false);
                    while (true) {
                        int i = in.read();
                        if (i == -1) {
                            break;
                        }
                        out.write(i);
                    }
                    in.close();
                    out.close();
                } else {
                    String s;
                    if (Copened == 0) {
                        s = "File not Opened";
                    } else if (Cencrypt == 0) {
                        s = "Not Encrypted";
                    } else {
                        s = "Not Decrypted";
                    }

                    JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } // end of save button
            // Action for send button
            else if (ae.getSource() == Bsend) {
                if (Copened == 1 && Cencrypt == 1) {
                    address = JOptionPane.showInputDialog("Enter The IPaddress");
                    ipaddress = InetAddress.getByName(address);
                    Socket socket = new Socket(ipaddress, 6000);
                    OutputStream out = socket.getOutputStream();
                    InputStream in = new FileInputStream(Ofilename);
                    while (true) {
                        int i = in.read();
                        if (i == -1) {
                            break;
                        }
                        out.write(i);
                    }
                    in.close();
                    out.close();
                } else {
                    String s;
                    if (Copened == 1) {
                        s = "Encryption not done";
                    } else {
                        s = "Open the File first";
                    }

                    JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } // end try
        catch (Exception e) {
            //
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

    } // end of actionperformed
}//end of class

class recv extends JFrame implements Runnable {

    JFileChooser fc;
    ServerSocket ss;
    Socket s;
    InputStream ins;
    OutputStream out;
    byte b[];
    int len;

    public recv() throws Exception {

        b = new byte[100];
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        ss = new ServerSocket(6000);
        torun();
    }

    public void torun() throws Exception {
        while (true) {
            s = ss.accept();
            ins = s.getInputStream();
            String str = "Your have Receive An AudioFile.Save them";

            JOptionPane.showMessageDialog(this, str, "Information", JOptionPane.INFORMATION_MESSAGE);
            int r = fc.showSaveDialog(this);
            File file = fc.getSelectedFile();
            out = new FileOutputStream(file);
            Thread t = new Thread(this);
            t.start();
        }
    }

    public void run() {
        try {
            while (true) {
                int n = ins.read();
                if (n == -1) {
                    break;
                }
                out.write(n);
            }
            //  s.close();
            ins.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } // end of run
} //end of class

