import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class GamePannel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT);
    static final int DELAY=75;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=3;
    int applesEaten=0;
    int applex;
    int appley;
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;
    GamePannel() {
        random =new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(new Color(34, 34, 34));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        // Constructor code here
    }

    public void startGame() {
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running){
                g.setColor(Color.red);
                g.fillOval(applex,appley,UNIT_SIZE,UNIT_SIZE);
                for(int i=0;i<bodyParts;i++){
                    if(i==0){
                        g.setColor(Color.white);
                        g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                    }
                    else
                    {
                        g.setColor(Color.white);
                        g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);


                    }
                }
            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans MS",Font.BOLD,20));
            FontMetrics metrics=getFontMetrics(g.getFont());
                g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());

        }

        else gameOver(g);


    }
    public void newApple(){
        applex=random.nextInt((int) SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appley=random.nextInt((int) SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;

    }

    public void move() {
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;


        }

    }

    public void checkApple() {
        if((x[0]==applex)&&(y[0]==appley)){
            bodyParts++;
            applesEaten++;
            newApple();
        }


    }

    public void checkCollisions() {
        //if head collides with body
        for(int i=bodyParts;i>0;i--){
            if(x[0]==x[i]&&y[0]==y[i]){
                running=false;
            }
        }
        //if head collides with left border
        if(x[0]<0){
            running=false;
        }
        //if head collides right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //if head collides touches top border
        if(y[0]<0){
            running=false;
        }
        //if head collides bottom border
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }

    }

    public void gameOver(Graphics g) {
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Your Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Your Score: "+applesEaten))/2,g.getFont().getSize());
        //game over text
        g.setColor(Color.white);
        g.setFont(new Font("Chiller",Font.BOLD,50));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH-metrics1.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Action performed code here
        if(running) {
            move();
            checkApple();
            checkCollisions();

        }
        repaint();
    }
}

