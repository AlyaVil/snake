import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game extends JPanel { //панель, где будет происходить основное действие игры
    private final int SIZE = 320; //размер поля
    private final int DOT_SIZE = 16; //размер ячейки змейки и еды в пикселях
    private  final int ALL_DOTS = 400; //кол-во единиц может поместиться  на игр поле (320\16=20 в ширину и 20 в длину)
    private Image dot;  //под игровую ячейку
    private Image apple;
    private int appleX; //позиция яблока х
    private int appleY;
    private int[] x= new int[ALL_DOTS];//позиция змейки каждую секунду
    private int[] y= new int[ALL_DOTS];
    private int dots; //размер змейки на данный момент
    private Timer timer;
    private boolean left = false;  //поля, отвечающие за текущее напрвление змейки
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true; // еще в игре или остановились


    public Game(){
        setBackground(Color.black); //цвет игрового поля
        loadImages(); //вызываем метод
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }

    public void initGame(){ //метод, инициализирующий игру
        dots = 3;
        for (int i = 0; i< dots; i++){
            x[i] = 48 - i*DOT_SIZE; //начальная позиция х на числе 48(тк кратно 16)
            y[i] = 48;
        }
        timer = new Timer(250, this); //частота,  которой инициализируются изменения
        timer.start();
        createApple(); //метод для создания яблока
    }

    private void createApple() {
        appleX = new Random().nextInt(20)*DOT_SIZE; //тк 20 ячеек может поместиться на игровом поле
        appleY = new Random().nextInt(20)*DOT_SIZE;

    public void loadImages(){ //метод для загрузки картинок
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iia = new ImageIcon("DOT.png");
        dot = iia.getImage();
    }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i =0; i< dots; i++){ //перерисовываем змейку
                g.drawImage(dot, x[i], y[i], this);

            }
        } else {
                String str = "Game Over";
                Font f= new Font("Arial",14,Font.BOLD); //шрифт
                g.setColor(Color.white);
                g.setFont(f);
                g.drawString(str,125,SIZE/2);
            }
    }

    public void move(){
        for (int i = dots; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left){ //для головы
           x[0] -=DOT_SIZE;
        }
        if (right){
            x[0] +=DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }
    public void checkApple(){
        if(x[0]== appleX && y[0] == appleY){ //если голова змейки касается яблока
            dots++;
            createApple(); //то создается др яблоко
        }
    }

    public void checkCollisions(){
        for (int i=dots; i>0; i--){//проверка, не столкнулась ли змейка с собой
           if(i>4 && x[0] == x[i] && y[0] ==y[i]){
               inGame = false;
           }
        }
        if (x[0]>SIZE){   //не столкнулась ли змейка с краем игрового поля
            inGame = false;
        }
        if (x[0]<0){
            inGame = false;
        }
        if (y[0]>SIZE){
            inGame = false;
        }
        if (y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
       if(inGame) {
           checkApple();
           checkCollisions();
           move(); //чтобы двигать змейку
       }
       repaint(); //для изменения игрового поля
    }

    class FiledKeyListener extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && ! right){  //зажата клавиша лево и при этом змейка не двигается вправо
                left = true;
                up= false;
                down= false;
            }
            if(key == KeyEvent.VK_RIGHT && ! left) {
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && ! down) {
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && ! up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
}

