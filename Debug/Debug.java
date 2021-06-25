
import static java.lang.String.format;

public final class Debug {
  private Debug(){}

  public static String print2DTable(double[][] vv, boolean columnXrowY, String[] rowItem, String[] colItem, int fractionSize, int uniformMinCellSize4)
  {
    if (vv == null || vv.length == 0) return "";
    if (rowItem == null) rowItem = new String[]{};
    if (colItem == null) colItem = new String[]{};
    fractionSize = Math.max(fractionSize, 0);
    
    int cellSize = 0;
    if (uniformMinCellSize4 >= 4)
    {
      int maxDecimalSize = 1;
      for (int rowY = 0; rowY < vv.length; ++rowY)
        for (int columnX = 0; columnX < vv[rowY].length; ++columnX)
          maxDecimalSize = Math.max(maxDecimalSize,  format("%.0f", vv[rowY][columnX]).length());
      cellSize = Math.max(uniformMinCellSize4, (maxDecimalSize+fractionSize+(fractionSize>0?1:0)));
    }

    StringBuilder sb = new StringBuilder();
    if (columnXrowY)//[columnX][rowY]
    {
      int maxColumnSize = vv[0].length;//can vary
      int maxRowSize = vv.length;//should be fixed
      for (int columnX = 0; columnX < maxRowSize; ++columnX)
        maxColumnSize = Math.max(maxColumnSize, vv[columnX].length);

      int[] colCellSizeArr = new int[maxRowSize+1];
      if (cellSize != 0) Arrays.fill(colCellSizeArr, cellSize);
      else
      {
        colCellSizeArr[0] = 4;
        for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          if (rowY < rowItem.length) colCellSizeArr[0] = Math.max(colCellSizeArr[0], rowItem[rowY].length());
          else colCellSizeArr[0] = Math.max(colCellSizeArr[0], format("%d", rowY+1).length()+4);
        for (int colX = 0; colX < maxRowSize; ++colX)
        {
          colCellSizeArr[colX+1] = 4;
          if (colX < colItem.length) colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], colItem[colX].length());
          else colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], format("%d", colX+1).length()+4);
          for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          {
            int decimalSize = (rowY < vv[colX].length) ? format("%.0f", vv[colX][rowY]).length() : 1;
            colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], (decimalSize+fractionSize+(fractionSize>0?1:0)));
          }
        }
      }

      for (int rowY = -1; rowY < maxColumnSize; ++rowY)//for each rowY (iterate vertically)
      {
        if (rowY == -1)
        {
          sb.append(format(("%"+(colCellSizeArr[0]+2)+"s")," "));
          int rowSize = colItem.length;
          for (int columnX = 0; columnX < maxRowSize; ++columnX)//for each columnX (iterate horizontally)
          {
            if (columnX < rowSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), colItem[columnX].substring(0, Math.min(colItem[columnX].length(), colCellSizeArr[columnX+1]))));
            else if (colCellSizeArr[columnX+1]-4 >= format("%d", columnX+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[columnX+1]-4)+"d"), "Col#", columnX+1));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "Col#"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        else
        {
          if (rowY < rowItem.length) sb.append(format(("%"+colCellSizeArr[0]+"s: "), rowItem[rowY].substring(0, Math.min(rowItem[rowY].length(), colCellSizeArr[0]))));
          else if (colCellSizeArr[0]-4 >= format("%d", rowY+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[0]-4)+"d: "), "Row#", rowY+1));
          else sb.append(format(("%"+colCellSizeArr[0]+"s: "), "Row#"));
          for (int columnX = 0; columnX < maxRowSize; ++columnX)//for each columnX (iterate horizontally)
          {
            int columnSize = vv[columnX].length;
            if (rowY < columnSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"."+fractionSize+"f"), vv[columnX][rowY]));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "NA"));
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
      
      int[] colCellSizeArr = new int[maxRowSize+1];
      if (cellSize != 0) Arrays.fill(colCellSizeArr, cellSize);
      else
      {
        colCellSizeArr[0] = 4;
        for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          if (rowY < rowItem.length) colCellSizeArr[0] = Math.max(colCellSizeArr[0], rowItem[rowY].length());
          else colCellSizeArr[0] = Math.max(colCellSizeArr[0], format("%d", rowY+1).length()+4);
        for (int colX = 0; colX < maxRowSize; ++colX)
        {
          colCellSizeArr[colX+1] = 4;
          if (colX < colItem.length) colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], colItem[colX].length());
          else colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], format("%d", colX+1).length()+4);
          for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          {
            int decimalSize = (colX < vv[rowY].length) ? format("%.0f", vv[rowY][colX]).length() : 1;
            colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], (decimalSize+fractionSize+(fractionSize>0?1:0)));
          }
        }
      }
      
      for (int rowY = -1; rowY < maxColumnSize; ++rowY)
      {
        if (rowY == -1)
        {
          sb.append(format(("%"+(colCellSizeArr[0]+2)+"s")," "));
          int rowSize = colItem.length;
          for (int columnX = 0; columnX < maxRowSize; ++columnX)
          {
            if (columnX < rowSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), colItem[columnX].substring(0, Math.min(colItem[columnX].length(), colCellSizeArr[columnX+1]))));
            else if (colCellSizeArr[columnX+1]-4 >= format("%d", columnX+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[columnX+1]-4)+"d"), "Col#", columnX+1));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "Col#"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        else
        {
          int rowSize = vv[rowY].length;
          if (rowY < rowItem.length) sb.append(format(("%"+colCellSizeArr[0]+"s: "), rowItem[rowY].substring(0, Math.min(rowItem[rowY].length(), colCellSizeArr[0]))));
          else if (colCellSizeArr[0]-4 >= format("%d", rowY+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[0]-4)+"d: "), "Row#", rowY+1));
          else sb.append(format(("%"+colCellSizeArr[0]+"s: "), "Row#"));
          for (int columnX = 0; columnX < maxRowSize; ++columnX)
          {
            if (columnX < rowSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"."+fractionSize+"f"), vv[rowY][columnX]));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "NA"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  public static String print2DTable(String[][] vv, boolean columnXrowY, String[] rowItem, String[] colItem, int uniformMinCellSize4)
  {
    if (vv == null || vv.length == 0) return "";
    if (rowItem == null) rowItem = new String[]{};
    if (colItem == null) colItem = new String[]{};
    
    int cellSize = 0;
    if (uniformMinCellSize4 >= 4)
    {
      int maxCellSize = 1;
      for (int rowY = 0; rowY < vv.length; ++rowY)
        for (int columnX = 0; columnX < vv[rowY].length; ++columnX)
          maxCellSize = Math.max(maxCellSize,  format("%s", vv[rowY][columnX]).length());
      cellSize = Math.max(uniformMinCellSize4, maxCellSize);
    }

    StringBuilder sb = new StringBuilder();
    if (columnXrowY)//[columnX][rowY]
    {
      int maxColumnSize = vv[0].length;//can vary
      int maxRowSize = vv.length;//should be fixed
      for (int columnX = 0; columnX < maxRowSize; ++columnX)
        maxColumnSize = Math.max(maxColumnSize, vv[columnX].length);

      int[] colCellSizeArr = new int[maxRowSize+1];
      if (cellSize != 0) Arrays.fill(colCellSizeArr, cellSize);
      else
      {
        colCellSizeArr[0] = 4;
        for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          if (rowY < rowItem.length) colCellSizeArr[0] = Math.max(colCellSizeArr[0], rowItem[rowY].length());
          else colCellSizeArr[0] = Math.max(colCellSizeArr[0], format("%d", rowY+1).length()+4);
        for (int colX = 0; colX < maxRowSize; ++colX)
        {
          colCellSizeArr[colX+1] = 4;
          if (colX < colItem.length) colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], colItem[colX].length());
          else colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], format("%d", colX+1).length()+4);
          for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          {
            int curCellSize = (rowY < vv[colX].length) ? format("%s", vv[colX][rowY]).length() : 1;
            colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], curCellSize);
          }
        }
      }

      for (int rowY = -1; rowY < maxColumnSize; ++rowY)//for each rowY (iterate vertically)
      {
        if (rowY == -1)
        {
          sb.append(format(("%"+(colCellSizeArr[0]+2)+"s")," "));
          int rowSize = colItem.length;
          for (int columnX = 0; columnX < maxRowSize; ++columnX)//for each columnX (iterate horizontally)
          {
            if (columnX < rowSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), colItem[columnX].substring(0, Math.min(colItem[columnX].length(), colCellSizeArr[columnX+1]))));
            else if (colCellSizeArr[columnX+1]-4 >= format("%d", columnX+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[columnX+1]-4)+"d"), "Col#", columnX+1));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "Col#"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        else
        {
          if (rowY < rowItem.length) sb.append(format(("%"+colCellSizeArr[0]+"s: "), rowItem[rowY].substring(0, Math.min(rowItem[rowY].length(), colCellSizeArr[0]))));
          else if (colCellSizeArr[0]-4 >= format("%d", rowY+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[0]-4)+"d: "), "Row#", rowY+1));
          else sb.append(format(("%"+colCellSizeArr[0]+"s: "), "Row#"));
          for (int columnX = 0; columnX < maxRowSize; ++columnX)//for each columnX (iterate horizontally)
          {
            int columnSize = vv[columnX].length;
            if (rowY < columnSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), vv[columnX][rowY].substring(0, Math.min(vv[columnX][rowY].length(), colCellSizeArr[columnX+1]))));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "NA"));
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
      
      int[] colCellSizeArr = new int[maxRowSize+1];
      if (cellSize != 0) Arrays.fill(colCellSizeArr, cellSize);
      else
      {
        colCellSizeArr[0] = 4;
        for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          if (rowY < rowItem.length) colCellSizeArr[0] = Math.max(colCellSizeArr[0], rowItem[rowY].length());
          else colCellSizeArr[0] = Math.max(colCellSizeArr[0], format("%d", rowY+1).length()+4);
        for (int colX = 0; colX < maxRowSize; ++colX)
        {
          colCellSizeArr[colX+1] = 4;
          if (colX < colItem.length) colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], colItem[colX].length());
          else colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], format("%d", colX+1).length()+4);
          for (int rowY = 0; rowY < maxColumnSize; ++rowY)
          {
            int curCellSize = (colX < vv[rowY].length) ? format("%s", vv[rowY][colX]).length() : 1;
            colCellSizeArr[colX+1] = Math.max(colCellSizeArr[colX+1], curCellSize);
          }
        }
      }
      
      for (int rowY = -1; rowY < maxColumnSize; ++rowY)
      {
        if (rowY == -1)
        {
          sb.append(format(("%"+(colCellSizeArr[0]+2)+"s")," "));
          int rowSize = colItem.length;
          for (int columnX = 0; columnX < maxRowSize; ++columnX)
          {
            if (columnX < rowSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), colItem[columnX].substring(0, Math.min(colItem[columnX].length(), colCellSizeArr[columnX+1]))));
            else if (colCellSizeArr[columnX+1]-4 >= format("%d", columnX+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[columnX+1]-4)+"d"), "Col#", columnX+1));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "Col#"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        else
        {
          int rowSize = vv[rowY].length;
          if (rowY < rowItem.length) sb.append(format(("%"+colCellSizeArr[0]+"s: "), rowItem[rowY].substring(0, Math.min(rowItem[rowY].length(), colCellSizeArr[0]))));
          else if (colCellSizeArr[0]-4 >= format("%d", rowY+1).length()) sb.append(format(("%s%0"+(colCellSizeArr[0]-4)+"d: "), "Row#", rowY+1));
          else sb.append(format(("%"+colCellSizeArr[0]+"s: "), "Row#"));
          for (int columnX = 0; columnX < maxRowSize; ++columnX)
          {
            if (columnX < rowSize) sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), vv[rowY][columnX].substring(0, Math.min(vv[rowY][columnX].length(), colCellSizeArr[columnX+1]))));
            else sb.append(format(("%"+colCellSizeArr[columnX+1]+"s"), "NA"));
            if (columnX != maxRowSize - 1) sb.append(", ");
          }
        }
        sb.append("\n");
      }
    }
    return sb.toString();
  }


}
