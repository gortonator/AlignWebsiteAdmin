# AlignWebsiteProject - Admin



## Database Access

The database connection in this project is using Hibernate query language using
C3P0 as the connection pool. The connection has been protected with TLS layer. To
access the connection settings, you can look at the resources sections and look at
the cfg.xml file. There are currently 6 cfg xml in the admin side to access the 
test databases and original databases for the admin, private, and public databases.
The username to access the databases for admin is "backend_admin", and the password
is "password".

## Database UML Diagram

For the higher level For all 3 schemas, you can look at the ALIGN diagram pdf files 
in this repo. For more details of the Database construction, you can look inside the
sql_scripts folder for the SQL Scripts to create the Databases.

## Admin API Documentation
https://docs.google.com/document/d/19Hpm_TniE9dJtsHz4JIIyGt0NO1B4NXXlD60bOCNZm0/edit?usp=sharing
