import java.util.Scanner;
public class LoadBalancing {
    public static void main(String[] args) {
        System.out.print("\n*** LOAD BALANCING ***\n");
        System.out.print("-----------------------\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of Servers: ");
        int numServers = sc.nextInt();
        System.out.print("Enter the number of Processes: ");
        int numProcesses = sc.nextInt();
        while (true) {
            printServerLoad(numServers, numProcesses);
            displayMenu();
            System.out.print("> ");
            int choice = sc.nextInt();
            int temp;
            switch (choice) {
                case 1:
                    System.out.println("Enter number of servers to be added: ");
                    temp = sc.nextInt();
                    numServers += temp;
                    System.out.println(temp + " servers added successfully!");
                    break;
                case 2:
                    System.out.println("Enter number of servers to be removed: ");
                    temp = sc.nextInt();
                    numServers -= temp;
                    System.out.println(temp + " servers removed successfully!");
                    break;
                case 3:
                    System.out.println("Enter number of processes to be added: ");
                    temp = sc.nextInt();
                    numProcesses += temp;
                    System.out.println(temp + " processes added successfully!");
                    break;
                case 4:
                    System.out.println("Enter number of processes to be removed: ");
                    temp = sc.nextInt();
                    numProcesses -= temp;
                    System.out.println(temp + " processes removed successfully!");
                    break;
                case 5:
                    System.out.println("Exiting");
                    sc.close();
                    return;
                default:
                    break;
            }     }    }
    static void printServerLoad(int numServers, int numProcesses) {
        int processesPerServer = numProcesses / numServers;
        int extraProcesses = numProcesses % numServers;
        int i = 0;
        for (i = 0; i < extraProcesses; i++)
            System.out.println("Server " + (i + 1) + " has " + (processesPerServer + 1) + " processes");
        for (; i < numServers; i++)
            System.out.println("Server " + (i + 1) + " has " + processesPerServer + " processes");
    }
    static void displayMenu() {
        System.out.println("\n1. Add Server");
        System.out.println("2. Remove Server");
        System.out.println("3. Add Processes");
        System.out.println("4. Remove Processes");
        System.out.println("5. Exit");
    }}
