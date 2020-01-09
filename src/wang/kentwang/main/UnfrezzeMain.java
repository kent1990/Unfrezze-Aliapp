package wang.kentwang.main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundAuthOrderUnfreezeRequest;
import com.alipay.api.response.AlipayFundAuthOrderUnfreezeResponse;

public class UnfrezzeMain {
	public static void main(String[] args) {
	
	  Scanner scanner=new Scanner(System.in); 
	  String auth_no; 
	  String out_request_no; 
	  String amount; 
	  String remark;
	  
	  String choose="y";
	  while("y".equals(choose)) {
		  System.out.println("请输入支付宝资金授权订单号（auth_no）："); 
		  auth_no=scanner.next();
		  System.out.println("请输入商户本次资金操作的请求流水号（out_request_no）：");
		  out_request_no=scanner.next(); 
		  System.out.println("请输入本次操作解冻的金额（amount）：");
		  amount=scanner.next(); 
		  System.out.println("请输入商户对本次解冻操作的附言描述（remark）：");
		  remark=scanner.next(); 
		  //调用解冻 
		  unfrezze(auth_no, out_request_no, amount,remark);
		  System.out.print("是否继续解冻订单（y/n）：");
		  choose=scanner.next();
	  }

	}

	public static void unfrezze(String auth_no, String out_request_no, String amount, String remark) {
		// 设置aliapp应用信息
		AlipayClient alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", 
				//支付宝appId
				"201910076xxxxxx",
				//程序私钥
				"MIIEvwIBADANBXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
				"json", 
				"utf-8",
				//支付宝公钥
				"MIIBIjANBXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
				"RSA2");
		AlipayFundAuthOrderUnfreezeRequest request = new AlipayFundAuthOrderUnfreezeRequest();
		// 设置请求参数
		String setBizContent = "{" + "\"auth_no\":\"" + auth_no + "\"," + "\"out_request_no\":\"" + out_request_no
				+ "\"," + "\"amount\":" + amount + "," + "\"remark\":\"" + remark + "\","
				+ "\"extra_param\":\"{\"unfreezeBizInfo\": \"{\"bizComplete\\\":\"true\"}\"}\"" + "  }";
		request.setBizContent(setBizContent);
		// 设置响应
		AlipayFundAuthOrderUnfreezeResponse response = null;
		try {
			// 执行请求获得响应
			System.out.println("开始执行请求");
			response = alipayClient.execute(request);
			System.out.println("执行请求完毕");
		} catch (AlipayApiException e) {
			System.out.println("执行请求异常");
		}
		if (response.isSuccess()) {
			System.out.println("解冻成功！");
		} else {
			System.out.println("解冻失败！");
		}
	}
}
