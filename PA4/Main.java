import java.util.Scanner; 

public class Main {

    public static void main(String[] args) {
        Item sword = new Item("Sword", 10.0);
        Item potion = new Item("Health Potion", 5.0);
        Item hat = new Item("Hat", 1);
        Store store = new Store();
        Player player = new Player(100.0);

        store.addItem(sword);
        store.addItem(potion);
        store.addItem(hat);

        store.displayInventory();

        Scanner scanner = new Scanner(System.in);
        enterStorePromt(store, player, scanner);
    }

    public void exposeGameSetup(Store store, Player player, Scanner scanner){
        enterStorePromt(store, player, scanner);
    }

    public void exposeGamePlay(Scanner scanner, Store store, Player player){
        storeMenu(scanner, store, player);
    }

    public void exposeGameStop(Store store, Player player){
        leaveStore(store, player);
    }

    public static void enterStorePromt(Store store, Player player, Scanner scanner){
        while (true) {
            System.out.println("\nEnter a command (1 to enter the store, 4 to exit):");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                storePromptForInputOne(store, player, scanner);
            } else if (input.equals("4")) {
                storePromptForInputFour(store, player);
                break;
            } else {
                storePromptForInvalidInput(store, player);
            }
        }
        scanner.close();
    }

    public static void storePromptForInputOne(Store store, Player player, Scanner scanner){
        enterStore(store, player);
        storeMenu(scanner, store, player);
    }

    public static void enterStore(Store storeToEnter, Player player){
        storeToEnter.enter(player);
    }

    public static void storePromptForInputFour(Store store, Player player){
        System.out.println("Exiting the program...");
    }

    public static void storePromptForInvalidInput(Store store, Player player){
        System.out.println("Invalid command!");
    }

    public static void storeMenu(Scanner scanner, Store store, Player player) {
        while (true) {
            storeCapabilities();
            String input = scanner.nextLine();
            if (input.equals("1")) {
                buyAnItem(store, player, scanner);
            } else if (input.equals("2")) {
                sellAnItem(store, player, scanner);
            } else if (input.equals("3")) {
                store.displayInventory();
            } else if (input.equals("4")) {
                leaveStore(store, player);
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static void storeCapabilities(){
        System.out.println("\nStore Menu:");
        System.out.println("1. Buy an item");
        System.out.println("2. Sell an item");
        System.out.println("3. Display inventory");
        System.out.println("4. Exit store");
    }

    public static void buyAnItem(Store storeBuyingFrom, Player player, Scanner scanner){
        storeBuyingFrom.displayInventory();
        System.out.println("Enter the name of the item you want to buy:");
        String itemName = scanner.nextLine();
        Item item = storeBuyingFrom.getItemByName(itemName);
        itemAvailability(item, storeBuyingFrom, player);
    }

    public static void itemAvailability(Item itemCheck, Store storeBuyingFrom, Player player){
        if(itemNullCheck(itemCheck)){
            conditionToBuyItem(itemCheck, storeBuyingFrom, player);
        }
        else{
            System.out.println("Item not available in the store.");
        }
    }

    public static void conditionToBuyItem(Item itemToBuy, Store storeBuyingFrom, Player player){
        storeBuyingFrom.buyItem(itemToBuy, player);
        if(player.checkIfPlayerOwnsItem(itemToBuy)){
            System.out.println("Item purchased successfully!");    
        }
        else{
            System.out.println("Could not purchase the desired item.");
        }
    }

    public static void sellAnItem(Store storeSellingTo, Player player, Scanner scanner){
        System.out.println("Enter the name of the item you want to sell:");
        String itemName = scanner.nextLine();
        Item item = player.getItemByName(itemName); 
        conditionToSellItem(item, storeSellingTo, player);
    }

    public static void conditionToSellItem(Item itemToSell, Store storeSellingTo, Player player){
        if (itemNullCheck(itemToSell)) {
            storeSellingTo.sellItem(itemToSell, player);
            System.out.println("Item sold successfully!");
        } 
        else {
            System.out.println("Item not found in your inventory.");
        }
    }

    public static boolean itemNullCheck(Item itemToCheck){
        return (itemToCheck != null);
    }

    public static void leaveStore(Store storeToLeave, Player player){
        System.out.println("Exiting the store...");
        storeToLeave.exit(player);
    }
}