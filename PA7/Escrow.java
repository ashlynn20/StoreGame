
public class Escrow {

    private static Item itemEscrowed;
    private static double moneyEscrow;

    public static void escrowItem(Item item){
        itemEscrowed = item;
    }
    
    public static void escrowMoney(double money){
        moneyEscrow += money;
    }

    public static void requestItem(Item item){
        itemEscrowed = item;
    }

    public static double receiveMoney(){
        double tempMoney = moneyEscrow;
        moneyEscrow = 0;
        return tempMoney;
    }

    public static Item receiveItem(){
        Item tempItem = itemEscrowed;
        itemEscrowed = null;
        return tempItem;
    }

    public static double returnMoney(){
        return moneyEscrow;
    }

    public static Item getItem(){
        return itemEscrowed;
    }

}
