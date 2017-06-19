package com.company;

import java.time.LocalDate;
import java.util.Currency;

/**
 * Describes an instruction sent by various clients in order to buy or sell
 */

public class TradeData {

	// A financial entity whose shares are to be bought or sold
	private String entity;

	// An Instruction from the client to Buy or Sell
	private Instruction instruction;

	// Date on which the instruction was sent
	private LocalDate instructionDate;

	// The date on which the client wished for the instruction to be settled with respect to Instruction Date
	private LocalDate settlementDate;

	// Currency in which the client deal
	private Currency tradingCurrency;

	// Foreign exchange rate with respect to USD that was agreed
	private double agreedFx;

	// Price of an entity per unit
	private double pricePerUnit;

	// USD amount of a trade
	private double usdAmountOfTrade;

	//Number of shares to be bought or sold
	private int units;

	//Ranking of entities based on incoming and outgoing amount
	private int rank;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Currency getTradingCurrency() {
		return tradingCurrency;
	}

	public void setTradingCurrency(Currency tradingCurrency) {
		this.tradingCurrency = tradingCurrency;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public double getAgreedFx() {
		return agreedFx;
	}

	public void setAgreedFx(double agreedFx) {
		this.agreedFx = agreedFx;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public double getUsdAmountOfTrade() {
		return usdAmountOfTrade;
	}

	public void setUsdAmountOfTrade(double usdAmountOfTrade) {
		this.usdAmountOfTrade = usdAmountOfTrade;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public TradeData(String entity, Instruction instruction, Currency tradingCurrency, LocalDate instructionDate,
			LocalDate settlementDate, double agreedFx, int units, double pricePerUnit) {
		this.entity = entity;
		this.instruction = instruction;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.tradingCurrency = tradingCurrency;
		this.agreedFx = agreedFx;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
	}

}
