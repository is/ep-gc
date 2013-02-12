{
  "root" : "data",
  "conf" : "conf",
  "emissions" : [ "EDGAR", "EMEP" ],
  "species" : [ "PM10", "SO2", "NH3" ],
  "sectors" : [ "Agriculture", "Transporation", "Residential", "Power", "Industry" ],
  "esconf" : {
    "EDGAR" : {
      "type" : "fs",
      "basePath" : "data/in",
      "dateStep" : "yearly",
      "name" : "EDGAR",
      "pathTemplate" : "<cf.basePath>/<es.name>/<es.species>/v42_<es.species>_<es.date>_IPCC_<es.sector>.0.1x0.1.nc|||emi_<es.speciesLower>"
    },
    "EMEP" : {
      "type" : "fs",
      "basePath" : "data/in",
      "dateStep" : "yearly",
      "name" : "EMEP",
      "pathTemplate" : "<cf.basePath>/<es.name>/EMEP_CO_SOx_NH3_NOx_NMVOC_<es.date>_0.5x0.5.nc|||<es.species>_<es.sector>",
      "speciesAliases" : {
        "SO2" : "SOx"
      }
    }
  },
  "beginYear" : 1970,
  "endYear" : 2008,
  "yearmap" : {
    "EDGAR,1970" : "1970",
    "EDGAR,1971" : "1971",
    "EDGAR,1972" : "1972",
    "EDGAR,1973" : "1973",
    "EDGAR,1974" : "1974",
    "EDGAR,1975" : "1975",
    "EDGAR,1976" : "1976",
    "EDGAR,1977" : "1977",
    "EDGAR,1978" : "1978",
    "EDGAR,1979" : "1979",
    "EDGAR,1980" : "1980",
    "EDGAR,1981" : "1981",
    "EDGAR,1982" : "1982",
    "EDGAR,1983" : "1983",
    "EDGAR,1984" : "1984",
    "EDGAR,1985" : "1985",
    "EDGAR,1986" : "1986",
    "EDGAR,1987" : "1987",
    "EDGAR,1988" : "1988",
    "EDGAR,1989" : "1989",
    "EDGAR,1990" : "1990",
    "EDGAR,1991" : "1991",
    "EDGAR,1992" : "1992",
    "EDGAR,1993" : "1993",
    "EDGAR,1994" : "1994",
    "EDGAR,1995" : "1995",
    "EDGAR,1996" : "1996",
    "EDGAR,1997" : "1997",
    "EDGAR,1998" : "1998",
    "EDGAR,1999" : "1999",
    "EDGAR,2000" : "2000",
    "EDGAR,2001" : "2001",
    "EDGAR,2002" : "2002",
    "EDGAR,2003" : "2003",
    "EDGAR,2004" : "2004",
    "EDGAR,2005" : "2005",
    "EDGAR,2006" : "2006",
    "EDGAR,2007" : "2007",
    "EDGAR,2008" : "2008",
    "EMEP,1970" : "1980",
    "EMEP,1971" : "1980",
    "EMEP,1972" : "1980",
    "EMEP,1973" : "1980",
    "EMEP,1974" : "1980",
    "EMEP,1975" : "1980",
    "EMEP,1976" : "1980",
    "EMEP,1977" : "1980",
    "EMEP,1978" : "1980",
    "EMEP,1979" : "1980",
    "EMEP,1980" : "1980",
    "EMEP,1981" : "1981",
    "EMEP,1982" : "1982",
    "EMEP,1983" : "1983",
    "EMEP,1984" : "1984",
    "EMEP,1985" : "1985",
    "EMEP,1986" : "1986",
    "EMEP,1987" : "1987",
    "EMEP,1988" : "1988",
    "EMEP,1989" : "1989",
    "EMEP,1990" : "1990",
    "EMEP,1991" : "1991",
    "EMEP,1992" : "1992",
    "EMEP,1993" : "1993",
    "EMEP,1994" : "1994",
    "EMEP,1995" : "1995",
    "EMEP,1996" : "1996",
    "EMEP,1997" : "1997",
    "EMEP,1998" : "1998",
    "EMEP,1999" : "1999",
    "EMEP,2000" : "2000",
    "EMEP,2001" : "2001",
    "EMEP,2002" : "2002",
    "EMEP,2003" : "2003",
    "EMEP,2004" : "2004",
    "EMEP,2005" : "2005",
    "EMEP,2006" : "2006",
    "EMEP,2007" : "2007",
    "EMEP,2008" : "2007"
  },
  "defaultEmission" : "EDGAR",
  "sectorMapper" : {
    "NH3,Agriculture" : {
      "sectors" : {
        "EDGAR" : [ "4B", "4C_4D" ],
        "EMEP" : [ "S10" ],
        "MEIC" : [ "M5" ]
      }
    },
    "NH3,Industry" : {
      "sectors" : {
        "EDGAR" : [ "1A2", "1A1b_c", "2A", "2B" ],
        "EMEP" : [ "S3", "S4", "S5", "S6" ],
        "MEIC" : [ "M2" ]
      }
    },
    "NH3,Power" : {
      "sectors" : {
        "EDGAR" : [ "1A1a_6" ],
        "EMEP" : [ "S1" ],
        "MEIC" : [ "M1" ]
      }
    },
    "NH3,Residential" : {
      "sectors" : {
        "EDGAR" : [ "1A4" ],
        "EMEP" : [ "S2" ],
        "MEIC" : [ "M3" ]
      }
    },
    "NH3,Transporation" : {
      "sectors" : {
        "EDGAR" : [ "1A3" ],
        "EMEP" : [ "S7", "S8" ],
        "MEIC" : [ "M4" ]
      }
    },
    "PM10,Industry" : {
      "sectors" : {
        "EDGAR" : [ "1A2", "1B2a_c_1A1b_c", "2_3" ],
        "EMEP" : [ "S3", "S4", "S5", "S6" ],
        "MEIC" : [ "M2" ]
      }
    },
    "PM10,Power" : {
      "sectors" : {
        "EDGAR" : [ "1A1a" ],
        "EMEP" : [ "S1" ],
        "MEIC" : [ "M1" ]
      }
    },
    "PM10,Residential" : {
      "sectors" : {
        "EDGAR" : [ "1A4" ],
        "EMEP" : [ "S2" ],
        "MEIC" : [ "M3" ]
      }
    },
    "PM10,Transporation" : {
      "sectors" : {
        "EDGAR" : [ "1A3a_c_d_e", "1A3b" ],
        "EMEP" : [ "S7", "S8" ],
        "MEIC" : [ "M4" ]
      }
    },
    "SO2,Industry" : {
      "sectors" : {
        "EDGAR" : [ "1A2", "2C", "1B1_1B2_1A1b_c", "2B_2D" ],
        "EMEP" : [ "S3", "S4", "S5", "S6" ],
        "MEIC" : [ "M2" ]
      }
    },
    "SO2,Power" : {
      "sectors" : {
        "EDGAR" : [ "1A1a_6C" ],
        "EMEP" : [ "S1" ],
        "MEIC" : [ "M1" ]
      }
    },
    "SO2,Residential" : {
      "sectors" : {
        "EDGAR" : [ "1A4" ],
        "EMEP" : [ "S2" ],
        "MEIC" : [ "M3" ]
      }
    },
    "SO2,Transporation" : {
      "sectors" : {
        "EDGAR" : [ "1A3a_c_d_e", "1A3b" ],
        "EMEP" : [ "S7", "S8" ],
        "MEIC" : [ "M4" ]
      }
    }
  },
  "targets" : [ {
    "name" : "middle",
    "shape" : [ 900, 1800 ],
    "base" : "data/o0",
    "beginDate" : "200301",
    "endDate" : "200412",
    "enabled" : [ "EDGAR" ],
    "dateStep" : "monthly",
    "pathTemplate" : "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>"
  } ]
}