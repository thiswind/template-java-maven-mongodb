
public class Main {
    public static void main(String[] args) {
        Init init=new Init();
        init.initializeDatabase();
        Menu menu=new Menu();
        menu.selectRole();
    }
}