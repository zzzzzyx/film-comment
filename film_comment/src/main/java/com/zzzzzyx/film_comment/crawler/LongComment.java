package com.zzzzzyx.film_comment.crawler;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LongComment {

	private LinkedList<Integer> movieIdList = new LinkedList<Integer>();
	
	public LongComment(){
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
	 * 获得电影名字
	 * @param movieId
	 * @return
	 */
	public String getMovieName(int movieId){
		String url = "https://movie.douban.com/subject/"+movieId+"/?from=showing";
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
	 * 创建文件并返回文件对象
	 * @param fileName 要创建的文件的名称
	 * @return
	 */
	public File createFile(String fileName){
		String path = "long_comment_data/"+fileName+".txt";
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
	
	public LinkedList<String> getMovieComment(int movieId){
		LinkedList<String> commentList = new LinkedList<String>();
		String url1 = "https://movie.douban.com/subject/"+movieId+"/reviews?start=0";
		String url2 = "https://movie.douban.com/subject/"+movieId+"/reviews?start=20";
		String url3 = "https://movie.douban.com/subject/"+movieId+"/reviews?start=40";
		commentList.addAll(getMovieCommentInfo(url1));
		commentList.addAll(getMovieCommentInfo(url2));
		commentList.addAll(getMovieCommentInfo(url3));
		return commentList;
	}
	
	private LinkedList<String> getMovieCommentInfo(String url){
		System.out.println(url);
		LinkedList<String> commentList = new LinkedList<String>();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements commentElements = doc.select("div.review-list div.review-item");
		Iterator<Element> iterator = commentElements.iterator();
		while(iterator.hasNext()){
			Element e = iterator.next();
			String commentId = e.attr("id");
			System.out.println(commentId);
		}
		return commentList;
	}
	public static void main(String[] args) {
		LongComment longComment = new LongComment();
		longComment.getMovieComment(25812712);
		System.out.println("长评读取完毕");
	}
}
