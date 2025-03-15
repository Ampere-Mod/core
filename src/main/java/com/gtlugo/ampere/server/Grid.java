package com.gtlugo.ampere.server;

public class Grid {
  private double max_wattage;
  private double in_use_wattage;

  public void register() {

  }

  public boolean is_overloaded() {
    return max_wattage < in_use_wattage;
  }
}
