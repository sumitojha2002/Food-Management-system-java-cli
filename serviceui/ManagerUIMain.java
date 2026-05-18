package serviceui;

import java.util.*;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import model.Order;
import model.Product;
import model.User;

 class ManagerUIMain {
    private Scanner scan = new Scanner(System.in);
    private ProductDAO productDAO = new ProductDAO();
    private UserDAO userDAO = new UserDAO();
    private Product product;
    private OrderDAO orderDAO = new OrderDAO();
    
    public void managerMainUi(){
        managerMainUi:
        while(true){
            System.out.println("\n Welcome to the menu of the mangerUI");
            System.out.println("\n0) Exit.");
            System.out.println("1) Add food items.");
            System.out.println("2) Update the price of food items.");
            System.out.println("3) Display all the food items.");
            System.out.println("4) Delete the food items.");
            System.out.println("5) Display all users.");
            System.out.println("6) Display user order.");
            System.out.print("\nEnter the option: ");
            int option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 0:
                    break managerMainUi;
                case 1:
                    addItemInMenu();
                    break;
                case 2:
                    updateThePriceOfFoodItem();
                    break;
                case 3: 
                    displayAllFoodItems();
                    break;
                case 4:
                    deleteFoodItem();
                    break;
                case 5:
                    displayAllUser();
                    break;
                case 6:
                    showUserOrder();
                    break;
                default:
                    System.out.println("\nInvalid input!\n");
                    break;
            }
        }
    }

    // add items in the menu.
    public void addItemInMenu(){
        System.out.print("\nEnter the name of the food: ");

        String nameOfFood = scan.nextLine();

        System.out.print("Enter the price of the food: ");

        double priceOfFood = scan.nextDouble();
        scan.nextLine();
        
        try{
            product = new Product(nameOfFood,priceOfFood);
            productDAO.addProduct(product);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // update the price of the food item
    public void updateThePriceOfFoodItem(){
        System.out.print("Enter the id of the food item: ");

        int idOfFood = scan.nextInt();
        scan.nextLine();

        System.out.println("Enter the price of the food: ");

        double price = scan.nextDouble();
        scan.nextLine();
        
        // void and displays for added or failed cases.
        productDAO.updateProduct(idOfFood, price);
    }

    // displaying all food items
    public void displayAllFoodItems(){
        List<Product> products = new ArrayList<>(productDAO.getAllProducts());

        if(products.size() != 0){
            System.out.println("\n ALL the Items in the menu are: ");
            for(Product product : products){
                System.out.println(product);
            }
        }else{
            System.out.println("\n There are no items added yet.");
        }
    }

    // deleting food items
    public void deleteFoodItem(){
        System.out.print("Enter the id of the item you want to delete: ");
        int idToDelItem = scan.nextInt();
        productDAO.deleteProductById(idToDelItem);
    }

    // display all users
    public void displayAllUser(){
        List<User>userlist = new ArrayList<>(userDAO.getAllUser());

        if(userlist.size() != 0){
            System.out.println("All the users present are: ");
            for(User user: userlist){
                System.out.println(user);
            }
        }else{
            System.out.println("No users found!!!");
        }
    }

    // display user order
    public void showUserOrder(){
        System.out.print("\nEnter the id of the user: ");
        int userId = scan.nextInt();
        scan.nextLine();
        User user = userDAO.getUserFromId(userId);
        System.out.println(user);
        if(user != null){
            List<Order> orders= new ArrayList<>(orderDAO.getOrderById(user));
            if(orders.size() != 0){
                for(Order order: orders){
                    List<String> userOrderList = new ArrayList<>(orderDAO.getUsersOrderForResManager(user, order));
                    for(String uoList : userOrderList){
                        System.out.println(uoList);
                    }
                    
                }
            }else{
                System.out.println("no orders made yet!!!");
            }
        }else{
            System.out.println("no user exist!");
        }
    }
}
