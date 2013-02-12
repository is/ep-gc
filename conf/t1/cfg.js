{
  "esconf" : {
    "EDGAR" : {
      "type" : "fs",
      "basePath" : "data/in",
      "timeScale" : "year",
      "name" : "EDGAR",
      "pathTemplate" : "<cf.basePath>/<es.name>/<es.species>/v42_<es.species>_<es.date>_IPCC_<es.sector>.0.1x0.1.nc|||emi_<es.speciesLower>"
    },
    "EMEP" : {
      "type" : "fs",
      "basePath" : "data/in",
      "timeScale" : "year",
      "name" : "EMEP",
      "pathTemplate" : "<cf.basePath>/<es.name>/EMEP_CO_SOx_NH3_NOx_NMVOC_<es.date>_0.5x0.5.nc|||<es.species>_<es.sector>"
    }
  },

  "root": "data",
  "conf": "conf/t2"
}