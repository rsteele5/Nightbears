package gamescreen.gameplay;

import gameengine.gamedata.PlayerData;
import gameengine.gamedata.VendorData;
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
import gameobject.container.RenderableGridContainer;
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
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<Item> vendorInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> vendorButtons;
    private Player player;
    private PlayerData playerData;
    private Player.PlayerState previousPlayerState;
    private Vendor vendor;
    private VendorData vendorData;
    //endregion ****************************************/

    public VendorScreen(ScreenManager screenManager, Player p1) {
        super(screenManager, "VendorScreen", true, 150, 80);
        player = p1;
    }

    @Override
    protected void initializeScreen() {
        Point vendorLocation = new Point(910, 575);
        Point playerLocation = new Point(520, 575);
        Point screenSize = (new Point(1600,900));
        Point vendorSize = (new Point(200,200));
        Point playerSize = (new Point(125,200));
        Point buttonSize = (new Point(200, 75));
        Point exitButtonLocation = (new Point(25, 25));
        Point buyButtonLocation = new Point(910, 780);
        Point sellButtonLocation = new Point(485, 780);
        Point playerTextLocation = new Point(440, 215);
        Point vendorTextLocation = new Point(865, 215);
        Point textBoxSize = new Point(315, 500);
        Point itemButtonSize = new Point(85, 85);
        Point goldTextboxLocation = new Point(1285, 20);
        Point goldTextboxSize = new Point(400, 100);
        Point playerGridLocation = new Point(75, 215);
        Point vendorGridLocation = new Point(1175, 215);
        int textBoxFont = 36;
        int goldTextFont = 80;

        //region Initialize variables
        vendorData = gameData.getVendorData();
        vendor = new Vendor(0,0,vendorData);
        playerData = gameData.getPlayerData();
        previousPlayerState = player.getState();
        player.setState(Player.PlayerState.asleep);
        vendorInventory = vendorData.getInventory();
        playerInventory = playerData.getInventory();
        playerButtons = new CopyOnWriteArrayList<>();
        vendorButtons = new CopyOnWriteArrayList<>();
        //endregion

        //region Create all renderable
        for (RenderableObject renderable: vendorData.getInventory()){
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
                            if (playerData.getGold() >= currentItem.getValue()) {
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
                new Font("NoScary", Font.BOLD, goldTextFont), Color.BLACK);
        goldTextBox.addToScreen(this, true);
        //endregion

        // Create GridContainers for player and vendor item button
        int rows = 7;
        int columns = 4;
        RenderableGridContainer playerGrid = new RenderableGridContainer(rows, columns, itemButtonSize.x, itemButtonSize.y, playerGridLocation.x, playerGridLocation.y);
        RenderableGridContainer vendorGrid = new RenderableGridContainer(rows, columns, itemButtonSize.x, itemButtonSize.y, vendorGridLocation.x, vendorGridLocation.y);

        //region Add item buttons to the Grid Containers
        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                itemContainerButton.setSize(itemButtonSize.x, itemButtonSize.y);
                playerGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem((Item)(playerInventory.get(k)));
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "player" );
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
                setClickEvent(itemContainerButton, itemDetailsPlayer, itemDetailsVendor, "vendor");
                // Add assigned button to array so it can be accessed again later
                vendorButtons.add(itemContainerButton);
            }
        }
        vendorGrid.addToScreen(this, true);
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
        return "Gold: " + playerData.getGold();
    }
    

    @Override
    protected void transitionOff() {
        // Change player's image back to overworld image
        player.setImage("/assets/player/overworld/Overworld-Teddy.png");
        gameData.save();
        // Update the original inventory arrays with all the changes that have been made here.
        if (vendorInventory != null && playerInventory != null) {
            vendorData.replaceList(vendorInventory);
            playerData.replaceList(playerInventory);
        } else {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "VendorScreen: Unable to overwrite inventory list");
        }
        exiting = true;
    }
}
