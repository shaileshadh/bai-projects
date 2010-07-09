

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import com.sun.speech.freetts.lexicon.Lexicon;
import com.sun.speech.freetts.en.us.CMULexicon;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main{

    static Lexicon lex;
    static Draw draw;

    static Map<String,Point> startPosM;
    static Map<String,Point> endPosM;
    static Map<String,BufferedImage> imageM;

    static Map<String,String[]> briefForms;
    
    /* Runtime loading of resources */
    static void load() throws Throwable {
        // Load lexer
        lex = CMULexicon.getInstance(true);

        // Contains positions for start and end points repectively
        startPosM = new HashMap<String,Point>();
        endPosM = new HashMap<String,Point>();

        imageM = new HashMap<String,BufferedImage>();
        briefForms = new HashMap<String,String[]>();

        // Read data file
        BufferedReader in = new BufferedReader(
            new FileReader("./alphabet/1.dat"));
        
        String line;
        while( (line = in.readLine()) != null){

            // Parse point position data
            Scanner sc = new Scanner(line);
            String base = sc.next();
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            startPosM.put(base,new Point(x1,y1));
            endPosM.put(base,new Point(x2,y2));

            // Read associated image
            File imfile = new File("./alphabet/" + base + ".png");
            BufferedImage image = ImageIO.read(imfile);
            imageM.put(base,image);
        }

        // Read brief forms file
        in = new BufferedReader(new FileReader("./alphabet/2.dat"));
        while( (line = in.readLine()) != null){
            String[] tks = line.split(" ");
            String base = tks[0];
            String[] tail = Arrays.copyOfRange(tks,1,tks.length);
            briefForms.put(base,tail);
        }
    }

    /* Create GUI windows */
    static void startGUI(){
        final JFrame frame = new JFrame();
        frame.setSize(600,800);
        frame.setLayout(new BorderLayout());

        // Exit on close
        frame.setDefaultCloseOperation(3);

        draw = new Draw();
        frame.add(draw,BorderLayout.CENTER);

        final JTextField tx = new JTextField();
        frame.add(tx,BorderLayout.SOUTH);

        tx.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){r();}
            public void removeUpdate(DocumentEvent e){r();}
            public void changedUpdate(DocumentEvent e){r();}
            void r(){
                // Refresh contents
                draw.setContents(tx.getText());
                draw.repaint();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) throws Throwable {
        load();
        startGUI();
    }

    /* English word to gregg codes */
    static String[] fromWord(String s){

        // Brief form
        if(briefForms.containsKey(s))
            return briefForms.get(s);

        String[] phs = lex.getPhones(s.toLowerCase(),"n");
        String[] gphs = new String[phs.length];
        for(int i=0; i<phs.length; i++)
            gphs[i] = ph2gregg(phs[i]);
        return gphs;
    }

    /* Look up from TTS codes to suitable gregg codes. We put the two letter
       codes first, so that for instance ph wouldn't match p before trying
       to match ph since we are detecting with startsWith not equals because
       some vowels have numbers after them to describe tone. */
    static String ph2gregg(String ph){
        String[][] cv = {
            {"aa","o"}, {"ax",""}, {"ae",""}, {"ey","a"}, {"eh",""},
            {"ih",""}, {"iy","e"}, {"ay","i"}, {"oy","o"}, {"ao","o"},
            {"ow","o"}, {"ah",""}, {"aw","u"}, {"uw","u"}, {"uh","u"},
            {"ch","ch"}, {"hh","h"}, {"jh","j"}, {"ng","ng"}, {"er","r"},
            {"sh","sh"}, {"zh","sh"}, {"th","th"}, {"dh","th"}, {"v","v"},
            {"y","e"}, {"m","m"}, {"n","n"}, {"s","s"}, {"d","d"},
            {"f","f"}, {"k","k"}, {"l","l"}, {"w","u"}, {"g","g"},
            {"r","r"}, {"p","p"}, {"t","t"}, {"z","s"}, {"b","b"} };

        for(String[] c : cv) if(ph.startsWith(c[0])) return c[1];
        return null;
    }

    static class Draw extends JPanel{
        String[][] words;

        Draw(){
            setBackground(Color.WHITE);
            setContents("");
        }

        void setContents(String s){
            String[] ws = s.split("\\s");
            words = new String[ws.length][];
            for(int i=0; i<ws.length; i++)
                words[i] = fromWord(ws[i].trim());
        }

        /* Calculate the length of a word in pixels */
        int lengthWord(String[] w){
            int len = 0;
            for(String s : w){
                if(s.trim().isEmpty()) continue;
                Point sp = startPosM.get(s);
                Point ep = endPosM.get(s);
                len += (ep.x-sp.x);
            }
            return len;
        }

        /* Height displacement of a word in pixels */
        int heightWord(String[] w){
            int minh = 0, maxh = 0;
            int py=0;
            for(String s : w){
                if(s.trim().isEmpty()) continue;
                Point sp = startPosM.get(s);
                Point ep = endPosM.get(s);
                py += (ep.y-sp.y);
                if(py > maxh) maxh = py;
                if(py < minh) minh = py;
            }
            int avg = (minh + maxh) / 2;
            return avg;
        }

        /* Paint the shorthand glyphs */
        public void paint(Graphics g){
            super.paint(g);
            
            int px = 50, py = 90;
            int lines = 1;

            for(String[] word : words){
                
                int lword = lengthWord(word);
                if(getWidth()-px < lword){
                    // Not big enough, wrap around
                    lines++;
                    px = 50;
                    py = lines*90;
                }

                py -= heightWord(word);
                for(String s : word){
                    if(s.trim().isEmpty()) continue;
                    BufferedImage im = imageM.get(s);
                    Point sp = startPosM.get(s);
                    Point ep = endPosM.get(s);
                    g.drawImage(im,px-sp.x,py-sp.y,this);
                    px += (ep.x-sp.x);
                    py += (ep.y-sp.y);
                }

                px += 60;
                py = lines*90;
            }
        }
    }
}
