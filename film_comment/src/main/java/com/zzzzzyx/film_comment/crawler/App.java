package com.zzzzzyx.film_comment.crawler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 爬虫
 */
public class App 
{
	private LinkedList<Integer> movieIdList = new LinkedList<Integer>();
	
	public App(){
		super();
		movieIdList.add(new Integer(25812712));
		movieIdList.add(new Integer(26662798));
		movieIdList.add(new Integer(26754831));
		movieIdList.add(new Integer(25824686));
		movieIdList.add(new Integer(26667056));
		movieIdList.add(new Integer(26650037));
		movieIdList.add(new Integer(26766760));
		movieIdList.add(new Integer(26606242));
		movieIdList.add(new Integer(26425072));
		movieIdList.add(new Integer(11803087));
		movieIdList.add(new Integer(26920269));
		movieIdList.add(new Integer(25827741));
		movieIdList.add(new Integer(26916202));
		movieIdList.add(new Integer(27063551));
		movieIdList.add(new Integer(26616894));
		movieIdList.add(new Integer(1578714));
		movieIdList.add(new Integer(26857793));
		movieIdList.add(new Integer(26898747));
		movieIdList.add(new Integer(26430107));
		movieIdList.add(new Integer(25863024));
		movieIdList.add(new Integer(27021323));
		movieIdList.add(new Integer(26776117));
	}
	
	/**
	 * 获得电影的url
	 * @param movieId 电影在豆瓣的Id
	 * @return
	 */
	public String movieUrlFactory(int movieId){
		return "https://movie.douban.com/subject/"+movieId+"/?from=showing";
	}
	
	/**
	 * 获得评论的url
	 * @param movieId 电影在豆瓣的Id
	 * @param count 第几次来读取评论，从0开始
	 * @return
	 */
	public String movieCommentUrlFactory(int movieId,int count){
		int start = count*20;
		return "https://movie.douban.com/subject/"+movieId+"/comments?start="+start+"&limit=20&sort=new_score&status=P";
	}
	
	/**
	 * 获得电影名称
	 * @param url
	 * @return
	 */
	public String getMovieName(String url){
		Document doc = null;
        try {
			doc = (Document) Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String movieName = doc.select("div#wrapper div#content h1").first().text();
        System.out.println("电影名称:"+movieName);
        return movieName;
	}
	
	/**
	 * 获得电影的评论
	 * @param url 电影的url
	 * @return
	 */
	public LinkedList<String> getMovieComment(String url){
		LinkedList<String> commentList = new LinkedList<String>();
		Document doc = null;
        try {
			doc = (Document) Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Elements comments = doc.select("div.comment-item div.comment p");
        Iterator<Element> iterator = comments.iterator();
        while(iterator.hasNext()){
        	Element e = iterator.next();
        	commentList.add(e.text());
        }
		return commentList;
	}

	/**
	 * 创建文件并返回文件对象
	 * @param fileName 要创建的文件的名称
	 * @return
	 */
	public File createFile(String fileName){
		String path = "comment_data/"+fileName+".txt";
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("文件路径:"+file.getAbsolutePath());
		return file;
	}
	
	public boolean writeCommentToFile(){
		Iterator<Integer> movieIdList = getMovieIdList().iterator();
		while(movieIdList.hasNext()){
			int movieId = movieIdList.next().intValue();
			String movieName = getMovieName(movieUrlFactory(movieId));
			LinkedList<String> movieCommentList = new LinkedList<String>();
			for(int i = 0; i<10; i++){
				LinkedList<String> c = getMovieComment(movieCommentUrlFactory(movieId,i));
				movieCommentList.addAll(c);
			}
			File file = createFile(movieName);
			if(!file.exists()||file==null){
				return false;
			}
			FileOutputStream fileOutputStream = null;
			BufferedOutputStream writer = null;
			Iterator<String> commentIterator = movieCommentList.iterator();
			try {
				fileOutputStream = new FileOutputStream(file);
				writer = new BufferedOutputStream(fileOutputStream);
				while(commentIterator.hasNext()){
					String comment = commentIterator.next()+"\r\n";
					writer.write(comment.getBytes());
				}
				writer.flush();
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}
	
	public LinkedList<Integer> getMovieIdList() {
		return movieIdList;
	}
	
	public static void main( String[] args )
    {
    	App app = new App();
    	if(app.writeCommentToFile()){
    		System.out.println("读写完毕");
    	}else{
    		System.out.println("读写失败");
    	}
    }
}
