package org.eclipse.scout.docs.snippets.dataobject;

import java.util.List;
import java.util.Map;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.dataobject.DataObjectAttributeDescriptor;
import org.eclipse.scout.rt.platform.dataobject.DataObjectInventory;
import org.eclipse.scout.rt.platform.dataobject.DoEntity;
import org.eclipse.scout.rt.platform.dataobject.DoEntityBuilder;
import org.eclipse.scout.rt.platform.dataobject.DoList;
import org.eclipse.scout.rt.platform.dataobject.IDataObject;
import org.eclipse.scout.rt.platform.dataobject.IDataObjectMapper;

@SuppressWarnings("unused")
public class DataObjectExamples {

  void serialize() {
    //tag::create[]
    ExampleEntityDo entity = BEANS.get(ExampleEntityDo.class)
        .withName("Example")
        .withValues(1, 2, 3, 4, 5);
    //end::create[]

    //tag::serialize[]
    String string = BEANS.get(IDataObjectMapper.class).writeValue(entity);
    //end::serialize[]

    //tag::deserialize[]
    ExampleEntityDo marhalled = BEANS.get(IDataObjectMapper.class)
        .readValue(string, ExampleEntityDo.class);
    //end::deserialize[]
  }

  void accessAttributes() {
    //tag::access[]
    ExampleEntityDo entity = BEANS.get(ExampleEntityDo.class)
        .withName("Example")
        .withValues(1, 2, 3, 4, 5);

    // access using attribute accessor
    String name1 = entity.name().get();
    List<Integer> values1 = entity.values().get();

    // access using generated attribute getter
    String name2 = entity.getName();
    List<Integer> values2 = entity.getValues();
    //end::access[]
  }

  void existsAttributes() {
    ExampleEntityDo entity = BEANS.get(ExampleEntityDo.class)
        .withName("Example")
        .withValues(1, 2, 3, 4, 5);

    //tag::exists[]
    // check existence of attribute
    boolean hasName = entity.name().exists();
    //end::exists[]
  }

  void accessAttributesRaw() {
    //tag::accessRaw[]
    ExampleEntityDo entity = BEANS.get(ExampleEntityDo.class)
        .withName("Example")
        .withValues(1, 2, 3, 4, 5);

    // access name attribute by its attribute name
    Object name1 = entity.get("name"); // <1>
    String name2 = entity.get("name", String.class); // <2>
    String name3 = entity.getString("name"); // <3>

    // access values attribute by its attribute name
    List<Object> values1 = entity.getList("values"); // <4>
    List<String> values2 = entity.getList("values", String.class); // <5>
    List<String> values3 = entity.getStringList("values"); // <6>
    //end::accessRaw[]
  }

  void accessMapAttributes() {
    //tag::accessMapAttributes[]
    ExampleMapEntityDo mapEntity = BEANS.get(ExampleMapEntityDo.class);
    mapEntity.put("mapAttribute1",
        BEANS.get(ExampleEntityDo.class)
            .withName("Example")
            .withValues(1, 2, 3, 4, 5));
    mapEntity.put("mapAttribute2",
        BEANS.get(ExampleEntityDo.class)
            .withName("Example")
            .withValues(6, 7, 8, 9));

    ExampleEntityDo attr1 = mapEntity.get("mapAttribute1"); // <1>
    Map<String, ExampleEntityDo> allAttributes = mapEntity.all(); // <2>
    //end::accessMapAttributes[]
  }

  void doEntityBuilder() {
    //tag::doEntityBuilder[]
    DoEntity entity = BEANS.get(DoEntityBuilder.class)
        .put("attr1", "foo")
        .put("attr2", "bar")
        .putList("listAttr", 1, 2, 3)
        .build(); // <1>

    String entityString = BEANS.get(DoEntityBuilder.class)
        .put("attr1", "foo")
        .put("attr2", "bar")
        .putList("listAttr", 1, 2, 3)
        .buildString(); // <2>
    //end::doEntityBuilder[]
  }

  void dataObjectInterface() {
    //tag::dataObjectInterface[]
    String json = "<any JSON content>";
    IDataObjectMapper mapper = BEANS.get(IDataObjectMapper.class);
    IDataObject dataObject = mapper.readValue(json, IDataObject.class);
    if (dataObject instanceof DoEntity) {
      // handle object content
    }
    else if (dataObject instanceof DoList) {
      // handle array content
    }
    //end::dataObjectInterface[]
  }

  void abstractDataObject() {
    //tag::abstractDataObject[]
    ExampleDoEntityListDo entity = BEANS.get(ExampleDoEntityListDo.class);
    entity.withListAttribute(
        BEANS.get(ExampleEntity1Do.class)
            .withName1Ex("one-ex")
            .withName("one"),
        BEANS.get(ExampleEntity2Do.class)
            .withName2Ex("two-ex")
            .withName("two"));

    entity.withSingleAttribute(
        BEANS.get(ExampleEntity1Do.class)
            .withName1Ex("single-one-ex")
            .withName("single-one"));
    //end::abstractDataObject[]
  }

  void accessInventory() {
    //tag::accessInventory[]
    Map<String, DataObjectAttributeDescriptor> attributes =
        BEANS.get(DataObjectInventory.class).getAttributesDescription(ExampleEntityDo.class);
    attributes.forEach(
        (key, value) -> System.out.println("Attribute " + key + " type " + value.getType()));
    //end::accessInventory[]
  }
}
