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

package com.orientechnologies.lucene.test.geo;

import com.orientechnologies.common.io.OIOUtils;
import com.orientechnologies.lucene.test.BaseSpatialLuceneTest;
import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.spatial4j.core.context.jts.JtsSpatialContext;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Enrico Risa on 07/08/15.
 */

@Test(groups = "embedded")
public class LuceneSpatialMultiPolygonTest extends BaseSpatialLuceneTest {

  private static final String MULTIWKT = "MULTIPOLYGON(((-159.656175 21.969122,-159.6561467 21.9690575,-159.6557896 21.9681398,-159.6553325 21.9669081,-159.6552805 21.9666414,-159.6552255 21.9657462,-159.655233 21.9657339,-159.6555663 21.9651873,-159.6559767 21.9645571,-159.656648 21.9640088,-159.6578895 21.9630017,-159.6590416 21.9619414,-159.6598896 21.9610959,-159.6604191 21.9601034,-159.6608596 21.9591291,-159.6615639 21.9574598,-159.6621416 21.9559216,-159.6625578 21.9552083,-159.6634363 21.954072,-159.6643206 21.953279,-159.6656085 21.9523019,-159.6659743 21.9516061,-159.666044 21.9515124,-159.666286 21.951219,-159.666366 21.951123,-159.666431 21.951143,-159.667107 21.951324,-159.6676335 21.9521128,-159.668363 21.9526701,-159.6693501 21.9529487,-159.670423 21.9531875,-159.6720537 21.9538244,-159.6743712 21.9543816,-159.6768603 21.9547797,-159.682673 21.955736,-159.685607 21.956101,-159.686706 21.956305,-159.688438 21.956628,-159.690609 21.95701,-159.691179 21.957084,-159.692888 21.957308,-159.693459 21.957383,-159.693193 21.957764,-159.692816 21.958307,-159.692484 21.958961,-159.692275 21.959376,-159.692245 21.959444,-159.6921315 21.9594997,-159.6920045 21.9596087,-159.6919485 21.9596587,-159.6918228 21.9599765,-159.6916513 21.9606401,-159.69152 21.9613,-159.689392 21.961244,-159.682434 21.96101,-159.68116 21.96097,-159.67998 21.96092,-159.679973 21.961287,-159.67997 21.96153,-159.67995 21.96157,-159.67991 21.96163,-159.6797 21.96198,-159.67953 21.96224,-159.679516 21.962261,-159.679327 21.962577,-159.679298 21.962623,-159.679213 21.962765,-159.679185 21.962812,-159.67909 21.96297,-159.679 21.96296,-159.67886 21.96291,-159.67882 21.96293,-159.67881 21.96299,-159.67879 21.96303,-159.67875 21.96308,-159.678736 21.963117,-159.67872 21.96316,-159.678329 21.965107,-159.678196 21.965772,-159.678091 21.965764,-159.678038 21.965764,-159.677566 21.96577,-159.677409 21.965772,-159.677111 21.965312,-159.676705 21.965227,-159.675666 21.965012,-159.67518 21.964613,-159.674748 21.964343,-159.674331 21.964212,-159.674167 21.964397,-159.673531 21.965119,-159.673829 21.9654,-159.674723 21.966246,-159.675022 21.966528,-159.67501 21.966526,-159.674561 21.966493,-159.674455 21.966471,-159.67426 21.966371,-159.673965 21.966322,-159.673892 21.966287,-159.673752 21.966222,-159.673357 21.965997,-159.673056 21.965848,-159.672395 21.965451,-159.672052 21.965187,-159.671556 21.964878,-159.670943 21.964437,-159.670927 21.964427,-159.670488 21.964167,-159.670116 21.963881,-159.670008 21.963726,-159.669847 21.963756,-159.669639 21.963641,-159.669619 21.963625,-159.669537 21.963558,-159.669428 21.963345,-159.669312 21.963149,-159.669161 21.962999,-159.668896 21.962808,-159.668757 21.962714,-159.668585 21.962597,-159.668371 21.962554,-159.668346 21.962561,-159.668271 21.962582,-159.668246 21.962589,-159.668126 21.96261,-159.667978 21.962637,-159.667813 21.962667,-159.667794 21.962707,-159.667743 21.962817,-159.667687 21.962869,-159.667667 21.962889,-159.667544 21.963051,-159.667498 21.963113,-159.667182 21.963533,-159.667164 21.963517,-159.667123 21.963483,-159.666666 21.962815,-159.666483 21.962507,-159.666273 21.962136,-159.666134 21.96189,-159.665974 21.961678,-159.66591 21.961593,-159.665827 21.961498,-159.665662 21.961307,-159.665526 21.961197,-159.665295 21.961061,-159.665284 21.961054,-159.665071 21.961008,-159.665019 21.960996,-159.66493 21.960977,-159.664862 21.960978,-159.66481 21.96098,-159.664735 21.960982,-159.664611 21.961004,-159.664458 21.961114,-159.663927 21.962326,-159.663879 21.962365,-159.663284 21.963659,-159.663055 21.964187,-159.662735 21.964932,-159.662454 21.965314,-159.66234 21.965471,-159.662169 21.965659,-159.66211 21.96578,-159.661744 21.96632,-159.661714 21.966347,-159.661667 21.966502,-159.661514 21.966882,-159.661425 21.967008,-159.661207 21.967416,-159.660888 21.967785,-159.660888 21.967823,-159.660812 21.967912,-159.660688 21.967989,-159.660523 21.968038,-159.660003 21.968105,-159.659318 21.968298,-159.659224 21.968331,-159.658993 21.968458,-159.658781 21.96854,-159.658415 21.9687,-159.658202 21.968755,-159.658126 21.968805,-159.65724 21.969835,-159.657193 21.969895,-159.657241 21.970154,-159.657288 21.970314,-159.657424 21.970606,-159.657453 21.970655,-159.657453 21.970688,-159.657518 21.971041,-159.65753 21.971135,-159.65753 21.971162,-159.657657 21.972474,-159.657928 21.975265,-159.65791 21.975413,-159.658004 21.975645,-159.658123 21.976008,-159.658217 21.976173,-159.658058 21.976151,-159.658041 21.976137,-159.657881 21.976003,-159.657668 21.975854,-159.657595 21.975835,-159.657579 21.975832,-159.657414 21.975827,-159.657426 21.975595,-159.65741 21.97546,-159.657402 21.975386,-159.657242 21.974835,-159.657189 21.974626,-159.657148 21.974439,-159.657139 21.974393,-159.657089 21.974136,-159.657013 21.974063,-159.65701 21.97398,-159.65698 21.97368,-159.656967 21.973491,-159.65695 21.97324,-159.65691 21.97294,-159.65683 21.97241,-159.65681 21.97231,-159.65679 21.97209,-159.656783 21.971781,-159.65678 21.9716,-159.65679 21.97145,-159.65681 21.97134,-159.65682 21.97121,-159.65683 21.97109,-159.65683 21.97086,-159.656815 21.970643,-159.656814 21.970637,-159.6568 21.97043,-159.65673 21.97002,-159.65672 21.96987,-159.65668 21.96975,-159.6566381 21.9696663,-159.65662 21.96963,-159.65657 21.96951,-159.65651 21.96935,-159.65647 21.96922,-159.656426 21.968976,-159.65642 21.96894,-159.65639 21.9687,-159.65634 21.96841,-159.656299 21.968483,-159.656225 21.968618,-159.656209 21.968713,-159.656205 21.96874,-159.656206 21.968797,-159.656207 21.968862,-159.65621 21.968958,-159.656188 21.969057,-159.656175 21.969122)))";

