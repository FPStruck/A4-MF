package library.entities;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Date;

import library.borrowitem.BorrowItemControl;
import library.borrowitem.BorrowItemUI;
import library.payfine.PayFineControl;
import library.payfine.PayFineUI;
import library.returnItem.ReturnItemControl;
import library.returnItem.ReturnItemUI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class LoanLimitTest {
	
	Loan loan;
	Loan loan1;
	Loan loan2;
	Loan loan3;
	Loan loan4;
	Patron patron;
	Item item;
	Item item1;
	Item item2;
	Item item3;
	Item item4;
	Library library;
	Calendar calendar;
	PayFineControl pfc;
	PayFineUI pfui;
	ReturnItemControl ric;
	ReturnItemUI riui;

	@BeforeEach
	void Setup() throws ParseException {
		
		library = Library.getInstance();
		calendar = Calendar.getInstance();
		patron = library.addPatron("T", "J", "EG", 1);
		item = library.addItem("A", "T", "12", ItemType.BOOK);
		item1 = library.addItem("X", "I", "35", ItemType.CD);
		item2 = library.addItem("V", "M", "22", ItemType.CASSETTE);
		item3 = library.addItem("G", "Y", "7", ItemType.DVD);
		item4 = library.addItem("H", "R", "3", ItemType.VHS);
		ric = new ReturnItemControl();
		pfc = new PayFineControl();
		riui = new ReturnItemUI(ric);
		pfui = new PayFineUI(pfc);
		
		/// Loan Check
		loan = library.issueLoan(item, patron);
		loan1 = library.issueLoan(item1, patron);
		loan2 = library.issueLoan(item2, patron);
		
		library.updateCurrentLoanStatus();
	}
	
	@Test
	void test() {	
		assertEquals(patron.getNumberOfCurrentLoans(), library.getLoanLimit());
	}
}



