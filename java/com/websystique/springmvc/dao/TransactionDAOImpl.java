package com.websystique.springmvc.dao;

import java.io.FileOutputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.model.User;

@Repository("transactionDAO")
public class TransactionDAOImpl extends AbstractDao<Integer, Transaction> implements TransactionDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Transaction> getLatestTrxByAccount(int accountNum) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "SELECT * FROM TRANSACTION WHERE accountnum = :account_num";
		Query query = session.createQuery(hql);
		query.setParameter("account_num", accountNum);
		@SuppressWarnings("unchecked")
		List<Transaction> trxList = query.list();
		for(Transaction trx : trxList){
			logger.info("Transaction List::"+trx);
		}
		return trxList;
	}
	
	@Override
	public void getTransactionsPDF(String loggedinuser) {
			Session session = this.sessionFactory.getCurrentSession();
			DateFormat dateFormat = new SimpleDateFormat("MMddhhmmss");
			Date date = new Date();
			String ts = dateFormat.format(date);
			List<User> usersList = session.createQuery("from User where ssoId = '"+loggedinuser+"'").list();
			int id=0;
			if(usersList.size()>0)
				id = usersList.get(0).getId();
			List<Transaction> accountsList = session.createQuery("from Transaction where id="+id).list();
			
				 Document document = new Document();
				    try
				    {   
				    	String home = System.getProperty("user.home");
					    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(home+"/Downloads/Statement"+ts+".pdf"));
				        document.open();
				 
				        PdfPTable table = new PdfPTable(4); // 3 columns.
				        table.setWidthPercentage(100); //Width 100%
				        table.setSpacingBefore(10f); //Space before table
				        table.setSpacingAfter(10f); //Space after table
				        
				        float[] columnWidths = {1f, 1f, 1f, 1f};
				        table.setWidths(columnWidths);
				        
				        
				        PdfPCell hcell1 = new PdfPCell(new Paragraph("From"));
				        hcell1.setBorderColor(BaseColor.BLUE);
				        hcell1.setPaddingLeft(10);
				        hcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				        hcell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 
				        PdfPCell hcell2 = new PdfPCell(new Paragraph(String.valueOf("To")));
				        hcell2.setBorderColor(BaseColor.BLUE);
				        hcell2.setPaddingLeft(10);
				        hcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				        hcell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 
				        PdfPCell hcell3 = new PdfPCell(new Paragraph(String.valueOf("Transaction Amount")));
				        hcell3.setBorderColor(BaseColor.BLUE);
				        hcell3.setPaddingLeft(10);
				        hcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				        hcell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				        
				        PdfPCell hcell4 = new PdfPCell(new Paragraph(String.valueOf("Date")));
				        hcell4.setBorderColor(BaseColor.BLUE);
				        hcell4.setPaddingLeft(10);
				        hcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				        hcell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
	 
				        table.addCell(hcell1);
				        table.addCell(hcell2);
				        table.addCell(hcell3);
				        table.addCell(hcell4);
	
				 
				        //Set Column widths
				        
				        for(Transaction c : accountsList){
					        PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf(c.getTrxAccNum())));
					        cell1.setBorderColor(BaseColor.BLUE);
					        cell1.setPaddingLeft(10);
					        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 
					        PdfPCell cell2 = new PdfPCell(new Paragraph(String.valueOf(c.getTrxMerchant())));
					        cell2.setBorderColor(BaseColor.BLUE);
					        cell2.setPaddingLeft(10);
					        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 
					        PdfPCell cell3 = new PdfPCell(new Paragraph(String.valueOf(c.getTrxAmount())));
					        cell3.setBorderColor(BaseColor.BLUE);
					        cell3.setPaddingLeft(10);
					        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
					        
					        PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(c.getTrxDate())));
					        cell4.setBorderColor(BaseColor.BLUE);
					        cell4.setPaddingLeft(10);
					        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 
					        table.addCell(cell1);
					        table.addCell(cell2);
					        table.addCell(cell3);
					        table.addCell(cell4);
				        }
				        document.add(table);
				 
				        document.close();
				        writer.close();
				    } catch (Exception e)
				    {
				        e.printStackTrace();
				    }
			}

	@Override
	public void saveAccount(Transaction tx) {
		//tx.setTrxAccNum(AccountDAOImpl.accNo);
		persist(tx);
	}

	@Override
	public List<Transaction> getPendingTransaction() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Transaction> TransactionList = session.createQuery("from Transaction where trxStatusId=1").list();
		for(Transaction t : TransactionList) {
			System.out.println("Account no retrieved is "+t.getTransactionId());
		}
		return TransactionList;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Transaction> updateTransaction(int transactionId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Transaction> TransactionList = session.createQuery("from Transaction where transactionId="+transactionId).list();
		TransactionList.get(0).setTrxStatusId(3);
		List<Account> accountList = session.createQuery("from Account where account_no="+TransactionList.get(0).getTrxAccNum()).list();
		accountList.get(0).setBalance(accountList.get(0).getBalance()-TransactionList.get(0).getTrxAmount());
		session.update(accountList.get(0));
		List<Account> toAccountList = session.createQuery("from Account where account_no="+TransactionList.get(0).getTrxMerchant()).list();
		toAccountList.get(0).setBalance(toAccountList.get(0).getBalance()+TransactionList.get(0).getTrxAmount());
		session.update(toAccountList.get(0));
		session.update(TransactionList.get(0));
		return TransactionList;
		// TODO Auto-generated method stub
		
	}
}
