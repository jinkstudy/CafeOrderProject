package jfreechart;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class MyFrame extends JFrame {

	// 우리가 만드는 화면 
	MyFrame(){
		 // *******************************************************
		 ChartA demo = new ChartA();   		 // (1) 정해진 값으로 차트
		 //ChartB demo = new ChartB();			 // (2) DB에서 가져온 값으로 차트 
         JFreeChart chart = demo.getChart();     
         ChartPanel chartPanel=new ChartPanel(chart); 
         				// JFreeChart는 ChartPanel이나 ChartFrame에만 붙일 수 있다.
         				// 차트만 출력하려면 ChartFrame에 붙여서 바로 출력하거나
         				// 기존의 화면에 보이게 하려면 ChartPanel에 붙이고 다시 우리 화면 JPanel에 붙인다. 
         add(chartPanel);
         setSize(800,400); 
         setVisible(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		MyFrame my = new MyFrame();
	}

}
