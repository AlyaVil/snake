import javax.swing.*;

public class Main extends JFrame {
    public Main(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //при нажатии коестика прога прекращает свою работу
        setSize(320,345); //размер окна игры
        setLocation(400,400);
        setVisible(true);
        add(new Game());
    }

    public static void main (String[] args) {
        Main m = new Main(); //отсюда прогр. будет начинать работу
    }

}
