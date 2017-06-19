# Daily Trade Reporting Engine

A simple daily trade reporting engine for incoming instructions.
Its input is a set of instructions and its output is a (daily) report printed in console.   

## The Report should print 

- Amount in USD settled incoming everyday
- Amount in USD settled outgoing everyday
- Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest amount for a buy instruction, then foo is rank 1 for outgoing

It includes information such as:
- *Entity*: A financial entity whose shares are to be bought or sold 
- *Trade Action*: Buy (Outgoing) or Sell (Incoming) 
- *Agreed FX*: The foreign exchange rate with respect to USD that was agreed 
- *Currency*: The currency in which instruction is traded
- *Instruction Date*: Date on which the instruction was sent to JP Morgan by various clients
- *Settlement Date*: The date on which the client wished for the instruction to be settled with respect to Instruction Date
- *USD Amount*: Price per unit * Units * Agreed Fx (It is calculated during the object construction)

## The working days
Depending on the currency of each instruction the settlement date may be change.
A regular working week starts Monday and ends Friday, unless the currency of the trade is **AED** or **SAR**, 
where the work week starts Sunday and ends Thursday. No other holidays to be taken into account.
