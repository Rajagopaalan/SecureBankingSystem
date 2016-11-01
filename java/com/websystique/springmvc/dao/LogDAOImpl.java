package com.websystique.springmvc.dao;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.Log;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.TransactionService;


@Transactional
@Repository("logDAO")
public class LogDAOImpl  extends AbstractDao<Integer, Log> implements LogDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public void updateLogs(String info) {
		// TODO Auto-generated method stub
		System.out.println();
	//	Session session = this.sessionFactory.getCurrentSession();
		Log lg = new Log();
		System.out.println("+++++++++++++++++++++++in otpdaoimpl");
		lg.setInfo(info);
		// TODO Auto-generated method stub
		persist(lg);
	}
	
	
	
	@Override
	public void downloadLogs(){
			Session session = this.sessionFactory.getCurrentSession();
			List<Log> logList = session.createQuery("from Log").list();
			
				 Document document = new Document();
				    try
				    {   
				    	String home = System.getProperty("user.home");
				    	DateFormat dateFormat = new SimpleDateFormat("MMddhhmmss");
						Date date = new Date();
						String ts = dateFormat.format(date);
					    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(home+"/Downloads/Logs"+ts+".pdf"));
				        document.open();
				 
				        PdfPTable table = new PdfPTable(1); // 3 columns.
				        table.setWidthPercentage(100); //Width 100%
				        table.setSpacingBefore(10f); //Space before table
				        table.setSpacingAfter(10f); //Space after table
				        
				        float[] columnWidths = {1f};
				        table.setWidths(columnWidths);
				        
				        
				        PdfPCell hcell1 = new PdfPCell(new Paragraph("Logs"));
				        hcell1.setBorderColor(BaseColor.BLACK);
				        hcell1.setPaddingLeft(10);
				        hcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				        hcell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 
				        
				        table.addCell(hcell1);
				        
	
				 
				        //Set Column widths
				        
				        for(Log c : logList){
					        PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf(c.getInfo())));
					        cell1.setBorderColor(BaseColor.BLACK);
					        cell1.setPaddingLeft(10);
					        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					        table.addCell(cell1);
					        
				        }
				        document.add(table);
				 
				        document.close();
				        writer.close();
				    } catch (Exception e)
				    {
				        e.printStackTrace();
				    }
			}


}
