package utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

//fix fields
public class MysqlConnect {
	private static final String alphaForPassword = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "0123456789";
	private static int numberOfAlpha = 6;
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	private static String balance;

	public static void signUp(String userName, String userPassword, String name, String surname,
			String otherName, String number, String birth, String group) {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/java?useLegacyDatetimeCode=false&amp&serverTimezone=UTC", "Anatoliy",
					"Anatoliy13");
			// here java is database name, root is username and password
			stmt = con.createStatement();
			String alpha = randomAlpha(numberOfAlpha);
			String hash = password(userPassword, alpha);
			String query;
			 query = "insert into intelligentapp (username, password, salt, name, surname, otherName, "
			 		+ "number, birth)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, userName);
			preparedStmt.setString(2, hash);
			preparedStmt.setString(3, alpha);
			preparedStmt.setString(4, name);
			preparedStmt.setString(5, surname);
			preparedStmt.setString(6, otherName);
			preparedStmt.setString(7, number);
			preparedStmt.setString(8, birth);
			//preparedStmt.setString(9, "a");

			// execute the preparedstatement
			preparedStmt.execute();
			/*
			 * rs = stmt.executeQuery(query); while (rs.next())
			 * System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " +
			 * rs.getString(3));
			 */
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException se) {
				/* can't do anything */ }
			try {
				stmt.close();
			} catch (SQLException se) {
				/* can't do anything */ }
			// try {
			// rs.close();
			// } catch (SQLException se) {
			/* can't do anything */ // }

		}
	}

	public static int login(String userName, String userPassword) {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/java?useLegacyDatetimeCode=false&amp&serverTimezone=UTC", "Anatoliy",
					"Anatoliy13");
			// here java is database name, root is username and password
			stmt = con.createStatement();
			String query = " select * from intelligentapp";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (userName.equals(rs.getString(1)) == true
						&& password(userPassword, rs.getString(3)).equals(rs.getString(2)) == true) {
					return 1;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException se) {
				/* can't do anything */ }
			try {
				stmt.close();
			} catch (SQLException se) {
				/* can't do anything */ }
			try {
				rs.close();
			} catch (SQLException se) {
				/* can't do anything */ }

		}
		return 0;
	}

	public static int checkUser(String userName) {
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/java?useLegacyDatetimeCode=false&amp&serverTimezone=UTC", "Anatoliy",
					"Anatoliy13");
			// here java is database name, root is username and password
			stmt = con.createStatement();
			String query = " select * from intelligentapp";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (userName.equals(rs.getString(1)) == true) {
					return 1;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				con.close();
			} catch (SQLException se) {
				/* can't do anything */ }
			try {
				stmt.close();
			} catch (SQLException se) {
				/* can't do anything */ }
			try {
				rs.close();
			} catch (SQLException se) {
				/* can't do anything */ }

		}
		return 0;
	}

	

	private static String randomAlpha(int count) {
		int success;
		StringBuilder builder = new StringBuilder();
		do {
			builder.setLength(0);
			while (count-- != 0) {
				int character = (int) (Math.random() * alphaForPassword.length());
				builder.append(alphaForPassword.charAt(character));
			}
			success = checkAlpha(builder.toString());
		} while (success != 0);
		return builder.toString();
	}

	private static String password(String password, String alpha) {
		/*
		 * StringBuilder pass = new StringBuilder(); pass.append(password); int number =
		 * (int) (password.length() / alpha.length()); int count = number; if
		 * (password.length() % alpha.length() == 0) { for (int i = 0; i <
		 * alpha.length(); i++) { pass.insert(number, alpha.charAt(i)); number += count
		 * + 1; } } else { for (int i = 0; i < alpha.length(); i++) {
		 * pass.insert(number, alpha.charAt(i)); number += count + 1; } }
		 */ // its my own hash
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(alpha.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword; // hash from developers sha512
	}

	private static int checkAlpha(String alpha) { //do not use without signUp function
		try {
			String query = " select * from bank";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (alpha.equals(rs.getString(4)) == true) {
					return 1;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException se) {
				/* can't do anything */ }

		}
		return 0;
	}

	public static void main(String[] args) {
		StringBuilder a = new StringBuilder();
		a.append("HelloHelloHi");
		a.insert(2, 'R');
		a.insert(5, 'R');
		a.insert(8, 'R');
		a.insert(11, 'R');
		a.insert(14, 'R');
		a.insert(17, 'R');
		String g = password("hellofr", "abcdef");
		System.out.println(g);
	}
}
