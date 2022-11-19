CREATE TABLE `loan-calculator`.monthly_loan_calculation
(
    id               bigint NOT NULL AUTO_INCREMENT,
    balance_owed     decimal(19,2),
    interest_amount  decimal(19,2),
    month            int,
    payment_amount   decimal(19,2),
    principal_amount decimal(19,2),
    request_id       bigint,
    CONSTRAINT request_pk PRIMARY KEY (id),
    CONSTRAINT monthly_loan_calculation_fk FOREIGN KEY (request_id) REFERENCES `loan-calculator`.loan_request (id)
);