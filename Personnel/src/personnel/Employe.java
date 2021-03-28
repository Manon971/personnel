package personnel;

import java.io.Serializable;
import java.util.Date;

/**
 * Employe d'une ligue hebergee par la M2L. Certains peuvent 
 * e�tre administrateurs des employes de leur ligue.
 * Un seul employe, rattache e� aucune ligue, est le root.
 * Il est impossible d'instancier directement un employe, 
 * il faut passer la methode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	/*
	 * On rajoute les variables de dateArrivee et dateDepart
	 */
	private static final long serialVersionUID = 4795721718037994734L;
	private int id;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private Date dateArrivee = null;
	private Date dateDepart = null;
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, int id, String nom, String prenom, String mail, String password, Date dateArrivee, Date dateDepart)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;

	}
	
	/**
	 * Retourne vrai ssi l'employe est administrateur de la ligue 
	 * passee en parametre.
	 * @return vrai ssi l'employe est administrateur de la ligue 
	 * passee en parametre.
	 * @param ligue la ligue pour laquelle on souhaite verifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employe est le root.
	 * @return vrai ssi l'employe est le root.
	 */
	
	public boolean estRoot()
	{
		return GestionPersonnel.getGestionPersonnel().getRoot() == this;
	}
	
	public int getId()
	{
		return id;
	}

	/**
	 * Retourne le nom de l'employe.
	 * @return le nom de l'employe. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employe.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne le prenom de l'employe.
	 * @return le prenom de l'employe.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prenom de l'employe.
	 * @param prenom le nouveau prenom de l'employe. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne le mail de l'employe.
	 * @return le mail de l'employe.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employe.
	 * @param mail le nouveau mail de l'employe.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retourne vrai ssi le password passe en parametre est bien celui
	 * de l'employe.
	 * @return vrai ssi le password passe en parametre est bien celui
	 * de l'employe.
	 * @param password le password auquel comparer celui de l'employe.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employe.
	 * @param password le nouveau password de l'employe. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
	}
	
	/**
	 * Change la date d'arrivee.
	 * @param dateArrivee la nouvelle date d'arrivee de l'employe. 
	 */
	public Date getdateArrivee()
	{
		return dateArrivee;
	}
	
	/**
	 * Retourne la date d'arrivee de l'employe.
	 * @return la date d'arrivee de l'employe.
	 */
	public void setdateArrivee(Date dateArrivee)
	{
		this.dateArrivee = dateArrivee;
	}
	
	/**
	 * Change le date de depart.
	 * @param dateDepart la nouvelle date de depart de l'employe. 
	 */
	public Date getdateDepart()
	{
		return dateDepart;
	}
	
	/**
	 * Retourne la date de depart de l'employe.
	 * @return la date de depart de l'employe.
	 */
	public void setdateDepart(Date dateDepart)
	{
		this.dateDepart = dateDepart;
	}
	
	
	/**
	 * Retourne la ligue e� laquelle l'employe est affecte.
	 * @return la ligue e� laquelle l'employe est affecte.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employe. Si celui-ci est un administrateur, le root
	 * recupere les droits d'administration sur sa ligue.
	 */
	
	public void remove()
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			ligue.remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
