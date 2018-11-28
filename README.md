# API RDF-over-RDBMS(RoR)

This project contains an API to run SPARQL queries over a relational database made over [Ontop](https://ontop.inf.unibz.it/).

## Running

To run this API you need 2 files:

* An OWL/XML file, describing the ontology schema;

* A [mapping file](), describing how the elements from the ontology schema can be retrieved from SQL queries. This file has to follow Ontop [syntax](https://github.com/ontop/ontop/wiki/ontopOBDAModel).

A simple use example can be found at [App.java](https://github.com/InsightLab/rdf-over-rdbms/blob/master/src/main/java/App.java).

## Extending

You can implement your own RoR API. Just implement the RoR [interface]((https://github.com/InsightLab/rdf-over-rdbms/blob/master/src/main/java/br/ufc/insightlab/ror/ROR.java)) to your own framework. 
