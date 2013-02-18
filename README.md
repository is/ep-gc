Introduction
------------
EP-GC is Emission Offline PreProcessor for GeosChem.

Requirements
------------
 * Java Develop Kit 1.7 (JDK7)
 * Maven


Installation
------------
1. Check source code from github
   <pre>
   git clone git@github.com:is/ep-gc.git
   </pre>

2. Build the source by maven
   <pre>
   cd ep-gc; mvn package
   </pre>

3. Run Demo.

   <pre>
   # Generate test data set for input
   mvn exec:java -Dexec.mainClass="ep.geoschem.demo.gen.GD1"

   # Run offline preprocessor
   mvn exec:java -Dexec.mainClass="ep.geoschem.demo.T1"
   </pre>