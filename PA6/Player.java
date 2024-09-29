import java.util.Scanner;
import java.util.List;

class Player { 
    private Inventory playerInventory; 
    private Equipment equippedItems;
    private Money playersMoney;
    private Scanner scanner;

    public Player(double amountOfMoney){
        playerInventory = new Inventory();
        equippedItems = new Equipment();
        playersMoney = new Money(amountOfMoney);
        scanner = new Scanner(System.in);
    }

    public Inventory getPlayerInventory(){
        return playerInventory;
    }

    public Equipment getEquippedItems(){
        return equippedItems;
    }

    public double getPlayerMoney(){
        return playersMoney.getPlayerMoney();
    }

    public void get(double amountToGet){
        playersMoney.get(amountToGet);
    }
    
    public void spend(double amountToSpend){
        playersMoney.spend(amountToSpend);
    }
    
    public void addMoney(double amountToAdd){
        playersMoney.addMoney(amountToAdd);
    }

    public void removeMoney(double amountToRemove){
        playersMoney.removeMoney(amountToRemove);
    }

    @Deprecated
    public void addItem(Item itemToAdd){
        acquire(itemToAdd);
    }

    @Deprecated
    public void removeItem(Item itemToRemove){
        relinquish(itemToRemove);
    }

    public void acquire(Item itemToAcquire){
        playerInventory.acquire(itemToAcquire);
    }

    public void relinquish(Item itemToRelinquish){
        playerInventory.relinquish(itemToRelinquish);
    }

    public Item getItemByName(String itemName){
        return playerInventory.getItemByName(itemName);
    }

    public void drink(Item itemToDrink){
        consume(itemToDrink);
    }

    public void eat(Item itemToEat){
        consume(itemToEat);
    }

    public void wear(Item itemToWear){
        equip(itemToWear);
    }

    public void hold(Item itemToHold){
        equip(itemToHold);
    }

    public void use(Item usable){
        String input = inputForUse().toLowerCase();
        conditionalForUse(input, usable);
    }

    public String inputForUse(){
        System.out.println("Is this item consumable or equippable? \nSelect 1 for consummable and 2 for equippable");
        return scanner.nextLine();
    }

    public void conditionalForUse(String type, Item usable){
        if(type.equals("1")){
            consume(usable);
        }
        else if(type.equals("2")){
            equip(usable);
        }
        else{
            System.out.println("Unable to use item");
        }
    }

    public void consume(Item consumable){ 
        equippedItems.successfullyConsumed(consumable);
        playerInventory.relinquish(consumable);
    }

    public void equip(Item equippable){
        equippedItems.successfullyConsumed(equippable);
        playerInventory.relinquish(equippable);
    }

    public void exposeCommonMethodEquip(Item equippable){
        equip(equippable);
    }

    public void exposeCommonMethodConsume(Item consumable){
        consume(consumable);
    }

    public void exposeCommonMethodUse(Item usable){
        use(usable);
    }

    public List exposeInventory(){
        return playerInventory.getInventory();
    }

    public List exposeWearInventory(){
        return playerInventory.getInventory();
    }

    public List exposeHoldInventory(){
        return playerInventory.getInventory();
    }

    public List exposeEatInventory(){
        return playerInventory.getInventory();
    }

    public List exposeDrinkInventory(){
        return playerInventory.getInventory();
    }

    public List exposeEquipInventory(){
        return equippedItems.getEquippedItems();
    }

    public List exposeConsumeInventory(){
        return equippedItems.getEquippedItems();
    }

    public boolean checkIfPlayerOwnsItem(Item item){
        return playerInventory.checkIfPlayerOwnsItem(item);
    }

    public void escrowBuy(Store store, Item item){
        try{
        if(playersMoney.getPlayerMoney() >= item.getPrice()){
            Escrow.escrowMoney(item.getPrice());
            playersMoney.removeMoney(item.getPrice());
            store.customerBuyUsingEscrow();
            acquire(item);
        }
        else{
            Escrow.receiveItem();
            throw new Exception("Insufficient funds");
        }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void escrowSell(Store store, Item item){
        if(playerInventory.checkIfPlayerOwnsItem(item)){
            store.customerSellUsingEscrow();
            addMoney(Escrow.receiveMoney()); 
            relinquish(item);
        }
        else{
            Escrow.receiveItem();
            System.out.println("Couldn't sell item");
        }
    }
}   