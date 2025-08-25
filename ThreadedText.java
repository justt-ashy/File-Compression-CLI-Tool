import javax.swing.*;
public class ThreadedText implements Runnable{
    private String text;
    private JLabel field;
    Thread t;
    boolean processDone = false;

     // Constructs a new ThreadedText with the given text and JLabel
    ThreadedText(String text, JLabel field){
        this.text = text;
        this.field = field;
        t = new Thread(this);
    }

       // Animates the text in the JLabel, one character at a time
    public void run(){
        synchronized(this) {
            String str = new String();
            for(int i=0; i < text.length();i++){
                try{
                    str += text.charAt(i) + "";
                    Thread.sleep(100);
                    field.setText(str);
                }
                catch(InterruptedException e){}
            }
        }
    }
}
