package rmi;

import entities.Product;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import services.ProductService;

/**
 *
 * @author Christian
 */
public class ClientRMI {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ProductService productService;
        String serverIpAddress = "localhost";
        int serverPort = 8888, id;
        try {
            Registry registry = LocateRegistry.getRegistry(serverIpAddress, serverPort);
            productService = (ProductService) (registry.lookup("ServerRMI"));        
            do {       
                System.out.println(productService.showMenu());
                
                System.out.println("Find product name : ...");
                List<Product> products = productService.findProduct(scan.nextLine());
                
                id = 1;
                if (products.size() == 0) {
                    System.out.println("No products found ...");
                } else {
                    for(Product product : products){
                    System.out.print(id++ + "- " + 
                            product.getName()+ "\t\t" + 
                            product.getPrice() + "\t" + 
                            product.getStock());
                    System.out.println();
                    for (int i = 0; i < 48; i++) {
                        System.out.print("-");
                    }
                    System.out.println();
                    }
                }
                
            } while (true);
        } catch (RemoteException | NotBoundException  e) {
        }        
    }
}
