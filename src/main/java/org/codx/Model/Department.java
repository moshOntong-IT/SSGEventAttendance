package org.codx.Model;

import org.codx.Services.DbConnection;

import java.sql.*;

public class Department {

    private long id;
    private String name;

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Department department(long depID){
        Connection conn = DbConnection.connectDb();
       Department dep = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM public.\"department\" where dep_id=?");
            stmt.setObject(1,depID, Types.BIGINT);
            ResultSet rst = stmt.executeQuery();
            while(rst.next()){
                int id =  rst.getInt("dep_id");
                String name = rst.getString("dep_name");

                dep = new Department(id,name);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  dep;

    }
}
