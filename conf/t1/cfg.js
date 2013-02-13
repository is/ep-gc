{
  "esconf" : {
    "EDGAR" : {
      "type" : "fs",
      "basePath" : "data/1",
      "timeScale" : "year",
      "name" : "EDGAR",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "EMEP" : {
      "type" : "fs",
      "basePath" : "data/1",
      "timeScale" : "year",
      "name" : "EMEP",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "MEIC" : {
      "type" : "fs",
      "basePath" : "data/1",
      "timeScale" : "month",
      "name" : "MEIC",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>/<es.month>.nc|||<es.species>_<es.sector>"
    }
  },

  "root": "data/1",
  "conf": "conf/t1"
}