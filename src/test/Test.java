package test;

import java.awt.Button;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TooManyListenersException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;

import bean.Album;
import bean.Artist;
import bean.ParentImpl;
import bean.SampleData;
import bean.Track;

public class Test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//语法
		Runnable task=()->System.out.println("test...");
		Thread t=new Thread(task);
		t.start();
		//函数接口
		Predicate<Integer> positive = x-> x>0;
		Use use=new Test().new Use();
		use.func(positive);
		/////////////流///////////////////////////
		//基础
		//List<Integer> lists=Arrays.asList(1,2,3);
		List<Artist> artists=SampleData.getThreeArtists();
		List<String> listNew=artists.stream().map(artist->artist.getName()).collect(Collectors.toList());
		for(String str:listNew){
			System.out.println("artist->"+str);
		}
		String ret=artists.stream().map(artist->artist.getName()).collect(Collectors.joining(",", "[", "]"));
		System.out.println(ret);
		//将两个list整合成stream
		List<Integer> is=Stream.of(Arrays.asList(1,2),Arrays.asList(3,4)).flatMap(x->x.stream()).collect(Collectors.toList());
		for(Integer i:is){
			System.out.println("member->"+i);
		}
		//min
		List<Track> tracks=SampleData.aLoveSupreme.getTrackList();
		Track strack=tracks.stream().min(Comparator.comparing(track->track.getLength())).get();
		System.out.println("min length track->"+strack.getName());
		
		//基本类型流
		List<Integer> lists=Arrays.asList(1,2,3);
		double value=lists.stream().mapToInt(x->x).average().getAsDouble();
		System.out.println("value->"+value);
		
		//默认方法
		ParentImpl child=new ParentImpl();
		child.message("child make a message");
		System.out.println(child.getLastMessage());
		child.welcome();
		System.out.println(child.getLastMessage());
		
		Function<Artist, Long> getCount=artist->artist.getMembers().count();
		Optional<Artist> maxArtist=artists.stream().collect(Collectors.maxBy(Comparator.comparing(getCount)));
		System.out.println("max artist is "+maxArtist.get().getMembers().count());
		//并行
		List<Album> albumsList=SampleData.albumsList;
		int num=albumsList.parallelStream().flatMap(album->album.getTracks()).mapToInt(track->track.getLength()).sum();
		System.out.println("tracks total length num->"+num);
	
		
		
	}
	class Use{
		public void func(Predicate<Integer> method){
			
			System.out.println("test predicate->"+method.test(1));
		}
	}

}
