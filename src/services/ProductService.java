package services;

import entities.Product;
import java.rmi.*;
import java.util.List;

/**
 *
 * @author Christian
 */
public interface ProductService extends Remote {
    public List<Product> findProduct() throws RemoteException;
    public String showMenu() throws RemoteException;
}