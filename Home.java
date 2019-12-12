/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.lang.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */

    private Rank rank;
    public String currentPlayer = "";
    public int current_player_highest_score = 0;

    /**
     * Read a file and return a representation of the lines
     * @param filename
     * @return String Return a representation of the lines
     * @throws FileNotFoundException
     */
    public String[] readFile(String filename) throws FileNotFoundException {
        
        ArrayList<String> lines = new ArrayList<String>();
        try {
            // Load the text file.
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null && !line.trim().equals("")) {
                lines.add(line);
            }
        } catch (IOException e) {
            // We fucked up.
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * Read a file retrieve the score for each person 
     * @param fileData
     * @return Rank the current rank 
     */
    public void initialiseRanks(String[] fileData) {
        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        for (String line : fileData) {
            String[] pieces = line.split("%");
            if (pieces.length != 2) {
                // There is a problem with this line, so ignore it.
                continue;
            }
            String gamertag = pieces[0];
            String score = pieces[1];
            int scoreCast;
            try {
                scoreCast = Integer.parseInt(score);
            } catch(ClassCastException e) {
                // Score is not a number, ignore this line.
                continue;
            }
            tags.add(gamertag);
            scores.add(scoreCast);
        }
        rank = new Rank(tags, scores);
    }

    public enum FrontState {
        LOGIN,
        NEW_USER,
        MENU,
        PLAY,
        BOT,
        QUIT
    }

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

    FrontState state;

    public ScheduledExecutorService ses;
    public ScheduledFuture<?> runningIteration;
    public ScheduledFuture<?> runningTimerIteration;

     /**
     * Initialization ans and customization of the jFrame  
     */
    public Home() {
        initComponents();
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("res/logo.png")).getImage());

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 220, 720);
        jPanel1.setVisible(false);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(220, 0, 780, 720);
        jPanel3.setVisible(false);

        getContentPane().add(jPanel6);
        jPanel6.setBounds(220, 0, 780, 720);
        jPanel6.setVisible(false);

        getContentPane().add(jPanel9);
        jPanel9.setBounds(0, 0, 780, 720);
        jPanel9.setVisible(true);

        jLabel2.setVisible(false);
        jLabel3.setVisible(false);

        getContentPane().add(jPanel12);
        jPanel12.setBounds(220, 0, 780, 720);
        jPanel12.setVisible(false);

        jButton4.setVisible(false);

        state = FrontState.LOGIN;

        try {
            initialiseRanks(readFile("Score.txt"));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } 
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jTextField1.getText();

                for (int i = 0; i < text.length(); i++) {
                    char cur = text.charAt(i);
                    if ((cur < 'a' || cur > 'z') && (cur < 'A' || cur > 'Z') && (cur < '0' || cur > '9') && cur != '_') {
                        newGamerTag();
                        return;
                    }
                }

                switch (state) {
                    case LOGIN:
                    int score = rank.getScore(text);
                    if (score == -1) {
                        wrongGamerTag();
                        return;
                    }
                    currentPlayer = text;
                    current_player_highest_score = score;
                    jLabel15.setText(currentPlayer);
                    jLabel14.setText("highest score " + current_player_highest_score);
                    enterApp();
                    break;

                    case NEW_USER:
                    score = rank.getScore(text);
                    if (score != -1) { 
                        userExists();
                        return;
                    }
                    try {
                        rank.addUser(text);
                    } catch(FileNotFoundException exc) {
                        exc.printStackTrace();
                        System.exit(0);
                    } 
                    currentPlayer = text;
                    current_player_highest_score = 0;
                    jLabel15.setText(currentPlayer);
                    jLabel14.setText("highest score " + current_player_highest_score);
                    enterApp();
                    break;
                }
            }
        });

        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton8.setVisible(false);
                newGamerTag();
                state = FrontState.NEW_USER;
            }
        });

        jPanel9.requestFocusInWindow();
    }

     /**
     * Displays the message for the user
     */
    public void wrongGamerTag() {
        jLabel2.setVisible(true);
        jLabel2.setText("Gamertag not found. Please type it again or create a new one.");
    }

     /**
     * Displays the message for the user
     */
    public void userExists() {
        jLabel2.setVisible(true);
        jLabel2.setText("Gamertag already exists.");
    }

     /**
     * Moving to creating a new account 
     */
    public void newGamerTag() {
        jLabel3.setVisible(true);
        jLabel2.setVisible(false);
    }

     /**
     * Entering the app 
     */
    public void enterApp() {
        state = FrontState.MENU;
        setPreferredSize(new Dimension(1000, 765));
        pack();

        jPanel1.setVisible(true);
        jPanel3.setVisible(false);
        jPanel6.setVisible(false);
        jPanel9.setVisible(false);
        jPanel12.setVisible(true);

        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goInMenu();
            }
        });
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (state == FrontState.PLAY)
                    ses.shutdown();
                enterGame();
            }
        });
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enterBot();
            }
        });
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        jPanel1.requestFocusInWindow();
        jPanel12.requestFocusInWindow();
    }

    public Pentomino currentObj;
    public Pentomino nxtObj;

    public KeyListener myKeyListener = new KeyListener() {
        public void keyTyped(KeyEvent event) {
    
        }
        public void keyPressed(KeyEvent event) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    switch (event.getKeyCode()) {
                        case KeyEvent.VK_LEFT:  currentObj.moveLeft();    jPanel4.setState(Field.getUsed()); break;
                        case KeyEvent.VK_RIGHT: currentObj.moveRight();   jPanel4.setState(Field.getUsed()); break;
                        case KeyEvent.VK_DOWN:  currentObj.moveDown();    jPanel4.setState(Field.getUsed()); break;
                        case KeyEvent.VK_SPACE: currentObj.drop();        jPanel4.setState(Field.getUsed()); break;
                        case KeyEvent.VK_Q:     currentObj.rotateLeft();  jPanel4.setState(Field.getUsed()); break;
                        case KeyEvent.VK_E:     currentObj.rotateRight(); jPanel4.setState(Field.getUsed()); break;
                    }
                }       
            });            
        }
        public void keyReleased(KeyEvent event) {

        } 
    };
    
    public Runnable pushGameIteration = new Runnable() {
        public void run() {
            EventQueue.invokeLater(gameIteration);
        }
    };

    public Runnable gameIteration = new Runnable() {
        public void run() {
            boolean result = currentObj.moveDown();

            if (result)
                jPanel4.setState(Field.getUsed());
            else {
                if (!currentObj.allInside()) {
                    current_player_highest_score = Math.max(Field.getScore(), current_player_highest_score);
                    jLabel14.setText("highest score " + current_player_highest_score);
                    try {
                        rank.addScore(currentPlayer, current_player_highest_score);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    ses.shutdown();
                    return;
                }
                else {
                    Field.updateScore();

                    label2.setText(Field.getScore() / 100 + " lines");
                    label3.setText(Field.getScore() + " score");
                    currentObj = nxtObj;
                    nxtObj = new Pentomino();
                    jPanel5.repaint();
                }
            }
        }
    };

    public int currentTime;
    public Runnable timerIteration = new Runnable() {
        public void run() {
            currentTime++;
            label1.setText(currentTime / 60 + ":" + (currentTime % 60) / 10 + (currentTime % 60) % 10);
            label4.setText(currentTime / 60 + ":" + (currentTime % 60) / 10 + (currentTime % 60) % 10);
        }
    };

     /**
     * Starting a new game 
     */
    public void enterGame() {
        if (state == FrontState.PLAY || state == FrontState.BOT)
            try {
                runningIteration.cancel(true);
                runningTimerIteration.cancel(true);
                ses.shutdown();
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        jPanel4.removeKeyListener(myKeyListener);
        state = FrontState.PLAY;

        jPanel1.setVisible(true);
        jPanel3.setVisible(true);
        jPanel6.setVisible(false);
        jPanel9.setVisible(false);
        jPanel12.setVisible(false); 

        Field.init();
        if (!PentominoBuilder.init()) {
            System.exit(0);
        }
        jPanel4.setState(Field.getUsed());
        RandomEngine.init();

        currentObj = new Pentomino();
        nxtObj = new Pentomino();
        jPanel5.repaint();
        jPanel3.addKeyListener(myKeyListener);

        jPanel1.requestFocusInWindow();
        jPanel3.requestFocusInWindow();

        currentTime = 0;
        ses = Executors.newScheduledThreadPool(2);
        runningIteration = ses.scheduleAtFixedRate(pushGameIteration, 0, 500, TimeUnit.MILLISECONDS);
        runningTimerIteration = ses.scheduleAtFixedRate(timerIteration, 0, 1000, TimeUnit.MILLISECONDS);
    }

     /**
     * Entering the bot
     */
    public void enterBot() {
        if (state == FrontState.PLAY || state == FrontState.BOT)
            try {
                runningIteration.cancel(true);
                runningTimerIteration.cancel(true);
                ses.shutdown();
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        jPanel3.removeKeyListener(myKeyListener);
        state = FrontState.BOT;
        jPanel1.setVisible(true);
        jPanel3.setVisible(false);
        jPanel6.setVisible(true);
        jPanel9.setVisible(false);
        jPanel12.setVisible(false);

        if (!PentominoBuilder.init()) {
            System.exit(0);
        }

        jPanel1.requestFocusInWindow();
        jPanel7.requestFocusInWindow();

        PentominoBuilder.init();
        RandomEngine.init();
        //PentominoBuilder.print();

        Vec obtainedVec = new Vec(new double[]{-0.3141280779824179, 0.9377892613492809, 0.0841010847339205, -0.12166289273077524}); 
        playGameAnimated(obtainedVec);
        //playGame(obtainedVec);
    }

    Function f;
    ArrayList<int[]> positions;

    public Runnable botIteration = new Runnable() {
        public void run() {
            if (positions.size() == 0)
                try {
                    ses.shutdown();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            int id = getBestPosition(currentObj.getPentID(), positions, f);
            int[] position = positions.get(id);
            Pentomino current = new Pentomino(position[0] - 5, position[1] - 5, currentObj.getPentID(), position[2]);
            Field.addPentomino(current);
            jPanel7.setState(Field.getUsed());

            try {
                Thread.sleep(2000);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            Field.updateScore();
            label5.setText(Field.getScore() / 100 + " lines");
            label6.setText(Field.getScore() + " score");
            jPanel7.setState(Field.getUsed());

            PositionSearch.init();
            currentObj = nxtObj;
            nxtObj = new Pentomino();
            jPanel8.repaint();
            positions = PositionSearch.search(currentObj);
        }
    };

    public Runnable botIterationAnimated = new Runnable() {
        public void run() {
            if (positions.size() == 0)
                try {
                    ses.shutdown();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            int id = getBestPosition(currentObj.getPentID(), positions, f);
            PositionSearch.buildBestPath(currentObj, positions.get(id), jPanel7);

            boolean result = Field.updateScore();
            if (result) {
                try {
                    Thread.sleep(2000);
                }
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                label5.setText(Field.getScore() / 100 + " lines");
                label6.setText(Field.getScore() + " score");
                jPanel7.setState(Field.getUsed());
            }

            PositionSearch.init();
            currentObj = nxtObj;
            nxtObj = new Pentomino();
            jPanel8.repaint();
            positions = PositionSearch.search(currentObj);
        }
    };

     /**
     * Bot is starting playing the game without animation 
     */
    public void playGame(Vec p) {
        Field.init();
        
        jPanel7.setState(Field.getUsed());
        
        f = new Function(p);

        currentObj = new Pentomino();
        nxtObj = new Pentomino();
        jPanel8.repaint();
        PositionSearch.init();
        positions = PositionSearch.search(currentObj);

        currentTime = 0;
        ses = Executors.newScheduledThreadPool(2);
        runningIteration = ses.scheduleWithFixedDelay(botIteration, 0, 2000, TimeUnit.MILLISECONDS);
        runningTimerIteration = ses.scheduleAtFixedRate(timerIteration, 0, 1000, TimeUnit.MILLISECONDS);
    }


     /**
     * Bot is starting playing the game with animation 
     */
    public void playGameAnimated(Vec p) {
        Field.init();
        jPanel7.setState(Field.getUsed());
        
        f = new Function(p);

        currentObj = new Pentomino();
        nxtObj = new Pentomino();
        jPanel8.repaint();
        PositionSearch.init();
        positions = PositionSearch.search(currentObj);

        currentTime = 0;
        ses = Executors.newScheduledThreadPool(2);
        runningIteration = ses.scheduleWithFixedDelay(botIterationAnimated, 0, 500, TimeUnit.MILLISECONDS);
        runningTimerIteration = ses.scheduleAtFixedRate(timerIteration, 0, 1000, TimeUnit.MILLISECONDS);
    }


     /**
     * Getting the best position out of all possible ones
     * @param pentID the id of pentomino we are working with
     * @param positions the list of all possible positions
     * @param f assessing function
     */
    public int getBestPosition(int pentID, ArrayList<int[]> positions, Function f) {
        double mx = 0;
        int id = 0; 
        for (int i = 0; i < positions.size(); i++) {
            int[] currentPosition = positions.get(i);
            Pentomino current = new Pentomino(currentPosition[0] - 5, currentPosition[1] - 5, pentID, currentPosition[2]);

            Field.addPentomino(current);
            double value = f.calc();
            Field.deletePentomino(current);

            if (i == 0 || mx < value) {
                mx = value;
                id = i;
            }
        }
        return id;
    }


     /**
     * Entering the menu
     */
    public void goInMenu() {
        if (state == FrontState.PLAY || state == FrontState.BOT)
            try {
                runningIteration.cancel(true);
                runningTimerIteration.cancel(true);
                ses.shutdown();
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        state = FrontState.MENU;
        jPanel1.setVisible(true);
        jPanel3.setVisible(false);
        jPanel6.setVisible(false);
        jPanel9.setVisible(false);
        jPanel12.setVisible(true);
    }

     /**
     * The main customization method made with NetBeans IDE 8.2 without direct coding 
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        try {
            jPanel4 = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        jPanel5 = new javax.swing.JPanel() {
            public void paintComponent(Graphics g) {
                try {
                Graphics2D g2 = (Graphics2D)g;

                int sz = 16;
                int length = nxtObj.getLength();
                int width = length * sz;
                int height = width;

                int shift = (150 - width) / 2; 
                g2.setColor(Color.LIGHT_GRAY);
                g2.fill(getVisibleRect());
                g2.setColor(Color.GRAY);

                for (int i = 0; i <= length; i++) {
                    g2.drawLine(shift, shift + i * sz, shift + length * sz, shift + i * sz);
                }

                for (int i = 0; i <= length; i++) {
                    g2.drawLine(shift + i * sz, shift, shift + i * sz, shift + length * sz);
                }

                int[][] state = nxtObj.getBits();
                for (int i = 0; i < state.length; i++) {
                    for (int j = 0; j < state[0].length; j++) {
                        if (state[i][j] == 1) {
                            g2.setColor(GetColorOfID(nxtObj.getPentID()));
                            g2.fill(new Rectangle2D.Double(shift + j * sz + 1, shift + i * sz + 1, sz - 1, sz - 1));
                        } else {
                            g2.setColor(GetColorOfID(-1));
                            g2.fill(new Rectangle2D.Double(shift + j * sz + 1, shift + i * sz + 1, sz - 1, sz - 1));
                        }
                    }
                }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };

        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        jPanel6 = new javax.swing.JPanel();
        try {
            jPanel7 = new UI(Field.getHeight(), Field.getWidth(), Field.getCellSize());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        jPanel8 = new javax.swing.JPanel() {
            public void paintComponent(Graphics g) {
                try {
                Graphics2D g2 = (Graphics2D)g;

                int sz = 16;
                int length = nxtObj.getLength();
                int width = length * sz;
                int height = width;

                int shift = (150 - width) / 2; 
                g2.setColor(Color.LIGHT_GRAY);
                g2.fill(getVisibleRect());
                g2.setColor(Color.GRAY);

                for (int i = 0; i <= length; i++) {
                    g2.drawLine(shift, shift + i * sz, shift + length * sz, shift + i * sz);
                }

                for (int i = 0; i <= length; i++) {
                    g2.drawLine(shift + i * sz, shift, shift + i * sz, shift + length * sz);
                }

                int[][] state = nxtObj.getBits();
                for (int i = 0; i < state.length; i++) {
                    for (int j = 0; j < state[0].length; j++) {
                        if (state[i][j] == 1) {
                            g2.setColor(GetColorOfID(nxtObj.getPentID()));
                            g2.fill(new Rectangle2D.Double(shift + j * sz + 1, shift + i * sz + 1, sz - 1, sz - 1));
                        } else {
                            g2.setColor(GetColorOfID(-1));
                            g2.fill(new Rectangle2D.Double(shift + j * sz + 1, shift + i * sz + 1, sz - 1, sz - 1));
                        }
                    }
                }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }  
        };
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(780, 765));
        pack();
        setLocationRelativeTo(null);
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(39, 39, 39));
        jPanel1.setPreferredSize(new java.awt.Dimension(220, 720));

        jPanel2.setBackground(new java.awt.Color(39, 39, 39));
        jPanel2.setForeground(new java.awt.Color(39, 39, 39));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 150));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);

        jButton1.setBackground(new java.awt.Color(39, 39, 39));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton1.setText("Home");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton1.setMinimumSize(new java.awt.Dimension(220, 65));
        jButton1.setPreferredSize(new java.awt.Dimension(220, 65));
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel1.add(jButton1);

        jButton2.setBackground(new java.awt.Color(39, 39, 39));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton2.setText("Play");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton2.setMinimumSize(new java.awt.Dimension(220, 65));
        jButton2.setPreferredSize(new java.awt.Dimension(220, 65));
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel1.add(jButton2);

        jButton3.setBackground(new java.awt.Color(39, 39, 39));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton3.setText("Bot");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton3.setMinimumSize(new java.awt.Dimension(220, 65));
        jButton3.setPreferredSize(new java.awt.Dimension(220, 65));
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel1.add(jButton3);

        jButton4.setBackground(new java.awt.Color(39, 39, 39));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton4.setText("Settings");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton4.setMinimumSize(new java.awt.Dimension(220, 65));
        jButton4.setPreferredSize(new java.awt.Dimension(220, 65));
        jButton4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel1.add(jButton4);

        jButton6.setBackground(new java.awt.Color(39, 39, 39));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton6.setText("Help");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton6.setMinimumSize(new java.awt.Dimension(220, 65));
        jButton6.setPreferredSize(new java.awt.Dimension(220, 65));
        jButton6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton6.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel1.add(jButton6);

        jButton5.setBackground(new java.awt.Color(39, 39, 39));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton5.setText("Quit");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton5.setMinimumSize(new java.awt.Dimension(220, 65));
        jButton5.setPreferredSize(new java.awt.Dimension(220, 65));
        jButton5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel1.add(jButton5);

        //jPanel4.setBackground(new java.awt.Color(39, 39, 39));
        //jPanel4.setForeground(new java.awt.Color(39, 39, 39));
        jPanel4.setBounds(150, 40, 96, 640);

        jPanel5.setBackground(new java.awt.Color(39, 39, 39));
        jPanel5.setForeground(new java.awt.Color(39, 39, 39));        
        jPanel5.setBounds(460, 40, 150, 150);

        jPanel3.add(jPanel4);
        jPanel3.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(240, 240, 240));
        jPanel3.setLayout(null);

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setBackground(new java.awt.Color(39, 39, 39));
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("0:00");

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setBackground(new java.awt.Color(39, 39, 39));
        label2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("0 lines");

        label3.setAlignment(java.awt.Label.CENTER);
        label3.setBackground(new java.awt.Color(39, 39, 39));
        label3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("0 score");

        jPanel3.add(label1);
        jPanel3.add(label2);
        jPanel3.add(label3);

        label1.setBounds(460, 230, 150, 35);
        label2.setBounds(460, 305, 150, 35);
        label3.setBounds(460, 380, 150, 35);

        jPanel7.setBackground(new java.awt.Color(39, 39, 39));
        jPanel7.setForeground(new java.awt.Color(39, 39, 39));
        jPanel7.setBounds(150, 40, 96, 640);

        jPanel8.setBackground(new java.awt.Color(39, 39, 39));
        jPanel8.setForeground(new java.awt.Color(39, 39, 39));
        jPanel8.setBounds(460, 40, 150, 150);

        jPanel6.add(jPanel7);
        jPanel6.add(jPanel8);

        jPanel6.setBackground(new java.awt.Color(240, 240, 240));
        jPanel6.setLayout(null);

        label4.setAlignment(java.awt.Label.CENTER);
        label4.setBackground(new java.awt.Color(39, 39, 39));
        label4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setText("0:00");

        label5.setAlignment(java.awt.Label.CENTER);
        label5.setBackground(new java.awt.Color(39, 39, 39));
        label5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label5.setForeground(new java.awt.Color(255, 255, 255));
        label5.setText("0 lines");

        label6.setAlignment(java.awt.Label.CENTER);
        label6.setBackground(new java.awt.Color(39, 39, 39));
        label6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label6.setForeground(new java.awt.Color(255, 255, 255));
        label6.setText("0 score");

        jPanel6.add(label4);
        jPanel6.add(label5);
        jPanel6.add(label6);

        label4.setBounds(460, 230, 150, 35);
        label5.setBounds(460, 305, 150, 35);
        label6.setBounds(460, 380, 150, 35);



        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(500, 65));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setBackground(new java.awt.Color(39, 39, 39));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gamertag");
        jLabel1.setMaximumSize(new java.awt.Dimension(220, 70));
        jLabel1.setMinimumSize(new java.awt.Dimension(220, 70));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(120, 70));
        jPanel10.add(jLabel1);

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setToolTipText("");
        jTextField1.setPreferredSize(new java.awt.Dimension(220, 70));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel10.add(jTextField1);

        jButton7.setBackground(new java.awt.Color(39, 39, 39));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton7.setText("Enter");
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setMaximumSize(new java.awt.Dimension(220, 70));
        jButton7.setMinimumSize(new java.awt.Dimension(220, 70));
        jButton7.setPreferredSize(new java.awt.Dimension(100, 70));
        jButton7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected100.png"))); // NOI18N
        jPanel10.add(jButton7);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(200, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Gamertag not found. Please type it again or create a new one.");

        jPanel11.setLayout(new java.awt.GridBagLayout());

        jButton8.setBackground(new java.awt.Color(39, 39, 39));
        jButton8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/default.png"))); // NOI18N
        jButton8.setText("Create a new gamertag");
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setMaximumSize(new java.awt.Dimension(220, 65));
        jButton8.setMinimumSize(new java.awt.Dimension(220, 39));
        jButton8.setPreferredSize(new java.awt.Dimension(220, 39));
        jButton8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hover.png"))); // NOI18N
        jButton8.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selected.png"))); // NOI18N
        jPanel11.add(jButton8, new java.awt.GridBagConstraints());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 100, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("You can use only latin letters, numbers and underscoes in your gamertag.");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );


        
        jPanel12.setPreferredSize(new java.awt.Dimension(780, 720));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(39, 39, 39));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Gamertag");
        jLabel15.setMaximumSize(new java.awt.Dimension(41, 70));
        jLabel15.setMinimumSize(new java.awt.Dimension(41, 70));
        jLabel15.setOpaque(true);
        jLabel15.setPreferredSize(new java.awt.Dimension(41, 70));

        jPanel13.setBackground(new java.awt.Color(39, 39, 39));
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(780, 170));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jLabel14.setBackground(new java.awt.Color(39, 39, 39));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("highest score 4500");
        jPanel13.add(jLabel14, new java.awt.GridBagConstraints());

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("0 hours played");

        jLabel17.setBackground(new java.awt.Color(39, 39, 39));
        jLabel17.setForeground(new java.awt.Color(39, 39, 39));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 200), 5));

        jLabel18.setBackground(new java.awt.Color(39, 39, 39));
        jLabel18.setForeground(new java.awt.Color(39, 39, 39));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 100, 100), 5));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(jLabel16))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private UI jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private UI jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    // End of variables declaration                   
}
