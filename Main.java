/**
 * Main thread: creates and starts all customer threads, then the manager,
 * and waits for everyone to finish.
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {


        long startTime = System.currentTimeMillis();


        CustomerThread.time = startTime;
        ManagerThread.time = startTime;



        CustomerThread[] customers = new CustomerThread[Store.NUM_CUSTOMERS];


        for (int i = 0; i < Store.NUM_CUSTOMERS; i++) {

            customers[i] = new CustomerThread(i);

            customers[i].start();

        }





        ManagerThread manager = new ManagerThread();
        manager.start();


        // Wait until all customers finish eating
        for(CustomerThread c : customers)
        {

            c.join();

        }






        // ==============================
        // Customers leave cafeteria
        // ==============================


        System.out.println();

        System.out.println("========== CAFETERIA EXIT ==========");


        for(int table = 0;
            table < Store.NUM_TABLES;
            table++)
        {


            System.out.println("Table " +(table+1) +" customers leaving:");

            CustomerThread[] currentTable = Store.tables[table];
            /*
             Customers leave in decreasing order
             of customer ID/name.

             Example:

             Customer-7
             Customer-5
             Customer-1

            */


            for(int i = Store.NUM_SEATS-1;
                i >= 0;
                i--)
            {


                if(currentTable[i] != null)
                {


                    if(currentTable[i].isAlive())
                    {

                        currentTable[i].join();

                    }

                    currentTable[i].msg("left the cafeteria");
                    Store.customersExited.incrementAndGet();
                }

            }

            System.out.println();

        }


        // Wait for manager to finish

        manager.join();

        System.out.println("Simulation complete - all threads terminated naturally."
        );


    }

}
