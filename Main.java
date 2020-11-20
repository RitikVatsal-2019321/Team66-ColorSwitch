import javafx.animation.Animation;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.nio.file.Paths;

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

    Ball(Stage stage)
    {
        this.setColour(r.nextInt(4));

        circle.setRadius(15);
        circle.setTranslateX(225);
        circle.setTranslateY(700);
        circle.setFill(Color.WHITE);

        getChildren().addAll(circle);
    }

    public void switchColour()
    {

    }
    public void jump()
    {

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
        imgvu.setFitHeight(600);
        imgvu.setFitWidth(450);

        getChildren().addAll(imgvu);
        this.movement();
    }

    public void movement()
    {
        RotateTransition rt = new RotateTransition(Duration.millis(5000),imgvu);
        rt.setByAngle(360);
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

    Star()
    {
        s1.setTranslateX(205);
        s1.setTranslateY(20);
        s1.setFitHeight(40);
        s1.setFitWidth(40);
        s1.setEffect(new GaussianBlur(3));

        getChildren().addAll(s1);
    }
}

class ColourSwitcher extends Colours
{
    protected int nextColour = -1;
    protected Image switcher1 = new Image("file:///C:/Resources/2.png");
    protected ImageView sw1 = new ImageView(switcher1);

    ColourSwitcher(Ball b)
    {
        randomColour(b);

        sw1.setTranslateX(205);
        sw1.setTranslateY(550);
        sw1.setFitHeight(40);
        sw1.setFitWidth(40);

        getChildren().addAll(sw1);
    }

    protected void randomColour(Ball b)
    {
        while(nextColour==b.currColour || nextColour==-1)
        {
            nextColour = r.nextInt(4);
        }
    }
}

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
        ColourSwitcher cs1 = new ColourSwitcher(ball);
        top.getChildren().addAll(imgvu,ball,o1,s1,cs1);
        int view;
        if ((view=4)<6){
            stage.setScene(scene2);
            stage.show();
        }
    }
}

public class Main extends Application
{


    MediaPlayer mp;
    private GameMenu menu;
    private GameMenu gameMenu;
    private GameMenu splash;


    public void BGmusic(int status) {
        Media bgMusic;
        bgMusic = new Media("file:///C:/Resources/bg.mp3");
        mp =new MediaPlayer(bgMusic);
        if (status==1)mp.play();
        if (status==0)mp.pause();

    }

    @Override
    public void start(Stage stage) throws Exception
    {
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

        BGmusic(1);
        gameMenu = new GameMenu(stage,0);
        splash = new GameMenu(stage,1);
        gameMenu.setVisible(false);
        top.getChildren().addAll(imgvu,gameMenu,splash);

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
        stage.setScene(scene1);
        stage.centerOnScreen();
    }

    private class GameMenu extends Parent
    {
        private final int obst=4;
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
