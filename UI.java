import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// This class takes care of all the graphics to display a certain state
public class UI extends JPanel {
    private JFrame window;
    private int[][] state;
    private int size;

    private PrintWriter writer;

    // Constructor: sets everything up
    public UI(int y, int x, int _size) throws FileNotFoundException, UnsupportedEncodingException {
        size = _size;
        setPreferredSize(new Dimension(x * size, y * size));

        writer = new PrintWriter("debug.tet", "UTF-8");

        window = new JFrame("Pentomino");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //window.setLocationRelativeTo(null);
        window.add(this);
        window.pack();
        window.setVisible(true);

        state = new int[y][x];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = -1;
            }
        }
    }

    public void add(KeyListener keyListener) {
        JPanel panel = new JPanel();
        window.getContentPane().add(panel);
        panel.addKeyListener(keyListener);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    // Paint function, called by the system if required for a new frame, uses the state stored by the UI class
    public void paintComponent(Graphics g) {
        Graphics2D localGraphics2D = (Graphics2D) g;

        localGraphics2D.setColor(Color.LIGHT_GRAY);
        localGraphics2D.fill(getVisibleRect());

        // draw lines
        localGraphics2D.setColor(Color.GRAY);
        for (int i = 0; i <= state.length; i++) {
            localGraphics2D.drawLine(0, i * size, state[0].length * size, i * size);
        }
        for (int i = 0; i <= state[0].length; i++) {
            localGraphics2D.drawLine(i * size, 0, i * size, state.length * size);
        }

        // draw blocks
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                localGraphics2D.setColor(GetColorOfID(state[i][j]));
                localGraphics2D.fill(new Rectangle2D.Double(j * size + 1, i * size + 1, size - 1, size - 1));
            }
        }
    }

    // Decodes the ID of a pentomino into a color
    private Color GetColorOfID(int i) {
        if(i==0) {return Color.BLUE;}
        else if(i==1) {return Color.ORANGE;}
        else if(i==2) {return Color.CYAN;}
        else if(i==3) {return Color.GREEN;}
        else if(i==4) {return Color.MAGENTA;}
        else if(i==5) {return Color.PINK;}
        else if(i==6) {return Color.RED;}
        else if(i==7) {return Color.YELLOW;}
        else if(i==8) {return  new Color(  0,   0,   0);}
        else if(i==9) {return  new Color(  0,   0, 100);}
        else if(i==10) {return new Color(100,   0,   0);}
        else if(i==11) {return new Color(  0, 100,   0);}
        else if(i==12) {return new Color(153, 153,   0);}
        else if(i==13) {return new Color(102, 178, 255);}
        else if(i==14) {return new Color(255, 153, 153);}
        else if(i==15) {return new Color(178, 102, 255);}
        else if(i==16) {return new Color(102, 255, 102);}
        else if(i==17) {return new Color(153,   0,   0);}
        else {return Color.LIGHT_GRAY;}
    }

    // This function should be called to update the displayed state (Makes a copy)
    public void setState(int[][] _state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = _state[i][j];
            }
        }

        for (int i = 0; i < _state.length; i++) {
            for (int j = 0; j < _state[i].length; j++) {
                if (state[i][j] == -1)
                    writer.print("-1 ");
                else if (state[i][j] < 10)
                    writer.print(" " + state[i][j] + " ");
                else
                    writer.print(state[i][j] + " ");
            }
            writer.println();
        }
        writer.println();

        // Tells the system a frame update is required
        repaint();
    }
    
    public void closeWindow() {
        writer.close();
    	window.setVisible(false);
    	window.dispose();
    }
}