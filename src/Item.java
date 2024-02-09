abstract class Item {
    protected String name;
    protected int id;

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public abstract void displayDetails();
}