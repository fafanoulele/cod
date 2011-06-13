import bsh.Console;
import junit.framework.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: fbousquet
 * Date: 11/06/11
 * Time: 18:32
 * 
 */
public class CodMainTest
{
  CodMain cmt;

  @BeforeTest
  public void instanciate()
  {
    cmt = new CodMain();
  }

  @Test
  public void testReadInput() throws IOException
  {
    File f = new File(CodMainTest.class.getResource("input.txt").getFile());

    cmt = new CodMain();

    List<int[]> result = cmt.readInput(f);

    Assert.assertTrue(result.size() == 3);
    Assert.assertTrue(result.get(0).length == 3);
    Assert.assertTrue(result.get(1).length == 4);
    Assert.assertTrue(result.get(2).length == 5);

    Assert.assertEquals(1, result.get(0)[0]);
    Assert.assertEquals(0, result.get(1)[1]);
    Assert.assertEquals(6, result.get(2)[2]);
    
  }

  @Test
  public void testSums()
  {
    int [] input = new int[0];

    Assert.assertEquals(cmt.sums(input), 0);
    Assert.assertEquals(cmt.sums(input,0, 3), 0);

    input = new int[]{5,6,3};

    Assert.assertEquals(14, cmt.sums(input));
    Assert.assertEquals(3, cmt.sums(input, 2, 3));
    Assert.assertEquals(9, cmt.sums(input, 1, 3));
    Assert.assertEquals(5, cmt.sums(input, 0, 0));
  }

  @Test
  public void testIsBalaceable()
  {
    int[] input = new int[]{3,2,6,2,9};

    Assert.assertFalse(cmt.isBalanceable(input));

    input = new int[]{4,0,3,1};

    Assert.assertTrue(cmt.isBalanceable(input));
    
  }

  @Test
  public void testIsBalancing()
  {
    int[] input = new int[]{3,2,6,2,9};

    Assert.assertFalse(cmt.isBalancing(input));

    input = new int[]{2,2,2,2,2};

    Assert.assertTrue(cmt.isBalancing(input));
  }

  @Test
  public void testComputeBalancingValue()
  {
    int[] input = new int[]{4,0,3,1};

    Assert.assertEquals(2, cmt.computeBalancingValue(input));
  }

  @Test
  public void testSearchGreatestNumber()
  {
    int[] input = new int[]{4,0,3,1};

    int[] result = cmt.searchGreatestNumber(input, cmt.computeBalancingValue(input));

    Assert.assertTrue(result.length == 2);
    Assert.assertEquals(0,result[0]);
    Assert.assertEquals(2,result[1]);
  }

  @Test
  public void testIteration()
  {
    int[] input = new int[]{4,0,3,1};

    int[] result = cmt.iteration(input, 2);

    Assert.assertEquals(3, result[0]);
    Assert.assertEquals(1, result[1]);
    Assert.assertEquals(2, result[2]);
    Assert.assertEquals(2, result[3]);

    input = new int[]{3,1,2,2};

    result = cmt.iteration(input, 2);

    Assert.assertEquals(2, result[0]);
    Assert.assertEquals(2, result[1]);
    Assert.assertEquals(2, result[2]);
    Assert.assertEquals(2, result[3]);

    input = new int[]{1,0,5};

    result = cmt.iteration(input, 2);

    Assert.assertEquals(1, result[0]);
    Assert.assertEquals(1, result[1]);
    Assert.assertEquals(4, result[2]);

    input = new int[]{1,1,4};

    result = cmt.iteration(input, 2);

    Assert.assertEquals(2, result[0]);
    Assert.assertEquals(1, result[1]);
    Assert.assertEquals(3, result[2]);

    input = new int[]{2,1,3};

    result = cmt.iteration(input, 2);

    Assert.assertEquals(2, result[0]);
    Assert.assertEquals(2, result[1]);
    Assert.assertEquals(2, result[2]);
  }

  @Test
  public void testWriteFile()
  {
    List<int[]> result = new ArrayList<int[]>(3);

    result.add(new int[]{1,0,5});
    result.add(new int[]{1,1,4});
    result.add(new int[]{2,1,3});
    result.add(new int[]{2,2,2});

    cmt.writeFile(result, new PrintWriter(System.out));
  }

  @Test
  public void testMain() throws IOException
  {
    String[] args = new String[1];
    args[0] = CodMainTest.class.getResource("input.txt").getFile();

    CodMain.main(args);

    File tmp = File.createTempFile("input","tmp");
    tmp.deleteOnExit();

    args = new String[2];
    args[0] = CodMainTest.class.getResource("input.txt").getFile();
    args[1] = tmp.getAbsolutePath();

    CodMain.main(args);

    String expected = readStreamContent(new File(CodMainTest.class.getResource("output.txt").getFile()));
    String actual = readStreamContent(tmp);

    Assert.assertEquals(expected, actual);
  }

  private String readStreamContent(File f) throws IOException
  {
    if (!f.exists())
    {
      return null;
    }

    BufferedReader buffer = null;
    try
    {
      buffer = new BufferedReader(new FileReader(f));
      String line;
      StringBuilder builder = new StringBuilder();
      while ((line = buffer.readLine()) != null)
      {
        builder.append(line);
        builder.append("\n");
      }

      return builder.toString();
    }
    finally
    {
      try
      {
        if (buffer != null)
        {
          buffer.close();
        }
      }
      catch (IOException e)
      {
        //do nothing
      }
    }
  }

  @Test
  public void testGenerateVector()
  {
    System.out.println(cmt.toStringInput(cmt.generateVector(52, 64, 5000)));
  }
}
