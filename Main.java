//GROUP 66
//Ritik Vatsal (2019321) | Vaibhav Saxena (2019342)

import javafx.animation.*;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;

import java.sql.Struct;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.nio.file.Paths;
import java.util.Timer;

class Colours extends Parent
{
    protected String coloursarr[] = {"Pink","Purple","Blue","Yellow"};
    protected int currColour = -1;
    protected Random r = new Random();

    protected void setColour(int newc)
    {
        currColour = newc;
    }
    protected String getColour()
    {
        return coloursarr[currColour];
    }
}

class Ball extends Colours
{
    protected Circle circle = new Circle();
    protected double xval = 225;
    protected double yval = 700;
    protected int hitgrnd = 0;

    Ball(Stage stage)
    {
        this.setColour(r.nextInt(4));

        circle.setRadius(15);
        circle.setTranslateX(xval);
        circle.setTranslateY(yval);
        circle.setFill(Color.WHITE);

        getChildren().addAll(circle);
    }

    public void switchColour(ColourSwitcher cs)
    {
        switch(cs.nextColour)
        {
            case 0: circle.setFill(Color.DEEPPINK);
                break;
            case 1: circle.setFill(Color.DARKVIOLET);
                break;
            case 2: circle.setFill(Color.DEEPSKYBLUE);
                break;
            case 3: circle.setFill(Color.YELLOW);
                break;
        }
    }
}

abstract class Obstacle extends Colours
{
    protected int objType = -1;

    public abstract void movement();
}

class Obj1 extends Obstacle
{
    protected Image obj1 = new Image("file:///C:/Resources/Obj1.png");
    protected ImageView imgvu = new ImageView(obj1);

    Obj1()
    {
        imgvu.setTranslateX(75);
        imgvu.setTranslateY(120);
        imgvu.setFitHeight(300);
        imgvu.setFitWidth(303);

        getChildren().addAll(imgvu);
        this.movement();
    }

    public void movement()
    {
        RotateTransition rt = new RotateTransition(Duration.millis(30500),imgvu);
        rt.setByAngle(3600);
        rt.setCycleCount(Animation.INDEFINITE);

        if (!rt.isAutoReverse()){
            rt.play();
        }
    }
}

class Star extends Parent
{
    protected Image star1 = new Image("file:///C:/Resources/1.png");
    protected ImageView s1 = new ImageView(star1);
    protected Circle circle = new Circle();
    protected int passed = 0;

    Star()
    {
        s1.setTranslateX(205);
        s1.setTranslateY(20);
        s1.setFitHeight(40);
        s1.setFitWidth(40);
        s1.setEffect(new GaussianBlur(3));

        circle.setRadius(15);
        circle.setTranslateX(225);
        circle.setTranslateY(40);
        circle.setFill(Color.WHITE);
        circle.setVisible(false);

        getChildren().addAll(s1,circle);
        this.twinkle();
    }

    public void twinkle()
    {
        FadeTransition ft = new FadeTransition(Duration.seconds(1),s1);

//            ft.setFromValue(0);
//            ft.setToValue(1);

        ft.setAutoReverse(true);
        s1.setVisible(true);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.play();
    }

    public void hide()
    {
        getChildren().remove(s1);
        getChildren().remove(circle);
    }
}

class ColourSwitcher extends Colours
{
    protected int nextColour = -1;
    protected Image switcher1 = new Image("file:///C:/Resources/2.png");
    protected ImageView sw1 = new ImageView(switcher1);
    protected Circle circle = new Circle();
    protected int switched = 0;

    ColourSwitcher(Ball b)
    {
        randomColour(b);

        sw1.setTranslateX(205);
        sw1.setTranslateY(550);
        sw1.setFitHeight(40);
        sw1.setFitWidth(40);
        movement();

        circle.setRadius(15);
        circle.setTranslateX(225);
        circle.setTranslateY(570);
        circle.setFill(Color.WHITE);
        circle.setVisible(false);

        getChildren().addAll(sw1,circle);
    }

