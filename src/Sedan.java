import java.awt.*;

public class Sedan extends Car{
    Sedan(Road road){
        super(road);
        width = 30;
        height = 10;
    }
    public void paintMeHorizontal(Graphics g){
        g.setColor(Color.red);
        g.fillRect(xPosition, yPosition, width, height);
    }
    public void paintMeVertical(Graphics g){
        g.setColor(Color.red);
        g.fillRect(xPosition, yPosition, width, height);
    }
}