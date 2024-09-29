import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Item> inventory;
    private List<Player> players_in_store; 

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

    public void buyItem(Item item, Player player) throws Exception{
        if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to buy anything");
        }
        else{
            player.escrowBuy(this, item);
        }
    }

    public void sellItem(Item item, Player player){
        if (check_player_in_store(player) == false){
            System.out.println("Player needs to enter the store before being able to sell anything");
        }
        else{
            player.escrowSell(this, item);
        }

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
    }

    public void customerBuyUsingEscrow() throws Exception{
        sellUsingEscrow();
    }

    public void customerSellUsingEscrow(){
        buyUsingEscrow();
    }

}