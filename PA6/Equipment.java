import java.util.ArrayList; 
import java.util.List;

public class Equipment {
    private List<Item> equippedItems;

    public Equipment(){
        equippedItems = new ArrayList<>();
    }

    public List getEquippedItems(){
        return equippedItems;
    }

    public void successfullyEquipped(Item equippable){
        System.out.println("Player equipped " + equippable.getName());
        equippedItems.add(equippable);
    }

    public void alreadyEquipped(Item equippable){
        System.out.println("Already equipped item " + equippable.getName());
    }

    public void equipUnsuccessful(Item equippable){
        System.out.println("Failed to equip item: " + equippable.getName());
    }

    public void successfullyConsumed(Item consumable){
        System.out.println("Player consumed " + consumable.getName());
        equippedItems.remove(consumable); 
    }

    public void unsuccessfullyConsumed(Item consumable){
        System.out.println("Failed to consume item: " + consumable.getName());
    }

    public boolean checkIfEquipped(Item itemToCheck){
        return equippedItems.contains(itemToCheck);
    }

    public void remove(Item itemToRemove){
        equippedItems.remove(itemToRemove);
    }

    public void iterateEquippedItems(){
        for(int i = 0; i < equippedItems.size(); i++){
            System.out.println(equippedItems.get(i));
        }
    }
}
