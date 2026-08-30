# rekolt-tracker
# REKOLT Planters' Cooperative Produce Tracker

## Project description
REKOLT Planters' Cooperative Produce Tracker is a console-based Java application for managing produce deliveries and member payments.
The cooperative has around 400 smallholder planters in the central uplands of Mauritius. At the collection point, each delivery is weighed and given a quality score. Previously, this information was recorded on paper, which made it difficult and time-consuming for the treasurer to calculate payments at the end of the season. This application makes the process easier by recording deliveries, calculating payments using the cooperative's rules, displaying useful season statistics, and generating a single Word report containing a payment statement for each member.

## Features
- Record deliveries through a console menu with input validation.
- Automatically assign a grade based on the quality score.
- Calculate the net payment using the five payment steps.
- Display season information, including:
  - Weekly volume by produce type
  - Total payment per member
  - Top five deliveries by value
  - Search for a delivery using its ID
  - A list with rejected deliveries removed
- Generate one Word report containing a section for each member and the season totals.
- Add a timestamp to 'output/run-log.txt' whenever a report is generated.

## Technologies
* Java 26
* Maven
* Apache POI for creating the Word report
* Git and GitHub for version control

## How to run
- Open the project in IntelliJ by selecting **File then Open** and choosing the 'rekolt-tracker' folder.
- IntelliJ should detect the 'pom.xml' file and set up the Maven project.
  After the dependencies have finished downloading, run the application by right-clicking:
  'src/main/java/mu/rekolt/app/Main.java'
  and selecting **Run 'Main.main()'**.

## Payment rules
Step                   | Rule                                                                       
1. Base price        = MZE 30 MUR/kg, BNS 90 MUR/kg, POT 45 MUR/kg, TEA 25 MUR/kg              
2. Grade multiplier   = A: 85–100 ×1.15, B: 70–84 ×1.00, C: 50–69 ×0.85, REJECT: below 50 ×0.00 
3. Category multiplier = Cereal ×1.00, Perishable ×0.90, Cash crop ×1.10                          
4. Commission          = 5% of the value after step 3                                               
5. Transport levy      = 2 MUR per kg delivered                                                     
Net payable            = Value after step 3 minus commission and transport levy                     
Validation             = Mass must be greater than 0 and not more than 5000 kg                      
A REJECT delivery is still recorded and included in the volume statistics. However, its value is zero, so no commission or transport levy is charged.

### Worked example
For member M-0042, delivering 236 kg of beans with a quality score of 91:
'236 × 90 = 21,240.00'
'21,240.00 × 1.15 = 24,426.00'
'24,426.00 × 1.00 = 24,426.00'
'24,426.00 − 1,221.30 − 472.00 = 22,732.70 MU'
The 'Objective1Demo.java' class also demonstrates this calculation step by step.

## Data types and precision
The application uses 'int' for values that should always be whole numbers, such as quality scores and delivery weeks.
'double' is used for values that can contain decimals, such as delivery mass and monetary calculations.
Money is not rounded during the calculation steps. The values are kept as 'double' throughout the payment calculation and are only formatted to two decimal places when they are displayed.

## Project structure
```text
rekolt-tracker/
├── pom.xml
├── docs/
│ ├── setup/ JDK, IDE, git identity, GitHub remote evidence
│ ├── git/ branch/merge history evidence
│ ├── collections-rationale.md
│ └── design/
│ ├── design-v1.pdf the paper design 
│ ├── design-v2.pdf the design as built 
│ └── change-log.md deviations from design-v1
├── output/
│ ├── run-log.txt one timestamped line per report generation
│ └── season-report.docx one section per member, plus season totals
└── src/main/java/mu/rekolt/
├── app/ Main, Objective1Demo, SeedData
├── model/ Member, Produce + subclasses, Delivery, Grade
├── service/ ProduceService, SeasonService, DocumentService, Payable, Reportable
└── util/ InputValidator
```

## Output
When option 3 is selected from the menu, the application creates 'output/season-report.docx'. The report contains a section for each member with their delivery table, commission, transport levy, net payable, and signature line. It then includes a closing section with each member's total and the overall season total.
Every time a report is generated, a timestamped entry is also added to 'output/run-log.txt'.

## Data storage
The delivery data is kept in memory while the application is running. It is stored in the Java collections used by 'SeasonService', including an 'ArrayList', two 'HashMaps', a 'HashSet', and a 2D array.
The delivery data is not saved to a file, so it is lost when the program is closed. The Word report and run log are the exceptions, as they are saved in the 'output/' folder.

