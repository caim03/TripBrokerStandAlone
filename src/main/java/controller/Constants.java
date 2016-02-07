package controller;

/*** This class contains the most used constant values in the application ***/

public class Constants {

    //ROLES
    public static String agent = "Agente",
                         admin = "Amministratore",
                         desig = "Designer",
                         scout = "Scout";

    //PRODUCT TYPES
    public static String packet = "Pacchetto",
                         group  = "Viaggio di gruppo",
                         travel = "Viaggio",
                         event  = "Evento",
                         stay   = "Pernottamento";

    //VEHICOLS
    public static String plane = "Aereo",
                         bus   = "Bus",
                         train = "Treno",
                         boat  = "Nave";

    public static final int minOverprice = 1,
                            maxOverprice = 2,
                                discount = 3,
                                minGroup = 4;

    public static final int costs = 1,
                          entries = 2;

}
