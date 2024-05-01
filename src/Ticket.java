public class Ticket {
    public int row;
    public int seat;
    public double price;

    private Person person;

    public Ticket(int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }
    public void print(){
        System.out.println("**************");
        System.out.println("Name : "+ person.firstName+" "+ person.lastName);
        System.out.println("Email : "+ person.email);
        System.out.println("Row : "+row);
        System.out.println("Seat : "+seat);
        System.out.println("Price : ₤"+price);
        System.out.println("**************");

    }
}