    protected void randomColour(Ball b)
    {
        while(nextColour==b.currColour || nextColour==-1)
        {
            nextColour = r.nextInt(4);
        }
    }

    public void movement()
    {
        RotateTransition rt = new RotateTransition(Duration.millis(150000),sw1);
        rt.setByAngle(72000);
        rt.setCycleCount(Animation.INDEFINITE);

        if (!rt.isAutoReverse()){
            rt.play();
        }
    }

    public void hide()
    {
        getChildren().remove(sw1);
        getChildren().remove(circle);
    }
}

public class Main extends Application
{
    int GameOver;
    MediaPlayer mp;
    MediaPlayer ded;
    MediaPlayer pnt;
    MediaPlayer swch;
    private GameMenu igmenu;
    private GameMenu scoreDisplay;
    private GameMenu gameMenu;
    private GameMenu splash;

    class Game
    {
        private long score = 0;
        private long savedscore1 = 0;
        private long savedscore2 = 0;
        private int rungame = 0;
        private int bonusgame = 0;

        Game(Stage stage)
        {
            Pane top = new Pane();
            top.setPrefSize(450, 800);
            Scene scene2 = new Scene(top);
            Image bgHS2 = new Image("file:///C:/Resources/hs2.png");
            ImageView imgvu = new ImageView(bgHS2);
            imgvu.setFitHeight(800);
            imgvu.setFitWidth(450);
            Ball ball = new Ball(stage);
            Obj1 o1 = new Obj1();
            Star s1 = new Star();
            igmenu = new GameMenu(stage,2);
            scoreDisplay = new GameMenu(stage,3);
            igmenu.setVisible(true);
            scoreDisplay.setVisible(true);
            ColourSwitcher cs1 = new ColourSwitcher(ball);
            top.getChildren().addAll(imgvu,ball,o1,s1,cs1, igmenu, scoreDisplay);
            int view;
            if ((view=4)<6)
            {
                stage.setScene(scene2);
                stage.show();
            }

            // Scrolling
            Timeline t4 = new Timeline(new KeyFrame(Duration.millis(20),new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent etl)
                {
                    o1.imgvu.setLayoutY(o1.imgvu.getLayoutY()+3);
                    s1.circle.setLayoutY(s1.circle.getLayoutY()+3);
                    s1.s1.setLayoutY(s1.s1.getLayoutY()+3);
                    cs1.sw1.setLayoutY(cs1.sw1.getLayoutY()+3);
                }
            }));
            t4.setCycleCount(Timeline.INDEFINITE);

