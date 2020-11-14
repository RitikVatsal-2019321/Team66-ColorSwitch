import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    private GameMenu menu;
    private GameMenu gameMenu;
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.show();
        stage.setTitle("Color Switch Professional V_1.0.2");
//        stage.setHeight(800);
//        stage.setWidth(450);

        Pane top = new Pane();

        Label label1=new Label("Epic Gamer Moment");
        top.setPrefSize(450, 800);
        Scene scene1=new Scene(top);
        scene1.setCursor(Cursor.CROSSHAIR);
        Image bgHS1 = new Image("file:///C:/Resources/hs1.png");
        ImageView imgvu=new ImageView(bgHS1);
        imgvu.setFitHeight(800);
        imgvu.setFitWidth(450);
        gameMenu = new GameMenu();
        gameMenu.setVisible(false);
        top.getChildren().addAll(  imgvu, gameMenu);

        FadeTransition ft = new FadeTransition(Duration.seconds(3), gameMenu);
        ft.setFromValue(0);
        ft.setToValue(1);
        gameMenu.setVisible(true);
        ft.play();
        stage.setScene(scene1);
    }


    private class GameMenu extends Parent {
        public GameMenu() {
            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menuPlay = new VBox(10);

            menu0.setTranslateX(100);
            menu0.setTranslateY(630);

            menu1.setTranslateX(100);
            menu1.setTranslateY(630);

            menuPlay.setTranslateX(127);
            menuPlay.setTranslateY(306);

            final int offset = 400;

            menu1.setTranslateX(offset);

            menuButtons btnnew = new menuButtons("NEW GAME");
            btnnew.setOnMouseClicked(event -> {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt -> setVisible(false));
                ft.play();
            });


            menuButtons btnCircle = new menuButtons();
            btnCircle.setOnMouseClicked(event -> {
//                FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
//                ft.setFromValue(1);
//                ft.setToValue(0);
//                ft.setOnFinished(evt -> setVisible(false));
//                ft.play();
            });

            menuButtons btnOptions = new menuButtons("LOAD GAME");
            btnOptions.setOnMouseClicked(event -> {
                getChildren().add(menu1);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });

            menuButtons btnExit = new menuButtons("EXIT");
            btnExit.setOnMouseClicked(event -> {
                System.exit(0);
            });

            menuButtons btnBack = new menuButtons("< BACK");
            btnBack.setOnMouseClicked(event -> {
                getChildren().add(menu0);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu1);
                });
            });

            menuButtons btnSound = new menuButtons("SAVE1 : 2 PTS");
            menuButtons btnVideo = new menuButtons("SAVE2 : 7 PTS");

            menu0.getChildren().addAll( btnnew, btnOptions, btnExit );
            menu1.getChildren().addAll( btnBack, btnSound, btnVideo );
            menuPlay.getChildren().addAll(btnCircle);

            Rectangle bg = new Rectangle(450, 800);
            bg.setFill(Color.GRAY);
            bg.setOpacity(0.3);

            getChildren().addAll(bg, menu0, menuPlay);
        }
    }    
    
    private static class menuButtons extends StackPane{
        private Text text;

        public menuButtons(String name){
            text=new Text(name);
            text.setFont(text.getFont().font(20));
            text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(250, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));

            setAlignment(Pos.CENTER_LEFT);
            setRotate(-0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
        }

        public menuButtons(){


            Circle bg = new Circle(100);
            bg.setOpacity(0.3);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(6));

            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg);

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

            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
        }
    }

    public static void main(String[] args) {
        System.out.println("Bruh");
        launch();
    }

}
