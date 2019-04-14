package gamescreen.gameplay;

import gameengine.gamedata.PlayerData;
import gameobject.renderable.item.armor.Armor;
import gameobject.renderable.item.consumable.Consumable;
import gameobject.renderable.item.consumable.ConsumableBuilder;
import gameobject.renderable.item.consumable.ConsumableType;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.*;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.text.TextBox;
import gameobject.renderable.button.Button;
import gameobject.renderable.button.ButtonText;
import gameobject.renderable.button.ItemButton;
import gameobject.renderable.item.*;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.Overlay;
import gamescreen.ScreenManager;
import gameobject.container.RenderableGridContainer;
import gamescreen.mainmenu.MainMenuScreen;
import gamescreen.mainmenu.options.OptionScreen;
import input.listeners.Key.ClickableKeyHandler;
import main.utilities.Clickable;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class PauseMenu extends Overlay {
    /* Initialize variables *****************/
    //region<Variable Declarations>
    private CopyOnWriteArrayList<Item> playerInventory;
    private CopyOnWriteArrayList<ItemButton> playerButtons;
    private CopyOnWriteArrayList<ItemButton> equipButtons;
    private CopyOnWriteArrayList<Item> currentEquipment;
    private ItemButton bigEquipment;
    private TextBox itemDetails;
    private ItemButton currentItemButton = null;
    private Item currentItem = null;
    private ButtonText useButton;
    private RenderableGridContainer playerGrid;
    private PlayerData playerData = gameData.getPlayerData();
    //Numbers
    int lblYVal = 150;
    int itemBtnSize = 70;
    int itemBigSize = itemBtnSize*2;
    //Define Points
    Point screenSize = (new Point(1600,900));
    Point bgLblSpot = (new Point(0,0));
    Point titleLblSpot = (new Point(5,0));
    Point itemLblSpot = (new Point(100,lblYVal));
    Point eqpLblSpot = (new Point(itemLblSpot.x+275,lblYVal));
    Point selectedLblSpot = (new Point(eqpLblSpot.x+350,lblYVal));
    Point teddyImgSpot  = (new Point(eqpLblSpot.x+75,560));
    Point teddyImgSize = (new Point(112,225));
    Point btnSpot = (new Point(1240,60));
    Point btnSize = (new Point(340,144));
    Point itemTxtSpot = (new Point(selectedLblSpot.x+50,selectedLblSpot.y+100));
    Point btnUseSpot = (new Point(itemTxtSpot.x+45,itemTxtSpot.y+310));
    Point btnUseSize = (new Point(210,98));
    Point btnBigSpot = (new Point(btnUseSpot.x+35,btnUseSpot.y+105));
    Point itemBtnSpot = (new Point(20,250));
    public enum Equipments{
        Helmet,
        OffHand,
        Chest,
        Weapon,
        Legs,
        Feet
    }

    //endregion

    public PauseMenu(ScreenManager screenManager, GameScreen parentScreen) {
        super(screenManager, parentScreen, "PauseMenu", 150, 80, 0f);
        isExclusive = true;
    }

    /**
     * Initializes all of the stuff you want on your GameScreen
     */
    @Override
    protected void initializeScreen() {
        playerInventory = playerData.getInventory();
        currentEquipment = playerData.getPlayerEquipment();
        playerButtons = new CopyOnWriteArrayList<>();
        equipButtons = new CopyOnWriteArrayList<>();


        //Add all the item in the dev GameScreen player to the GameScreen
        for (RenderableObject renderable: playerInventory){
            renderable.addToScreen(this, false);
        }

        for (RenderableObject renderable: currentEquipment){
            if(renderable!=null)renderable.addToScreen(this, false);
        }

        //Create Labels
        initLabels();
        //Create Menu Buttons
        initButtons();
        //Create Item Display Area
        initItemDisplay();
        //Create Item Buttons
        initItemButtons();
        //Create Equipment Buttons
        initEquipButtons();
    }

    private void setClickEvent(ItemButton itemContainerButton, TextBox itemDetailsPlayer){
        itemContainerButton.setOnClick(() -> {
            if (currentItemButton != null) {
                currentItemButton.deSelect();
                // Reset previous item's text to ""
                if (itemDetailsPlayer.getText().length() > 0) {
                    itemDetailsPlayer.setText("");
                    bigEquipment.setItem(null);
                }
            }

            currentItemButton = itemContainerButton;
            currentItem = currentItemButton.getItem();

            if (currentItemButton.getItem() != null) {
                itemDetailsPlayer.setText(currentItem.getDescription(false));
                bigEquipment.setItem(currentItemButton.getItem());
                if(currentItem.getCategory().toString() == "Consumable"){
                    useButton.setText("Consume");
                    useButton.setOnClick(() -> {
                        Consumable myConsumable = (Consumable)currentItem;
                        //Type: edible
                        int rand = getRandomNumber(myConsumable.getMinAffect(), myConsumable.getMaxAffect());
                        Debug.log(DebugEnabler.TEST_LOG, "Consumable Type: " + currentItem.getType());
                        if (currentItem.getType() == 0){
                            Debug.log(DebugEnabler.TEST_LOG, "Affect Type: " + myConsumable.getAffectType());
                            Debug.log(DebugEnabler.TEST_LOG, "Current Health: " + playerData.getCurrentHealth());
                            if (myConsumable.getAffectType() == AffectType.healthBoost){
                                Debug.log(DebugEnabler.TEST_LOG, "Rand: " + rand);
                                playerData.modifyCurrentHealth(rand);
                                Debug.log(DebugEnabler.TEST_LOG, "New Health: " + playerData.getCurrentHealth());

                            } else {
                                Debug.log(DebugEnabler.TEST_LOG, "Rand: " + rand);
                                Debug.log(DebugEnabler.TEST_LOG, "Current Max Health: " + playerData.getMaxHealth());
                                playerData.modifyMaxHealth(rand);
                                Debug.log(DebugEnabler.TEST_LOG, "Current Max Health: " + playerData.getMaxHealth());
                            }
                        }
                        else if (currentItem.getType() == 2){
                            Weapon myWeapon = (Weapon) playerData.getPlayerEquipment().get(3);
                            if (myWeapon != null) {
                                myWeapon.increaseAttributes(rand);
                            }
                        }
                        playerInventory.remove(currentItem);
                        playerData.removeItem(currentItem);
                        clearFields();
                        //todo offer healing or bonuses
                    });
                } else if((currentItem.getCategory().toString() == "Armor" || currentItem.getCategory().toString() == "Weapon") && !equipButtons.contains(itemContainerButton)){
                    useButton.setText("Equip");
                    useButton.setOnClick(() -> {

                        if(currentItem instanceof Weapon)
                            playerData.equipItem(currentItem, 3);//3 is weapon slot
                        if(currentItem instanceof Armor) {
                            if (currentItem.getType() >= 3)
                                playerData.equipItem(currentItem, currentItem.getType() + 1);
                            else playerData.equipItem(currentItem, currentItem.getType());
                        }
                        clearFields();

                    });
                } else if((currentItem.getCategory().toString() == "Armor" || currentItem.getCategory().toString() == "Weapon") && equipButtons.contains(itemContainerButton)){
                    useButton.setText("Unequip");
                    useButton.setOnClick(() -> {
                        if(currentItem != null) {
                            if (currentItem instanceof Weapon)
                                playerData.unequipItem(currentItem, 3);
                            else if (currentItem.getType() > 2)
                                playerData.unequipItem(currentItem, currentItem.getType() + 1);
                            else
                                playerData.unequipItem(currentItem, currentItem.getType());
                            clearFields();
                        }
                    });
                }
            } else {
                currentItem = null;
                currentItemButton.deSelect();
                currentItemButton = null;
                useButton.setText("");
            }
        });
    }

    private void clearFields() {
        currentEquipment = playerData.getPlayerEquipment();
        playerInventory = playerData.getInventory();
        playerInventory.sort(new ItemComparator());
        bigEquipment.resetItem();
        itemDetails.setText("");
        useButton.setText("");
        currentItem = null;
        resetButtonItems();
        currentItemButton.deSelect();
    }

    private void resetButtonItems(){
        // Reset all player item button to null, set item button again, and establish click events

        int count = playerInventory.size();
        int k = 0;
        for (ItemButton pbutton : playerButtons) {
            pbutton.resetItem();
            if (k < count){
                pbutton.setItem(playerInventory.get(k));
                setClickEvent(pbutton, itemDetails );
                k++;
            }
        }
        for(int x = 0; x < 6/*todo make 6 from somewhere else*/; x++) {
                equipButtons.get(x).setItem(currentEquipment.get(x));
                setClickEvent(equipButtons.get(x), itemDetails );
        }
    }

    private void initLabels() {

        //BackGround
        ImageContainer background;
        background = new ImageContainer(bgLblSpot.x,bgLblSpot.y, "/assets/backgrounds/BG-Inventory.png", DrawLayer.Background);
        background.setSize(screenSize.x,screenSize.y);
        background.addToScreen(this,true);

        //Fonts
        Font noScaryHeader1 = new Font("NoScary", Font.PLAIN, 192);
        Font noScaryHeader2 = new Font("NoScary", Font.PLAIN, 100);

        //Add menu Title
        TextBox menuTitle = new TextBox(titleLblSpot.x, titleLblSpot.y, 800, 1, "Pause Menu",
                noScaryHeader1, Color.WHITE);
        menuTitle.addToScreen(this,true);

        //Item label
        TextBox items = new TextBox(itemLblSpot.x, itemLblSpot.y, 500, 200, "Items",
                noScaryHeader2, Color.BLUE);
        items.addToScreen(this,true);

        //Equipped label
        TextBox equipped = new TextBox(eqpLblSpot.x, eqpLblSpot.y, 500, 200, "Equipped",
                noScaryHeader2, Color.BLUE);
        equipped.addToScreen(this,true);

        //Selected item label
        TextBox selectedItem = new TextBox(selectedLblSpot.x, selectedLblSpot.y, 800, 200, "Selected Item",
                noScaryHeader2, Color.BLUE);
        selectedItem.addToScreen(this,true);

        //Teddy
        ImageContainer teddy = new ImageContainer(teddyImgSpot.x,teddyImgSpot.y,
                "/assets/player/color/"+playerData.getImageDirectory()+"/Teddy.png", DrawLayer.Entity);
        teddy.setSize(teddyImgSize.x,teddyImgSize.y);
        teddy.addToScreen(this,true);

        //Name
        TextBox teddyName = new TextBox(teddyImgSpot.x - 100, teddyImgSize.y + 550, 400, 100, gameData.getPlayerData().getName(),
                noScaryHeader2, Color.BLACK);
        teddyName.addToScreen(this, true);
    }

    private void initButtons() {
        ArrayList<Clickable> butts = new ArrayList<>();
        //Main Menu Button
        Button button = new gameobject.renderable.button.Button(btnSpot.x,btnSpot.y,
                "/assets/buttons/Button-MainMenu.png",
                "/assets/buttons/Button-MainMenuPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    screenManager.addScreen(new MainMenuScreen(screenManager));
                });
        button.setSize(btnSize.x, btnSize.y);
        button.addToScreen(this,true);
        butts.add(button);

        //Save Button
        button = new gameobject.renderable.button.Button(btnSpot.x,btnSpot.y+(btnSize.y+btnSpot.y),
                "/assets/buttons/Button-Save.png",
                "/assets/buttons/Button-SavePressed.png",
                DrawLayer.Entity,
                () ->{
                    //TODO Save stuff
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Save");
                });
        button.setSize(btnSize.x, btnSize.y);
        button.addToScreen(this,true);
        butts.add(button);

        //Options Button
        button = new gameobject.renderable.button.Button(btnSpot.x,btnSpot.y+((btnSize.y+btnSpot.y) * 2),
                "/assets/buttons/Button-Options.png",
                "/assets/buttons/Button-OptionsPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager));
                });
        button.setSize(btnSize.x, btnSize.y);
        button.addToScreen(this,true);
        butts.add(button);

        //Back Button
        button = new gameobject.renderable.button.Button(btnSpot.x,btnSpot.y+((btnSize.y+btnSpot.y) * 3),
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    currentState = ScreenState.TransitionOff;
                });
        button.setSize(btnSize.x, btnSize.y);
        button.addToScreen(this,true);
        butts.add(button);

        setKeyHandler(new ClickableKeyHandler(butts));
    }

    private void initItemButtons() {
        //Set up the grid for the player inventory
        int rows = 7;
        int columns = 4;
        playerGrid = new RenderableGridContainer(rows, columns, itemBtnSize, itemBtnSize, itemBtnSpot.x, itemBtnSpot.y);

        //region Add button to the Grid Containers
        int count = playerInventory.size();
        int k = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                ItemButton itemContainerButton = new ItemButton();
                itemContainerButton.setSize(itemBtnSize,itemBtnSize);
                playerGrid.addAt(itemContainerButton, i, j);
                if (k < count) {
                    itemContainerButton.setItem(playerInventory.get(k));
                    k++;
                }
                setClickEvent(itemContainerButton, itemDetails);
                // Add button to array so it can be accessed again later
                playerButtons.add(itemContainerButton);
            }
        }
        playerGrid.addToScreen(this, true);
    }

    private void initEquipButtons(){
        //Create grid for the cross pattern equipment stuff
        RenderableGridContainer equipGrid = new RenderableGridContainer(4, 3, itemBtnSize, itemBtnSize, eqpLblSpot.x+25, itemBtnSpot.y);
        //Equipment Buttons
        //Helmet
        ItemButton equipHead =  new ItemButton();
        equipHead.setSize(itemBtnSize,itemBtnSize);
        if(currentEquipment.get(Equipments.Helmet.ordinal()) != null) {
            equipHead.setItem(currentEquipment.get(Equipments.Helmet.ordinal()));
        }
        equipGrid.addAt(equipHead, 0, 1);
        equipButtons.add(equipHead);
        setClickEvent(equipHead, itemDetails);
        //OffHand
        ItemButton equipOffHand =  new ItemButton();
        equipOffHand.setSize(itemBtnSize,itemBtnSize);
        if(currentEquipment.get(Equipments.OffHand.ordinal()) != null) {
            equipOffHand.setItem(currentEquipment.get(Equipments.OffHand.ordinal()));
        }
        equipGrid.addAt(equipOffHand, 1, 0);
        equipButtons.add(equipOffHand);
        setClickEvent(equipOffHand, itemDetails);
        //Chest
        ItemButton equipChest =  new ItemButton();
        equipChest.setSize(itemBtnSize,itemBtnSize);
        if(currentEquipment.get(Equipments.Chest.ordinal()) != null) {
            equipChest.setItem(currentEquipment.get(Equipments.Chest.ordinal()));
        }
        equipGrid.addAt(equipChest, 1, 1);
        equipButtons.add(equipChest);
        setClickEvent(equipChest, itemDetails);
        //Weapon
        ItemButton equipWeapon =  new ItemButton();
        equipWeapon.setSize(itemBtnSize,itemBtnSize);
        if(currentEquipment.get(Equipments.Weapon.ordinal()) != null) {
            equipWeapon.setItem(currentEquipment.get(Equipments.Weapon.ordinal()));
        }
        equipGrid.addAt(equipWeapon, 1, 2);
        equipButtons.add(equipWeapon);
        setClickEvent(equipWeapon, itemDetails);
        //Legs
        ItemButton equipLegs =  new ItemButton();
        equipLegs.setSize(itemBtnSize,itemBtnSize);
        if(currentEquipment.get(Equipments.Legs.ordinal()) != null) {
            equipLegs.setItem(currentEquipment.get(Equipments.Legs.ordinal()));
        }
        equipGrid.addAt(equipLegs, 2, 1);
        equipButtons.add(equipLegs);
        setClickEvent(equipLegs, itemDetails);
        //Feet
        ItemButton equipFeet =  new ItemButton();
        equipFeet.setSize(itemBtnSize,itemBtnSize);
        if(currentEquipment.get(Equipments.Feet.ordinal()) != null) {
            equipFeet.setItem(currentEquipment.get(Equipments.Feet.ordinal()));
        }
       // equipHead.setSlot(Equipments.Feet.ordinal());
        equipGrid.addAt(equipFeet, 3, 1);
        equipButtons.add(equipFeet);
        setClickEvent(equipFeet, itemDetails);
        //endregion

        equipGrid.addToScreen(this, true);
    }

    private void initItemDisplay() {
        //Item details text box
        itemDetails = new TextBox(itemTxtSpot.x, itemTxtSpot.y, 700, 500, "",
                new Font("NoScary", Font.PLAIN, 60), Color.WHITE);
        itemDetails.addToScreen(this,true);

        //Use Buttons
        Font noScaryHeader2 = new Font("NoScary", Font.PLAIN, 70);
        useButton = new ButtonText(btnUseSpot.x, btnUseSpot.y, "/assets/buttons/Button-Inventory-Empty.png","/assets/buttons/Button-Inventory-Empty.png", DrawLayer.Entity,noScaryHeader2,Color.BLACK,btnUseSize.x,btnUseSize.y);
        useButton.addToScreen(this, true);

        //Setup the big equipment button
        bigEquipment =  new ItemButton(btnBigSpot.x,btnBigSpot.y, DrawLayer.Entity);
        bigEquipment.setSize(itemBigSize,itemBigSize);

        //If you click the big view do nothing, can change this later
        bigEquipment.setOnClick( () -> bigEquipment.deSelect());
        bigEquipment.addToScreen(this, true);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }
}
