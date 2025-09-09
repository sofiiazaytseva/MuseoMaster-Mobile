package eu.pl.snk.senseibunny.museomaster.models;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;
import java.util.Objects;


public class Model {
    private static Model model;
    private DataBaseDriver dataBaseDriver;

    private Client client;

    private boolean ClientLoginSuccessFlag;

    //Admin
    private final ArrayList<Client> clients;

    private final ArrayList<Report> reports;
    ////////////////////////////////


    //Curator
    private final ArrayList<Exhibit> exhibits;
    private final ArrayList<Exhibition> exhibitions;
    private final ArrayList<String> allRooms;
    private final ArrayList<String> rooms;
    private final ArrayList<Exhibit> exhibitsSearched;
    private boolean setSearchedExhibitsSuccessFlag;
    ////////////////////////////////


    //Normal worker
    private final ArrayList<Task> tasks;
    private final ArrayList<Task> tasks_finished;

    private final ArrayList<Exhibit> exAssigned;

    ////////////////////////////////////////////////


    //Pracownik+
    private final ArrayList<Task> tasksAssignedTo;

    private final ArrayList<Client> workersAssigned;

    private int searchTaskFlag =0;

    ////////////////////////////////////////////////////////////////

    //Technical Worker
    private final ArrayList<Exhibit> exhibitsAssigned;

    private final ArrayList<Exhibit> exhibitsChecked;

    private final ArrayList<Integer> exIds;

    ///////////////////////////////////////////////////////////////

    private Model(Context context) throws SQLException {

        this.client = new Client(0, "", "", "", 0, 0, "x", 0, "x");

        //Admin
        this.dataBaseDriver = new DataBaseDriver(context);
        //Admin
        this.clients = new ArrayList<Client>();
        this.reports=new ArrayList<Report>();
        //Curator
        this.exhibits = new ArrayList<>();
        this.exhibitions = new ArrayList<>();
        this.allRooms = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.exhibitsSearched = new ArrayList<>();
        setSearchedExhibitsSuccessFlag = false;
        //Normal worker
        this.tasks = new ArrayList<Task>();
        this.tasks_finished=new ArrayList<Task>();

        //Worker+
        this.tasksAssignedTo = new ArrayList<Task>();
        this.workersAssigned = new ArrayList<Client>();
        this.exAssigned = new ArrayList<Exhibit>();

        //Technical Worker
        this.exhibitsAssigned = new ArrayList<>();
        this.exhibitsChecked = new ArrayList<>();
        this.exIds = new ArrayList<>();



    }

    public DataBaseDriver getDataBaseDriver() {
        return dataBaseDriver;
    }

    public static synchronized Model getInstance(Context context) throws SQLException {
        if (model == null) {
            model = new Model(context);
        }
        return model;
    }

    public static synchronized Model getInstanceWC() throws SQLException {

        return model;
    }


