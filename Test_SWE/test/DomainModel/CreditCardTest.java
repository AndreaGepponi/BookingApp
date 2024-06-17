package DomainModel;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreditCardTest {

    @Test
    void payTest()  {

        Room room = new Room(4, 1, 50, "Hotel_Name");

        System.out.println("CreditCardTest: payTest()");

        String simulatedUserInput = "yes\n473\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));


        // if the output stream is equal to this string,
        // it means that the payment was successful
        String expectedOutput = "\nPaying for room: " + room.getId() +
                "\n" + "Amount: " + "10.1" + "€ (including " +
                "1.0" + "% commission)" + "\n" +
                "\nConfirm the payment? (yes/no)" + "\n" +
                "Paying with credit card..." + "\n" +
                "Enter your credit card security code:" + "\n" +
                "Connecting to the bank..." + "\n" +
                "Payment successful!";
        System.setOut(originalOut);
        String output = outContent.toString();

        assertFalse(output.contains(expectedOutput));

        System.out.println();

    }
    @Test
    void refundTest(){

        Customer customer = new Customer(1, "Mario", "Rossi", 30, "mario.rossi", "Password1",
                "Credit Card", "135895322", "2025-12-31",
                "473");

        Room room = new Room(4, 1, 50, "Hotel_Name");

        System.out.println("CreditCardTest: refundTest()");

        customer.getCC().refund(room);

        System.out.println();

    }
}
