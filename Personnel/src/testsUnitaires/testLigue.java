package testsUnitaires;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
	static final String PASSWORD = "azerty";
	
	@Test
	void testAddLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
	}

	@Test
	void testAddLigueById() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue(1, "Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
		//assertEquals(1, ligue.getId());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
	}
	
	@Test
	void testAddEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", PASSWORD); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	@Test
	void testGetLigues() throws SauvegardeImpossible{
		gestionPersonnel.addLigue("Fléchettes");
		gestionPersonnel.addLigue("Foot");
		
		assertEquals(2, gestionPersonnel.getLigues().size());
	}

	@Test
	void testGetLigue() throws SauvegardeImpossible{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe administrateur = ligue.addEmploye("Admin", "Admin", "admin@gmail.com", PASSWORD); 
		ligue.setAdministrateur(administrateur);
		
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", PASSWORD);
		
		Ligue ligueByAdmin = gestionPersonnel.getLigue(administrateur);
		assertNotNull(ligueByAdmin);
		assertEquals(ligue, ligueByAdmin);
		
		ligueByAdmin = gestionPersonnel.getLigue(employe);
		assertNull(ligueByAdmin);
	}
	
	@Test
	void testRemoveLigues() throws SauvegardeImpossible{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
		ligue.remove();
		assertTrue(gestionPersonnel.getLigues().isEmpty());
	}
	
	
}
