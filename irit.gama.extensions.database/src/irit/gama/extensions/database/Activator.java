package irit.gama.extensions.database;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import irit.gama.extensions.database.sql.SqlConnection;
import irit.gama.extensions.database.sql.SqlUtils;
import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.geometry.GeometryUtils;
import msi.gama.metamodel.topology.projection.IProjection;
import msi.gama.util.list.IList;
import msi.gama.util.map.IMap;

@SuppressWarnings ({ "unchecked", "rawtypes" })
public class Activator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		GeometryUtils.addEnvelopeComputer((scope, obj) -> {

			if (!(obj instanceof IMap)) { return null; }
			final IMap<String, Object> params = (IMap<String, Object>) obj;
			SqlConnection sqlConn;
			Envelope3D env = null;
			// create connection
			sqlConn = SqlUtils.createConnectionObject(scope, params);
			// get data
			final IList gamaList = sqlConn.selectDB(scope, (String) params.get("select"));
			env = SqlConnection.getBounds(gamaList);

			IProjection gis;
			gis = scope.getSimulation().getProjectionFactory().fromParams(scope, params, env);
			env = gis.getProjectedEnvelope();

			return env;
			// ----------------------------------------------------------------------------------------------------

		});

	}

	@Override
	public void stop(final BundleContext context) throws Exception {}

}
