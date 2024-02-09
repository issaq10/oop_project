class Book extends Item {
    private String author;
    private boolean isAvailable;

    public Book(String title, String author, int id) {
        super(title, id);
        this.author = author;
        this.isAvailable = true;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    @Override
    public void displayDetails() {
        System.out.println("Book Details - Title: " + name + ", Author: " + author + ", ID: " + id);
    }
}
