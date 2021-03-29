package de.n04h.towny.core.utils;

import de.n04h.towny.core.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQL {

    Connection con;

    Core core;
    public MYSQL(Core core) throws SQLException {
        this.core = core;
        con = DriverManager.getConnection("jdbc:mysql://161.97.68.227:3306/towny?user=root&password=devserver&autoReconnect=true");
    }




}
