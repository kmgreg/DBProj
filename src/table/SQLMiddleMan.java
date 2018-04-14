package table;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class SQLMiddleMan {

	private Statement st;

	private static final String PRIMARY_KEY_VIOLATION = "Unique index or " +
			"primary key violation";

	public SQLMiddleMan(Statement st) {
		this.st = st;
	}

	/**
	 * @param ar [name, phone]
	 */
	public void addCustomer(ArrayList<String> ar) {
		try {
			st.execute(String.format("INSERT INTO CUSTOMER VALUES (MAX" +
					"(SELECT SSN FROM CUSTOMER) + 1, \'%s\'," +
					"\'%s\');", ar.get(0), ar.get(1)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmployee(Employee emp) {
		try {
			st.execute("DELETE FROM EMPLOYEE WHERE SSN = \'" + emp.getSSN() +
					"\'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadEmpData(ObservableList<Employee> data) {
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYEE");
			rs.first();
			while (!rs.isAfterLast()) {
				data.add(new Employee(rs.getString(1), rs.getString(2), rs
						.getString(3)));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addEmployee(Employee emp) {
		try {
			ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM EMPLOYEE " +
					"WHERE SSN = " + emp.getSSN());
			rs.first();
			if (rs.getInt(1) == 0) {
				st.execute("INSERT INTO EMPLOYEE VALUES (\'" + emp.getSSN() +
						"\', \'" + emp.getName() + "\', \'" + emp.getPnumber
						() + "\')");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateEmployee(Employee newEmp, Employee oldEmp) {
		try {
			if (!newEmp.getSSN().equals(oldEmp.getSSN())) {
				st.execute("UPDATE EMPLOYEE SET SSN = \'" + newEmp.getSSN()
						+ "\' WHERE SSN = \'" + oldEmp.getSSN() + "\'");
			}
			if (!newEmp.getName().equals(oldEmp.getName())) {
				st.execute("UPDATE EMPLOYEE SET NAME = \'" + newEmp.getName
						() + "\' WHERE NAME = \'" + oldEmp.getName() + "\' " +
						"AND SSN = \'" + newEmp.getSSN() + "\'");
			}
			if (!newEmp.getPnumber().equals(oldEmp.getPnumber())) {
				st.execute("UPDATE EMPLOYEE SET PHONE = \'" + newEmp
						.getPnumber() + "\' WHERE PHONE = \'" + oldEmp
						.getPnumber() + "\' AND SSN = \'" + newEmp.getSSN() +
						"\'");
			}
			return true;
		} catch (SQLException e) {
			if (e.getMessage().contains(PRIMARY_KEY_VIOLATION)) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
		/*
		try {
			ResultSet rs = st.executeQuery("select count(*) from employee " +
					"where ssn = 0");
			System.out.println(rs.getInt(1));
		} catch (SQLException e) {
			if (e.getMessage().contains("No data available")) {
				System.out.println("squashed");
			}
			e.printStackTrace();
		}
		*/
	}

	/*
	public void addEmployee(ar) {
		try {
			st.execute(String.format());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	*/
}
