package main1;
import java.awt.*;
import javax.swing.*;

import view.CustomerView;
import view.OrderView;
import view.StockView;

//import  view.orderView;
//import  view.customerView;
//import  view.stockView;

public class BanaCafe  extends JFrame {



	OrderView order;
	CustomerView customer;
	StockView stock;

	public BanaCafe(){

		//각각의 화면을 관리하는 클래스 객체 생성
		order = new OrderView();
		customer = new CustomerView();
		stock = new StockView();
		try {
			// 1. 자바 내장 룩앤필
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// 2. quaqua.jar : Quaqua Look and Feel

//			   UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
//			   JFrame.setDefaultLookAndFeelDecorated(true);
			// 3. liquidlnf.jar : Liquid Look and Feel
			    UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		     // 4. idw-gpl.jar : InfoNode Look and Feel
			   // UIManager.setLookAndFeel("net.infonode.gui.laf.InfoNodeLookAndFeel");
			// 5. JTattoo.jar : JTattoo Look and Feel
			  // UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			  // UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			 
		} catch (Exception e) {
			System.out.println("테마적용에러");
		}
		JTabbedPane  pane = new JTabbedPane();
		pane.addTab("주문화면", order );
		pane.addTab("고객정보", customer);
		pane.addTab("재고관리", stock );
		//pane.addTab("통계", rent );

		pane.setSelectedIndex(0);  //첫 화면

		// 화면크기지정
		add("Center", pane );
		setSize(1000,600);
		setVisible( true );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	
	}


	public static void main(String[] args) {

		new BanaCafe();


	}
}

