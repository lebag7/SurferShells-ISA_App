# SurferShells-ISA_App - User Manual
The customer “International Stock Analysis” (ISA) wants to create an application which helps to stores prices for stock at a given time and compares it to previous prices.

# General Requirements:

- Creating a new database which stores stocks and prices for a given moment in time.
- The application works as a “REPL” application, which means, it only stops when the user types “exit”.

# Application Features:

-	The “IMPORT” command allows importing the file “STOCK_DATA_3.csv” to the ISA_App. 
-	The “DELETE” command allows to delete all data from your database.
- 	The “SEARCH” command helps to find the id for a company. It supports places holders, so you need to type the first 			characters of a company to find it.	
-	The “SHOW <id>” command shows you the last ten prices for a stock with a specific id. 
-	The “ADD <id> <date> <price>” command should allow you add a new price for a specific time.
-	The “MAX <id>” command shows the highest price for a stock ever had.
-	The “LOW <id>” command shows the lowest price for a stock ever had.
-	The “GAP <id>” command shows the difference between the highest and the lowest price ever recorded.
-	The “UPDATE-INDUSTRY <id> <industry>” command updates a stocks industry.
-	The “INDUSTRIES” command lists all industries in the database with its ID and the number of stocks assigned to this 	industry.
-	The “EXPORT” command exports all data to a CSV file which could be imported again.
