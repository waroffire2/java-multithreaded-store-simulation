import java.util.concurrent.ThreadLocalRandom;


/**
 * Customer thread:
 * 1. Travels to store
 * 2. Waits in FCFS entrance line
 * 3. Shops
 * 4. Rushes to checkout
 * 5. Uses self checkout
 * 6. Goes to cafeteria
 */
public class CustomerThread extends Thread {


    public static long time = System.currentTimeMillis();


    private int linePosition;
    private boolean elderly;
    private int customerID;
    private int tableNumber;

    public CustomerThread(int id){

        customerID = id;
        setName("Customer-" + id);

        // 25% chance elderly
        elderly = ThreadLocalRandom.current().nextInt(4) == 0;
    }



    public int getCustomerID(){
        return customerID;
    }




    public void msg(String message){
        System.out.println(
                "[" + (System.currentTimeMillis()-time) + "] " + getName() + ": " + message);
    }

    @Override
    public void run(){
        // Travel
        int travel = ThreadLocalRandom.current().nextInt(50,150);


        msg("commuting to the store (about " +travel+"ms)...");


        try
        {
            Thread.sleep(travel);
        }
        catch(Exception e)
        {
            Thread.currentThread().interrupt();
        }

        // Store line

        linePosition = Store.lineTail.getAndIncrement();
        Store.waitingLine[linePosition]=this;


        msg("arrived and joined the line (position " +linePosition+")");


        // Enter store
        while(true){
            if(Store.lineHead.get()==linePosition && Store.storeOpen.get()){
                int current = Store.customersInsideStore.get();
                if(current < Store.STORE_CAPACITY){
                    if(Store.customersInsideStore.compareAndSet(current,current+1)){
                        Store.customersEnteredTotal.incrementAndGet();
                        Store.lineHead.incrementAndGet();
                        break;
                    }
                }
            }
        }



        msg("entered the store ("
                +Store.customersInsideStore.get()
                +"/"
                +Store.STORE_CAPACITY
                +" inside now)");

        // Shopping

        int shopTime = ThreadLocalRandom.current().nextInt(50,150);



        msg("shopping for groceries (" +shopTime+"ms)");



        try
        {
            Thread.sleep(shopTime);
        }
        catch(Exception e)
        {
            Thread.currentThread().interrupt();
        }







        // Rush to checkout


        int oldPriority=getPriority();
        setPriority(Thread.MAX_PRIORITY);

        msg("finished shopping and rushing to the self-checkout");



        try
        {
            Thread.sleep(
                    ThreadLocalRandom.current().nextInt(20,60));
        }
        catch(Exception e)
        {
            Thread.currentThread().interrupt();
        }



        setPriority(oldPriority);







        // Elderly advantage


        if(!elderly){

            Thread.yield();
            Thread.yield();

        }

        else{

            msg("is an elderly customer and gets priority at checkout");

        }







        // Checkout line


        int checkoutPosition =
                Store.checkoutTail.getAndIncrement();



        Store.checkoutLine[checkoutPosition]=this;


        msg("joined the self-checkout line (position " +checkoutPosition+")");







        // Wait for turn

        while(Store.checkoutHead.get()!=checkoutPosition){

        }








        // Find register


        int register=-1;


        while(register==-1){


            for(int i=0;
                i<Store.NUM_SELF_CHECKOUT;
                i++){



                if(Store.registers[i]
                        .compareAndSet(true,false)){


                    register=i;

                    break;

                }

            }


            Thread.yield();

        }






        Store.checkoutHead.incrementAndGet();



        msg("is using self-checkout register " +register);


        // Pay
        try
        {

            Thread.sleep(ThreadLocalRandom.current().nextInt(50,100));

        }

        catch(Exception e)
        {

            Thread.currentThread().interrupt();

        }







        Store.registers[register].set(true);



        msg("finished paying");


        // Leave store


        Store.customersInsideStore.decrementAndGet();



        goToCafeteria();



    }







    // ============================
    // Cafeteria
    // ============================



    private void goToCafeteria(){


        msg("going to cafeteria");



        int refreshTime = ThreadLocalRandom.current().nextInt(50,150);



        msg("ordering refreshments (" +refreshTime+"ms)");



        try
        {
            Thread.sleep(refreshTime);
        }
        catch(Exception e)
        {
            Thread.currentThread().interrupt();
        }

        sitAtTable();

    }








    private void sitAtTable(){


        while(true){


            for(int i=0;i<Store.NUM_TABLES;i++){

                int seat = Store.tableSeats[i].get();

                if(seat < Store.NUM_SEATS){

                    if(Store.tableSeats[i].compareAndSet(seat,seat+1)){

                        tableNumber=i;

                        Store.tables[i][seat]=this;

                        msg("sat at table " +(i+1) +" seat " +(seat+1));eat();
                        return;

                    }

                }

            }

        }

    }








    private void eat(){


        int eatTime = ThreadLocalRandom.current().nextInt(100,250);

        msg("eating/drinking (" +eatTime+"ms)");



        try
        {
            Thread.sleep(eatTime);
        }
        catch(Exception e)
        {
            Thread.currentThread().interrupt();
        }



        msg("finished eating");



        Store.customersFinishedEating.incrementAndGet();


    }




}