[
{   "type" : "Target",
    "name" : "t2",
    "shape" : [ 90, 144 ],
    "base" : "data/2",
    "beginDate" : "200801",
    "endDate" : "200812",
    "zorder" : [ "EMEP","MICS_Asia","EDGAR"],
    "defaultEmission" : "EDGAR",
    "timeScale" : "month",
    "species" : [ "NH3" ],
    "clip" : { "bottom":0, "left": 0, "width":144, "height":90 }, 
    "pathTemplate" : "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>"
  }
]
