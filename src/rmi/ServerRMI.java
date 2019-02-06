package rmi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Product;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.ProductService;

/**
 *
 * @author Christian
 */
public class ServerRMI extends UnicastRemoteObject implements ProductService {

    static final long serialVersionID = 0012301222;
    private final int PORT = 8888;
    private final String DATAPATH = "D:/Netbeans/Projects/PharmacyRMI/src/data/data.json";
            
    public ServerRMI() throws RemoteException {}
    
    public static void main(String[] args) throws RemoteException {
               
        (new ServerRMI()).InitServer();
           
    }
    
    public void InitServer() {
        try {
            String addressIP = (InetAddress.getLocalHost().toString());
            System.out.println("Server up ..." + addressIP + ":" + PORT);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind("ServerRMI", (ProductService) this);
        } catch (UnknownHostException | RemoteException | AlreadyBoundException e) {
        }
    }   
      
    @Override
    public List<Product> findProduct() throws RemoteException {
        Gson gson = new Gson();
                 
        TypeToken<List<Product>> token = new TypeToken<List<Product>>() {};
        ArrayList<Product> products = null;
        
        try {
            products = gson.fromJson(getDataPath(), token.getType());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return products;
    }            

    @Override
    public String showMenu() throws RemoteException {
        
        return "============================\n" +
                "\tPHARMACY\n" +
                "1. CheckProduct";
        
    }
    
    private FileReader getDataPath() throws FileNotFoundException {
        return new FileReader(DATAPATH);
    } 
}
