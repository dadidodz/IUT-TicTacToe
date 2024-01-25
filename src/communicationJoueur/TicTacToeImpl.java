package communicationJoueur ;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tictactoe.GestionClicCase;

public class TicTacToeImpl extends UnicastRemoteObject implements TicTacToe, GestionClicCase{
	
	private String adresseDistante;
	private String portDistant;
	private tictactoe.TicTacToeIHM ihm;
	
	public TicTacToeImpl(String adresseDistante, String portDistant, boolean estPremier) throws RemoteException {
		this.adresseDistante = adresseDistante;
		this.portDistant = portDistant;
		this.ihm = new tictactoe.TicTacToeIHMImpl(estPremier);
		this.ihm.setGestionnaireClic(this);
	}

	@Override
	public boolean traitementCaseCliquee(int lig, int col) {
		//Vérifier que le joueur n'est pas bloqué
		//Vérifier que la case est vide
		//Propager coup joueur distant
		boolean res;
		if(!this.ihm.estBloque() && this.ihm.getContenuCase(lig, col).isEmpty()) {
			try {
				((TicTacToe)java.rmi.Naming.lookup("rmi://" + this.adresseDistante + ":" + 
						this.portDistant + "/joueur")).propagerCoup(lig, col);
				res = true;
			} catch (RemoteException | MalformedURLException | NotBoundException e) {
				e.printStackTrace();
				res = false ;
			}
		}else {
			res = false;
		}
		return res;
	}
		
	@Override
	public void propagerCoup(final int ligne, final int colonne) throws java.rmi.RemoteException {
		this.ihm.jouerDistant(ligne, colonne);
	}
}
