{
  "esconf" : {
    "EDGAR" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "year",
      "name" : "EDGAR",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "TBond" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "year",
      "name" : "TBond",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "SFactor_TBond" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "year",
      "name" : "SFactor_TBond",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "EMEP" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "year",
      "name" : "EMEP",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "NEI" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "year",
      "name" : "NEI",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    },
    "MEIC" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "month",
      "name" : "MEIC",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>/<es.month>.nc|||<es.species>_<es.sector>"
    },
    "MICS_Asia" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "month",
      "name" : "MICS_Asia",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>/<es.month>.nc|||<es.species>_<es.sector>"
    },
    "INTEX-B" : {
      "type" : "fs",
      "basePath" : "data/2",
      "timeScale" : "year",
      "name" : "INTEX-B",
      "mask": "<cf.up.root>/<es.name>/<es.name>_Map.nc|||<es.name>_Map",
      "pathTemplate" : "<cf.up.root>/<es.name>/<es.year>.nc|||<es.species>_<es.sector>"
    }
  },

  "defaultEmission" : "EDGAR",
  "zorder": [ "EMEP","NEI","MICS_Asia","EDGAR"  ],
  "directVocs" : ["MICS_Asia"],
  "root": "data/2",
  "conf": "conf/t2"
}
