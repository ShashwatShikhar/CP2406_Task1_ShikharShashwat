import java.awt.*;
import java.util.ArrayList;

public class Car {
    private Road road; // road that the car is on
    protected int yPosition; // current position on map
    protected int xPosition; // current position on map
    protected int  height;
    protected int width;
    public void paintMeHorizontal(Graphics g){
    }
    public void paintMeVertical(Graphics g){
    }
    Car(Road road){
        this.road = road;
        yPosition = getRoadCarIsOn().roadYPosition;
        xPosition = getRoadCarIsOn().roadXPosition;
    }

    public Road getRoadCarIsOn(){
        return road;
    }

    public int getCarXPosition(){ return xPosition; }
    public void setCarXPosition(int x){
        xPosition = x;
    }
    public int getCarYPosition(){ return yPosition; }
    public void setCarYPosition(int y){
        yPosition = y;
    }
    public int getCarWidth(){return width;}

    private void setRecentRoad(Road road){
        this.road = road;
    }
    private boolean checkIfAtEndOfRoad(){
        if(getRoadCarIsOn().getTrafficDirection().equals("east") || getRoadCarIsOn().getTrafficDirection().equals("south")){
            return (xPosition+width >= getRoadCarIsOn().getEndRoadXPosition());
        }
        else if(getRoadCarIsOn().getTrafficDirection().equals("west") || getRoadCarIsOn().getTrafficDirection().equals("north")){
            return (xPosition <= road.getRoadXPosition());
        }
        else
            return true;
    }
    public boolean collision(int x, Car car){
        String direction = getRoadCarIsOn().getTrafficDirection();
        for (int i = 0; i < Map.cars.size(); i++) {
            Car c = Map.cars.get(i);
            if (c.getRoadCarIsOn() == getRoadCarIsOn() && car.getCarYPosition() == c.getCarYPosition()) {
                int otherCarXPosition = c.getCarXPosition();
                int otherCarWidth = c.getCarWidth();
                if (!car.equals(c)) { // if not checking current car
                    if (x < otherCarXPosition + otherCarWidth && //left side is left  of cars right side
                            x + otherCarWidth > otherCarXPosition && (direction.equals("east") || direction.equals("south"))) { // right side right of his left side
                        return true;
                    }
                    else if (x < otherCarXPosition + otherCarWidth * 2 - 15 && x + car.getCarWidth() > otherCarXPosition &&
                            (direction.equals("west") || direction.equals("north"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean canMoveForward(){
        String direction = getRoadCarIsOn().getTrafficDirection();
        if(xPosition+width >= getRoadCarIsOn().getRoadLength()*25-25+getRoadCarIsOn().getRoadXPosition() && (direction.equals("east") || direction.equals("south"))
                || xPosition <= getRoadCarIsOn().getRoadXPosition()+25 && ( direction.equals("west") || direction.equals("north") )) {
            if (getRoadCarIsOn().getTrafficLight() == null) {
                return true;
            }
            else {
                TrafficLight light = getRoadCarIsOn().getTrafficLight();
                return light.getColorNow().equals("Green");
            }
        }
        return true;
    }
    private int getIndexOfRoadNow(){
        return Map.roads.indexOf(road);
    }
    private Road nextRoad(){
        int otherRoadXPosition;
        int otherRoadYPosition;
        int otherRoadEndXPosition;
        int otherRoadEndYPosition;
        int roadXPositionNow;
        int roadYPositionNow;
        int roadEndXPositionNow;
        int roadEndYPositionNow;
        Road roadNow = Map.roads.get(getIndexOfRoadNow());
        Road nextRoad = Map.roads.get(0);
        ArrayList<Integer> xPositions = new ArrayList<Integer>();
        ArrayList<Integer> yPositions = new ArrayList<Integer>();
        if(roadNow.getOrientation().equals("Vertical")){
            roadXPositionNow = roadNow.getRoadYPosition();
            roadYPositionNow = roadNow.getRoadXPosition();
            roadEndXPositionNow = roadNow.getEndRoadYPosition();
            roadEndYPositionNow = roadNow.getEndRoadXPosition();
        }
        else{
            roadXPositionNow = roadNow.getRoadXPosition();
            roadYPositionNow = roadNow.getRoadYPosition();
            roadEndXPositionNow = roadNow.getEndRoadXPosition();
            roadEndYPositionNow = roadNow.getEndRoadYPosition();
        }
        for (int i = 0; i < Map.roads.size(); i++) {
            Road r = Map.roads.get(i);
            if(r != roadNow) {

                if(r.getOrientation().equals("Horizontal")){
                    otherRoadXPosition = r.getRoadXPosition();
                    otherRoadYPosition = r.getRoadYPosition();
                    otherRoadEndXPosition = r.getEndRoadXPosition();
                    otherRoadEndYPosition = r.getEndRoadYPosition();
                }
                else{
                    otherRoadXPosition = r.getRoadYPosition();
                    otherRoadYPosition = r.getRoadXPosition();
                    otherRoadEndXPosition = r.getEndRoadYPosition();
                    otherRoadEndYPosition = r.getEndRoadXPosition();
                }
                if(roadNow.getTrafficDirection().equals("east") && otherRoadXPosition > roadEndXPositionNow) {
                    xPositions.add(otherRoadXPosition);
                }
                else if(roadNow.getTrafficDirection().equals("west") && otherRoadEndXPosition < roadXPositionNow) {
                    xPositions.add(otherRoadEndXPosition);
                }
                else if(roadNow.getTrafficDirection().equals("north") && otherRoadEndYPosition < roadYPositionNow) {
                    yPositions.add(otherRoadEndYPosition);
                }
                else if(roadNow.getTrafficDirection().equals("south") && otherRoadYPosition > roadEndYPositionNow) {
                    yPositions.add(otherRoadYPosition);
                }
            }
        }
        int number;
        int number2;
        number = getCarXPosition(); //trying to find road with x position closest to this x position
        number2 = getCarYPosition(); // trying to find road with y position closest to this y position
        int index = 0;
        int index2 =0;
        int difference_1 = 10000;
        int difference_2 = 10000;
        if(roadNow.getTrafficDirection().equals("east") || roadNow.getTrafficDirection().equals("west")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_x = Math.abs(xPositions.get(j) - number);
                if (Difference_x < difference_1) { // checks if difference is getting smaller
                    index = j;
                    difference_1 = Difference_x;
                }
            }
        }
        else if(roadNow.getTrafficDirection().equals("south") || roadNow.getTrafficDirection().equals("north")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_y = Math.abs(yPositions.get(j) - number2);
                if (Difference_y < difference_2) { // checks if difference is getting smaller
                    index2 = j;
                    difference_2 = Difference_y;
                }
            }
        }
        int closestXPosition = 0;
        int closestYPosition = 0;
        if(roadNow.getTrafficDirection().equals("east") || roadNow.getTrafficDirection().equals("west")){
            closestXPosition = xPositions.get(index);
        }
        else {
            closestYPosition = yPositions.get(index2);
        }
        System.out.println(closestXPosition);

        for(int z = 0; z<Map.roads.size();z++){
            Road r = Map.roads.get(z);
            if ((r.getRoadXPosition() == closestXPosition || r.getEndRoadXPosition() == closestXPosition) && r.getOrientation().equals("Horizontal")) {
                nextRoad = r;
            }
            else if ((r.getRoadYPosition() == closestXPosition || r.getEndRoadYPosition() == closestXPosition) && r.getOrientation().equals("Vertical")){
                nextRoad = r;
            }
            if ((r.getRoadYPosition() == closestYPosition || r.getEndRoadXPosition() == closestYPosition) && r.getOrientation().equals("Horizontal")) {
                nextRoad = r;
            }
            else if ((r.getRoadXPosition() == closestYPosition || r.getEndRoadXPosition() == closestYPosition) && r.getOrientation().equals("Vertical")){
                nextRoad = r;
            }
        }
        xPositions.clear();
        yPositions.clear();
        return nextRoad;
    }


    public void move() {
        if(canMoveForward()) {
            if(road.getTrafficDirection().equals("east") || road.getTrafficDirection().equals("south")) {
                xPosition += 25;
            }
            else if(road.getTrafficDirection().equals("west") || road.getTrafficDirection().equals("north")){
                xPosition -= 25;
            }
            if (checkIfAtEndOfRoad()) {
                try {
                    Road r = nextRoad();
                    setRecentRoad(r);
                    if(r.getOrientation().equals("Horizontal") && r.getTrafficDirection().equals("east") || r.getOrientation().equals("Vertical") && r.getTrafficDirection().equals("south")) {
                        for (int x = r.getRoadXPosition(); x + getCarWidth() < r.getRoadLength()*25+ r.getEndRoadXPosition(); x = x + 30) {
                            setCarXPosition(x);
                            setCarYPosition(getRoadCarIsOn().getRoadYPosition()+5);
                            if(!collision(x, this)){
                                return;
                            }
                        }
                    }
                    else if(r.getOrientation().equals("Horizontal") && r.getTrafficDirection().equals("west") || r.getOrientation().equals("Vertical") && r.getTrafficDirection().equals("north")){
                        for (int x = r.getRoadXPosition() + r.getRoadLength()*25 - getCarWidth(); x > r.getRoadXPosition(); x = x - 30) {
                            setCarXPosition(x);
                            setCarYPosition(getRoadCarIsOn().getRoadYPosition()+5);
                            if(!collision(x, this)){
                                return;
                            }
                        }
                    }
                }
                catch (IndexOutOfBoundsException e){
                    setRecentRoad(road);
                    xPosition = road.getRoadXPosition();
                    yPosition = road.getRoadYPosition() + 5;
                }
            }
        }

    }

}