import java.util.ArrayList;
import java.util.List;

class Player {
    private Inventory playerInventory; 
    private List<Item> equippedItems;
    private Money playersMoney;

    class Money{
        private double playerMoney;

        public Money(double playerMoney){
            this.playerMoney = playerMoney;
        }

        public double getPlayerMoney(){
            return playerMoney;
        }

        public void get(double amountToGet){
            addMoney(amountToGet);
        }

        public void spend(double amountToSpend){
            removeMoney(amountToSpend);
        }

        public void addMoney(double amountToAdd){
            playerMoney += amountToAdd;
        }

        public boolean removeMoney(double amountToRemove){
            if(getPlayerMoney() - amountToRemove < 0 || amountToRemove <= 0){
                return false;
            }
            playerMoney -= amountToRemove;
            return true;
        }

    }

    class Inventory{
        private List<Object> inventory;

        public Inventory(){
            inventory = new ArrayList<>();
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
            if(itemToAcquire == null){
                System.out.println("Can't acquire item");
            }
            else{
                System.out.println(itemToAcquire.getName() + " acquired");
                inventory.add(itemToAcquire);
            }
        }

        public void relinquish(Item itemToRelinquish){
            if(!checkIfPlayerOwnsItem(itemToRelinquish) || itemToRelinquish == null){
                System.out.println("Can't relenquish item");
            }
            else{
                if(checkIfItemIsEquipped(itemToRelinquish)){
                    equippedItems.remove(itemToRelinquish);
                }
                System.out.println(itemToRelinquish.getName() + " relenquished");
                inventory.remove(itemToRelinquish);
            }
        }

        public Item getItemByName(String itemName){
            for(int i = 0; i < inventory.size(); i++){
                if(((Item)inventory.get(i)).getName().equals(itemName)){
                    return (Item)inventory.get(i);
                }
            }
            return null;
        }

    }

    public Player(double amountOfMoney){
        playerInventory = new Inventory();
        equippedItems = new ArrayList<>();
        playersMoney = new Money(amountOfMoney);
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

    public boolean removeMoney(double amountToRemove){
        return playersMoney.removeMoney(amountToRemove);
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

    public void consume(Item consumableItem){
        if(checkIfPlayerOwnsItem(consumableItem) && consumableItem != null){
            System.out.println("Player consumed " + consumableItem.getName());
            playerInventory.removeItem(consumableItem);
        }
        else{
            System.out.println("Failed to consume item: " + consumableItem.getName());
        }
    }

    public void equip(Item nonConsumableItem){
        if(!checkIfPlayerOwnsItem(nonConsumableItem) || nonConsumableItem == null){
            System.out.println("Failed to equip item: " + nonConsumableItem.getName());
        }
        else if(checkIfItemIsEquipped(nonConsumableItem)){
            System.out.println("Already equipped item " + nonConsumableItem.getName());
        }
        else{
            System.out.println("Player equipped " + nonConsumableItem.getName());
            equippedItems.add(nonConsumableItem);
        }
    }

    public boolean checkIfPlayerOwnsItem(Item itemToCheck){ 
        if(playerInventory.inventory.contains(itemToCheck)){
            return true;
        }
        return false;
    }

    public boolean checkIfItemIsEquipped(Item itemToCheck){
        if(equippedItems.contains(itemToCheck)){
            return true;
        }
        else{
            return false;
        }
    }

}     
