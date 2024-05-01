import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Theatre {
    static int[] Row1=new int[12];
    static int[] Row2=new int[16];
    static int[] Row3=new int[20];
    static ArrayList<Ticket> buyers =new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static boolean programOn =true;
    public static void main(String[] args) {
        System.out.println("--------WELCOME TO THE NEW THEATRE--------");
        while (programOn){
            displayMenu();
            int menuNumber = inputValidator("Enter the number of the task you need to do :",0,8);
            switchMenu(menuNumber);
        }
    }
    public static void displayMenu(){
        System.out.print("""
                ____________________________________________
                1) Buy a ticket
                2) Print seating area
                3) Cancel ticket
                4) List available seats
                5) Save to file
                6) Load from file
                7) Print ticket information and total price
                8) Sort tickets by price
                0) Quit
                ____________________________________________
                """);
    }
    public static void switchMenu(int menuNumber){
    //This method is to select the option that user need to do.
        switch (menuNumber) {
            case 1 -> BuyTicket();
            case 2 -> printSeatingPlan();
            case 3 -> cancelTicket();
            case 4 -> showAvailable();
            case 5 -> fileWriter(Row1, Row2, Row3);
            case 6 -> load(Row1,Row2,Row3);
            case 7 -> {printInfo();calculateTotalPrice();}
            case 8 -> sortTickets();
            case 0 -> programOn = false;
        }
    }
    public static int inputValidator(String prompt,int min , int max){
    //This method is to validate integer inputs we get in this code.
        int data;
        while (true){
            try {
                System.out.print(prompt);
                data =input.nextInt();
                if (min<=data && data<=max){
                    return data;
                }
                else {
                    System.out.println("Please enter a number between "+min+" and "+max);
                }
            }catch (InputMismatchException e){
                input.nextLine();
                System.out.println("Enter a valid Integer");
            }
        }
    }
    public static double priceValidator(String prompt,double min , double max){
    //This is for validate double prices.
        double data;
        while (true){
            try {
                System.out.print(prompt);
                data =input.nextInt();
                if (min<=data && data<=max){
                    return data;
                }
                else {
                    System.out.println("Please enter a price between "+min+" and "+max);
                }
            }catch (InputMismatchException e){
                input.nextLine();
                System.out.println("Invalid Input");
            }
        }
    }
    public static boolean emailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public static String inputEmail(){
        String email;
        do {
            System.out.println("Enter your email : ");
            email = input.next();
            System.out.println("Enter a valid email.");
        } while (!emailValid(email));
        return email;
    }
    public static String userDetailsInput(String prompt){
    //this is for validating string inputs we get.
        String userDetails;
        while(true) {
            try {
                System.out.print(prompt);
                userDetails = input.next();
                if (!userDetails.matches("^[a-zA-Z]*$")) {
                    throw new InputMismatchException();
                }
                return userDetails;
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid input.");
            }
        }
    }
    public static boolean isNotOccupied(int rowNum, int seatNum){
    //This method is to check if a seat is booked or not.
        if (rowNum==1){
            return Row1[seatNum-1]==0;
        }
        else if(rowNum==2){
            return Row2[seatNum-1]==0;
        }
        return Row3[seatNum - 1] == 0;
    }
    public static void markSeatAsBooked(int rowNum, int seatNum){
    /*When user buys a ticket,that particular seat
     being marked with the help of this method*/
        if (rowNum==1){
            Row1[seatNum-1]=1;
        }
        else if (rowNum==2){
            Row2[seatNum-1]=1;
        }
        else{
            Row3[seatNum-1]=1;
        }
    }
    public static void BuyTicket(){
    //With the help of this method I completed the process of buying a ticket.
        int rowNumber,seatNumber;
        double price;
        boolean run=true;
        while (run) {
            rowNumber = inputValidator("Please Enter a row number : ", 1, 3);
            if (rowNumber == 1) {
                seatNumber = inputValidator("Please Enter a seat number : ", 1, 12);
                price = priceValidator("Enter a price between 5 and 8 : ", 5, 8);
            } else if (rowNumber == 2) {
                seatNumber = inputValidator("Please Enter a seat number : ", 1, 16);
                price = priceValidator("Enter a price between 10 and 13 :", 10, 13);
            } else {
                seatNumber = inputValidator("Please Enter a seat number : ", 1, 20);
                price = priceValidator("Enter a price between 15 and 18 : ", 15, 18);
            }

            if (!isNotOccupied(rowNumber, seatNumber)) {
                System.out.println("This Seat has been already occupied.");
            }
            else {
                String firstName = userDetailsInput("what is your first name: ");
                String lastName = userDetailsInput("what is your last name: ");
                String email = inputEmail();
                markSeatAsBooked(rowNumber, seatNumber);
                Person person = new Person(firstName, lastName, email);
                Ticket ticket = new Ticket(rowNumber, seatNumber, price, person);
                buyers.add(ticket);
            }
            while (true){
                String wantToContinue=userDetailsInput("Do you want to book another ticket?(Yes/No) : ");
                if (wantToContinue.equalsIgnoreCase("No")){
                    run=false;
                    break;
                }
                else if (wantToContinue.equalsIgnoreCase("Yes")) {
                    break;
                }
                else{
                    System.out.println("Invalid input.");
                }
            }
        }
    }
    public static void formattedSeatingPlan(int[] Row){
    //This method is to print a row in a seating plan nicely.
        int count=1;
        for (int element :Row){
            if (element ==1 && Row.length/2 == count){
                System.out.print("X ");
            }
            else if(element ==0 && Row.length/2 == count){
                System.out.print("0 ");
            }else if(element ==1 ){
                System.out.print("X");
            } else {
                System.out.print("0");
            }
            count++;
        }
        System.out.println();
    }
    public  static void printSeatingPlan(){
    //with the help of formattedSeatingPlan method,in this method I have printed the whole seating plan.
        System.out.print("       ");
        System.out.println("***********");
        System.out.print("       ");
        System.out.println("*  STAGE  *");
        System.out.print("       ");
        System.out.println("***********");
        System.out.print("      ");
        formattedSeatingPlan(Row1);
        System.out.print("    ");
        formattedSeatingPlan(Row2);
        System.out.print("  ");
        formattedSeatingPlan(Row3);
    }
    public static void seatReset(int rowNum, int seatNum){
    //if the user needs to cancel a ticket,with the help of this method I have marked seat as 0 if it was occupied before.
        try{
            if (rowNum==1){
                if (Row1[seatNum-1]==1){
                    Row1[seatNum-1]=0;
                }
            }
            else if(rowNum==2){
                if (Row2[seatNum-1]==1){
                    Row2[seatNum-1]=0;
                }
            }
            else if(rowNum==3) {
                if (Row3[seatNum-1]==1){
                    Row3[seatNum-1]=0;
                }
            }

        }catch (IndexOutOfBoundsException e){
            System.out.println("This seat is already vacant.");
        }
    }
    public static void cancelTicket(){
    //If user needs to cancel the ticket it happens with help of this method.
        int rowNumber = inputValidator("Please Enter a row number : ",1,3);
        int seatNumber;
        if(rowNumber ==1){
            seatNumber=inputValidator("Please Enter a seat number : ",1,12);

        }else if(rowNumber==2){
            seatNumber=inputValidator("Please Enter a seat number : ",1,16);

        }else {
            seatNumber=inputValidator("Please Enter a seat number : ",1,20);
        }
        int count=0;
        try {
            for (Ticket ticket : buyers) {
                if (ticket.seat == seatNumber && ticket.row == rowNumber) {
                    seatReset(rowNumber, seatNumber);
                    System.out.println("Seat has successfully cancelled.");
                    break;
                }
                count++;
            }
            buyers.remove(count);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("This seat is already vacant.");
        }
    }
    public static void listAvailableSeats(int[]Row){
        int count =1;
        for(int element:Row){
            if(element==0){
                if(count==Row.length){
                    System.out.print(count);
                }
                else{
                    System.out.print(count + ",");
                }
            }
            count++;
        }
    }
    public static void showAvailable(){
        System.out.print("Available seats in row 1 : ");
        listAvailableSeats(Row1);
        System.out.println();
        System.out.print("Available seats in row 2 : ");
        listAvailableSeats(Row2);
        System.out.println();
        System.out.print("Available seats in row 3 : ");
        listAvailableSeats(Row3);
        System.out.println();
    }
    public static void fileWriter(int [] row1,int [] row2,int [] row3){
        int [][] rows={row1,row2,row3};
        try{
            FileWriter writer =new FileWriter("info.txt");
            writer.write("");
            for (int[]row:rows){
                for (int i:row){
                    writer.append(Integer.toString(i));
                }
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void arrayCreator(ArrayList<Integer>row1Loaded, int [] row){
        int i=0;
        for (int seat:row1Loaded){
            row[i]=seat;
            i++;
        }
    }
    public static void load(int [] row1,int [] row2,int [] row3) {
        ArrayList<Integer>row1Loaded=new ArrayList<>();
        ArrayList<Integer>row2Loaded=new ArrayList<>();
        ArrayList<Integer>row3Loaded=new ArrayList<>();
        ArrayList<Character> read_data=new ArrayList<>();
        int count =1;
        String convertedCharValue;
        try{
            FileReader reader = new FileReader("info.txt");
            int data =reader.read();
            while (data != -1) {
                read_data.add((char)data);
                data = reader.read();
            }
            for (char i :read_data) {
                convertedCharValue = String.valueOf(i);
                if(convertedCharValue.equals("0")){
                    if (count<=12)
                    {
                        row1Loaded.add(0);
                        count++;
                    }
                    else if (count<=28)
                    {
                        row2Loaded.add(0);
                        count++;
                    }
                    else if(count<=48) {
                        row3Loaded.add(0);
                        count++;
                    }
                } else if (convertedCharValue.equals("1")) {
                    if (count<=12) {
                        row1Loaded.add(1);
                        count++;
                    }
                    else if (count<=28) {
                        row2Loaded.add(1);
                        count++;
                    }
                    else if(count<=48) {
                        row3Loaded.add(1);
                        count++;
                    }
                }
            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        arrayCreator(row1Loaded,row1);
        arrayCreator(row2Loaded,row2);
        arrayCreator(row3Loaded,row3);
        System.out.println("----Data load Successfully----");
        System.out.println();
        printSeatingPlan();
    }
    public static void printInfo(){
        for (Ticket ticket: buyers){
            ticket.print();
        }
    }
    public static void calculateTotalPrice(){
        int totalPrice=0;
        for (Ticket ticket: buyers){
            totalPrice+=ticket.price;
        }
        System.out.println("Total price is ₤"+totalPrice);
    }
    public  static void sortTickets(){
        ArrayList<Ticket> sortedList = new ArrayList<>(buyers);
        System.out.println("Ticket details(Sorted)");
        // One by one move boundary of unsorted sub array
        for (int i = 0; i < sortedList.size()-1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i+1; j < sortedList.size(); j++)
                if (sortedList.get(j).price < sortedList.get(minIndex).price)
                    minIndex = j;
            // Swap the found minimum element with the first
            // element
            Ticket temp = sortedList.get(minIndex);
            sortedList.set(minIndex, sortedList.get(i));
            sortedList.set(i,temp);
        }
        for (Ticket element: sortedList){
            element.print();
        }
    }
}

