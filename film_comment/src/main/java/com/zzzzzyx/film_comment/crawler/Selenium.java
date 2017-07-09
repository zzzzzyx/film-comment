package com.zzzzzyx.film_comment.crawler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium {
	
	private LinkedList<Integer> movieIdList = new LinkedList<Integer>();
	
	public Selenium(){
		super();
	//	movieIdList.add(new Integer(25812712));
	//	movieIdList.add(new Integer(26662798));
	//	movieIdList.add(new Integer(26754831));
	//	movieIdList.add(new Integer(25824686));
	//	movieIdList.add(new Integer(26667056));
	//	movieIdList.add(new Integer(26650037));
	//	movieIdList.add(new Integer(26766760));
	//	movieIdList.add(new Integer(26606242));
	//	movieIdList.add(new Integer(26425072));
	//	movieIdList.add(new Integer(11803087));
	//	movieIdList.add(new Integer(26920269));
	//	movieIdList.add(new Integer(25827741));
	//	movieIdList.add(new Integer(26916202));
	//	movieIdList.add(new Integer(27063551));
	//	movieIdList.add(new Integer(26616894));
	//	movieIdList.add(new Integer(1578714));
	//	movieIdList.add(new Integer(26857793));
	//	movieIdList.add(new Integer(26898747));
	//	movieIdList.add(new Integer(26430107));
	//	movieIdList.add(new Integer(25863024));
	//	movieIdList.add(new Integer(27021323));
		movieIdList.add(new Integer(26776117));
	}

	public LinkedList<Integer> getMovieIdList() {
		return movieIdList;
	}
	
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
	
	public String movieCommentUrlFactory(int movieId,int count){
		int start = count*20;
		return "https://movie.douban.com/subject/"+movieId+"/comments?start="+start+"&limit=20&sort=new_score&status=P";
	}
	
	public void login(WebDriver driver){
		driver.get("https://accounts.douban.com/login?source=movie");
		driver.findElement(By.xpath("//*[@id='email']")).sendKeys("13852290911");
		driver.findElement(By.xpath("//*[@id='password']")).sendKeys("Just4mybabygirl");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id='lzform']/div[7]/input")).click();
	}

	public File createFile(String fileName){
		String path = "all_comment_data/"+fileName+".txt";
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
	
	public LinkedList<String> getComments(WebDriver driver,String url){
		LinkedList<String> commentList = new LinkedList<String>();
		driver.get(url);
		List<WebElement> commentElement = driver.findElements(By.cssSelector("div.comment-item div.comment p"));
		Iterator<WebElement> iterator = commentElement.iterator();
		while(iterator.hasNext()){
			WebElement c = iterator.next();
			commentList.add(c.getText());
		}
		return commentList;
	}
	
	public boolean writeToFile(){
		WebDriver driver = new ChromeDriver();
		login(driver);
		LinkedList<Integer> movieIdList = getMovieIdList();
		Iterator<Integer> iterator = movieIdList.iterator();
		while(iterator.hasNext()){
			int movieId = iterator.next().intValue();
			File file = createFile(getMovieName(movieId));
			FileOutputStream fileOutputStream = null;
			BufferedOutputStream writer = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				writer = new BufferedOutputStream(fileOutputStream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i<100; i++){
				String url = movieCommentUrlFactory(movieId,i);
				LinkedList<String> commentList = getComments(driver,url);
				Iterator<String> commentIterator = commentList.iterator();
				while(commentIterator.hasNext()){
					String comment = commentIterator.next()+"\r\n";
					try {
						writer.write(comment.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Selenium s = new Selenium();
		if(s.writeToFile()){
			System.out.println("读写成功，完毕");
		}else{
			System.out.println("读写失败，完毕");
		}
		
	}
}
