package serviceui;

import java.util.*;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;

public class UserUIMain {
    private Scanner scan = new Scanner(System.in);
    private UserDAO userDAO = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();

    public void userMainUI(){
        userMainUILoop:
        while(true){
            System.out.println("\n------> User Menu <-----\n");
            System.out.println("0) Exit ");
            System.out.println("1) User food terminal ");
            System.out.print("Enter the option: ");
            int option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 0:
                    break userMainUILoop;
                case 1:
                    try{
                    System.out.print("Enter user id: ");
                    int id = scan.nextInt();
                    scan.nextLine();

                    User user = userDAO.getUserFromId(id);
                    
                    dedicatedUser(user);
                    
                    }catch(NullPointerException e){
                        System.out.println(e.getMessage());
                    }
                     
                default:
                    break;
            }
        }
    }

    public void dedicatedUser(User user){
        System.out.println("HELLO "+user.getName()+"\n");
        System.out.println("\n How may I serve you today? ");
        
        deciatedUserLoop:
        while(true){
            System.out.println("---> Food Panda Terminal <---");
            System.out.println("\n0) To exit from user: "+user.getName());
            System.out.println("1) Show all the menu item. ");
            System.out.println("2) Order Food items. ");
            System.out.println("3) Show ordered items based on id. ");
            System.out.println("4) Show all the ordered items. ");
            System.out.print("Enter the option: ");
            int opt = scan.nextInt();
            scan.nextLine();

            switch (opt) {
                case 0:
                    break deciatedUserLoop;
                case 1:
                    displayAllMenu();
                    break;
                case 2:
                    createOrderForUser(user);
                    break;
                case 3:
                    break;
                case 4:
                    displayAllOrdersByTheUser(user);
                    break;
                default:
                    break;
            }

        }
    }

    public void displayAllMenu(){
        System.out.println("WELCOME TO FOOD PANDA MENU");
        
        List<Product> products = new ArrayList<>(productDAO.getAllProducts());

        for(Product product:  products){
            System.out.println(product);
        }
    }

    public void createOrderForUser(User user){
        orderDAO.createOrder(user);
        List<Order> orders = new ArrayList<>(orderDAO.getOrderById(user));

        // gets the latest order;
        int size = orders.size() - 1 ;
        try{
            Order order = orders.get(size);
            while(true){
                displayAllMenu();

                System.out.print("\n Enter the id of the food you want to add: ");
                int foodId = scan.nextInt();
                scan.nextLine();
                Product product = productDAO.getProductById(foodId);
                
                System.out.print("\nEnter the quntity of the food item you want to add: ");
                int quntity = scan.nextInt();
                scan.nextLine();

                orderDAO.addProductInOrderItem(product, quntity, order);

                System.out.print("\nAre you done ordering? (y/n) ");
                String input = scan.nextLine();

                if(input.equalsIgnoreCase("y") == true){
                    System.out.println("Thank you for ordering.");
                    break;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void displayAllOrdersByTheUser(User user){
       List<Order> orders = new ArrayList<>(orderDAO.getOrderById(user));
        
       if(orders.size() != 0){
            for(Order order : orders){
                List<String> orderList = orderDAO.getUsersOrderForResManager(user, order);
                if(orderList.size() != 0){
                    for(String orderListResult:orderList){
                        System.out.println(orderListResult);
                    }
                }else{
                    System.out.println("Empty no orders made yet.");
                }
            }     
        }else{
            System.out.println("No orders made yet.");
        }
        
    }
}
