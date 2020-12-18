import java.awt.*;

public class SUV extends Car {
    SUV(Road road){
        super(road);
        width = 36;
        height = 13;
    }
    public void paintMeHorizontal(Graphics g){
        g.setColor(Color.green);
        g.fillRect(xPosition, yPosition, width, height);
    }
    public void paintMeVertical(Graphics g){
        g.setColor(Color.green);
        g.fillRect(xPosition, yPosition, width, height);
    }
}
