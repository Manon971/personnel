package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.Passerelle;
import personnel.SauvegardeImpossible;

public class JDBC implements Passerelle 
{
	private static final String SELECT_REQUEST = "select id_ligue, nom_ligue from ligue";
	private static final String INSERT_REQUEST = "insert into ligue (nom_ligue) values(?)";
	private static final String SELECT_EMPLOYE_BYLIGUE = "select id_employe, nom_employe, prenom_employe, mail_employe, password_employe, date_arrivee, date_depart, est_admin, est_root, id_ligue from employe";
	private static final String UPDATE_LIGUE_NOADMIN = "update employe set est_admin = 0 where id_ligue = (?)";
	private static final String UPDATE_LIGUE_ADMIN = "update employe set est_admin = 1 where id_ligue = (?) and id_employe = (?)";
	
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(ConnexionBDD.getDriverClassName());
			connection = DriverManager.getConnection(ConnexionBDD.getUrl(), ConnexionBDD.getUser(), ConnexionBDD.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installe.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try (Statement instruction = connection.createStatement())
		{
			Map<Integer, Ligue> ligueList = new HashMap<>();
			try (ResultSet ligues = instruction.executeQuery(SELECT_REQUEST)) {
				while (ligues.next()) {
					ligueList.put(ligues.getInt("id_ligue"), gestionPersonnel.addLigue(ligues.getInt("id_ligue"), ligues.getString("nom_ligue")));
				}
			}
			
			
			try (ResultSet employes = instruction.executeQuery(SELECT_EMPLOYE_BYLIGUE)) {
				while (employes.next()) {
					Ligue ligue = ligueList.get(employes.getInt("id_ligue"));
					if (ligue != null) {
						Employe employe = ligue.addEmploye(employes.getInt("id_employe"), employes.getString("nom_employe"), employes.getString("prenom_employe"), employes.getString("mail_employe"), employes.getString("password_employe"),
								this.formatDate(employes.getDate("date_arrivee")), this.formatDate(employes.getDate("date_depart")));
						if (employes.getBoolean("est_admin")) {
							ligue.setAdministrateur(employe);
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}
	
	private String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");  
		return dateFormat.format(date);  
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try (PreparedStatement instruction = connection.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS))
		{
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			try(ResultSet id = instruction.getGeneratedKeys()){
				if (id.next()) {
					return id.getInt(1);
				} else {
					return 0;
				}
			}
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void updateLigueAdmin(int ligueId, Employe employe)  throws SauvegardeImpossible{
		try (PreparedStatement instruction = connection.prepareStatement(UPDATE_LIGUE_NOADMIN)){
			instruction.setInt(1, ligueId);
			instruction.executeUpdate();
		}
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
		try (PreparedStatement instruction = connection.prepareStatement(UPDATE_LIGUE_ADMIN)){
			instruction.setInt(1, ligueId);
			instruction.setInt(2, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
}
