

import javax.swing.JFrame;

public class game extends JFrame {
    
    game(){
       setTitle("Snake Game");
       add(new Board());
       pack();
        setSize(620 ,375);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
   public static void main(String[] args)  throws Exception {
    new game();
   } 
}
