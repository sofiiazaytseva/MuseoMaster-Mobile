package eu.pl.snk.senseibunny.museomaster.models;

import android.content.Context;
import android.content.res.Resources;


import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class DataBaseDriver{
    private Connection conn;
    public DataBaseDriver(Context context) throws SQLException {


        String password="";
        try{

            try {
                Properties properties = new Properties();

                Resources resources = context.getResources();
                InputStream configFile = context.getResources().getAssets().open("config.properties");
                properties.load(configFile);

                password = properties.getProperty("db.password");

                // ...
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


        String url = "jdbc:mysql://10.0.2.2:3306/MuseoMaster?useSSL=false";
        String username = "remoteUser";


        conn = DriverManager.getConnection(url, username, password);
        System.out.println("Database connection established");


        }
        catch(SQLException e){
            System.out.println ("Database NOT connection established");
            e.printStackTrace();
        }


    }



    //Sekcja Clienta
    ////////////////////////////////////////////////////////////////////////
    public ResultSet getClientData(String username, String password, String rola){
        Statement statement;
        ResultSet resultSet =null;

        try{
            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM pracownik WHERE nazwaUżytkownika='"+username+"' AND haslo ='"+password+"' AND rola ='"+rola+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getHashedPassword(String username, String rola){
        Statement statement;
        ResultSet resultSet =null;

        try{
            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT haslo FROM pracownik WHERE nazwaUżytkownika='"+username+"' AND rola ='"+rola+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }


    public void createReport(String opis, Integer id, String username){

        Statement statement ;
        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("INSERT INTO raport (opis, idPracownika,username) VALUES ('"+opis+"', '"+id+"', '"+username+"');");
        } catch (SQLException e) {

        }
    }
    //Sekcja Admina

    private boolean createSuccessFlag;
    public boolean getCreateSuccessFlag(){
        return createSuccessFlag;
    }
    public void createClient(String imiePracownika, String nazwiskoPracownika, String emailPracownika, Integer wiekPracownika,
                             Integer czyUprawniony, String rola, Integer nrTelefonu, String nazwaUzytkownika, String hasloUzytkownika){
        String none = "-";
        Statement statement ;
        try{
            statement=this.conn.createStatement();
            createSuccessFlag=true;
            statement.executeUpdate("INSERT INTO pracownik (imie, nazwisko, nazwaUżytkownika, `e-mail`, nrTelefonu, wiek, haslo, status, czyUprawniony, rola) " +
                    "VALUES ('"+imiePracownika+"','"+nazwiskoPracownika+"','"+nazwaUzytkownika+"','"+emailPracownika+"','"+nrTelefonu+"','"+wiekPracownika+"', '"+hasloUzytkownika+"','"+none+"', '"+czyUprawniony+"','"+rola+"');");
        } catch (SQLException e) {
            createSuccessFlag=false;
        }
    }



    public ResultSet getAllClientsData(){
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement=this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM pracownik");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }


    public void deleteClient(Integer id){
        Statement statement;

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("DELETE FROM pracownik WHERE idPracownika ='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createRoom(Integer wielkosc ,String nazwaSali, String typ){
        String none = "wolna";
        Statement statement ;
        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("INSERT INTO sala (wielkość,nazwa,status,typ) VALUES ('"+wielkosc+"','"+nazwaSali+"','"+none+"','"+typ+"');");
        } catch (SQLException e) {

        }
    }

    public ResultSet getAllReporstData(){
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement=this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM raport");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public void deleteReport(Integer id){
        Statement statement;

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("DELETE FROM raport WHERE idRaportu ='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////

    //Pracownik +
    ////////////////////////////////////////////////////////////////////////
    public ResultSet getWorkerData(String Input, String rola){
        Statement statement;
        ResultSet resultSet = null;
        String imie = null;
        String nazwisko = null;

        try{
            if (Input.contains(" ")) {
                String[] words = Input.split(" ");
                imie=words[0];
                nazwisko=words[1];
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


        try {
            if (Input.trim().isEmpty()) {
                if (rola != null && !rola.trim().isEmpty()) {
                    statement = this.conn.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM pracownik WHERE rola='" + rola + "';");
                }
            } else if (Input != null && Input.contains(" ")) {
                String[] words = Input.split(" ");
                imie = words[0];
                nazwisko = words[1];
                statement = this.conn.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM pracownik WHERE imie='" + imie + "' AND nazwisko='" + nazwisko + "';");
            } else {
                statement = this.conn.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM pracownik WHERE nazwaUżytkownika ='" + Input + "'");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public void createTask(Integer idPracownika, String opis, String temat, java.sql.Date dataRozpoczecia, java.sql.Date dataZakonczenia, String nazwaNadajacego, String nazwaUzytkownika){
        String status = "wTrackcie";
        Statement statement ;
        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("INSERT INTO zadanie2 (temat,opis,dataRozpoczęcia,dataZakończenia,status,idPracownika,nazwaNadajacego,nazwaUzytkownika) VALUES ('"+temat+"','"+opis+"','"+dataRozpoczecia+"','"+dataZakonczenia+"','"+status+"','"+idPracownika+"', '"+nazwaNadajacego+"', '"+nazwaUzytkownika+"');");
            //Zadanie x = new Zadanie(temat,opis,dataRozpoczecia,dataZakonczenia,status,idPracownika,nazwaNadajacego,nazwaUzytkownika);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAssignedTaskToLv(String nazwaNadajacego){
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement=this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM zadanie2 WHERE nazwaNadajacego='"+nazwaNadajacego+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public ResultSet getAssignedTaskState(Integer id){
        Statement statement;
        ResultSet resultSet = null;


        try{
            statement=this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT status FROM zadanie2 WHERE idZadania='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public void UpdateEx(String status,String doceloweMiejsce, Integer idEksponatu){
        Statement statement;


        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("Update eksponat SET status='"+status+"', docMiejscePrzech='" + doceloweMiejsce + "' Where idEksponatu='"+idEksponatu+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEksponatZadanie(Integer idPracownika, Integer idZabytku){
        String status = "wTrackcie";
        Statement statement ;
        try{
            statement=this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(idZadania) FROM zadanie2;");
            int highestId = 0;
            if (resultSet.next()) {
                highestId = resultSet.getInt(1);
            }
            statement.executeUpdate("INSERT INTO eksponat_zadanie (idZadania,idPracownika,idZabytku) VALUES ('"+highestId+"','"+idPracownika+"','"+idZabytku+"');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /////////////////////////////////////////////////////////////////////////


    //Sekcja pracownika
    ////////////////////////////////////////////////////////////////////////
    public ResultSet getAssignedTask(Integer id){
        Statement statement;
        String status = "wTrackcie";
        ResultSet resultSet = null;

        try{
            statement=this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM zadanie2 WHERE status='"+status+"' AND idPracownika='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public ResultSet getFinishedTask(Integer id){
        Statement statement;
        String status1 = "Zakonczone";
        String status2 = "Problem";
        String status3 = "Fail";
        ResultSet resultSet = null;

        try{
            statement=this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM zadanie2 WHERE (status='"+status1+"' OR status='"+status2+"' OR status='"+status3+"') AND idPracownika='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public void setAssignedTask(String status1, Integer id){
        Statement statement;
        String status2 = "wTrackcie";


        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("Update zadanie2 SET status='"+status1+"' Where status='"+status2+"' AND idZadania='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Integer getSizeAssignedTask(Integer id){
        Statement statement;
        ResultSet resultSet=null;
        int count = 0;
        String status2 = "wTrackcie";

        try{
            statement=this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM zadanie2 WHERE idPracownika='"+id+"' and status='"+status2+"';");
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    /////////////////////////////////////////////////////////////////////////









    // Sekcja Kuratora
    private boolean createExhibitSuccessFlag;
    private boolean editExhibitSuccessFlag;
    public boolean geteditExhibitSuccessFlag(){
        return editExhibitSuccessFlag;
    }
    public boolean getCreateExhibitSuccessFlag(){
        return createExhibitSuccessFlag;
    }
    public void createExhibit(String nazwaZabytku, Integer okresPowstania, String tematyka, String tworca, String aktMiejscePrzechowywania, String docMiejcePrzech, String opis){
        Statement statement;
        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO eksponat (nazwaEksponatu, okresPowstania, tematyka, twórca, aktualMiejscePrzech, docMiejscePrzech , opis) " +
                    "VALUES ('"+nazwaZabytku+"','"+okresPowstania+"','"+tematyka+"','"+tworca+"','"+aktMiejscePrzechowywania+"', '"+docMiejcePrzech+"', '"+opis+"');");
            createExhibitSuccessFlag = true;
        } catch (SQLException e) {
            createExhibitSuccessFlag = false;
        }
    }
    public void deleteExhibit(Integer id){
        Statement statement;

        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM eksponat WHERE idEksponatu ='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getAllExhibitsData(){

        Statement statement;
        ResultSet resultSet = null;

        try{

            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM eksponat");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    //createMusicFile
    public void createMusicFile(File mp3File){
        PreparedStatement pstmt = null;
        Statement statement;
        ResultSet resultSet = null;
        try{

            String sql = "INSERT INTO plikmuzyczny (idZabytku,Muzyka,nazwaPliku) VALUES (?, ?, ?)";
            FileInputStream fis = new FileInputStream(mp3File);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(3, mp3File.getName());
            pstmt.setBinaryStream(2, fis, (int) mp3File.length());
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(idEksponatu) FROM eksponat;");
            int highestId = 0;
            if (resultSet.next()) {
                highestId = resultSet.getInt(1);
            }
            pstmt.setInt(1, highestId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            createExhibitSuccessFlag = false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Create exhibition
    private boolean createExhibitionSuccessFlag;
    public boolean getCreateExhibitionSuccessFlag(){
        return createExhibitionSuccessFlag;
    }
    public void createExhibition(String nazwaWystawy, String sala, String miejsceWykonania,
                                 String tematyka, String tworca, Date dataRozpoczecia, Date dataZakonczenia){
        Statement statement;
        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO wystawa (nazwaWystawy, sala, miejsceWykonania, tematyka, tworca, dataRozpoczecia , dataZakonczenia) " +
                    "VALUES ('"+nazwaWystawy+"','"+sala+"','"+miejsceWykonania+"','"+tematyka+"','"+tworca+"', '"+dataRozpoczecia+"', '"+dataZakonczenia+"');");
            createExhibitionSuccessFlag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            createExhibitionSuccessFlag = false;
        }
    }
    public void deleteExhibition(Integer id){
        Statement statement;

        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("DELETE FROM wystawa WHERE idWystawy ='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getAllExhibitionsData(){

        Statement statement;
        ResultSet resultSet = null;

        try{

            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM wystawa");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByName(String nazwa){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE nazwaEksponatu LIKE '"+nazwa+"%'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByTopic(String topic){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE tematyka LIKE '"+topic+"%'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByAuthor(String autor){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE twórca LIKE '"+autor+"%'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitBySecYear(Integer year){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE okresPowstania <= '"+year+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByFirstYear(Integer year){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE okresPowstania >= '"+year+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByYears(Integer year1, Integer year2){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE okresPowstania BETWEEN '"+year1+"' AND '"+year2+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByPlace(String miejsce){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE aktualMiejscePrzech = '"+miejsce+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getAllRoomsNames(){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sala");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
    public ResultSet getRoomsNames(){

        String type = "P.wystawowe";
        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sala WHERE typ='"+type+"';");
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
    public void editExhibit(Integer idZabytku, String nazwaZabytku, Integer okresPowstania, String tematyka, String tworca, String aktMiejscePrzechowywania, String docMiejcePrzech, String opis){
        Statement statement;
        try{
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE eksponat SET nazwaEksponatu = '"+nazwaZabytku+"', okresPowstania = '"+okresPowstania+"', tematyka = '"+tematyka+"', twórca = '"+tworca+"', aktualMiejscePrzech = '"+aktMiejscePrzechowywania+"', docMiejscePrzech = '"+docMiejcePrzech+"', opis = '"+opis+"' WHERE idEksponatu = '"+idZabytku+"';");
            editExhibitSuccessFlag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            editExhibitSuccessFlag = false;
        }
    }





    //Normal Client
    ////////////////////////////////////////////////////////////////
    public ResultSet getNormalClientData(String username, String password){
        Statement statement;
        ResultSet resultSet =null;

        try{
            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT * FROM zwyklyUzytkownik WHERE nazwaUzytkownika='"+username+"' AND haslo ='"+password+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public boolean checkEmailExists(String email) {
        Statement statement;
        ResultSet resultSet = null;
        boolean emailExists = false;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT email FROM zwyklyUzytkownik WHERE email = '" + email + "'");
            emailExists = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return emailExists;
    }

    public boolean checkUsernameExists(String username) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT nazwaUzytkownika FROM zwyklyUzytkownik WHERE nazwaUzytkownika = '" + username + "'");
            return resultSet.next(); // Returns true if the result set is not empty
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the result set and statement
            if (resultSet != null) {
                try {
                    resultSet.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int createNormalClient(String email, String haslo, String nazwaUzytkownika){
        Statement statement ;
        ResultSet resultSet;
        try{
            statement=this.conn.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM zwyklyuzytkownik WHERE nazwaUzytkownika='" + nazwaUzytkownika + "';");

            if (resultSet.next()) {
                // User with the same "nazwaUzytkownika" already exists
                return 1;
            }
            else{
                statement.executeUpdate("INSERT INTO zwyklyuzytkownik (email,haslo,nazwaUzytkownika) VALUES ('"+email+"','"+haslo+"','"+nazwaUzytkownika+"');");
                return 0;
            }

        } catch (SQLException e) {
            return 1;
        }
    }

    public ResultSet getHashedPasswordNormalUser(String username){
        Statement statement;
        ResultSet resultSet =null;

        try{
            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT haslo FROM zwyklyuzytkownik WHERE nazwaUzytkownika='"+username+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public boolean getMusicFile(Integer idZabytku) throws SQLException, IOException {
        PreparedStatement pstmt = null;
        String sql2 = "SELECT * FROM plikMuzyczny WHERE idZabytku=?";
        pstmt = conn.prepareStatement(sql2);
        pstmt.setInt(1, idZabytku);
        ResultSet rs = null;
        rs = pstmt.executeQuery();
        if (rs.next()) {
            // Retrieve the MP3 file data from the result set
            System.out.println("Zaczynam");
            System.out.println(rs.getString(4));
            InputStream inputStream = rs.getBinaryStream("Muzyka");

            // Save the MP3 file to a local file
            String outputPath = "pobrane/pobrane.mp3"; // Update the output path to a relative path within the JAR file

            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();

            try (OutputStream outputStream = new FileOutputStream(outputFile, false)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("Kończe");
            // Close the input stream
            inputStream.close();
            return true;
        } else {
            System.out.println("nie znaleziono");
            return false;
        }
    }
    ////////////////////////////////////////////////////////////////





    //Pracownik Techniczny
    ////////////////////////////////////////////////////////////////
    public ResultSet getTechnicianEx(Integer idZadania, Integer idPracownika){
        Statement statement;
        ResultSet resultSet =null;

        try{
            statement = this.conn.createStatement();
            resultSet=statement.executeQuery("SELECT idZabytku, idEksponatZadanie FROM eksponat_zadanie WHERE idZadania='"+idZadania+"' AND idPracownika='"+idPracownika+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public ResultSet getExById(Integer idEx){
        Statement statement;
        ResultSet resultSet =null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT idEksponatu, nazwaEksponatu, docMiejscePrzech, aktualMiejscePrzech FROM eksponat WHERE idEksponatu='" + idEx + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public void UpdateExPlaceAndStatu(String status,String aktualneMiejsce, Integer idEksponatu){
        Statement statement;
        String doc="-";

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("UPDATE eksponat SET status='" + status + "', docMiejscePrzech='" + doc + "', aktualMiejscePrzech='" + aktualneMiejsce + "' WHERE idEksponatu='" + idEksponatu + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEx_Task(Integer id){
        Statement statement;

        try{
            statement=this.conn.createStatement();
            statement.executeUpdate("DELETE FROM eksponat_zadanie WHERE idEksponatZadanie ='"+id+"';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //////////////////////////////////////  ///////////////////////

    //Utility methods
    /////////////////////////////////////////////////////////////
    private String doc = "null";
    public ResultSet getExhibitByNameForTask(String nazwa){

        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE nazwaEksponatu LIKE '"+nazwa+"%' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByTopicForTask(String topic){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE tematyka LIKE '"+topic+"%' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByAuthorForTask(String autor){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE twórca LIKE '"+autor+"%' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitBySecYearForTask(Integer year){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE okresPowstania <= '"+year+"' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByFirstYearForTask(Integer year){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE okresPowstania >= '"+year+"' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByYearsForTask(Integer year1, Integer year2){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE okresPowstania BETWEEN '"+year1+"' AND '"+year2+"' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    public ResultSet getExhibitByPlaceForTask(String miejsce){

        Statement statement;
        ResultSet resultSet = null;

        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM eksponat " +
                    "WHERE aktualMiejscePrzech = '"+miejsce+"' AND docMiejscePrzech = '"+doc+"'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }
    ////////////////////////////////////////////////////////////////////////
}

