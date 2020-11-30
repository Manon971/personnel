package testsUnitaires;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import personnel.DroitsInsuffisants;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.ImpossibleDeSupprimerRoot;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


class TestLigue 
{
	/*
	 * Initialisation des variables de la classe
	 */
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
	static final String PASSWORD = "azerty";
	
	/*
	 * Ce test permet de verifier si la fonction AddLigue fonctionne
	 */
	@Test
	void testAddLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		assertEquals("Flechettes", ligue.getNom());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
	}

	/*
	 * Ce test permet de verifier si la fonction AddLigue fonctionne
	 * avec un Id
	 */
	@Test
	void testAddLigueById() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(1, "Flechettes");
		assertEquals("Flechettes", ligue.getNom());
		//assertEquals(1, ligue.getId());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
	}
	
	/*
	 * Ce test permet de verifier si la fonction AddEmploye fonctionne
	 */
	@Test
	void testAddEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gerard", "g.bouchard@gmail.com", PASSWORD, "01/01/2010", "02/01/2020"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	/*
	 * Ce test permet de verifier si la fonction GetLigues fonctionne
	 */
	@Test
	void testGetLigues() throws SauvegardeImpossible{
		gestionPersonnel.addLigue("Flechettes");
		gestionPersonnel.addLigue("Foot");
		
		assertEquals(2, gestionPersonnel.getLigues().size());
	}

	/*
	 * Ce test permet de verifier si la fonction AddLigue fonctionne
	 */
	@Test
	void testGetLigue() throws SauvegardeImpossible{
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		Employe administrateur = ligue.addEmploye("Admin", "Admin", "admin@gmail.com", PASSWORD, "01/03/2000", "02/03/2001"); 
		ligue.setAdministrateur(administrateur);
		
		Employe employe = ligue.addEmploye("Bouchard", "Gerard", "g.bouchard@gmail.com", PASSWORD, "01/03/2000", "02/03/2001");
		
		Ligue ligueByAdmin = gestionPersonnel.getLigue(administrateur);
		assertNotNull(ligueByAdmin);
		assertEquals(ligue, ligueByAdmin);
		
		ligueByAdmin = gestionPersonnel.getLigue(employe);
		assertNull(ligueByAdmin);
	}
	
	/*
	 * Ce test permet de verifier si la fonction RemoveLigues fonctionne
	 */
	@Test
	void testRemoveLigues() throws SauvegardeImpossible{
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		assertEquals("Flechettes", ligue.getNom());
		ligue.remove();
		assertTrue(gestionPersonnel.getLigues().isEmpty());
	}
	
	/*
	 * Ce test permet de verifier si la fonction SetAdministrateur fonctionne
	 */
	@Test
	void testSetAdministrateur() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		Ligue ligue1 = gestionPersonnel.addLigue("Foot");
		Employe employe = ligue.addEmploye("toto", "toto", "toto@toto.com", PASSWORD, "01/03/2000", "02/03/2001");
		assertThrows(DroitsInsuffisants.class, () -> {
			ligue1.setAdministrateur(employe);
		});
	}
	
	
	
}
