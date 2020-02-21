@echo off
title BankAccountSBI
echo Bank Account Creation and Transaction 
echo If Account Not Create First Create Bank Account press 1,later press 2
echo Press any one of these options 1.BankAccCreate 2.BankTransaction
set /p option=
if %option%==1  goto accountcreate
if %option%==2  goto banktransaction

:accountcreate
javac bankacccreate.java
java bankacccreate
pause

exit
:banktransaction
javac banktransaction.java
java banktransaction

pause
exit