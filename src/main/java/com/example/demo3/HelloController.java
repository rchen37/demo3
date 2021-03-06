package com.example.demo3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private static double[][] hourweather1=new double[24][3];
    private static Object[][] newdata=new Object[5][24];
    private static boolean picture=false;

    private static Rectangle[][] Rectangle=new Rectangle[3][15];
    public Button back;

    @FXML
    private Label temp;
    @FXML
    private Label text;
    @FXML
    private Label wind_mph;
    @FXML
    private Label humid;
    @FXML
    private Label precip;



    @FXML
    private Rectangle x11;
    @FXML
    private Rectangle x12;
    @FXML
    private Rectangle x13;
    @FXML
    private Rectangle x14;
    @FXML
    private Rectangle x15;
    @FXML
    private Rectangle x16;
    @FXML
    private Rectangle x17;
    @FXML
    private Rectangle x18;
    @FXML
    private Rectangle x19;
    @FXML
    private Rectangle x20;
    @FXML
    private Rectangle x21;
    @FXML
    private Rectangle x22;
    @FXML
    private Rectangle x23;
    @FXML
    private Rectangle x24;
    @FXML
    private Rectangle x25;
    @FXML
    private Rectangle y11;
    @FXML
    private Rectangle y12;
    @FXML
    private Rectangle y13;
    @FXML
    private Rectangle y14;
    @FXML
    private Rectangle y15;
    @FXML
    private Rectangle y16;
    @FXML
    private Rectangle y17;
    @FXML
    private Rectangle y18;
    @FXML
    private Rectangle y19;
    @FXML
    private Rectangle y20;
    @FXML
    private Rectangle y21;
    @FXML
    private Rectangle y22;
    @FXML
    private Rectangle y23;
    @FXML
    private Rectangle y24;
    @FXML
    private Rectangle y25;
    @FXML
    private Rectangle z11;
    @FXML
    private Rectangle z12;
    @FXML
    private Rectangle z13;
    @FXML
    private Rectangle z14;
    @FXML
    private Rectangle z15;
    @FXML
    private Rectangle z16;
    @FXML
    private Rectangle z17;
    @FXML
    private Rectangle z18;
    @FXML
    private Rectangle z19;
    @FXML
    private Rectangle z20;
    @FXML
    private Rectangle z21;
    @FXML
    private Rectangle z22;
    @FXML
    private Rectangle z23;
    @FXML
    private Rectangle z24;
    @FXML
    private Rectangle z25;



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
    private Label selected;
    @FXML
    private Label vis11;
    @FXML
    private Label wind11;

    @FXML
    private ImageView g1;
    @FXML
    private ImageView g2;
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
            selected.setText("current");

            time.setText(String.valueOf(new Date().getHours())+"h");
            c1.setVisible(true);
            c2.setVisible(true);
            c3.setVisible(true);
            l11.setVisible(true);
            l21.setVisible(true);
            l31.setVisible(true);
            currenthour=new Date().getHours();
            hour=currenthour;
            updatehourdata();
            map=getMap();
            int vis=(Integer) ((Map)(map.get("current"))).get("visibility");
            double wind=Math.ceil((Double) ((Map)(map.get("current"))).get("wind_speed"));
            java.util.List<Map<Object,Object>> weather = (List<Map<Object, Object>>) ((Map)(map.get("current"))).get("weather");
            int id= (int) weather.get(0).get("id");
            List<Map> min= (List<Map>) (map.get("minutely"));
            double rain=hourweather1[currenthour][0];//Double.parseDouble(min.get(0).get("precipitation").toString()) ;
            if(rain<0.1){l1.setText(" Rain: Clear");
                //Image image1=new Image(String.valueOf(getClass().getResource("images\\1.jpg")));
                Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
                //Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
                g1.setImage(image);g2.setImage(image);}else{if(rain<1){l1.setText(" Rain: Mild");Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\2.jpg"));
                g1.setImage(image);g2.setImage(image);}else{l1.setText(" Rain: Downpour");Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\3.png"));
                g1.setImage(image);g2.setImage(image);}}
            //out.setText("id "+id);
            //in.setText("   "+ new Random());
            String wind1=(wind>20?"Unsafe":"Safe");
            String vis1=(vis>5000?"Safe":"Unsafe");
            l2.setText(" Wind: "+wind1);
            l3.setText(" Vis:  "+vis1);

            vis11.setText(String.valueOf(hourweather1[hour][2])+" km");
            wind11.setText(String.valueOf(hourweather1[hour][1])+" kph");

            System.out.println(min.get(5).get("precipitation"));
            double five=Double.parseDouble(min.get(10).get("precipitation").toString()) ;
            double ten=  Double.parseDouble(min.get(30).get("precipitation").toString()) ;
            double thirty=  Double.parseDouble(min.get(50).get("precipitation").toString()) ;

            if(five<0.1){c1.setFill(Color.LIGHTGREEN);}else{if(five<1)c1.setFill(Color.YELLOW);else{c1.setFill(Color.RED);}}
            if(ten<0.1){c2.setFill(Color.LIGHTGREEN);}else{if(ten<1)c2.setFill(Color.YELLOW);else{c2.setFill(Color.RED);}}
            if(thirty<0.1){c3.setFill(Color.LIGHTGREEN);}else{if(thirty<1)c3.setFill(Color.YELLOW);else{c3.setFill(Color.RED);}}

            //Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
            Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
            //g2.setImage(image);
            //g2.setVisible(false);

            temp.setText("Temp: "+(String) String.valueOf(newdata[0][hour]));
            text.setText((String) (newdata[1][hour]));
            wind_mph.setText("Wind: "+(String) String.valueOf(newdata[2][hour])+" mph");
            humid.setText("Humid: "+(String) String.valueOf(newdata[3][hour])+"%");
            precip.setText("Precip: "+(String) String.valueOf(newdata[4][hour])+"mm");

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
        hourweather1=new double[24][3];
        int i=0;
        for(Map one:hours){
            double rain1= (double) one.get("precip_mm");

            double vis1= (double) one.get("vis_km");
            double wind1=(double) one.get("wind_kph");
            hourweather1[i][0]=rain1;
            hourweather1[i][1]=wind1; hourweather1[i][2]=vis1;

            System.out.println("wind speed is "+wind1);
            System.out.println("visibility is "+vis1);
            int rain=(rain1<0.1?0:(rain1<5?1:2));
            int vis=vis1>5?0:1;
            int wind=wind1<30?0:1;
            hourweather[i][0]=rain; hourweather[i][1]=wind;hourweather[i][2]=vis;


            Object tem=one.get("temp_c");
            Map condition=(Map)one.get("condition");
            Object tex=condition.get("text");
            Object win=one.get("wind_mph");
            Object hum=one.get("humidity");
            Object pre=one.get("precip_mm");
            newdata[0][i]=tem; newdata[1][i]=tex; newdata[2][i]=win; newdata[3][i]=hum; newdata[4][i]=pre;


            i++;



        }

    }

    @FXML
    private void nexthour(){
        selected.setText("selected");
        l11.setVisible(false);
        l21.setVisible(false);
        l31.setVisible(false);
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        updatehourdata();
        hour=(hour==23?0:hour+1);
        time.setText(String.valueOf(hour)+"h");
        //time.setTextFill(Color.BLUEVIOLET);
        int rain=hourweather[hour][0];int wind=hourweather[hour][1];int vis=hourweather[hour][2];
        if(rain==0){l1.setText(" Rain: Clear");
            Image image;
            if(picture){
                image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
                //image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
            }else{
                image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
                //image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\4.jpg");
            }
            picture=!picture;
            g1.setImage(image);g2.setImage(image);}else {

            if (rain == 1) {
                l1.setText(" Rain: Mild");
                Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\2.jpg"));
                //Image image = new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\2.jpg");
                g1.setImage(image);
                g2.setImage(image);
                ;
            } else {
                l1.setText(" Rain: Heavy");
                Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\3.png"));
                //Image image = new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\3.png");
                g1.setImage(image);
                g2.setImage(image);
            }
        }
            temp.setText("Temp: "+(String) String.valueOf(newdata[0][hour]));
            text.setText((String) (newdata[1][hour]));
            wind_mph.setText("Wind: "+(String) String.valueOf(newdata[2][hour])+" mph");
            humid.setText("Humid: "+(String) String.valueOf(newdata[3][hour])+"%");
            precip.setText("Precip: "+(String) String.valueOf(newdata[4][hour])+"mm");




        vis11.setText(String.valueOf(hourweather1[hour][2])+" km");
        wind11.setText(String.valueOf(hourweather1[hour][1])+" kph");
        if(wind==0){l2.setText(" Wind: "+"Safe");
            }else{l2.setText(" Wind: "+"Unsafe");}
        if(vis==0){l3.setText(" Vis:  "+"Safe");}else{l3.setText(" Vis:  "+"Unsafe");}
    }
    @FXML
    private void previoushour(){
        selected.setText("selected");
        l11.setVisible(false);
        l21.setVisible(false);
        l31.setVisible(false);
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        updatehourdata();
        hour=(hour==0?23:hour-1);

        time.setText(String.valueOf(hour)+"h");
        //time.setTextFill(Color.BLUEVIOLET);
        int rain=hourweather[hour][0];int wind=hourweather[hour][1];int vis=hourweather[hour][2];
        if(rain==0){l1.setText(" Rain: Clear");
            Image image;
            if(picture){
                image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
                //image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\1.jpg");
            }else{
                image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
                //image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\4.jpg");
            }
            picture=!picture;
            g1.setImage(image);g2.setImage(image);}else{

            if(rain==1){
                l1.setText(" Rain: Mild");Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\2.jpg"));
                g1.setImage(image);g2.setImage(image);
            }else{
                l1.setText(" Rain: Heavy");Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\3.png"));
                g1.setImage(image);g2.setImage(image);
            }



        }
        vis11.setText(String.valueOf(hourweather1[hour][2])+" km");
        wind11.setText(String.valueOf(hourweather1[hour][1])+" kph");
        if(wind==0){l2.setText(" Wind: "+"Safe");
        }else{l2.setText(" Wind: "+"Unsafe");}
        if(vis==0){l3.setText(" Vis:  "+"Safe");}else{l3.setText(" Vis:  "+"Unsafe");}
        temp.setText("Temp: "+(String) String.valueOf(newdata[0][hour]));
        text.setText((String) (newdata[1][hour]));
        wind_mph.setText("Wind: "+(String) String.valueOf(newdata[2][hour])+" mph");
        humid.setText("Humid: "+(String) String.valueOf(newdata[3][hour])+"%");
        precip.setText("Precip: "+(String) String.valueOf(newdata[4][hour])+"mm");
    }



    /*
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
            g1.setImage(image);g2.setImage(image);}else{if(rain<1){l1.setText("Rain: Mild");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\2.jpg");
            g1.setImage(image);g2.setImage(image);}else{l1.setText("Rain: Downpour");Image image=new Image("C:\\Users\\86189\\Desktop\\demo3\\src\\main\\resources\\images\\3.png");
            g1.setImage(image);g2.setImage(image);}}

        in.setText("   "+ new Random());
        l2.setText("wind "+wind);
        l3.setText("vis "+vis);
    }}
    */

    @FXML
    private void initialize() {
        //Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
        Image image=new Image(String.valueOf(System.getProperty("user.dir")+"\\src\\main\\resources\\images\\1.jpg"));
        g1.setImage(image);g2.setImage(image);
        current();


        Rectangle[0][0]=x11;
        Rectangle[0][1]=x12;
        Rectangle[0][2]=x13;
        Rectangle[0][3]=x14;
        Rectangle[0][4]=x15;
        Rectangle[0][5]=x16;
        Rectangle[0][6]=x17;
        Rectangle[0][7]=x18;
        Rectangle[0][8]=x19;
        Rectangle[0][9]=x20;
        Rectangle[0][10]=x21;
        Rectangle[0][11]=x22;
        Rectangle[0][12]=x23;
        Rectangle[0][13]=x24;
        Rectangle[0][14]=x25;

        Rectangle[1][0]=y11;
        Rectangle[1][1]=y12;
        Rectangle[1][2]=y13;
        Rectangle[1][3]=y14;
        Rectangle[1][4]=y15;
        Rectangle[1][5]=y16;
        Rectangle[1][6]=y17;
        Rectangle[1][7]=y18;
        Rectangle[1][8]=y19;
        Rectangle[1][9]=y20;
        Rectangle[1][10]=y21;
        Rectangle[1][11]=y22;
        Rectangle[1][12]=y23;
        Rectangle[1][13]=y24;
        Rectangle[1][14]=y25;

        Rectangle[2][0]=z11;
        Rectangle[2][1]=z12;
        Rectangle[2][2]=z13;
        Rectangle[2][3]=z14;
        Rectangle[2][4]=z15;
        Rectangle[2][5]=z16;
        Rectangle[2][6]=z17;
        Rectangle[2][7]=z18;
        Rectangle[2][8]=z19;
        Rectangle[2][9]=z20;
        Rectangle[2][10]=z21;
        Rectangle[2][11]=z22;
        Rectangle[2][12]=z23;
        Rectangle[2][13]=z24;
        Rectangle[2][14]=z25;

        updatehourdata();

        for(int i=0;i<3;i++){
            for(int j=7;j<=21;j++){

                if(hourweather[j][i]==1){
                    Rectangle[i][j-7].setFill(Paint.valueOf("#f4d5d5"));
                    Rectangle[i][j-7].setAccessibleText(String.valueOf(j));
                }else{Rectangle[i][j-7].setFill(Color.TRANSPARENT);

                }
            }
        }

        back.setVisible(false);
        precip.setVisible(false);
        g2.setVisible(false);
        temp.setVisible(false);
        text.setVisible(false);
        wind_mph.setVisible(false);
        humid.setVisible(false);
    }


    @FXML
    private void goback(){
        back.setVisible(false);
        precip.setVisible(false);
        g2.setVisible(false);
        temp.setVisible(false);
        text.setVisible(false);
        wind_mph.setVisible(false);
        humid.setVisible(false);
    }

    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {

    }


    @FXML
    private void more(){

        System.out.println("clicked");
        back.setVisible(true);
        precip.setVisible(true);
        g2.setVisible(true);
        temp.setVisible(true);
        text.setVisible(true);
        wind_mph.setVisible(true);
        humid.setVisible(true);
    }

}