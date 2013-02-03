package ep.common;

public class ESID {
  public String name;
  public String date;
  public String species;
  public String sector;

  public ESID() {}

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
}
