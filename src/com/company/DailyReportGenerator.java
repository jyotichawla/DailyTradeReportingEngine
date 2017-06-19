/**
 * <h1>Daily Trade Reporting Engine</h1>
 * 
 * @author Jyoti Chawla
 * @since 19/06/2017
 */
package com.company;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DailyReportGenerator {

	private static DayOfWeek day;
	private static LocalDate sendDate;
	private LocalDate settlementDate;
	private Currency currency;
	private double agreedFX;
	private int units;
	private double pricePerUnit;
	private LocalDate newSettlementDate;
	private double usdAmount;
	private int tradingRank;
	private double amountUSD;
	private StringBuilder reportToBePrinted;
	private List<TradeData> totalTradeBuy;
	private List<TradeData> totalTradeSell;
	private List<TradeData> tradeDataList;

	public String generateReport(Set<TradeData> tradeData) {


		tradeDataList = new ArrayList<TradeData>();
		totalTradeBuy = new ArrayList<TradeData>();
		totalTradeSell = new ArrayList<TradeData>();

		//Process sample data 
		setUpTradingData(tradeData);

		tradeDataList = tradeData.stream().collect(Collectors.toList());

		//Sorted list of shares bought
		totalTradeBuy = sortListToCalculateRank(tradeDataList, Instruction.B);

		//Sorted list of shares sold
		totalTradeSell = sortListToCalculateRank(tradeDataList, Instruction.S);

		//Dates on which Buy Instructions have been received 
		List<LocalDate> uniqueSettlementDateBuy = findUniqueSettlementDateBuy(totalTradeBuy);

		//Dates on which Sell Instructions have been received
		List<LocalDate> uniqueSettlementDateSell = findUniqueSettlementDateBuy(totalTradeSell);

		/*Sorts the processed(Adjusted Settlement dates and USD amount of a trade) input sample data
		 * First sorting based on Settlement dates and then USD amount of a trade
		 * */ 
		tradeDataList
				.sort(Comparator.comparing(TradeData::getSettlementDate).thenComparing(TradeData::getUsdAmountOfTrade));

		// StringBuilder for report printing
		reportToBePrinted = new StringBuilder();

		//Printed Report
		reportToBePrinted
				.append("\n===============================PROCESSED SAMPLE DATA====================================\n");
		reportToBePrinted.append("Entity" + "\t" + "Instruction" + "\t" + "Adjusted Settlement Date" + "\t"
				+ "Amount in USD" + "\t" + "\n");
		for (TradeData sampleData : tradeDataList) {
			reportToBePrinted.append(sampleData.getEntity() + "\t" + sampleData.getInstruction() + "\t\t"
					+ sampleData.getSettlementDate() + "\t\t\t" + sampleData.getUsdAmountOfTrade() + "\n");
		}
		reportToBePrinted.append(
				"\n=========================================AMOUNT INCOMING=======================================\n");
		reportToBePrinted.append("Settlement Date" + "\t" + "Incoming Amount in USD\n");

		//Calculates the incoming amount of a day
		String incoming = amountEveryDay(uniqueSettlementDateSell, totalTradeSell);
		reportToBePrinted.append(incoming);

		reportToBePrinted.append(
				"\n=======================================INCOMING RANK=====================================" + "\n");
		reportToBePrinted.append("Entity" + "\t" + "Rank" + "\t" + "SettlementDate" + "\t" + "AMOUNT in USD" + "\n");
		for (TradeData tData : totalTradeSell) {
			reportToBePrinted.append(tData.getEntity() + "\t" + tData.getRank() + "\t" + tData.getSettlementDate()
					+ "\t" + tData.getUsdAmountOfTrade() + "\n");
		}

		reportToBePrinted.append(
				"\n=======================================AMOUNT OUTGOING=====================================\n");
		reportToBePrinted.append("Settlement Date" + "\t" + "Outgoing Amount in USD\n");

		//Calculates the incoming amount of a day
		String outgoing = amountEveryDay(uniqueSettlementDateBuy, totalTradeBuy);
		reportToBePrinted.append(outgoing);

		reportToBePrinted.append("\n");
		reportToBePrinted
				.append("======================================== OUTGOING Rank===================================\n");
		reportToBePrinted
				.append("Entity" + "\t" + "Rank" + "\t" + "SettlementDate" + "\t" + "AMOUNT in USD" + "\t" + "\n");
		for (TradeData tData : totalTradeBuy) {
			reportToBePrinted.append(tData.getEntity() + "\t" + tData.getRank() + "\t" + tData.getSettlementDate()
					+ "\t" + tData.getUsdAmountOfTrade() + "\n");
		}
		return reportToBePrinted.toString();

	}

	/*This method sets the price of shares for an entity in USD
	 * and settlement date according to working day 
	 * */
	public void setUpTradingData(Set<TradeData> tradeData) {

		for (TradeData td : tradeData) {
			settlementDate = td.getSettlementDate();
			currency = td.getTradingCurrency();
			newSettlementDate = setDate(settlementDate, currency);
			td.setSettlementDate(newSettlementDate);
			setUSDAmount(td);
		}

	}

	//Calculates the amount incoming or outgoing for unique dates 
	private String amountEveryDay(List<LocalDate> uniqueSettlementDates, List<TradeData> tradeDataList) {
		String str = "";
		for (LocalDate date : uniqueSettlementDates) {
			amountUSD = 0.0;
			for (TradeData tData : tradeDataList) {
				if (date.compareTo(tData.getSettlementDate()) == 0) {
					amountUSD += tData.getUsdAmountOfTrade();
				}
			}
			str = str + date + "\t" + twoDecimalPlaces(amountUSD) + "\n";
		}
		return str;
	}

	//This method calculates and sets the price of shares for an entity in USD
	private void setUSDAmount(TradeData td) {
		agreedFX = td.getAgreedFx();
		units = td.getUnits();
		pricePerUnit = td.getPricePerUnit();

		usdAmount = agreedFX * pricePerUnit * units;
		usdAmount = twoDecimalPlaces(usdAmount);
		td.setUsdAmountOfTrade(usdAmount);
	}

	/*Sorts and  returns incoming list according to Instruction (Buy or Sell)
	 * and assign ranks to entities based on incoming and outgoing amount 
	*/
	public List<TradeData> sortListToCalculateRank(List<TradeData> listToBeSorted, Instruction ins) {

		List<TradeData> totalTradeAccIns = new ArrayList<TradeData>();

		for (TradeData tempTradeData : listToBeSorted) {
			if (tempTradeData.getInstruction() == ins)
				totalTradeAccIns.add(tempTradeData);
		}

		totalTradeAccIns.sort(Comparator.comparing(TradeData::getUsdAmountOfTrade));
		Collections.reverse(totalTradeAccIns);
		tradingRank = 1;
		for (TradeData tdBuySell : totalTradeAccIns) {
			tdBuySell.setRank(tradingRank);
			tradingRank++;
		}

		totalTradeAccIns
				.sort(Comparator.comparing(TradeData::getSettlementDate).thenComparing(TradeData::getUsdAmountOfTrade));

		return totalTradeAccIns;
	}

	//Returns a list of unique dates in a list of Trading data
	private List<LocalDate> findUniqueSettlementDateBuy(List<TradeData> listTradeData) {
		List<LocalDate> uniqueSettlementDate = new ArrayList<LocalDate>();

		for (TradeData listData : listTradeData) {
			if (!uniqueSettlementDate.contains(listData.getSettlementDate()))
				uniqueSettlementDate.add(listData.getSettlementDate());
		}
		return uniqueSettlementDate;
	}

	//Method to return a a double value with two decimal places
	private double twoDecimalPlaces(double amount) {
		DecimalFormat df = new DecimalFormat("#.##");
		amount = Double.valueOf(df.format(amount));
		return amount;
	}

	//This method calculates the settlement dates based on Currency
	private LocalDate setDate(LocalDate settlementDate, Currency currency) {
		day = settlementDate.getDayOfWeek();

		/* Conditions to check for weekend day based on currency */

		switch (day) {
		case FRIDAY:
			if (currency == Currency.getInstance("AED") || currency == Currency.getInstance("SAR")) {
				sendDate = settlementDate.plusDays(2);

			} else
				sendDate = settlementDate;
			break;

		case SATURDAY:
			if (currency == Currency.getInstance("AED") || currency == Currency.getInstance("SAR")) {
				sendDate = settlementDate.plusDays(1);
			} else
				sendDate = settlementDate.plusDays(2);
			break;

		case SUNDAY:
			if (!(currency == Currency.getInstance("AED") || currency == Currency.getInstance("SAR"))) {
				sendDate = settlementDate.plusDays(1);
				break;
			}
		default:
			sendDate = settlementDate;
		}
		return sendDate;

	}

}
