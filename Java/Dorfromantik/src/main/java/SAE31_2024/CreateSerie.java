package SAE31_2024;

import SAE31_2024.controller.HexPanelTilePreview;

import java.sql.*;

/**
 * The CreateSerie class is responsible for creating a series of entries in the LT_Games database table.
 */
public class CreateSerie {
    /**
     * The main method which serves as the entry point for the Java application.
     * It retrieves the last ID from the LT_Games table and inserts 10 new entries with incremented IDs.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int id = 0;

        // Retrieve the last ID from the LT_Games table
        try (Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");
             PreparedStatement ps = DB.prepareStatement("select * from LT_Games");
             ResultSet rs = ps.executeQuery()) {

            if (rs.last()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Insert 10 new entries into the LT_Games table
        for (int i = 0; i < 10; i++) {
            HexPanelTilePreview prev = new HexPanelTilePreview(null);

            try (Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");
                 PreparedStatement ps = DB.prepareStatement("insert into LT_Games values(?,?)")) {

                ps.setInt(1, id + i);
                ps.setString(2, prev.toString());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}