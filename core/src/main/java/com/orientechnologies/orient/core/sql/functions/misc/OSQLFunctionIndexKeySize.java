/*
 *
 *  *  Copyright 2010-2016 OrientDB LTD (http://orientdb.com)
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *  *
 *  * For more information: http://orientdb.com
 *
 */
package com.orientechnologies.orient.core.sql.functions.misc;

import com.orientechnologies.common.util.ORawPair;
import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.sql.functions.OSQLFunctionAbstract;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * returns the number of keys for an index
 *
 * @author Luigi Dell'Aquila (l.dellaquila--(at)--orientdb.com)
 */
public class OSQLFunctionIndexKeySize extends OSQLFunctionAbstract {
  public static final String NAME = "indexKeySize";

  public OSQLFunctionIndexKeySize() {
    super(NAME, 1, 1);
  }

  public Object execute(Object iThis, final OIdentifiable iCurrentRecord, Object iCurrentResult, final Object[] iParams,
      OCommandContext context) {
    final Object value = iParams[0];

    String indexName = String.valueOf(value);
    final ODatabaseDocumentInternal database = (ODatabaseDocumentInternal) context.getDatabase();
    OIndex index = database.getMetadata().getIndexManagerInternal().getIndex(database, indexName);
    if (index == null) {
      return null;
    }
    try (Stream<ORawPair<Object, ORID>> stream = index.getInternal().stream()) {
      return stream.map((pair) -> pair.first).distinct().count() + Optional.ofNullable(index.get(null)).map((entry) -> {
        if (entry instanceof Collection) {
          return ((Collection) entry).isEmpty() ? 0 : 1;
        }

        return 1;
      }).orElse(0);
    }
  }

  public String getSyntax() {
    return "indexKeySize(<indexName-string>)";
  }
}
