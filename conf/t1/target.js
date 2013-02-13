[ {
    "type" : "Target",
    "name" : "t1",
    "shape" : [ 90, 180 ],
    "base" : "data/1",
    "beginDate" : "200301",
    "endDate" : "200412",
    "enabled" : [ "EDGAR", "MEIC", "EMEP"],
    "timeScale" : "month",
    "pathTemplate" : "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>"
  } ]