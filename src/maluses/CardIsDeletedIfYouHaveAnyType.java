package maluses;

public class CardIsDeletedIfYouHaveAnyType extends Malus {
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
