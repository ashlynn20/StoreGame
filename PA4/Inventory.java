import java.util.ArrayList; 
import java.util.List;

class Inventory{
        private List<Object> inventory;
        private Equipment equipped;

        public Inventory(){
            equipped = new Equipment();
            inventory = new ArrayList<>();
        }

        public List getInventory(){
            return inventory;
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
                relinquishEquipped(itemToRelinquish);
                System.out.println(itemToRelinquish.getName() + " relenquished");
                inventory.remove(itemToRelinquish);
            }
        }

        public void relinquishEquipped(Item itemToRelinquish){
            if(equipped.checkIfEquipped(itemToRelinquish)){
                equipped.remove(itemToRelinquish);
            }
        }

        public Item getItemByName(String itemName){ //looks gross
            for(int i = 0; i < inventory.size(); i++){
                if(((Item)inventory.get(i)).getName().equals(itemName)){
                    return (Item)inventory.get(i);
                }
            }
            return null;
        }

        public boolean checkIfPlayerOwnsItem(Item itemToCheck){ 
            return (inventory.contains(itemToCheck));
        }

        public void iterateInventory(){
            for(int i = 0; i < inventory.size(); i++){
                System.out.println(inventory.get(i));
            }
        }

    }