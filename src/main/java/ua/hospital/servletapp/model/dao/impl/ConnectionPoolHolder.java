package ua.hospital.servletapp.model.dao.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionPoolHolder {
	private static volatile DataSource dataSource;
    public static DataSource getDataSource(){

        if (dataSource == null){
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl("jdbc:mysql://localhost:3306/hospital_servlet_db?serverTimezone=UTC");
                    ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    ds.setUsername("root");
                    ds.setPassword("RootRoot1991");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }

}
