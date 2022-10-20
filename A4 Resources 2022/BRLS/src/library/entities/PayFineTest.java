package library.entities;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Date;
import library.payfine.PayFineControl;
import library.payfine.PayFineUI;
import library.returnItem.ReturnItemControl;
import library.returnItem.ReturnItemUI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PayFineTest {
	
	Loan loan;
	Patron patron;
	Item item;
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
		ric = new ReturnItemControl();
		pfc = new PayFineControl();
		riui = new ReturnItemUI(ric);
		pfui = new PayFineUI(pfc);
		
		
		loan = library.issueLoan(item, patron);
		calendar.incrementDate(4);
		library.updateCurrentLoanStatus();
		
		
		library.calculateOverDueFine(loan);
		
		//// Fine Check /////
		library.dischargeLoan(loan, false);
		
		pfui.setReady();
		pfui.setPaying();
		pfc.cardSwiped(1);
		pfc.payFine(loan.getFines());
		pfui.setCompleted();
	}
	
	@Test
	void test() {	
		assertEquals(patron.finesOwed(), loan.getFines());
	}
}



