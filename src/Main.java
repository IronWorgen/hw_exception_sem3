import controller.MainController;
import model.Model;
import view.View;

public class Main {
    public static void main(String[] args) {


        MainController controller = new MainController(new View(), new Model());
        controller.run();


    }
}