package rmi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Product;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
         //write();
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
    public List<Product> findProduct(String toFind) throws RemoteException {
              
        List<Product> filterProducts = getProducts()
                .stream()
                .filter(p -> p.getName().contains(toFind.toUpperCase()))
                .collect(Collectors.toList());
        
        return filterProducts;
    }            

    @Override
    public String showMenu() throws RemoteException {
        
        return "============================\n" +
                "\tPHARMACY\n" +
                "1. CheckProduct" +
                "Search: ";        
    }        

    @Override
    public boolean buyProduct(int id, int quantity) throws RemoteException {
        Gson gson = new Gson();
        List <Product> products = getProducts();
        Product product = products
                .stream()
                .filter(p -> p.getId() == id && p.getStock() >= quantity)
                .findFirst()
                .orElse(null);
        
        if (product == null) return false;
        for (Product obj : products) {
            if (obj.getId() == id) {
                obj.setStock(obj.getStock() - quantity);
                break;
            }
        } 
        String strJson = gson.toJson(products);
        try { //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter(DATAPATH);
            writer.write(strJson);           

       } catch (IOException e) {
       }
        
        return true;
    }
    
    private FileReader getDataPath() throws FileNotFoundException {
        return new FileReader(DATAPATH);
    } 
    
    private List<Product> getProducts() {
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
    
    private static void write() {
        Gson gson = new Gson();
        /*Gson gson = new GsonBuilder().create();
        gson.toJson(users, writer);*/
        String strJson = gson.toJson("qwe");
        try { //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter("D:/Netbeans/Projects/PharmacyRMI/src/data/datax.json");
            writer.write(strJson);           

       } catch (IOException e) {
       }
    }
}
