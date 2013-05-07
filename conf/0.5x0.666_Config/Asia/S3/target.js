[
{   "type" : "Target",
    "name" : "t2",
    "shape" : [ 360, 540 ],
    "base" : "data/2",
    "beginDate" : "200801",
    "endDate" : "200812",
    "zorder" : [ "EMEP","MICS_Asia","EDGAR"],
    "defaultEmission" : "EDGAR",
    "timeScale" : "month",
    "species" : [ "NH3" ],
    "clip" : { "bottom":158, "left": 375, "width":121, "height":133 }, 
    "pathTemplate" : "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>"
  }
]
