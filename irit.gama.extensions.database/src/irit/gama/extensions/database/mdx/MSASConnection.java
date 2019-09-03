/*********************************************************************************************
 *
 *
 * 'MSASConnection.java', in plugin 'msi.gama.core', is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package irit.gama.extensions.database.mdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.olap4j.OlapConnection;
import org.olap4j.OlapWrapper;

import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;

class MSASConnection extends MdxConnection {

	private static final String DRIVER = "org.olap4j.driver.xmla.XmlaOlap4jDriver";

	MSASConnection(final String venderName, final String url, final String port, final String dbName,
			final String userName, final String password) {
		super(venderName, url, port, dbName, userName, password);
	}

	@Override
	public OlapConnection connectMDB(final IScope scope) throws GamaRuntimeException {
		OlapWrapper wrapper;
		Connection conn;
		try {
			if (vender.equalsIgnoreCase(MSAS)) {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(
						// "jdbc:xmla:Server=http://localhost/xmla/msxisapi.dll");
						"jdbc:xmla:Server=http://" + url + ":" + port + "/" + dbName + "/msmdpump.dll;", userName,
						password);
				wrapper = (OlapWrapper) conn;
				olapConnection = wrapper.unwrap(OlapConnection.class);
			} else {
				throw GamaRuntimeException.error("MSASConnection.connectMDB: The " + vender + " is not supported!",
						scope);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			throw GamaRuntimeException.error(e.toString(), scope);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			throw GamaRuntimeException.error(e.toString(), scope);
		}
		return olapConnection;
	}

	@Override
	public OlapConnection connectMDB(final IScope scope, final String dbName) throws GamaRuntimeException {
		OlapWrapper wrapper;
		Connection conn;
		try {
			if (vender.equalsIgnoreCase(MSAS)) {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(
						"jdbc:xmla:Server=http://" + url + ":" + port + "/" + dbName + "/msmdpump.dll;", userName,
						password);
				wrapper = (OlapWrapper) conn;
				olapConnection = wrapper.unwrap(OlapConnection.class);
				// olapConnection.setCatalog(catalog);
			} else {
				throw GamaRuntimeException.error("MSASConnection.connectMDB: The " + vender + " is not supported!",
						scope);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			throw GamaRuntimeException.error(e.toString(), scope);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			throw GamaRuntimeException.error(e.toString(), scope);
		}
		return olapConnection;
	}

	@Override
	public OlapConnection connectMDB(final IScope scope, final String dbName, final String catalog)
			throws GamaRuntimeException {
		OlapWrapper wrapper;
		Connection conn;
		try {
			if (vender.equalsIgnoreCase(MSAS)) {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(
						"jdbc:xmla:Server=http://" + url + ":" + port + "/" + dbName + "/msmdpump.dll;", userName,
						password);
				wrapper = (OlapWrapper) conn;
				olapConnection = wrapper.unwrap(OlapConnection.class);
				olapConnection.setCatalog(catalog);
			} else {
				throw GamaRuntimeException.error("MSASConnection.connectMDB: The " + vender + " is not supported!",
						scope);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			throw GamaRuntimeException.error(e.toString(), scope);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			throw GamaRuntimeException.error(e.toString(), scope);
		}
		return olapConnection;
	}

}
