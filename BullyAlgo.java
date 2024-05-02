import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class BullyAlgo{
  public static void main(String[] args){
    System.out.println("\n***   ELECTION ALGORITHM: Bully Algorithm    ***\n");
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the number of Processes:");
    int n = sc.nextInt();
  ArrayList<Boolean> processes = new ArrayList<Boolean>();
  int prev_request = -1;
  for(int i = 0;i<n;i++){
    processes.add(new Random().nextBoolean());
  }
  processes.set(n-1,false);
  System.out.println("\n Co-Ordinator Process: "+ n +"\nCo-Ordinator Process Now Dead: " + n);
  int request = new Random().nextInt(n-1);
  System.out.println(processes);
  System.out.println(request+1);
  while(request < n-1 && prev_request != request) {
     System.out.println("\n\nRequesting Process: "+ (request+1));
     for(int i = request+1;i<n;i++)
     {
        System.out.println("\n\t "+ (request+1)+"==== Election ===>"+ (i + 1));
     }
     System.out.println();
     prev_request = request;
     request = BullyAlgo(request,n,processes);
  }
  System.out.println("\n\nElected Co-Ordinator Process : "+ (request + 1) + "\nSending Message To All Other Process...");
  for(int i = 0;i<request;i++){
    System.out.println("\n\tProcess" + (request + 1) + "==== Co-Ordinator ===>" + (i+1));
  }
  System.out.println("\nAll Messages Sent!!!");
}
public static int BullyAlgo(int request, int n, ArrayList<Boolean> processes){
    for(int j = request + 1;j<n;j++){
        if(processes.get(j)){
            System.out.println("\n\tReply from process: "+(j + 1) + " OK");
            request = j;
            break;
        }
    }
    return request;
}
}
