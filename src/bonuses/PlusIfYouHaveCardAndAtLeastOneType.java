package bonuses;

public class PlusIfYouHaveCardAndAtLeastOneType extends Bonus  {
    public String text;

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        return super.count();
    }
}
