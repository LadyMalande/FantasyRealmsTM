package bonuses;

public class BonusOrBonus extends Bonus {

    private String text;
    private Bonus b1;
    private Bonus b2;

    public BonusOrBonus(Bonus b1, Bonus b2) {
        this.text = b1.getText() + "\n----- OR -----\n" + b2.getText();
        this.b1 = b1;
        this.b2 = b2;
    }



    @Override
    public int count() {
        return Math.max(b1.count(), b2.count());
    }

    @Override
    public String getText() {
        return this.text;
    }
}
