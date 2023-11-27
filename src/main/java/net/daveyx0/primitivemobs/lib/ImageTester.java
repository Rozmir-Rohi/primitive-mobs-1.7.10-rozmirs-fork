package net.daveyx0.primitivemobs.lib;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageTester {
  public static int[] main(InputStream fileloc) throws Exception {
    ImageInputStream is = ImageIO.createImageInputStream(fileloc);
    Iterator<ImageReader> iter = ImageIO.getImageReaders(is);
    if (!iter.hasNext()) {
      System.out.println("Cannot load the specified file ");
      System.exit(1);
    } 
    ImageReader imageReader = iter.next();
    imageReader.setInput(is);
    BufferedImage imageOld = imageReader.read(0);
    BufferedImage image = getScaledImage(imageOld, 8, 8);
    int height = image.getHeight();
    int width = image.getWidth();
    Map<Object, Object> m = new HashMap<Object, Object>();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int rgb = image.getRGB(i, j);
        int[] rgbArr = getRGBArr(rgb);
        if (rgbArr[3] != 0) {
          Integer counter = (Integer)m.get(Integer.valueOf(rgb));
          if (counter == null)
            counter = Integer.valueOf(0); 
          Integer integer1 = counter, integer2 = counter = Integer.valueOf(counter.intValue() + 1);
          m.put(Integer.valueOf(rgb), counter);
        } 
      } 
    } 
    int[] colour = getMostCommonColour(m);
    return colour;
  }
  
  public static int[] getMostCommonColour(Map map) {
    List<?> list = new LinkedList(map.entrySet());
    Collections.sort(list, new Comparator() {
          public int compare(Object o1, Object o2) {
            return ((Comparable)((Map.Entry)o1).getValue()).compareTo(((Map.Entry)o2).getValue());
          }
        });
    Map.Entry me = (Map.Entry)list.get(list.size() - 1);
    int[] rgb = getRGBArr(((Integer)me.getKey()).intValue());
    return rgb;
  }
  
  public static int[] getRGBArr(int pixel) {
    int alpha = pixel >> 24 & 0xFF;
    int red = pixel >> 16 & 0xFF;
    int green = pixel >> 8 & 0xFF;
    int blue = pixel & 0xFF;
    return new int[] { red, green, blue, alpha };
  }
  
  private static BufferedImage getScaledImage(BufferedImage src, int w, int h) {
    int finalw = w;
    int finalh = h;
    double factor = 1.0D;
    if (src.getWidth() > src.getHeight()) {
      factor = src.getHeight() / src.getWidth();
      finalh = (int)(finalw * factor);
    } else {
      factor = src.getWidth() / src.getHeight();
      finalw = (int)(finalh * factor);
    } 
    if (finalw <= 0)
      finalw = w; 
    if (finalh <= 0)
      finalh = h; 
    BufferedImage resizedImg = new BufferedImage(finalw, finalh, 3);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(src, 0, 0, finalw, finalh, null);
    g2.dispose();
    return resizedImg;
  }
}
