package testsUnitaires;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.ImpossibleDeSupprimerRoot;
import personnel.Ligue;

class TestEmploye {
	// GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	static final String PASSWORD = "azerty";

	@Test
	void testRemoveEmployeRoot() {
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> {
			root.remove();
		});
	}
	
	/*
	 * Ce test permet de supprimer l'employe d'une ligue
	 */
	@Test
	void testRemoveEmploye() {
		Ligue ligue = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue.addEmploye("toto", "toto", "toto@toto.com", PASSWORD, null, null);
		assertTrue(ligue.getEmployes().contains(employe));
		ligue.setAdministrateur(employe);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
		assertEquals(this.gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}
	/*
	 * Ce test permet de verifier si le bon employe
	 * a ete supprime
	 */
	
	@Test
	void testRemoveEmploye1() {
		Ligue ligue = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue.addEmploye("toto", "toto", "toto@toto.com", PASSWORD, null, null);
		Employe employe1 = ligue.addEmploye("toto1", "toto1", "toto1@toto.com", PASSWORD, null, null);
		assertTrue(ligue.getEmployes().contains(employe));
		assertTrue(ligue.getEmployes().contains(employe1));
		
		ligue.setAdministrateur(employe1);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
		assertNotEquals(this.gestionPersonnel.getRoot(), ligue.getAdministrateur());
		assertEquals(employe1, ligue.getAdministrateur());
	}
	/*
	 * Ce test permet de verifier la methode checkPassword
	 */
	@Test
	void testcheckPassword(){
		Ligue ligue = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue.addEmploye("toto", "toto", "toto@toto.com", PASSWORD, null, null);
		assertTrue(employe.checkPassword(PASSWORD));
		
		assertFalse(employe.checkPassword("tata"));
	}
	
	/*
	 * Ce test permet de verifier si l'employe est admin ou non
	 */
	@Test
	void testEstAdmin() {
		Ligue ligue = this.gestionPersonnel.addLigue(1, "Flechete");
		Employe employe = ligue.addEmploye("toto", "toto", "toto@toto.com", PASSWORD, null, null);
		assertFalse(employe.estAdmin(ligue));
		
		ligue.setAdministrateur(employe);
		assertTrue(employe.estAdmin(ligue));
		
		Ligue ligue1 = this.gestionPersonnel.addLigue(1, "Foot");
		assertFalse(employe.estAdmin(ligue1));
	}
	
	
}
