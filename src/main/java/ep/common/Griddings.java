package ep.common;


import java.io.IOException;
import java.util.Map;

import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

public class Griddings {


  public static Gridding read(NetcdfFile ncfile, String variable) throws IOException, InvalidRangeException {
    Array array = ncfile.readSection(variable);

    Gridding g = new Gridding();
    g.setSurface(array);
    g.globalize();
    return g;
  }


  public static Gridding empty(Class typeClass, int lat, int lon) {
    int shape[] = new int[] {lat, lon};
    Array surface = Array.factory(typeClass, shape);
    Gridding g = new Gridding();

    g.setSurface(surface);
    g.globalize();
    return g;
  }


  public static void remap(Gridding source, Gridding dest) {
    // Very simple & ugly remap function.
    // Only work on Globe surface combination
    Array srcSur = source.getSurface();
    Array destSur = dest.getSurface();

    int srcShape[] = srcSur.getShape();
    int destShape[] = destSur.getShape();

    int srcResLat = srcShape[0];
    int srcResLon = srcShape[1];

    int destResLat = destShape[0];
    int destResLon = destShape[1];

    double srcDeltaLat = 180.0 / srcResLat;
    double srcDeltaLon = 360.0 / srcResLon;

    double destDeltaLat = 180.0 / destResLat;
    double destDeltaLon = 360.0 / destResLon;

    double srcBaseLat = - srcDeltaLat * 0.5;
    double srcBaseLon;

    double destLimitLat = destDeltaLat;
    double destLimitLon;

    float[] srcArr = (float[])srcSur.getStorage();
    float[] destArr = (float[])destSur.getStorage();

    int os = -1;
    int od = 0;

    for (int j = 0; j < srcResLat; ++j) {
      srcBaseLat += srcDeltaLat;
      if (srcBaseLat > destLimitLat) {
        od += destResLon;
        destLimitLat += destDeltaLat;
      }


      // reset scan line
      srcBaseLon = - srcDeltaLon * 0.5;
      destLimitLon = destDeltaLon;
      int od2 = od;

      for (int i = 0; i < srcResLon; ++i) {
        os += 1;
        srcBaseLon += srcDeltaLon;

        if (srcBaseLon > destLimitLon) {
          destLimitLon += destDeltaLon;
          od2 += 1;
        }
        destArr[od2] += srcArr[os];
      }
    }
  }


  public static NetcdfFileWriter createNetCDF(String path) throws IOException {
    NetcdfFileWriter ncwriter = NetcdfFileWriter.createNew(
      NetcdfFileWriter.Version.netcdf3, path);
    return ncwriter;
  }


  public static void setAttributes(NetcdfFileWriter ncwriter, Map<String, Object> attrs) {
    for (Map.Entry e: attrs.entrySet()) {
      Attribute attr = new Attribute(e.getKey().toString(), e.getValue().toString());
      ncwriter.addGroupAttribute(null, attr);
    }
  }


  public static void addGlobalAttribute(NetcdfFileWriter ncwriter, String name, String value) {
    Attribute attr = new Attribute(name, value);
    ncwriter.addGroupAttribute(null, attr);
  }


  public static final String CONTACT = "qzhang@tsinghua.edu.cn";
  public static final String CONVENTIONS = "CF-1.0";


  public static void standardizeCF(NetcdfFileWriter ncwriter, int shape[]) throws IOException, InvalidRangeException {

    addGlobalAttribute(ncwriter, "Convensions", CONVENTIONS);
    addGlobalAttribute(ncwriter, "contact", CONTACT);

    // int shape[] = g.getShape();

    int resLat = shape[0];
    int resLon = shape[1];

    // add dimensions
    Dimension latDim = ncwriter.addDimension(null, "lat", resLat);
    Dimension lonDim = ncwriter.addDimension(null, "lon", resLon);

    // add dimension variables
    Variable latVar = ncwriter.addVariable(null, "lat", DataType.FLOAT, latDim.getName());
    latVar.addAttribute(new Attribute("standard_name", "latitude"));
    latVar.addAttribute(new Attribute("long_name", "latitude"));
    latVar.addAttribute(new Attribute("units", "degrees_north"));
    latVar.addAttribute(new Attribute("comment", "center_of_cell"));

    Variable lonVar = ncwriter.addVariable(null, "lon", DataType.FLOAT, lonDim.getName());
    lonVar.addAttribute(new Attribute("standard_name", "longitude"));
    lonVar.addAttribute(new Attribute("long_name", "longitude"));
    lonVar.addAttribute(new Attribute("units", "degrees_east"));
    lonVar.addAttribute(new Attribute("comment", "center_of_cell"));
  }


  public static void writeDimensionVariable(NetcdfFileWriter ncwriter, int shape[]) throws IOException, InvalidRangeException {
    int resLat = shape[0];
    int resLon = shape[1];

    Variable latVar = ncwriter.findVariable("lat");
    Variable lonVar = ncwriter.findVariable("lon");

    Array latArr = Array.factory(float.class, new int[] {resLat});
    float[] latArr2 = (float[])latArr.getStorage();
    float cellLat = 180f / resLat;
    float baseLat = -90f +  cellLat / 2;
    for (int i = 0; i < resLat; ++i) {
      latArr2[i] = baseLat;
      baseLat += cellLat;
    }
    ncwriter.write(latVar, latArr);


    Array lonArr = Array.factory(float.class, new int[] {resLon});
    float[] lonArr2 = (float[])lonArr.getStorage();
    float cellLon = 360f / resLon;
    float baseLon = 0f +  cellLon / 2;
    for (int i = 0; i < resLon; ++i) {
      lonArr2[i] = baseLon;
      baseLon += cellLon;
    }
    ncwriter.write(lonVar, lonArr);
  }
}
