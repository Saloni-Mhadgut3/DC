public class CalculatorImpl 
    extends 
      java.rmi.server.UnicastRemoteObject 
    implements Calculator {  
    public CalculatorImpl() 
        throws java.rmi.RemoteException { 
        super();     } 
    public long add(long a, long b) 
        throws java.rmi.RemoteException { 
        System.out.println("Saloni Performing Addition");
            return a + b;     }  
    public long sub(long a, long b) 
        throws java.rmi.RemoteException { 
            System.out.println("Saloni Performing Subtraction");
            return a - b;     }  
    public long mul(long a, long b) 
        throws java.rmi.RemoteException { 
            System.out.println("Saloni Performing Mutliplication");
            return a * b; 
    }  
    public long div(long a, long b) 
        throws java.rmi.RemoteException { 
            System.out.println("Saloni Performing Division");
            return a / b; 
    } 
} 
