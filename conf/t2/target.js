[ {
    "type" : "Target",
    "name" : "t2",
    "shape" : [ 90, 180 ],
    "base" : "data/2",
    "beginDate" : "200701",
    "endDate" : "200812",
    "enabled" : [ "EDGAR", "MEIC", "EMEP"],
    "timeScale" : "month",
    "pathTemplate" : "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>"
  } ]
