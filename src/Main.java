import javax.swing.*;

class Main extends JFrame{
    public static void main(String[] args) {
        Thread thread = new Thread(new Layout()); // neuer Thread (mit Layout als Instanz) erstellt
        thread.start(); // Thread wird gestartet
    }
}