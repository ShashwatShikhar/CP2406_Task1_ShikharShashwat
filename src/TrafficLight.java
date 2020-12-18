public class TrafficLight {
    private double rateOfChange = 0.5;
    private String colorNow = "Green";
    private int redTime = 0;
    private int greenTime = 0;


    public String getColorNow(){
        return colorNow;
    }

    public void operate(){

        if (colorNow.equals("Red")){
            rateOfChange = 1;
            redTime += 1;
        }
        else{
            rateOfChange = 0;
            greenTime +=1;
        }

        if(redTime == 12 || greenTime == 8){
            redTime = 0;
            greenTime = 0;
            rateOfChange = 0.3;
        }
        double num = Math.random();
        if(num < rateOfChange) {
            colorNow = "Red";
        }
        else
            colorNow = "Green";
    }
}