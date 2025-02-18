
# Product Scout Web Application

This project is a full-stack web application designed to automate the retrieval of product information from shopping list(s) uploaded by the user. The application uses React, REST API, and Apache Kafka, along with other technologies.

## Features

- **File Upload**: Users can upload a shopping list in file format.
- **Real-Time Data Processing**: The uploaded file is processed line by line, and each item is sent as a message to an Apache Kafka topic.
- **Web Scraping**: The system uses a web scraper to gather product details from the store's website for each item.
- **Backend Integration**: Once messages from the topic are consumed and their product details are gathered, the application sends the data to the backend, where it is saved and available for display.
- **Frontend Display**: The React frontend fetches the product details from the backend and displays them to the user.

## Workflow

1. **Frontend Interaction**:  
   The user uploads a shopping list file or types a product name through the React frontend. 
<img src="images/frontend-submit.png" alt="drawing" width="550"/>

2. **File Upload and Handling**:  
* _Upload Shopping List_ option: A POST request is sent to the backend with the uploaded file. The backend stores the uploaded file in the `input` folder of the local directory.
* _Type a Product Name_ option: A POST request is sent to the backend with the product name, and a file is created in the `input` folder of the local directory. The new file has the product name written to it.

3. **File Processing**:  
   A service listens for new files in the `input` folder. When a new file is detected, it triggers the file processor service.

4. **Message Production**:  
   The file processor service breaks the file into individual messages (one per line) and sends these messages to a Kafka topic using a Kafka message producer.

5. **Message Consumption**:  
   The Kafka message consumer listens for messages from the Kafka topic and processes them as they arrive.

6. **Message Processing**:  
   Upon receiving a message, the message processor service creates a product and uses the web scraper to extract relevant product details.

7. **Backend Update**:  
   The message processor sends a PUT request with the extracted product information to the backend.

8. **Frontend Display**:  
   The frontend fetches the product information from the backend and displays it to the user.

## Technologies Used

- **Frontend**: React
- **Backend**: REST API, Spring Boot, Spring MVC
- **Real-Time Messaging**: Apache Kafka
- **Web Scraping**: Jsoup (Java library for HTML parsing and extraction)
- **Database**: PostgreSQL (_in progress_)
- **Cloud Deployment**: Docker, Kubernetes (_in progress_)

## Setup Instructions

### Prerequisites

- Apache Kafka (for message handling)

### Installation

1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd <project_directory>
   ```

2. Install dependencies for both frontend and backend:
   ```bash
   npm install
   ```

3. Set up Apache Kafka and ensure the topic is configured.
* Download the latest version of Apache Kafka from https://kafka.apache.org/downloads
* Navigate to the downloaded Kafka folder (ex: `kafka-3.9.0-src`) in three terminal tabs and run the following in each tab:
   ```bash
   # Run Zookeeper
   bin/zookeeper-server-start.sh config/zookeeper.properties

   # Run server
   bin/kafka-server-start.sh config/server.properties
   
   # Run standalone server
   bin/connect-standalone.sh config/connect-standalone.properties
   ```
* Create a new topic called products with 2 partitions
   ```bash
   bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 2 --topic products
   ```
* To check the list of topics, run `bin/kafka-topics.sh --bootstrap-server localhost:9092 --list`
* To check the messages in the topic, run `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic products --from-beginning`
* To get information about the topic, run `bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic products`
* To delete the topic, run `bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic products`

4. Run the backend (`src/main/java/com/activity/product_scout/ProductScoutApplication.java`):

5. Run the frontend:
   ```bash
   cd product-display
   npm start
   ```

6. Upload a shopping list file through the frontend, and the application will handle the rest.
