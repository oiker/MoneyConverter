package iqTask.moneyConverter.database;

import iqTask.moneyConverter.crbclient.CRBClient;
import iqTask.moneyConverter.objects.ConvertHistory;
import iqTask.moneyConverter.objects.ValCurs;
import iqTask.moneyConverter.objects.Valute;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DataBase {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static Connection dbConnection = null;
    private static Statement statement = null;

    @Resource
    private CRBClient crbClient;

    @PostConstruct
    public void postConstruct() {
        System.out.println("init bean database");
        // проверка на наличие таблиц
        //если таблицы не найдены - создать
        try {
            init();
            initHistory();
            createSchema();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createSchema() throws SQLException {
        String createSchema = "CREATE SCHEMA IF NOT EXISTS IQTASK";
        executeStatement(createSchema);
    }

    public void init() throws SQLException {

        String createTableSQL = "CREATE TABLE IF NOT EXISTS VALCURS  ("
                + "DATE VARCHAR(100) NOT NULL, "
                + "ID VARCHAR(100) NOT NULL, "
                + "NUMCODE VARCHAR(100) NOT NULL, "
                + "CHARCODE VARCHAR(100) NOT NULL, "
                + "NOMINAL VARCHAR(100) NOT NULL, "
                + "NAME VARCHAR(100) NOT NULL, "
                + "VALUE VARCHAR(100) NOT NULL "
                + ")";

        executeStatement(createTableSQL);
    }

    public void initHistory() throws SQLException {
        String createHistoryTable = "CREATE TABLE IF NOT EXISTS HISTORY ("
                + "DATE VARCHAR (40) NOT NULL,"
                + "FIRSTNAME VARCHAR(40) NOT NULL,"
                + "SECONDNAME VARCHAR(40) NOT NULL,"
                + "AMOUNT VARCHAR (40),"
                + "RESULT VARCHAR (40)"
                + ")";

        executeStatement(createHistoryTable);
    }


    public List getHistory() throws SQLException {
        List<ConvertHistory> list = new ArrayList<>();

        String SQLValCursFromDB = "SELECT DATE, FIRSTNAME, SECONDNAME, AMOUNT, RESULT FROM HISTORY;";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(SQLValCursFromDB);
            while (rs.next()) {
                ConvertHistory convertHistory = new ConvertHistory(rs.getString("DATE"), rs.getString("FIRSTNAME"), rs.getString("SECONDNAME"), rs.getString("AMOUNT"), rs.getString("RESULT"));
                list.add(convertHistory);
            }
            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if ( statement != null ) {
                statement.close();
            }
            if ( dbConnection != null ) {
                dbConnection.close();
            }
        }
        return null;
    }


    public List<Valute> getCurrentValCurs() throws SQLException {
        List<Valute> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(new Date());

        String getValCursFromDB = "SELECT ID, NUMCODE, CHARCODE, NOMINAL, NAME, VALUE FROM VALCURS WHERE DATE = '" + date + "';";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getValCursFromDB);
            if ( rs == null ) {
                addCurrentValCursToDb();
                ResultSet rs2 = statement.executeQuery(getValCursFromDB);
                while (rs2.next()) {
                    Valute valute = new Valute(rs.getString("ID"), rs.getString("NUMCODE"), rs.getString("CHARCODE"), rs.getString("NOMINAL"), rs.getString("NAME"), rs.getString("VALUE"));
                    list.add(valute);
                }
            return list;
            }
            else {
                while (rs.next()) {
                    Valute valute = new Valute(rs.getString("ID"), rs.getString("NUMCODE"), rs.getString("CHARCODE"), rs.getString("NOMINAL"), rs.getString("NAME"), rs.getString("VALUE"));
                    list.add(valute);
                }
                return list;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            if ( statement != null ) {
                statement.close();
            }
            if ( dbConnection != null ) {
                dbConnection.close();
            }
        }
    }


    public void addCurrentValCursToDb() throws SQLException {
        ValCurs valCurs = crbClient.getCurrentValCurs();

        try {
            List<Valute> valCursList = valCurs.getValute();

            for (Valute valute : valCursList) {
                String addCurrentValcurs = "INSERT INTO valcurs "
                        + "(DATE, ID, NUMCODE, CHARCODE, NOMINAL, NAME, VALUE)" + "VALUES"
                        + "('" + valCurs.getDate() + "','" + valute.getID() + "','" + valute.getNumCode() + "','" + valute.getCharCode() + "','" + valute.getNominal() + "','" + valute.getName() + "','" + valute.getValue() + "')";
                dbConnection = getDBConnection();
                statement = dbConnection.createStatement();
                statement.executeUpdate(addCurrentValcurs);
            }
        } finally {
            if ( statement != null ) {
                statement.close();
            }
            if ( dbConnection != null ) {
                dbConnection.close();
            }
        }
    }

    public void addToHistory(String date, String firstName, String secondName, String amount, String result) throws SQLException {
        String createHistoryTable = "INSERT INTO HISTORY("
                + "DATE, FIRSTNAME , SECONDNAME, AMOUNT, RESULT)" + "VALUES"
                + "('" + date + "','" + firstName + "','" + secondName + "','" + amount + "','" + result + "');";

        executeStatement(createHistoryTable);
    }

    private void executeStatement(String createTableSQL) throws SQLException {
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if ( statement != null ) {
                statement.close();
            }
            if ( dbConnection != null ) {
                dbConnection.close();
            }
        }
    }


    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