            // Ball Up
            Timeline t1 = new Timeline(new KeyFrame(Duration.millis(20),new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent etl)
                {
                    ball.circle.setLayoutY(ball.circle.getLayoutY()-5);
                    if(ball.circle.getLayoutY()<-300)
                        t4.play();
                }
            }));
            t1.setCycleCount(Timeline.INDEFINITE);

            // Ball Down
            Timeline t2 = new Timeline(new KeyFrame(Duration.millis(20),new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent etl)
                {
                    t4.pause();
                    ball.circle.setLayoutY(ball.circle.getLayoutY()+6);
                }
            }));
            t2.setCycleCount(Timeline.INDEFINITE);

            // Collision
            Timeline t3 = new Timeline(new KeyFrame(Duration.millis(1),new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent etl)
                {
                    Shape sh1;
                    sh1 = Shape.intersect(ball.circle,s1.circle);
                    Shape sh2;
                    sh2 = Shape.intersect(ball.circle,cs1.circle);

                    if(sh1.getLayoutBounds().getWidth()!=-1)
                    {
                        if(s1.passed==0)
                        {
                            yesyesyes(1);
                            System.out.println("Score +1!");
                            score++;
                            String newscore = "SCORE : " + Long.toString(score);
                            scoreDisplay.score.setText(newscore);
                            s1.hide();
                            s1.passed = 1;
                        }
                    }
                    if(sh2.getLayoutBounds().getWidth()!=-1)
                    {
                        if(cs1.switched==0)
                        {   change(1);
                            cs1.switched = 1;
                            cs1.hide();
                            ball.switchColour(cs1);
                            System.out.println("Colour Changed!");
                        }
                    }
                    if(ball.circle.getLayoutY()>100)
                    {
                        t1.pause();
                        t2.pause();
                        t4.pause();
                        if(ball.hitgrnd==0)
                        {
                            ball.hitgrnd = 1;
                            MissionFailed(1);
                            System.out.println("Hit Ground! Game Over!");
                            GameOver=1;

                        }
                    }
                }
            }));
            t3.setCycleCount(Timeline.INDEFINITE);
            t3.play();
            scene2.setOnMousePressed(
                    event -> {
                        t2.pause();
                        t1.play();
                    }
            );
            scene2.setOnMouseReleased(
                    event -> {
                        t1.pause();
                        t2.play();
                    }
            );
        }
    }

    public void BGmusic(int status) {
        Media bgMusic;
        bgMusic = new Media("file:///C:/Resources/bg.mp3");
        mp =new MediaPlayer(bgMusic);
        if (status==1)mp.play();
        if (status==0)mp.pause();

    }

    public void MissionFailed(int status) {
        Media bgMusic;
        bgMusic = new Media("file:///C:/Resources/ded.mp3");
        ded =new MediaPlayer(bgMusic);
        if (status==1)ded.play();
        if (status==0)ded.pause();
    }

    public void yesyesyes(int status) {
        Media bgMusic;
        bgMusic = new Media("file:///C:/Resources/pnt.mp3");
        pnt =new MediaPlayer(bgMusic);
        if (status==1)pnt.play();
        if (status==0)pnt.pause();
    }

    public void change(int status) {
        Media bgMusic;
        bgMusic = new Media("file:///C:/Resources/swch.mp3");
        swch =new MediaPlayer(bgMusic);
        if (status==1)swch.play();
        if (status==0)swch.pause();
    }



    @Override
    public void start(Stage stage) throws Exception
    {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setTitle("ColorSwitch V_1.0.2");

        Pane top = new Pane();

        Label label1 = new Label("Play ColorSwitch");
        top.setPrefSize(450, 800);
        Scene scene1 = new Scene(top);
        scene1.setCursor(Cursor.CROSSHAIR);
        Image bgHS1 = new Image("file:///C:/Resources/hs1.png");
        ImageView imgvu = new ImageView(bgHS1);

        imgvu.setFitHeight(800);
        imgvu.setFitWidth(450);

        //ROTATING RINGS

        Image out = new Image("file:///C:/Resources/outer.png");
        ImageView outer = new ImageView(out);

        outer.setFitHeight(370);
        outer.setFitWidth(370);
        outer.setTranslateX(42);
        outer.setTranslateY(220);

        Image mid = new Image("file:///C:/Resources/MID.png");
        ImageView middle = new ImageView(mid);

        middle.setFitHeight(306);
        middle.setFitWidth(306);
        middle.setTranslateX(74.5);
        middle.setTranslateY(252);

        Image in = new Image("file:///C:/Resources/INN.png");
        ImageView inner = new ImageView(in);

        inner.setFitHeight(236);
        inner.setFitWidth(236);
        inner.setTranslateX(110);
        inner.setTranslateY(287);

        RotateTransition rto = new RotateTransition(Duration.millis(195500),outer);
        rto.setByAngle(36000);
        rto.setCycleCount(Animation.INDEFINITE);
        RotateTransition rtm = new RotateTransition(Duration.millis(195500),middle);
        rtm.setByAngle(-36000);
        rtm.setCycleCount(Animation.INDEFINITE);
        RotateTransition rti = new RotateTransition(Duration.millis(195500),inner);
        rti.setByAngle(36000);
        rti.setCycleCount(Animation.INDEFINITE);

        if (!rto.isAutoReverse()){
            rto.play();
            rtm.play();
            rti.play();
        }

        //END

        BGmusic(1);
        gameMenu = new GameMenu(stage,0);
        splash = new GameMenu(stage,1);
        gameMenu.setVisible(false);
        top.getChildren().addAll(imgvu,gameMenu,splash,outer,middle,inner);

        splash.setVisible(true);

        scene1.setOnKeyPressed(event -> {

            if (!gameMenu.isVisible()) {
                splash.setVisible(false);
                FadeTransition ft = new FadeTransition(Duration.seconds(1),gameMenu);
                int view;
                if ((view=4)<6){
                    ft.setFromValue(0);
                    ft.setToValue(1);
                }

                gameMenu.setVisible(true);
                ft.play();
            }
            else {}

        });

        if (GameOver==1){
            System.out.println("oof");
        }
        stage.setScene(scene1);
        stage.centerOnScreen();

    }

    private class GameMenu extends Parent
    {
        private final int obst = 4;
        public Text score;

        public GameMenu(Stage stage, int type)
        {
            if (type==0){
                VBox menu0 = new VBox(10);
                VBox menu1 = new VBox(10);
                VBox menuPlay = new VBox(10);
                VBox music = new VBox();
                menu0.setTranslateX(100);
                menu0.setTranslateY(630);

                menu1.setTranslateX(100);
                menu1.setTranslateY(630);

                menuPlay.setTranslateX(127);
                menuPlay.setTranslateY(306);

                music.setTranslateX(370);
                music.setTranslateY(10);
                int vision;
                TranslateTransition bb1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                TranslateTransition bb = new TranslateTransition(Duration.seconds(0.25), menu1);
                vision=2;
                menu1.setTranslateX(400);
                menuButtons btnnew = new menuButtons("NEW GAME", 0,1);
                btnnew.setOnMouseClicked(event -> {
//
                    setVisible(false);
                    new Game(stage);
                });

                menuButtons musicCtrl = new menuButtons("MUSIC", 0,65);
                musicCtrl.setOnMouseClicked(event -> {


                    if (mp.isMute()==true){
                        mp.setMute(false);
                    }
                    else{
                        mp.setMute(true);
                    }

                });
                menuButtons btnCircle = new menuButtons("alwaysPlay", 1,0);
                btnCircle.setOnMouseClicked(event -> {
//                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
//                ft.setFromValue(1);
//                ft.setToValue(0);
//                ft.setOnFinished(evt -> setVisible(false));
//                ft.play();
                    new Game(stage);
                });
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);

                menuButtons btnOptions = new menuButtons("LOAD GAME", 0,1);
                btnOptions.setOnMouseClicked(event -> {
                    int view;
                    if ((view=4)<6){getChildren().add(menu1);}
//                BGmusic(0);
                    tt.setToX(menu0.getTranslateX() - 400);

                    tt.play();
                    tt1.setToX(menu0.getTranslateX());



                    tt1.play();
                    int textSize=tt.getCycleCount();
                    tt.setOnFinished(evt -> {
                        if ((view)<6){ getChildren().remove(menu0);}
                    });
                });

                menuButtons btnSound = null;
                menuButtons btnVideo = null;

                menuButtons btnBack = new menuButtons("< BACK", 0,1);
                btnBack.setOnMouseClicked(event -> {
                    getChildren().add(menu0);

                    bb.setToX(menu1.getTranslateX() + 400);
                    bb.play();

                    bb1.setToX(menu1.getTranslateX());


                    bb1.play();
                    int VisibleParamCheck;
                    if (!isVisible()){VisibleParamCheck = 0;}
                    else
                    {VisibleParamCheck=1;}
                    bb.setOnFinished(evt -> {
                        getChildren().remove(menu1);
                    });
                });


                if (vision>1) {

                    btnSound = new menuButtons("SAVE1 : 2 PTS", 0, 1);

                    btnVideo = new menuButtons("SAVE2 : 7 PTS", 0, 1);
                }
                menuButtons btnExit = new menuButtons("EXIT", 0,1);
                btnExit.setOnMouseClicked(event -> {
                    System.out.println("Exiting Game...");
                    System.exit(0);
                });

                menu0.getChildren().addAll(btnnew, btnOptions, btnExit);
                menu1.getChildren().addAll(btnBack, btnSound, btnVideo);
                menuPlay.getChildren().addAll(btnCircle);
                music.getChildren().addAll(musicCtrl);

                Rectangle bg = new Rectangle(450, 800);
                bg.setFill(Color.GRAY);
                int VisibleParamCheck;
                if (!isVisible()){VisibleParamCheck = 0;}
                else
                {VisibleParamCheck=1;}
                bg.setOpacity(0.3);

                getChildren().addAll(bg, menu0, menuPlay,music);
            }

            else if (type==1){
                VBox start = new VBox(10);
                start.setTranslateX(112);
                start.setTranslateY(660);
                menuButtons starter = new menuButtons("PRESS ANY BUTTON",2,0);
                int view;
                if ((view=4)<6){
                    start.getChildren().addAll(starter);}
                getChildren().addAll(start);
            }

            else if (type==2){
                VBox menu0 = new VBox(10);

                VBox pausebtn = new VBox();
                menu0.setTranslateX(100);
                menu0.setTranslateY(630);

                menu0.setVisible(false);
                pausebtn.setTranslateX(370);
                pausebtn.setTranslateY(10);


                menuButtons btnnew = new menuButtons("NEW GAME", 0,1);
                btnnew.setOnMouseClicked(event -> {
                    new Game(stage);
                });

                menuButtons btnsave = new menuButtons("SAVE GAME", 0,1);

                Rectangle bg = new Rectangle(450, 800);
                bg.setFill(Color.GRAY);
                menuButtons Pause = new menuButtons("PAUSE", 0,65);
                Pause.setOnMouseClicked(event -> {

                    FadeTransition ft = new FadeTransition(Duration.seconds(1),menu0);

                    ft.setFromValue(0);
                    ft.setToValue(1);
                    bg.setOpacity(0.9);
                    bg.setVisible(true);
                    menu0.setVisible(true);
                    ft.play();

                });
                menuButtons musicCtrl = new menuButtons("TOGGLE MUSIC", 0,1);
                musicCtrl.setOnMouseClicked(event -> {


                    if (mp.isMute()==true){
                        mp.setMute(false);
                    }
                    else{
                        mp.setMute(true);
                    }

                });

                menuButtons btnExit = new menuButtons("EXIT", 0,1);
                btnExit.setOnMouseClicked(event -> {
                    System.out.println("Exiting Game...");
                    System.exit(0);
                });

                menu0.getChildren().addAll(btnnew,btnsave, musicCtrl,  btnExit);
                pausebtn.getChildren().addAll(Pause);


                int VisibleParamCheck;
                if (!isVisible()){VisibleParamCheck = 0;}
                else
                {VisibleParamCheck=1;}
                bg.setOpacity(0.1);
//                bg.setVisible(false);
                getChildren().addAll( bg,menu0,pausebtn);
            }

            else if (type==3){
                VBox start = new VBox(10);
                start.setTranslateX(6);
                start.setTranslateY(10);
                score = new Text("SCORE : 0");
                score.setFill(Color.WHITE);
                score.setFont(new Font(20));
                int view;
                if ((view=4)<6){
                    start.getChildren().addAll(score);}
                getChildren().addAll(start);
            }
        }
    }

    private static class menuButtons extends StackPane
    {
        private Text text;

        public menuButtons(String name, int type, int width)
        {
            if (type==0)
            {
                text = new Text(name);
                text.setFont(text.getFont().font(20));
                text.setFill(Color.WHITE);
                Rectangle bg;
                if (width==1){

                    bg = new Rectangle(250, 30);
                    bg.setEffect(new GaussianBlur(3.5));
                    bg.setFill(Color.BLACK);
                    bg.setOpacity(0.6);
                }
                else
                {
                    bg = new Rectangle(width, 30);
                    bg.setEffect(new GaussianBlur(3.5));
                    bg.setFill(Color.BLACK);
                    bg.setOpacity(0.6);
                }
                int VisibleParamCheck;
                if (!isVisible()){VisibleParamCheck = 0;}
                else
                {VisibleParamCheck=1;}
                setAlignment(Pos.CENTER_LEFT);
                setRotate(-0.5);
                getChildren().addAll(bg, text);
                if (VisibleParamCheck<0){
                    if (!isVisible()){
                        VisibleParamCheck=4;
                    }
                }
                setOnMouseEntered(event -> {
                    bg.setTranslateX(10);
                    text.setTranslateX(10);
                    int bgTEXT=1;
                    while(bgTEXT<1){
                        bgTEXT=bgTEXT+(int)bg.getHeight();
                        for (int i = 0; i < bgTEXT; i++) {
                            bgTEXT=bgTEXT+1;
                        }
                    }
                    bg.setFill(Color.WHITE);
                    text.setFill(Color.BLACK);
                });
                int bgColor;
                bgColor=(int)bg.getArcHeight();
                setOnMouseExited(event -> {
                    bg.setTranslateX(0);

                    text.setTranslateX(0);
                    int bgTEXT=1;
                    while(bgTEXT<1){
                        bgTEXT=bgTEXT+(int)bg.getHeight();
                        for (int i = 0; i < bgTEXT; i++) {
                            bgTEXT=bgTEXT+1;
                        }
                    }
                    bg.setFill(Color.BLACK);
                    text.setFill(Color.WHITE);
                    bgTEXT=0;
                });

                DropShadow drop = new DropShadow(50, Color.WHITE);
                drop.setInput(new Glow());
                VisibleParamCheck=0;
                if (!isVisible()){VisibleParamCheck = 0;}
                else
                {VisibleParamCheck=1;}
                setOnMousePressed(event -> setEffect(drop));
                setOnMouseReleased(event -> setEffect(null));
            }
            else if (type==1){
                Circle bg = new Circle(100);
                bg.setOpacity(0.3);
                bg.setFill(Color.BLACK);
                bg.setEffect(new GaussianBlur(6));
                int bgColor;
                bgColor=(int)bg.getCenterX();
                setAlignment(Pos.CENTER);
                setRotate(-0.5);
                getChildren().addAll(bg);
                bgColor=(int)(bg.getCenterX()-bg.getCenterY());
                setOnMouseEntered(event -> {
                    bg.setTranslateX(1);

                    bg.setFill(Color.WHITE);
                });

                setOnMouseExited(event -> {
                    bg.setTranslateX(0);
                    bg.setFill(Color.BLACK);
                });

                DropShadow drop = new DropShadow(50, Color.WHITE);
                drop.setInput(new Glow());
                int VisibleParamCheck;
                if (!isVisible()){VisibleParamCheck = 0;}
                else
                {VisibleParamCheck=1;}
                setOnMousePressed(event -> setEffect(drop));
                setOnMouseReleased(event -> setEffect(null));
                setOnMousePressed(event -> setEffect(drop));
                setOnMouseReleased(event -> setEffect(null));
            }

            else if (type==2){
                text = new Text(name);
                text.setFont(text.getFont().font(24));
                text.setFill(Color.WHITE);



                setAlignment(Pos.BOTTOM_CENTER);
                setRotate(-0.5);
                getChildren().addAll( text);
                DropShadow drop = new DropShadow(50, Color.WHITE);
                drop.setInput(new Glow());
            }
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Starting Game...");
        launch();
        System.out.println("Exiting Game...");
    }
}