  @Override
  protected String getDatabaseName() {
    return "spatialMultiPolygonTest";
  }

  @BeforeClass
  public void init() {
    initDB();

    OSchema schema = databaseDocumentTx.getMetadata().getSchema();
    OClass v = schema.getClass("V");
    OClass oClass = schema.createClass("Place");
    oClass.setSuperClass(v);
    oClass.createProperty("location", OType.EMBEDDED, schema.getClass("OMultiPolygon"));
    oClass.createProperty("name", OType.STRING);

    databaseDocumentTx.command(new OCommandSQL("CREATE INDEX Place.location ON Place(location) SPATIAL ENGINE LUCENE")).execute();

  }

  @Test
  public void testMultiPolygonWithoutIndex() {
    databaseDocumentTx.command(new OCommandSQL("DROP INDEX Place.location")).execute();
    queryMultiPolygon();
  }

  @Test
  public void testIndexingMultiPolygon() throws IOException {

    ODocument location = loadMultiPolygon();

    ODocument italy = new ODocument("Place");
    italy.field("name", "Italy");
    italy.field("location", location);
    databaseDocumentTx.save(italy);

    OIndex<?> index = databaseDocumentTx.getMetadata().getIndexManager().getIndex("Place.location");

    Assert.assertEquals(index.getSize(), 1);

    InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("multipolygon.txt");

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    OIOUtils.copyStream(systemResourceAsStream, outputStream, -1);
    databaseDocumentTx.command(
        new OCommandSQL("insert into Place set name = 'Test' , location = ST_GeomFromText('" + outputStream.toString() + "')"))
        .execute();

    databaseDocumentTx.command(
        new OCommandSQL("insert into Place set name = 'Test1' , location = ST_GeomFromText('" + MULTIWKT + "')")).execute();

    Assert.assertEquals(index.getSize(), 3);

    queryMultiPolygon();

  }

