
import static java.lang.String.format;

public final class Debug {
  private Debug(){}

  public static String print2DTable(double[][] vv, boolean columnXrowY, String[] rowItem, String[] colItem, int fractionSize, int minCellSize)
  {
    if (vv == null || vv.length == 0) return "";
    if (rowItem == null) rowItem = new String[]{};
    if (colItem == null) colItem = new String[]{};
    int maxDecimalSize = 1;
    for (int rowY = 0; rowY < vv.length; ++rowY)
      for (int columnX = 0; columnX < vv[rowY].length; ++columnX)
        maxDecimalSize = Math.max(maxDecimalSize,  format("%.0f", vv[rowY][columnX]).length());
    fractionSize = Math.max(fractionSize, 0);
    int cellSize = minCellSize;
    cellSize = Math.max(cellSize, Math.max((maxDecimalSize+fractionSize+(fractionSize>0?1:0)), 4));
    StringBuilder sb = new StringBuilder();
    if (columnXrowY)//[columnX][rowY]
    {
      int maxColumnSize = vv[0].length;//can vary
      int maxRowSize = vv.length;//should be fixed
      for (int columnX = 0; columnX < maxRowSize; ++columnX)
        maxColumnSize = Math.max(maxColumnSize, vv[columnX].length);
      for (int rowY = -1; rowY < maxColumnSize; ++rowY)//for each rowY (iterate vertically)
      {
        if (rowY == -1)
        {
          sb.append(format(("%"+(cellSize+2)+"s")," "));
          int rowSize = colItem.length;
          for (int columnX = 0; columnX < maxRowSize; ++columnX)//for each columnX (iterate horizontally)
          {
            if (columnX < rowSize) sb.append(format(("%"+cellSize+"s"), colItem[columnX].substring(0, Math.min(colItem[columnX].length(), cellSize))));
            else sb.append(format(("%s%0"+(cellSize-4)+"d"), "Col#", columnX));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        else
        {
          if (rowY < rowItem.length) sb.append(format(("%"+cellSize+"s: "), rowItem[rowY].substring(0, Math.min(rowItem[rowY].length(), cellSize))));
          else sb.append(format(("%s%0"+(cellSize-4)+"d: "), "Row#", rowY));
          for (int columnX = 0; columnX < maxRowSize; ++columnX)//for each columnX (iterate horizontally)
          {
            int columnSize = vv[columnX].length;
            if (rowY < columnSize) sb.append(format(("%"+cellSize+"."+fractionSize+"f"), vv[columnX][rowY]));
            else sb.append(format(("%"+cellSize+"s"), "NA"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        sb.append("\n");
      }
    }
    else//[rowY][columnX]
    {
      int maxColumnSize = vv.length;//should be fixed
      int maxRowSize = vv[0].length;//can vary
      for (int rowY = 0; rowY < maxColumnSize; ++rowY)
        maxRowSize = Math.max(maxRowSize, vv[rowY].length);
      for (int rowY = -1; rowY < maxColumnSize; ++rowY)
      {
        if (rowY == -1)
        {
          sb.append(format(("%"+(cellSize+2)+"s")," "));
          int rowSize = colItem.length;
          for (int columnX = 0; columnX < maxRowSize; ++columnX)
          {
            if (columnX < rowSize) sb.append(format(("%"+cellSize+"s"), colItem[columnX].substring(0, Math.min(colItem[columnX].length(), cellSize))));
            else sb.append(format(("%s%0"+(cellSize-4)+"d"), "Col#", columnX));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        else
        {
          int rowSize = vv[rowY].length;
          if (rowY < rowItem.length) sb.append(format(("%"+cellSize+"s: "), rowItem[rowY].substring(0, Math.min(rowItem[rowY].length(), cellSize))));
          else sb.append(format(("%s%0"+(cellSize-4)+"d: "), "Row#", rowY));
          for (int columnX = 0; columnX < maxRowSize; ++columnX)
          {
            if (columnX < rowSize) sb.append(format(("%"+cellSize+"."+fractionSize+"f"), vv[rowY][columnX]));
            else sb.append(format(("%"+cellSize+"s"), "NA"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }

}
