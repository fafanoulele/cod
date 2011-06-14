import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: fbousquet
 * Date: 11/06/11
 * Time: 18:31
 */
public class CodMain
{

  public static void main(String[] args)
  {

    CodMain codMain = new CodMain();
    PrintWriter writer = null;

    try
    {
      List<int[]> vectorsInput = codMain.readInput(new File(args[0]));
      OutputStream outputStream = args.length > 1 ? new FileOutputStream(new File(args[1])) : System.out;
      writer = new PrintWriter(outputStream);
      for(int[] vector :vectorsInput)
      {
        if(codMain.isBalanceable(vector))
        {
          codMain.writeFile(codMain.balance(vector), writer);
        }
        else
        {
          writer.println(-1);
        }
        writer.println();
      }

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {

      if (writer != null)
      {
        writer.close();
      }
    }


  }


  public void writeFile(List<int[]> iterations, PrintWriter writer)
  {

    int line = 0;
    writer.println(iterations.size() -1);
    for (int[] result : iterations)
    {
      writer.println(new StringBuilder().append(line).append(" : ").append(toStringResult(result)));
      line++;
    }
    
  }

  public String toStringResult(int[] iteration)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    int count=0;
    for(int it : iteration)
    {
      if(count != 0)
      {
        sb.append(", ");
      }
      sb.append(it);
      count++;
    }
    sb.append(")");

    return sb.toString();
  }

  public List<int[]> balance(int[] vector)
  {
    List<int[]> iterations = new ArrayList<int[]>();

    int balancing = computeBalancingValue(vector);

    iterations.add(vector);

    while (!isBalancing(vector))
    {
      vector = iteration(vector, balancing);

      iterations.add(vector);
    }

    return iterations;
  }

  public int[] iteration(int[] vector, int balancing)
  {
    int[] result = new int[vector.length];
    System.arraycopy(vector, 0, result, 0, vector.length);

    for (int i = 0; i < vector.length; i++)
    {
      if (vector[i] > 0)
      {
        int rightSum = i <= vector.length - 1 ? sums(vector, i + 1, vector.length) : -1;
        int leftSum = i > 0 ? sums(vector, 0, i - 1) : -1;

        //Choice left or right transfer
        if ((rightSum != -1 && rightSum < (balancing * (vector.length - 1 - i))))
        {
          result[i] -= 1;
          result[i + 1] += 1;
        }
        else if ((leftSum != -1 && leftSum < (balancing * i)))
        {
          result[i] -= 1;
          result[i - 1] += 1;
        }
      }
    }

    return result;
  }

  public List<int[]> readInput(File f) throws IOException
  {
    if (!f.exists())
    {
      return Collections.emptyList();
    }

    List<int[]> result = new ArrayList<int[]>();
    BufferedReader reader = null;

    try
    {
      reader = new BufferedReader(new FileReader(f));

      String line;
      int i = 0;
      int[] vector = null;
      while ((line = reader.readLine()) != null)
      {
        //Line : espace split
        if (i == 0)
        {
          int vectorCount = Integer.valueOf(line);
          if (vectorCount != 0)
          {
            vector = new int[vectorCount];
            i++;
          }
          else
          {
            break;
          }
        }
        else if (i == 1)
        {
          vector = convertArray(line.split(" "));
          i++;
        }
        else
        {
          result.add(vector);
          i = 0;
        }
      }
    }
    finally
    {
      if (reader != null)
      {
        reader.close();
      }
    }

    return result;
  }

  /**
   * Convertie un tableau de String en Int
   *
   * @param stringArray
   *
   * @return
   */
  public int[] convertArray(String[] stringArray)
  {

    int intArray[] = new int[stringArray.length];
    for (int i = 0; i < stringArray.length; i++)
    {
      intArray[i] = Integer.parseInt(stringArray[i]);
    }
    return intArray;

  }

  public boolean isBalanceable(int[] vector)
  {
    return sums(vector) % vector.length == 0;
  }


  public int sums(int[] vector)
  {
    return sums(vector, 0, vector.length);
  }

  public int sums(int[] vector, int start, int end)
  {
    int result = 0;
    for (int i = start; (i < vector.length && i <= end); i++)
    {
      result += vector[i];
    }

    return result;
  }

  public int[] searchGreatestNumber(int[] vector, int lowThreshold)
  {
    List<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < vector.length; i++)
    {
      if (vector[i] > lowThreshold)
      {
        result.add(i);
      }
    }

    return toIntArray(result);
  }

  public int computeBalancingValue(int[] vector)
  {
    return sums(vector) / vector.length;
  }

  public boolean isBalancing(int[] vector)
  {
    if (vector.length == 0)
    {
      return true;
    }

    int balancingValue = vector[0];
    for (int i = 1; i < vector.length; i++)
    {
      if (vector[i] != balancingValue)
      {
        return false;
      }
    }

    return true;
  }

  public int[] toIntArray(List<Integer> input)
  {
    int[] result = new int[input.size()];

    int i = 0;
    for (int intValue : input)
    {
      result[i] = intValue;
      i++;
    }

    return result;
  }

  public int[] generateVector(int balancingValue, int nb, int nbTransfer)
  {
    Random random = new Random();
    int[] result = new int[nb];
    int index;
    Arrays.fill(result, balancingValue);
    for (int i = 0; i < nbTransfer; i++) {
      if (random.nextBoolean()) {
        index = random.nextInt(nb - 1);
        result[index + 1]++;
        result[index]--;
      } else {
        index = random.nextInt(nb - 1) + 1;
        result[index - 1]++;
        result[index]--;
      }
    }
    return result;
  }

  public String toStringInput(int[] vector)
  {
    StringBuilder sb = new StringBuilder();

    int count=0;
    for(int it : vector)
    {
      if(count != 0)
      {
        sb.append(" ");
      }
      sb.append(it);
      count++;
    }

    return sb.toString();
  }
}
