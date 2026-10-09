# Name group: 404 Founders - Team 5

## 

## Members:

* NESTOR JOSE OLIVO BENAVIDEZ
* DENNIS ALEJANDRO NOBOA MONTESDEOCA 
* JULIAN ALBERTO OLALLA CHAMBA



# Project: Bookly



Google Docs Link: https://docs.google.com/document/d/1p29hUNTpuUd7aUDWdATBZTkoZmlgE62lhyKTo7Oj68M/edit?usp=sharing



# Problem

We need a system that allows managing the books available in a bookstore and recording the sales made, with the aim of maintaining proper control of the existing books, their stock, prices, and the sales carried out.

To understand what the program must accomplish, it’s first necessary to know a bit about the books, their authors, categories, price, stock, and the sales made. The system will remain simple and will be mainly focused on book management, stock control and sales recording.

Overview

In the context of a bookstore it’s necessary to keep a record of the books available for sale, including information such as the title, author, category , price, and available quantity.

When a customer buys one or more books, the sale must be recorded in the system. Based on this information, the system should automatically update the stock, calculate the total value of the sale, and generate an invoice or proof of purchase. Additionally, the system will allow you to check information about the books and sales made.

The system will also allow you analyze sales through reports by time periods and check the income earned over a specific period.

&#x20;

# Background

A bookstore is much more than just a place to purchase books. It is  about finding, choosing and selecting many different kinds of books. Each book can be identified using specific information such as its title, author, category, price and even the number of copies currently available. This information is very important to keep it safe because a bookstore needs to know which books are available, their prices and how many books are in stock.



A book is the main item you can find in a bookstore, it can be stored and offered for sale. The title represents the name of the book, while the author represents the person who wrote it, the ISBN is a unique identifier associated with a book, and a category is used to classify books depending on their content. 



The price is essential to represent the necessary money it is needed to buy a book. For a bookstore it is important to keep track of the stock, which represents the number of copies that are available for sale.



Stocks represent the quantity of copies of the books that are available in the bookstore and it is necessary for the stock to keep constantly updated, because it is not a constant number, stock can decrease or increase depending on how many books customers bought on a day or in how many new copies were added for sale. 



For example, a book can have at that moment 10 copies, if customers bought 4 copies during the day, the remaining stock at the end of the day will be: 10 - 4 = 6.

Keeping stock uploaded is fundamental for a bookstore to know which books are available or which ones need to be replaced.



A sale is a transaction where one or more books are purchased by a customer. A sale contains crucial information such as the books purchased, their prices and the date of the transaction.



When a sale is completed, the system should update the stock of the books remaining after the transaction. This helps in the consistency of inventory changes.



The total amount of sales depends on the quantity and prices of books that were sold. The total can be calculated by multiplying the price of the books by the quantity purchased and adding it with the other results if more than one kind of book were sold.



For example if a customer buys two copies of a $12 book and one copy of a $15 book, the total is: (2 x $12) + (1 x $15) = $39.

The total calculated represents the amount of money the customer should pay for the books he was searching for. 



Sales information can be analyzed by periods like days, months or years depending on the necessities of the manager. A sales report can provide important information about transactions that were realised during the selected period. 



Sales reports can also help to identify the best selling books by comparing the sales of each book, in that way it is possible to identify the most rentable books.



Revenue represents the money generated during a period. It can be calculated by adding the totals of sales made during that period. For example if three sales generated $25, $40 and $35, the total revenue in that period will be: 

Revenue =  $25 + $40 + $35 = $100



# Analyst Comparison



To justify the development and implementation of Bookly, an operational analysis was conducted comparing three primary approaches to bookstore management: traditional manual tracking using physical logbooks, general spreadsheet software (such as Excel or Google Sheets), and the custom-built Bookly desktop application.



Stock and Inventory Management Under traditional manual management, inventory control relies entirely on physical counting and handwritten logs, creating a high vulnerability to human error, forgotten entries, and zero visibility into real-time stock levels. While spreadsheets offer a digital upgrade, they still require store employees to manually update quantity cells after every transaction, leading to version control issues, file overwrites, and inaccurate inventory records over time. In contrast, Bookly automates inventory management by deducting purchased quantities directly from available stock the moment a transaction is completed, maintaining accurate stock counts without manual intervention.



Catalog Query and Search Capabilities Searching for specific titles or verifying book details in a manual ledger requires clerks to physically flip through pages, creating noticeable delays during customer service. Spreadsheets improve on this by providing basic text filtering, but search results remain prone to formatting inconsistencies, typos, and fragmented data across multiple tabs. Bookly resolves these limitations by offering a structured, multi-criteria search engine that lets clerks filter books instantaneously by title, author, category, price range, or ISBN.



Invoicing and Transaction Processing Issuing purchase receipts manually requires writing out receipts by hand, which is time-consuming and inefficient during busy hours. Spreadsheet tools lack native functionality to format and output customer-facing bills directly from raw row data. Bookly streamlines point-of-sale operations by automatically calculating item totals, applying pricing rules, and generating printed or digital invoices immediately upon sale confirmation.



Business Insights and Financial Reporting Calculating revenue or analyzing best-selling items in manual books requires hours of tedious calculation, significantly increasing the likelihood of arithmetic mistakes. Spreadsheets allow mathematical formulas, but creating multi-period financial analyses or identifying top-selling trends demands advanced technical knowledge, continuous template maintenance, and manual data aggregation. Bookly automates business reporting entirely, allowing managers to generate period-based sales summaries (daily, monthly, or yearly) and rank top-performing titles with a single click.



Usability and Operational Efficiency While pen-and-paper systems require no technical training and spreadsheets rely on existing desktop software, both solutions introduce heavy operational overhead through repetitive manual workarounds. Bookly delivers a lightweight, dedicated user interface designed specifically around bookstore workflows, minimizing technical complexity for staff while securing store data in a single system.



