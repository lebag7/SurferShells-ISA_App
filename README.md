# SurferShells-ISA_App - brief documentation
The customer “International Stock Analysis” (ISA) wants to create an application which helps to stores prices for stock at a given time and compares it to previous prices.

# General Requirements:

-	Creating a new database which stores stocks and prices for a given moment in time
- The application works as a “REPL” application, which means, it only stops when the user types “exit”.

# Application Features:

-	The “import” command allows importing the file “STOCK_DATA.csv” to the ISA_App. 
	--> special feature: While importing the CSV File to the ISA_App the data is inserted into the isa_db in MySQL.
-	The “delete” command allows to delete all data from your database.
	--> special feature: Delete command using truncateTable method = the user can delete all values of each table without deleting the whole tables.
- The “search” command helps you to find the id for a company. It should support places holders, so you need to type the first characters of a company to find it.
  --> special feature: 
-	Work in progress - The “show <id>” command should show you the last ten prices for a stock with a specific id. 
-	The “add <id> <date> <price>” command should allow you add a new price for a specific time.
-	Work in progress - The “max <id>” command show the highest price for a stock ever had
-	Work in progress -  The “low <id>” command show the lowest price for a stock ever had
-	Work in progress - The “gap <id>” command show the difference between the highest and the lowest price ever recorded
-	Work in progress - The “update-industry <id> <industry>” command should update a stocks industry
-	Work in progress - The “industries” command lists all industries in the database with its ID and the number of stocks assigned to this industry
-	Work in progress - The “export” command exports all data to a CSV file which could be imported again
