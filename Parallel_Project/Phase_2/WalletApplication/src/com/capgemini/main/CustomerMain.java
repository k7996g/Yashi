package com.capgemini.main;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.beans.Customer;
import com.capgemini.exceptions.DuplicateMobileNumberException;
import com.capgemini.exceptions.InsufficientAmountException;
import com.capgemini.exceptions.InvalidInputException;
import com.capgemini.exceptions.MobileNumberDoesNotExistException;
import com.capgemini.repo.WalletRepoImpl;
import com.capgemini.service.WalletServiceImpl;

public class CustomerMain {
	static Scanner scanner = new Scanner(System.in);
	WalletRepoImpl walletRepo = new WalletRepoImpl();
	WalletServiceImpl walletService = new WalletServiceImpl(walletRepo);

	public static void main(String[] args) throws Exception {

		CustomerMain customerMain = new CustomerMain();
		while (true) {
			customerMain.showMenu();
		}

	}

	public void showMenu() {
		System.out.println("----------Wallet-----------\n\n");

		System.out.println("1)Create Account");
		System.out.println("2)Balance Enquiry");
		System.out.println("3)Deposit Amount");
		System.out.println("4)Withdraw Funds");
		System.out.println("5)Transfer Funds");
		System.out.println("6)Exit");

		System.out.println("\nChoose Operation:");
		int choice = scanner.nextInt();
		switch (choice) {

		case 1:
			createAccount();
			break;

		case 2:
			showBalance();
			break;

		case 3:
			deposit();
			break;

		case 4:
			withdraw();
			break;

		case 5:
			transfer();
			break;

		case 6:
			System.out.println("Are you sure you want to exit? (yes/no)");
			String reply = scanner.next();
			if (reply.trim().equalsIgnoreCase("yes")) {
				exitWallet();
			}
			break;

		default:
			System.out.println("Invalid Choice! Please try again.");
			break;
		}
	}

	public void exitWallet() {
		System.out.println("\n--------- Thank you for using MyWallet services. Have a nice day! :) ----------- \n");
		System.exit(0);
	}

	private void transfer() {
		System.out.println("\nEnter Sender's Phone Number: ");
		String sourceMobileNo = scanner.next();

		System.out.println("\nEnter Recipient's Phone Number:");
		String targetMobileNo = scanner.next();

		System.out.println("\nEnter Amount to Transfer:  ");
		BigDecimal amount = scanner.nextBigDecimal();

		try {
			if (walletService.validateMobNo(sourceMobileNo) && walletService.validateMobNo(targetMobileNo)) {
				if (walletService.validateAmount(amount)) {

					try {
						Customer c = walletService.fundTransfer(sourceMobileNo, targetMobileNo, amount);
						System.out.println("Amount of " + amount
								+ " has been SUCCESSFULLY transferred to A/C linked to Phone Number : xxxxxx"
								+ targetMobileNo.substring(6) + "\n");
						System.out.println("Balance in A/C: " + c.getWallet().getAmount() + "\n");

					} catch (InsufficientAmountException e) {
						// e.printStackTrace();
						System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
					} catch (Exception e) {
						// e.printStackTrace();
						System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
					}
				} else {
					System.out.println("Invalid Amount. Please enter a POSITIVE amount. Thanks. :)");
				}
			} else {
				System.out.println("Mobile Number is not Valid!");
			}

		} catch (InvalidInputException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		}

	}

	private void withdraw() {

		System.out.println("\nEnter Phone Number: ");
		String mobileNumber = scanner.next();

		System.out.println("\nEnter Amount to Withdraw:  ");
		BigDecimal amount = scanner.nextBigDecimal();
		try {
			if (walletService.validateMobNo(mobileNumber)) {
				if (walletService.validateAmount(amount)) {
					try {
						Customer c = walletService.withdrawAmount(mobileNumber, amount);
						System.out
								.println("Amount of " + amount + " has been debited from A/C linked to Phone Number : "
										+ " xxxxxx" + mobileNumber.substring(6) + "\n");
						System.out.println("Balance in A/C: " + c.getWallet().getAmount() + "\n");

					} catch (InsufficientAmountException e) {
						// e.printStackTrace();
						System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
					} catch (Exception e) {
						// e.printStackTrace();
						System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
					}
				} else {
					System.out.println("Invalid Amount. Please enter a POSITIVE amount. Thanks. :)");
				}
			} else {
				System.out.println("Mobile Number is not Valid!");
			}

		} catch (InvalidInputException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		}

	}

	private void deposit() {
		System.out.println("\nEnter Phone Number: ");
		String mobileNumber = scanner.next();
		System.out.println("\nEnter Amount to Deposit:  ");
		BigDecimal amount = scanner.nextBigDecimal();
		try {
			if (walletService.validateMobNo(mobileNumber)) {
				if (walletService.validateAmount(amount)) {
					try {
						Customer c = walletService.depositAmount(mobileNumber, amount);

						System.out.println(
								"Amount of " + amount + " deposited Successfully to A/C linked to Phone Number : "
										+ " xxxxxx" + mobileNumber.substring(6) + "\n");
						System.out.println("Balance in A/C: " + c.getWallet().getAmount() + "\n");

					} catch (MobileNumberDoesNotExistException e) {
						// e.printStackTrace();
						System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
					} catch (Exception e) {
						// e.printStackTrace();
						System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
					}
				} else {
					System.out.println("Invalid Amount. Please enter a POSITIVE amount. Thanks. :)");
				}
			}

			else {
				System.out.println("Mobile Number not Valid!");
			}
		} catch (InvalidInputException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		}

	}

	private void showBalance() {
		System.out.println("\nEnter Phone Number: ");
		String mobileNumber = scanner.next();
		try {
			if (walletService.validateMobNo(mobileNumber)) {
				Customer c = walletService.showBalance(mobileNumber);
				System.out.println("Balance in Your Wallet : " + c.getWallet().getAmount() + "\n");
			} else {
				System.out.println("Mobile Number is not Valid!");
			}

		} catch (MobileNumberDoesNotExistException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
		}
	}

	private void createAccount() {
		System.out.println("Enter Credentials to Create an Account with MyWallet");

		System.out.println("\nEnter Your Name: ");
		String name = scanner.next();

		System.out.println("Enter Your Phone Number:");
		String mobileNumber = scanner.next();

		System.out.println("\nEnter Amount to Deposit:  ");
		BigDecimal amount = scanner.nextBigDecimal();
		try {
			if (walletService.validateMobNo(mobileNumber)) {
				if (walletService.validateName(name)) {
					if (walletService.validateAmount(amount)) {
						walletService.createAccount(name, mobileNumber, amount);
						System.out.println("\nDear " + name.toUpperCase()
								+ ", Your MyWallet Account has been SUCCESSFULLY created. Your MyWallet ID is your Phone Number : "
								+ mobileNumber + "\n");
						System.out.println("\nBalance in A/C: " + amount + "\n");
					} else {
						System.out.println("Amount is not Valid!");
					}
				} else {
					System.out.println("Name is not Valid!");
				}
			} else {
				System.out.println("Mobile Number is not Valid!");
			}

		} catch (DuplicateMobileNumberException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
		}

	}
}

