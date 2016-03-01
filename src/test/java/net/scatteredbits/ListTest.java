package net.scatteredbits;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Specification(
        name="List behavior",
        description = "A List can be ordered or unordered and contain the same entry many times.")
public class ListTest {

    @Rule
    public SpecificationLogger logger = new SpecificationLogger();


    @KeyExample(description = "Adding a value to an ArrayList will increment its size")
    @Test public void assert_add_to_list(){
        List<String> list = new ArrayList<String>();
        assertEquals(0,list.size());
        list.add("Hello");
        assertEquals(1,list.size());
    }

    @KeyExample(description="Using contains() will validate that an object exists in an ArrayList")
    @Test public void assert_add_to_list_validates_with_contains(){
        List<String> list = new ArrayList<String>();
        assertEquals(0,list.size());
        list.add("dog");
        assertTrue(list.contains("dog"));
    }


    @KeyExample(description="remove() will remove an item from an ArrayList")
    @Test public void assert_can_remove_from_list(){
        List<String> list = new ArrayList<String>();
        list.add("dog");
        list.add("cat");
        list.remove("dog");
        assertEquals(0,list.size());
    }
}
