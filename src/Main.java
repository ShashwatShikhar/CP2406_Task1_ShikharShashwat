import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Main implements ActionListener, Runnable, MouseListener {
    private int x, y;
    private boolean running = false;
    private JFrame frame = new JFrame("Traffic Simulator");
    private TrafficLight light = new TrafficLight();
    Road roadStart = new Road(10, "Horizontal",0, 250, "east", light); // fixed starting road on map
    private int getX(){
        return x;
    }
    private int getY(){
        return y;
    }
    //north container
    private JLabel info = new JLabel("Click On Screen To Select x,y Position");
    private JLabel labelXPositionField = new JLabel("Road x Position");
    private JTextField xPositionField = new JTextField("0");
    private JLabel labelYPositionField = new JLabel("Road y Position");
    private JTextField yPositionField = new JTextField("0");
    private Container north = new Container();

    //south container
    private JButton startSimulator = new JButton("Start");
    private JButton exitSimulator = new JButton("Exit");
    private JButton removeRoad = new JButton("Remove Last Road");
    private Container south = new Container();

    //west container
    private Container west = new Container();
    private JButton addSedan = new JButton("Add Sedan");
    private JButton addBus = new JButton("Add Bus");
    private JButton addSUV = new JButton("Add SUV");
    private JButton addRoad = new JButton("Add Road");
    //road orientation selection
    private ButtonGroup selections = new ButtonGroup();
    private JRadioButton horizontal = new JRadioButton("Horizontal");
    private JRadioButton vertical = new JRadioButton("Vertical");
    //has traffic light selection
    private ButtonGroup selections2 = new ButtonGroup();
    private JRadioButton hasLight = new JRadioButton("Traffic Light");
    private JRadioButton noLight = new JRadioButton("No Traffic Light");
    //road length
    private JLabel label = new JLabel("Enter Road Length");
    private JTextField length = new JTextField("");
    //traffic direction
    private ButtonGroup selections3 = new ButtonGroup();
    private JRadioButton northDirection = new JRadioButton("Upwards");
    private JRadioButton southDirection = new JRadioButton("Downwards");
    private JRadioButton westDirection = new JRadioButton("Leftwards");
    private JRadioButton eastDirection = new JRadioButton("Rightwards");

    private Main(){

        Map.roads.add(roadStart);
        frame.setSize(1270,670);
        frame.setLayout(new BorderLayout());
        frame.add(roadStart, BorderLayout.CENTER);
        roadStart.addMouseListener(this);
        north.setLayout(new GridLayout(1, 5));
        north.add(info);
        north.add(labelXPositionField);
        north.add(xPositionField);
        north.add(labelYPositionField);
        north.add(yPositionField);
        frame.add(north, BorderLayout.NORTH);

        south.setLayout(new GridLayout(1, 3));
        south.add(startSimulator);
        startSimulator.addActionListener(this);
        south.add(exitSimulator);
        exitSimulator.addActionListener(this);
        south.add(removeRoad);
        removeRoad.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        west.setLayout(new GridLayout(13,1));
        west.add(addSedan);
        addSedan.addActionListener(this);
        west.add(addBus);
        addSUV.addActionListener(this);
        west.add(addSUV);
        addBus.addActionListener(this);
        west.add(addRoad);
        addRoad.addActionListener(this);
        west.add(label);
        west.add(length);
        length.addActionListener(this);

        selections.add(vertical);
        selections.add(horizontal);
        west.add(vertical);
        vertical.addActionListener(this);
        horizontal.setSelected(true);
        west.add(horizontal);
        horizontal.addActionListener(this);

        selections2.add(hasLight);
        selections2.add(noLight);
        west.add(hasLight);
        hasLight.addActionListener(this);
        west.add(noLight);
        noLight.addActionListener(this);
        noLight.setSelected(true);

        selections3.add(northDirection);
        selections3.add(southDirection);
        selections3.add(eastDirection);
        selections3.add(westDirection);
        west.add(northDirection);
        northDirection.addActionListener(this);
        northDirection.setEnabled(false);
        west.add(southDirection);
        southDirection.addActionListener(this);
        southDirection.setEnabled(false);
        west.add(eastDirection);
        eastDirection.addActionListener(this);
        eastDirection.setSelected(true);
        west.add(westDirection);
        westDirection.addActionListener(this);

        frame.add(west, BorderLayout.WEST);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Map.trafficLights.add(light);
        frame.repaint();

    }

    public static void main(String[] args){
        new Main();
        Map map = new Map();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(horizontal.isSelected()){
            northDirection.setEnabled(false);
            southDirection.setEnabled(false);
            eastDirection.setEnabled(true);
            westDirection.setEnabled(true);
        }
        else if(vertical.isSelected()){
            eastDirection.setEnabled(false);
            westDirection.setEnabled(false);
            northDirection.setEnabled(true);
            southDirection.setEnabled(true);
        }
        if(source == startSimulator){
            if(!running) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }
        if(source == removeRoad){
            if(Map.roads.size()>1) {
                Map.roads.remove(Map.roads.size() - 1);
                frame.repaint();
            }
        }
        if(source == addBus){
            Bus bus = new Bus(roadStart);
            Map.cars.add(bus);
            for (int x = roadStart.roadXPosition; x < bus.getRoadCarIsOn().getRoadLength()*50; x = x + 30) {
                bus.setCarXPosition(x);
                bus.setCarYPosition(bus.getRoadCarIsOn().getRoadYPosition()+5);
                if(!bus.collision(x, bus)){
                    frame.repaint();
                    return;
                }
            }
        }
        if(source == addSedan){
            Sedan sedan = new Sedan(roadStart);
            Map.cars.add(sedan);
            sedan.setCarYPosition(sedan.getRoadCarIsOn().getRoadYPosition()+5);
            for (int x = roadStart.roadXPosition; x < sedan.getRoadCarIsOn().getRoadLength()*50; x = x + 30) {
                sedan.setCarXPosition(x);
                if(!sedan.collision(x, sedan)){
                    frame.repaint();
                    return;
                }

            }
        }
        if(source == addSUV){
            SUV suv = new SUV(roadStart);
            Map.cars.add(suv);
            for (int x = roadStart.roadXPosition; x < suv.getRoadCarIsOn().getRoadLength()*50; x = x + 30) {
                suv.setCarXPosition(x);
                suv.setCarYPosition(suv.getRoadCarIsOn().getRoadYPosition()+5);
                if(!suv.collision(x, suv)){
                    frame.repaint();
                    return;
                }
            }
        }
        if(source == addRoad){
            int roadLength = 5;
            String orientation = "Horizontal";
            String direction = "east";
            int xPosition = 0;
            int yPosition = 0;
            Boolean lightOnRoad = false;
            if(vertical.isSelected()){
                orientation = "Vertical";
            }
            else if(horizontal.isSelected()){
                orientation = "Horizontal";
            }
            if(hasLight.isSelected()){
                lightOnRoad = true;
            }
            else if(noLight.isSelected()){
                lightOnRoad = false;
            }
            if(eastDirection.isSelected()){ direction = "east";}
            else if(westDirection.isSelected()) { direction = "west";}
            else if(northDirection.isSelected()) { direction = "north";}
            else if(southDirection.isSelected()){direction = "south";}

            if (orientation.equals("Horizontal")){
                yPosition = Integer.parseInt(yPositionField.getText());
                xPosition = Integer.parseInt(xPositionField.getText());
            }
            else if(orientation.equals("Vertical")){
                xPosition = Integer.parseInt(yPositionField.getText());
                yPosition = Integer.parseInt(xPositionField.getText());
            }
            try {
                roadLength = Integer.parseInt(length.getText());
            }
            catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Road Length Needs An Integer");
                length.setText("5");
            }
            if(lightOnRoad) {
                Road road = new Road(roadLength, orientation, xPosition, yPosition, direction, new TrafficLight());
                Map.roads.add(road);
            }
            else{
                Road road = new Road(roadLength, orientation, xPosition, yPosition, direction);
                Map.roads.add(road);
            }
            frame.repaint();

        }
        if(source==exitSimulator){
            System.exit(0);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        x = e.getX();
        y = e.getY();
        xPositionField.setText(Integer.toString(getX()));
        yPositionField.setText(Integer.toString(getY()));
    }
    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void run() {
        boolean carCollision = false;
        ArrayList<Boolean> trueCases = new ArrayList<Boolean>();
        while (running) {
            try {
                Thread.sleep(300);
            }
            catch (Exception ignored) {
            }
            for (int j = 0; j < Map.roads.size(); j++) {
                Road r = Map.roads.get(j);
                TrafficLight l = r.getTrafficLight();
                if(l != null) {
                    l.operate();
                    if (l.getColorNow().equals("Red")) {
                        r.setLightColor(Color.red);
                    }
                    else{
                        r.setLightColor(Color.GREEN);
                    }
                }

            }
            for (int i = 0; i < Map.cars.size(); i++) {
                Car recentCar = Map.cars.get(i);
                String direction = recentCar.getRoadCarIsOn().getTrafficDirection();
                if(!recentCar.collision(recentCar.getCarXPosition() + 30, recentCar) && (direction.equals("east") || direction.equals("south"))
                        || !recentCar.collision(recentCar.getCarXPosition(), recentCar) && (direction.equals("west") || direction.equals("north"))){
                    recentCar.move();
                }
                else{
                    for(int z=0; z< Map.cars.size(); z++) {
                        Car otherCar = Map.cars.get(z);
                        if (otherCar.getCarYPosition() != recentCar.getCarYPosition()) {
                            if (recentCar.getCarXPosition() + recentCar.getCarWidth() < otherCar.getCarXPosition()) {
                                trueCases.add(true); // safe to switch lane
                            }
                            else {
                                trueCases.add(false); // not safe to switch lane
                            }
                        }
                    }
                    for (int l = 0; l < trueCases.size(); l++) {
                        if (!trueCases.get(l)) {
                            carCollision = true;
                            break;
                        }
                    }
                    if(!carCollision){
                        recentCar.setCarYPosition(recentCar.getRoadCarIsOn().getRoadYPosition() + 30);
                    }
                    for(int m =0; m<trueCases.size(); m++){
                        trueCases.remove(m);
                    }
                    carCollision = false;
                }

            }
            frame.repaint();

        }
    }
}
