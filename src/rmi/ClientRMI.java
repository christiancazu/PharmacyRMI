package rmi;

import entities.Product;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import services.ProductService;

/**
 *
 * @author Christian
 */
public class ClientRMI {
    public static void main(String[] args) {
        ProductService productService;
        String serverIpAddress = "localhost";
        int serverPort = 8888;
        try {
            Registry registry = LocateRegistry.getRegistry(serverIpAddress, serverPort);
            productService = (ProductService) (registry.lookup("ServerRMI"));        
        
            List<Product> received = productService.findProduct();
            String showMenu = productService.showMenu();
            System.out.println(showMenu);
        } catch (RemoteException | NotBoundException  e) {
        }        
    }
}
