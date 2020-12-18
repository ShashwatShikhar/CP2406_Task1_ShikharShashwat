import java.awt.*;

public class Bus extends Car {
    Bus(Road road){
        super(road);
        width = 50;
        height = 20;
    }
    public void paintMeHorizontal(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(xPosition, yPosition, width, height);
    }
    public void paintMeVertical(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(xPosition, yPosition, width, height);
    }
}