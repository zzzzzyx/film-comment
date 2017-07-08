package com.zzzzzyx.film_comment.nlp;

import java.util.ArrayList;
import java.util.List;

import com.zzzzzyx.film_comment.database.DatabaseConnector;
import com.zzzzzyx.film_comment.model.Frequency;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ArrayList<String> h = new ArrayList<String>();
    	h.add("好看！");
    	h.add("不好看！");
    	DatabaseConnector.saveComment(h,"嘿嘿");
    	
    	List<String> l = DatabaseConnector.getCommentByFilmName("嘿嘿");
    	for(String f : l){
    		System.out.println(f);
    	}
    }
}