    //Admin Section ********************************
    public void setClients() {
        ResultSet resultSet = dataBaseDriver.getAllClientsData();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idPracownika");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String nazwaUzytkownika = resultSet.getString("nazwaUżytkownika");
                String email = resultSet.getString("e-mail");
                Integer nrTelefonu = resultSet.getInt("nrTelefonu");
                Integer wiek = resultSet.getInt("wiek");
                Integer uprawniony = resultSet.getInt("czyUprawniony");
                String rola = resultSet.getString("rola");
                clients.add(new Client(id, imie, nazwisko, email, wiek, uprawniony, rola, nrTelefonu, nazwaUzytkownika));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports() {
        ResultSet resultSet = dataBaseDriver.getAllReporstData();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idPracownika");
                String nazwaUzytkownika = resultSet.getString("username");
                String opis = resultSet.getString("opis");
                Integer idR = resultSet.getInt("idRaportu");
                reports.add(new Report(id, idR, nazwaUzytkownika, opis));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////



    //Curator Section
    public void setExhibits() {
        ResultSet resultSet = dataBaseDriver.getAllExhibitsData();
        try {
            while (resultSet.next()) {
                Integer idZabytku = resultSet.getInt("idEksponatu");
                String nazwaEksponatu = resultSet.getString("nazwaEksponatu");
                String okresPowstania = resultSet.getString("okresPowstania");
                String tematyka = resultSet.getString("tematyka");
                String tworca = resultSet.getString("twórca");
                String aktualMiejscePrzech = resultSet.getString("aktualMiejscePrzech");
                String opis = resultSet.getString("opis");
                Integer WystawaidWystawy = resultSet.getInt("WystawaidWystawy");
                Integer ZadanieidZadania = resultSet.getInt("ZadanieidZadania");
                Integer SalaidSali = resultSet.getInt("SalaidSali");
                Integer ZadaniePracownikidPracownika = resultSet.getInt("ZadaniePracownikidPracownika");
                exhibits.add(new Exhibit(idZabytku, nazwaEksponatu, okresPowstania, tematyka, tworca, aktualMiejscePrzech, opis,
                        WystawaidWystawy, ZadanieidZadania, SalaidSali, ZadaniePracownikidPracownika,""));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Normal worker section *************************************************************************************************************
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> getFishedTasks() {
        return tasks_finished;
    }

    public void setTasks(String type) {
        ResultSet resultSet;
        if (Objects.equals(type, "assigned")) {
            resultSet = dataBaseDriver.getAssignedTask(client.getIdPracownika());
        } else if (Objects.equals(type, "assignedTo")) {
            resultSet = dataBaseDriver.getAssignedTaskToLv(client.getNazwaUzytkownika());
        } else {
            resultSet = dataBaseDriver.getFinishedTask(client.getIdPracownika());
        }

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idZadania");
                String temat = resultSet.getString("temat");
                String opis = resultSet.getString("opis");
                String dataRozpoczecia = resultSet.getDate("dataRozpoczęcia").toString();
                String dataZakonczenia = resultSet.getDate("dataZakończenia").toString();
                String status = resultSet.getString("status");
                Integer idPracownika = resultSet.getInt("idPracownika");
                String nazwaUzytkownikaNadajacego = resultSet.getString("nazwaNadajacego");
                String nazwaUzytkownika = resultSet.getString("nazwaUzytkownika");
                if (Objects.equals(type, "assigned")) {
                    tasks.add(0, new Task(id, temat, opis, dataRozpoczecia, dataZakonczenia, status, idPracownika, nazwaUzytkownikaNadajacego, nazwaUzytkownika));
                } else if (Objects.equals(type, "assignedTo")) {
                    tasksAssignedTo.add(0, new Task(id, temat, opis, dataRozpoczecia, dataZakonczenia, status, idPracownika, nazwaUzytkownikaNadajacego, nazwaUzytkownika));
                } else {
                    tasks_finished.add(0, new Task(id, temat, opis, dataRozpoczecia, dataZakonczenia, status, idPracownika, nazwaUzytkownikaNadajacego, nazwaUzytkownika));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearExhibitList(){
        exhibits.clear();
    }
    public void clearExhibitions(){
        exhibitions.clear();
    }
    public void clearSearchedExhibits(){
        exhibitsSearched.clear();
    }
    public void clearAllRooms(){
        allRooms.clear();
    }
    public void clearRooms(){
        rooms.clear();
    }
    public ArrayList<Exhibit> getExhibits() {
        return exhibits;
    }
    public ArrayList<Exhibition> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions() {
        ResultSet resultSet = dataBaseDriver.getAllExhibitionsData();

        try {
            while (resultSet.next()) {
                Integer idWystawy = resultSet.getInt("idWystawy");
                String nazwaWystawy = resultSet.getString("nazwaWystawy");
                String sala = resultSet.getString("sala");
                String miejsceWykonania = resultSet.getString("miejsceWykonania");
                String tematyka = resultSet.getString("tematyka");
                String tworca = resultSet.getString("tworca");
                Date dataRozpoczecia = resultSet.getDate("dataRozpoczecia");
                Date dataZakonczenia = resultSet.getDate("dataZakonczenia");
                exhibitions.add(new Exhibition(idWystawy, nazwaWystawy, sala, miejsceWykonania, tematyka, tworca, dataRozpoczecia, dataZakonczenia));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getAllRooms() {
        return allRooms;
    }

    public void setAllRooms() {
        ResultSet resultSet = dataBaseDriver.getAllRoomsNames();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idSali");
                Integer wielksc = resultSet.getInt("wielkość");
                String nazwa = resultSet.getString("nazwa");
                String typ = resultSet.getString("typ");
                allRooms.add(nazwa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<String> getRooms() { return rooms; }
    public void setRoom() {
        ResultSet resultSet = dataBaseDriver.getRoomsNames();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idSali");
                Integer wielksc = resultSet.getInt("wielkość");
                String nazwa = resultSet.getString("nazwa");
                String typ = resultSet.getString("typ");
                rooms.add(nazwa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Exhibit> getExhibitsSearched() {return exhibitsSearched;}

    public boolean isSetSearchedExhibitsSuccessFlag() {return setSearchedExhibitsSuccessFlag;}

    public void setExhibitsSearched(String nazwa, String autor, String topic, Integer rok1, Integer rok2, String
            miejsce) {
        ResultSet resultSet;
        if (!Objects.equals(nazwa, "")) {
            resultSet = dataBaseDriver.getExhibitByName(nazwa);
        } else {
            if (!Objects.equals(topic, "")) {
                resultSet = dataBaseDriver.getExhibitByTopic(topic);
            } else {
                if (!Objects.equals(autor, "")) {
                    resultSet = dataBaseDriver.getExhibitByAuthor(autor);
                } else {
                    if (rok1 == 10000 && rok2 != 10000) {
                        resultSet = dataBaseDriver.getExhibitBySecYear(rok2);
                    }
                    else if (rok2 == 10000 && rok1 != 10000) {
                        resultSet = dataBaseDriver.getExhibitByFirstYear(rok1);
                    }
                    else if (rok1 != 10000 && rok2 != 10000) {
                        resultSet = dataBaseDriver.getExhibitByYears(rok1, rok2);
                    }
                    else {
                        if (!Objects.equals(miejsce, "null")) {
                            resultSet = dataBaseDriver.getExhibitByPlace(miejsce);
                            System.out.println(miejsce);
                        }
                        else {
                            System.out.println("DUPOA");
                            resultSet = dataBaseDriver.getExhibitByYears(-1000000, 2024);
                        }
                    }
                }
            }
        }

        try {
            while (resultSet.next()) {
                Integer idZabytku = resultSet.getInt("idEksponatu");
                String nazwaEksponatu = resultSet.getString("nazwaEksponatu");
                String okresPowstania = resultSet.getString("okresPowstania");
                String tematyka = resultSet.getString("tematyka");
                String tworca = resultSet.getString("twórca");
                String aktualMiejscePrzech = resultSet.getString("aktualMiejscePrzech");
                String opis = resultSet.getString("opis");
                Integer WystawaidWystawy = resultSet.getInt("WystawaidWystawy");
                Integer ZadanieidZadania = resultSet.getInt("ZadanieidZadania");
                Integer SalaidSali = resultSet.getInt("SalaidSali");
                Integer ZadaniePracownikidPracownika = resultSet.getInt("ZadaniePracownikidPracownika");
                exhibitsSearched.add(new Exhibit(idZabytku, nazwaEksponatu, okresPowstania, tematyka, tworca, aktualMiejscePrzech, opis,
                        WystawaidWystawy, ZadanieidZadania, SalaidSali, ZadaniePracownikidPracownika,""));
            }
            setSearchedExhibitsSuccessFlag = true;
        } catch (SQLException e) {
            setSearchedExhibitsSuccessFlag = false;
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////

    public void clearTasks() {
        tasks.clear();
    }

    public void clearFinishedTasks() {
        tasks_finished.clear();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        tasks_finished.add(0, task);
    }

    public Client getClient() {
        return client;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////




    //Worker+ ****************************************************************

    public ArrayList<Task> getAssignedToTasks() {
        return tasksAssignedTo;
    }

    public void setWorkers(String Input, String rolaa) {
        ResultSet resultSet = dataBaseDriver.getWorkerData(Input, rolaa);

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idPracownika");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String nazwaUzytkownika = resultSet.getString("nazwaUżytkownika");
                String email = resultSet.getString("e-mail");
                Integer nrTelefonu = resultSet.getInt("nrTelefonu");
                Integer wiek = resultSet.getInt("wiek");
                Integer uprawniony = resultSet.getInt("czyUprawniony");
                String rola = resultSet.getString("rola");
                clients.add(new Client(id, imie, nazwisko, email, wiek, uprawniony, rola, nrTelefonu, nazwaUzytkownika));
                System.out.println(clients);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearWorkers() {
        clients.clear();
    }

    public void assignWorker(Client client) {
        workersAssigned.add(client);
        System.out.println(workersAssigned);
    }

    public void removeWorker(Client client) {
        workersAssigned.remove(client);
        System.out.println(workersAssigned);
    }

    public ArrayList<Client> getWorkersAssigned() {
        return workersAssigned;
    }

    public void clearWorkersAssigned() {
        workersAssigned.clear();
    }

    public void clearAssignedTasks() {
        tasksAssignedTo.clear();
    }

    public void addTaskAssignedTo(Task task) {
        tasksAssignedTo.add(0, task);
    }

    public void assignEx(Exhibit ex) {
        exAssigned.add(ex);
        System.out.println(exAssigned);
    }

    public void removeEx(Exhibit ex) {
        exAssigned.remove(ex);
        System.out.println(exAssigned);
    }

    public void clearEx(){
        exAssigned.clear();
    }

    public ArrayList<Exhibit> getExAssigned() {
        return exAssigned;
    }

    public int getSearchTaskFlag() {
        return searchTaskFlag;
    }

    public void setSearchTaskFlag(int searchTaskFlag) {
        this.searchTaskFlag = searchTaskFlag;
    }

    public void updateAssignedTasks(String type) {
        ResultSet resultSet;
        if (Objects.equals(type, "assigned")) {
            resultSet = dataBaseDriver.getAssignedTask(client.getIdPracownika());
        } else if (Objects.equals(type, "assignedTo")) {
            resultSet = dataBaseDriver.getAssignedTaskToLv(client.getNazwaUzytkownika());
        } else {
            resultSet = dataBaseDriver.getFinishedTask(client.getIdPracownika());
        }

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("idZadania");
                String temat = resultSet.getString("temat");
                String opis = resultSet.getString("opis");
                String dataRozpoczecia = resultSet.getDate("dataRozpoczęcia").toString();
                String dataZakonczenia = resultSet.getDate("dataZakończenia").toString();
                String status = resultSet.getString("status");
                Integer idPracownika = resultSet.getInt("idPracownika");
                String nazwaUzytkownikaNadajacego = resultSet.getString("nazwaNadajacego");
                String nazwaUzytkownika = resultSet.getString("nazwaUzytkownika");
                if (Objects.equals(type, "assigned")) {
                    tasks.add(0, new Task(id, temat, opis, dataRozpoczecia, dataZakonczenia, status, idPracownika, nazwaUzytkownikaNadajacego, nazwaUzytkownika));
                } else if (Objects.equals(type, "assignedTo")) {
                    tasksAssignedTo.add(0, new Task(id, temat, opis, dataRozpoczecia, dataZakonczenia, status, idPracownika, nazwaUzytkownikaNadajacego, nazwaUzytkownika));
                } else {
                    tasks_finished.add(0, new Task(id, temat, opis, dataRozpoczecia, dataZakonczenia, status, idPracownika, nazwaUzytkownikaNadajacego, nazwaUzytkownika));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    String desc;
    String subject;
    java.sql.Date starDate;
    java.sql.Date endDate;

    public void setTaskVars(String desc, String subject, java.sql.Date starDate, java.sql.Date endDate) {
        this.desc = desc;
        this.subject = subject;
        this.starDate = starDate;
        this.endDate = endDate;
    }

    public void changeDesc(String x){
        desc=x.concat(":  "+desc);
        System.out.println(desc);
    }
    public void createTask(Integer idPracownika, String nazwaUzytkownia, String nazwaNadajacego) {

        dataBaseDriver.createTask(idPracownika, desc, subject, starDate, endDate, nazwaNadajacego, nazwaUzytkownia);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Login Section ****************************************************************
//    public boolean getClientLoginFlag() {
//        return ClientLoginSuccessFlag;
//    }
//
//    public void setClientLoginFlag(boolean flag) {
//        this.ClientLoginSuccessFlag = flag;
//    }
//
//    public void setNormalClientLoginSuccess(boolean flag) {
//        this.NormalClientLoginSuccessFlag = flag;
//    }
//
//    public boolean getNornalClientLoginSuccess() {
//        return this.NormalClientLoginSuccessFlag;
//    }


    //Client Section
    ////////////////////////////////////////////////////////////////

        public boolean getClientLoginFlag(){
            return ClientLoginSuccessFlag;
        }

        public void setClientLoginFlag(boolean flag){
            this.ClientLoginSuccessFlag = flag;
        }



        public void evaluateClient(String username, String password, String rola){
            ResultSet resultSet = dataBaseDriver.getClientData(username,password,rola);
            try{
                if(resultSet.next()){
                    //
                    // System.out.println("rep1");
                    this.client.setIdPracownika(resultSet.getInt("idPracownika"));
                    this.client.setImiePracownika(resultSet.getString("imie"));
                    this.client.setNazwiskoPracownika(resultSet.getString("nazwisko"));
                    this.client.setNazwaUzytkownika(resultSet.getString("nazwaUżytkownika"));
                    this.client.setEmailPracownika(resultSet.getString("e-mail"));
                    this.client.setNrTelefonu(resultSet.getInt("nrTelefonu"));
                    this.client.setWiekPracownika(resultSet.getInt("wiek"));
                    this.client.setCzyUprawniony(resultSet.getInt("czyUprawniony"));
                    this.client.setRola(resultSet.getString("rola"));
                    this.setClientLoginFlag(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    public void evaluateNormalUser(String username, String password) {
        ResultSet resultSet = dataBaseDriver.getNormalClientData(username, password);
        try{
            if(resultSet.next()){
                this.setClientLoginFlag(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Technical Worker
    ArrayList<Integer> idZadList = new ArrayList<>();
    ArrayList<Integer> idZadExList = new ArrayList<>();
    public void setExIds() throws SQLException {
        for (Task task : tasks) {
            ResultSet resultSet = dataBaseDriver.getTechnicianEx(task.getIdZadania(), client.getIdPracownika());
            while (resultSet.next()) {
                int value = resultSet.getInt(1);
                Integer idZadEx=resultSet.getInt(2);
                Integer idZad=task.getIdZadania();
                idZadList.add(idZad);
                idZadExList.add(idZadEx);
                exIds.add(value);
            }
        }
    }

    public void clearExIds() {
        idZadExList.clear();
        idZadList.clear();
        exIds.clear();
    }

    public void setExModel() throws SQLException {
        int i=0;
        for (Integer id : exIds) {
            ResultSet resultSet = dataBaseDriver.getExById(id);

            while (resultSet.next()) {

                Integer idEx= resultSet.getInt(1);
                String nazwaEksponatu=resultSet.getString(2);
                String docMiejsce=resultSet.getString(3);
                String aktualMiejscePrzech=resultSet.getString(4);
                if(!docMiejsce.isBlank() || !docMiejsce.equals("-"))
                {
                    Exhibit x = new Exhibit(idEx,nazwaEksponatu,aktualMiejscePrzech,docMiejsce, idZadList.get(i),idZadExList.get(i));
                    i++;
                    exhibitsAssigned.add(x);
                }
            }
        }
    }
    public void checkExhibit(Exhibit exhibit) {exhibitsChecked.add(exhibit);}

    public void uncheckExhibit(Exhibit exhibit) {exhibitsChecked.remove(exhibit);}

    public void clearCheckedEx() {exhibitsChecked.clear();}

    public void clearExAssigned() {exhibitsAssigned.clear();}

    public ArrayList<Exhibit> getExChecked() {return exhibitsChecked;}


    public ArrayList<Exhibit> getExhibitsAssigned() {return exhibitsAssigned;}


    ////////////////////////////////////////////////////////////////////////////////////////////////////////

}