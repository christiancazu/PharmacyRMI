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
        int serverPort = 8888, i, selectedId, quantify;
        try {
            Registry registry = LocateRegistry.getRegistry(serverIpAddress, serverPort);
            productService = (ProductService) (registry.lookup("ServerRMI"));        
            
            List<Product> products = null;
            do {       
                System.out.println(productService.showMenu());
                
                System.out.println("Find product name: ");
                products = productService.findProduct(scan.next());
                
                i = 1;
                if (products.size() == 0) {
                    System.out.println("No products found ...");
                } else {
                    for(Product product : products){
                    System.out.print(i++ + "- " + 
                            product.getName()+ "\t\t" +
                            product.getStock());
                    System.out.println();
                    for (int j = 0; j < 48; j++) {
                        System.out.print("-");
                    }
                    System.out.println();
                    }
                    System.out.println("Choose product id: ");
                    selectedId = scan.nextInt();
                    System.out.println("Choose quantify: ");
                    quantify = scan.nextInt();
                    
                    if (productService.buyProduct(products.get(selectedId - 1).getId(), quantify)) {
                        System.out.println("purchase completed !!!");
                    }
                    else {
                        System.out.println("The product no exists or haven`t in stock");
                    }
                                        
                }
                
            } while (true);
        } catch (RemoteException | NotBoundException  e) {
        }        
    }
}
