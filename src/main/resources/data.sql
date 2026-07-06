INSERT INTO app_users (username, secret)
SELECT 'SummaryBot', 'SERVICE_ACCOUNT_NO_LOGIN'
WHERE NOT EXISTS (SELECT 1 FROM app_users WHERE username = 'SummaryBot');