CREATE TABLE `loan-calculator`.loan_request
(
    id              bigint NOT NULL AUTO_INCREMENT,
    interest_rate   decimal(19,2),
    loan_amount     decimal(19,2),
    loan_term_years int,
    CONSTRAINT request_pk PRIMARY KEY (id)
);