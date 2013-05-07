[
{  "type" : "Target",
    "name" : "t2",
    "shape" : [360,540],
    "base" : "data/2",
    "beginDate" : "200801",
    "endDate" : "200812",
    "zorder" : [ "EMEP","MICS_Asia","EDGAR" ],
    "defaultEmission": "EDGAR",
    "timeScale" : "month",
    "species" : [ "SO2","NOx","CO","ACET","ALD2","ALK4","C2H6","C3H8","CH2O","MEK","PRPE"  ],
    "clip" : { "bottom":158, "left": 375, "width":121, "height":133 }, 
    "pathTemplate" : "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>"
  }
]
