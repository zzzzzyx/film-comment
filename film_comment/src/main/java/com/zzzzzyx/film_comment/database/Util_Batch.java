package com.zzzzzyx.film_comment.database;

import java.sql.DriverManager;

public class Util_Batch extends Util{

    private static final String private_url = "jdbc:mysql://zzzzzyx.com:3306/moskwa"
    		+ "?useServerPrepStmts=false&rewriteBatchedStatements=true&useSSL=false";    
    private static final String private_user = "root";    
    private static final String private_password = "mypassword";   
    
    public Util_Batch() {
    	try {    
            conn = DriverManager.getConnection(private_url, private_user, private_password);//获取连接    
        } catch (Exception e) {    
            e.printStackTrace();    
        } 
    }
}