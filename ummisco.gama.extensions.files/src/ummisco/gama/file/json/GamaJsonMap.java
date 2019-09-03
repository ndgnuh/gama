/*
 * Copyright 2016-2017 Clifton Labs Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package ummisco.gama.file.json;

import java.util.Iterator;
import java.util.Map;

import msi.gama.common.util.TextBuilder;
import msi.gama.util.map.GamaMap;
import msi.gaml.types.Types;

/**
 * GamaJsonMap is a GAMA compatible implementation of a json object
 *
 * @author A. Drogoul, adapted from json-simple library
 */
@SuppressWarnings ("unchecked")
public class GamaJsonMap extends GamaMap<String, Object> implements Jsonable {

	public GamaJsonMap() {
		super(0, Types.STRING, Types.NO_TYPE);
	}

	@Override
	public String toJson() {
		try (TextBuilder sb = TextBuilder.create()) {
			boolean isFirstEntry = true;
			final Iterator<Map.Entry<String, Object>> entries = this.entrySet().iterator();
			sb.append('{');
			while (entries.hasNext()) {
				if (isFirstEntry) {
					isFirstEntry = false;
				} else {
					sb.append(',');
				}
				final Map.Entry<String, Object> entry = entries.next();
				sb.append(Jsoner.serialize(entry.getKey())).append(':').append(Jsoner.serialize(entry.getValue()));
			}
			sb.append('}');
			return sb.toString();
		}
	}
}
