package FTF.tcgdeckbuilderandmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import FTF.tcgdeckbuilderandmanager.DAO.AppDatabase;

// Using  a modified version of the word code: https://developer.android.com/codelabs/android-room-with-a-view#16

// todo: Implement cards having thie own images
// todo: Implement season numbers and individual card season numbers  (S0 -100)
// todo: Set up 'Requirements' (Summon monsters needing another specific monster in the deck)

@Entity(tableName = AppDatabase.CARD_TABLE)
public class Card {

    // Primary key is used to help look through the list of cards
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int mID;

    // Each card has it's own name!!!
    @NonNull
    @ColumnInfo(name = "Card Name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "Set Number")
    private Integer mSetNum;

    @NonNull
    @ColumnInfo(name = "Set Monster Number")
    private Integer mSetMonNum;

    // Type refers to what type of card a card is (1 = monster, 2 = item/spell)
    @NonNull
    @ColumnInfo(name = "Card Type")
    private Integer mType;

    // Sub Type refers to the specific type of card a card is dependent on what primary type the card is

    // If the card is a monster: If the subtype is one, then it's a basic monster
    // If the subtype is two, it's a summonable monster and needs another monster to be played

    // If the card is a item/spell then it's either an item or spell which is determined by the subtype num

    @NonNull
    @ColumnInfo(name = "Card Sub-Type")
    private Integer mSubType;

    // Each card has a certain power level! For monsters, this is the strength of a creature
    // For items/spells, this tends to be how much of a boost it gives to the attached creature

    // Each card has a different typing (rock , paper, scissors)
    @NonNull
    @ColumnInfo(name = "RPS Type")
    private Integer mRPSType;

    @ColumnInfo(name = "Card Power Level")
    private Integer mPower;

    // Each card has a certain amount of slots! For monsters, this is amount of slots available
    // For items/spells, this tends to be how many slots the item takes up on a creature
    @NonNull
    @ColumnInfo(name = "Card Slots")
    private Integer mSlots;

    // If a card has an effect, it will be listed here!
    @ColumnInfo(name = "Card Effect")
    private String mEffect;

    @ColumnInfo(name = "Card Description")
    private String mDesc;

    // Quick boolean that is used to show if a card is still legal
    @NonNull
    @ColumnInfo(name = "Card Legality?")
    private boolean mIsLegal;

    // Boolean Variable that is used to show if a you can multiple variants of a certain card in a deck
    @NonNull
    @ColumnInfo(name = "Duplicates Allowed?")
    private boolean mDupesAllowed;

    public Card() {
        this("Subsitute", 1, 1, 1, 200,
                1, true, -1, -1, false, null, null);
    }

    public Card(@NonNull String name, @NonNull Integer rpsType, @NonNull Integer type,
                @NonNull Integer subType, Integer power, @NonNull Integer slots,
                @NonNull boolean dupesAllowed, @NonNull Integer setNum, @NonNull Integer setMonNum,
                @NonNull boolean isLegal, String effect, String desc) {

        this.mName = name;
        this.mSetNum = setNum;
        this.mSetMonNum = setMonNum;
        this.mType = type;
        this.mSubType = subType;
        this.mRPSType = rpsType;
        this.mPower = power;
        this.mSlots = slots;
        this.mIsLegal = isLegal;
        this.mDupesAllowed = dupesAllowed;

        if (effect == null) {
            this.mEffect = "No special effects";
        }
        else {
            this.mEffect = effect;
        }

    }

    // Standard getters that get used throughout the code

    public int getID() {
        return mID;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public Integer getSetNum() {
        return mSetNum;
    }

    @NonNull
    public Integer getSetMonNum() {
        return mSetMonNum;
    }

    @NonNull
    public Integer getType() {
        return mType;
    }

    @NonNull
    public Integer getSubType() {
        return mSubType;
    }

    @NonNull
    public Integer getRPSType() {
        return mRPSType;
    }

    public Integer getPower() {
        return mPower;
    }

    @NonNull
    public Integer getSlots() {
        return mSlots;
    }

    public String getEffect() {
        return mEffect;
    }

    public boolean getIsLegal() {
        return mIsLegal;
    }

    public boolean getDupesAllowed() {
        return mDupesAllowed;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public void setName(@NonNull String mName) {
        this.mName = mName;
    }

    public void setSetNum(@NonNull Integer mSetNum) {
        this.mSetNum = mSetNum;
    }

    public void setSetMonNum(@NonNull Integer mSetMonNum) {
        this.mSetMonNum = mSetMonNum;
    }

    public void setType(@NonNull Integer mType) {
        this.mType = mType;
    }

    public void setSubType(@NonNull Integer mSubType) {
        this.mSubType = mSubType;
    }

    public void setRPSType(@NonNull Integer mRPSType) {
        this.mRPSType = mRPSType;
    }

    public void setPower(Integer mPower) {
        this.mPower = mPower;
    }

    public void setSlots(@NonNull Integer mSlots) {
        this.mSlots = mSlots;
    }

    public void setEffect(String mEffect) {
        this.mEffect = mEffect;
    }

    public void setIsLegal(boolean mIsLegal) {
        this.mIsLegal = mIsLegal;
    }

    public void setDupesAllowed(boolean mDupesAllowed) {
        this.mDupesAllowed = mDupesAllowed;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    // This class returns a string dependent on the cards type and subtype
    // This class is used to turn the two ints type/subtype into text a normal user can understand
    public String getFormType() {
        String temp = "";

        if (this.getType() == 1) {

            if (this.getSubType() == 1) {
                temp = "Basic Monster";
            } else {
                temp = "Summonable Monster";
            }

        } else if (this.getSubType() == 2) {

            if (this.getSubType() == 1) {
                temp = "Equipment";
            } else {
                temp = "Spell";
            }

        }

        return temp;
    }

    // Returns a formatted string that's dependent on a creatures set number and individual number in that set
    public String getFormNum(){

        return "S" + this.getSetNum() + "-" + this.getSetMonNum();

    }

    public String checkIsLegal() {
        if (this.getIsLegal()) {
            return "YEAH";
        } else {
            return "Nuh uh";
        }
    }

    public String isDupesAllowed() {
        if (this.getDupesAllowed()) {
            return "YEAH";
        } else {
            return "Nuh uh";
        }
    }

    @Override
    public String toString() {
        return "Card{ Name: " + this.getName() +
                " Season Number: " + this.getFormNum() +
                " Card ID: " + this.getID() +
                '}';
    }
}