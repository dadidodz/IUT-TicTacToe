package communicationJoueur;

public interface TicTacToe extends java.rmi.Remote {
	
	public void propagerCoup(final int ligne, final int colonne) throws java.rmi.RemoteException;
	
}