import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Shared data between threads.
 * No synchronized blocks, semaphores, or wait/notify.
 */
public class Store {


    public static final int STORE_CAPACITY = 8;
    public static final int NUM_CUSTOMERS = 20;

    public static final int NUM_SELF_CHECKOUT = 3;



    // Store entrance line

    public static final CustomerThread[] waitingLine =
            new CustomerThread[NUM_CUSTOMERS];


    public static final AtomicInteger lineTail =
            new AtomicInteger(0);


    public static final AtomicInteger lineHead =
            new AtomicInteger(0);



    // Checkout line

    public static final CustomerThread[] checkoutLine =
            new CustomerThread[NUM_CUSTOMERS];


    public static final AtomicInteger checkoutTail =
            new AtomicInteger(0);


    public static final AtomicInteger checkoutHead =
            new AtomicInteger(0);



    // Registers

    public static final AtomicBoolean[] registers =
            {
                    new AtomicBoolean(true),
                    new AtomicBoolean(true),
                    new AtomicBoolean(true)
            };



    // Store status

    public static final AtomicBoolean storeOpen =
            new AtomicBoolean(false);


    public static final AtomicInteger customersInsideStore =
            new AtomicInteger(0);


    public static final AtomicInteger customersEnteredTotal =
            new AtomicInteger(0);




    // Cafeteria, part C addition



    public static final int NUM_SEATS = 4;


    public static final int NUM_TABLES =
            NUM_CUSTOMERS / NUM_SEATS;



    public static final CustomerThread[][] tables =
            new CustomerThread[NUM_TABLES][NUM_SEATS];



    public static final AtomicInteger[] tableSeats =
            new AtomicInteger[NUM_TABLES];



    public static final AtomicInteger customersFinishedEating =
            new AtomicInteger(0);



    public static final AtomicInteger customersExited =
            new AtomicInteger(0);



    static
    {
        for(int i = 0; i < NUM_TABLES; i++)
        {
            tableSeats[i] = new AtomicInteger(0);
        }
    }



    private Store()
    {

    }

}