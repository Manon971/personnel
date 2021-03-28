package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gerer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administree par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Selectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employe", "a",
				() -> 
				{
					ligue.addEmploye(-1, getString("nom : "), 
						getString("prenom : "), 
						getString("mail : "), 
						getString("password : "),
						getString("dateArrivee : "),
						getString("dateDepart : "));
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gerer les employes de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmployes(ligue));
		
		menu.addBack("q");
		return menu;
	}

	private List<Employe> supprimerEmploye(final Ligue ligue)
	{
		return new List<>("Supprimer un employe", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {element.remove();}
				);
	}
	
	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		/*return new List<Employe>("modifier l'administrateur", "f", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {ligue.setAdministrateur(element);}
				);*/
		return new List<Employe>("modifier l'administrateur", "f", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> afficherAdmin(ligue, element));
	}	
	
	private Option afficherAdmin(final Ligue ligue, final Employe employe)
	{
		return new Option("test" + employe.getNom(), "l", () -> {ligue.setAdministrateur(employe);});
	}
	
	
	
	private Menu changerAdministrateur(Employe employe)
	{
		Menu menu = new Menu("modifier l'administrateur " + employe.getNom(), "f");
		menu.add(afficher(employe));
		menu.add(changerNom(employe));
		menu.add(changerPrenom(employe));
		menu.add(changerMail(employe));
		menu.add(changerPassword(employe));
		menu.addBack("q");
		return menu;
	}
	
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employe", "l", () -> {System.out.println(employe);});
	}
	
	
	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prenom", "p", () -> {employe.setPrenom(getString("Nouveau prenom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
	private List<Employe> selectionnerEmployes(final Ligue ligue)
	{
		return new List<>("selectionner un employe", "d", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}
	
//	private Menu selectionnerEmploye(Ligue ligue)
//	{
//		Menu menu = new Menu("Selectionner les employes de " + ligue.getNom(), "d");
//		menu.add(modifierEmploye(ligue));
//		menu.add(supprimerEmploye(ligue));
//		menu.addBack("q");
//		return menu;
//	}
	
	
	
	private List<Employe> modifierEmploye(final Ligue ligue)
	{
		return new List<>("Modifier un employe", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
}
