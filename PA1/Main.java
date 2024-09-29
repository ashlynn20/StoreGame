import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Player {
    private List<Item> playerInventory;
    private List<Item> equippedItems;
    private double playerMoney;

    public Player(double playerMoney){
        this.playerMoney = playerMoney;
        playerInventory = new ArrayList<>();
        equippedItems = new ArrayList<>();
    }

    public double getPlayerMoney(){
        return playerMoney;
    }

    public boolean removeMoney(double amountToRemove){
        if(getPlayerMoney() - amountToRemove < 0 || amountToRemove <= 0){
            return false;
        }
        playerMoney -= amountToRemove;
        return true;
    }

    public void addMoney(double amountToAdd){
        playerMoney += amountToAdd;
    }

    public void addItem(Item itemToAdd){
        playerInventory.add(itemToAdd);
    }

    public void removeItem(Item itemToRemove){
        playerInventory.remove(itemToRemove);
    }

    public Item getItemByName(String itemName){
        for(int i = 0; i < playerInventory.size(); i++){
            if(playerInventory.get(i).getName().equals(itemName)){
                return playerInventory.get(i);
            }
        }
        return null;
    }

    public void drink(Item itemToDrink){
        actionForConsumableItems(itemToDrink, "drink", "drank");
    }

    public void eat(Item itemToEat){
        actionForConsumableItems(itemToEat, "eat", "ate");
    }

    public void wear(Item itemToWear){
        actionForNonConsumableItems(itemToWear, "wearing", "wear");
    }

    public void hold(Item itemToHold){
        actionForNonConsumableItems(itemToHold, "holding", "hold");
    }

    public void actionForConsumableItems(Item consumableItem, String presentTense, String pastTense){
        if(checkIfPlayerOwnsItem(consumableItem) && consumableItem != null){
            System.out.println("Player " + pastTense + " " + consumableItem.getName());
            playerInventory.remove(consumableItem);
        }
        else{
            System.out.println("Failed to " + presentTense + " item: " + consumableItem.getName());
        }
    }

    public void actionForNonConsumableItems(Item nonConsumableItem, String actionWithParticle, String presentAction){
        if(!checkIfPlayerOwnsItem(nonConsumableItem) || nonConsumableItem == null){
            System.out.println("Failed to " + presentAction + " item: " + nonConsumableItem.getName());
        }
        else if(checkIfItemIsEquipped(nonConsumableItem)){
            System.out.println("Already " + actionWithParticle + " item " + nonConsumableItem.getName());
        }
        else{
            System.out.println("Player is " + actionWithParticle + " " + nonConsumableItem.getName());
        }
    }

    public void equipItem(Item eqippableItem){
        if(!checkIfPlayerOwnsItem(eqippableItem) || checkIfItemIsEquipped(eqippableItem)){
            System.out.println("Unable to equip");
        }
        else{
            equippedItems.add(eqippableItem);
            System.out.println("Equipped " + eqippableItem.getName());
        }
    }

    public boolean checkIfItemIsEquipped(Item itemToCheck){
        if(equippedItems.contains(itemToCheck)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkIfPlayerOwnsItem(Item itemToCheck){ 
        if(playerInventory.contains(itemToCheck)){
            return true;
        }
        return false;
    }

}     

class Store {
    private List<Item> inventory;
    private List<Player> players_in_store; 

    public Store() {
        inventory = new ArrayList<>();
        players_in_store = new ArrayList<>();
    }

    public void enter(Player player){
        if (check_player_in_store(player) == false){
            players_in_store.add(player);
        } else {
            System.out.println("Player is already in the store.");
        }
    }

    public void exit(Player player){
         if (check_player_in_store(player) == true){
            players_in_store.remove(player);
        } else {
            System.out.println("Player never entered the store.");
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void displayInventory() {
        System.out.println("Store Inventory:");
        for (Item item : inventory) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    private boolean check_player_in_store(Player player){
        int index =  players_in_store.indexOf(player);
        if (index == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Item getItemByName(String name) {
        // Iterate through the player's items and return the item with the matching name
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null; // Item not found in the player's inventory
    }

    public boolean buyItem(Item item, Player player) {
        if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to buy anything");
            return false;
        }
        
        if (inventory.contains(item)) {
            if (player.removeMoney(item.getPrice())){
                inventory.remove(item);
                player.addItem(item);
                return true;
            }
        } else {
            System.out.println("Item not available in the store.");
        }
        return false;
    }

    public boolean sellItem(Item item, Player player) {
         if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to sell anything");
            return false;
        }
        player.removeItem(item);
        player.addMoney(item.getPrice());
        inventory.add(item);
        return true;
    }

}

public class Main {
    public static void main(String[] args) {
        Item sword = new Item("Sword", 10.0);
        Item potion = new Item("Health Potion", 5.0);
        Item hat = new Item("Hat", 1);

        Store store = new Store();
        store.addItem(sword);
        store.addItem(potion);
        store.addItem(hat);

        Player player = new Player(100.0);

        store.displayInventory();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter a command (1 to enter the store, 4 to exit):");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                store.enter(player);
                storeMenu(scanner, store, player);
                store.exit(player);
            } else if (input.equals("4")) {
                System.out.println("Exiting the program...");
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }

        scanner.close();
    }

    public static void storeMenu(Scanner scanner, Store store, Player player) {
        while (true) {
            System.out.println("\nStore Menu:");
            System.out.println("1. Buy an item");
            System.out.println("2. Sell an item");
            System.out.println("3. Display inventory");
            System.out.println("4. Exit store");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                store.displayInventory();
                System.out.println("Enter the name of the item you want to buy:");
                String itemName = scanner.nextLine();
                Item item = store.getItemByName(itemName);
                if (item != null) {
                    if (store.buyItem(item, player)){
                        System.out.println("Item purchased successfully!");
                    } else {
                        System.out.println("Could not purchase the desired item.");
                    }
                } else {
                    System.out.println("Item not available in the store.");
                }
            } else if (input.equals("2")) {
                System.out.println("Enter the name of the item you want to sell:");
                String itemName = scanner.nextLine();
                Item item = player.getItemByName(itemName); 
                if (item != null) {
                    store.sellItem(item, player);
                    System.out.println("Item sold successfully!");
                } else {
                    System.out.println("Item not found in your inventory.");
                }
            } else if (input.equals("3")) {
                store.displayInventory();
            } else if (input.equals("4")) {
                System.out.println("Exiting the store...");
                store.exit(player);
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
}