package jfreechart;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class MyFrame extends JFrame {

	// �츮�� ����� ȭ�� 
	MyFrame(){
		 // *******************************************************
		 ChartA demo = new ChartA();   		 // (1) ������ ������ ��Ʈ
		 //ChartB demo = new ChartB();			 // (2) DB���� ������ ������ ��Ʈ 
         JFreeChart chart = demo.getChart();     
         ChartPanel chartPanel=new ChartPanel(chart); 
         				// JFreeChart�� ChartPanel�̳� ChartFrame���� ���� �� �ִ�.
         				// ��Ʈ�� ����Ϸ��� ChartFrame�� �ٿ��� �ٷ� ����ϰų�
         				// ������ ȭ�鿡 ���̰� �Ϸ��� ChartPanel�� ���̰� �ٽ� �츮 ȭ�� JPanel�� ���δ�. 
         add(chartPanel);
         setSize(800,400); 
         setVisible(true);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		MyFrame my = new MyFrame();
	}

}
