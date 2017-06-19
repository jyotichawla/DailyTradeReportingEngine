/**
 * The DailyReportGeneratorTest is the test class for DailyReportGenerator
 * 
 * @author Jyoti Chawla
 * @since 19/06/2017
 */

package com.company;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class DailyReportGeneratorTest {

	private DailyReportGenerator reportGenerator;
	private List<TradeData> testTradeDateList;
	private static final double DELTA = 1e-15;

	@Before
	public void setUp() {
		reportGenerator = new DailyReportGenerator();
	}

	@Test
	public void testSettlementDateWeekendRegularCurrency() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		LocalDate expectedDate;
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData td : testTradeDateSet) {
			expectedDate = td.getSettlementDate();
			if (td.getEntity() == "Test Data Sell Rank 1") {
				assertEquals(expectedDate, LocalDate.of(2017, Month.JANUARY, 9));
			}
		}
	}

	@Test
	public void testSettlementDateWeekDayRegularCurrency() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		LocalDate expectedDate;
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData td : testTradeDateSet) {
			expectedDate = td.getSettlementDate();
			if (td.getEntity() == "Test Data Buy Rank 2") {
				assertEquals(expectedDate, LocalDate.of(2017, Month.JANUARY, 5));
			}
		}
	}

	@Test
	public void testSettlementDateWeekendAEDCurrency() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		LocalDate expectedDate;
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData td : testTradeDateSet) {
			expectedDate = td.getSettlementDate();
			if (td.getEntity() == "Test Data Sell Rank 1") {
				assertEquals(expectedDate, LocalDate.of(2017, Month.JANUARY, 9));
			}
		}
	}

	@Test
	public void testSettlementDateWeekDayAEDCurrency() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		LocalDate expectedDate;
		setData(testTradeDateSet);
		testTradeDateSet.add(new TradeData("Test Data", Instruction.B, Currency.getInstance("AED"),
				LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 8), 0.50, 200, 100.25));
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData td : testTradeDateSet) {
			expectedDate = td.getSettlementDate();
			if (td.getEntity() == "Test Data") {
				assertEquals(expectedDate, LocalDate.of(2017, Month.JANUARY, 8));
			}
		}
	}
	
	@Test
	public void testSettlementDateWeekendSARCurrency() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		LocalDate expectedDate;
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData td : testTradeDateSet) {
			expectedDate = td.getSettlementDate();
			if (td.getEntity() == "Test Data Sell Rank 1") {
				assertEquals(expectedDate, LocalDate.of(2017, Month.JANUARY, 9));
			}
		}
	}

	@Test
	public void testSettlementDateWeekDaySARCurrency() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		LocalDate expectedDate;
		setData(testTradeDateSet);
		testTradeDateSet.add(new TradeData("Test Data", Instruction.B, Currency.getInstance("SAR"),
				LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 8), 0.50, 200, 100.25));
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData td : testTradeDateSet) {
			expectedDate = td.getSettlementDate();
			if (td.getEntity() == "Test Data") {
				assertEquals(expectedDate, LocalDate.of(2017, Month.JANUARY, 8));
			}
		}
	}

	@Test
	public void testCalculateAmountInUSD() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		for (TradeData tradeData : testTradeDateSet) {
			if (tradeData.getEntity() == "Test Data Sell Rank 1") {
				assertEquals(tradeData.getUsdAmountOfTrade(), 129000, DELTA);
			}
			if (tradeData.getEntity() == "Test Data Buy Rank 2") {
				assertEquals(tradeData.getUsdAmountOfTrade(), 100000, DELTA);
			}
			if (tradeData.getEntity() == "Test Data Sell Rank 3") {
				assertEquals(tradeData.getUsdAmountOfTrade(), 27000, DELTA);
			}
		}
	}

	@Test
	public void testSortListToCalculateIncomingRank() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		testTradeDateList = testTradeDateSet.stream().collect(Collectors.toList());
		List<TradeData> incomingList = reportGenerator.sortListToCalculateRank(testTradeDateList, Instruction.S);
		for (TradeData inc : incomingList) {
			if (inc.getEntity() == "Test Data Sell Rank 1") {
				assertEquals(inc.getRank(), 1);
			}
			if (inc.getEntity() == "Test Data Sell Rank 2") {
				assertEquals(inc.getRank(), 2);
			}
			if (inc.getEntity() == "Test Data Sell Rank 3") {
				assertEquals(inc.getRank(), 3);
			}
		}
	}

	@Test
	public void testSortListToCalculateOutgoingRank() {
		Set<TradeData> testTradeDateSet = new HashSet<TradeData>();
		setData(testTradeDateSet);
		reportGenerator.setUpTradingData(testTradeDateSet);
		testTradeDateList = testTradeDateSet.stream().collect(Collectors.toList());
		List<TradeData> incomingList = reportGenerator.sortListToCalculateRank(testTradeDateList, Instruction.B);
		for (TradeData inc : incomingList) {
			if (inc.getEntity() == "Test Data Buy Rank 1") {
				assertEquals(inc.getRank(), 1);
			}
			if (inc.getEntity() == "Test Data Buy Rank 2") {
				assertEquals(inc.getRank(), 2);
			}
			if (inc.getEntity() == "Test Data Buy Rank 3") {
				assertEquals(inc.getRank(), 3);
			}
			if (inc.getEntity() == "Test Data Buy Rank 4") {
				assertEquals(inc.getRank(), 4);
			}
		}
	}

	private void setData(Set<TradeData> testTradeDateSet) {

		testTradeDateSet = new HashSet<>(Arrays.asList(
				new TradeData("Test Data Buy Rank 3", Instruction.B, Currency.getInstance("SGD"),
						LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 8), 0.50, 100, 1000),
				new TradeData("Test Data Sell Rank 3", Instruction.S, Currency.getInstance("AED"),
						LocalDate.of(2017, Month.JANUARY, 2), LocalDate.of(2017, Month.JANUARY, 6), 0.27, 100, 1000),
				new TradeData("Test Data Buy Rank 4", Instruction.B, Currency.getInstance("SAR"),
						LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 6), 0.29, 100, 1000),
				new TradeData("Test Data Sell Rank 2", Instruction.S, Currency.getInstance("AUD"),
						LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 8), 0.80, 100, 1000),
				new TradeData("Test Data Buy Rank 1", Instruction.B, Currency.getInstance("EUR"),
						LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 8), 1.12, 100, 1000),
				new TradeData("Test Data Sell Rank 1", Instruction.S, Currency.getInstance("GBP"),
						LocalDate.of(2017, Month.JANUARY, 5), LocalDate.of(2017, Month.JANUARY, 8), 1.29, 100, 1000),
				new TradeData("Test Data Buy Rank 2", Instruction.B, Currency.getInstance("USD"),
						LocalDate.of(2017, Month.JANUARY, 3), LocalDate.of(2017, Month.JANUARY, 5), 1, 100, 1000)));
	}

}
