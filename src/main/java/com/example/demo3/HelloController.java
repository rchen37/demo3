package com.example.demo3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.codehaus.jackson.map.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

public class HelloController {

    private static int hour=0;
    private static int currenthour=0;
    private static int[][] hourweather;// update by calling updatehourdata(), int[24][3], int[3][0] is rain at 3am, int[3][1] is wind,int[3][2] is vis
                                // if 1 then unsafe/rain, if 0 then safe/no rain.

    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l11;
    @FXML
    private Label l21;
    @FXML
    private Label l31;
    @FXML
    private Label time;
    @FXML
    private ImageView g1;
    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private TextField in;
    @FXML
    private TextArea out;

    private Map map;




    public static Map<String, Object> getMap() throws IOException {

        //"https://api.openweathermap.org/data/2.5/onecall?lat=52.20&lon=0.11&exclude=hourly&appid=8d0947155c9fc52d66c72043321d4920"

        URL url=new URL("https://api.openweathermap.org/data/2.5/onecall?lat=52.20&lon=0.11&exclude=hourly&appid=8d0947155c9fc52d66c72043321d4920");
        InputStream inputStream = url.openConnection().getInputStream();
        HttpURLConnection huc=(HttpURLConnection)url.openConnection();
        Object huc1= huc.getContent();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);

        System.out.println(((Map)jsonMap.get("current")).get("humidity"));

