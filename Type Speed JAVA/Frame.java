import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Frame extends JFrame implements ActionListener, KeyListener {
    String passage = "";
    String typedPass = "";
    String message = "";
    int typed = 0;
    double start;
    double end;
    boolean running = false;
    boolean ended = false;

    final int SCREEN_WIDTH;
    final int SCREEN_HEIGHT;
    final int DELAY = 100;

    JButton button;
    Timer timer;
    JLabel label;

    public Frame() {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN_WIDTH = 720;
        SCREEN_HEIGHT = 400;
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        button = new JButton("Start");
        button.setFont(new Font("MV Boli", Font.BOLD, 30));
        button.setForeground(Color.GREEN);
        button.setVisible(true);
        button.addActionListener(this);
        button.setFocusable(false);

        label = new JLabel();
        label.setText("Click the Start Button");
        label.setFont(new Font("MV Boli", Font.BOLD, 30));
        label.setVisible(true);
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.LIGHT_GRAY);
        this.add(button, BorderLayout.SOUTH);
        this.add(label, BorderLayout.NORTH);
        this.getContentPane().setBackground(Color.WHITE);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setResizable(false);
        this.setTitle("Typing Speed Test");
        this.revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setFont(new Font("MV Boli", Font.BOLD, 25));
        int lineHeight = g.getFontMetrics().getHeight();
        int y = 150; // Starting y position for the text
        int lineWidth = 50; // Characters per line

        for (int i = 0; i < passage.length(); i += lineWidth) {
            int endIndex = Math.min(i + lineWidth, passage.length());
            String line = passage.substring(i, endIndex);

            // Draw typed part in green
            if (i < typedPass.length()) {
                String typedLine = typedPass.substring(i, Math.min(endIndex, typedPass.length()));
                g.setColor(Color.GREEN);
                g.drawString(typedLine, 10, y);

                // Draw the rest of the line in black
                g.setColor(Color.BLACK);
                g.drawString(line.substring(typedLine.length()), 10 + g.getFontMetrics().stringWidth(typedLine), y);
            } else {
                // Draw the entire line in black if it hasn't been typed yet
                g.setColor(Color.BLACK);
                g.drawString(line, 10, y);
            }
            y += lineHeight;
        }
    }
    private String getTypingSpeed(double timeTaken) {
        // Calculate words per minute
        double wordsTyped = typedPass.length() / 5.0; // Assuming average word length is 5 characters
        double wpm = (wordsTyped / (timeTaken / 60)); // Convert time to minutes
    
        // Determine speed category
        if (wpm < 20) {
            return "Slow";
        } else if (wpm < 40) {
            return "Medium";
        } else if (wpm < 60) {
            return "Fast";
        } else {
            return "Excellent";
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        if (running) {
            char c = e.getKeyChar();
    
            // Handle backspace
            if (c == KeyEvent.VK_BACK_SPACE && typedPass.length() > 0) {
                typedPass = typedPass.substring(0, typedPass.length() - 1);
                typed--;
            } else if (typedPass.length() < passage.length() && c == passage.charAt(typedPass.length())) {
                // Only accept the correct next character
                typedPass += c;
                typed++;
            }
    
            // Check if the passage has been typed correctly
            if (typedPass.equals(passage)) {
                running = false;
                end = System.currentTimeMillis();
                double timeTaken = (end - start) / 1000.0; // Time in seconds
                
                // Calculate and display typing speed feedback
                String speedFeedback = getTypingSpeed(timeTaken);
                label.setText("Time:" + timeTaken + " Typing speed is: " + speedFeedback);
                button.setEnabled(true);
            }
    
            repaint(); // Repaint to reflect the new typed character
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            passage = getPassage();
            running = true;
            ended = false;
            typedPass = "";
            typed = 0;
            start = System.currentTimeMillis();
            button.setEnabled(false);
            label.setText("Typing Test Started!");
            repaint();
        }
    }
    public static String getPassage() {
        ArrayList<String> Passages = new ArrayList<>();
        String pas1 = "Robotics blends engineering, computer science, and AI to create machines performing tasks once requiring human intelligence. Robots now range from autonomous vehicles to medical devices.";
        String pas2 = "Politics is the process of making decisions that apply to members of a group, often within governments. It involves negotiation and implementation of policies and laws.";
        String pas3 = "The discovery of fire marked a pivotal moment in human history. Fire provided warmth, protection, and a means to cook food, enhancing survival and fostering social gatherings.";
        String pas4 = "Nikola Tesla was a pioneering inventor whose work revolutionized electricity. He developed the alternating current (AC) system, which became the standard for electrical power distribution.";
        String pas5 = "The Himalayas, the world's highest mountain range, stretch across five countries: India, Nepal, Bhutan, China, and Pakistan. They influence climate and provide water to major rivers.";
        String pas6 = "Cars revolutionized transportation in the 20th century. They provide personal mobility, enabling long-distance travel. However, they also present challenges like traffic congestion.";
        String pas7 = "Software design is the process of creating software solutions to meet specific needs. It involves defining architecture, components, and user interfaces for effective applications.";
        String pas8 = "Human intelligence encompasses the ability to learn, reason, and solve problems. It includes cognitive processes and emotional intelligence, enabling innovation and societal development.";
        String pas9 = "Future technology promises to reshape our lives. Advancements in AI will enhance automation and decision-making. Innovations in biotechnology may lead to breakthroughs in medicine.";
        String pas10 = "Subhash Chandra Bose was a prominent leader in the Indian independence movement. He advocated for complete independence through armed struggle and founded the Indian National Army.";
    
        Passages.add(pas1);
        Passages.add(pas2);
        Passages.add(pas3);
        Passages.add(pas4);
        Passages.add(pas5);
        Passages.add(pas6);
        Passages.add(pas7);
        Passages.add(pas8);
        Passages.add(pas9);
        Passages.add(pas10);
    
        Random rand = new Random();
        int place = rand.nextInt(Passages.size());
        String toReturn = Passages.get(place);
    
        // Trim to 200 characters and ensure it ends correctly
        if (toReturn.length() > 200) {
            toReturn = toReturn.substring(0, 200);
            if (toReturn.charAt(199) != ' ') {
                // Optionally trim to the last space to avoid cutting off mid-word
                int lastSpace = toReturn.lastIndexOf(' ');
                if (lastSpace > -1) {
                    toReturn = toReturn.substring(0, lastSpace);
                }
            }
            toReturn = toReturn.strip() + ".";
        }
    
        return toReturn;
    }
    
    
}
