/*
 *
 *  * Copyright 2014 Orient Technologies.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  
 */

package com.orientechnologies.lucene.test;

import org.testng.annotations.Test;

//@Test(groups = "remote",enabled = false)
public class LuceneInsertUpdateRemoteTest extends LuceneInsertUpdateTest {

  public LuceneInsertUpdateRemoteTest() {
    super(true);
  }
  
  @Override
  protected String getDatabaseName() {
    return "insertUpdateRemote";
  }

  @Test
  @Override
  public void testInsertUpdateWithIndex() throws Exception {
//    databaseDocumentTx.close();
    databaseDocumentTx.reload();
//    databaseDocumentTx.open("admin", "admin");
    super.testInsertUpdateWithIndex();
  }
}
