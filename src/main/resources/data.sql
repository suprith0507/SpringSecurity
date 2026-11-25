INSERT INTO customers (customer_number,username,password,full_name,email,mobile_number,role,enabled,created_by,modified_by,created_date,modified_date) VALUES ('CUST1001','john.doe','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','John Doe','john.doe@gmail.com','9876543210','CUSTOMER',true,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO customers (customer_number,username,password,full_name,email,mobile_number,role,enabled,created_by,modified_by,created_date,modified_date) VALUES ('CUST1002','jane.smith','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','Jane Smith','jane.smith@gmail.com','9876543211','CUSTOMER',true,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO customers (customer_number,username,password,full_name,email,mobile_number,role,enabled,created_by,modified_by,created_date,modified_date) VALUES ('ADMIN001','admin','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','System Administrator','admin@gmail.com','9999999999','ADMIN',true,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO aadhaar_cards (aadhaar_number,customer_id,created_by,modified_by,created_date,modified_date) VALUES ('123412341234',1,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO aadhaar_cards (aadhaar_number,customer_id,created_by,modified_by,created_date,modified_date) VALUES ('567856785678',2,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO transactions (transaction_type,amount,customer_id,created_by,modified_by,created_date,modified_date) VALUES ('DEPOSIT',5000.00,1,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO transactions (transaction_type,amount,customer_id,created_by,modified_by,created_date,modified_date) VALUES ('WITHDRAWAL',1000.00,1,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO transactions (transaction_type,amount,customer_id,created_by,modified_by,created_date,modified_date) VALUES ('DEPOSIT',25000.00,2,'SYSTEM','SYSTEM',NOW(),NOW());

INSERT INTO transactions (transaction_type,amount,customer_id,created_by,modified_by,created_date,modified_date) VALUES ('TRANSFER',5000.00,2,'SYSTEM','SYSTEM',NOW(),NOW());