  // TODO reanable and disable validity check
  @Test(enabled = false)
  public void testReadingMultiPolygon() throws IOException, ParseException {
    InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream("multipolygon_err.txt");

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    OIOUtils.copyStream(systemResourceAsStream, outputStream, -1);

    JtsSpatialContext.GEO.getWktShapeParser().parse(outputStream.toString());
  }

  // DISABLED
  protected void queryMultiPolygon() {

    String query = "select * from Place where location && 'POLYGON((-162.5537109375 62.11416112594049,-161.87255859375 61.80428390136847,-161.455078125 61.92861247439052,-160.7958984375 62.03183469254472,-160.24658203125 62.196264616146884,-160.64208984375 62.63376960786813,-160.2685546875 63.00513377927512,-159.9609375 63.450509218001095,-159.4775390625 63.860035895395306,-158.97216796875 64.28275952823394,-158.79638671875 64.54844014422517,-158.4228515625 64.77412531292873,-157.43408203125 64.95146502589559,-156.77490234375 64.87693823228864,-155.85205078125 64.79284777557432,-155.41259765625 64.94216049820734,-154.18212890625 64.92354174306496,-154.8193359375 64.76475920891367,-155.56640625 64.65211223878966,-156.51123046875 64.58618480339979,-157.17041015625 64.5578812115091,-157.763671875 64.69910544204765,-158.04931640625 64.33039136366138,-158.26904296875 64.05297838071347,-158.62060546875 63.68524808030714,-159.06005859375 63.361982464431236,-159.45556640625 62.96521201337507,-159.5654296875 62.63376960786813,-159.521484375 62.27814559876582,-160.77392578125 61.53316997618228,-162.53173828125 61.4597705702975,-162.861328125 61.762728830472696,-163.14697265625 62.12443624549497,-162.5537109375 62.11416112594049))' ";
    List<ODocument> docs = databaseDocumentTx.query(new OSQLSynchQuery<ODocument>(query));

    Assert.assertEquals(docs.size(), 1);

    query = "select * from Place where location && 'MULTIPOLYGON("
        + "((-159.78515625 22.126354759919685,-159.510498046875 22.238259929564308,-159.27978515625 22.16705785788614,-159.290771484375 21.9328547363353,-159.466552734375 21.841104749065046,-159.796142578125 21.94304553343818,-159.78515625 22.126354759919685)),"
        + "((-157.269287109375 21.233062254412808,-157.10174560546875 21.20233749272323,-157.005615234375 21.189533621502626,-156.95343017578125 21.217700673132317,-156.8902587890625 21.194655303138642,-156.8133544921875 21.192094484509035,-156.697998046875 21.174167511794955,-156.7364501953125 21.081937360616084,-156.86004638671875 21.025546284581797,-157.07977294921875 21.066560095381984,-157.247314453125 21.06912308335471,-157.335205078125 21.112687117676757,-157.269287109375 21.18185076626612,-157.269287109375 21.233062254412808)),"
        + "((-157.06878662109375 20.92296241226858,-157.060546875 20.861378341878538,-157.01934814453125 20.825442642791966,-157.005615234375 20.745840238902257,-156.95068359375 20.72529087399421,-156.84906005859375 20.73556590521865,-156.79962158203125 20.79463378941528,-156.796875 20.84597837877989,-156.86553955078125 20.91013448169267,-156.9671630859375 20.93578924489374,-157.03033447265625 20.93578924489374,-157.06878662109375 20.92296241226858)),"
        + "((-156.7034912109375 20.925527866647226,-156.67327880859375 21.002471054356725,-156.5936279296875 21.040927787394494,-156.5277099609375 20.981956742832327,-156.5057373046875 20.943484817224167,-156.456298828125 20.92296241226858,-156.37115478515625 20.948614979019347,-156.28326416015625 20.96400440917832,-156.16241455078125 20.89217353537473,-156.04705810546875 20.835710860933656,-155.95367431640625 20.77409105752739,-155.994873046875 20.658486188041294,-156.0992431640625 20.599365240955553,-156.302490234375 20.56336734348637,-156.43707275390625 20.571081893508193,-156.4727783203125 20.658486188041294,-156.47552490234375 20.761250430919663,-156.49749755859375 20.781794909576234,-156.55242919921875 20.756113874762082,-156.66229248046875 20.80747157680652,-156.7034912109375 20.86907773201848,-156.7034912109375 20.925527866647226)),"
        + "((-156.60736083984375 20.499064283413055,-156.7034912109375 20.4964915991075,-156.71722412109375 20.532505247689578,-156.67327880859375 20.57879605371868,-156.59637451171875 20.609648794045206,-156.5386962890625 20.607077970830282,-156.52496337890625 20.560795740208448,-156.54144287109375 20.514499482150526,-156.5716552734375 20.50678207718623,-156.60736083984375 20.499064283413055)),"
        + "((-155.928955078125 20.25704380463238,-155.7476806640625 20.2725032501349,-155.58837890625 20.17456745043183,-155.3741455078125 20.12299755620777,-155.2093505859375 19.99916046737026,-154.9786376953125 19.808054128088585,-154.786376953125 19.580493479202527,-154.7259521484375 19.49248592618279,-154.9346923828125 19.331878440818787,-155.3851318359375 19.129599439736833,-155.5224609375 18.932268511298087,-155.7806396484375 18.916679786648565,-155.93994140625 19.062117883514667,-156.0003662109375 19.25929414046391,-156.0113525390625 19.54943746814108,-156.192626953125 19.766703551716972,-155.950927734375 19.921712747556207,-155.9344482421875 20.13847031245115,-155.928955078125 20.25704380463238))"
        + ")' ";
    docs = databaseDocumentTx.query(new OSQLSynchQuery<ODocument>(query));

    Assert.assertEquals(docs.size(), 1);
  }

  @AfterClass
  public void deInit() {
    deInitDB();
  }
}
