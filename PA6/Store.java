import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Store {
    private List<Item> inventory;
    private List<Player> players_in_store; 
    public static Logger logger = LogManager.getLogger(Store.class);  

    public Store() {
        inventory = new ArrayList<>();
        players_in_store = new ArrayList<>();
    }

    public List<Item> getInventory(){
        return inventory;
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
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void buyItem(Item item, Player player) {

        logger.info("INFO!!");

        if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to buy anything");
        }
        if (inventory.contains(item)) {
            if (item.getPrice() <= player.getPlayerMoney()){
                player.removeMoney(item.getPrice());
                inventory.remove(item);
                player.acquire(item);
                //.addItem(item);
            }
        } else {
            System.out.println("Item not available in the store.");
        }
    }
    
    public void sellItem(Item item, Player player){
        // if (check_player_in_store(player) == false){
        //     System.out.println("Player needs to enter the store before being able to sell anything");
        // }
        // else{
        //     player.escrowSell(this, item);
        // }
        if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to sell anything");
        }
        player.relinquish(item);
        //player.removeItem(item);
        player.addMoney(item.getPrice());
        inventory.add(item);

    }

    private void buyUsingEscrow(){
        Item itemToBuy = Escrow.getItem();
        inventory.add(itemToBuy);
        Escrow.escrowMoney(itemToBuy.getPrice());
        Escrow.receiveItem();
    }

    private void sellUsingEscrow() throws Exception{
        Item itemToSell = Escrow.getItem();
        if(itemToSell == null){
            throw new Exception("Item is null");
        }
        double cost = itemToSell.getPrice();
        if(inventory.contains(itemToSell)){
            if(cost <= Escrow.returnMoney()){
                Escrow.receiveMoney();
                finalizeEscrow();
            }
        }
        else{
            Escrow.receiveItem();
            throw new Exception("Store doesn't have item");
        }
    }

    public void finalizeEscrow(){
        inventory.remove(Escrow.getItem());

        logger.info("INFO!!");
    }

    public void customerBuyUsingEscrow() throws Exception{
        sellUsingEscrow();
    }

    public void customerSellUsingEscrow(){
        buyUsingEscrow();
    }

}