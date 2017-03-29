# Annotation Specs

A small example to show a very simple way to enhance junit tests with texts that can be
collected and used for living documentation.
(Really! It's just some annotations with texts to put on your tests and test cases!)<br>
After the tests have run, those texts will be available as two collections;
one with texts from successful tests, and one with texts from from failed tests.
The default behaviour is to log the texts with Log4j, but you can of course do whatever you want.<br>
Only three classes, so feel free to copy and change them at your heart's will!<br><br>

Run the included test class for an example.
The logger will print to the console and specification.log

## Basic Usage
* Annotate your Junit test class and provide a name and description of the tests/examples in the class.
```
@Specification(
        name="List behavior",
        description = "A List can be ordered or unordered and contain the same entry many times.")
```
* Enhance the Junit test class with a Junit Rule.
```
@Rule
public SpecificationLogger logger = new SpecificationLogger();
```

* Annotate each test case and provide a description.
```
@KeyExample(description = "Adding a value to an ArrayList will increment its size")
```

* Run the tests. When all the tests are run, the descriptions for both successful
and failed tests/examples will be logged.

## Advanced Usage
No

