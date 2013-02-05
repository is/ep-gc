package ep.common;

public class ESID {
  public String name;
  public String date;
  public String species;
  public String sector;

  public ESID() {}

  public ESID(ESID e) {
    name = e.name;
    date = e.date;
    species = e.species;
    sector = e.sector;
  }


  public ESID(String name, String date, String species, String sector) {
    this.name = name;
    this.date = date;
    this.sector = sector;
    this.species = species;
  }

  @SuppressWarnings("unused")
  public String getSpeciesLower() {
    return this.species.toLowerCase();
  }

  @SuppressWarnings("unused")
  public String getSectorLower() {
    return this.sector.toLowerCase();
  }


  @SuppressWarnings("unused")
  public String getNameLower() {
    return this.name.toLowerCase();
  }


  @SuppressWarnings("unused")
  public String getNameUpper() {
    return this.name.toUpperCase();
  }


  @SuppressWarnings("unused")
  public String getYear() {
    if (date.length() == 4)
      return date;

    return date.substring(0, 4);
  }


  @SuppressWarnings("unused")
  public String getMonth() {
    if (date.length() == 4)
      return null;
    return date.substring(4, 6);
  }
}
