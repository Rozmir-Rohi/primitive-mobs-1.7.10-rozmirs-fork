package net.daveyx0.primitivemobs.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class ResourceChecker {
  private static SimpleReloadableResourceManager resourceManager;
  
  public static IResource getResource(String domain, String filename) {
    if (resourceManager == null)
      resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager(); 
    try {
      return resourceManager.getResource(new ResourceLocation(domain, filename));
    } catch (Exception e) {
      return null;
    } 
  }
  
  public static void getModFileDirectories() {
    SimpleReloadableResourceManager resourceManager = (SimpleReloadableResourceManager)Minecraft.getMinecraft().getResourceManager();
    IResource resource = getResource("primitivemobs", "sounds.json");
    ResourceLocation location = new ResourceLocation("primitivemobs", "sounds.json");
    InputStream stream = resource.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    try {
      while (reader.ready())
        System.out.println(reader.readLine()); 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
