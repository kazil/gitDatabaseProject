package healtycaresystem;

import java.sql.*;

/**
 * Cleaner class for database system.
 * + Statement openStatement()
 * + ResultSet getSelection(String sqlString)
 * + doUpdate(String sqlString)
 * + Connection openConnection()
 * + boolean closeStatement(Statement stm)
 * + boolean closeResultSet(ResultSet res)
 * + boolean closeConnection(Connection con)
 * + boolean setAutoCommit(Connection con, boolean on)
 *
 * @author Team One(Tom)
 */
public class DatabaseManagement {

    private Connection con;
    private ResultSet res;
    private Statement stm;
    private final String dbDriver;
    private final String dbName;

    /**
     * Constructor
     *
     * @param dbDriver - Driver for the database application.
     * @param dbName   - Location String for the database.
     */
    public DatabaseManagement(String dbDriver, String dbName) {
        this.dbDriver = dbDriver;
        this.dbName = dbName;
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load driver.\n" + e);
        }
    }

    /**
     * Method to open Connection
     *
     * @return confirmation. True if sucsessfull, false if not.
     */
    public boolean openConnection() {
        boolean bool = true;
        try {
            con = DriverManager.getConnection(dbName);
        } catch (SQLException e) {
            System.out.println("Could not open connection to database.\n" + e);
            bool = false;
        } finally {
            return bool;
        }
    }

    /**
     * Method to open connection
     *
     * @return opened Connection object
     */
    public Statement openStatement() {
        try {
            stm = con.createStatement();
        } catch (SQLException e) {
            System.out.println("Kunne ikke åpne Statement.\n" + e);
        } finally {
            return stm;
        }
    }

    /**
     * Method to open ResultSet
     *
     * @param sqlString - Sql sentence that's going to be run
     * @return opened ResultSet object
     */
    public ResultSet getSelection(String sqlString) {
        try {
            res = openStatement().executeQuery(sqlString);
        } catch (Exception e) {
            System.out.println("Kunne ikke åpne ResultSet.\n" + e);
        } finally {
            return res;
        }
    }

    /**
     * Method for updating the database.
     *
     * @param sqlString - sql operation as string.
     * @return - boolean confirmation.
     */
    public boolean doUpdate(String sqlString) {
        boolean bool = true;
        int antChanged = 0;
        try {
            stm = con.createStatement();
            antChanged = stm.executeUpdate(sqlString);
        } catch (SQLException e) {
            System.out.println("Could not update database.\n" + e);
            bool = false;
        } finally {
            if (antChanged > 0) {
                bool = true;
            } else {
                bool = false;
            }
            return bool;
        }
    }

    /**
     * runs close() on a Statement object
     *
     * @param stm
     * @return true if close() is sucsessfull, and false if not.
     */
    private boolean closeStatement(Statement stm) {
        boolean bool = true;
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            System.out.println("Kunne ikke stenge statement.\n" + e);
            bool = false;
        } finally {
            return bool;
        }
    }

    /**
     * runs close() on ResultSet object
     *
     * @param res
     * @return true if close() is sucsessfull, and false if not.
     */
    private boolean closeResultSet(ResultSet res) {
        boolean bool = true;
        try {
            if (res != null) {
                res.close();
            }
        } catch (SQLException e) {
            System.out.println("Kunne ikke stenge ResultSet.\n" + e);
            bool = false;
        } finally {
            return bool;
        }
    }

    /**
     * Method for closing both ResultSet and Statement objects
     *
     * @return - true if ok, false if it fails.
     */
    public boolean closeResultSetAndStatement() {
        if (closeResultSet(res) && closeStatement(stm)) {
            return true;
        }
        // Kommer bare hit hvis den ikke klarer å stenge begge på 5 forsøk
        return false;
    }

    /**
     * Closes both ResultSet and Statement, pluss Connection
     *
     * @return true if close() is sucsessfull, and false if not.
     */
    public boolean closeConnection() {
        boolean bool = true;
        try {
            boolean closedAll = closeResultSetAndStatement();
            if (closedAll) {
                if (con != null) {
                    con.close();
                } else {
                    System.out.println("Can't close connection.");
                    bool = false;
                }
            } else {
                System.out.println("can't close all objects.");
                bool = false;
            }
        } catch (SQLException e) {
            System.out.println("Kunne ikke stenge Connection.\n" + e);
            bool = false;
        } finally {
            return bool;
        }
    }

    /**
     * Set auto commit on the passed in Connection object
     *
     * @param con - Connection object
     * @param on  - True to set auto commit on, false to turn it off.
     * @return true if setAutoCommit is sucsessfull, and false if not.
     */
    public boolean setAutoCommit(Connection con, boolean on) {
        boolean bool = true;
        try {
            con.setAutoCommit(on);
        } catch (SQLException e) {
            System.out.println("Kunne ikke sette auto commit til: " + on + "\n" + e);
            bool = false;
        } finally {
            return bool;
        }
    }
}