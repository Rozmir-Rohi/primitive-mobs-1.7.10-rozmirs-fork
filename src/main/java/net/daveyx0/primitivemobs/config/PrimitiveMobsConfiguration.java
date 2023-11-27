package net.daveyx0.primitivemobs.config;

import java.io.File;

public class PrimitiveMobsConfiguration {
  public static File generalConfigFile;
  
  public static File spawnRatesConfigFile;
  
  public static void init(String configpath) {
    generalConfigFile = new File(configpath + "general.cfg");
    spawnRatesConfigFile = new File(configpath + "spawn_rates.cfg");
    PrimitiveMobsConfigurationGeneral.init(generalConfigFile);
    PrimitiveMobsConfigurationSpawns.init(spawnRatesConfigFile);
  }
  
  public static void postInit(String configpath) {}
}
