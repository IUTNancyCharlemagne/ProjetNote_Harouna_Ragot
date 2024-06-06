import raytracer.Image;
import raytracer.Scene;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface CalculInterface extends Remote
{
    public Image calculer(Scene scene, Coordonnees coor) throws RemoteException, ServerNotActiveException;
}