import javax.swing.*;
import java.awt.*;

public class Road extends JPanel{
    private TrafficLight light;
    private int numberOfSegments;
    private final int roadWidth = 55;
    final int roadYPosition;
    final int endRoadYPosition;
    final int roadXPosition;
    final int endRoadXPosition;
    private Color lightColor = Color.green;
    private String orientation;
    String trafficDirection;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 1200, 1200);

        for(int z = 0; z < Map.roads.size();z++){
            Road r = Map.roads.get(z);
            r.paintRoad(g);
            if(r.getOrientation().equals("Vertical")){
                for (int c = 0; c < Map.cars.size(); c++) {
                    Car car = Map.cars.get(c);
                    if(car.getRoadCarIsOn().equals(r)) {
                        car.paintMeVertical(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
            else{
                for (int c = 0; c < Map.cars.size(); c++) {
                    Car car = Map.cars.get(c);
                    if(car.getRoadCarIsOn().equals(r)) {
                        car.paintMeHorizontal(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
        }
    }

    // paints traffic light
    public void paintLight(Graphics g){
        g.setColor(lightColor);
        if(getOrientation().equals("Horizontal")) {
            if (getTrafficDirection().equals("east")) {
                g.fillRect(roadXPosition + numberOfSegments * 25 - 10, roadYPosition - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadXPosition + numberOfSegments * 25 - 10, roadYPosition - 20, 10, 20);
            } else {
                g.fillRect(roadXPosition, roadYPosition - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadXPosition, roadYPosition - 20, 10, 20);
            }
        }
        else{
            if (getTrafficDirection().equals("south")) {
                g.fillRect(roadYPosition - 20, roadXPosition + numberOfSegments * 25 - 10, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadYPosition - 20, roadXPosition + numberOfSegments * 25 - 10, 20, 10);
            }
            else{
                g.fillRect(roadYPosition - 20, roadXPosition, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadYPosition - 20, roadXPosition, 20, 10);
            }
        }
    }

    public void paintRoad(Graphics g){
        if(orientation.equals("Horizontal")) {
            g.setColor(Color.black);
            g.fillRect(roadXPosition, roadYPosition,numberOfSegments * 25, roadWidth);
            g.setColor(Color.WHITE);
            for (int j = 0; j < numberOfSegments * 25; j = j + 50) { // line being drawn
                g.fillRect(roadXPosition + j, roadYPosition + roadWidth / 2, 30, 2);
            }
        }
        else{
            g.setColor(Color.black);
            g.fillRect(roadYPosition, roadXPosition, roadWidth, numberOfSegments * 25);
            g.setColor(Color.WHITE);
            for (int j = 0; j < numberOfSegments * 25; j = j + 50) { // line being drawn
                g.fillRect( roadYPosition + roadWidth / 2, roadXPosition + j, 2,30);
            }
        }
    }

    Road(int numberOfSegments, String orientation, int xPosition, int yPosition, String direction){
        super();
        this.numberOfSegments = numberOfSegments*2;
        this.orientation = orientation;
        this.roadXPosition = xPosition;
        this.roadYPosition = yPosition;
        this.endRoadXPosition = xPosition + numberOfSegments * 50;
        this.endRoadYPosition = yPosition + numberOfSegments * 50;
        this.trafficDirection = direction;
    }
    Road(int numOfSegments, String orientation, int xPosition, int yPosition, String direction, TrafficLight light){
        super();
        this.numberOfSegments = numOfSegments*2;
        this.orientation = orientation;
        this.light = light;
        this.roadXPosition = xPosition;
        this.roadYPosition = yPosition;
        this.endRoadXPosition = xPosition + numOfSegments * 50;
        this.endRoadYPosition = yPosition + numOfSegments * 50;
        this.trafficDirection = direction;

    }
    public String getOrientation(){ return orientation;}
    public TrafficLight getTrafficLight(){
        return light;
    }
    public int getRoadLength(){
        return numberOfSegments;
    }
    public int getRoadYPosition(){
        return roadYPosition;
    }
    public int getRoadXPosition(){
        return roadXPosition;
    }
    public int getEndRoadYPosition(){
        return endRoadYPosition;
    }
    public int getEndRoadXPosition(){
        return endRoadXPosition;
    }
    public String getTrafficDirection(){ return trafficDirection; }
    public void setLightColor(Color c){
        lightColor = c;
    }

}
