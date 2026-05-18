package serviceui;
import java.util.Scanner;

public class HeroUI {
    private Scanner scan = new Scanner(System.in);
    private ManagerUIMain managerUI = new ManagerUIMain();
    private UserUIMain userUI = new UserUIMain();
    public void displayHeroUI(){
        heroLoop:
        while (true) {    
        System.out.println("----> WELCOME TO OUR RESTURANT MENU <----");
        System.out.println("\n0) Exit.");
        System.out.println("1) Resturant Manager.");
        System.out.println("2) User.");
        System.out.print("Enter the option you want: ");
        
        int option = scan.nextInt();
        scan.nextLine();
        
        switch (option) {
            case 0:
                break heroLoop;
            case 1:   
                managerUI.managerMainUi();
                break;
            case 2:
                userUI.userMainUI();
                break;
                default:
                    System.out.println("\n Invalid input \n");
                    break;
            }
        }
    }
}

