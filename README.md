# java-object-filter
Simple Java-based filter to filter objects based on their methods/fields.

## background
There are times in which objects are needed to be filtered based on their attributes (fields, methods, etc.). However :
1. It is often we are only allowed to access fields from their getters
2. It is also often that we don't know anything about the object until runtime

To respect (1) and (2), this filter is created. Due to (2), this program heavily utilizes JAva Reflection API.

## How to use
Simply run ```mvn package``` to compile. You can also inject the code in any program you've created. All of the required packages should be installed already within javac.

## Todo
1. Finish
2. Add DTD
3. Create test suite