        huc.disconnect();
        return jsonMap;
    }



    public static Map<String, Object> getMap2(){
        try{
            //http://api.weatherapi.com/v1/forecast.json?key=ff81ec4c776e4e6c8bb164300221205&q=Cambridge
            URL url=new URL("http://api.weatherapi.com/v1/forecast.json?key=ff81ec4c776e4e6c8bb164300221205&q=Cambridge");
            InputStream inputStream = url.openConnection().getInputStream();
            HttpURLConnection huc=(HttpURLConnection)url.openConnection();
            Object huc1= huc.getContent();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);
            //System.out.println(((Map)jsonMap.get("current")).get("humidity"));
            huc.disconnect();
            return jsonMap;
        }catch(IOException e){}
        return null;
    }



    @FXML
    private void current(){

        try{
            time.setText(String.valueOf(new Date().getHours()));
            c1.setVisible(true);
            c2.setVisible(true);
            c3.setVisible(true);
            l11.setVisible(true);
            l21.setVisible(true);
            l31.setVisible(true);
            currenthour=new Date().getHours();
            hour=currenthour;
            map=getMap();
            int vis=(Integer) ((Map)(map.get("current"))).get("visibility");
            double wind=Math.ceil((Double) ((Map)(map.get("current"))).get("wind_speed"));
            java.util.List<Map<Object,Object>> weather = (List<Map<Object, Object>>) ((Map)(map.get("current"))).get("weather");
            int id= (int) weather.get(0).get("id");
            List<Map> min= (List<Map>) (map.get("minutely"));
            double rain=Double.parseDouble(min.get(0).get("precipitation").toString()) ;
            if(rain<0.1){l1.setText(" Rain: Clear");
                Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
                g1.setImage(image);}else{if(rain<1){l1.setText(" Rain: Mild");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\2.jpg");
                g1.setImage(image);}else{l1.setText(" Rain: Downpour");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\3.jpg");
                g1.setImage(image);}}
            //out.setText("id "+id);
            //in.setText("   "+ new Random());
            String wind1=(wind>20?"Unsafe":"Safe");
            String vis1=(vis>5000?"Safe":"Unsafe");
            l2.setText(" Wind: "+wind1);
            l3.setText(" Vis:  "+vis1);


            System.out.println(min.get(5).get("precipitation"));
            double five=Double.parseDouble(min.get(10).get("precipitation").toString()) ;
            double ten=  Double.parseDouble(min.get(30).get("precipitation").toString()) ;
            double thirty=  Double.parseDouble(min.get(50).get("precipitation").toString()) ;

            if(five<0.1){c1.setFill(Color.LIGHTGREEN);}else{if(five<1)c1.setFill(Color.YELLOW);else{c1.setFill(Color.RED);}}
            if(ten<0.1){c2.setFill(Color.LIGHTGREEN);}else{if(ten<1)c2.setFill(Color.YELLOW);else{c2.setFill(Color.RED);}}
            if(thirty<0.1){c3.setFill(Color.LIGHTGREEN);}else{if(thirty<1)c3.setFill(Color.YELLOW);else{c3.setFill(Color.RED);}}


        }catch (IOException e){

        }

    }


    private static void updatehourdata(){

        Map map=getMap2();
        Map forecast= (Map) map.get("forecast");
        List<Map> forecastday=(List<Map>) (forecast.get("forecastday"));
        Map toHour=forecastday.get(0);
        List<Map> hours=(List)toHour.get("hour");
        hourweather=new int[24][3];
        int i=0;
        for(Map one:hours){
            int rain= (int) one.get("will_it_rain");

            double vis1= (double) one.get("vis_km");
            double wind1=(double) one.get("wind_kph");

            System.out.println("wind speed is "+wind1);
            System.out.println("visibility is "+vis1);

            int vis=vis1>5?0:1;
            int wind=wind1<20?0:1;
            hourweather[i][0]=rain; hourweather[i][1]=wind;hourweather[i][2]=vis;
        }

    }

    @FXML
    private void nexthour(){
        l11.setVisible(false);
        l21.setVisible(false);
        l31.setVisible(false);
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        updatehourdata();
        hour=(hour==23?0:hour+1);
        time.setText(String.valueOf(hour));
        time.setTextFill(Color.BLUEVIOLET);
        int rain=hourweather[hour][0];int wind=hourweather[hour][1];int vis=hourweather[hour][2];
        if(rain==0){l1.setText(" Rain: Clear");
            Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");g1.setImage(image);}else{
            l1.setText(" Rain: Mild");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\2.jpg");
            g1.setImage(image);

        }
        if(wind==0){l2.setText(" Wind: "+"Safe");
            }else{l2.setText(" Wind: "+"Unsafe");}
        if(vis==0){l3.setText(" Vis:  "+"Safe");}else{l3.setText(" Vis:  "+"Unsafe");}
    }
    @FXML
    private void previoushour(){
        l11.setVisible(false);
        l21.setVisible(false);
        l31.setVisible(false);
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        updatehourdata();
        hour=(hour==0?23:hour-1);
        time.setText(String.valueOf(hour));
        time.setTextFill(Color.BLUEVIOLET);
        int rain=hourweather[hour][0];int wind=hourweather[hour][1];int vis=hourweather[hour][2];
        if(rain==0){l1.setText(" Rain: Clear");
            Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");g1.setImage(image);}else{
            l1.setText(" Rain: Mild");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\2.jpg");
            g1.setImage(image);

        }
        if(wind==0){l2.setText(" Wind: "+"Safe");
        }else{l2.setText(" Wind: "+"Unsafe");}
        if(vis==0){l3.setText(" Vis:  "+"Safe");}else{l3.setText(" Vis:  "+"Unsafe");}
    }

    @FXML
    private void changetime(){  //not used
        CharSequence a=in.getCharacters();
        char a1=a.charAt(0);
        char a2=a.charAt(1);
        int n=(a1-'0')*10+(a2-'1');
        int x=n-new Date().getHours();
        System.out.println(x);
        List<Map> h= (List<Map>) (map.get("hourly"));
        System.out.println(h.size());
        if(x<0){
            System.out.println(1);
        Map h1=h.get(x);
        int vis=(Integer) h1.get("visibility");
        double wind=Math.ceil((Double) h1.get("wind_speed"));
        boolean israin=true;
        double rain=0;
        try{
            rain=Double.parseDouble(((Map)(h1.get("rain"))).get("1h").toString()) ;
        }catch(NullPointerException e){
            israin=false;
        }
        if(!israin||rain<0.1){l1.setText("Rain: Clear");
            Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
            g1.setImage(image);}else{if(rain<1){l1.setText("Rain: Mild");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\2.jpg");
            g1.setImage(image);}else{l1.setText("Rain: Downpour");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\3.jpg");
            g1.setImage(image);}}

        in.setText("   "+ new Random());
        l2.setText("wind "+wind);
        l3.setText("vis "+vis);
    }}


    @FXML
    private void initialize() {
        Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
        g1.setImage(image);
        current();

    }


    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {


    }
}