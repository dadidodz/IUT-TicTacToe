package communicationJoueur;

import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;
import java.net.MalformedURLException;

/**
argv[0] : numero du port où se trouve l’annuaire local ;
argv[1] : adresse de la machine d’où on lance le client local ;
argv[2] : adresse de la machine où se trouve le joueur distant ;
argv[3] : numéro du port où se trouve l’annuaire du joueur distant ;
argv[4] : true si le joueur joue avec la X et false sinon.
*/
public class TicTacToeServer {
	
	public static void main(String[] args) throws RemoteException {
		
		TicTacToeImpl objetDist = new TicTacToeImpl(args[2], args[3], Boolean.valueOf(args[4]));
		try {
			java.rmi.registry.LocateRegistry.createRegistry(Integer.valueOf(args[0]));
			java.rmi.Naming.bind("rmi://" + args[1] + ":" + args[0] + "/joueur", (TicTacToe)objetDist);
		}
		catch (NumberFormatException | RemoteException | AlreadyBoundException | MalformedURLException ex){
			ex.printStackTrace();
		}
		
	}
}