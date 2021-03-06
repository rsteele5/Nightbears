package gamescreen.gameplay;

import gameengine.gamedata.PlayerData;
import gameengine.gamedata.VendorData;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.*;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.text.TextBox;
import gameobject.renderable.button.ItemButton;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gameobject.renderable.item.Item;
import gameobject.renderable.button.Button;
import gameobject.container.RenderableGridContainer;
import gamescreen.popup.ConfirmationPopup;
import input.listeners.Key.ClickableKeyHandler;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends Overlay {
    //region <Variables>
    private ItemButton currentItemButton = null;
    private Item currentItem = null;
    private TextBox itemDetailsVendor;
    private TextBox itemDetailsPlayer;
    private TextBox goldTextBox;
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> vendorButtons;
    private PlayerData playerData;
    private VendorData vendorData;
    //endregion

    public VendorScreen(ScreenManager screenManager, GameScreen parentScreen) {
        super(screenManager, parentScreen, "VendorScreen", 150, 80, 0f);
        isExclusive = true;
    }

    @Override
    protected void initializeScreen() {
        //region <Initialize screen variables>
        Point vendorLocation = new Point(910, 575);
        Point playerLocation = new Point(520, 575);
        Point exitButtonLocation = (new Point(25, 25));
        Point buyButtonLocation = new Point(910, 780);
        Point sellButtonLocation = new Point(485, 780);
        Point playerTextLocation = new Point(440, 215);
        Point vendorTextLocation = new Point(865, 215);
        Point goldTextboxLocation = new Point(1285, 20);
        Point playerGridLocation = new Point(75, 215);
        Point vendorGridLocation = new Point(1175, 215);
        Point screenSize = (new Point(1600,900));
        Point vendorSize = (new Point(200,200));
        Point playerSize = (new Point(125,200));
        Point buttonSize = (new Point(200, 75));
        Point textBoxSize = new Point(315, 500);
        Point itemButtonSize = new Point(85, 85);
        Point goldTextboxSize = new Point(400, 100);
        int textBoxFont = 36;
        int goldTextFont = 80;
        //endregion

        //region <Initialize all other variables>
        vendorData = gameData.getVendorData();

        playerData = gameData.getPlayerData();
        vendorInventory = vendorData.getInventory();
        playerInventory = playerData.getInventory();
        playerButtons = new CopyOnWriteArrayList<>();
        vendorButtons = new CopyOnWriteArrayList<>();
        //endregion

        //region <Add objects to screen>
        for (RenderableObject renderable: vendorData.getInventory()){
            renderable.addToScreen(this, false);
        }
        for (RenderableObject renderable: playerData.getInventory()){
            renderable.addToScreen(this, false);
        }

        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/vendor/VendorBackground.png",
                DrawLayer.Background);
        imageContainer.setSize(screenSize.x, screenSize.y);
        imageContainer.addToScreen(this, true);


        imageContainer = new ImageContainer(vendorLocation.x, vendorLocation.y,
                "/assets/vendor/Vendor.png", DrawLayer.Entity);
        imageContainer.setSize(vendorSize.x, vendorSize.y);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(playerLocation.x, playerLocation.y,
                "/assets/player/color/"+playerData.getImageDirectory()+"/Teddy.png", DrawLayer.Entity);
        imageContainer.setSize(playerSize.x, playerSize.y);
        imageContainer.addToScreen(this, true);
        //endregion

        //region <Add buttons to screen>
        ArrayList<Clickable> butts = new ArrayList<>();
        Button button;

        button = new Button(exitButtonLocation.x, exitButtonLocation.y,
                "/assets/buttons/Button-Vendor-Exit.png",
                "/assets/buttons/Button-Vendor-ExitPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.setSize(buttonSize.x, buttonSize.y);
        button.addToScreen(this, true);
        butts.add(button);

        button = new Button(buyButtonLocation.x, buyButtonLocation.y,
                "/assets/buttons/Button-Vendor-Buy.png",
                "/assets/buttons/Button-Vendor-BuyPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    //Vendor must have at least one item in the inventory and
                    // the player must have clicked an item for purchase
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        // Item must be in vendor's inventory
                        if (vendorInventory.indexOf(currentItem) >= 0) {
                            // Verify that player has enough gold
                            if (playerData.getGold() >= currentItem.getValue()) {
                                // Confirmation screen
                                screenManager.addScreen(new ConfirmationPopup(screenManager,
                                        "You want to purchase " + currentItem.getItemName() +
                                                " for " + currentItem.getValue() + " gold pieces. Is this correct?",
                                        () -> {
                                            // Move item between inventory arrays
                                            vendorData.removeItem(currentItem);
                                            playerData.addItem(currentItem);
                                            playerInventory.sort(new ItemComparator());
                                            // Decrease player's gold and display on splashscreen
                                            playerData.changeGold(-currentItem.getValue());
                                            goldTextBox.setText("");
                                            goldTextBox.setText(getGoldText());
                                            currentItem.setValue(currentItem.depreciate(currentItem.getValue()));
                                            // Reset button item to the updated inventory arrays
                                            resetButtonItems();
                                            currentItemButton.deSelect();
                                            currentItem = null;
                                            itemDetailsVendor.setText("");
                                        }));
                            }
                            // Not enough gold!
                            else {
                                screenManager.addScreen(new ConfirmationPopup(screenManager,
                                        "You don't have enough gold for this purchase. Continue shopping?",
                                        ()->{},
                                        ()-> this.exiting = true));
                            }
                        }
                    }
                });
        button.setSize(buttonSize.x, buttonSize.y);
        button.addToScreen(this, true);
        butts.add(button);

        button = new Button(sellButtonLocation.x, sellButtonLocation.y,
                "/assets/buttons/Button-Vendor-Sell.png",
                "/assets/buttons/Button-Vendor-SellPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    // Player must have at least one item in the inventory and
                    // the player must have clicked an item to sell
                    if (playerInventory.size() > 0 && currentItem != null) {
                        // Item must be in player's inventory
                        if (playerInventory.indexOf(currentItem) >= 0){
                            // Confirmation screen
                            screenManager.addScreen(new ConfirmationPopup(screenManager,
                                    "I will buy your " + currentItem.getItemName() +
                                            " for " + currentItem.getValue() + " gold pieces. Is this correct?",
                                    ()-> {
                                        // Move item between inventory arrays
                                        playerData.removeItem(currentItem);
                                        vendorData.addItem(currentItem);
                                        vendorInventory.sort(new ItemComparator());
                                        // Increase player's gold and display on splashscreen
                                        playerData.changeGold(currentItem.getValue());
                                        goldTextBox.setText("");
                                        goldTextBox.setText(getGoldText());
                                        // Reset button item to the updated inventory arrays
                                        resetButtonItems();
                                        currentItemButton.deSelect();
                                        currentItem = null;
                                        itemDetailsPlayer.setText("");
                                    }));
                        }
                    }
                });
        button.setSize(buttonSize.x, buttonSize.y);
        button.addToScreen(this, true);
        butts.add(button);

        setKeyHandler(new ClickableKeyHandler(butts));
        //endregion

        //region <Add text boxes to screen>
        itemDetailsPlayer = new TextBox(playerTextLocation.x, playerTextLocation.y, textBoxSize.x, textBoxSize.y, "",
                new Font("NoScary", Font.PLAIN, textBoxFont), Color.BLACK);
        itemDetailsPlayer.addToScreen(this,true);

        itemDetailsVendor = new TextBox(vendorTextLocation.x, vendorTextLocation.y, textBoxSize.x, textBoxSize.y, "",
                new Font("NoScary", Font.PLAIN, textBoxFont), Color.BLACK);
        itemDetailsVendor.addToScreen(this,true);

        String goldText = getGoldText();
        goldTextBox = new TextBox(goldTextboxLocation.x, goldTextboxLocation.y, goldTextboxSize.x, goldTextboxSize.y, goldText,
                new Font("NoScary", Font.BOLD, goldTextFont), Color.BLACK);
        goldTextBox.addToScreen(this, true);
        //endregion

        //region <Add item buttons to the Grid Containers>
        // Create GridContainers for player and vendor item button
        int rows = 7;
        int columns = 4;
        RenderableGridContainer playerGrid = new RenderableGridContainer(rows, columns, itemButtonSize.x, itemButtonSize.y, playerGridLocation.x, playerGridLocation.y);
        RenderableGridContainer vendorGrid = new RenderableGridContainer(rows, columns, itemButtonSize.x, itemButtonSize.y, vendorGridLocation.x, vendorGridLocation.y);

        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                itemContainerButton.setSize(itemButtonSize.x, itemButtonSize.y);
                playerGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(playerInventory.get(k));
                    k++;
                }
                setClickEvent(itemContainerButton, "player" );
                // Add button to array so it can be accessed again later
                playerButtons.add(itemContainerButton);
            }
        }
        playerGrid.addToScreen(this, true);

        count = vendorInventory.size();
        k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                itemContainerButton.setSize(itemButtonSize.x, itemButtonSize.y);
                vendorGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(vendorInventory.get(k));
                    k++;
                }
                setClickEvent(itemContainerButton, "vendor");
                // Add assigned button to array so it can be accessed again later
                vendorButtons.add(itemContainerButton);
            }
        }
        vendorGrid.addToScreen(this, true);
        //endregion
    }

    /**
     * Sets the click event for each inventory item button
     * @param itemContainerButton the item button being set
     * @param sender describes where the item belongs: vendor inventory or player inventory
     */
    private void setClickEvent(ItemButton itemContainerButton, String sender){
        itemContainerButton.setOnClick(() -> {
            // If a previous button has already been pressed, then deselect and reset text boxes
            if (currentItemButton != null && currentItemButton != itemContainerButton) {
                currentItemButton.deSelect();
                // Reset previous item's text to ""
                if (itemDetailsPlayer.getText().length() > 0) {
                    itemDetailsPlayer.setText("");
                }
                if (itemDetailsVendor.getText().length() > 0) {
                    itemDetailsVendor.setText("");
                }
            }
            currentItemButton = itemContainerButton;
            currentItem = currentItemButton.getItem();
            if (currentItemButton.getItem() != null) {
                if (sender.equals("vendor")) {
                    itemDetailsVendor.setText(currentItem.getDescription(true));
                } else itemDetailsPlayer.setText(currentItem.getDescription(true));
            } else {
                currentItem = null;
                currentItemButton.deSelect();
                currentItemButton = null;
            }
        });
    }

    /**
     * This method is called after changes to the inventory while screen is active. It resets the item
     * buttons to the new inventory lists.
     */
    private void resetButtonItems(){
        // Reset all vendor item buttons to null, set item buttons again, and establish click events
        int count = vendorInventory.size();
        int k = 0;
        for (ItemButton vbutton : vendorButtons) {
            vbutton.resetItem();
            if (k < count){
                vbutton.setItem(vendorInventory.get(k));
                setClickEvent(vbutton, "vendor" );
                k++;
            }
        }

        // Reset all player item buttons to null, set item button again, and establish click events
        count = playerInventory.size();
        k = 0;
        for (ItemButton pbutton : playerButtons) {
            pbutton.resetItem();
            if (k < count){
                pbutton.setItem((Item)(playerInventory.get(k)));
                setClickEvent(pbutton, "player" );
                k++;
            }
        }
    }

    /**
     * Gets the text to display the player's gold
     * @return player's gold text
     */
    private String getGoldText(){
        return "Gold: " + playerData.getGold();
    }
    

    @Override
    protected void transitionOff() {
        gameData.save();
        exiting = true;
    }
}
