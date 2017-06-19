/**
 * <h1>Daily Trade Reporting Engine Main Class</h1>
 * The Daily Trade Reporting Engine generates a report that shows amount in USD Incoming and Outgoing everyday.
 * The report also shows the ranking of entities based on incoming and outgoing amount in USD.
 * 
 * @author Jyoti Chawla
 * @since 19/06/2017
 */

package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		
		//Hard code Sample trading data 
		final Set<TradeData> tradeData = new HashSet<>(Arrays.asList(new TradeData("foo", Instruction.B,
				Currency.getInstance("SGD"), convertDate("01-Jan-2017"), convertDate("02-Jan-2017"), 0.50, 200, 100.25),

				new TradeData("bar", Instruction.B, Currency.getInstance("AED"), convertDate("05-Jan-2017"),
						convertDate("07-Jan-2017"), 0.22, 450, 150.5),

				new TradeData("baz", Instruction.S, Currency.getInstance("SAR"), convertDate("07-Feb-2017"),
						convertDate("09-Feb-2017"), 0.27, 500, 250.85),

				new TradeData("qux", Instruction.S, Currency.getInstance("GBP"), convertDate("21-May-2017"),
						convertDate("23-May-2017"), 1.27, 60, 11.6),

				new TradeData("quux", Instruction.B, Currency.getInstance("EUR"), convertDate("10-Mar-2017"),
						convertDate("12-Mar-2017"), 1.11, 53, 600.5),

				new TradeData("quuz", Instruction.B, Currency.getInstance("QAR"), convertDate("10-Mar-2017"),
						convertDate("12-Mar-2017"), 0.27, 23, 600.5),

				new TradeData("corge", Instruction.S, Currency.getInstance("INR"), convertDate("10-Mar-2017"),
						convertDate("13-Mar-2017"), 0.016, 56, 1200),

				new TradeData("grault", Instruction.S, Currency.getInstance("AED"), convertDate("11-Mar-2017"),
						convertDate("12-Mar-2017"), 0.22, 67, 45),

				new TradeData("garply", Instruction.B, Currency.getInstance("SAR"), convertDate("10-Mar-2017"),
						convertDate("11-Mar-2017"), 0.27, 43, 49.99),

				new TradeData("waldo", Instruction.S, Currency.getInstance("AUD"), convertDate("10-Mar-2017"),
						convertDate("11-Mar-2017"), 1.11, 34, 80.95)));

		final DailyReportGenerator dailyReportGenerator = new DailyReportGenerator();

		String toBePrinted = dailyReportGenerator.generateReport(tradeData);
		
		System.out.println(toBePrinted);
	}

	//Method to covert string date to LOcalDate 
	private static LocalDate convertDate(String dateToConvert) {
		LocalDate convertedDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			convertedDate = LocalDate.parse(dateToConvert, formatter);
		} catch (DateTimeParseException exc) {
			System.out.printf("%s is not parsable!%n", dateToConvert);
		}
		return convertedDate;
	}
}
