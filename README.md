# Hall Booking System

## Application setup:

- The application uses MySQL database, and requires user and database initialisations. Provided below is the init script. 

```
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'admin'@'localhost';
CREATE DATABASE hbs;
```

- Once the application is up and running, we need to pre-populate the database table _halls_ with static data.
```
insert into halls values (uuid_to_bin(uuid()), current_timestamp, 50, "A");
insert into halls values (uuid_to_bin(uuid()), current_timestamp, 100, "B");
insert into halls values (uuid_to_bin(uuid()), current_timestamp, 200, "C");
insert into halls values (uuid_to_bin(uuid()), current_timestamp, 350, "D");
insert into halls values (uuid_to_bin(uuid()), current_timestamp, 500, "E");
insert into halls values (uuid_to_bin(uuid()), current_timestamp, 1000, "F");
```

- That's it! We're good to use the application.

##**REST API endpoints:**

- Check current availability: 
> localhost:8080/hall/availability/check

Note: Time is in 24hr format, and date in yyyy-mm-dd format

Sample request body:
```
{
    "capacity" : "500",
    "bookingDate": "2021-04-17",
    "startTime": "12:00",
    "endTime": "14:30"
}
```
 
- Book a hall: 
> localhost:8080/hall/{{hall-id}}/book

Note: It utilizes the same request body as the availability check endpoint.

- See all bookings: 
> localhost:8080/hall/bookings/fetch

Sample request body:
```
{
    "startDate": "2021-04-18",
    "endDate": "2021-04-20"
}
```