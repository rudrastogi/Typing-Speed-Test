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
    public static String getPassage()
    {
        ArrayList<String> Passages=new ArrayList<String>();
        String pas1="Robotics blends engineering, computer science, and AI to create machines performing tasks once requiring human intelligence. Starting in mid-20th-century manufacturing, robots now range from autonomous vehicles to medical devices. AI integration enables learning and decision-making, transforming industries like healthcare with surgical robots and logistics with automated delivery. Exploring hazardous environments, robots gather data and perform dangerous tasks, promising a future of enhanced human-robot collaboration and improved quality of life.";
        String pas2="Politics is the process of making decisions that apply to members of a group, often within governments. It involves the negotiation and implementation of policies, laws, and regulations. Key political activities include voting, debating, and lobbying. Political systems vary worldwide, from democracies to autocracies, shaping how power is distributed and exercised. The impact of politics is profound, influencing societal structures, economic policies, and individual rights, ultimately shaping the course of nations and communities.";
        String pas3="The discovery of fire marked a pivotal moment in human history. Early humans learned to harness fire around 1.7 million years ago, transforming their way of life. Fire provided warmth, protection from predators, and a means to cook food, making it more digestible and nutritious. This discovery not only enhanced survival but also fostered social gatherings, leading to advances in communication and community building, profoundly shaping human evolution and culture.";
        String pas4="Nikola Tesla was a pioneering inventor and electrical engineer whose work revolutionized the field of electricity. Born in 1856 in modern-day Croatia, Tesla developed the alternating current (AC) system, which became the standard for electrical power distribution. His innovations include the Tesla coil, wireless communication technologies, and early contributions to radio. Despite financial struggles and limited recognition in his lifetime, Tesla's visionary ideas and inventions laid the foundation for modern electrical engineering and technology.";
        String pas5="The Himalayas, the world's highest mountain range, stretch across five countries: India, Nepal, Bhutan, China, and Pakistan. Home to Mount Everest, the tallest peak on Earth, the Himalayas span over 2,400 kilometers. These majestic mountains influence regional climate, providing water to major rivers like the Ganges and the Brahmaputra. Rich in biodiversity, the Himalayas are a cultural and spiritual hub, housing ancient monasteries and diverse communities, and playing a vital role in the ecological and cultural fabric of South Asia.";
        String pas6="Cars, integral to modern life, revolutionized transportation in the 20th century. Invented in the late 19th century, they evolved from steam and gasoline engines to today's electric and hybrid models. Cars provide personal mobility, enabling people to travel long distances quickly and conveniently. They have shaped urban planning, economies, and social dynamics. While offering immense benefits, cars also present challenges such as traffic congestion and environmental impact, driving the push for sustainable and innovative automotive technologies.";
        String pas7="Software design is the process of conceptualizing and creating software solutions to meet specific needs or solve problems. It involves defining architecture, components, interfaces, and data for a system to ensure functionality, scalability, and maintainability. Key aspects include requirements analysis, system modeling, and user interface design. Effective software design balances technical constraints with user needs, laying the foundation for robust, efficient, and user-friendly applications that drive innovation across various industries.";
        String pas8="Human intelligence encompasses the ability to learn, reason, solve problems, and adapt to new situations. It involves cognitive processes such as memory, perception, and language. Human intelligence is not limited to academic skills but also includes emotional intelligence, creativity, and social understanding. This multifaceted capability enables humans to innovate, build complex societies, and navigate the world. The study of human intelligence spans psychology, neuroscience, and artificial intelligence, seeking to understand and replicate these remarkable mental abilities.";
        String pas9="Future technology promises to reshape the way we live, work, and interact. Advancements in artificial intelligence will enhance automation, allowing machines to perform complex tasks and make decisions. Innovations in biotechnology may lead to breakthroughs in medicine, potentially eradicating diseases and prolonging life. Quantum computing holds the potential to solve problems currently deemed impossible. Moreover, the integration of the Internet of Things (IoT) will create smarter homes and cities. As these technologies converge, they will transform industries, improve quality of life, and redefine human experiences.";
        String pas10="Subhash Chandra Bose was a prominent leader in the Indian independence movement against British rule. Born in 1897, he advocated for complete independence through armed struggle, differing from the non-violent approach of Gandhi. Bose founded the Indian National Army (INA) to fight for India's freedom and sought international support, particularly from Axis powers during World War II. His famous slogan, \"Give me blood, and I shall give you freedom,\" inspired many. Bose's legacy as a nationalist leader continues to resonate in India’s history and independence narrative.";
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
        Random rand= new Random();
        int place=(rand.nextInt(10));
        String toReturn=Passages.get(place).substring(0,200);
        if(toReturn.charAt(199)==32)
        {
            toReturn=toReturn.strip();
            toReturn=toReturn+".";
        }
        return(toReturn);
    }
    
}
