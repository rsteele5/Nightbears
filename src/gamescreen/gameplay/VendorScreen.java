package gamescreen.gameplay;

import gameengine.GameEngine;
import gameengine.gamedata.PlayerData;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.player.Player;
import gameobject.renderable.*;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.text.TextBox;
import gameobject.renderable.button.ItemButton;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.vendor.Vendor;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.item.Item;
import gameobject.renderable.button.Button;
import gamescreen.container.GridContainer;
import gamescreen.popup.ConfirmationPopup;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class VendorScreen extends GameScreen {
    //region Variables ******************************/
    private ItemButton currentItemButton = null;
    private Item currentItem = null;
    private TextBox itemDetailsVendor;
    private TextBox itemDetailsPlayer;
    private TextBox goldTextBox;
    private CopyOnWriteArrayList<RenderableObject> playerInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> vendorButtons;
    private Player player;
    private PlayerData playerData;
    private Player.PlayerState previousPlayerState;
    private Vendor vendor;
    //endregion ****************************************/

    public VendorScreen(ScreenManager screenManager) {
        super(screenManager, "VendorScreen", true, 150, 80);
    }

    @Override
    protected void initializeScreen() {
        Point vendorLocation = new Point(850, 575);
        Point playerLocation = new Point(600, 575);
        Point screenSize = (new Point(1600,900));
        Point vendorSize = (new Point(200,200));
        Point playerSize = (new Point(125,200));
        Point buttonSize = (new Point(200, 75));
        Point exitButtonLocation = (new Point(25, 25));
        Point buyButtonLocation = new Point(850, 780);
        Point sellButtonLocation = new Point(550, 780);
        Point playerTextLocation = new Point(270, 125);
        Point vendorTextLocation = new Point(550, 125);
        Point textBoxSize = new Point(210, 230);
        Point itemButtonSize = new Point(50, 50);
        Point goldTextboxLocation = new Point(850, 20);
        Point goldTextboxSize = new Point(150, 50);
        Point playerGridLocation = new Point(50, 150);
        Point vendorGridLocation = new Point(760, 150);
        int textBoxFont = 24;
        int goldTextFont = 48;

        //region Initialize variables
        vendor = GameEngine.vendor;
        player = GameEngine.players.get(0);
        playerData = gameData.getPlayerData();
        previousPlayerState = player.getState();
        player.setState(Player.PlayerState.asleep);
        vendorInventory = vendor.getItems();
        playerInventory = playerData.getInventory();
        playerButtons = new CopyOnWriteArrayList<>();
        vendorButtons = new CopyOnWriteArrayList<>();
        //endregion

        //region Create all renderable
        for (RenderableObject renderable: vendor.getRenderables()){
            renderable.addToScreen(this, false);
        }
        for (RenderableObject renderable: playerData.getInventory()){
            renderable.addToScreen(this, false);
        }

        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/vendor/VendorBackground.png", DrawLayer.Background);
        imageContainer.setSize(screenSize.x, screenSize.y);
        imageContainer.addToScreen(this, true);


        imageContainer = new ImageContainer(vendorLocation.x, vendorLocation.y, "/assets/vendor/Vendor.png", vendor.getDrawLayer());
        imageContainer.setSize(vendorSize.x, vendorSize.y);
        imageContainer.addToScreen(this, true);

        imageContainer = new ImageContainer(playerLocation.x, playerLocation.y, "/assets/player/sidescrolling/Teddy.png", player.getDrawLayer());
        imageContainer.setSize(playerSize.x, playerSize.y);
        imageContainer.addToScreen(this, true);
        //endregion

        //region Create splashscreen button **/
        Button button;

        button = new Button(exitButtonLocation.x, exitButtonLocation.y,
                "/assets/buttons/Button-Vendor-Exit.png",
                "/assets/buttons/Button-Vendor-ExitPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Exit Vendor");
                    player.setState(previousPlayerState);
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.setSize(buttonSize.x, buttonSize.y);
        button.addToScreen(this, true);

        button = new Button(buyButtonLocation.x, buyButtonLocation.y,
                "/assets/buttons/Button-Vendor-Buy.png",
                "/assets/buttons/Button-Vendor-BuyPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Buy from Vendor");
                    if (vendorInventory.size() > 0 && currentItem != null) {
                        // Item must be in vendor's inventory
                        if (vendorInventory.indexOf(currentItem) >= 0) {
                            // Verify that player has enough gold
                            if (player.getGold() >= currentItem.getValue()) {
                                // Confirmation
                                screenManager.addScreen(new ConfirmationPopup(screenManager,
                                        "You want to purchase " + currentItem.getItemName() +
                                                " for " + currentItem.getValue() + " gold pieces. Is this correct?",
                                        () -> {
                                            // Move item between inventory arrays
                                            vendorInventory.remove(currentItem);
                                            playerInventory.add(currentItem);
                                            playerInventory.sort(new ItemComparator());
                                            // Decrease player's gold and display on splashscreen
                                            player.changeGold(-currentItem.getValue());
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

        button = new Button(sellButtonLocation.x, sellButtonLocation.y,
                "/assets/buttons/Button-Vendor-Sell.png",
                "/assets/buttons/Button-Vendor-SellPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Sell to Vendor");
                    if (playerInventory.size() > 0 && currentItem != null) {
                        // Item must be in player's inventory
                        if (playerInventory.indexOf(currentItem) >= 0){
                            // Confirmation
                            screenManager.addScreen(new ConfirmationPopup(screenManager,
                                    "I will buy your " + currentItem.getItemName() +
                                            " for " + currentItem.getValue() + " gold pieces. Is this correct?",
                                    ()-> {
                                        // Move item between inventory arrays
                                        playerInventory.remove(currentItem);
                                        vendorInventory.add(currentItem);
                                        vendorInventory.sort(new ItemComparator());
                                        // Increase player's gold and display on splashscreen
                                        player.changeGold(currentItem.getValue());
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

        // TODO: Remove after Sprint 3 testing and adjust restockItems() in Vendor
        /*button = new Button(460, 485,
                "/assets/testAssets/TestButton.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Restock Items");
                    vendorInventory = vendor.restockItems();
                    vendor.replaceList(vendorInventory);

                    // Access the new images
                    for (RenderableObject renderable: vendor.getRenderables()){
                        renderable.load();
                    }
                    resetButtonItems();
                    if (currentItemButton != null){
                        currentItemButton.deSelect();
                        currentItem = null;
                        itemDetailsPlayer.setText("");
                    }
                });
        button.setSize(100, 50);
        button.addToScreen(this, true);*/

        //endregion

        //region Create text boxes to hold item description
        /* x and y positions for text */

        itemDetailsPlayer = new TextBox(playerTextLocation.x, playerTextLocation.y, textBoxSize.x, textBoxSize.y, "",
                new Font("NoScary", Font.PLAIN, textBoxFont), Color.BLACK);
        itemDetailsPlayer.addToScreen(this,true);
        itemDetailsVendor = new TextBox(vendorTextLocation.x, vendorTextLocation.y, textBoxSize.x, textBoxSize.y, "",
                new Font("NoScary", Font.PLAIN, textBoxFont), Color.BLACK);
        itemDetailsVendor.addToScreen(this,true);
        //endregion

        //region TextBox to hold player's available gold
        String goldText = getGoldText();
        goldTextBox = new TextBox(goldTextboxLocation.x, goldTextboxLocation.y, goldTextboxSize.x, goldTextboxSize.y, goldText,
                new Font("NoScary", Font.PLAIN, goldTextFont), Color.BLACK);
        goldTextBox.addToScreen(this, true);
        //endregion

        // Create GridContainers for player and vendor item button
        int rows = 7;
        int columns = 4;
        GridContainer playerGrid = new GridContainer(this, rows, columns, itemButtonSize.x, itemButtonSize.y, playerGridLocation.x, playerGridLocation.y);
        GridContainer vendorGrid = new GridContainer(this, rows, columns, itemButtonSize.x, itemButtonSize.y, vendorGridLocation.x, vendorGridLocation.y);

        //region Add item buttons to the Grid Containers
        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                playerGrid.dynamicAddAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem((Item)(playerInventory.get(k)));
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "player" );
                // Add button to array so it can be accessed again later
                playerButtons.add(itemContainerButton);
            }
        }

        count = vendorInventory.size();
        k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                vendorGrid.dynamicAddAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(vendorInventory.get(k));
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "vendor");
                // Add assigned button to array so it can be accessed again later
                vendorButtons.add(itemContainerButton);
            }
        }
        //endregion
    }


    private void setClickEvent(ItemButton itemContainerButton, TextBox itemDetailsPlayer, TextBox itemDetailsVendor, String sender){
        itemContainerButton.setOnClick(() -> {
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

    private void resetButtonItems(){
        // Reset all vendor item buttons to null, set item button again, and establish click events
        int count = vendorInventory.size();
        int k = 0;
        for (ItemButton vbutton : vendorButtons) {
            vbutton.resetItem();
            if (k < count){
                vbutton.setItem(vendorInventory.get(k));
                setClickEvent(vbutton, itemDetailsPlayer, itemDetailsVendor, "vendor" );
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
                setClickEvent(pbutton, itemDetailsPlayer, itemDetailsVendor, "player" );
                k++;
            }
        }
    }

    private String getGoldText(){
        return "Gold: " + player.getGold();
    }
    

    @Override
    protected void transitionOff() {
        // Change player's image back to overworld image
        player.setImage("/assets/player/overworld/Overworld-Teddy.png");
        gameData.save();
        // Update the original inventory arrays with all the changes that have been made here.
        if (vendorInventory != null && playerInventory != null) {
            vendor.replaceList(vendorInventory);
            playerData.replaceList(playerInventory);
        } else {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "VendorScreen: Unable to overwrite inventory list");
        }
        exiting = true;
    }
}
