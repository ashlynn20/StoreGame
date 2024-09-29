import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestSetJunit4 {

  static Store store;
  static Player player;
  static Item item0;
  static Item item1; 
  static Item item2;

  @Before 
  public void setup() {
    store = new Store();

    item0 = new Item("test0", 1.0);
    item1 = new Item("test1", 1.0);
    item2 = new Item("test2", 1.0); 


    player = new Player(100.0);

    store.addItem(item0);
    store.addItem(item1);
    store.addItem(item2);
  }

  @Test
  public void testAquire() {

    Item acquiredItem = null;
  
    store.enter(player);

    store.buyItem(item0, player);
    store.buyItem(item1, player);
    store.buyItem(item2, player);
    player.acquire(acquiredItem);

    assertSame(item0, player.getItemByName("test0"));
    assertSame(item1, player.getItemByName("test1"));
    assertSame(item2, player.getItemByName("test2"));
    assertFalse(player.exposeInventory().contains(acquiredItem));
  }

  

  @Test
  public void testPlayerCanSell() {
    Store store = new Store();
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    Item item2 = new Item("player_item2", 1.0);

    player.acquire(item);
    player.acquire(item2);
    assertSame(item, player.getItemByName("player_item"));
    store.enter(player);
    store.sellItem(item, player);
    assertNull(player.getItemByName("player_item"));
    assertSame(item, store.getItemByName("player_item"));
    assertTrue(store.check_player_in_store(player));
    store.exit(player);
    store.sellItem(item2, player);
    assertFalse(store.check_player_in_store(player));
    assertNull(store.getItemByName("player_item2"));
  }

  @Test
  public void testRelinquish(){
    Item relinquishedItem = new Item("relinquished", 1.0);

    store.enter(player);
    store.sellItem(item0, player);
    store.sellItem(item1, player);
    player.acquire(relinquishedItem);
    player.relinquish(relinquishedItem);

    assertNull(player.getItemByName("test0"));
    assertNull(player.getItemByName("test1"));
    assertNull(player.getItemByName("relinquished"));
    
  }

  @Test
  public void testBuyItem(){  
    Store store = new Store();
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    Item item2 = new Item("player_item2", 1.0);
    Item exspensiveItem = new Item("expensive_item", 110.0);

    store.enter(player);
    store.addItem(item);
    store.addItem(item2);
    store.addItem(exspensiveItem);
    store.buyItem(item, player);
    store.buyItem(exspensiveItem, player);
    store.exit(player);
    store.buyItem(item2, player);

    assertNull(store.getItemByName("player_item"));
    assertTrue(player.checkIfPlayerOwnsItem(item));
    assertFalse(store.check_player_in_store(player));
    assertNull(player.getItemByName("expensive_item"));
    assertNull(player.getItemByName("player_item2"));

  }

  @Test 
  public void testMoney(){
    Player player = new Player(100.0);
    double pMoney = player.getPlayerMoney();

    assertTrue(pMoney == player.getPlayerMoney());
    player.removeMoney(item0.getPrice());
    assertTrue(player.getPlayerMoney() == pMoney - item0.getPrice());
  }

  @Test
  public void testCheck_player_in_store(){
    assertFalse(store.check_player_in_store(player));
    store.enter(player);
    assertTrue(store.check_player_in_store(player));
  }

  @Test 
  public void testPlayerGetItemByName(){
    store.enter(player);
    player.acquire(item0);
    player.acquire(item1);

    assertSame(item0, player.getItemByName("test0"));
    assertNotEquals(item1, player.getItemByName("test0"));
  }

  @Test 
  public void testStoreGetItemByName(){
    assertSame(item0, store.getItemByName("test0"));
    assertNull(store.getItemByName("itemx"));
  }

  @Test
  public void testBuyUsingEscrow(){
    Store store = new Store();
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    Item item2 = new Item("player_item2", 110.0);

    store.enter(player);
    store.addItem(item);
    store.addItem(item2);

    Escrow.escrowItem(item);
    player.escrowBuy(store, item);
    assertTrue(player.checkIfPlayerOwnsItem(item));
    
    Escrow.escrowItem(item2);
    player.escrowBuy(store, item2);
    assertNull(player.getItemByName("player_item2"));
  }

  @Test
  public void testCustomerBuyUsingEscrow(){
    Store store = new Store();
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    Item item2 = new Item("player_item2", 110.0);
    Item item3 = new Item("player_item3", 1.0);

    store.enter(player);
    store.addItem(item);
    store.addItem(item2);

    Escrow.escrowItem(item);
    Escrow.escrowMoney(item.getPrice());
    try{
      store.customerBuyUsingEscrow();
      player.acquire(item);
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
    assertTrue(player.checkIfPlayerOwnsItem(item));

    Escrow.escrowItem(item2);
    Escrow.escrowMoney(item2.getPrice());
    try{
      store.customerBuyUsingEscrow();
      player.acquire(item2);
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
    assertFalse(player.checkIfPlayerOwnsItem(item2));

    Escrow.escrowItem(item3);
    Escrow.escrowMoney(item3.getPrice());
    try{
      store.customerBuyUsingEscrow();
      player.acquire(item3);
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
    assertFalse(player.checkIfPlayerOwnsItem(item3));
  }

  @Test
  public void testSellUsingEscrow(){
    Store store = new Store();
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    Item item2 = new Item("player_item2", 1.0);

    player.acquire(item);
    Escrow.escrowItem(item);
    Escrow.escrowItem(item2);
    player.escrowSell(store, item);
    player.escrowSell(store, item2);

    assertFalse(player.checkIfPlayerOwnsItem(item));
    assertFalse(player.checkIfPlayerOwnsItem(item2));
  }

  @Test
  public void testGetInventory(){
    Store store = new Store();
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    Item item2 = new Item("player_item2", 110.0);
    store.addItem(item);
    player.acquire(item2);

    assertNotNull(store.getInventory());
    assertNotNull(player.getPlayerInventory());
  }

  @Test
  public void testStoreRemove(){
    Store store = new Store();
    Item item = new Item("player_item", 1.0);
    store.addItem(item);
    store.removeItem(item);
    assertFalse(store.getInventory().contains(item));
  }

  @Test
  public void testExit(){
    Store store = new Store();
    Player player = new Player(100.0);
    assertFalse(store.check_player_in_store(player));
    store.exit(player);
  }

  @Test
  public void testInventory(){
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);

    player.getPlayerInventory().addItem(item);
    player.getPlayerInventory().iterateInventory();
    assertNotNull(player.getPlayerInventory());
    player.getPlayerInventory().removeItem(item);
  }

  @Test
  public void testDisplayInventory(){
    Store store = new Store();
    Item item = new Item("player_item", 1.0);
    store.addItem(item);
    store.displayInventory();
  }

  @Test
  public void exposeInventories(){
    Player player = new Player(100.0);
    Item item = new Item("player_item", 1.0);
    player.acquire(item);
    player.exposeWearInventory();
    player.exposeDrinkInventory();
    player.exposeEatInventory();
    player.exposeHoldInventory();
    player.exposeEquipInventory();
    player.exposeConsumeInventory();
  }

  @Test
  public void testEscrow(){
    Item item = new Item("player_item", 1.0);
    Escrow escrow = new Escrow();
    double money = 10;
    escrow.escrowMoney(money);
    escrow.escrowItem(item);
    escrow.requestItem(item);

    assertTrue(money == escrow.returnMoney());
  }

}