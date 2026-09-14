import java.util.concurrent.ThreadLocalRandom;


/**
 * Manager opens store after customers arrive.
 * Manager terminates after all customers exit.
 */
public class ManagerThread extends Thread {


    public static long time = System.currentTimeMillis();



    public ManagerThread()
    {
        setName("Manager");
    }



    public void msg(String message){

        System.out.println("["+ (System.currentTimeMillis()-time) +"] " +getName() +": " +message);

    }






    @Override
    public void run(){


        msg("watching the front door for customers...");

        // wait for customers
        while(Store.lineTail.get()==0){

        }

        int delay = ThreadLocalRandom.current().nextInt(20,80);

        msg("customers are lining up, opening the doors in " +delay+"ms");


        try{
            Thread.sleep(delay);
        }

        catch(Exception e){
            Thread.currentThread().interrupt();
        }





        Store.storeOpen.set(true);

        msg("store is now OPEN (capacity " +Store.STORE_CAPACITY +")");



        // Wait until all customers entered

        while(Store.customersEnteredTotal.get() < Store.NUM_CUSTOMERS){

        }



        msg("all " +Store.NUM_CUSTOMERS +" customers have entered the store");



        // ==============================
        // New Homework 3C addition
        // ==============================


        // Wait until all customers leave cafeteria

        while(Store.customersExited.get() < Store.NUM_CUSTOMERS){

        }
        msg("all customers have exited the cafeteria");
        msg("manager is leaving the store");



    }

}