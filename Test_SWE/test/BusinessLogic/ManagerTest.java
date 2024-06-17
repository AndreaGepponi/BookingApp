package BusinessLogic;

import DomainModel.Manager;
import DomainModel.Structure;
import ORM.ManagerDAO;
import ORM.StructureDAO;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class ManagerTest {

    @Test
    public void StructureOperationsTest() throws SQLException, ClassNotFoundException{
        ManagerDAO managerDAO = new ManagerDAO();
        Manager manager = managerDAO.getUser("giul");
        ManagerProfileController managerProfileController = new ManagerProfileController(manager);
        StructureDAO structureDAO = new StructureDAO();

        int [] s = {2, 2, 2};
        int [] r = {2, 3, 4};

        try {
           managerProfileController.addStructure("Hotel_Name", "Firenze", "giul", "Hotel", r, s, 25);
           Structure hotel = structureDAO.getStructure("giul","Hotel_Name");
           assertNotNull(hotel);
           structureDAO.removeStructure("Hotel_Name");
           Structure h = structureDAO.getStructure("giul","Hotel_Name");
           assertNull(h);

        } catch (SQLException | ClassNotFoundException E) {
            System.err.println(E.getMessage());
        }
    }
}
