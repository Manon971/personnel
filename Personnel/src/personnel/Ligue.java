package personnel;

import java.io.Console;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represente une ligue. Chaque ligue est reliee e  une liste
 * d'employes dont un administrateur. Comme il n'est pas possible
 * de creer un employe sans l'affecter e  une ligue, le root est 
 * l'administrateur de la ligue jusqu'e  ce qu'un administrateur 
 * lui ait ete affecte avec la fonction {@link #setAdministrateur}.
 */

public class Ligue implements Serializable, Comparable<Ligue>
{
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String nom;
	private SortedSet<Employe> employes;
	private Employe administrateur;
	private GestionPersonnel gestionPersonnel;
	
	/**
	 * Cree une ligue.
	 * @param nom le nom de la ligue.
	 */
	
	Ligue(GestionPersonnel gestionPersonnel, String nom) throws SauvegardeImpossible
	{
		this(gestionPersonnel, -1, nom);
		this.id = gestionPersonnel.insert(this); 
	}

	Ligue(GestionPersonnel gestionPersonnel, int id, String nom)
	{
		this.nom = nom;
		employes = new TreeSet<>();
		this.gestionPersonnel = gestionPersonnel;
		administrateur = gestionPersonnel.getRoot();
		this.id = id;
	}

	/**
	 * Retourne le nom de la ligue.
	 * @return le nom de la ligue.
	 */

	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom.
	 * @param nom le nouveau nom de la ligue.
	 */

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne l'administrateur de la ligue.
	 * @return l'administrateur de la ligue.
	 */
	
	public Employe getAdministrateur()
	{
		return administrateur;
	}

	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Leve DroitsInsuffisants si l'administrateur n'est pas 
	 * un employe de la ligue ou le root. Revoque les droits de l'ancien 
	 * administrateur.
	 * @param administrateur le nouvel administrateur de la ligue.
	 */
	
	public void setAdministrateur(Employe administrateur)
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		if (administrateur != root && administrateur.getLigue() != this)
			throw new DroitsInsuffisants();
		this.administrateur = administrateur;
	}

	/**
	 * Retourne les employes de la ligue.
	 * @return les employes de la ligue dans l'ordre alphabetique.
	 */
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}

	/**
	 * Ajoute un employe dans la ligue. Cette methode 
	 * est le seul moyen de creer un employe.
	 * @param nom le nom de l'employe.
	 * @param prenom le prenom de l'employe.
	 * @param mail l'adresse mail de l'employe.
	 * @param password le password de l'employe.
	 * @return l'employe cree. 
	 */

	public Employe addEmploye(String nom, String prenom, String mail, String password, String dateArrivee, String dateDepart)
	{
		/*
		 * Transforme la chaine de caractere en Date
		 */
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
		
			Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password, dateArrivee == null ? null : simpleDateFormat.parse(dateArrivee), dateDepart == null ? null : simpleDateFormat.parse(dateDepart));
			if (employe.getdateDepart().compareTo(employe.getdateArrivee())>0)
				{
				employes.add(employe);
				return employe;
				}
			else {
				System.out.println("La date de depart doit etre superieur a la date de depart");
				return null;
			}
				
			
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
		
		
		
	}
	
	void remove(Employe employe)
	{
		employes.remove(employe);
	}
	
	/**
	 * Supprime la ligue, entrae®ne la suppression de tous les employes
	 * de la ligue.
	 */
	
	public void remove()
	{
		GestionPersonnel.getGestionPersonnel().remove(this);
	}
	

	@Override
	public int compareTo(Ligue autre)
	{
		return getNom().compareTo(autre.getNom());
	}
	
	@Override
	public String toString()
	{
		return nom;
	}
}
