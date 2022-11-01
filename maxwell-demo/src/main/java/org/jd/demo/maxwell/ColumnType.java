package org.jd.demo.maxwell;

/**
 * @Auther jd
 */
public enum ColumnType {

  VARCHAR("varchar", false),
  TINYTEXT("tinytext", false),
  BIGINT("bigint", false),
  CHAR("char", false),
  DATE("date", false),
  DATETIME("datetime", false),
  DECIMAL("decimal", false),
  DOUBLE("double", false),
  ENUM("enum", false),
  FLOAT("float", false),
  GEOMETRY("geometry", false),
  GEOMETRYCOLLECTION("geometrycollection", false),
  INT("int", false),
  INTEGER("integer", false),
  JSON("json", false),
  LINESTRING("linestring", false),
  LONGTEXT("longtext", false),
  MEDIUMINT("mediumint", false),
  MEDIUMTEXT("mediumtext", false),
  MULTILINESTRING("multilinestring", false),
  MULTIPOINT("multipoint", false),
  MULTIPOLYGON("multipolygon", false),
  NUMERIC("numeric", false),
  POINT("point", false),
  POLYGON("polygon", false),
  REAL("real", false),
  SET("set", false),
  SMALLINT("smallint", false),
  TEXT("text", false),
  TIME("time", false),
  TIMESTAMP("timestamp", false),
  TINYINT("tinyint", false),
  YEAR("year", false),
  BIT("bit", true),
  BINARY("binary", true),
  VARBINARY("varbinary", true),
  TINYBLOB("tinyblob", true),
  BLOB("blob", true),
  MEDIUMBLOB("mediumblob", true),
  LONGBLOB("longblob", true),
  ;

  private String type;

  private boolean isBinary;

  ColumnType(String type, boolean isBinary) {
    this.type = type;
    this.isBinary = isBinary;
  }

  public String getType() {
    return type;
  }

  public boolean isBinary() {
    return isBinary;
  }

}
