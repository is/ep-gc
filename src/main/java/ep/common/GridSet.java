package ep.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

public class GridSet {
  public static final String CONTACT = "qiangzhang@tsinghua.edu.cn";
  public static final String CONVENTIONS = "CF-1.6";

  String path;
  int shape[];
  Rect clip;

  NetcdfFileWriter writer;

  Map<String, Dimension> dimensions;
  Map<String, Variable> variables;
  Map<String, Array> variableDatas;
  List<String> variableNames;


  public GridSet(String path, int shape[], Rect clip) {
    this.path = path;
    this.shape = shape;
    this.clip = clip;

    dimensions = new HashMap<>();
    variableDatas = new HashMap<>();
    variableNames = new LinkedList<>();
    variables = new HashMap<>();
  }


  public GridSet(String path, int shape[]) {
    this(path, shape, null);
  }


  public void open() throws IOException {
    writer = NetcdfFileWriter.createNew(
      NetcdfFileWriter.Version.netcdf3, path);

    addAttribute("Conventions", CONVENTIONS);
    addAttribute("contact", CONTACT);

    addDimessions();
  }


  public void addGridding(String name, Map<String, String> attr, Grid g) {
    addArray(name, attr, g.getSurface());
  }


  public void addArray(String name, Map<String, String> attr, Array g) {
    Variable var = writer.addVariable(null, name, DataType.FLOAT, "lat lon");
    if (attr != null) {
      for (Map.Entry e: attr.entrySet()) {
        // TODO handle number and other datatype attributes.
        var.addAttribute(new Attribute(e.getKey().toString(), e.getValue().toString()));
      }
    }
    addVariable(var, g);
  }


  void addDimessions() {

    int resLat;
    int resLon;

    if (clip == null) {
      resLat = shape[0];
      resLon = shape[1];
    } else {
      resLat = clip.height;
      resLon = clip.width;
    }

    // add dimensions

    Dimension latDim = writer.addDimension(null, "lat", resLat);
    Dimension lonDim = writer.addDimension(null, "lon", resLon);

    dimensions.put("lat", latDim);
    dimensions.put("lon", lonDim);

    Variable latVar = writer.addVariable(null, "lat", DataType.FLOAT, latDim.getName());
    latVar.addAttribute(new Attribute("standard_name", "latitude"));
    latVar.addAttribute(new Attribute("long_name", "latitude"));
    latVar.addAttribute(new Attribute("units", "degrees_north"));
    latVar.addAttribute(new Attribute("comment", "center_of_cell"));
    latVar.addAttribute(new Attribute("axis", "Y"));


    Variable lonVar = writer.addVariable(null, "lon", DataType.FLOAT, lonDim.getName());
    lonVar.addAttribute(new Attribute("standard_name", "longitude"));
    lonVar.addAttribute(new Attribute("long_name", "longitude"));
    lonVar.addAttribute(new Attribute("units", "degrees_east"));
    lonVar.addAttribute(new Attribute("comment", "center_of_cell"));
    lonVar.addAttribute(new Attribute("axis", "X"));

    float cellLon = 360f / resLon;
    float cellLat = 180f / resLat;

    float baseLon = 0f +  cellLon / 2;
    float baseLat = -90f +  cellLat / 2;

    if (clip != null) {
      baseLat += cellLat * clip.bottom;
      baseLon += cellLon * clip.left;
    }

    Array latArr = Array.factory(float.class, new int[] {resLat});
    float[] latArr2 = (float[])latArr.getStorage();

    for (int i = 0; i < resLat; ++i) {
      latArr2[i] = baseLat;
      baseLat += cellLat;
    }
    addVariable(latVar, latArr);

    Array lonArr = Array.factory(float.class, new int[] {resLon});
    float[] lonArr2 = (float[])lonArr.getStorage();

    for (int i = 0; i < resLon; ++i) {
      lonArr2[i] = baseLon;
      baseLon += cellLon;
    }
    addVariable(lonVar, lonArr);
  }


  void addVariable(Variable var, Array data) {
    String name = var.getShortName();
    variableNames.add(name);
    variables.put(name, var);
    variableDatas.put(name, data);
  }


  void addAttribute(String name, String val) {
    writer.addGroupAttribute(null, new Attribute(name, val));
  }


  public void flushOut() throws IOException, InvalidRangeException {
    writer.create();
    for (String name: variableNames) {
      writer.write(variables.get(name), variableDatas.get(name));
    }
  }

  public void close() throws IOException {
    writer.close();
  }

  public void flushAndClose() throws IOException, InvalidRangeException {
    flushOut();
    close();
  }
}
