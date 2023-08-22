import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
    
    private Image apple;
    private Image dot;
    private Image head;
    private int dots;

    private final int alldots=2325;
    private final int dotsize=10;
    private final int Random_pos = 29;
     private int apple_x;
     private int apple_y;

    private final int x[]= new  int[alldots];
    private final int y[]= new  int[alldots];

    private Timer timer;
    private boolean leftdirec= false;
    private boolean rightdirec= true;
    private boolean updirec= false;
    private boolean downdirec= false;
   
    private boolean inGame = true;

    JButton retryButton;

    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.blue);
        setPreferredSize(new Dimension(620, 375));;
        setFocusable(true);
        
        loadImages();
        initgame();
    }
    
    public void loadImages(){
        // ImageIcon i1=new ImageIcon( ClassLoader.getSystemResource("icons/apple.png"));
        ImageIcon i1= new ImageIcon("./icons/apple.png");
        apple = i1.getImage();

        // ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        ImageIcon i2= new ImageIcon("./icons/dot.png");
        dot = i2.getImage();

         
        // ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        ImageIcon i3= new ImageIcon("./icons/head.png");
        head = i3.getImage();
    }

    public void initgame(){
        dots=3;

        for(int i=0; i< dots; i++){
            y[i]=50;
            x[i]=50 - i*dotsize;
            
           

        }
        locateApple();

         timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple(){
        int r = (int)(Math.random()* Random_pos);
        apple_x=  r * dotsize;
         r = (int)(Math.random()* Random_pos);
        apple_y = r * dotsize;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){
        if(inGame){
        g.drawImage(apple, apple_x, apple_y, this);
        for(int i=0;i< dots ; i++){
            if(i== 0){
                g.drawImage(head, x[i], y[i], this);
            }
            else{
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
    else{
        gameOver(g);
    }
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (620 - metrices.stringWidth(msg)) / 2, 375/2);
        
        retryButton = new JButton("Try Again! ");
        retryButton.setFont(new Font("Lexenda Deca", Font.PLAIN, 14));
        retryButton.setBounds((620 - 100)/2 - 48, (375/ 2)+ 20 , 200, 50);
        retryButton.addActionListener(e -> restart());
        this.add(retryButton);
    }

    public void restart() {
        inGame = true;
        // this.remove(new Button());
        new game();
    }
 

    public void move(){
        for( int i= dots; i> 0;i--){
           x[i]=x[i-1];
           y[i]=y[i-1]; 
        }

        if(leftdirec){
            x[0]-=dotsize;
        }
        if(rightdirec){
            x[0]+=dotsize;
        } 
        if(updirec){
            y[0]-=dotsize;
        }
        if(downdirec){
            y[0]+=dotsize;
        }

        
    } 

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    public void checkCollision() {
        for(int i = dots; i > 0; i--) {
            if (( i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        
        if (y[0] >= 375) {
            inGame = false;
        }
        if (x[0] >= 620) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent ae){
        
        if(inGame){
        checkApple();
        checkCollision();
        move();
        }
        // if (e.getSource() == retryButton) {    
        //     this.remove(game);
        //     game = new Board();
        //     this.add(game);
        // }
        repaint();
    }

     public class TAdapter extends KeyAdapter {
        // @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT && (!rightdirec)) {
                leftdirec = true;
                updirec = false;
                downdirec = false;
            }
            
            if (key == KeyEvent.VK_RIGHT && (!leftdirec)) {
                rightdirec = true;
                updirec = false;
                downdirec = false;
            }
            
            if (key == KeyEvent.VK_UP && (!downdirec)) {
                updirec = true;
                leftdirec = false;
                rightdirec = false;
            }
            
            if (key == KeyEvent.VK_DOWN && (!updirec)) {
                downdirec = true;
                leftdirec = false;
                rightdirec = false;
            }
        }
    }

    
}
